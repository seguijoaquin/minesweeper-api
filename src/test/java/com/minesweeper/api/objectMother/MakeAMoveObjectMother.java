package com.minesweeper.api.objectMother;

import com.minesweeper.api.adapter.web.MoveRequest;
import com.minesweeper.api.application.port.in.MakeAMoveCommand;
import com.minesweeper.api.domain.Action;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MakeAMoveObjectMother {
    private static final String ROW = "1";
    private static final String COLUMN = "2";

    public static MakeAMoveCommand revealCommand() {
        return MakeAMoveCommand.builder()
                .action(Action.REVEAL)
                .col(COLUMN)
                .row(ROW)
                .gameId(GameObjectMother.getGame().getId())
                .build();
    }

    public static MoveRequest revealRequest() {
        return new MoveRequest(ROW, COLUMN, Action.REVEAL.name());
    }
}
