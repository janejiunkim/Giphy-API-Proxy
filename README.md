# Giphy API Proxy

- [Giphy API Proxy](#giphy-api-proxy)
  - [About](#about)
  - [Configuration](#configuration)
  - [Instructions](#instructions)
  - [Reflection](#reflection)
    - [Blockers](#blockers)
    - [Stretch Goals:](#stretch-goals)
    - [Postman Examples:](#postman-examples)

## About

This project is a proxy to access the Giphy API: [Giphy API](https://developers.giphy.com/docs/api#quick-start-guide). It allows a client to search for gifs according to a search term using the route /search/[search_term]. At the service layer, the API key being used is appended (hidden from client), along with the restrictions on the response (0 or 5 results). It then parses response according to the instructions provided (extract only id and url for each gif and send back 0 if less than five and five if at least 5).
The client sends a GET request, this service then sends a GET request to access the Giphy web API search endpoint and get the JSON with Gifs related to the search_term. The API response is in the form of a JSON like so: 

```json
{
    // This "data" array contains the gif objects related to the query
    data: [
        // Each of these objects are equal to a gif object
        {
            type: string,
            id: string,
            slug: string,
            url: string
            // other data
            ... 
        }
    ]
}
```

This project takes the response from the API, retrieves only the id and url for each gif object, wraps the entire list of newly defined gif objects with just the id and url and sends it back to the client. The returned JSON should appear like so:

```json
{
    data: [
        {
            gif_id: string,
            url: string
        },
        {
            gif_id: string,
            url: string
        },
        {
            gif_id: string,
            url: string
        },
        {
            gif_id: string,
            url: string
        },
        {
            gif_id: string,
            url: string
        }
    ]
}
```

The API returns 5 gif objects in the data array if there are at least 5 results to the query.

If there are less than 5 objects related to the query, 0 results are returned and the response appears like this:

```json
{
    data: []
}
```

There should also be output to the console:
"Less than five Gif objects :("

## Configuration

To change the **API key** being used to access Giphy API:

Inside the project directory locate the application.properties file which is at path:

```sh
/src/main/java/com/janekim/gif/resources/application.properties
```

Inside the application.properties file, the first property should be:

```sh
api_key=nPcu7YTrpMkRwN3aMWS63gJWEm4bKKI0
```

It is currently set to the api key that was provided, set api_key to a different key.

## Instructions

To run this application:
Locate to the project directory gif-webservice in a terminal window
Run the following command:

```sh
./mvnw spring-boot:run
```

In a separate terminal window run the following command:

```curl
curl localhost:8080/search/[search_term]
```

Ctrl+C to stop application

## Reflection

### Blockers

- As this was my very first project in Spring Boot/Spring/Maven in general, the concepts and actual execution took longer than expected.
  
- The automatic serialization/deserialization of the HTTP Response bodies proved to be challenging. I ended up extracting individual JSON properties using Jackson JsonNode and wrapping it back up into a data object which consists of a list of gif objects. Probably could have used @JsonFilter annotation to only serialize id and url fields.
  
- Whether or not to use a database:
  - As the assignment never mentioned saving the query results, for the sake of time and ease of difficulty, I opted not to. As this is just a thin proxy service layer, the client may serve queries if necessary.

- The Spring framework in general: There were many abstractions that I have yet to master in this particular area, but overall this project proved to be a great opportunity for me as a developer to learn a new methodology.

### Stretch Goals:

- If I was allowed more time, I would have liked to practice implementing testing and adding more custom exceptions.

### Postman Examples:

Valid Response for search_term = cats:

![Valid Postman Response Screenshot](/ValidPostman.png)

Invalid Response for search_term = gsngesonione:

![Invalid Postman Response Screenshot](/InvalidPostman.png)