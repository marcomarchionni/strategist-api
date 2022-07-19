package com.marcomarchionni.ibportfolio.repositories;

import com.marcomarchionni.ibportfolio.models.Dividend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DividendRepository extends JpaRepository<Dividend, Long> {

  void deleteByOpenClosed(String openClosed);
}
