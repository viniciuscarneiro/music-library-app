
# Music Library App

This project is a Music Library application that allows users to manage their music collections. 

It utilizes Spring Boot for the backend and MongoDB as the database.

## Prerequisites

Before running the application, make sure you have the following installed:

- Docker: [Install Docker](https://docs.docker.com/get-docker/)
- Docker Compose: [Install Docker Compose](https://docs.docker.com/compose/install/)

## Getting Started

1. **Clone the repository:**

    ```bash
    git clone https://git.jobsity.com/vinicius/backend-challenge.git
    cd backend-challenge
    ```

2. **Create an environment variables file:**

    Create a file named `.env` in the root of the project and set your MongoDB and Spring application variables. Use the provided `.env.example` as a template.

    ```env
    MONGODB_USER=root
    MONGODB_PASSWORD=123456
    MONGODB_DATABASE=music_library_db
    MONGODB_LOCAL_PORT=7017
    MONGODB_DOCKER_PORT=27017

    SPRING_LOCAL_PORT=6868
    SPRING_DOCKER_PORT=8080
    ```

3. **Run the application:**

    ```bash
    docker-compose up -d
    ```

    This command will build the Docker images and start the application services in the background.

4. **Access the Music Library App:**

    Open your web browser and go to [http://localhost:6868/swagger-ui/index.html](http://localhost:6868/swagger-ui/index.html) to access the Music Library Api documentation.

5. **Stop the application:**

    To stop the application, run:

    ```bash
    docker-compose down
    ```
    
##  API Endpoints

### 1. Search

**Endpoint:** GET /search

**Description:**
This endpoint allows clients to perform a search based on a specified search term. The search term is provided as a query parameter (`searchTerm`). The response includes a collection of search results in the form of a `SearchResultResponse`, encompassing artists, albums, and songs matching the search criteria.

**Request:**
- **Method:** GET
- **Path:** `/search`
- **Query Parameter:**
  - `searchTerm` (String): The term to be used for the search.

**Response:**
- **Status Code:** 200 OK
- **Body:** `SearchResultResponse` containing information about the search results.

**Note:**

The searchTerm parameter represents the term for which the search is conducted.
The response includes a collection of artists, albums, and songs matching the specified search term.
If no results are found, an empty SearchResultResponse is returned.


### 2. Create Library

**Endpoint:** POST /


**Description:**
This endpoint allows clients to create a new library. The request body should contain information required for creating the library.

**Request:**
- **Method:** POST
- **Path:** `/`
- **Request Body:**
  - `createLibraryRequest` (CreateLibraryRequest): Information required for creating the library.

**Response:**
- **Status Code:** 200 OK
- **Body:** `LibraryResponse` containing information about the created library.

### 3. Add Library Item

**Endpoint:** PUT /{libraryId}




**Description:**
This endpoint allows clients to add an item to a library. The `libraryId` path parameter identifies the target library, and the request body should contain information about the item to be added.

**Request:**
- **Method:** PUT
- **Path:** `/{libraryId}`
- **Path Parameter:**
  - `libraryId` (String): Identifier of the target library.
- **Request Body:**
  - `addItemToLibraryRequest` (AddItemToLibraryRequest): Information about the item to be added.

**Response:**
- **Status Code:** 204 No Content
- **Body:** No content in the response body.


### 4. Remove Library

**Endpoint:** DELETE /{libraryId}

**Description:**
This endpoint allows clients to remove an existing library. The `libraryId` path parameter identifies the library to be removed.

**Request:**
- **Method:** DELETE
- **Path:** `/{libraryId}`
- **Path Parameter:**
  - `libraryId` (String): Identifier of the library to be removed.

**Response:**
- **Status Code:** 204 No Content
- **Body:** No content in the response body.


### 5. Remove Library Item

**Endpoint:** DELETE /{libraryId}/{libraryItemId}


**Description:**
This endpoint allows clients to remove an item from a library. The `libraryId` path parameter identifies the target library, and the `libraryItemId` path parameter identifies the item to be removed.

**Request:**
- **Method:** DELETE
- **Path:** `/{libraryId}/{libraryItemId}`
- **Path Parameters:**
  - `libraryId` (String): Identifier of the target library.
  - `libraryItemId` (String): Identifier of the item to be removed.

**Response:**
- **Status Code:** 204 No Content
- **Body:** No content in the response body.


### 6. Get Library

**Endpoint:** GET /{libraryId}

**Description:**
This endpoint allows clients to retrieve information about a specific library. The `libraryId` path parameter identifies the target library.

**Request:**
- **Method:** GET
- **Path:** `/{libraryId}`
- **Path Parameter:**
  - `libraryId` (String): Identifier of the target library.

**Response:**
- **Status Code:** 200 OK
- **Body:** `LibraryResponse` containing information about the requested library.
