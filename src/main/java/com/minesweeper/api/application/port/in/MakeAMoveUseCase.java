package com.minesweeper.api.application.port.in;

import com.minesweeper.api.domain.Game;
import reactor.core.publisher.Mono;

public interface MakeAMoveUseCase {
    Mono<Game> makeAMove(MakeAMoveCommand command);
}
