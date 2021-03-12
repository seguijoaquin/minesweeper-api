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
import java.util.stream.Stream;

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
        Game game = Game.builder()
                .id(generateNewGameId())
                .rows(command.getRows())
                .cols(command.getCols())
                .mines(command.getMines())
                .startedAt(clock.instant().toString())
                .status(GameStatus.PLAYING)
                .board(buildBoard(
                        Integer.valueOf(command.getRows()),
                        Integer.valueOf(command.getCols()),
                        Integer.valueOf(command.getMines()))
                )
                .build();
        return persistGamePort.saveGame(game).thenReturn(game);
    }

    private List<Cell> buildBoard(final Integer rows, final Integer cols, final Integer mines) {
        List<Cell> board = new ArrayList<>();
        Cell emptyCell = new Cell(EMPTY, CellStatus.COVERED);
        Cell minedCell = new Cell(MINE, CellStatus.COVERED);
        Set<Integer> minePositions = getMinePositionsInBoard(rows, cols, mines);
        for (int i = 0; i < (rows * cols); i++) {
            board.add(minePositions.contains(i) ? minedCell : emptyCell);
        }
        return board;
    }

    private Set<Integer> getMinePositionsInBoard(final Integer rows, final Integer cols, final Integer mines) {
        return randomSupplier.get().ints(mines, 0, rows * cols).boxed().collect(Collectors.toSet());
    }

    private String generateNewGameId() {
        return uuidSupplier.get().toString();
    }
}
