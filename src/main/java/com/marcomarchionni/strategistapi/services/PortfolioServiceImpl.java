package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.accessservice.PortfolioAccessService;
import com.marcomarchionni.strategistapi.domain.Portfolio;
import com.marcomarchionni.strategistapi.dtos.request.FindAllReq;
import com.marcomarchionni.strategistapi.dtos.request.PortfolioSave;
import com.marcomarchionni.strategistapi.dtos.response.ApiResponse;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioDetail;
import com.marcomarchionni.strategistapi.dtos.response.PortfolioSummary;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.UnableToDeleteEntitiesException;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.UnableToSaveEntitiesException;
import com.marcomarchionni.strategistapi.mappers.PortfolioMapper;
import com.marcomarchionni.strategistapi.repositories.PortfolioRepository;
import com.marcomarchionni.strategistapi.services.specifications.PagingUtil;
import com.marcomarchionni.strategistapi.services.specifications.SimplePortfolioSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioAccessService portfolioAccessService;
    private final UserService userService;
    private final PortfolioMapper portfolioMapper;
    private final PortfolioRepository portfolioRepository;
    private final SimplePortfolioSpecification portfolioSpecification;

    @Override
    public ApiResponse<PortfolioSummary> findAllWithCount(FindAllReq findReq) {
        String accountId = userService.getUserAccountId();
        Pageable pageable = PagingUtil.createPageable(findReq);

        // Use simple specification with filter parameters from FindAllReq
        Specification<Portfolio> spec = portfolioSpecification.buildSpecification(
                accountId,
                findReq.getName(),
                findReq.getDescription(),
                findReq.getCreatedAfter(),
                findReq.getCreatedBefore());

        // Fetch filtered results
        Page<Portfolio> portfolios = portfolioRepository.findAll(spec, pageable);

        // Get total count with filter
        long totalCount = portfolioRepository.count(spec);

        return ApiResponse.<PortfolioSummary>builder()
                .result(portfolios.stream().map(portfolioMapper::portfolioToPortfolioSummary).toList())
                .count(totalCount)
                .build();
    }

    @Override
    public PortfolioDetail findById(Long portfolioId) {
        Portfolio portfolio = portfolioAccessService.findById(portfolioId).orElseThrow(
                () -> new EntityNotFoundException(Portfolio.class, portfolioId));
        return portfolioMapper.toPortfolioDetailDto(portfolio);
    }

    @Override
    public ServiceType getServiceType() {
        return ServiceType.PORTFOLIO;
    }

    @Override
    public void deleteById(Long portfolioId) {
        Portfolio portfolioToDelete = portfolioAccessService.findById(portfolioId).orElseThrow(
                () -> new EntityNotFoundException(Portfolio.class, portfolioId));
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
        var portfolio = Portfolio.builder().accountId(userService.getUserAccountId()).createdAt(LocalDate.now())
                .build();
        return mergeAndSave(portfolioSave, portfolio);
    }

    @Override
    @Transactional
    public PortfolioSummary update(PortfolioSave portfolioDto) {

        // Get portfolio id
        Long portfolioId = portfolioDto.getId();

        // Check if the portfolio to update exists
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow(
                () -> new EntityNotFoundException(Portfolio.class, portfolioId));
        // Check if a portfolio with the same name already exists (excluding current
        // portfolio)
        checkIfPortfolioNameExists(portfolioDto.getName(), portfolioId);

        return mergeAndSave(portfolioDto, portfolio);
    }

    private void checkIfPortfolioNameExists(String name) {
        checkIfPortfolioNameExists(name, null);
    }

    private void checkIfPortfolioNameExists(String name, Long excludePortfolioId) {
        String accountId = userService.getUserAccountId();

        // Find portfolio with the same name
        Optional<Portfolio> existingPortfolio = portfolioRepository.findByAccountIdAndName(accountId, name);

        // If a portfolio with this name exists and it's not the one we're updating,
        // throw exception
        if (existingPortfolio.isPresent()
                && (excludePortfolioId == null || !existingPortfolio.get().getId().equals(excludePortfolioId))) {
            throw new UnableToSaveEntitiesException("Portfolio with name: " + name + " already exists.");
        }
    }

    private PortfolioSummary mergeAndSave(PortfolioSave dto, Portfolio portfolio) {
        portfolioMapper.mergePortfolioSaveToPortfolio(dto, portfolio);
        Portfolio savedPortfolio = portfolioRepository.save(portfolio);
        return portfolioMapper.portfolioToPortfolioSummary(savedPortfolio);
    }
}