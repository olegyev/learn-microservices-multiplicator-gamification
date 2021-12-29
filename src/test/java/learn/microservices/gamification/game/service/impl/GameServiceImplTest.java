package learn.microservices.gamification.game.service.impl;

import learn.microservices.gamification.game.dto.ChallengeSolvedDto;
import learn.microservices.gamification.game.entity.ScoreCard;
import learn.microservices.gamification.game.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class GameServiceImplTest {

    private GameService gameService;

    @BeforeEach
    public void setUp() {
        gameService = new GameServiceImpl();
    }

    @Test
    public void whenNewAttemptForUserIsCorrect_thenScoreIsEqualsToTen() {
        // given
        ChallengeSolvedDto dto = new ChallengeSolvedDto(
                "1",
                true,
                50,
                60,
                "1",
                "john_doe");
        // when
        GameService.GameResult result = gameService.newAttemptForUser(dto);
        // then
        then(result.getScore()).isEqualTo(ScoreCard.DEFAULT_SCORE);
    }

    @Test
    public void whenNewAttemptForUserIsWrong_thenScoreIsEqualsToZero() {
        // given
        ChallengeSolvedDto dto = new ChallengeSolvedDto(
                1L,
                false,
                50,
                60,
                1L,
                "john_doe");
        // when
        GameService.GameResult result = gameService.newAttemptForUser(dto);
        // then
        then(result.getScore()).isEqualTo(0);
    }

}