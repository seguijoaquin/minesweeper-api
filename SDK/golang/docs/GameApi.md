# {{classname}}

All URIs are relative to *https://virtserver.swaggerhub.com/seguijoaquin/minesweeper/1.0.0*

Method | HTTP request | Description
------------- | ------------- | -------------
[**CreateGame**](GameApi.md#CreateGame) | **Post** /minesweeper/api/game | Creates a new game
[**GetGameById**](GameApi.md#GetGameById) | **Get** /minesweeper/api/game/{gameId} | Gets a game by ID
[**UpdateGame**](GameApi.md#UpdateGame) | **Put** /minesweeper/api/game/{gameId} | Makes a move on existing game

# **CreateGame**
> Game CreateGame(ctx, body)
Creates a new game

### Required Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ctx** | **context.Context** | context for authentication, logging, cancellation, deadlines, tracing, etc.
  **body** | [**GameRequest**](GameRequest.md)| Game specs | 

### Return type

[**Game**](Game.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **GetGameById**
> Game GetGameById(ctx, gameId)
Gets a game by ID

Returns a single game

### Required Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ctx** | **context.Context** | context for authentication, logging, cancellation, deadlines, tracing, etc.
  **gameId** | **int64**| ID of game to return | 

### Return type

[**Game**](Game.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **UpdateGame**
> Game UpdateGame(ctx, body, gameId)
Makes a move on existing game

### Required Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ctx** | **context.Context** | context for authentication, logging, cancellation, deadlines, tracing, etc.
  **body** | [**MoveRequest**](MoveRequest.md)| Move specs | 
  **gameId** | **int64**| ID of game to be | 

### Return type

[**Game**](Game.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

