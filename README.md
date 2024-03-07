#Vacation Request Module
This is a REST API module that provides functionality for managing vacation requests for employees in a corporation. The module is designed to streamline the vacation request and approval process, improve transparency, and ensure compliance.

##Goals
The goal of this project is to develop a REST API vacation module that lets users fill in the dates for their vacation days, taking into consideration the working days of the week as well as the national holidays. Each user should start with 25 available vacation days per year. The module exposes endpoints for creating and modifying vacation requests, getting a list of existing vacation requests, getting a list of national holidays, and getting the number of available vacation days for a given year.

In addition to the basic functionality, the module provides minimal test coverage for both unit and integration tests.

Authentication
This module implements JWT authentication for user and admin roles.

##Requirements
The module is written in Java using the SpringBoot framework, and data must be persistent even if the API receives successive restarts. The module should compile and expose the basic functionalities of requesting a vacation and checking the vacation dates for an employee mentioned above should be fully functional.

##Endpoints
The following endpoints are exposed by the VacationRequestController:

POST /api/v1/vacation-requests - creates a new vacation request for a specified employee
PUT /api/v1/update-request/{id} - modifies an existing vacation request
GET /api/v1/employees/{employeeId}/available-vacation-days - gets the number of available vacation days for a given employee and year
GET /api/v1/get-requests - gets a list of all existing vacation requests
GET /api/v1/holidays - gets a list of all national holidays
All endpoints require JWT authentication with either a user or admin role. Admin role is required for accessing the get-requests and holidays endpoints.
