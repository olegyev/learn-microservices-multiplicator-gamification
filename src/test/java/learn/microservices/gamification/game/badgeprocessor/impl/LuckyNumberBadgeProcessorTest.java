package learn.microservices.gamification.game.badgeprocessor.impl;

import learn.microservices.gamification.game.dto.ChallengeSolvedEvent;
import learn.microservices.gamification.game.enumeration.BadgeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;

class LuckyNumberBadgeProcessorTest {

    private LuckyNumberBadgeProcessor luckyNumberBadgeProcessor;

    @BeforeEach
    public void setUp() {
        luckyNumberBadgeProcessor = new LuckyNumberBadgeProcessor();
    }

    @Test
    void whenSolvedChallengeOneOfFactorsIsEqualTo42_thenReturnLuckyNumberBadge() {
        // given
        int luckyNumber = LuckyNumberBadgeProcessor.LUCKY_FACTOR;
        ChallengeSolvedEvent luckyDto = new ChallengeSolvedEvent("1", true, luckyNumber, 20, "1", "john_doe");

        // when
        Optional<BadgeType> returnedBadge = luckyNumberBadgeProcessor.processForOptionalBadge(10, List.of(), luckyDto);

        // then
        then(returnedBadge.isPresent()).isTrue();
        then(returnedBadge.get()).isEqualTo(BadgeType.LUCKY_NUMBER);
    }

    @Test
    void whenSolvedChallengeBothFactorsAreNotEqualTo42_thenReturnEmptyResult() {
        // given
        ChallengeSolvedEvent unluckyDto = new ChallengeSolvedEvent("1", true, 10, 20, "1", "john_doe");

        // when
        Optional<BadgeType> returnedBadge = luckyNumberBadgeProcessor.processForOptionalBadge(20, List.of(), unluckyDto);

        // then
        then(returnedBadge.isEmpty()).isTrue();
    }

    @Test
    void whenSolvedChallengeIsNull_thenReturnEmptyResult() {
        // given
        ChallengeSolvedEvent nullDto = null;

        // when
        Optional<BadgeType> returnedBadge = luckyNumberBadgeProcessor.processForOptionalBadge(20, List.of(), nullDto);

        // then
        then(returnedBadge.isEmpty()).isTrue();
    }

    @Test
    void whenGetBadge_thenReturnLuckyNumberBadge() {
        // when
        BadgeType returnedBadge = luckyNumberBadgeProcessor.badgeType();

        // then
        then(returnedBadge).isEqualTo(BadgeType.LUCKY_NUMBER);
    }

}