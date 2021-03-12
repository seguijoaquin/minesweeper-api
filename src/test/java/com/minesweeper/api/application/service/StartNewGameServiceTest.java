package com.minesweeper.api.application.service;

import com.minesweeper.api.application.port.in.StartNewGameCommand;
import com.minesweeper.api.application.port.out.PersistGamePort;
import com.minesweeper.api.domain.Game;
import com.minesweeper.api.domain.GameStatus;
import com.minesweeper.api.objectMother.GameObjectMother;
import com.minesweeper.api.objectMother.StartNewGameObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Clock;
import java.time.Instant;
import java.util.Random;
import java.util.UUID;
import java.util.function.Supplier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StartNewGameServiceTest {

    private static final String MINE = "M";
    private static final long RANDOM_TEST_SEED = 123L;
    @Mock
    private Supplier<UUID> uuidSupplier;
    @Mock
    private PersistGamePort persistGamePort;
    @Mock
    private Clock clock;
    @Mock
    private Supplier<Random> randomSupplier;

    private StartNewGameService startNewGameService;

    @BeforeEach
    void setUp() {
        startNewGameService = new StartNewGameService(uuidSupplier, persistGamePort, clock, randomSupplier);
    }

    @Test
    @DisplayName("Creating a new game should create a new board with mines, all cells initialized and in PLAYING state")
    void startNewGame() {
        // GIVEN
        StartNewGameCommand command = StartNewGameObjectMother.newGameCommand();
        Game game = GameObjectMother.getGame();

        // WHEN
        when(uuidSupplier.get()).thenReturn(UUID.fromString(game.getId()));
        when(clock.instant()).thenReturn(Instant.parse(game.getStartedAt()));
        when(persistGamePort.saveGame(Mockito.any(Game.class))).thenReturn(Mono.just(true));
        when(randomSupplier.get()).thenReturn(new Random(RANDOM_TEST_SEED));

        StepVerifier.create(startNewGameService.startNewGame(command))
                // THEN
                .assertNext(newGame -> {
                    Assertions.assertEquals(command.getRows(), newGame.getRows());
                    Assertions.assertEquals(command.getCols(), newGame.getCols());
                    Assertions.assertEquals(command.getMines(), newGame.getMines());
                    Assertions.assertEquals(GameStatus.PLAYING, newGame.getStatus());
                    Assertions.assertNotNull(game.getStartedAt());
                    Assertions.assertNull(game.getUpdatedAt());
                    Assertions.assertEquals(Integer.parseInt(command.getCols()) * Integer.parseInt(command.getRows()), newGame.getBoard().size());
                    Assertions.assertEquals(command.getMines(), countMinesOnGameBoard(newGame));
                }).verifyComplete();
    }

    private String countMinesOnGameBoard(Game game) {
        return String.valueOf(game.getBoard().stream().filter(cell -> MINE.equalsIgnoreCase(cell.getValue())).count());
    }
}