package com.minesweeper.api.application.port.out;

import com.minesweeper.api.domain.Game;
import reactor.core.publisher.Mono;

public interface GetGameByIdPort {
    Mono<Game> getGameById(String gameId);
}
