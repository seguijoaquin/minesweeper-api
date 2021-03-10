package com.minesweeper.api.adapter.web;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GameRequest {
    private final String rows;
    private final String cols;
    private final String mines;
}
