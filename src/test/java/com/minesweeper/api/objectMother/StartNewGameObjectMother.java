package com.minesweeper.api.objectMother;

import com.minesweeper.api.adapter.web.GameRequest;
import com.minesweeper.api.application.port.in.StartNewGameCommand;
import com.minesweeper.api.domain.Game;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StartNewGameObjectMother {
    public static StartNewGameCommand newGameCommand() {
        return StartNewGameCommand.builder()
                .rows(GameObjectMother.getGame().getRows())
                .cols(GameObjectMother.getGame().getCols())
                .mines(GameObjectMother.getGame().getMines())
                .user(GameObjectMother.getGame().getUser())
                .build();
    }

    public static GameRequest newGameRequest() {
        Game game = GameObjectMother.getGame();
        return new GameRequest(game.getRows(), game.getCols(), game.getMines());
    }
}
