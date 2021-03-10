package com.minesweeper.api.adapter.persistance;

import com.minesweeper.api.application.port.out.GetGameByIdPort;
import com.minesweeper.api.application.port.out.PersistGamePort;
import com.minesweeper.api.domain.Game;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
class GamePersistenceAdapter implements PersistGamePort, GetGameByIdPort {

    private final ReactiveRedisTemplate<String, Game> redisTemplate;

    @Override
    public Mono<Boolean> saveGame(Game game) {
        return redisTemplate.opsForValue().set(game.getId(), game);
    }

    @Override
    public Mono<Game> getGameById(String gameId) {
        return redisTemplate.opsForValue().get(gameId);
    }
}
