package com.minesweeper.api.application.service;

import com.minesweeper.api.application.port.in.MakeAMoveCommand;
import com.minesweeper.api.application.port.in.MakeAMoveUseCase;
import com.minesweeper.api.domain.Game;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MakeAMoveService implements MakeAMoveUseCase {
    @Override
    public Mono<Game> makeAMove(MakeAMoveCommand command) {
        return Mono.just(Game.builder()
                .id("123")
                .rows("1")
                .cols("2")
                .mines("3")
                .build());
    }
}
