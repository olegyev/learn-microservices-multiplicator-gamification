package learn.microservices.gamification.game.badgeprocessor.impl;

import learn.microservices.gamification.game.badgeprocessor.BadgeProcessor;
import learn.microservices.gamification.game.dto.ChallengeSolvedDto;
import learn.microservices.gamification.game.entity.ScoreCard;
import learn.microservices.gamification.game.enumeration.BadgeType;

import java.util.List;
import java.util.Optional;

public class LuckyNumberBadgeProcessor implements BadgeProcessor {

    private final static int LUCKY_FACTOR = 42;

    @Override
    public Optional<BadgeType> processForOptionalBadge(final int currentScore, final List<ScoreCard> scoreCards, final ChallengeSolvedDto solved) {
        return (solved.getFactorA() == LUCKY_FACTOR || solved.getFactorB() == LUCKY_FACTOR) ? Optional.of(BadgeType.LUCKY_NUMBER) : Optional.empty();
    }

    @Override
    public BadgeType badgeType() {
        return BadgeType.LUCKY_NUMBER;
    }

}