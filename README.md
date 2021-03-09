# Minesweeper API Server

A minesweeper API server. Contract first developed with editor.swagger.io

OpenAPI doc may be found at [/docs](https://github.com/seguijoaquin/minesweeper-api/blob/450208cd569178325e9e3bc267b812ca3b2275ca/docs/minesweeper-1.0.0-swagger.yaml).

App deployed in Heroku. Base URL is https://poc-minesweeper-api.herokuapp.com/


## Responsibilities

1. Creating a new game. [See how](#create_game). 

2. Getting status of an existing game. [See how](#get_status)

3. Making a move on existing game. [See how](#make_move)


### Endpoints

| Endpoint                         | Method     | Description
| -------------------------        | ---------- | -----------------------------------      |
| `/minesweeper/api/game`          | POST       | Creates a new game                       |
| `/minesweeper/api/game/:game_id` | GET        | Returns game information given its id    |
| `/minesweeper/api/game/:game_id` | PUT        | Makes a move on game given its id        |



## How to authenticate

_User registration, login and logout is out of the scope. The API asumes the user is correctly registered and in possesion of a basic authentication token._

Basic authentication is a simple authentication scheme built into the HTTP protocol. The client sends HTTP requests with the Authorization header that contains the word Basic word followed by a space and a base64-encoded string username:password.

_The following tokens are supposed to be from registered users:_
  - _Basic dXNlcm5hbWU6cGFzc3dvcmQ=_
  - _Basic YW5vdGhlcl91c2VybmFtZTpwYXNzd29yZA==_
  - _Basic cGVyc29uYWxfdXNlcjpwYXNzd29yZA==_


## Error handling

Client and server error are expected to be represented following theProblem Details proposed standard on [RFC 7807 https://tools.ietf.org/html/rfc7807](https://tools.ietf.org/html/rfc7807)


## Examples

### Create a new game

### POST `/minesweeper/api/game`

| Code  | Description   |
| ----  | ------------- |
| 201   | Game created  |
| 400   | Invalid input |
| 401   | Unauthorized  |

```
curl --request POST 'https://poc-minesweeper-api.herokuapp.com/minesweeper/api/game' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic dXNlcm5hbWU6cGFzc3dvcmQ=' \ 
--data-raw '{"rows": 2, "cols": 2, "mines": 1}
```

#### Request body example
```
{
  "rows": 2,
  "cols": 2,
  "mines": 1
}
```

#### Response
```
{
  "id": 1,
  "rows": 2,
  "cols": 2,
  "mines": 1,
  "started_at": "2021-03-09T02:07:19.010Z",
  "status": "playing",
  "board": [
    { "value": "1", "status": "uncovered" },
    { "value": "1", "status": "uncovered" },
    { "value": "1", "status": "uncovered" },
    { "value": "B", "status": "uncovered" }
  ]
}
```

### Get a game by ID

### GET `/minesweeper/api/game/:gameId`

| Code  | Description   |
| ----  | ------------- |
| 200   | Game found    |
| 401   | Unauthorized  |
| 404   | Game not found|

```
curl --request GET 'https://poc-minesweeper-api.herokuapp.com/minesweeper/api/game/:gameId' \
--header 'Authorization: Basic dXNlcm5hbWU6cGFzc3dvcmQ=' 
```

#### Response
```
{
  "id": 1,
  "rows": 2,
  "cols": 2,
  "mines": 1,
  "started_at": "2021-03-09T02:07:19.010Z",
  "status": "playing",
  "board": [
    { "value": "1", "status": "uncovered" },
    { "value": "1", "status": "uncovered" },
    { "value": "1", "status": "uncovered" },
    { "value": "B", "status": "uncovered" }
  ]
}
```

### Making a move on existing game

### PUT `/minesweeper/api/game/:gameId`

| Code  | Description   |
| ----  | ------------- |
| 201   | Success       |
| 400   | Invalid input |
| 401   | Unauthorized  |
| 404   | Game not found|
| 409   | Conflict move |

```
curl --request POST 'https://poc-minesweeper-api.herokuapp.com/minesweeper/api/game/:gameId' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic dXNlcm5hbWU6cGFzc3dvcmQ=' \ 
--data-raw '{"row": 2, "col": 2, "action": "reveal"}
```

#### Request body example
```
{
  "row": 2,
  "col": 2,
  "action": "reveal"
}
```

#### Response
```
{
  "id": 1,
  "rows": 2,
  "cols": 2,
  "mines": 1,
  "started_at": "2021-03-09T02:07:19.010Z",
  "ended_at": "2090-06-20T02:07:19.010Z",
  "status": "lost",
  "board": [
    { "value": "1", "status": "uncovered" },
    { "value": "1", "status": "uncovered" },
    { "value": "1", "status": "uncovered" },
    { "value": "B", "status": "revealed" }
  ]
}
```