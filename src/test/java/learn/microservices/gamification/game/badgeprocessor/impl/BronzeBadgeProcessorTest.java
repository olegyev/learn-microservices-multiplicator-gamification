package learn.microservices.gamification.game.badgeprocessor.impl;

import learn.microservices.gamification.game.enumeration.BadgeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;

class BronzeBadgeProcessorTest {

    private BronzeBadgeProcessor bronzeBadgeProcessor;

    @BeforeEach
    public void setUp() {
        bronzeBadgeProcessor = new BronzeBadgeProcessor();
    }

    @Test
    void whenCurrentScoreIsGreaterThan50_thenReturnBronzeBadge() {
        // given
        int currentScore = 51;

        // when
        Optional<BadgeType> returnedBadge = bronzeBadgeProcessor.processForOptionalBadge(currentScore, List.of(), null);

        // then
        then(returnedBadge.isPresent()).isTrue();
        then(returnedBadge.get()).isEqualTo(BadgeType.BRONZE);
    }

    @Test
    void whenCurrentScoreIsLowerThanOrEqualTo50_thenReturnEmptyResult() {
        // given
        int currentScore = 50;

        // when
        Optional<BadgeType> returnedBadge = bronzeBadgeProcessor.processForOptionalBadge(currentScore, List.of(), null);

        // then
        then(returnedBadge.isEmpty()).isTrue();
    }

    @Test
    void whenGetBadge_thenReturnBronzeBadge() {
        // when
        BadgeType returnedBadge = bronzeBadgeProcessor.badgeType();

        // then
        then(returnedBadge).isEqualTo(BadgeType.BRONZE);
    }

}