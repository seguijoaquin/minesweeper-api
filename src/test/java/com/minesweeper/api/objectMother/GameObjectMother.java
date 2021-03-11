package com.minesweeper.api.objectMother;

import com.minesweeper.api.domain.Cell;
import com.minesweeper.api.domain.CellStatus;
import com.minesweeper.api.domain.Game;
import com.minesweeper.api.domain.GameStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GameObjectMother {

    private static final String ID = "bc921e17-57ff-44d7-b42f-1f01718b37b0";
    private static final String ROWS = "3";
    private static final String COLS = "3";
    private static final String MINES = "2";

    public static Game getGame() {
        return Game.builder()
                .id(ID)
                .rows(ROWS)
                .cols(COLS)
                .mines(MINES)
                .startedAt("2021-03-10T05:39:56.936214Z")
                .status(GameStatus.PLAYING)
                .board(getBoard())
                .build();
    }

    public static List<Cell> getBoard() {
        return Collections.nCopies(Integer.parseInt(ROWS) * Integer.parseInt(COLS), new Cell("0", CellStatus.COVERED));
    }
}
