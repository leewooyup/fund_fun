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

        if(reqData == null) {
            model.addAttribute("state", "noId");
        } else {
            model.addAttribute("postId", UUID.fromString(reqData));
            model.addAttribute("state", "pass");
        }
        return "portfolio/portfolioList";
    }

    /*
     * 포트폴리오 작성/수정/상세보기/삭제
     * state (비슷한 페이지여서 상태 설정해서, 그 때에 따라 만들어버림.)
     * - W : 작성(새로 생성)
     * - U : 수정
     * - R : 상세보기
     * - D : 삭제
     */
    
    //페이지 상세보기
    @GetMapping("/portfolio/goDetail")
    public String goDetail(HttpServletRequest req, HttpServletResponse res, Model model) {
        String portfolioId = req.getParameter("id");  //포폴아이디
        String postId = req.getParameter("postId");   //포스트아이디

        if(portfolioId != null) {
            UUID id = UUID.fromString(portfolioId);  //uuid로 가져오기 > String 변경
            model.addAttribute("data", portfolioService.selectById(id));
            // portfolioService에서 아이디로 불러오기

            // 존재하지 않은 게시물 처리
        } else {
            model.addAttribute("data", null);
        }

        model.addAttribute("state", req.getParameter("state"));  //상태
        model.addAttribute("postId", postId);  //포스트아이디 추가

        return "portfolio/portfolioDetail";

    }

    // 포트폴리오 목록 불러오기 - ajax
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
            
            // 포트폴리오 삭제
            if(state.equals("D")) {
                UUID portfolioId = UUID.fromString(paramMap.get("portfolioId").toString());
                portfolioDto.setId(portfolioId);
                portfolioService.deletePort(portfolioDto);

            } else { //포트폴리오 수정 및 생성

                portfolioDto.setTitle(paramMap.get("title").toString());
                portfolioDto.setBeneRatio(Float.parseFloat(paramMap.get("beneRatio").toString()));
                portfolioDto.setWarnLevel(paramMap.get("warnLevel").toString());
                portfolioDto.setContentPortfolio(paramMap.get("content").toString());

                if(state.equals("W")) {
                    portfolioService.createPort(portfolioDto);
                } else {
                    UUID portfolioId = UUID.fromString(paramMap.get("portfolioId").toString());
                    portfolioDto.setId(portfolioId);
                    portfolioService.updatePort(portfolioDto);
                }
            }
            map.put("msg", "success");   //성공
        } catch (Exception e) {
            System.out.println("Portfolio 수정/등록 시 에러 : " + e);
        } finally {
            return map;
        }
    }
}
