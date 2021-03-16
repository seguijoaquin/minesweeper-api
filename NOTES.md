## Dev Notes

_sp stands for "Story Points"_

### TO DO List:
 - Fill board with numbers indicating how many mines are adjacent to the cell during board creation (2 sp)
 - Create a Mapper and return appropiate DTO on controller layer, so we do not expose our interal sets used in the `Game` entity to check for winning/losing games (1 sp)
 - Implement controller exception handler on `adapter.web layer and return error based on Problem Detail Standard (2 sp)
 - Update swagger yaml or just merge into the code (2 sp)
 - Validate user input on creation (2 sp)
 - Validate user input on update (2 sp)
 - Implement recursive revealing action (3 sp)
 - Update `MakeAMoveService` tests (1 sp)
 - Update time on each movement (1 sp)
 


### Thinking process

The API was designed using a contract first approach. The first step was to set the contract the API should have.
This approach made possible to have a client SDK implemented on any language, and a api server in case needed, by just defining our the api contract with Swagger.

Once the API contract was set up, as we were asked to write the API using Java, next step was to decide about our stack.
Spring was the easy choice, since it's one of the most popular framework for java backend.

Although the server could have perfectly be made using a non-reactive approach, while thinking about the code I was going to write
I felt it would be nicer to have all game validations and rule checking chained to one another by lambda calls rather than
going non-reactive (And I wanted to try the reactor-core api xD)

As for the NoSQL storage, there were no queries to be made besides the `getGameById` one so thought a relational database would be
an overkill. Redis was chosen just because it was free on Heroku platform.
  
Quickiest and cheapest way to go with such stack was using the Heroku platform with a Free Redis instance.

Having decided about all that, first thing I wanted to do was to get a base app up and running with an endpoint that could
read and write values to the configured Redis cluster on Heroku, and could be exposed to the internet as it was a requirement.

Once that was OK, next thing was to implement the game and everything else.

The app was designed following hexagonal architecture, with a ports and adapters approach. It basically has three layers:

 - **adapter**: Divided into `persistance` and `web` they are  responsible for implementing the output ports from the `application` layer. 
 They are the ones with the know-how of the interfaces needed to communicate with other systems. `web` package contains everything about the endpoints
 the API exposes, while `persistence` is responsible for communicating with the Redis cluster.   
 
 - **application**: Divided into `port` and `service`, this package contains application specific configuration and most importantly
 it defines the UseCases the application handles. Those usecases are defined into the `port.in` package and implemented in the `service` package
 This package should only import classes from the domain package, and any interaction with an external system should be made by interfaces defined on the `port.out` package
 
 - **domain**: Domain objects and errors, related to business, should only contain pure java code and do not import code from other packages

