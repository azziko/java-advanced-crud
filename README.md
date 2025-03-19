# CRUD as specified in the technical desccription for Azul
- [Set Up](#set-up)
- [Auth](#auth)
- [Database](#database)
- [Postman test outputs](#postman-test-outputs)

## Set up 
To implement CRUD I used standard Spring boot package that includes the following dependencies:
- **Lombok** to reduce the boilerplate
- **MYSQL Driver** to communicate with MySQL database
- **Spring  Web** to build the RESTful application
- **Spring  Security** to implement basic auth
- **Spring  Data JPA** to manage SQL queries 
- The project is ran on Maven.

## Auth
To restrict access to end-points that are not GET, I configured Spring Security Config. For the purpose of demonstration, I also submitted it with the login and password revealed. 

## Database
For the testing purposes, I installed MySQL Workbench and MySQL Community server to be able to run and test the queries. As for the scheme, I described the Books table that would store books.

## Postman test outputs
### POST http://localhost:8080/api/books
*Basic-auth:*
- Username: Admin
- Password: qwerty
*Body:*
```json
{
    "title": "The Catcher in the Rye",
    "author": "J.D. Salinger",
    "genre": "fantasy",
    "price": 42
}
```
*Output code: 201*

### GET http://localhost:8080/api/books/search/title?title=catcher&page=0&size=5
*Output:*
```json 
{
    "content": [
        {
            "id": 1,
            "title": "The Catcher in the Rye",
            "author": "J.D. Salinger",
            "genre": "fantasy",
            "price": 42.000
        }
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 5,
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "last": true,
    "totalPages": 1,
    "totalElements": 1,
    "first": true,
    "size": 5,
    "number": 0,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "numberOfElements": 1,
    "empty": false
}
```

## Notes:
- Without basic auth returns Unauthorized.
- To run(with appropriate datasource configuration up to database name and password):
``` 
mvn install
mvn spring-boot:run
```
