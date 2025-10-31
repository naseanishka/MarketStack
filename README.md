🛍️ MarketStack — Product Module (E-Commerce Framework)
📘 Overview

MarketStack is a modular Spring Boot–based e-commerce backend framework.
This Product Module handles product management, including category association and database persistence.
It demonstrates clean architecture, manual JDBC control, and Spring Boot configuration — ideal for understanding backend fundamentals before integrating JPA or ORM tools.

⚙️ Tech Stack

Language: Java 17+

Framework: Spring Boot

Build Tool: Maven

Database: MySQL

Data Access: JDBC (DriverManager, PreparedStatement, ResultSet)

IDE: IntelliJ IDEA 2024.1.7 (macOS)

📂 Key Project Structure
src/
 └── main/
      ├── java/com/ecommerce/productmodule/
      │     ├── dao/ProductDAO.java         # Core DAO handling product operations
      │     └── pojo/ProductDTO.java        # Data Transfer Object for product data
      ├── resources/
      │     └── application.properties      # Database configuration and logging
 ├── pom.xml                                # Maven build and dependencies

🧱 Database Schema

The module expects three main tables:
category, product, and a join table product_category.

-- Create database
CREATE DATABASE IF NOT EXISTS ecomm;
USE ecomm;

-- Category table
CREATE TABLE IF NOT EXISTS category (
  category_id INT PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

-- Product table
CREATE TABLE IF NOT EXISTS product (
  product_id INT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  price INT NOT NULL,
  description TEXT,
  image_url VARCHAR(512)
);

-- Join table (many-to-many relationship)
CREATE TABLE IF NOT EXISTS product_category (
  product_id INT NOT NULL,
  category_id INT NOT NULL,
  PRIMARY KEY (product_id, category_id),
  FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE,
  FOREIGN KEY (category_id) REFERENCES category(category_id) ON DELETE CASCADE
);

⚙️ Configuration

Set up your MySQL credentials in src/main/resources/application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/ecomm
spring.datasource.username=root
spring.datasource.password=CHANGE_ME
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Optional logging settings
spring.jpa.show-sql=false
logging.level.root=INFO


🔐 Important: Replace CHANGE_ME with your actual MySQL password.

🚀 Build & Run
1️⃣ Set Up Database

Start MySQL and create the ecomm database using the SQL above.

Update your DB credentials in application.properties.

2️⃣ Build the Project
mvn clean package

3️⃣ Run the Application
mvn spring-boot:run


or

java -jar target/<artifactId>-<version>.jar

🧩 ProductDAO Overview
🧠 Responsibilities

Ensures the category exists before inserting a product

Validates unique product IDs

Inserts product data, then associates it with categories

⚠️ Current Limitations

No Transaction Handling: Inserts into product and product_category are not atomic.

Manual Resource Management: Connections are not auto-closed (no try-with-resources).

Uses Raw JDBC: Instead of Spring’s JdbcTemplate or JPA.

🔧 Suggested Improvements
Area	Current	Recommended
Connection Management	Manual via DriverManager	Use DataSource / HikariCP
DB Operations	Raw JDBC	Switch to JdbcTemplate or Spring Data JPA
Transaction Safety	None	Add @Transactional
Logging	System.out.println	Use SLF4J or Spring logger
Validation	Basic	Add DTO validation (e.g., Hibernate Validator)
Schema Consistency	Mixed casing	Use consistent lowercase table names
🧪 Testing

Use H2 in-memory DB or Testcontainers (MySQL) for unit tests.

Add integration tests to verify:

Referential integrity

Transaction rollback

DAO functionality

🔐 Security Best Practices

Never hardcode credentials — use environment variables or .env files.

Externalize configurations using Spring Boot profiles.

Keep passwords out of version control.

For production, integrate with secret managers (e.g., AWS Secrets Manager, Vault).

📈 Future Enhancements

 Add transaction management

 Implement CRUD API endpoints with Spring MVC

 Introduce JWT-based authentication

 Containerize with Docker

 Integrate with JPA for production-ready ORM

🧑‍💻 Author

Anishka Nase
Backend Developer | Java & Spring Enthusiast
💡 Exploring clean architecture and modular backend design.

