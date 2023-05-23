package com.fundfun.fundfund.controller.portfolio;

import com.fundfun.fundfund.domain.opinion.Opinion;
import com.fundfun.fundfund.domain.portfolio.Portfolio;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.dto.opinion.OpinionDto;
import com.fundfun.fundfund.dto.portfolio.PortfolioDto;
import com.fundfun.fundfund.dto.vote.VoteDto;
import com.fundfun.fundfund.service.opinion.OpinionService;
import com.fundfun.fundfund.service.portfolio.PortfolioService;
import com.fundfun.fundfund.service.vote.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class PortfolioController {
    @Autowired
    private final PortfolioService portfolioService;
    @Autowired
    private final OpinionService opinionService;

    @Autowired
    private final VoteService voteService;

    @GetMapping("/portfolio")
    public String goPortfolioList(HttpServletRequest req, HttpServletResponse res, Model model, @AuthenticationPrincipal Users user) {
        String reqData = req.getParameter("postId");

        if(reqData == null) {
            model.addAttribute("state", "noId");
        } else {
            model.addAttribute("postId", UUID.fromString(reqData));
            model.addAttribute("state", "pass");

            UUID postId = UUID.fromString(reqData);
            VoteDto voteDto = voteService.selectVoteByPostId(postId);
            model.addAttribute("chkVote", opinionService.checkOpinion(voteDto, user) ? "1" : "0");
        }
        return "portfolio/portfolioList";
    }

    /*
     * 포트폴리오 작성/수정/상세보기/삭제
     * state
     * - W : 작성(새로 생성)
     * - U : 수정
     * - R : 상세보기
     * - D : 삭제
     */
    @GetMapping("/portfolio/goDetail")
    public String goDetail(HttpServletRequest req, HttpServletResponse res, Model model) {
        String portfolioId = req.getParameter("id");
        String postId = req.getParameter("postId");

        if(portfolioId != null) {
            UUID id = UUID.fromString(portfolioId);
            model.addAttribute("data", portfolioService.selectById(id));
        } else {
            model.addAttribute("data", null);
        }

        model.addAttribute("state", req.getParameter("state"));
        model.addAttribute("postId", postId);

        return "portfolio/portfolioDetail";
        //존재하지 않은 게시물 처리
    }

   /* // 포트폴리오 목록 불러오기 - ajax (0521 코드)
    @PostMapping(value = "/portfolio/getData")
    @ResponseBody
    public HashMap<String, Object> getData(@RequestBody Map<String, Object> paramMap) {
        HashMap<String, Object> map = new HashMap<>();
        UUID postId = UUID.fromString(paramMap.get("postId").toString());
        List<PortfolioDto> portfolioList = portfolioService.selectPortByPostId(postId);
        List<Integer> voteList = new ArrayList<>(portfolioList.size());
        map.put("portfolioList", portfolioList);

        for(int i=0; i<portfolioList.size(); i++) {
            PortfolioDto portfolioDto = new PortfolioDto();
            portfolioDto.setId(portfolioList.get(i).getId());
            int num = opinionService.countByVotedFor(portfolioDto);
            voteList.add(i, num);
        }

        map.put("voteList", voteList);

        return map;
    }

    // 포트폴리오 투표 - ajax
    @PostMapping(value = "/portfolio/votePortfolio")
    @ResponseBody
    public HashMap<String, Object> votePortfolio(@RequestBody Map<String, Object> paramMap, @AuthenticationPrincipal Users user) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("msg", "fail");

        try {
            OpinionDto opinionDto = new OpinionDto();
            UUID postId = UUID.fromString(paramMap.get("postId").toString());
            UUID portpolioId = UUID.fromString(paramMap.get("portfolioId").toString());
            VoteDto voteDto = voteService.selectVoteByPostId(postId);
            opinionDto.setVoteId(voteDto.getId());
            opinionDto.setUserId(user.getId());
            opinionDto.setVotedFor(portpolioId);

            opinionService.createOpinion(opinionDto);

            map.put("msg", "success");
        } catch(Exception e) {
            System.out.println("Portfolio 투표 시 에러 : " + e);
        } finally {
            return map;
        }
    }

   /* //0522 수정한 부분
    @PostMapping(value = "/portfolio/getData")
    @ResponseBody
    public HashMap<String, Object> getData(@AuthenticationPrincipal Users user,
                                           @RequestBody Map<String, Object> paramMap) {

        // user 객체를 사용하여 필요한 작업 수행
        // String userId = String.valueOf(user.getId());
        HashMap<String, Object> map = new HashMap<>();
        UUID postId = UUID.fromString(paramMap.get("postId").toString());
        map.put("portfolioList", portfolioService.selectPortByPostId(postId));

        return map;
    }*/

    //0521 코드
    // 포트폴리오 수정/작성 - ajax
    @PostMapping(value = "/portfolio/commitData")
    @ResponseBody
    public HashMap<String, Object> commitData(@RequestBody Map<String, Object> paramMap, @AuthenticationPrincipal Users user) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("msg", "fail");
        try {
            PortfolioDto portfolioDto = new PortfolioDto();
            String state = paramMap.get("state").toString();

            UUID postId = UUID.fromString(paramMap.get("postId").toString());
            VoteDto voteDto = voteService.selectVoteByPostId(postId);

            portfolioDto.setPostId(postId);
            portfolioDto.setVoteId(voteDto.getId());
            portfolioDto.setUserId(user.getId());

            if(state.equals("D")) {
                UUID portfolioId = UUID.fromString(paramMap.get("portfolioId").toString());
                portfolioDto.setId(portfolioId);
                portfolioService.deletePort(portfolioDto);
            } else {
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

            map.put("msg", "success");
        } catch (Exception e) {
            System.out.println("Portfolio 수정/등록 시 에러 : " + e);
        } finally {
            return map;
        }
    }
}


   /* //0522 코드
    // 포트폴리오 수정/작성 - ajax
    @PostMapping(value = "/portfolio/commitData")
    @ResponseBody
    public HashMap<String, Object> commitData(@AuthenticationPrincipal Users user,
                                              @RequestBody Map<String, Object> paramMap) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("msg", "fail");

        //펀드매니저만 설정되게 해놓고 싶음.
        if (user.getRole().equals("fund_manager")) {
            try {
                PortfolioDto portfolioDto = new PortfolioDto();
                String state = paramMap.get("state").toString();

                UUID postId = UUID.fromString(paramMap.get("postId").toString());
                VoteDto voteDto = voteService.selectVoteByPostId(postId);

                portfolioDto.setPostId(postId);
                portfolioDto.setVoteId(voteDto.getId());

                // 포트폴리오 삭제
                if (state.equals("D")) {
                    UUID portfolioId = UUID.fromString(paramMap.get("portfolioId").toString());
                    portfolioDto.setId(portfolioId);
                    portfolioService.deletePort(portfolioDto);
                } else { //포트폴리오 수정 및 생성
                    portfolioDto.setTitle(paramMap.get("title").toString());
                    portfolioDto.setBeneRatio(Float.parseFloat(paramMap.get("beneRatio").toString()));
                    portfolioDto.setWarnLevel(paramMap.get("warnLevel").toString());
                    portfolioDto.setContentPortfolio(paramMap.get("content").toString());

                    if (state.equals("W")) {
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
        } else {
            //펀드매니저만 가능함 등록되지 않음;
        }
        return map;
    }*/




