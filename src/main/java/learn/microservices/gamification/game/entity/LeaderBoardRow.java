package learn.microservices.gamification.game.entity;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.With;

import java.util.List;

@Value
@AllArgsConstructor
public class LeaderBoardRow {

    String userId;

    Long totalScore;

    @With
    List<String> badges;

    public LeaderBoardRow(String userId, Long totalScore) {
        this.userId = userId;
        this.totalScore = totalScore;
        this.badges = List.of();
    }

}