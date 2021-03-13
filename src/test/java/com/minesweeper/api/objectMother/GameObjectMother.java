package com.minesweeper.api.objectMother;

import com.minesweeper.api.domain.Cell;
import com.minesweeper.api.domain.CellStatus;
import com.minesweeper.api.domain.Game;
import com.minesweeper.api.domain.GameStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GameObjectMother {

    private static final String ID = "bc921e17-57ff-44d7-b42f-1f01718b37b0";
    private static final String ROWS = "3";
    private static final String COLS = "3";
    private static final String MINES = "2";
    private static final String MINE = "M";
    private static final String EMPTY = "0";
    private static final String USER = "user";

    public static Game getGame() {
        return Game.builder()
                .id(ID)
                .rows(ROWS)
                .cols(COLS)
                .mines(MINES)
                .startedAt("2021-03-10T05:39:56.936214Z")
                .status(GameStatus.PLAYING)
                .board(getBoard())
                .user(USER)
                .build();
    }

    public static Game getFinishedGame() {
        //return getGame().toBuilder().status(GameStatus.WON).build();
        return Game.builder().id("123").status(GameStatus.WON).build();
    }

    public static List<Cell> getBoard() {
        return List.of(
                new Cell(EMPTY, CellStatus.COVERED, 0),
                new Cell(MINE, CellStatus.COVERED, 1),
                new Cell(EMPTY, CellStatus.COVERED, 2),
                new Cell(EMPTY, CellStatus.COVERED, 3),
                new Cell(EMPTY, CellStatus.COVERED, 4),
                new Cell(EMPTY, CellStatus.COVERED, 5),
                new Cell(EMPTY, CellStatus.COVERED, 6),
                new Cell(EMPTY, CellStatus.COVERED, 7),
                new Cell(MINE, CellStatus.COVERED, 8)
        );
    }

}
