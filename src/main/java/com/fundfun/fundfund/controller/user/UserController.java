package com.fundfun.fundfund.controller.user;

import com.fundfun.fundfund.domain.user.*;
import com.fundfun.fundfund.dto.post.PostDto;
import com.fundfun.fundfund.service.post.PostService;
import com.fundfun.fundfund.service.post.PostServiceImpl;
import com.fundfun.fundfund.service.user.UserService;
import com.fundfun.fundfund.util.ApiResponse;
import com.fundfun.fundfund.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Controller
public class UserController {

    private final UserService userService;
    private final PostService postService;

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

    /**
     * @Author yeoooo
     * 접속한 유저의 아이디를 통해 프로필 사진을 찾아 이를 갱신해
     * 유저 정보 수정 폼을 포함시키는 함수
     *
     */
    @GetMapping("/mypage/edit")
    public String myPageForm(@AuthenticationPrincipal UserAdapter adapter, Model model) throws IOException {
        model.addAttribute("imgDir", Util.findProfileImg(adapter.getUser().getId()));
        model.addAttribute("form", new UserUpdateForm());
        return "user/mypage_form";
    }

    /**
     * @Author yeoooo
     * 유저의 id 값으로 된 이미지 파일을 받아 이를 서버의 파일과 대조하고
     * 존재하지 않는 경우 생성, 존재하는 경우 대체하고 나머지 정보를 입력받아
     * 정보를 수정하는 함수
     *
     */
    @PostMapping("/mypage/edit")
    public String editMyPage(@AuthenticationPrincipal UserAdapter adapter, @Valid UserUpdateForm form) {
        String directoryPath = "src/main/resources/static/img/profiles";
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
        }
        File[] files = directory.listFiles();
        String fileName = "";

        String fileNamePattern = adapter.getUser().getId().toString();
        File targetFile = null;

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
                .email(form.getEmail().length() == 0 ? dto.getEmail() : form.getEmail())
                .phone(form.getPhone().length() == 0 ? dto.getPhone() : form.getPhone())
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
    public String goMyPageUser(@AuthenticationPrincipal UserAdapter adapter, Model model) {
        UserDTO dto = userService.findById(adapter.getUser().getId());
        List<PostDto> postDTO = postService.selectPostByUserId(dto.getId());
        model.addAttribute("user", dto);
        model.addAttribute("posts", postDTO);
        model.addAttribute("formattedMoney", Util.number.formatNumberWithComma(dto.getMoney()));

        return "user/myPageUser";
    }

    @GetMapping("/user/myPageFund")
    public String goMyPageFund() {
        return "user/myPageFund";
    }

    /**
     * @Author yeoooo
     * <img> 태그의 요청에 대한 응답으로 헤더 값에 Cache-Control을 넣어 이미지를 전송하는 함수
     */
    @GetMapping("/img/profiles/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) throws IOException {
        String directoryPath = "src/main/resources/static/img/profiles";
        Path filePath = Paths.get(directoryPath, filename);

        // 이미지 파일 읽기
        byte[] imageBytes = Files.readAllBytes(filePath);

        // Cache-Control 헤더 설정
        CacheControl cacheControl = CacheControl.maxAge(0, TimeUnit.SECONDS).noCache().mustRevalidate();
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .cacheControl(cacheControl)
                .body(imageBytes);
    }
}
