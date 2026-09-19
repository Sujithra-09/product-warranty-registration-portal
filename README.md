# Product Warranty Registration Portal

A web-based application for registering and managing product warranty information using Java Spring Boot and MySQL.

## Project Overview

The Product Warranty Registration Portal helps users register their product warranty details and allows administrators to manage warranty records.

The system provides CRUD operations, role-based access, input validation, warranty expiry calculation, and warranty status tracking.

## Features

### User
- User login
- Product warranty registration
- Input validation
- Registration confirmation

### Admin
- Admin login
- Admin dashboard
- View warranty records
- Create warranty records
- Update warranty records
- Delete warranty records
- View warranty expiry date
- View warranty status

## Warranty Status

The system calculates the warranty expiry date as one year from the purchase date.

- Active – Warranty is currently valid.
- Expiring Soon – Warranty expires within 30 days.
- Expired – Warranty expiry date has passed.

## Technology Stack

- Java 17
- Spring Boot
- Spring MVC
- Spring Data JPA
- MySQL
- Thymeleaf
- Gradle
- BCrypt
- Git
- GitHub Actions

## User Roles

### Admin

Admin users can:
- Access the Admin Dashboard
- View warranty records
- Update warranty records
- Delete warranty records
- Manage warranty information

### User

Users can:
- Login
- Register product warranty details

## Database

Database name:

`warranty_portal`

The application uses MySQL and Spring Data JPA for database operations.

Database credentials are handled using environment variables.

## Environment Variable

The database password is not stored directly in the source code.

Example:

`spring.datasource.password=${DB_PASSWORD}`

Before running the application, configure the `DB_PASSWORD` environment variable.

## How to Run

Build the project:

`gradlew.bat build`

Run the application:

`gradlew.bat bootRun`

Application URL:

`http://localhost:8081`

## Health Check

Endpoint:

`GET /health`

URL:

`http://localhost:8081/health`

Expected response:

`OK`

## Testing

Run unit tests:

`gradlew.bat test`

Build and test:

`gradlew.bat clean build`

## GitHub Actions

The project uses GitHub Actions for continuous integration.

Workflow file:

`.github/workflows/ci.yml`

The workflow:

1. Checks out the source code.
2. Sets up JDK 17.
3. Builds the project.
4. Runs the tests.

The workflow runs on pushes and pull requests to the `main` branch.

## Security

- Passwords are processed using BCrypt.
- Database credentials are handled using environment variables.
- Admin pages are protected using role-based session checks.
- User accounts cannot access Admin-only pages.
- Input validation is implemented for required warranty fields.
- CORS configuration is included.

## Validation

The following fields are required:

- Product Name
- Product ID
- Customer Name
- Purchase Date

## Project Structure

`src/main/java/com/warranty/portal`

Main classes:

- LoginController.java
- WarrantyPortalApplication.java
- WarrantyRegistration.java
- WarrantyRegistrationRepository.java
- WarrantyRegistrationRequest.java
- WarrantyService.java
- CorsConfig.java

## API

### Health Check

`GET /health`

Response:

`OK`

## Future Improvements

- Cloud deployment
- JWT-based authentication
- Production database hosting
- Email warranty notifications
- Advanced admin analytics

## Author

Product Warranty Registration Portal  
Java Spring Boot + MySQL Project