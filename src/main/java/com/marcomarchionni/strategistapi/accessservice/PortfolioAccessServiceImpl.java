package com.marcomarchionni.strategistapi.accessservice;

import com.marcomarchionni.strategistapi.domain.Portfolio;
import com.marcomarchionni.strategistapi.repositories.PortfolioRepository;
import com.marcomarchionni.strategistapi.services.UserService;
import com.marcomarchionni.strategistapi.validators.AccountIdValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PortfolioAccessServiceImpl implements PortfolioAccessService {
    private final PortfolioRepository portfolioRepository;
    private final UserService userService;
    private final AccountIdValidator<Portfolio> accountIdValidator;

    @Override
    public List<Portfolio> findAll() {
        String accountId = userService.getUserAccountId();
        return portfolioRepository.findAllByAccountId(accountId);
    }

    @Override
    public Page<Portfolio> findAll(Specification<Portfolio> spec, Pageable pageable) {
        return portfolioRepository.findAllBy(spec, pageable);
    }

    @Override
    public boolean existsByName(String name) {
        String accountId = userService.getUserAccountId();
        return portfolioRepository.existsByAccountIdAndName(accountId, name);
    }

    @Override
    public Optional<Portfolio> findById(Long id) {
        String accountId = userService.getUserAccountId();
        return portfolioRepository.findByIdAndAccountId(id, accountId);
    }

    @Override
    public Portfolio save(Portfolio portfolio) {
        String userAccountId = userService.getUserAccountId();
        accountIdValidator.hasValidAccountId(portfolio, userAccountId);
        return portfolioRepository.save(portfolio);
    }

    @Override
    public void delete(Portfolio portfolio) {
        String accountId = userService.getUserAccountId();
        accountIdValidator.hasValidAccountId(portfolio, accountId);
        portfolioRepository.deleteById(portfolio.getId());
    }

    @Override
    public int count() {
        String accountId = userService.getUserAccountId();
        return portfolioRepository.countByAccountId(accountId);
    }
}

