# CountryStateCity-Controller
The project is a Spring Boot microservice architecture consisting of three microservices - City, State, and Country - each responsible for managing data related to their respective entities. The microservices are designed to communicate with each other via REST APIs.

The City microservice manages data related to cities, including city name, population, and other relevant information. The State microservice manages data related to states, including state name, capital city, and other relevant information. The Country microservice manages data related to countries, including country name, currency, and other relevant information.

The project includes a function that takes a country ID and returns all the states within that country, and inside each state, it returns all the cities within that state. This functionality is implemented using REST APIs to communicate between the microservices.

In addition, the project includes a Configuration microservice that manages global configurations for the system, such as database credentials and other system-level settings. It also includes a Service Registry microservice that utilizes Eureka server for service registration and discovery.

Lastly, an API Gateway microservice is included and integrated to serve as a single entry point for external requests to the system, routing them to the appropriate microservices based on the request path.

Overall, this Spring Boot microservice architecture provides a scalable and flexible solution for managing city, state, and country data with the ability to add more services in the future as the system grows.
