package com.marcomarchionni.ibportfolio.repositories;

import com.marcomarchionni.ibportfolio.domain.FlexStatement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlexStatementRepository extends JpaRepository<FlexStatement, Long> {

    Optional<FlexStatement> findFirstByOrderByToDateDesc();

    List<FlexStatement> findByOrderByFromDateAsc();

//    @Query("SELECT MAX(F.toDate) FROM flex_statement F")
//    LocalDate findLastReportedDate();

}