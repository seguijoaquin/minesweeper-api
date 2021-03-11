package com.minesweeper.api.adapter.web;

import com.minesweeper.api.application.port.in.GetGameByIdUseCase;
import com.minesweeper.api.application.port.in.MakeAMoveCommand;
import com.minesweeper.api.application.port.in.MakeAMoveUseCase;
import com.minesweeper.api.application.port.in.StartNewGameCommand;
import com.minesweeper.api.application.port.in.StartNewGameUseCase;
import com.minesweeper.api.domain.Game;
import com.minesweeper.api.objectMother.GameObjectMother;
import com.minesweeper.api.objectMother.MakeAMoveObjectMother;
import com.minesweeper.api.objectMother.StartNewGameObjectMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@WebFluxTest
class GameControllerTest {

    @Autowired
    private WebTestClient client;
    @MockBean
    private StartNewGameUseCase startNewGameUseCase;
    @MockBean
    private GetGameByIdUseCase getGameByIdUseCase;
    @MockBean
    private MakeAMoveUseCase makeAMoveUseCase;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Starting a new game returns a game and HTTP status 201")
    void startNewGame() {
        // GIVEN
        Game expectedGame = GameObjectMother.getGame();
        StartNewGameCommand command = StartNewGameObjectMother.newGameCommand();
        GameRequest request = StartNewGameObjectMother.newGameRequest();

        // WHEN
        when(startNewGameUseCase.startNewGame(command)).thenReturn(Mono.just(expectedGame));

        client.post().uri("/minesweeper/api/game")
                .body(Mono.just(request), GameRequest.class)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                // THEN
                .expectStatus().isCreated()
                .expectBody(Game.class)
                .value(response -> assertAll(
                        () -> assertEquals(expectedGame.getId(), response.getId()),
                        () -> assertEquals(expectedGame, response)
                ));
    }

    @Test
    @DisplayName("Getting a game by ID returns a game and HTTP status 200")
    void getGameById() {
        // GIVEN
        Game expectedGame = GameObjectMother.getGame();
        String gameId = expectedGame.getId();

        // WHEN
        when(getGameByIdUseCase.getGameById(gameId)).thenReturn(Mono.just(expectedGame));

        client.get().uri("/minesweeper/api/game/" + gameId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                // THEN
                .expectStatus().isOk()
                .expectBody(Game.class)
                .value(response -> assertAll(
                        () -> assertEquals(expectedGame.getId(), response.getId()),
                        () -> assertEquals(expectedGame, response)
                ));
    }

    @Test
    @DisplayName("Making a move by gameId returns the new game status and HTTP status 200")
    void makeAMove() {
        // GIVEN
        Game expectedGame = GameObjectMother.getGame();
        String gameId = expectedGame.getId();
        MakeAMoveCommand command = MakeAMoveObjectMother.revealCommand();
        MoveRequest request = MakeAMoveObjectMother.revealRequest();

        // WHEN
        when(makeAMoveUseCase.makeAMove(command)).thenReturn(Mono.just(expectedGame));

        client.put().uri("/minesweeper/api/game/" + gameId)
                .body(Mono.just(request), MoveRequest.class)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                // THEN
                .expectStatus().isOk()
                .expectBody(Game.class)
                .value(response -> assertAll(
                        () -> assertEquals(expectedGame.getId(), response.getId()),
                        () -> assertEquals(expectedGame, response)
                ));
    }
}