package com.marcomarchionni.ibportfolio.repositories;

import com.marcomarchionni.ibportfolio.models.FlexInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlexInfoRepository extends JpaRepository<FlexInfo, Long> {

    // find last report day
    Optional<FlexInfo> findFirstByOrderByToDateDesc();
    Optional<FlexInfo> findFirstByOrderByFromDateAsc();
    List<FlexInfo> findByOrderByFromDateAsc();
}