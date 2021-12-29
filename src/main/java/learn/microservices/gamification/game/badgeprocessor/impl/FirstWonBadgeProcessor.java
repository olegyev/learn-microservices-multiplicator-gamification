package learn.microservices.gamification.game.badgeprocessor.impl;

import learn.microservices.gamification.game.badgeprocessor.BadgeProcessor;
import learn.microservices.gamification.game.dto.ChallengeSolvedDto;
import learn.microservices.gamification.game.entity.ScoreCard;
import learn.microservices.gamification.game.enumeration.BadgeType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class FirstWonBadgeProcessor implements BadgeProcessor {

    @Override
    public Optional<BadgeType> processForOptionalBadge(final int currentScore, final List<ScoreCard> scoreCards, final ChallengeSolvedDto solved) {
        return scoreCards.size() == 1 ? Optional.of(BadgeType.FIRST_WON) : Optional.empty();
    }

    @Override
    public BadgeType badgeType() {
        return BadgeType.FIRST_WON;
    }

}
