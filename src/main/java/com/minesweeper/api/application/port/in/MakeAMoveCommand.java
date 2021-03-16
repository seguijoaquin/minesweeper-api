package com.minesweeper.api.application.port.in;

import com.minesweeper.api.domain.Action;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class MakeAMoveCommand {
    private final String gameId;
    private final String row;
    private final String col;
    private final Action action;
    private final String user;

    public Integer getBoardIndex() {
        return (Integer.parseInt(row) * Integer.parseInt(col)) - 1;
    }
}
