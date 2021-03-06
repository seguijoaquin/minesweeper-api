package com.minesweeper.api.application.port.in;

import com.minesweeper.api.domain.Game;
import reactor.core.publisher.Mono;

public interface GetGameByIdUseCase {
    Mono<Game> getGameById(String gameId);
}
