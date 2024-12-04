
![Logo](https://github.com/sasmithx/Green-Shadow/blob/main/readme-source/SpringBoot.png)

# Green Shadow Spring Boot Application

This is a Spring Boot-based backend system. It provides RESTful APIs for managing crop , equipment , field , staff , user , vehicle , monitoring log and transactions. The project uses Spring Boot, JPA, Hibernate,Spring Security, and MySQL for database connectivity.


## Features

- Equipment Management
- Field Management
- Staff Management
- User Management
- Vehicle Management
- Monitoring Log Management
- Role-Based Access Control
- Token Generation
- Token Validation
- Token Refresh
- Spring Security Integration
- Custom Authentication Provider
- Secure Password Storage
- Profile Management
- Login Attempt Auditing
- Activity Logging
- Protected Endpoints
- Transaction Processing
- Exception Handling and Validation using Hibernate Validator
- JSON Response formatting
- Logs with logback


## Technologies

- **Java Vesion:** JDK 21
- **Backend Framework:** Spring Boot (v3.4.0)
- **Database:** MySQL
- **ORM:** JPA, Hibernate
- **Server:** Apache Tomcat
- **Validation:** Hibernate Validator
- **Logger:** Logback


## Architecture Overview
- **Entities:** Representations for Crop , Email , Equipment , Field , Staff , User , Vehicle and Monitoring Log
- **Data Transfer Objects (DTOs):** Includes CropDTO, EmailDTO, EquipmentDTO , FieldDTO , StaffDTO , UserDTO , VehicleDTO and MoniterLogDTO
- **Repositories:** Interfaces for database operations.
- **Services:** Business logic for manage.
- **Controllers:** API endpoints for handle Requests.
- **Utilities:** Helper classes for tasks.
- **Exceptions:** Custom error handling mechanisms for specific scenarios
- **Configuration:** Application setup classes like application.properties,application-dev.properties

## Validation
Data validation is enforced through Hibernate Validator annotations within the DTO classes, ensuring both data integrity and accuracy.

## Logging
Logging is set up with Logback, capturing logs both in the console and in a dedicated file.

## Custom Exceptions
Custom exceptions are designed to address specific error situations, delivering clear and informative error messages to the client.

## Setup and Configuration

**Prerequisites**

- **JDK 21**
- **MySQL server**
- **Maven**


## Clone the repository:

```bash
  https://github.com/sasmithx/Green-Shadow.git
```
## Database Configuration

<img src="https://github.com/sasmithx/Green-Shadow/blob/main/readme-source/db.png" width="700px" height="auto">

## API Documentation

To view this project API Documentation

Refer to the [ Postman API Documentation](https://documenter.getpostman.com/view/35385442/2sAYBaApai) for detailed API endpoints and usage instructions.


## License

This project is licensed under the MIT License - see the [ MIT License](https://github.com/sasmithx/Green-Shadow?tab=MIT-1-ov-file#) file for details.

##
<div align="center">

<br>
<div align="center">
<p>
    <img src="https://img.shields.io/badge/Git-black?style=for-the-badge&logo=git&logoColor=F05032" />
    <img src="https://img.shields.io/badge/GitHub-black?style=for-the-badge&logo=github&logoColor=white" />
    <img src="https://img.shields.io/badge/Spring_Boot-000000?style=for-the-badge&logo=spring-boot&logoColor=green" />
    <img src="https://img.shields.io/badge/Spring_Security-000000?style=for-the-badge&logo=Spring-Security&logoColor=green" />
    <img src="https://img.shields.io/badge/MySQL-000000?style=for-the-badge&logo=mysql&logoColor=005C84" />
    <img src="https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=purple" />
    <img src="https://img.shields.io/badge/Ubuntu-black?style=for-the-badge&logo=ubuntu&logoColor=orange" />
</p>
</div>

</div> <br>
<p align="center">
  &copy; 2024 Sasmith Manawadu
</p>
