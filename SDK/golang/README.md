# Go API client for swagger

This is a Minesweeper server.  You can find out more about it at [github.com/seguijoaquin/minesweeper](github.com/seguijoaquin/minesweeper) 

## Overview
This API client was generated by the [swagger-codegen](https://github.com/swagger-api/swagger-codegen) project.  By using the [swagger-spec](https://github.com/swagger-api/swagger-spec) from a remote server, you can easily generate an API client.

- API version: 1.0.0
- Package version: 1.0.0
- Build package: io.swagger.codegen.v3.generators.go.GoClientCodegen

## Installation
Put the package under your project folder and add the following in import:
```golang
import "./swagger"
```

## Documentation for API Endpoints

All URIs are relative to *https://virtserver.swaggerhub.com/seguijoaquin/minesweeper/1.0.0*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*GameApi* | [**CreateGame**](docs/GameApi.md#creategame) | **Post** /minesweeper/api/game | Creates a new game
*GameApi* | [**GetGameById**](docs/GameApi.md#getgamebyid) | **Get** /minesweeper/api/game/{gameId} | Gets a game by ID
*GameApi* | [**UpdateGame**](docs/GameApi.md#updategame) | **Put** /minesweeper/api/game/{gameId} | Makes a move on existing game

## Documentation For Models

 - [Cell](docs/Cell.md)
 - [Game](docs/Game.md)
 - [GameRequest](docs/GameRequest.md)
 - [MoveRequest](docs/MoveRequest.md)
 - [ProblemDetail](docs/ProblemDetail.md)

## Documentation For Authorization

## basicAuth
- **Type**: HTTP basic authentication

Example
```golang
auth := context.WithValue(context.Background(), sw.ContextBasicAuth, sw.BasicAuth{
	UserName: "username",
	Password: "password",
})
r, err := client.Service.Operation(auth, args)
```

## Author

segui.joaquin@gmail.com


