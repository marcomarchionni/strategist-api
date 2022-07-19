package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.FlexInfo;
import com.marcomarchionni.ibportfolio.repositories.FlexInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class FlexInfoServiceImpl implements FlexInfoService {

    private final FlexInfoRepository flexInfoRepository;

    public FlexInfoServiceImpl(FlexInfoRepository flexInfoRepository) {
        this.flexInfoRepository = flexInfoRepository;
    }

    @Override
    public LocalDate getLatestDateWithDataInDb() {

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
