package com.minesweeper.api.application.service;

import com.minesweeper.api.application.port.in.StartNewGameCommand;
import com.minesweeper.api.application.port.in.StartNewGameUseCase;
import com.minesweeper.api.application.port.out.PersistGamePort;
import com.minesweeper.api.domain.Cell;
import com.minesweeper.api.domain.CellStatus;
import com.minesweeper.api.domain.Game;
import com.minesweeper.api.domain.GameStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Clock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StartNewGameService implements StartNewGameUseCase {

    private static final String MINE = "M";
    private static final String EMPTY = "0";
    private final Supplier<UUID> uuidSupplier;
    private final PersistGamePort persistGamePort;
    private final Clock clock;
    private final Supplier<Random> randomSupplier;

    @Override
    public Mono<Game> startNewGame(StartNewGameCommand command) {
        Set<Integer> minePositions = getMinePositionsInBoard(
                Integer.valueOf(command.getRows()),
                Integer.valueOf(command.getCols()),
                Integer.valueOf(command.getMines())
        );
        List<Cell> board = buildBoard(
                Integer.valueOf(command.getRows()),
                Integer.valueOf(command.getCols()),
                minePositions
        );
        Game game = Game.builder()
                .id(generateNewGameId())
                .rows(command.getRows())
                .cols(command.getCols())
                .mines(command.getMines())
                .startedAt(clock.instant().toString())
                .status(GameStatus.PLAYING)
                .board(board)
                .minesIndexes(minePositions)
                .redFlagIndexes(Collections.emptySet())
                .questionFlagIndexes(Collections.emptySet())
                .revealedIndexes(Collections.emptySet())
                .user(command.getUser())
                .build();
        return persistGamePort.saveGame(game).thenReturn(game);
    }

    private List<Cell> buildBoard(final Integer rows, final Integer cols, final Set<Integer> minePositions) {
        List<Cell> board = new ArrayList<>();
        // Iterate to fill with mines and empty cells
        for (int boardIndex = 0; boardIndex < (rows * cols); boardIndex++) {
            board.add(minePositions.contains(boardIndex) ?
                    new Cell(MINE, CellStatus.COVERED, boardIndex) :
                    new Cell(EMPTY, CellStatus.COVERED, boardIndex)
            );
        }
        // Iterate again to fill with numbers next to mines
        for (int boardIndex = 0; boardIndex < (rows * cols); boardIndex++) {
            List<Integer> surroundings = board.get(boardIndex).getValue().equalsIgnoreCase(MINE) ?
                    getSurroundingPositionsToFill(boardIndex) : Collections.emptyList();
            surroundings.stream()
                    .peek(position ->
                            board.get(position).setValue(
                                    String.valueOf(Integer.parseInt(board.get(position).getValue()) + 1)
                            ));

        }
        return board;
    }

    private List<Integer> getSurroundingPositionsToFill(int boardIndex) {
        // TODO: Implement!!!
        return List.of();
    }

    private Set<Integer> getMinePositionsInBoard(final Integer rows, final Integer cols, final Integer mines) {
        return randomSupplier.get().ints(mines, 0, rows * cols).boxed().collect(Collectors.toSet());
    }

    private String generateNewGameId() {
        return uuidSupplier.get().toString();
    }
}
