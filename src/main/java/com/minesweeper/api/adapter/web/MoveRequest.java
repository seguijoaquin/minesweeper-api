package com.minesweeper.api.adapter.web;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MoveRequest{
    private final String row;
    private final String col;
    private final String action;
}