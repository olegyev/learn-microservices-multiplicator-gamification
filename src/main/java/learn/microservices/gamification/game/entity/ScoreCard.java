package learn.microservices.gamification.game.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This class represents the Score linked to an attempt in the game,
 * with an associated user and the timestamp in which the score is registered.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("scores")
public class ScoreCard {

    // The default score assigned to this card, if not specified.
    public static final int DEFAULT_SCORE = 10;

    @Id
    private String id;

    private String userId;

    private String attemptId;

    @EqualsAndHashCode.Exclude
    private long timestamp;

    private int score;

    public ScoreCard(final String userId, final String attemptId) {
        this(null, userId, attemptId, System.currentTimeMillis(), DEFAULT_SCORE);
    }

}