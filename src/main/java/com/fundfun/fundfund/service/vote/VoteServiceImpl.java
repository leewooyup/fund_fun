package com.fundfun.fundfund.service.vote;

import com.fundfun.fundfund.domain.vote.Vote;
import com.fundfun.fundfund.dto.vote.VoteDto;
import com.fundfun.fundfund.repository.vote.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService{
    @Autowired
    private final VoteRepository voteRepository;

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


    @Override
    public void updateVoteStatus(UUID voteId) {
        // Vote 상태 업데이트 로직
        Vote vote = voteRepository.findById(voteId).orElse(null);
        if (vote != null) {
            vote.updateStatus();
            voteRepository.save(vote);
        }
    }

    @Override
    public void deleteVote(UUID voteId){
        Vote vote = voteRepository.findById(voteId).orElse(null);
        if(vote == null)
            throw new RuntimeException("해당 투표가 존재하지 않습니다.");

        voteRepository.delete(vote);
    }
}