IT Asset Management System
Overview

The IT Asset Management System is a web-based application developed for CMPDI to efficiently manage and monitor organizational IT assets across multiple locations.

The system centralizes asset information, employee allocation, warranty monitoring, QR code generation, survey lot creation, and dashboard analytics in a single platform.

Features
Dashboard Analytics
Real-time asset statistics
Asset category distribution charts
Asset status monitoring
Warranty expiry monitoring
Recent asset activity
Multi-Location Management
Location-based access control
Location selection after admin login
Automatic filtering of assets by selected location
Current location indicator across pages
Asset Management
Register and manage IT assets
View asset details
Update asset information
Delete assets
Search assets
Product Management
Add and manage products
Upload product documents and images
Track purchase dates and warranty information
Employee Management
Maintain employee records
Assign assets to employees
Track asset ownership
Warranty Monitoring
Automatically detect expired warranties
Dedicated warranty report page
Dashboard warranty indicators
QR Code Management
Generate QR labels for assets
Print asset QR labels
Quick asset identification
Survey Lot Management
Auto-generate lot numbers
Group eligible assets
Bulk assign assets into lots
Support survey and disposal processes
Reports
CSV export
PDF export
Asset reports
Survey reports
Technologies Used
Backend
Java 17
Spring Boot
Spring MVC
Spring Data JPA
Frontend
HTML5
CSS3
Thymeleaf
JavaScript
Database
PostgreSQL
Build Tool
Maven
Project Structure
src/
 ├── main/
 │   ├── java/
 │   │   ├── controller/
 │   │   ├── domain/
 │   │   ├── repository/
 │   │   └── service/
 │   │
 │   └── resources/
 │       ├── static/
 │       └── templates/
 │
 └── test/
Major Modules
Admin Dashboard
Employee Management
Location Management
Product Management
Asset Inventory
Warranty Monitoring
QR Label Generation
Survey Lot Management
Reports & Analytics
