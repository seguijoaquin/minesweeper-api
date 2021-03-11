package com.minesweeper.api.adapter.web;

import com.minesweeper.api.application.port.in.GetGameByIdUseCase;
import com.minesweeper.api.application.port.in.MakeAMoveCommand;
import com.minesweeper.api.application.port.in.MakeAMoveUseCase;
import com.minesweeper.api.application.port.in.StartNewGameCommand;
import com.minesweeper.api.application.port.in.StartNewGameUseCase;
import com.minesweeper.api.domain.Action;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public Mono<ResponseEntity<?>> startNewGame(@RequestBody GameRequest gameRequest) {
        return startNewGameUseCase.startNewGame(
                StartNewGameCommand.builder()
                        .rows(gameRequest.getRows())
                        .cols(gameRequest.getCols())
                        .mines(gameRequest.getMines())
                        .build()
        ).map(game -> ResponseEntity.status(HttpStatus.CREATED).body(game));
    }

    @GetMapping("/{gameId}")
    public Mono<ResponseEntity<?>> getGameById(@PathVariable final String gameId) {
        return getGameByIdUseCase.getGameById(gameId)
                .map(game -> ResponseEntity.status(HttpStatus.OK).body(game));
    }

    @PutMapping("/{gameId}")
    public Mono<ResponseEntity<?>> makeAMove(@PathVariable final String gameId, @RequestBody MoveRequest moveRequest) {
        return makeAMoveUseCase.makeAMove(
                MakeAMoveCommand.builder()
                        .gameId(gameId)
                        .row(moveRequest.getRow())
                        .col(moveRequest.getCol())
                        .action(Action.valueOf(moveRequest.getAction()))
                        .build()
        ).map(updatedGame -> ResponseEntity.status(HttpStatus.OK).body(updatedGame));
    }
}

