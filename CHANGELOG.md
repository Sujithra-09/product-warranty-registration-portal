# Changelog

All notable changes to the Product Warranty Registration Portal are documented here.

## [1.0.0] - 2026-09-19

### Added
- User login functionality
- Admin login functionality
- Role-based access for Admin and User
- Product warranty registration
- Warranty record creation
- Warranty record viewing
- Warranty record update
- Warranty record deletion
- Warranty expiry date calculation
- Warranty status calculation
- Input validation
- BCrypt password hashing
- Database password through environment variable
- CORS configuration
- Health check endpoint
- Admin dashboard
- GitHub Actions CI workflow
- Unit testing support
- README documentation

### Security
- BCrypt used for password hashing
- Admin-only pages protected using role-based session checks
- Database password removed from application source configuration

### Database
- MySQL database integration
- Spring Data JPA repository
- Warranty registration data persistence

### CI
- GitHub Actions configured for build and test
- JDK 17 configured for CI