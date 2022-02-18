package learn.microservices.gamification.game.badgeprocessor;

import learn.microservices.gamification.game.dto.ChallengeSolvedEvent;
import learn.microservices.gamification.game.entity.ScoreCard;
import learn.microservices.gamification.game.enumeration.BadgeType;

import java.util.List;
import java.util.Optional;

public interface BadgeProcessor {

    /**
     * Processes some or all of the passed parameters and decides if the user is entitled to a badge.
     *
     * @return a BadgeType if the user is entitled to a badge, otherwise empty.
     */
    Optional<BadgeType> processForOptionalBadge(int currentScore, List<ScoreCard> scoreCards, ChallengeSolvedEvent solved);

    /**
     * @return the BadgeType object that this processor is handling.
     * You can use it to filter processors according to your needs.
     */
    BadgeType badgeType();

}