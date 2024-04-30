# Ximple Library Application

Ximple Library is an online library system developed in Java 21 and Spring Boot that enables users to make bibliographical queries, place reservations, review books and retrieve feedback from readers. The application provides endpoints for creating users, managing books, placing reservations and generating reviews.

## Getting Started

These instructions will help you get a copy of the project running on your local machine for development and testing purposes.

### Prerequisites

To set up and run this project locally, you will need to have installed:

- Java 21
- Maven
- An SQL database, such as PostgreSQL (recommended for production environment). For development and basic testing, an in-memory database is configured by default.

### Installation

1. Clone the repository: git clone https://github.com/pedro-hbl/ximple-library.git
2. Navigate to the project directory: cd ximple-library
3. Compile the project using Maven: mvn clean install
4. Run the application: mvn spring-boot:run

Now, the application should be running locally and ready to be accessed.

## Technologies Used

### Java 21

Java 21 was chosen for its robustness, performance, and extensive developer community support.

### Spring Boot

Spring Boot was utilized to accelerate the development process by simplifying the setup and configuration of Spring-based applications.

### H2 Database

For development environments, I opted for the H2 Database due to its ease of use and configuration. H2 provides an in-memory database option, which is lightweight and suitable for local development and testing.

### Caching

Caching was implemented within the application to enhance performance, particularly for operations that request frequently accessed data such as the list of books. The choice of caching helps reduce the response time of these operations by storing frequently accessed data in memory, thus minimizing the need for repeated database queries. This improves overall system responsiveness and scalability.

### Observability

Observability was incorporated into the application to ensure monitoring of its health, performance tracking, and logging of important events. By leveraging observability tools such as Micrometer and Prometheus, we can collect and analyze metrics related to the application's performance and resource utilization. This allows for better insights into the application's behavior and facilitates proactive monitoring and troubleshooting.


## Database Structure

### Database Modeling
The database schema for the online library system is designed to efficiently support book searches, reservations, and reviews. The schema includes tables for users, books, reservations, and reviews, allowing for the management of these entities in a relational database.

### User Table
The users table stores information about registered users of the library system. Each user is identified by a unique user ID (userId) and includes fields for the user's name, email, password, and creation timestamp. This table enables user authentication and management within the application.

### Book Table
The books table represents the collection of books available in the library. Each book entry contains details such as the author's name, book title, publisher, publication year, total copies available, and reserved copies. The bookId serves as the primary key for identifying each book in the system. This table facilitates book search functionality and inventory management.

### Reservation Table
Reservations made by users to borrow books are stored in the reservations table. Each reservation is associated with a specific user (userId) and book (bookId) and includes a reservation ID (reservationId) and the timestamp of the reservation request (reservationRequestDatetime). This table tracks booked items and assists in managing borrowing periods and availability.

### Review Table
The reviews table records user reviews and ratings for books in the library. Each review entry is linked to a user (userId) and book (bookId) and includes fields for the review ID (reviewId), user comment, rating, and creation timestamp (creationDatetime). This table supports the review functionality, enabling users to provide feedback and ratings for books they have read.

### Database Structure Justification
The relational database schema was chosen for its suitability in modeling the relationships between users, books, reservations, and reviews. This structure allows for efficient data retrieval, updates, and integrity enforcement. By utilizing a relational database, we can ensure data consistency and facilitate complex queries required for various application features.

The chosen schema provides a clear representation of the entities and their relationships, making it easy to understand and maintain. Additionally, relational databases offer robust transaction support, ensuring ACID (Atomicity, Consistency, Isolation, Durability) properties, which are crucial for maintaining data integrity in a multi-user environment.

Overall, the relational database schema aligns with the requirements of the online library system, providing a solid foundation for data management and application functionality.

This schema was carefully crafted to ensure data integrity and efficient query performance.

## Project Structure

The project follows the principles of Clean Architecture, with packages organized into:

- `controller`: Contains classes defining the endpoints of the REST API.
- `service`: Houses the business logic of the application.
- `entity`: Defines the domain entities representing database tables.
- `repository`: Provides the data access layer for interacting with the database.
- `config`: Contains general application configurations.

This structure promotes modularity, scalability, and maintainability of the codebase.

## API Usage

### Book Controller APIs

- **Create a New Book**: `curl -X POST -H "Content-Type: application/json" -d '{"authorName":"Author Name", "title":"Book Title", "publisher":"Publisher Name", "publicationYear":2022, "totalCopies":10, "reservedCopies":0}' http://localhost:8080/ximple-api/books`

- **Update an Existing Book**: `curl -X PUT -H "Content-Type: application/json" -d '{"authorName":"New Author Name", "title":"New Book Title"}' http://localhost:8080/ximple-api/books/{bookId}`

- **Get a Book by ID**: `curl http://localhost:8080/ximple-api/books/{bookId}`

- **Check Book Availability**: `curl http://localhost:8080/ximple-api/books/{bookId}/availability`

- **Get Book Reviews**: `curl http://localhost:8080/ximple-api/books/{bookId}/reviews`

### User Controller APIs

- **Create a New User**: `curl -X POST -H "Content-Type: application/json" -d '{"name":"User Name", "email":"user@email.com", "password":"password123"}' http://localhost:8080/ximple-api/users`

- **Get a User by ID**: `curl http://localhost:8080/ximple-api/users/{userId}`

- **Delete a User by ID**: `curl -X DELETE http://localhost:8080/ximple-api/users/{userId}`

- **Reserve a Book for a User**: `curl -X POST http://localhost:8080/ximple-api/users/{userId}/book/{bookId}`

- **Review a Book**: `curl -X POST -H "Content-Type: application/json" -d '{"userComment":"User Comment", "rating":5}' http://localhost:8080/ximple-api/users/{userId}/review/{bookId}`

### Cache Config

The caching configuration is managed in the `CacheConfig` class, which defines the caching strategy for book reviews.

### Additional Notes

- **Observability**: Prometheus is configured to collect metrics, providing insights into application performance.
- **Database Migration**: Flyway is utilized for database schema versioning and migration.

















