package com.fundfun.fundfund.repository.opinion;

import com.fundfun.fundfund.domain.opinion.Opinion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OpinionRepository extends JpaRepository<Opinion, UUID> {
}
