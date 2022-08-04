package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.model.domain.FlexInfo;
import com.marcomarchionni.ibportfolio.repositories.FlexInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class FlexInfoServiceImpl implements FlexInfoService {

    private final FlexInfoRepository flexInfoRepository;

    @Autowired
    public FlexInfoServiceImpl(FlexInfoRepository flexInfoRepository) {
        this.flexInfoRepository = flexInfoRepository;
    }

    @Override
    public LocalDate getLastReportDate() {
        Optional<FlexInfo> optionalLastFlex = flexInfoRepository.findFirstByOrderByToDateDesc();
        return optionalLastFlex.map(FlexInfo::getToDate).orElse(LocalDate.MIN);
    }

    @Override
    public void save(FlexInfo flexInfo) {
        flexInfoRepository.save(flexInfo);
    }

    public List<FlexInfo> findAllOrderedByFromDateAsc() {
        return flexInfoRepository.findByOrderByFromDateAsc();
    }
}
