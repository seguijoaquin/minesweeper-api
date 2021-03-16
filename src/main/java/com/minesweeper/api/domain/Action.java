package com.minesweeper.api.domain;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Action {
    REVEAL {
        @Override
        protected Game execute(Game game, Integer index) {
            // TODO: implement
            return game;
        }
    }, RED_FLAG {
        @Override
        protected Game execute(Game game, Integer index) {
            Set<Integer> updatedredFlagIndexes = Stream.concat(
                    Stream.of(index),
                    game.getRedFlagIndexes().stream()
            ).collect(Collectors.toSet());

            Set<Integer> updatedredQuestionFlagIndexes = game.getQuestionFlagIndexes().stream()
                    .filter(e -> !e.equals(index))
                    .collect(Collectors.toSet());

            Game updatedGame = game.toBuilder()
                    .redFlagIndexes(updatedredFlagIndexes)
                    .questionFlagIndexes(updatedredQuestionFlagIndexes)
                    .build();

            return updatedGame.gameWon() ? updatedGame.toBuilder().status(GameStatus.WON).build() : updatedGame;
        }
    }, UNKNOWN_FLAG {
        @Override
        protected Game execute(Game game, Integer index) {
            Set<Integer> updatedredQuestionFlagIndexes = Stream.concat(
                    Stream.of(index),
                    game.getQuestionFlagIndexes().stream()
            ).collect(Collectors.toSet());

            Set<Integer> updatedRedFlagIndexes = game.getRedFlagIndexes().stream()
                    .filter(e -> !e.equals(index))
                    .collect(Collectors.toSet());

            return game.toBuilder()
                    .redFlagIndexes(updatedRedFlagIndexes)
                    .questionFlagIndexes(updatedredQuestionFlagIndexes)
                    .build();
        }
    }, UNFLAG {
        @Override
        protected Game execute(Game game, Integer index) {
            Set<Integer> updatedredQuestionFlagIndexes = game.getQuestionFlagIndexes().stream()
                    .filter(e -> !e.equals(index))
                    .collect(Collectors.toSet());

            Set<Integer> updatedRedFlagIndexes = game.getRedFlagIndexes().stream()
                    .filter(e -> !e.equals(index))
                    .collect(Collectors.toSet());

            return game.toBuilder()
                    .redFlagIndexes(updatedRedFlagIndexes)
                    .questionFlagIndexes(updatedredQuestionFlagIndexes)
                    .build();
        }
    };

    public Game act(Game game, Integer index) {
        // If the cell is already revealed we don't do anything
        if (game.getRevealedIndexes().contains(index)) {
            return game;
        }
        return this.execute(game, index);
    }

    protected abstract Game execute(Game game, Integer index);
}
