package com.minesweeper.api.application.service;

import com.minesweeper.api.application.port.in.MakeAMoveCommand;
import com.minesweeper.api.application.port.in.MakeAMoveUseCase;
import com.minesweeper.api.domain.Game;
import com.minesweeper.api.domain.GameAlreadyFinishedError;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MakeAMoveService implements MakeAMoveUseCase {
    @Override
    public Mono<Game> makeAMove(MakeAMoveCommand command) {
        return Mono.error(GameAlreadyFinishedError::new);
    }
}
