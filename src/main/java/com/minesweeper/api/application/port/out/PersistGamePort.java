package com.minesweeper.api.application.port.out;

import com.minesweeper.api.domain.Game;
import reactor.core.publisher.Mono;

public interface PersistGamePort {
    Mono<Boolean> saveGame(Game game);
}
