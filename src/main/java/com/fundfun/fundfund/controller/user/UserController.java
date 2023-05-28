package com.fundfun.fundfund.controller.user;

import com.fundfun.fundfund.domain.user.*;
import com.fundfun.fundfund.service.user.UserService;
import com.fundfun.fundfund.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Controller
public class UserController {

    private final UserService userService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/register")
    public String getRegisterForm(@RequestParam(value = "role", required = false) String role, Model model) {
        if(role == null){
            return "register_role";
        } else if(role.equals("COMMON")){
            model.addAttribute("role", role);
            return "register";
        } else {
            model.addAttribute("role", role);
            return "register";
        }
    }

    @PostMapping("/register")
    public String register(RegisterForm form, @RequestParam("role") String role) {
        UserDTO user = userService.register(Users.builder()
                .count(0L)
                .email(form.getEmail())
                .gender(Gender.valueOf(form.getGender()))
                .money(0L)
                .benefit(0L)
                .name(form.getName())
                .phone(form.getPhone())
                .password(bCryptPasswordEncoder.encode(form.getPassword()))
                .role(Role.valueOf(role))
                .total_investment(0L)
                .build()
        );

        log.info("[UserController] ]User Role {} has been registered.", user.getRole(), user.toString());
        return "redirect:/";
    }

    @GetMapping("/mypage")
    public String myPage(@AuthenticationPrincipal UserAdapter adapter, Model model) {
        UserDTO user = userService.findById(adapter.getUser().getId());

        model.addAttribute("user", user);
        if (user.getRole() == Role.COMMON) {
            model.addAttribute("investment", user.getOrders().stream().map(x -> x.getProduct()).collect(Collectors.toList()));
            model.addAttribute("posts", user.getPosts());

        } else if (user.getRole() == Role.FUND_MANAGER) {
            model.addAttribute("portfolios", user.getOn_vote_portfolio());
            model.addAttribute("products", user.getManaging_product());
        }
        return "index";
    }

    @GetMapping("/mypage/edit")
    public String myPageForm(@AuthenticationPrincipal UserAdapter adapter, Model model) throws IOException {
        String directoryPath = "src/main/resources/static/img/profiles";
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            throw new IllegalArgumentException("디렉토리를 찾을 수 없습니다: " + directoryPath);
        }

        File[] files = directory.listFiles();
        System.out.println("files.toString() = " + Arrays.toString(files));
        String fileName = "default.jpeg";
        if (files == null) {
            throw new IOException("디렉토리 내 파일을 읽어올 수 없습니다: " + directoryPath);
        }
        String fileNamePattern = adapter.getUser().getId().toString().replace("-","");

        for (File file : files) {
            if (file.isFile() && file.getName().split("\\.")[0].equals(fileNamePattern)) {
                fileName = file.getName();
                break;
            }
        }
        System.out.println("adapter = " + adapter.getUser().getId());
        model.addAttribute("imgDir", fileName.replace("-",""));
        model.addAttribute("form", new UserUpdateForm());
        return "user/mypage_form";
    }

    @PostMapping("/mypage/edit")
    @Transactional
    public String editMyPage(@AuthenticationPrincipal UserAdapter adapter, @Valid UserUpdateForm form) {
        String directoryPath = "src/main/resources/static/img/profiles";
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            // 디렉토리가 존재하지 않거나 디렉토리가 아닌 경우 처리 로직 추가
            // 예를 들어, 디렉토리를 생성하거나 오류를 반환할 수 있습니다.
            System.out.println("directory.exists() = " + directory.exists());
            System.out.println("directory = " + directory.isDirectory());
        }
        File[] files = directory.listFiles();
//        System.out.println(new File("/main/resources/static/img/profiles/").listFiles().toString());
        String fileName = "";

        String fileNamePattern = adapter.getUser().getId().toString();
        File targetFile = null;
        System.out.println("files = " + files);

        for (File file : files) {
            if (file.isFile() && file.getName().matches(fileNamePattern)) {
                fileName = file.getName();
                targetFile = file;
                break;
            }
        }

        if (targetFile != null) {
            targetFile.delete();
        }



        String base64Data = form.getImage().split(",")[1]; // 데이터 URL에서 실제 데이터 부분 추출
        String type = form.getImage().split(",")[0].split("/")[1].split(";")[0];
        byte[] decodedBytes = Base64.getDecoder().decode(base64Data); // Base64 디코딩


        File file = new File("src/main/resources/static/img/profiles/" + adapter.getUser().getId().toString().replace("-","") + "." + type);
        System.out.println("경로 = " + file.getPath());

        try (FileOutputStream fos = new FileOutputStream(file)) {
            FileCopyUtils.copy(decodedBytes, fos); // 파일로 저장
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UserDTO dto = userService.findById(adapter.getUser().getId());
        userService.update(dto.getId(), UserDTO
                .builder()
                .id(dto.getId())
                .password(dto.getPassword())
                .name(dto.getName())
                .email(form.getEmail() == null ? dto.getEmail() : form.getEmail())
                .phone(form.getPhone() == null ? dto.getPhone() : form.getPhone())
                .role(dto.getRole())
                .gender(dto.getGender())
                .money(dto.getMoney())
                .build());
        return "redirect:/";
    }

    @GetMapping("/user/charge")
    public String showMoney(@AuthenticationPrincipal UserAdapter adapter, Model model) {
        UserDTO dto = userService.findById(adapter.getUser().getId());
        long curPoints = dto.getMoney();
        model.addAttribute("curPoints", curPoints);
        return "user/charge";
    }

    @GetMapping("/user/myPageUser")
    public String goMyPageUser() {
        return "user/myPageUser";
    }

    @GetMapping("/user/myPageFund")
    public String goMyPageFund() {
        return "user/myPageFund";
    }
}
