package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.accessservice.PortfolioAccessService;
import com.marcomarchionni.strategistapi.domain.Portfolio;
import com.marcomarchionni.strategistapi.dtos.request.FindAllReq;
import com.marcomarchionni.strategistapi.dtos.request.PortfolioSave;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioDetail;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioSummary;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.UnableToDeleteEntitiesException;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.strategistapi.mappers.PortfolioMapper;
import com.marcomarchionni.strategistapi.repositories.PortfolioRepository;
import com.marcomarchionni.strategistapi.services.odata.PagingUtil;
import com.marcomarchionni.strategistapi.services.odata.PortfolioSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioAccessService portfolioAccessService;
    private final UserService userService;
    private final PortfolioMapper portfolioMapper;
    private final PortfolioRepository portfolioRepository;
    private final PortfolioSpecification portfolioSpecification;


    @Override
    public int getTotalCount() {
        return portfolioAccessService.count();
    }

    @Override
    public List<PortfolioSummary> findAll() {
        List<Portfolio> portfolios = portfolioAccessService.findAll();
        return portfolios.stream().map(portfolioMapper::portfolioToPortfolioSummary).toList();
    }

    @Override
    public List<PortfolioSummary> findAllWithPaging(FindAllReq findReq) {
        String accountId = userService.getUserAccountId();
        Pageable pageable = PagingUtil.createPageable(findReq);
        Specification<Portfolio> spec = portfolioSpecification.fromFilter(findReq.getFilter(), accountId);
        Page<Portfolio> portfolios = portfolioRepository.findAll(spec, pageable);
        return portfolios.stream().map(portfolioMapper::portfolioToPortfolioSummary).toList();
    }

    @Override
    public PortfolioDetail findById(Long portfolioId) {
        Portfolio portfolio = portfolioAccessService.findById(portfolioId).orElseThrow(
                () -> new EntityNotFoundException(Portfolio.class, portfolioId)
        );
        return portfolioMapper.toPortfolioDetailDto(portfolio);
    }

    @Override
    public ServiceType getServiceType() {
        return ServiceType.PORTFOLIO;
    }

    @Override
    public void deleteById(Long portfolioId) {
        Portfolio portfolioToDelete = portfolioAccessService.findById(portfolioId).orElseThrow(
                () -> new EntityNotFoundException(Portfolio.class, portfolioId)
        );
        if (!portfolioToDelete.getStrategies().isEmpty()) {
            throw new UnableToDeleteEntitiesException("Portfolio contains strategies and cannot be deleted");
        }
        portfolioAccessService.delete(portfolioToDelete);
    }


    @Override
    @Transactional
    public PortfolioSummary create(PortfolioSave portfolioSave) {

        // Check if portfolio with the same name already exists
        checkIfPortfolioNameExists(portfolioSave.getName());

        // Save portfolio
        var portfolio = Portfolio.builder().accountId(userService.getUserAccountId()).build();
        return mergeAndSave(portfolioSave, portfolio);
    }

    @Override
    @Transactional
    public PortfolioSummary update(PortfolioSave portfolioDto) {

        // Get portfolio id
        Long portfolioId = portfolioDto.getId();

        // Check if the portfolio to update exists
        Portfolio portfolio = portfolioAccessService.findById(portfolioId).orElseThrow(
                () -> new EntityNotFoundException(Portfolio.class, portfolioId)
        );
        // Check if a portfolio with the same name already exists
        checkIfPortfolioNameExists(portfolioDto.getName());

        return mergeAndSave(portfolioDto, portfolio);
    }

    private void checkIfPortfolioNameExists(String name) {
        if (portfolioAccessService.existsByName(name)) {
            throw new UnableToSaveEntitiesException("Portfolio with name: " + name + " already exists.");
        }
    }

    private PortfolioSummary mergeAndSave(PortfolioSave dto, Portfolio portfolio) {
        portfolioMapper.mergePortfolioSaveToPortfolio(dto, portfolio);
        Portfolio savedPortfolio = portfolioAccessService.save(portfolio);
        return portfolioMapper.portfolioToPortfolioSummary(savedPortfolio);
    }
}
