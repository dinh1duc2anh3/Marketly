# Marketly: An online ecommerce website

A desktop e-commerce software built with Spring Boot for selling products.

## 📋 Project Overview

Marketly is designed to operate 24/7 with high performance and reliability:
- Supports up to 1,000 concurrent customers
- Maximum 1-hour recovery time after incidents
- Response time: 2 seconds (normal) / 5 seconds (peak hours)

## 🛠️ Technology Stack

- **Backend**: Spring Boot
- **Frontend**: HTML + CSS
- **Architecture**: 3-Layer Architecture
  - Presentation Layer (Controllers)
  - Business Layer (Services)
  - Data Layer (Repositories)
- **Payment Gateway**: VNPay Sandbox
- **Database**: Postgresql

## 🏗️ Architecture

```
┌─────────────────────┐
│   Presentation      │  ← Controllers, HTML/CSS
├─────────────────────┤
│   Business Logic    │  ← Services, Business Rules
├─────────────────────┤
│   Data Access       │  ← Repositories, Data Models
└─────────────────────┘
```

## ✨ Features

### For Product Managers
- **Product Management**
  - Add/view/edit/delete products
  - Add one product at a time
  - Delete up to 10 products simultaneously
  - Daily limit: max 30 deletions/updates, unlimited additions
  - Price updates up to 2 times per day (30%-150% of product value)

- **Order Management**
  - Review pending orders (30 per page)
  - Approve/reject orders
  - Automatic stock validation

### For Customers
- **Product Browsing**
  - View 20 random products per page
  - Search by product attributes
  - Sort by price
  - View detailed product information

- **Shopping Cart**
  - Add products with quantities
  - Modify cart contents
  - Single cart per session
  - Inventory validation

- **Order Placement**
  - No login required
  - Delivery information setup
  - Payment via VNPay integration
  - Rush delivery option (Hanoi inner city only)

- **Delivery Options**
  - Standard delivery
  - Rush delivery (2-hour timeframe for eligible products)
  - Free shipping for orders > 100,000 VND (up to 25,000 VND)

### For Administrators
- **User Management**
  - Create/view/update/delete users
  - Password reset functionality
  - Block/unblock users
  - Role assignment (multiple roles per user)
  - Email notifications for administrative actions

## 💰 Pricing & Delivery

### Shipping Fees
- **Tax-free shipping**
- **Free shipping**: Orders > 100,000 VND (max 25,000 VND discount)
- **Weight-based calculation** (heaviest item determines base rate)

#### Standard Delivery Rates
- **Hanoi/Ho Chi Minh City**: 22,000 VND (first 3kg)
- **Other provinces**: 30,000 VND (first 0.5kg)
- **Additional weight**: 2,500 VND per 0.5kg

#### Rush Delivery
- **Additional fee**: 10,000 VND per item
- **Availability**: Hanoi inner city districts only
- **Delivery time**: Within 2 hours

## 🚀 Getting Started

### Prerequisites
- Java 11 or higher
- Maven 3.6+
- Postgresql

### Installation

1. **Clone the repository**
   ```bash
   git clone [your-repository-url]
   cd aims-project
   ```

2. **Configure application properties**
   ```bash
   # Copy and modify application.properties
   cp src/main/resources/application.properties.example src/main/resources/application.properties
   ```

3. **Database setup**
   ```bash
   # Create database and run migrations
   ```

4. **VNPay Configuration**
   - Set up VNPay Sandbox credentials in `application.properties`
   - Configure payment gateway endpoints

5. **Build and run**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

6. **Access the application**
   - Open browser and navigate to `http://localhost:8080`

## 🔧 Configuration

### VNPay Integration
```properties
# VNPay Sandbox Configuration
vnpay.api.url=https://sandbox.vnpayment.vn/paymentv2/vpcpay.html
vnpay.return.url=http://localhost:8080/payment/return
vnpay.tmn.code=
vnpay.secret.key=
```

### Application Settings
```properties
# Server Configuration
server.port=8080

# Database Configuration
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=

# Email Configuration (for notifications)
spring.mail.host=
spring.mail.port=
```

## 📁 Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/aims/
│   │       ├── controller/     # Presentation Layer
│   │       ├── service/        # Business Logic Layer
│   │       ├── repository/     # Data Access Layer
│   │       ├── model/          # Entity Classes
│   │       ├── dto/            # Data Transfer Objects
│   │       └── config/         # Configuration Classes
│   ├── resources/
│   │   ├── static/            # CSS, JS, Images
│   │   ├── templates/         # HTML Templates
│   │   └── application.properties
└── test/                      # Unit and Integration Tests
```

## 🧪 Testing

### VNPay Sandbox
- **Demo**: https://sandbox.vnpayment.vn/apis/vnpay-demo/
- **Payment API**: https://sandbox.vnpayment.vn/apis/docs/thanh-toan-pay/pay.html
- **Query/Refund API**: https://sandbox.vnpayment.vn/apis/docs/truy-van-hoan-tien/querydr&refund.html

### Test Cards
[Add VNPay sandbox test card information]

## 📊 Business Rules

### Product Management
- Maximum 30 products deleted/updated per day per manager
- Price range: 30%-150% of product value
- Maximum 2 price updates per day
- All prices exclude 10% VAT

### Inventory Management
- Real-time stock validation
- Automatic inventory checks during order placement
- Stock alerts for insufficient inventory

### Order Processing
- Pending orders reviewed by product managers
- Automatic order rejection for insufficient stock
- Email notifications for order status changes

## 🔐 Security Features

- Role-based access control
- Input validation and sanitization
- Secure payment processing through VNPay
- Operation history logging
- Administrative action notifications

## 📈 Performance Specifications

- **Concurrent Users**: Up to 1,000
- **Uptime**: 300 hours continuous operation
- **Recovery Time**: Maximum 1 hour
- **Response Time**: 2s normal, 5s peak

---
