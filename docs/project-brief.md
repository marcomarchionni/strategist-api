# Project Brief – Trading Portfolio Monitor (Backend)

## Overview

A monitoring and analysis tool for stock and option portfolios.  
The backend retrieves trading data from the Interactive Brokers API, stores it in MySQL, and exposes a REST API for the frontend to query.

---

## Tech Stack

- Java + Spring Boot
- Spring Data JPA with MySQL
- JWT-based authentication
- Integration with Interactive Brokers API
- REST API exposed at `/api/v1/*`
- JUnit + Mockito for testing

---

## Responsibilities

1. **Data Retrieval**

   - Fetch trades, positions, dividends from Interactive Brokers.
   - Store and update in MySQL.

2. **Entity Management**

   - CRUD for trades, positions, dividends.
   - CRUD for strategies and portfolios.

3. **Tagging System**

   - Strategies: tags applied to trades, positions, dividends.
   - Portfolios: tags applied to strategies.

4. **Performance Calculation**
   - Aggregate stats for strategies and portfolios.
   - Provide breakdown for analysis.

---

## Security

- JWT authentication.
- Role-based access control (future enhancement).

---

## API Endpoints

- `/portfolios` – manage portfolios.
- `/strategies` – manage strategies.
- `/trades` – manage and query trades.
- `/positions` – manage and query positions.
- `/dividends` – manage and query dividends.

---

## Next Steps

- Examine and improve current code
- Adapt apis to AG Grid needs
