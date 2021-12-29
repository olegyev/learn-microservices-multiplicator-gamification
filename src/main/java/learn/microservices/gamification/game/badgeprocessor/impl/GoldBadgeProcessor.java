package learn.microservices.gamification.game.badgeprocessor.impl;

import learn.microservices.gamification.game.badgeprocessor.BadgeProcessor;
import learn.microservices.gamification.game.dto.ChallengeSolvedDto;
import learn.microservices.gamification.game.entity.ScoreCard;
import learn.microservices.gamification.game.enumeration.BadgeType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GoldBadgeProcessor implements BadgeProcessor {

    @Override
    public Optional<BadgeType> processForOptionalBadge(final int currentScore, final List<ScoreCard> scoreCards, final ChallengeSolvedDto solved) {
        return currentScore > 400 ? Optional.of(BadgeType.GOLD) : Optional.empty();
    }

    @Override
    public BadgeType badgeType() {
        return BadgeType.GOLD;
    }

}