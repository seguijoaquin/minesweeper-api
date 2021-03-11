package com.minesweeper.api.application.port.in;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class StartNewGameCommand {
    private final String rows;
    private final String cols;
    private final String mines;
}
