package com.fundfun.fundfund.controller.portfolio;

import com.fundfun.fundfund.domain.opinion.Opinion;
import com.fundfun.fundfund.domain.portfolio.Portfolio;
import com.fundfun.fundfund.domain.portfolio.StPortfolio;
import com.fundfun.fundfund.domain.user.Role;
import com.fundfun.fundfund.domain.user.UserAdapter;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.dto.opinion.OpinionDto;
import com.fundfun.fundfund.dto.portfolio.PortfolioDto;
import com.fundfun.fundfund.dto.post.PostDto;
import com.fundfun.fundfund.dto.vote.VoteDto;
import com.fundfun.fundfund.service.opinion.OpinionService;
import com.fundfun.fundfund.service.portfolio.PortfolioService;
import com.fundfun.fundfund.service.post.PostService;
import com.fundfun.fundfund.service.vote.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
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
    private final PostService postService;

    @Autowired
    private final VoteService voteService;

    @GetMapping("/portfolio")
    public String goPortfolioList(HttpServletRequest req, HttpServletResponse res, Model model,
                                  @AuthenticationPrincipal UserAdapter adapter) {
        String reqData = req.getParameter("postId");  // 뷰에서 받은 postId

        if (reqData == null) { // postId 없이 url만 입력 후 진입 시 -> /post/list 로 강제 이동
            model.addAttribute("state", "noId");  // 모델에 state속성 추가
        } else {
            UUID postId = UUID.fromString(reqData); // 입력받은 postId 형식( String ) UUID 형식으로 변환

            PostDto postDto = postService.selectPostById(postId);
            if (postDto.getId() == null) { // 실제 POST DB에 해당 postId의 데이터가 있는지 검증
                model.addAttribute("state", "noData"); // 없을 때 -> 경고창 띄운 후 /post/list 로 강제 이동
            } else { // 정상 데이터 확인 시
                model.addAttribute("postId", postId);  // postId 값 넣기( 목록 조회 AJAX 에서 사용해야 하므로 뷰로 다시 넘김 )
                model.addAttribute("state", "pass"); // 정상 결과 값 넣기

                /*
                 * 투표 여부 확인( 한 POST 당 한번만 투표 가능 ) : 입력받은 postId와 로그인한 사용자로 opinion 테이블에서 검색
                 * 0 : 투표 이력 없음
                 * 1 : 투표 이력 있음
                 */
                String chkVote = opinionService.checkOpinion(voteService.selectVoteByPostId(postId), adapter.getUser()) ? "1" : "0";
                model.addAttribute("chkVote", chkVote); // 투표 여부 넣기
                model.addAttribute("authChk", adapter.getUser().getRole().toString().equals(Role.FUND_MANAGER.name()) ? 2 : 1);
            }
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
    public String goDetail(HttpServletRequest req, HttpServletResponse res, Model model,
                           @AuthenticationPrincipal UserAdapter adapter) {
        String portfolioId = req.getParameter("id");
        String postId = req.getParameter("postId");
        String btnVisible = "";
        String regVisible = "";
        System.out.println("현재 porfolioId ="+ portfolioId );
        System.out.println("현재 postId ="+postId);

        if (portfolioId != null) {
            UUID id = UUID.fromString(portfolioId);
            PortfolioDto portfolioDto = new PortfolioDto();
            portfolioDto = portfolioService.selectById(id);
            model.addAttribute("data", portfolioDto);

            regVisible = portfolioDto.getStatusPortfolio().equals(StPortfolio.WINNER)
                    && portfolioDto.getUserId().equals(adapter.getUser().getId()) ? "1" : "0";
            System.out.println("regVisible = " + regVisible);
            // 상품 등록 활성화 여부 - 해당 포트폴리오가 선출되었고, 접속 유저가 작성자 본인일 때

            btnVisible = portfolioDto.getUserId().equals(adapter.getUser().getId()) ? "1" : "0";
        } else {
            model.addAttribute("data", null);
        }

        model.addAttribute("state", req.getParameter("state"));
        model.addAttribute("postId", postId);
        model.addAttribute("btnVisible", btnVisible);
        model.addAttribute("regVisible", regVisible);

        return "portfolio/portfolioDetail";
    }
    // 포트폴리오 목록 불러오기 - ajax (0521 코드)
    @PostMapping(value = "/portfolio/getData")
    @ResponseBody
    public HashMap<String, Object> getData(@RequestBody Map<String, Object> paramMap) {
        HashMap<String, Object> map = new HashMap<>();
        UUID postId = UUID.fromString(paramMap.get("postId").toString());
        List<PortfolioDto> portfolioList = portfolioService.selectPortByPostId(postId);
        List<Integer> voteList = new ArrayList<>(portfolioList.size());
        map.put("portfolioList", portfolioList);

        for (int i = 0; i < portfolioList.size(); i++) {
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
    public HashMap<String, Object> votePortfolio(@RequestBody Map<String, Object> paramMap,
                                                 @AuthenticationPrincipal UserAdapter adapter) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("msg", "fail");

        try {
            OpinionDto opinionDto = new OpinionDto();
            UUID postId = UUID.fromString(paramMap.get("postId").toString());
            UUID portfolioId = UUID.fromString(paramMap.get("portfolioId").toString());
            VoteDto voteDto = voteService.selectVoteByPostId(postId);
            opinionDto.setVoteId(voteDto.getId());
            opinionDto.setUserId(adapter.getUser().getId());
            opinionDto.setVotedFor(portfolioId);

            opinionService.createOpinion(opinionDto);

            map.put("msg", "success");
        } catch (Exception e) {
            System.out.println("Portfolio 투표 시 에러 : " + e);
        } finally {
            return map;
        }
    }

    //0521 코드
    // 포트폴리오 수정/작성 - ajax
    //0521 코드
    // 포트폴리오 수정/작성 - ajax
    @PostMapping(value = "/portfolio/commitData")
    @ResponseBody
    public HashMap<String, Object> commitData(@RequestBody Map<String, Object> paramMap,
                                              @AuthenticationPrincipal UserAdapter userAdapter) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("resultState", "fail");
        map.put("msg", "저장 중 문제가 발생하였습니다.");

        try {
            if (userAdapter.getUser().getRole().toString().equals(Role.FUND_MANAGER.name())) { // 권한 검사
                String state = paramMap.get("state").toString(); // 수정/삭제/작성 여부 - U / D / W
                UUID postId = UUID.fromString(paramMap.get("postId").toString());
                PortfolioDto portfolioDto = new PortfolioDto();
                VoteDto voteDto = voteService.selectVoteByPostId(postId); // vote 데이터 가져오기

                portfolioDto.setPostId(postId); // postId Setting
                portfolioDto.setVoteId(voteDto.getId()); // voteId Setting
                portfolioDto.setUserId(userAdapter.getUser().getId()); // 사용자Id Setting

                if (state.equals("W")) { // 작성
                    portfolioDto.setTitle(paramMap.get("title").toString()); // 입력한 title
                    portfolioDto.setBeneRatio(Float.parseFloat(paramMap.get("beneRatio").toString())); // 입력한 beneRatio
                    portfolioDto.setWarnLevel(paramMap.get("warnLevel").toString()); // 입력한 warnLevel
                    portfolioDto.setContentPortfolio(paramMap.get("content").toString()); // 입력한 content
                    portfolioService.createPort(portfolioDto);
                    map.put("resultState", "success");
                    map.put("msg", "정상 저장되었습니다.");
                } else {
                    //수정/삭제
                    UUID portfolioId = UUID.fromString(paramMap.get("portfolioId").toString());
                    PortfolioDto portfolioChkDto = portfolioService.selectById(portfolioId);

                    // 해당 포트폴리오Id로 로그인 한 사용자와 작성자 일치 여부
                    if (portfolioChkDto.getUserId().equals(userAdapter.getUser().getId())) {
                        if (state.equals("D")) { // 삭제
                            portfolioDto = new PortfolioDto();
                            portfolioDto.setId(portfolioId);
                            portfolioService.deletePort(portfolioDto);

                            map.put("resultState", "success");
                            map.put("msg", "정상 삭제되었습니다.");
                        } else { // 수정
                            portfolioDto.setTitle(paramMap.get("title").toString()); // 입력한 title
                            portfolioDto.setBeneRatio(Float.parseFloat(paramMap.get("beneRatio").toString())); // 입력한 beneRatio
                            portfolioDto.setWarnLevel(paramMap.get("warnLevel").toString()); // 입력한 warnLevel
                            portfolioDto.setContentPortfolio(paramMap.get("content").toString()); // 입력한 content
                            portfolioDto.setId(portfolioId);
                            portfolioService.updatePort(portfolioDto);

                            map.put("resultState", "success");
                            map.put("msg", "정상 수정되었습니다.");
                        }
                    }else {
                        map.put("resultState", "notUser");
                        map.put("msg", "해당 게시글 작성자가 다릅니다.");
                    }

                }
            } else {
                map.put("resultState", "notAuth");
                map.put("msg", "게시글 생성/수정/삭제 권한이 없습니다.");
            }
        } catch (Exception e) {
            System.out.println("Portfolio 수정/등록 시 에러 : " + e);
        } finally {
            return map;
        }
    }
}