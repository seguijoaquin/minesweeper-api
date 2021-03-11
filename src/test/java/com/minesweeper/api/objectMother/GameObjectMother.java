package com.minesweeper.api.objectMother;

import com.minesweeper.api.domain.Game;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GameObjectMother {

    private static final String ID = "bc921e17-57ff-44d7-b42f-1f01718b37b0";

    public static Game getGame() {
        return Game.builder()
                .id(ID)
                .build();
    }
}
