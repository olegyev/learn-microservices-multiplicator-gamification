package learn.microservices.gamification.game.repository;

import learn.microservices.gamification.game.entity.BadgeCard;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BadgeRepository extends MongoRepository<BadgeCard, String> {

    /**
     * @return the list of BadgeCards for a user, ordered by most recent first
     */
    List<BadgeCard> findByUserIdOrderByTimestampDesc(String userId);

}