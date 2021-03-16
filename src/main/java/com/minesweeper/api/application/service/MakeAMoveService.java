package com.minesweeper.api.application.service;

import com.minesweeper.api.application.port.in.MakeAMoveCommand;
import com.minesweeper.api.application.port.in.MakeAMoveUseCase;
import com.minesweeper.api.application.port.out.GetGameByIdPort;
import com.minesweeper.api.application.port.out.PersistGamePort;
import com.minesweeper.api.domain.Game;
import com.minesweeper.api.domain.GameAlreadyFinishedError;
import com.minesweeper.api.domain.GameNotFoundError;
import com.minesweeper.api.domain.MovementForbiddenError;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class MakeAMoveService implements MakeAMoveUseCase {

    private final GetGameByIdPort getGameByIdPort;
    private final PersistGamePort persistGamePort;

    @Override
    public Mono<Game> makeAMove(MakeAMoveCommand command) {
        return getGameByIdPort.getGameById(command.getGameId())
                .switchIfEmpty(Mono.error(GameNotFoundError::new))
                .filter(game -> game.getUser().equalsIgnoreCase(command.getUser()))
                .switchIfEmpty(Mono.error(MovementForbiddenError::new))
                .filter(game -> !game.hasFinished())
                .switchIfEmpty(Mono.error(GameAlreadyFinishedError::new))
                .flatMap(game -> processGameCommand(game, command));
    }

    private Mono<Game> processGameCommand(Game game, MakeAMoveCommand command) {
        Game updatedGame = game.toBuilder()
                .id(game.getId())
                .build();
        persistGamePort.saveGame(updatedGame);
        return Mono.just(updatedGame);
    }

}
