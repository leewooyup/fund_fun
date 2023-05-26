package com.fundfun.fundfund.service.vote;

import com.fundfun.fundfund.domain.post.Post;
import com.fundfun.fundfund.domain.vote.StVote;
import com.fundfun.fundfund.domain.vote.Vote;
import com.fundfun.fundfund.dto.vote.VoteDto;
import com.fundfun.fundfund.repository.post.PostRepository;
import com.fundfun.fundfund.repository.vote.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService{
    @Autowired
    private final VoteRepository voteRepository;

    @Autowired
    private final PostRepository postRepository;

    @Autowired
    private final ModelMapper modelMapper;

    @Override
    public Vote createVote(VoteDto voteDto) {
        // Vote 생성 로직
        Vote vote = modelMapper.map(voteDto, Vote.class);
        return voteRepository.save(vote);
    }

    @Override
    public List<VoteDto> selectAll(){
        /*List<Vote> voteList = voteRepository.findAll();
        List<VoteDto> voteDtoList = new ArrayList<>();
        for(Vote v : voteList){
            VoteDto voteDto = modelMapper.map(v, VoteDto.class);
            voteDto.setPostId
        }*/
        return voteRepository.findAll().stream().map(vote -> modelMapper.map(vote, VoteDto.class)).collect(Collectors.toList());
    }

    @Override
    public VoteDto selectVoteById(UUID voteId) {
        // Vote 조회 로직
        return modelMapper.map(voteRepository.findById(voteId).orElse(null), VoteDto.class);
    }

    @Override
    public VoteDto selectVoteByPostId(UUID postId) {
        Vote vote = voteRepository.findByPostId(postId);
        if(vote == null)
            throw new RuntimeException("해당 투표가 존재하지 않습니다.");
        VoteDto voteDto = modelMapper.map(vote, VoteDto.class);
        return voteDto;

    }


    /*
    투표 상태 체크 및 업데이트
     */
/*    public boolean updateVoteStatus(VoteDto voteDto) {

        //조건
            Vote vote = modelMapper.map(voteDto, Vote.class);
            voteRepository.save(vote);
            return true;
        }
        return false;
    }*/

    public boolean updateVoteStatus(VoteDto voteDto) {
        Vote vote = modelMapper.map(voteDto, Vote.class);

        // 현재 시간과 voteEnd 비교
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime voteEnd = vote.getVoteEnd();
        if (currentTime.isAfter(voteEnd) || currentTime.isEqual(voteEnd)) {
            vote.updateStatus();
            /*Post post =postRepository.findById(voteDto.getPostId()).orElse(null);
            if(post!=null){
                vote.linkPost(post);
                System.out.println("vote에 연결된 post : " + vote.getPost().getId());
            }*/
        }
        voteRepository.save(vote);
        return true;
        }


    public String plusWeeks(String startDate) {
        LocalDate voteStart = LocalDate.now();
        LocalDate voteEnd = voteStart.plusDays(7);
       return voteEnd.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }



    @Override
    public void deleteVote(UUID voteId){
        Vote vote = voteRepository.findById(voteId).orElse(null);
        if(vote == null)
            throw new RuntimeException("해당 투표가 존재하지 않습니다.");

        voteRepository.delete(vote);
    }
}