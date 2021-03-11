package com.minesweeper.api.application.service;

import com.minesweeper.api.application.port.out.GetGameByIdPort;
import com.minesweeper.api.domain.Game;
import com.minesweeper.api.objectMother.GameObjectMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetGameByIdServiceTest {

    @Mock
    private GetGameByIdPort getGameByIdPort;

    private GetGameByIdService getGameByIdService;

    @BeforeEach
    void setUp() {
        getGameByIdService = new GetGameByIdService(getGameByIdPort);
    }

    @Test
    void getGameById() {
        // GIVEN
        Game game = GameObjectMother.getGame();
        String gameId = game.getId();

        // WHEN
        when(getGameByIdPort.getGameById(gameId)).thenReturn(Mono.just(game));

        StepVerifier.create(getGameByIdService.getGameById(gameId))
                // THEN
                .expectNext(game).verifyComplete();
    }
}