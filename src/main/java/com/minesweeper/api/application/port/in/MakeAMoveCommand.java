package com.minesweeper.api.application.port.in;

import com.minesweeper.api.domain.Action;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MakeAMoveCommand {
    private final String gameId;
    private final String row;
    private final String col;
    private final Action action;
}
