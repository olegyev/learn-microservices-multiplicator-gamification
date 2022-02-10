package learn.microservices.gamification.game.service;

import learn.microservices.gamification.game.dto.ChallengeSolvedEvent;
import learn.microservices.gamification.game.enumeration.BadgeType;
import lombok.Value;

import java.util.List;

public interface GameService {

    /**
     * Process a new attempt from a given user.
     *
     * @param challenge the challenge data with the user details, factors, etc.
     * @return a {@link GameResult} object containing the new score and badge cards obtained
     */
    GameResult newAttemptForUser(ChallengeSolvedEvent challenge);

    @Value
    class GameResult {
        int score;
        List<BadgeType> badges;
    }

}