# Helpdesk System

## Description

The Helpdesk System is designed to optimize the management of technical support requests in telecommunications companies.

Customer service agents can register support tickets based on phone calls received from clients. Clients can then track the status of their requests and receive updates through REST APIs. The system stores all data in a relational database and provides reporting on average resolution times and technician workload.  
The system also features full **JWT authentication** and **role-based authorization**. It supports Customer Support and Technician user flows.

---

## Functional Requirements

1. The system supports the registration, assignment, and tracking of technical support requests for a telecom company's clients.
2. Ticket registration is performed by customer service agents upon receiving phone calls from clients.
3. For each ticket, the system records customer details, the phone line number with issues, and a description of the problem.
4. Tickets are categorized (e.g., billing, connectivity, telephony, connection speed).
5. The system automatically assigns tickets to appropriate engineers based on their specialization and current workload, aiming for balanced distribution.
6. Each engineer is qualified to handle specific ticket categories according to their expertise.
7. The admin team maps engineer specialties to ticket categories and assigns specialties to engineers in coordination with HR.
8. During ticket resolution, engineers record their actions, classified as:
   - Technical tasks
   - Customer communications
9. Each action includes a title, description, and execution date.
10. For customer communications, the system also records the call duration.
11. Once a ticket is resolved, the customer is notified via email.
12. The system generates reports for management, such as:
    - Average number of communications per ticket category
    - Number of tickets per month

---

## Development & Build

The system was developed using an iterative and incremental software development process.

This version is built using the [Quarkus](https://quarkus.io/) framework.  
It uses JPA 2 with [Hibernate](https://hibernate.org/orm/) as the JPA provider to access the relational database.  
The REST API is implemented using JAX-RS.

Automated tests are written using JUnit and RestAssured.  
Code coverage analysis (statement and branch coverage) is performed using JaCoCo.

> **Note:** The system currently does not provide a graphical or user-facing interface.  
Functionality is demonstrated through REST scenarios executed via automated tests or tools like Postman.

### Build Requirements

- **Java 21**
- **Maven 3.9.9** (or newer)
- **Docker** (for containerized deployments)

### Technologies Used

- **[Quarkus](https://quarkus.io/)** — Java framework tailored for Kubernetes and GraalVM
- **[MapStruct](https://mapstruct.org/)** — Java bean mapping framework for DTOs and entities
- **[Hibernate ORM](https://hibernate.org/orm/)** — JPA implementation for persistence
- **JWT** (via `quarkus-smallrye-jwt`) (Role-based access control** (`@RolesAllowed`), **RSA key pair for token signing**)
- **[H2 Database](https://www.h2database.com/)** — Lightweight in-memory database for development/testing
- **[JUnit 5](https://junit.org/junit5/)** — Modern Java testing framework
- **[RestAssured](https://rest-assured.io/)** — Java DSL for testing RESTful services
- **[Docker](https://www.docker.com/)** — Containerization of the application for portable environments
- **Maven Wrapper (`mvnw`)** — For consistent project builds across environments

#### Environment Setup

1. Set the `JAVA_HOME` environment variable to your JDK installation directory.
2. Add `apache-maven-3.9.9/bin` to your system `PATH`.
3. Set `MAVEN_HOME` to your Maven installation directory.

### Running the Application
You can run the application either locally or using Docker:

#### Option 1: Local Run

1. **Clean and Build the Project**

   ```bash
   ./mvnw clean install
   ```

2. **Start the Quarkus Dev Mode**

   ```bash
   ./mvnw quarkus:dev
   ```

   > Quarkus will hot-reload your changes instantly.

3. **Access the Application**

   [http://localhost:8080](http://localhost:8080)

---

#### Option 2: Dockerized Run

1. **Build the Docker Image**

   ```bash
   docker build -t helpdesk-system .
   ```

2. **Run the Container**

   ```bash
   docker run -p 8080:8080 helpdesk-system
   ```

3. **Access the Application**

   [http://localhost:8080](http://localhost:8080)

> The database is automatically initialized at startup using the `import.sql` file.

---
### Running Tests

```bash
./mvnw test
```

Unit and repository tests are executed using JUnit 5 and RestAssured for testing the REST endpoints. Coverage is enabled via **JaCoCo**.

### API Testing with Postman

You can test the REST endpoints using Postman by importing the following collection:

**Directory**: `postman/`  
**File**: `Helpdesk-System.postman_collection.json`

#### How to Use:

1. Open [Postman](https://www.postman.com/downloads/).
2. Go to `File` > `Import` or use the `Import` button.
3. Select the file: `postman/Helpdesk-System.postman_collection.json`.
4. Use the predefined requests to test the Helpdesk System API.

>  Tip: Make sure the application is running locally (default port: `http://localhost:8080`) before executing requests.


### Project Structure

```bash
src/
 ├── main/
 │   ├── java/com/example/helpdesk/         # Core source code
 │   └── resources/                          # Configuration files (application.properties)
 └── test/                                   # Unit & integration tests
postman/                                    # Postman collections
Dockerfile                                  # Docker build definition
```



#### Common Maven Commands

```bash
mvn clean               # Clean the target directory
mvn compile             # Compile the source code
mvn test-compile        # Compile the test code
mvn test                # Run JUnit tests
mvn site                # Generate the project site with documentation
```

---

## Authentication & Authorization

The system uses **JWT tokens** for secure login. Tokens are signed using **asymmetric RSA keys** and validated by the application.  
More on this: [Authentication - Authorization](docs/MD_Files/Authentication_Authorization.md)

---

## Documentation

Documentation is written in **Markdown**. UML diagrams are created using **UMLet**.

Under the `/docs` folder, you will find subfolders containing UML sources, images, and markdown files.

More information about the domain model can be found here:  
[Domain Model Diagram](docs/MD_Files/Domain_Model.md)

---

## Author

**Christos Bampoulis**  
GitHub: [@ChristosBaboulis](https://github.com/ChristosBaboulis)

---

## Acknowledgments

This project was developed as part of an academic assignment during my MSc studies, in the context of the course "Advanced Methods for Software Development".  
It demonstrates the design and implementation of a structured helpdesk ticketing workflow with automated assignment and analytics features.
