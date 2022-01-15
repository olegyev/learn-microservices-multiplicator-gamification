package learn.microservices.gamification.game.badgeprocessor.impl;

import learn.microservices.gamification.game.entity.ScoreCard;
import learn.microservices.gamification.game.enumeration.BadgeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;

class FirstWonBadgeProcessorTest {

    private FirstWonBadgeProcessor firstWonBadgeProcessor;

    @BeforeEach
    public void setUp() {
        firstWonBadgeProcessor = new FirstWonBadgeProcessor();
    }

    @Test
    void whenScoreCardsSizeIsEqualTo1_thenReturnFirstWonBadge() {
        // given
        List<ScoreCard> scoreCards = List.of(new ScoreCard("1", "1"));

        // when
        Optional<BadgeType> returnedBadge = firstWonBadgeProcessor.processForOptionalBadge(10, scoreCards, null);

        // then
        then(returnedBadge.isPresent()).isTrue();
        then(returnedBadge.get()).isEqualTo(BadgeType.FIRST_WON);
    }

    @Test
    void whenScoreCardsSizeIsNotEqualTo1_thenReturnEmptyResult() {
        // given
        List<ScoreCard> scoreCards = List.of(
                new ScoreCard("1", "1"),
                new ScoreCard("2", "2")
        );

        // when
        Optional<BadgeType> returnedBadge = firstWonBadgeProcessor.processForOptionalBadge(20, scoreCards, null);

        // then
        then(returnedBadge.isEmpty()).isTrue();
    }

    @Test
    void whenScoreCardsListIsNull_thenReturnEmptyResult() {
        // given
        List<ScoreCard> scoreCards = null;

        // when
        Optional<BadgeType> returnedBadge = firstWonBadgeProcessor.processForOptionalBadge(20, scoreCards, null);

        // then
        then(returnedBadge.isEmpty()).isTrue();
    }

    @Test
    void whenGetBadge_thenReturnFirstWonBadge() {
        // when
        BadgeType returnedBadge = firstWonBadgeProcessor.badgeType();

        // then
        then(returnedBadge).isEqualTo(BadgeType.FIRST_WON);
    }

}