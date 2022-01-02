package learn.microservices.gamification.game.repository;

import learn.microservices.gamification.game.entity.LeaderBoardRow;
import learn.microservices.gamification.game.entity.ScoreCard;
import lombok.Data;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ScoreRepository extends MongoRepository<ScoreCard, String> {

    /**
     * @return the total score for a given user (the sum of the scores of all their ScoreCards),
     * empty if user doesn't exist
     */
    @Aggregation(pipeline = {"{$match: {userId: '?0'}}", "{$group: {_id: 'userTotalScore', totalScore: {$sum: '$score'}}}"})
    AggregationResults<UserTotalScore> sumTotalScoreForUser(String userId);

    List<ScoreCard> findByUserIdOrderByTimestampDesc(String userId);

    List<LeaderBoardRow> findFirst10();

    @Data
    class UserTotalScore {
        private Integer totalScore;
    }

}