package com.minesweeper.api.application.service;

import com.minesweeper.api.application.port.in.MakeAMoveCommand;
import com.minesweeper.api.application.port.out.GetGameByIdPort;
import com.minesweeper.api.application.port.out.PersistGamePort;
import com.minesweeper.api.domain.Action;
import com.minesweeper.api.domain.Cell;
import com.minesweeper.api.domain.CellStatus;
import com.minesweeper.api.domain.Game;
import com.minesweeper.api.domain.GameAlreadyFinishedError;
import com.minesweeper.api.domain.GameNotFoundError;
import com.minesweeper.api.domain.GameStatus;
import com.minesweeper.api.objectMother.GameObjectMother;
import com.minesweeper.api.objectMother.MakeAMoveObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MakeAMoveServiceTest {

    @Mock
    private GetGameByIdPort getGameByIdPort;
    @Mock
    private PersistGamePort persistGamePort;

    private MakeAMoveService makeAMoveService;

    @BeforeEach
    void setUp() {
        makeAMoveService = new MakeAMoveService(getGameByIdPort, persistGamePort);
    }

    @Test
    @DisplayName("Revealing a cell with a mine ends the game")
    void detectWhenTheGameIsOver() {
        // GIVEN
        Game game = GameObjectMother.getGame(); // 3x3 board with 2 mines in {(1,2);(3,3)}
        MakeAMoveCommand command = MakeAMoveCommand.builder()
                .gameId(game.getId())
                .row("1")
                .col("2")
                .action(Action.REVEAL)
                .build();

        // WHEN
        StepVerifier.create(makeAMoveService.makeAMove(command))
                // THEN
                .assertNext(newGame -> {
                    Assertions.assertEquals(GameStatus.LOST, newGame.getStatus());
                }).verifyComplete();
    }

    @Test
    @DisplayName("Making a move on a game with a different status of PLAYING should throw an error")
    void makingAMoveOnFinishedGameShouldThrowAnError() {
        // GIVEN
        MakeAMoveCommand command = MakeAMoveObjectMother.revealCommand();
        Game game = GameObjectMother.getFinishedGame();

        when(getGameByIdPort.getGameById(command.getGameId())).thenReturn(Mono.just(game));

        // WHEN
        StepVerifier.create(makeAMoveService.makeAMove(command))
                // THEN
                .expectError(GameAlreadyFinishedError.class)
                .verify();
    }

    @Test
    @DisplayName("Making a move on a non existing game should return an error")
    void makingAMoveOnNonExistingGameShouldThrowAnError() {
        // GIVEN
        MakeAMoveCommand command = MakeAMoveObjectMother.revealCommand();

        when(getGameByIdPort.getGameById(command.getGameId())).thenReturn(Mono.empty());

        // WHEN
        StepVerifier.create(makeAMoveService.makeAMove(command))
                // THEN
                .verifyError(GameNotFoundError.class);
    }

    @Test
    @DisplayName("When we flag a cell with a red flag the status of that cell should change")
    void redFlagOk() {
        // GIVEN
        Game game = GameObjectMother.getGame(); // 3x3 board
        MakeAMoveCommand command = MakeAMoveCommand.builder()
                .gameId(game.getId())
                .row("1")
                .col("2")
                .action(Action.RED_FLAG)
                .build();

        // WHEN
        StepVerifier.create(makeAMoveService.makeAMove(command))
                // THEN
                .assertNext(newGame -> {
                    Assertions.assertEquals(GameStatus.PLAYING, newGame.getStatus());
                    Assertions.assertEquals(CellStatus.RED_FLAGGED, game.getBoard().get(1).getStatus());
                }).verifyComplete();
    }

    @Test
    @DisplayName("When we flag a cell with a question mark the status of that cell should change")
    void questionMarkOk() {
        // GIVEN
        Game game = GameObjectMother.getGame(); // 3x3 board
        MakeAMoveCommand command = MakeAMoveCommand.builder()
                .gameId(game.getId())
                .row("1")
                .col("2")
                .action(Action.UNKNOWN_FLAG)
                .build();

        // WHEN
        StepVerifier.create(makeAMoveService.makeAMove(command))
                // THEN
                .assertNext(newGame -> {
                    Assertions.assertEquals(GameStatus.PLAYING, newGame.getStatus());
                    Assertions.assertEquals(CellStatus.QUESTION_MARKED, game.getBoard().get(1).getStatus());
                }).verifyComplete();
    }

    @Test
    @DisplayName("When we unflag a cell already flagged the status of that cell should change")
    void unflagAFlaggedCell() {
        // GIVEN
        Game game = GameObjectMother.getGame().toBuilder()
                .rows("1")
                .cols("2")
                .board(List.of(
                        new Cell("M", CellStatus.RED_FLAGGED, 0),
                        new Cell("M", CellStatus.COVERED, 1)
                ))
                .build();// 1x2 board with a red flag on  cell (0,0)

        MakeAMoveCommand command = MakeAMoveCommand.builder()
                .gameId(game.getId())
                .row("0")
                .col("0")
                .action(Action.UNFLAG)
                .build();

        // WHEN
        StepVerifier.create(makeAMoveService.makeAMove(command))
                // THEN
                .assertNext(newGame -> {
                    Assertions.assertEquals(GameStatus.PLAYING, newGame.getStatus());
                    Assertions.assertEquals(CellStatus.COVERED, game.getBoard().get(0).getStatus());
                }).verifyComplete();
    }

    @DisplayName("When a cell with no adjacent mines is revealed, all adjacent squares will be revealed (and repeat)")
    @Test
    void revealCellWithNoAdjacentMinesOK() {
        // GIVEN
        Game game = GameObjectMother.getGame().toBuilder()
                .board(List.of(
                        new Cell("0", CellStatus.COVERED, 0),
                        new Cell("0", CellStatus.COVERED, 1),
                        new Cell("0", CellStatus.COVERED, 2),
                        new Cell("2", CellStatus.COVERED, 3),
                        new Cell("3", CellStatus.COVERED, 4),
                        new Cell("2", CellStatus.COVERED, 5),
                        new Cell("M", CellStatus.COVERED, 6),
                        new Cell("M", CellStatus.COVERED, 7),
                        new Cell("M", CellStatus.COVERED, 8)
                ))
                .build();// 3x3 board with 3 mines on the last row of the board

        MakeAMoveCommand command = MakeAMoveCommand.builder() // Reveal cell on index 0
                .gameId(game.getId())
                .row("0")
                .col("0")
                .action(Action.REVEAL)
                .build();

        // WHEN
        StepVerifier.create(makeAMoveService.makeAMove(command))
                // THEN
                .assertNext(newGame -> {
                    Assertions.assertEquals(GameStatus.PLAYING, newGame.getStatus());
                    Assertions.assertEquals(CellStatus.UNCOVERED, game.getBoard().get(0).getStatus());
                    Assertions.assertEquals(CellStatus.UNCOVERED, game.getBoard().get(1).getStatus());
                    Assertions.assertEquals(CellStatus.UNCOVERED, game.getBoard().get(2).getStatus());
                    Assertions.assertEquals(CellStatus.COVERED, game.getBoard().get(3).getStatus());
                    Assertions.assertEquals(CellStatus.COVERED, game.getBoard().get(4).getStatus());
                    Assertions.assertEquals(CellStatus.COVERED, game.getBoard().get(5).getStatus());
                    Assertions.assertEquals(CellStatus.COVERED, game.getBoard().get(6).getStatus());
                    Assertions.assertEquals(CellStatus.COVERED, game.getBoard().get(7).getStatus());
                    Assertions.assertEquals(CellStatus.COVERED, game.getBoard().get(8).getStatus());
                }).verifyComplete();
    }

    @DisplayName("When a cell with adjacent mines is revealed, no adjacent squares will be revealed")
    @Test
    void revealCellWithAdjacentMinesOK() {
        // GIVEN
        Game game = GameObjectMother.getGame().toBuilder()
                .board(List.of(
                        new Cell("0", CellStatus.COVERED, 0),
                        new Cell("0", CellStatus.COVERED, 1),
                        new Cell("0", CellStatus.COVERED, 2),
                        new Cell("2", CellStatus.COVERED, 3),
                        new Cell("3", CellStatus.COVERED, 4),
                        new Cell("2", CellStatus.COVERED, 5),
                        new Cell("M", CellStatus.COVERED, 6),
                        new Cell("M", CellStatus.COVERED, 7),
                        new Cell("M", CellStatus.COVERED, 8)
                ))
                .build();// 3x3 board with 3 mines on the last row of the board

        MakeAMoveCommand command = MakeAMoveCommand.builder() // Reveal Cell on index 4
                .gameId(game.getId())
                .row("1")
                .col("1")
                .action(Action.REVEAL)
                .build();

        // WHEN
        StepVerifier.create(makeAMoveService.makeAMove(command))
                // THEN
                .assertNext(newGame -> {
                    Assertions.assertEquals(GameStatus.PLAYING, newGame.getStatus());
                    Assertions.assertEquals(CellStatus.COVERED, game.getBoard().get(0).getStatus());
                    Assertions.assertEquals(CellStatus.COVERED, game.getBoard().get(1).getStatus());
                    Assertions.assertEquals(CellStatus.COVERED, game.getBoard().get(2).getStatus());
                    Assertions.assertEquals(CellStatus.COVERED, game.getBoard().get(3).getStatus());
                    Assertions.assertEquals(CellStatus.UNCOVERED, game.getBoard().get(4).getStatus());
                    Assertions.assertEquals(CellStatus.COVERED, game.getBoard().get(5).getStatus());
                    Assertions.assertEquals(CellStatus.COVERED, game.getBoard().get(6).getStatus());
                    Assertions.assertEquals(CellStatus.COVERED, game.getBoard().get(7).getStatus());
                    Assertions.assertEquals(CellStatus.COVERED, game.getBoard().get(8).getStatus());
                }).verifyComplete();
    }
}