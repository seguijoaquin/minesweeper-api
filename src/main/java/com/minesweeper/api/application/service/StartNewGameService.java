package com.minesweeper.api.application.service;

import com.minesweeper.api.application.port.in.StartNewGameCommand;
import com.minesweeper.api.application.port.in.StartNewGameUseCase;
import com.minesweeper.api.application.port.out.PersistGamePort;
import com.minesweeper.api.domain.Game;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.function.Supplier;

@Service
@AllArgsConstructor
public class StartNewGameService implements StartNewGameUseCase {

    private final Supplier<UUID> uuidSupplier;
    private final PersistGamePort persistGamePort;

    @Override
    public Mono<Game> startNewGame(StartNewGameCommand command) {
        Game game = Game.builder()
                .id(generateNewGameId())
                .rows(command.getRows())
                .cols(command.getCols())
                .mines(command.getMines())
                .build();
        return persistGamePort.saveGame(game).thenReturn(game);
    }

    private String generateNewGameId() {
        return uuidSupplier.get().toString();
    }
}
