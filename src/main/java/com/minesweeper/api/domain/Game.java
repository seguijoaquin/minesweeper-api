package com.minesweeper.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Game implements Serializable {
    private String id;
    private String rows;
    private String cols;
    private String mines;
    @JsonProperty("started_at")
    private String startedAt;
    @JsonProperty("updated_at")
    private String updatedAt;
    private GameStatus status;
    private List<Cell> board;
    private String user;
    private Set<Integer> minesIndexes;
    private Set<Integer> revealedIndexes;
    private Set<Integer> redFlagIndexes;
    private Set<Integer> questionFlagIndexes;

    public boolean hasFinished() {
        return !GameStatus.PLAYING.equals(this.getStatus());
    }

    public boolean gameWon() {
        return redFlagIndexes.equals(minesIndexes) &&
                questionFlagIndexes.isEmpty() &&
                Objects.equals(revealedIndexes.size(), board.size() - minesIndexes.size());
    }

}
