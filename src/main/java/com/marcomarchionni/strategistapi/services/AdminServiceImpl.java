package com.marcomarchionni.strategistapi.services;

import com.marcomarchionni.strategistapi.domain.User;
import com.marcomarchionni.strategistapi.errorhandling.exceptions.EntityNotFoundException;
import com.marcomarchionni.strategistapi.portfolios.repo.PortfolioRepository;
import com.marcomarchionni.strategistapi.repositories.*;
import com.marcomarchionni.strategistapi.strategies.repo.StrategyRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

  private final UserRepository userRepository;
  private final FlexStatementRepository flexStatementRepository;
  private final PortfolioRepository portfolioRepository;
  private final StrategyRepository strategyRepository;
  private final PositionRepository positionRepository;
  private final TradeRepository tradeRepository;
  private final DividendRepository dividendRepository;

  @Override
  @Transactional
  public void deleteUserAndUserData(String email) {
    User user =
        userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException(email));
    String accountId = user.getAccountId();
    positionRepository.deleteByAccountId(accountId);
    tradeRepository.deleteByAccountId(accountId);
    dividendRepository.deleteByAccountId(accountId);
    strategyRepository.deleteByAccountId(accountId);
    portfolioRepository.deleteByAccountId(accountId);
    flexStatementRepository.deleteByAccountId(accountId);
    userRepository.delete(user);
  }

  @Override
  public List<User> findAllUsers() {
    return userRepository.findAll();
  }
}
