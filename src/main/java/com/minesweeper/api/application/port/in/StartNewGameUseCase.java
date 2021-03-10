package com.minesweeper.api.application.port.in;

import com.minesweeper.api.domain.Game;
import reactor.core.publisher.Mono;

public interface StartNewGameUseCase {
    Mono<Game> startNewGame(StartNewGameCommand command);
}
