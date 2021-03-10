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
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

@Service
@AllArgsConstructor
public class StartNewGameService implements StartNewGameUseCase {

    private final Supplier<UUID> uuidSupplier;
    private final PersistGamePort persistGamePort;
    private final Clock clock;

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

    private List<Cell> buildBoard(Integer rows, Integer cols, Integer mines) {
        String emptyValue = "0";
        return Collections.nCopies(rows * cols, new Cell(emptyValue, CellStatus.COVERED));
    }

    private String generateNewGameId() {
        return uuidSupplier.get().toString();
    }
}
