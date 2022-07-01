package com.marcomarchionni.ibportfolio.repositories;

import com.marcomarchionni.ibportfolio.models.FlexStatement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlexStatementRepository extends JpaRepository<FlexStatement, Long> {

    // find last report day
    Optional<FlexStatement> findFirstByOrderByToDateDesc();
    Optional<FlexStatement> findFirstByOrderByFromDateAsc();
    List<FlexStatement> findByOrderByFromDateAsc();
}