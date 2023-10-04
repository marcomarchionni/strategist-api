# Portfolio Strategist API

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## Overview

**Portfolio Strategist** is a web application designed to empower stock and option traders by providing tools to organize, view, and query their trading data from an Interactive Brokers account. By appending custom tags to their trading data, users can query data and analyze performance at a refined level, enabling them to make more informed decisions.

**Portfolio Strategist API**, serving as the backend of the application, is accountable for:

- Fetching user data directly from Interactive Brokers accounts.

- Exposing a rich API to facilitate data querying for the frontend.

## Tech Stack

The Portfolio Strategist API leverages the following technology stack:

- **Language**: Java (Version: 17)

- **Framework**: SpringBoot (Version: 3.1.3)

- **Database Management**:
  - Spring JPA & Hibernate: Managing database access and mapping.
  - MySql: Engaged for storage and organization of user data.
  - H2: Utilized for data storage during testing phases.

- **Additional Features**:
    - Data Validation
    - Advanced Error Handling
    - AOP Logging

### Current Status

The application is under active development and is not yet primed for production.

### Upcoming Features

- **Security**: Implementation of Authentication and Authorization utilizing Spring Security and JWT.

- **Advanced Analytics**: Enhanced strategy and performance analysis.

## License

This project is licensed under the [MIT License](LICENSE.md).

## Acknowledgements

Many thanks to Matteo for the guidance and support during the initial phases of this project.



