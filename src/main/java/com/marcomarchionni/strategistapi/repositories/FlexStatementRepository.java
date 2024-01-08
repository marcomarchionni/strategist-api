package com.marcomarchionni.strategistapi.repositories;

import com.marcomarchionni.strategistapi.domain.FlexStatement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FlexStatementRepository extends JpaRepository<FlexStatement, Long> {
    Optional<FlexStatement> findFirstByAccountIdOrderByToDateDesc(String accountId);
}