package com.minesweeper.api.application.port.in;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class StartNewGameCommand {
    private final String rows;
    private final String cols;
    private final String mines;
}
