package learn.microservices.gamification.game.badgeprocessor.impl;

import learn.microservices.gamification.game.enumeration.BadgeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;

class GoldBadgeProcessorTest {

    private GoldBadgeProcessor goldBadgeProcessor;

    @BeforeEach
    public void setUp() {
        goldBadgeProcessor = new GoldBadgeProcessor();
    }

    @Test
    void whenCurrentScoreIsGreaterThan400_thenReturnGoldBadge() {
        // given
        int currentScore = 401;

        // when
        Optional<BadgeType> returnedBadge = goldBadgeProcessor.processForOptionalBadge(currentScore, List.of(), null);

        // then
        then(returnedBadge.isPresent()).isTrue();
        then(returnedBadge.get()).isEqualTo(BadgeType.GOLD);
    }

    @Test
    void whenCurrentScoreIsLowerThanOrEqualTo400_thenReturnEmptyResult() {
        // given
        int currentScore = 400;

        // when
        Optional<BadgeType> returnedBadge = goldBadgeProcessor.processForOptionalBadge(currentScore, List.of(), null);

        // then
        then(returnedBadge.isEmpty()).isTrue();
    }

    @Test
    void whenGetBadge_thenReturnGoldBadge() {
        // when
        BadgeType returnedBadge = goldBadgeProcessor.badgeType();

        // then
        then(returnedBadge).isEqualTo(BadgeType.GOLD);
    }

}