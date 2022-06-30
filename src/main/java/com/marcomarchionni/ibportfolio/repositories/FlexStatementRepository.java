package com.marcomarchionni.ibportfolio.repositories;

import com.marcomarchionni.ibportfolio.models.FlexStatement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FlexStatementRepository extends JpaRepository<FlexStatement, Long> {

    Optional<FlexStatement> findFirstByOrderByToDateDesc();
}