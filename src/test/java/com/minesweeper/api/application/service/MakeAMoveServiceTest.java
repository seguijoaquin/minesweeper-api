package com.minesweeper.api.application.service;

import com.minesweeper.api.application.port.in.MakeAMoveCommand;
import com.minesweeper.api.domain.Game;
import com.minesweeper.api.objectMother.MakeAMoveObjectMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class MakeAMoveServiceTest {

    private MakeAMoveService makeAMoveService;

    @BeforeEach
    void setUp() {
        makeAMoveService = new MakeAMoveService();
    }

    @Test
    void makeAMove() {
        // GIVEN
        Game game = Game.builder().id("123").rows("1").cols("2").mines("3").build();
        MakeAMoveCommand command = MakeAMoveObjectMother.revealCommand();

        // WHEN
        StepVerifier.create(makeAMoveService.makeAMove(command))
                // THEN
                .expectNext(game).verifyComplete();
    }
}