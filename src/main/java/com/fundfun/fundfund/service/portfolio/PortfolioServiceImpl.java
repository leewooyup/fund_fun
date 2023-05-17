package com.fundfun.fundfund.service.portfolio;

import com.fundfun.fundfund.domain.portfolio.Portfolio;
import com.fundfun.fundfund.repository.portfolio.PortRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
    public class PortfolioServiceImpl implements PortfolioService {
    private final PortRepository portRep;
    private final ModelMapper modelMapper;
    //전체 포폴조회
    public List<Portfolio> selectAll() {
        return portRep.findAll();
    }
    //제목으로 포폴조회
    public List<Portfolio> selectPortfolioByKeyword(String keyword) {
        return portRep.findByTitleContaining(keyword);

    }

    //작성자로 포폴조회
    public Optional<Portfolio> selectPortfolioByUserId(UUID userId) {
        return portRep.findById(userId);
    }

    //위험도로 포트폴리오 조회
    public List<Portfolio> selectPortfolioByWarnLevel(String warnLevel){

        return portRep.findByWarnLevel(warnLevel);
    }

    //예상수익율로 포트폴리오 조회
    public List<Portfolio> selectPortfolioByBeneRatio(int beneratio){
        return portRep.findByBeneRatio(beneratio);
    }

    //포트폴리오 삭제
    public void delete(Portfolio portfolio){
        portRep.delete(portfolio);
    }

    //포트폴리오 수정
    public void updatePort(Portfolio portfolio){
        Portfolio existingPort = portRep.findById(portfolio.getId()).orElse(null);
        if (existingPort != null) {
            // 변경할 필드값을 업데이트합니다.
            existingPort.setTitle(portfolio.getTitle());
            existingPort.setContentPortfolio(portfolio.getContentPortfolio());
            existingPort.setWarnLevel(portfolio.getWarnLevel());
            existingPort.setBeneRatio(portfolio.getBeneRatio());

            // 게시물을 저장하여 업데이트합니다.
            portRep.save(existingPort);

        }else{
            throw new RuntimeException("검색한 게시물이 존재하지 않습니다.");
        }
    }


}


