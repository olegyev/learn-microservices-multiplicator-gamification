package learn.microservices.gamification.game.badgeprocessor.impl;

import learn.microservices.gamification.game.enumeration.BadgeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;

class SilverBadgeProcessorTest {

    private SilverBadgeProcessor silverBadgeProcessor;

    @BeforeEach
    public void setUp() {
        silverBadgeProcessor = new SilverBadgeProcessor();
    }

    @Test
    void whenCurrentScoreIsGreaterThan150_thenReturnSilverBadge() {
        // given
        int currentScore = 151;

        // when
        Optional<BadgeType> returnedBadge = silverBadgeProcessor.processForOptionalBadge(currentScore, List.of(), null);

        // then
        then(returnedBadge.isPresent()).isTrue();
        then(returnedBadge.get()).isEqualTo(BadgeType.SILVER);
    }

    @Test
    void whenCurrentScoreIsLowerThanOrEqualTo150_thenReturnEmptyResult() {
        // given
        int currentScore = 150;

        // when
        Optional<BadgeType> returnedBadge = silverBadgeProcessor.processForOptionalBadge(currentScore, List.of(), null);

        // then
        then(returnedBadge.isEmpty()).isTrue();
    }

    @Test
    void whenGetBadge_thenReturnSilverBadge() {
        // when
        BadgeType returnedBadge = silverBadgeProcessor.badgeType();

        // then
        then(returnedBadge).isEqualTo(BadgeType.SILVER);
    }

}