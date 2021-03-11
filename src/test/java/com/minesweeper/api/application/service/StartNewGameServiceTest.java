package com.minesweeper.api.application.service;

import com.minesweeper.api.application.port.in.StartNewGameCommand;
import com.minesweeper.api.application.port.out.PersistGamePort;
import com.minesweeper.api.domain.Game;
import com.minesweeper.api.objectMother.GameObjectMother;
import com.minesweeper.api.objectMother.StartNewGameObjectMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;
import java.util.function.Supplier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StartNewGameServiceTest {

    @Mock
    private Supplier<UUID> uuidSupplier;
    @Mock
    private PersistGamePort persistGamePort;
    @Mock
    private Clock clock;

    private StartNewGameService startNewGameService;

    @BeforeEach
    void setUp() {
        startNewGameService = new StartNewGameService(uuidSupplier, persistGamePort, clock);
    }

    @Test
    void startNewGame() {
        // GIVEN
        StartNewGameCommand command = StartNewGameObjectMother.newGameCommand();
        Game game = GameObjectMother.getGame();

        // WHEN
        when(uuidSupplier.get()).thenReturn(UUID.fromString(game.getId()));
        when(clock.instant()).thenReturn(Instant.parse(game.getStartedAt()));
        when(persistGamePort.saveGame(game)).thenReturn(Mono.just(true));

        StepVerifier.create(startNewGameService.startNewGame(command))
                // THEN
                .expectNext(game).verifyComplete();

    }
}