package com.marcomarchionni.ibportfolio.repositories;

import com.marcomarchionni.ibportfolio.models.domain.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {

    @Query("SELECT p FROM position p WHERE " +
            "(:symbol is null or p.symbol = :symbol) and" +
            "(:assetCategory is null or p.assetCategory = :assetCategory) and" +
            "(:tagged is null or ((:tagged is true and p.strategy is not null ) or (:tagged is false and p.strategy is null)))")
    List<Position> findWithParameters(@Param("tagged") Boolean tagged,
                                      @Param("symbol") String symbol,
                                      @Param("assetCategory") String assetCategory);

}
