# 🛒 E-Commerce Backend System

A full-featured **Spring Boot–based E-Commerce Backend** built with clean architecture, security best practices, and real-world workflows. This project covers product management, cart & order processing, authentication with JWT, email notifications, password recovery, and more.

---

## 📌 Project Overview

This backend system is designed to support a modern e-commerce application with **Admin** and **User** roles. It provides secure APIs for managing products, carts, orders, reviews, authentication, and account recovery.

The system follows **RESTful principles**, uses **JWT-based authentication**, and is structured for scalability and maintainability.

---

## 🧱 Tech Stack

### 🔧 Backend
- **Java 17**
- **Spring Boot**
- **Spring Web (REST APIs)**
- **Spring Data JPA (Hibernate)**
- **Spring Security**
- **JWT (JSON Web Tokens)**
- **Spring Validation**

### 🗄 Database
- **PostgreSQL**
- **Docker & Docker Compose**
- **pgAdmin** (for DB inspection)

### 🔐 Security
- JWT Authentication & Authorization
- BCrypt Password Encoding
- Role-based Access Control (`ROLE_USER`, `ROLE_ADMIN`)

### ✉️ Communication
- **Spring Mail**
- **Brevo (SMTP provider)** for transactional emails

### 🧰 Utilities & Tools
- Lombok
- Maven
- Postman (API testing)
- Logging with SLF4J

---

## 👥 User Roles

### 👤 User
- View products
- Add products to cart
- Update / remove cart items
- Place orders (Buy Now / Cart checkout)
- Write product reviews
- Track order history
- Update password
- Forgot & reset password via email

### 🛠 Admin
- Add / update / delete products
- Manage product stock
- View all products
- (Planned) Manage orders & users

---

## 📦 Core Modules

### 1️⃣ Authentication & Authorization
- User Registration & Login
- JWT token generation & validation
- Stateless session management
- Role-based endpoint access

**Key Components:**
- `JwtUtility`
- `JwtAuthenticationFilter`
- `SecurityConfig`
- `CustomUserDetailsService`

---

### 2️⃣ Product Module

#### Admin Product Management
- Add product
- Update product details
- Delete product
- Update stock quantity

#### User Product Access
- View all products
- View product by ID
- Search by category

---

### 3️⃣ Cart Module

- Create cart per user
- Add product to cart
- Update cart item quantity
- Remove item from cart
- Clear cart
- View cart with total price

**Entities:**
- `Cart`
- `CartItems`

---

### 4️⃣ Order Module

- Buy Now order flow
- Place order from cart
- Order status lifecycle (`PENDING`, `PAID`, `CANCELLED`, etc.)
- Stock deduction on order placement
- View orders by user
- Cancel order

**Entities:**
- `Orders`
- `OrderItems`

---

### 5️⃣ Review Module

- Add product reviews
- View reviews by product
- Delete reviews (linked to product)

Uses proper **JPA relationships** (`ManyToOne` with Product).

---

### 6️⃣ Email Notification Module

- Order confirmation email
- Password reset email
- SMTP configured using **Brevo**
- Supports formatted transactional emails

---

### 7️⃣ Password Management Module

#### Update Password
- Requires old password

#### Forgot Password
- Generates secure reset token
- Sends reset link via email

#### Reset Password
- Validates token & expiry
- Updates password securely

**Entity:**
- `PasswordResetToken`

---

## 🔐 Security Highlights

- BCrypt password hashing
- JWT-based stateless authentication
- Role-based API protection
- Token expiration handling
- Secure password reset workflow

---

## 🐳 Docker Setup

The application uses Docker for database setup.

### Services
- **PostgreSQL 15**
- **pgAdmin 4**

### Volume Mapping
Ensures database persistence across container restarts.

---

## 📂 Project Structure (High Level)

```
com.ecommerce.ecommerce_backend
│
├── config          # Security & JWT config
├── controller      # REST Controllers
├── service         # Business logic
├── repository      # JPA Repositories
├── models          # Entities
├── dto             # Request/Response DTOs
├── enums           # Enums (Role, OrderStatus)
└── utils           # Utility classes
```

---

## 🚀 Future Enhancements

- Global Exception Handling
- Admin Order Management APIs
- Pagination & Sorting
- Refresh Token mechanism
- API Rate Limiting
- Swagger / OpenAPI documentation
- Payment Gateway integration
- Order tracking system

---

## ✅ Status

✔ Core backend features implemented
✔ Secure authentication & authorization
✔ Production-style architecture
✔ Ready for frontend integration

---

## 🙌 Author

Developed as a **learning-focused, real-world backend project** to deeply understand Spring Boot, Security, JPA, and system design concepts.

---

✨ *This project represents a solid foundation for a production-grade e-commerce system.*

