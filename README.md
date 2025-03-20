# Coupon Management System

A Spring Boot-based coupon management system that allows companies to create and manage coupons, and customers to purchase and use them.
The system includes JWT authentication and role-based access control.

## System Architecture

![System Architecture](src/main/resources/flowcharts/SystemArchitecture.png)

## Features

- **Authentication & Authorization**
  - JWT-based authentication
  - Role-based access (Admin, Company, Customer)
  - Secure token management
- **Admin Features**
  - Manage companies and customers
  - View system statistics
  - Full CRUD operations for companies and customers
- **Company Features**
  - Create and manage coupons
  - Update coupon details
  - View company statistics
  - Track coupon purchases
- **Customer Features**
  - Browse available coupons
  - Purchase coupons
  - View purchased coupons
  - Filter coupons by category and price

## Technical Stack

- **Backend**: Spring Boot 3.x
- **Database**: MySQL
- **Security**: JWT Authentication
- **API**: RESTful web services
- **Background Jobs**: Scheduled tasks for coupon expiration

## Getting Started

### Prerequisites

- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.x

### Installation

1. Clone the repository:
   git clone [https://github.com/MatanT93/CouponProjectServer]
2. Configure MySQL database in `application.properties`:
   spring.datasource.ur l=jdbc:mysql://local host:3306/CouponsDB
   spring.datasource.username=root
   spring.datasource.password=1234
3. Build the project:
   mvn clean install

## API Endpoints

### Authentication
- POST `/users/login` - Login for all user types
- GET `/users/logout` - Logout and invalidate token

### Admin Operations
- POST `/admin/add/company` - Add new company
- PUT `/admin/update/company/{id}` - Update company
- DELETE `/admin/delete/company/{id}` - Delete company
- GET `/admin/companies` - Get all companies
- GET `/admin/company/{id}` - Get specific company
- Similar endpoints for customer management

### Company Operations
- POST `/company/add/coupon` - Create new coupon
- PUT `/company/update/coupon` - Update coupon
- DELETE `/company/delete/coupon/{id}` - Delete coupon
- GET `/company/coupons` - Get company's coupons
- GET `/company/coupons/category` - Filter coupons by category
- GET `/company/coupons/maxprice` - Filter coupons by price

### Customer Operations
- POST `/customer/add/purchase/{couponId}` - Purchase coupon
- GET `/customer/owned/coupons` - Get purchased coupons
- GET `/customer/coupons` - Get all available coupons
- GET `/customer/category` - Filter coupons by category
- GET `/customer/maxprice` - Filter coupons by price

## Database Schema

The system uses the following main entities:
- Companies
- Customers
- Coupons
- Categories
- CustomerVSCoupon (Purchase records)

## Security

The system implements a comprehensive security model:
- JWT-based authentication
- Token validation for each request
- Role-based access control
- CORS configuration for frontend integration

## Background Jobs

- Daily job to remove expired coupons
- Automatic cleanup of expired tokens

## Error Handling

Custom exceptions for business logic:
- `CouponOutOfStockException`
- `NameAlreadyExistException`
- `ResourceNotFoundException`
- `WrongDetailsException`

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a new Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.