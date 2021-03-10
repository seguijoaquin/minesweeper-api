package com.minesweeper.api.application.service;

import com.minesweeper.api.application.port.in.GetGameByIdUseCase;
import com.minesweeper.api.application.port.out.GetGameByIdPort;
import com.minesweeper.api.domain.Game;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class GetGameByIdService implements GetGameByIdUseCase {

    private final GetGameByIdPort getGameByIdPort;

    @Override
    public Mono<Game> getGameById(String gameId) {
        return getGameByIdPort.getGameById(gameId);
    }
}
