package com.marcomarchionni.ibportfolio.repositories;

import com.marcomarchionni.ibportfolio.model.domain.FlexInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlexInfoRepository extends JpaRepository<FlexInfo, Long> {

    Optional<FlexInfo> findFirstByOrderByToDateDesc();

    List<FlexInfo> findByOrderByFromDateAsc();
}