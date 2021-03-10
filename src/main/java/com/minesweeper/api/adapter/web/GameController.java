package com.minesweeper.api.adapter.web;

import com.minesweeper.api.application.port.in.GetGameByIdUseCase;
import com.minesweeper.api.application.port.in.MakeAMoveUseCase;
import com.minesweeper.api.application.port.in.StartNewGameUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/minesweeper/api/game")
public class GameController {

    private final StartNewGameUseCase startNewGameUseCase;
    private final GetGameByIdUseCase getGameByIdUseCase;
    private final MakeAMoveUseCase makeAMoveUseCase;

    @PostMapping
    public Mono<ResponseEntity<?>> startNewGame() {
        return Mono.empty();
    }

    @GetMapping("/{gameId}")
    public Mono<ResponseEntity<?>> getGameById(@PathVariable final String gameId) {
        return Mono.empty();
    }

    @PutMapping("/{gameId}")
    public Mono<ResponseEntity<?>> makeAMove(@PathVariable final String gameId) {
        return Mono.empty();
    }
}

