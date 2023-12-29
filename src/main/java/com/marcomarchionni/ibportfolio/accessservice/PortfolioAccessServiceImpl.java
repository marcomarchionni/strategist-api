package com.marcomarchionni.ibportfolio.accessservice;

import com.marcomarchionni.ibportfolio.domain.Portfolio;
import com.marcomarchionni.ibportfolio.errorhandling.exceptions.InvalidUserDataException;
import com.marcomarchionni.ibportfolio.repositories.PortfolioRepository;
import com.marcomarchionni.ibportfolio.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PortfolioAccessServiceImpl implements PortfolioAccessService {
    private final PortfolioRepository portfolioRepository;
    private final UserService userService;

    @Override
    public List<Portfolio> findAll() {
        String accountId = userService.getUserAccountId();
        return portfolioRepository.findAllByAccountId(accountId);
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
        if (portfolio.getAccountId() != null && !portfolio.getAccountId().equals(userAccountId)) {
            throw new InvalidUserDataException("Portfolio with id " + portfolio.getId() + " does not belong to user");
        }
        portfolio.setAccountId(userAccountId);
        return portfolioRepository.save(portfolio);
    }

    @Override
    public void delete(Portfolio portfolio) {
        String accountId = userService.getUserAccountId();
        if (!portfolio.getAccountId().equals(accountId)) {
            throw new InvalidUserDataException("Portfolio with id " + portfolio.getId() + " does not belong to user");
        }
        portfolioRepository.deleteById(portfolio.getId());
    }
}

