package learn.microservices.gamification.game.repository;

import learn.microservices.gamification.game.entity.LeaderBoardRow;
import learn.microservices.gamification.game.entity.ScoreCard;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ScoreRepository extends MongoRepository<ScoreCard, String> {

    /**
     * @return the total score for a given user (the sum of the scores of all their ScoreCards),
     * empty if user doesn't exist.
     */
    @Aggregation(pipeline = {"{$match: {userId: '?0'}}", "{$group: {_id: '$userId', totalScore: {$sum: '$score'}}}"})
    AggregationResults<UserTotalScore> sumTotalScoreForUser(String userId);

    /**
     * Retrieves a list of {@link LeaderBoardRow}s representing the Leader Board
     * of users and their total score.
     *
     * @return the leader board, sorted by highest score first.
     */
    @Aggregation(pipeline = {
            "{$group: {_id: '$userId', totalScore: {$sum: '$score'}}}",
            "{$sort: {totalScore : -1}}",
            "{$limit: 10}"
    })
    AggregationResults<LeaderBoardRow> findFirst10();

    List<ScoreCard> findByUserIdOrderByTimestampDesc(String userId);

    @Data
    class UserTotalScore {
        @NonNull
        private Integer totalScore;
    }

}