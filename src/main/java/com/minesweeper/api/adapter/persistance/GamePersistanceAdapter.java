package com.minesweeper.api.adapter.persistance;

import com.minesweeper.api.application.port.out.FindGameByIdPort;
import com.minesweeper.api.application.port.out.PersistGamePort;
import org.springframework.stereotype.Component;

@Component
class GamePersistanceAdapter implements PersistGamePort, FindGameByIdPort {
}
