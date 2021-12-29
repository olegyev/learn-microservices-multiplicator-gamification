package learn.microservices.gamification.game.repository;

import learn.microservices.gamification.game.entity.ScoreCard;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ScoreRepository extends MongoRepository<ScoreCard, String> {

    Optional<Integer> getTotalScoreForUser(String userId);

    List<ScoreCard> findByUserIdOrderByTimestampDesc(String userId);

}