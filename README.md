# 🛒 Microservices E-Commerce System

This repository contains a **microservices-based E-Commerce system** built with **Spring Boot (Java)** and **Kotlin** (for DTOs and models).  
The architecture leverages **event-driven communication (Kafka)** and **synchronous REST calls (Feign)**, with **OAuth2 security** and **rollback mechanisms** for reliability.  
Deployment and local testing are simplified using **Docker Compose**.

---

## 📂 Microservices

The system is composed of the following services:

- **ms-customer** – Customer management service  
- **ms-eureka-server** – Service registry (Netflix Eureka)  
- **ms-identity** – Authentication & authorization with OAuth2 + JWT (asymmetric encryption)  
- **ms-order** – Order management (order lifecycle: pending → completed/failed)  
- **ms-payment** – Payment processing and integration with order service  
- **ms-product** – Product catalog and stock management  

---

## 🔌 Ports & Dashboards

| Service         | Host Port | Main URL                                  | Swagger UI                              | OpenAPI JSON            |
|-----------------|-----------|--------------------------------------------|------------------------------------------|-------------------------|
| Eureka          | **8761**  | http://localhost:8761                      | –                                        | –                       |
| Kafka UI        | **8087**  | http://localhost:8087                      | –                                        | –                       |
| Identity        | **9000**  | http://localhost:9000                      | http://localhost:9000/swagger-ui.html    | http://localhost:9000/v3/api-docs |
| Product         | **9001**  | http://localhost:9001                      | http://localhost:9001/swagger-ui.html    | http://localhost:9001/v3/api-docs |
| Customer        | **9002**  | http://localhost:9002                      | http://localhost:9002/swagger-ui.html    | http://localhost:9002/v3/api-docs |
| Order           | **9003**  | http://localhost:9003                      | http://localhost:9003/swagger-ui.html    | http://localhost:9003/v3/api-docs |
| Payment         | **9004**  | http://localhost:9004                      | http://localhost:9004/swagger-ui.html    | http://localhost:9004/v3/api-docs |

> **Note:** With springdoc `2.x`, `/swagger-ui.html` redirects to `/swagger-ui/index.html`. Both should work by default.

**Kafka Broker (host access):** `localhost:9092`  
Docker mapping: `9092:29092` with advertised listener `PLAINTEXT_HOST://localhost:9092`.

**PostgreSQL (host access):** `localhost:5432` (user: `postgres`, pass: `secret123`)

---

## 🔄 Workflow Overview

1. **Authentication** – User logs in through **ms-identity** and receives an OAuth2 JWT.  
2. **Order Creation** – An order is created in `PENDING` state via **ms-order**.  
3. **Stock Handling** – **ms-product** decrements product stock upon order creation.  
4. **Payment Processing** – **ms-payment** handles payment and confirms transaction.  
5. **Order Completion** – If payment succeeds, **ms-order** updates the status to `COMPLETED`.  
6. **Rollback Support** – In case of failure, compensating actions (rollback) are triggered (e.g., restoring stock, canceling order).  

✔ Communication happens through both:  
- **Asynchronous messaging** → Kafka (producer/consumer)  
- **Synchronous REST** → Feign Clients  

---

## 🛠️ Tech Stack

- **Languages:** Java 17, Kotlin (DTOs & Models)  
- **Frameworks:** Spring Boot 3.5.x, Spring Cloud 2025.x  
- **Security:** Spring Security 6.5.x, OAuth2 Resource Server, JWT (asymmetric keys)  
- **Database:** PostgreSQL  
- **Messaging:** Apache Kafka  
- **Service Discovery:** Netflix Eureka  
- **API Communication:** OpenFeign  
- **Documentation:** Springdoc OpenAPI (Swagger UI)  
- **Build Tool:** Maven  
- **Containerization:** Docker & Docker Compose  

---

## ⚙️ Running Locally

### Prerequisites
- [Docker](https://www.docker.com/) & [Docker Compose](https://docs.docker.com/compose/)  
- JDK 17+  
- Maven 3.9+  

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/rahib533/MS-Ecommerce.git
   cd MS-Ecommerce
   ```
2. Start the stack:
   ```bash
   docker-compose up --build
   ```
3. Open dashboards:
   - **Eureka:** http://localhost:8761  
   - **Kafka UI:** http://localhost:8087

---

## 🔐 Auth (OAuth2, JWT)

- Authorization Server: **ms-identity** (`localhost:9000`)  
- Resource Servers: **ms-product**, **ms-customer**, **ms-order**, **ms-payment**  
- Tokens: **JWT** with **asymmetric keys** (public key used by resource servers)  

> Configure audiences per service (e.g., `product-service`, `order-service`, etc.) as seen in environment variables.

---

## 🧪 Quick Test (after login)

```bash
# Example: call a protected endpoint with a Bearer token
curl -H "Authorization: Bearer <YOUR_JWT>" http://localhost:9003/api/orders
```

---

## 📜 Features

- ✅ Event-driven communication with Kafka  
- ✅ Synchronous REST integration with Feign Clients  
- ✅ OAuth2 authentication with asymmetric encryption  
- ✅ PostgreSQL persistence  
- ✅ Containerized setup (Docker Compose)  
- ✅ Rollback mechanism for failed transactions  
- ✅ API documentation with Swagger  

---

## 📖 License

This project is licensed under the MIT License.
