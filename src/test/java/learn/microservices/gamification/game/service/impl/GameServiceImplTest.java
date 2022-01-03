package learn.microservices.gamification.game.service.impl;

import learn.microservices.gamification.game.badgeprocessor.BadgeProcessor;
import learn.microservices.gamification.game.badgeprocessor.impl.LuckyNumberBadgeProcessor;
import learn.microservices.gamification.game.dto.ChallengeSolvedDto;
import learn.microservices.gamification.game.entity.BadgeCard;
import learn.microservices.gamification.game.entity.ScoreCard;
import learn.microservices.gamification.game.enumeration.BadgeType;
import learn.microservices.gamification.game.repository.BadgeRepository;
import learn.microservices.gamification.game.repository.ScoreRepository;
import learn.microservices.gamification.game.repository.ScoreRepository.UserTotalScore;
import learn.microservices.gamification.game.service.GameService;
import learn.microservices.gamification.game.service.GameService.GameResult;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {

    private GameService gameService;

    @Mock
    private ScoreRepository scoreRepository;

    @Mock
    private BadgeRepository badgeRepository;

    @Mock
    BadgeProcessor badgeProcessor;

    @BeforeEach
    public void setUp() {
        gameService = new GameServiceImpl(scoreRepository, badgeRepository, List.of(badgeProcessor));
    }

    @Test
    public void whenNewAttemptForUserIsCorrect_thenScoreIsEqualToTenAndBadgeIsPresentInGameResult() {
        // given
        String userId = "1";
        String attemptId = "1";
        int luckyNumber = LuckyNumberBadgeProcessor.LUCKY_FACTOR;
        int currentScore = ScoreCard.DEFAULT_SCORE;
        ScoreCard scoreCard = new ScoreCard(userId, attemptId);
        BadgeCard badgeCard = new BadgeCard(userId, BadgeType.FIRST_WON);
        ChallengeSolvedDto dto = new ChallengeSolvedDto(
                attemptId,
                true,
                luckyNumber,
                luckyNumber,
                userId,
                "john_doe");

        given(scoreRepository.sumTotalScoreForUser(userId)).willReturn(new AggregationResults<>(List.of(new UserTotalScore(currentScore)), new Document()));
        given(scoreRepository.findByUserIdOrderByTimestampDesc(userId)).willReturn(List.of(scoreCard));
        given(badgeRepository.findByUserIdOrderByTimestampDesc(userId)).willReturn(List.of(badgeCard));
        given(badgeProcessor.badgeType()).willReturn(BadgeType.LUCKY_NUMBER);
        given(badgeProcessor.processForOptionalBadge(currentScore, List.of(scoreCard), dto)).willReturn(Optional.of(BadgeType.LUCKY_NUMBER));

        // when
        GameResult result = gameService.newAttemptForUser(dto);

        // then
        then(result).isEqualTo(new GameResult(10, List.of(BadgeType.LUCKY_NUMBER)));
        verify(scoreRepository).save(scoreCard);
        verify(badgeRepository).saveAll(List.of(new BadgeCard(userId, BadgeType.LUCKY_NUMBER)));
    }

    @Test
    public void whenNewAttemptForUserIsWrong_thenScoreIsEqualToZeroAndNoBadgesInGameResult() {
        // given
        ChallengeSolvedDto dto = new ChallengeSolvedDto("1", false, 50, 60, "1", "john_doe");
        // when
        GameService.GameResult result = gameService.newAttemptForUser(dto);
        // then
        then(result).isEqualTo(new GameResult(0, List.of()));
    }

}