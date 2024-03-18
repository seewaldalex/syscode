# syscode
Demo project


Profile Service can be found in SnapShots/ProfileService folder.
It can be started via the following command:
java -jar ProfileService-0.0.1-SNAPSHOT.jar


Address Service can be found in SnapShots/AddressService folder.
It can be started via the following command:
java -jar AddressService-0.0.1-SNAPSHOT.jar


The project can be accessed/tested/used via swagger trough the following link.
localhost:8080/swagger-ui/index.html


Address Service is only used in one use-case, namely when we are getting a Student by its id.
In that case we call the Address Service and it will return a dummy address.
In case we do not start the Address Service, then we will catch the exception against the remote call
and return a dummy default address that is built in the Profile Service with an address of "Address unavailable". 


Improvements that can be added:
* Mocking out the remote service in integration tests by MockServerClient.
* Adding unit tests. Handling remote service call exception by exception type és response code type.
* Adding logback for auto logging endpoint calls.
* Using docker compose with different profiles for selecting what we would like to run.
* With docker adding non embedded database e.g. MySQL. In this case adding MySQL test container for integration tests.  
* Implementing Address Service for all use-cases.
* Persisting the address.
* Adding role based authentication with bearer token.