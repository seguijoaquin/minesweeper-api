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
 
Every endpoint of the app needs an authentication header, and asumes the user already has one. Basic authentication was chosen
to handle multiple users/accounts since it was an easy and friendly way of having a username stored next to the Game entity
available for checking game ownership if needed and supporting the "multiple account" requirement. Same thing could have been done using tokens, api-keys or similar.

As for the time tracking and saving/resuming game, since it is a RestAPI, each state change is persisted so time could be tracked from when
the game starts up to current time if needed. And resuming a game could be achieved by just getting the game by ID, and making another move.

The logic behind detecting when a game is over needed aditional data structures to be handled easily. First of all, when creating the game
we need to know where the mines are, and if given a board index there is a mine on the board. Easiest way of doing it 
is to have a Set of indexes storing each mine position on the board. Same thing needs to be made with flags on the board and revealed cells.

So whenever we want to reveal a new cell, we first check if our Mines set contains that cell. In case it does, the game is over.
If it doesn't we add that cell to the revealed cells Set. Once that is made, we need to check if we won:
We do that by checking if the revealed cells set is equal to the total cells set minus the mines set. If it is not, we evaluate the value of the revealed cell.
If it is empty, we repeat same procedure recursively on adjacent cells. Otherwise, the revealing stops there.
 
In case we want to flag a cell, the procedure is quite the same. First we check if the flagged set is equal to the mines set
and there are no unrevealed cells. If both things are ok then we won the game. If its not, we add the cell to the flagged cells.
