package com.fundfun.fundfund.controller.portfolio;

import com.fundfun.fundfund.controller.post.PostForm;
import com.fundfun.fundfund.domain.portfolio.Portfolio;
import com.fundfun.fundfund.dto.portfolio.PortfolioDto;
import com.fundfun.fundfund.dto.vote.VoteDto;
import com.fundfun.fundfund.service.portfolio.PortfolioService;
import com.fundfun.fundfund.service.vote.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.sampled.Port;
import javax.validation.Valid;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class PortfolioController {

    @Autowired
    private final PortfolioService portfolioService;

    @Autowired
    private final VoteService voteService;

    // 포트폴리오 목록 이동
    @GetMapping("/portfolio")
    public String goPortfolioList(HttpServletRequest req, HttpServletResponse res, Model model) {
        String reqData = req.getParameter("postId");

        /*if(reqData == null) {
            model.addAttribute("state", "noId");
        } else {
            model.addAttribute("postId", UUID.fromString(reqData));
            model.addAttribute("state", "pass");
        }*/
        return "portfolio/portfolioList";
    }

    /*
     * 포트폴리오 상세/수정/작성
     * state
     * - R : 상세
     * - U : 수정
     * - W : 생성
     */
    @GetMapping("/portfolio/goDetail")
    public String goDetail(HttpServletRequest req, HttpServletResponse res, Model model) {
        String portfolioId = req.getParameter("id");
        String postId = req.getParameter("postId");

    /*    if(portfolioId != null) {
            UUID id = UUID.fromString(portfolioId);
            model.addAttribute("data", portfolioService.selectById(id));
        } else {
            model.addAttribute("data", null);
        }*/

        model.addAttribute("state", req.getParameter("state"));
        model.addAttribute("postId", postId);

        return "portfolio/portfolioDetail";
        //존재하지 않은 게시물 처리
    }

    // 포트폴리오 목록 데이터 - ajax
    @PostMapping(value = "/portfolio/getData")
    @ResponseBody
    public HashMap<String, Object> getData(@RequestBody Map<String, Object> paramMap) {
        HashMap<String, Object> map = new HashMap<>();
        UUID postId = UUID.fromString(paramMap.get("postId").toString());

        map.put("portfolioList", portfolioService.selectPortByPostId(postId));

        return map;
    }

    // 포트폴리오 수정/작성 - ajax
    @PostMapping(value = "/portfolio/commitData")
    @ResponseBody
    public HashMap<String, Object> commitData(@RequestBody Map<String, Object> paramMap) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("msg", "fail");
        try {
            PortfolioDto portfolioDto = new PortfolioDto();
            String state = paramMap.get("state").toString();

            UUID postId = UUID.fromString(paramMap.get("postId").toString());
            VoteDto voteDto = voteService.selectVoteByPostId(postId);

            portfolioDto.setPostId(postId);
            portfolioDto.setVoteId(voteDto.getId());



            map.put("msg", "success");
        } catch (Exception e) {
            System.out.println("Portfolio 수정/등록 시 에러 : " + e);
        } finally {
            return map;
        }
    }
}
