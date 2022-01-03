package learn.microservices.gamification.game.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor // is needed for MongoDB mapping (see ScoreRepository#findFirst10 query)
@AllArgsConstructor
public final class LeaderBoardRow {

    @Field("_id")
    private String userId;

    private Long totalScore;

    @With
    private List<String> badges = List.of();

    public LeaderBoardRow(String userId, Long totalScore) {
        this.userId = userId;
        this.totalScore = totalScore;
    }

}