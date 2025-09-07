# CRUD as specified in the technical desccription for Azul
- [Set Up](#set-up)
- [Auth](#auth)
- [Database](#database)
- [UI](#ui)
- [Postman test outputs](#postman-test-outputs)

## Set up 
To implement CRUD I used standard Spring boot package that includes the following dependencies:
- **Lombok** to reduce the boilerplate
- **MYSQL Driver** to communicate with MySQL database
- **Spring  Web** to build the RESTful application
- **Spring  Security** to implement basic auth
- **Spring  Data JPA** to manage SQL queries 
- **Thymeleaf** to manage UI via a templater
- The project is ran on Maven.

## Auth
To restrict access to end-points that are not GET, I configured Spring Security Config.

## Database
For the testing purposes, I installed MySQL Workbench and MySQL Community server to be able to run and test the queries. As for the scheme, I described the Books table that would store poems.

## UI
To see the UI, after running the project, open localhost in any browser on port 8080.

## Postman test outputs

### Post http://localhost:8080/api/poems
*Basic-auth:*
- Username: Admin
- Password: qwerty
*Body:*
```json
{
    "id": 162,
    "title": "Some random poem",
    "author": "King",
    "genre": "horror",
    "content": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce iaculis dapibus orci, quis hendrerit nisl congue pulvinar. Phasellus sollicitudin dui risus, sed tincidunt risus molestie vel. In finibus egestas mauris, sed venenatis tortor sodales eget. Donec ipsum nunc, venenatis at efficitur ac, tincidunt eget nisi. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Pellentesque lectus magna, fermentum ut auctor eget, tempus ac erat. Aenean eu justo pellentesque ligula convallis blandit nec eu magna. Aliquam nec quam a libero luctus varius. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed faucibus massa eget purus dictum viverra. ",
    "createdAt": "2025-09-07T09:56:10.3862505"
}
```
*Output code: 201*

### Get http://localhost:8080/api/poems/search/title?title=Some&page=0&size=5
```json
{
    "content": [
        {
            "id": 148,
            "title": "Some random poem 123",
            "author": "King",
            "genre": "horror",
            "content": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce iaculis dapibus orci, quis hendrerit nisl congue pulvinar. Phasellus sollicitudin dui risus, sed tincidunt risus molestie vel. In finibus egestas mauris, sed venenatis tortor sodales eget. Donec ipsum nunc, venenatis at efficitur ac, tincidunt eget nisi. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Pellentesque lectus magna, fermentum ut auctor eget, tempus ac erat. Aenean eu justo pellentesque ligula convallis blandit nec eu magna. Aliquam nec quam a libero luctus varius. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed faucibus massa eget purus dictum viverra. ",
            "createdAt": "2025-07-12T12:23:16.918307"
        },
        {
            "id": 162,
            "title": "Some random poem",
            "author": "King",
            "genre": "horror",
            "content": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce iaculis dapibus orci, quis hendrerit nisl congue pulvinar. Phasellus sollicitudin dui risus, sed tincidunt risus molestie vel. In finibus egestas mauris, sed venenatis tortor sodales eget. Donec ipsum nunc, venenatis at efficitur ac, tincidunt eget nisi. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Pellentesque lectus magna, fermentum ut auctor eget, tempus ac erat. Aenean eu justo pellentesque ligula convallis blandit nec eu magna. Aliquam nec quam a libero luctus varius. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed faucibus massa eget purus dictum viverra. ",
            "createdAt": "2025-09-07T09:56:10.386251"
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
    "totalElements": 2,
    "first": true,
    "size": 5,
    "number": 0,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "numberOfElements": 2,
    "empty": false
}
```

## Notes:
- To run(with appropriate datasource configuration up to database name and password):
``` 
mvn install
mvn spring-boot:run
```
