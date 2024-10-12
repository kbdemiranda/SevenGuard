# SevenGuard

SevenGuard is a simple inventory management system designed to help businesses track their products and stock levels efficiently. It provides easy-to-use APIs for managing products, categories, and generating inventory reports. Built with Spring Boot and PostgreSQL, SevenGuard offers robust backend functionality with a clean and scalable architecture.

## Features

- **Product Management**: CRUD operations for managing products.
- **Category Management**: Organize products into categories for better management.
- **Stock Control**: Track stock levels for each product and get notified of low stock.
- **Inventory Reports**: Generate simple reports on product availability.
- **Database Migrations**: Seamless database versioning with Flyway.
- **Validation**: Input validation for product and category data.

## Technologies Used

- **Java 21**: Modern language features and long-term support.
- **Spring Boot 3**: Simplified backend development.
- **PostgreSQL**: Powerful and scalable relational database.
- **Flyway**: Database migrations and versioning.
- **Spring Data JPA**: ORM for easy interaction with the database.
- **Lombok**: Simplifies boilerplate code for entities and services.
- **Spring Validation**: For input validation across the application.

## Getting Started

### Prerequisites

- JDK 21
- Maven 3.8+
- PostgreSQL 13+ (or Docker setup)

### Installation

1. Clone the repository:
    - `git clone https://github.com/your-username/sevenguard.git`
    - `cd sevenguard`

2. Set up the database:
    - Ensure PostgreSQL is running.
    - Create a new database for the project:
        - `createdb sevenguard_db`

3. Configure your database connection in the `application.yml` file:
   ```
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/sevenguard_db
       username: your_db_user
       password: your_db_password
     jpa:
       hibernate:
         ddl-auto: update
   ```

4. Run the application:
    - `./mvnw spring-boot:run`

## API Endpoints

- **Products**:
    - `GET /api/products`: List all products.
    - `POST /api/products`: Create a new product.
    - `PUT /api/products/{id}`: Update a product by ID.
    - `DELETE /api/products/{id}`: Delete a product by ID.

- **Categories**:
    - `GET /api/categories`: List all categories.
    - `POST /api/categories`: Create a new category.
    - `PUT /api/categories/{id}`: Update a category by ID.
    - `DELETE /api/categories/{id}`: Delete a category by ID.

- **Inventory Reports**:
    - `GET /api/inventory/report`: Generate a stock availability report.

**Database Migrations**

SevenGuard uses Flyway for database versioning. To run migrations:
- `./mvnw flyway:migrate`

**Tests**

To run the tests:
- `./mvnw test`

**License**

This project is licensed under the MIT License - see the LICENSE file for details.