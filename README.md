# Project Tracker Backend and Database

This folder contains the backend code for the Project Tracker application, built with Spring Boot, and instructions for database setup using MySQL.

## Prerequisites
Before running the backend and database, ensure you have the following software installed on your system:
- Java Development Kit (JDK) and IntelliJ IDEA (for Spring Boot development)
- MySQL Server and MySQL Workbench (for database management)
- Apache Tomcat or another servlet container (for running the Spring Boot backend)

## Getting Started

1. **Clone the Repository:**
   - Clone the GitHub repository using the following command:

     ```
     git clone https://github.com/mishabcp/backend-Project-Tracker.git
     ```

2. **Set Up Backend (IntelliJ IDEA):**
   - Open IntelliJ IDEA.
   - Navigate to the backend folder (`foldername-backend-Project-Tracker`).
   - Open this folder as a project in IntelliJ IDEA.

3. **Database Setup:**
   - Install MySQL Server if not already installed. Download and install it from the [official MySQL website](https://dev.mysql.com/downloads/mysql/).
   - Open MySQL Workbench and connect to your MySQL Server using the username and password as `root`.
   - Create a new schema named `fullstackprojectdb`.

4. **Run the Backend:**
   - Start the backend server in IntelliJ IDEA.
   - Tables will be automatically generated in the MySQL database based on entity classes in the Spring Boot application.

5. **Accessing the Application:**
   - Ensure that both the backend server and frontend development server are running to access the full functionality of the Project Tracker application.


