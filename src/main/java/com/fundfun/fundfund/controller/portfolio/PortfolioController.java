package com.fundfun.fundfund.controller.portfolio;

import com.fundfun.fundfund.domain.portfolio.Portfolio;
import com.fundfun.fundfund.dto.portfolio.PortfolioDto;
import com.fundfun.fundfund.service.portfolio.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class PortfolioController {

    @Autowired
    private final PortfolioService portfolioService;

    @GetMapping("/portfolio")
    public String goPortfolioList(Model model) {
        return "portfolio/portfolioList";
    }

    //포트폴리오 상세페이지 조회
    @GetMapping("/portfolio/goDetail")
    public String goDetail(HttpServletRequest req, HttpServletResponse res, Model model) {
        UUID id = UUID.fromString(req.getParameter("id"));
        model.addAttribute("data", portfolioService.selectPortById(id));
        return "portfolio/portfolioDetail";
        //존재하지 않은 게시물 처리
    }

    @PostMapping(value = "/portfolio/getData")
    @ResponseBody
    public HashMap<String, Object> getData(@RequestBody Map<String, Object> paramMap) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("portfolioList", portfolioService.selectAll());
        return map;
    }

    //포트폴리오 등록하기
    @GetMapping("/portfolio/write")
    public String goIdeaWrite(Model model) {
     //   model.addAttribute("form", new PortfolioForm());
        return "/portfolio/write";
    }
    @GetMapping ("/portfolio/write")
    public String create(Portfolio form) {
        Portfolio portfolio = new Portfolio();
        portfolio.setTitle(form.getTitle());
        portfolio.setContentPortfolio(form.getContentPortfolio());
        portfolio.setWarnLevel(form.getWarnLevel());
        portfolio.setBeneRatio(form.getBeneRatio());
        return "redirect:/portfolio";
        //(값 입력안되면 등록 못하게 하고 싶음)  >> not empty라는 주석을 봤는데 여기가 아니라 entity에 달아야하는것인지 구음..
    }
    
    
//    //포트폴리오 수정하기
//    @GetMapping("/postfolio/detail/edit/{portfolioId}")
//    public String goIdeaEdit(@PathVariable UUID portfolioId, Model model) {
//        Optional<PortfolioDto> portOptional
//                = Optional.ofNullable(portfolioService.selectPortById(portfolioId));
//        if (portOptional.isPresent()) {
//            Portfolio port = portOptional.get();
//            PortfolioForm form = new PortfolioForm();
//            form.setContentPost(post.getContentPost());
//            form.setWarnLevel(form.getWarnLevel());
//            form.setBeneRatio(form.getBeneRatio());
//            model.addAttribute("form", form);
//            return "idea/ideaEdit";
//        } else {
//            // 존재하지 않는 게시물일 경우 처리
//            return "redirect:/postfolio/detail";
//        }
}

