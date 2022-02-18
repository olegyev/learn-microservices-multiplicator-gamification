package learn.microservices.gamification.game.repository;

import learn.microservices.gamification.game.entity.LeaderBoardRow;
import learn.microservices.gamification.game.entity.ScoreCard;
import learn.microservices.gamification.game.repository.ScoreRepository.UserTotalScore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest
class ScoreRepositoryTest {

    @Autowired
    ScoreRepository scoreRepository;

    private final List<ScoreCard> user1ScoreCards = new ArrayList<>(3);
    private final List<ScoreCard> user2ScoreCards = new ArrayList<>(3);

    @BeforeEach
    public void setUp() {
        // Using predefined userId and attemptId in form of integers in the tests is justified,
        // because normally they are MongoDB's ObjectId, so that there will be no coincidences
        // between test and actual documents.

        user1ScoreCards.add(new ScoreCard(null, "1", "1", System.currentTimeMillis(), 10));
        user1ScoreCards.add(new ScoreCard(null, "1", "2", System.currentTimeMillis() + 10, 20));
        user1ScoreCards.add(new ScoreCard(null, "1", "3", System.currentTimeMillis() + 20, 30));

        user1ScoreCards.add(new ScoreCard(null, "2", "4", System.currentTimeMillis(), 100));
        user1ScoreCards.add(new ScoreCard(null, "2", "5", System.currentTimeMillis() + 10, 200));
        user1ScoreCards.add(new ScoreCard(null, "2", "6", System.currentTimeMillis() + 20, 300));

        scoreRepository.saveAll(user1ScoreCards);
        scoreRepository.saveAll(user2ScoreCards);
    }

    @Test
    void whenGetTotalScoreForExistingUser_thenReturnCorrectTotalScore() {
        // given
        String userId = "1";

        // when
        AggregationResults<UserTotalScore> aggregationResult = scoreRepository.sumTotalScoreForUser(userId);

        // then
        Optional<Integer> userTotalScore = Optional.ofNullable(aggregationResult.getUniqueMappedResult())
                .map(UserTotalScore::getTotalScore);
        then(userTotalScore.isPresent()).isTrue();
        then(userTotalScore.get()).isEqualTo(60);
    }

    @Test
    void whenGetTotalScoreForNonExistingUser_thenReturnEmptyAggregationResults() {
        // given
        String userId = "3"; // non-existing user

        // when
        AggregationResults<UserTotalScore> aggregationResult = scoreRepository.sumTotalScoreForUser(userId);

        // then
        then(aggregationResult.getUniqueMappedResult()).isNull();
        Optional<Integer> userTotalScore = Optional.ofNullable(aggregationResult.getUniqueMappedResult())
                .map(UserTotalScore::getTotalScore);
        then(userTotalScore.isEmpty()).isTrue();
    }

    @Test
    void whenFindFirst10_thenReturnCorrectLeaderBoard() {
        // given
        // Objects user1ScoreCards and user2ScoreCards stored in DB.

        // when
        AggregationResults<LeaderBoardRow> aggregationResults = scoreRepository.findFirst10();

        // then
        List<LeaderBoardRow> leaderBoard = aggregationResults.getMappedResults();
        then(leaderBoard.size()).isEqualTo(2);
        then(leaderBoard.get(0)).isEqualTo(new LeaderBoardRow("2", 600L));
        then(leaderBoard.get(1)).isEqualTo(new LeaderBoardRow("1", 60L));
    }

    @Test
    void whenFindByUserIdOrderByTimestampDescForExistingUser_thenReturnCorrectListOfScoreCards() {
        // given
        String userId = "2";

        // when
        List<ScoreCard> scoreCards = scoreRepository.findByUserIdOrderByTimestampDesc(userId);

        // then
        then(scoreCards.size()).isEqualTo(3);

        ScoreCard firstScoreCard = scoreCards.get(0);
        then(firstScoreCard.getUserId()).isEqualTo("2");
        then(firstScoreCard.getAttemptId()).isEqualTo("6");
        then(firstScoreCard.getScore()).isEqualTo(300);

        ScoreCard secondScoreCard = scoreCards.get(1);
        then(secondScoreCard.getUserId()).isEqualTo("2");
        then(secondScoreCard.getAttemptId()).isEqualTo("5");
        then(secondScoreCard.getScore()).isEqualTo(200);

        ScoreCard thirdScoreCard = scoreCards.get(2);
        then(thirdScoreCard.getUserId()).isEqualTo("2");
        then(thirdScoreCard.getAttemptId()).isEqualTo("4");
        then(thirdScoreCard.getScore()).isEqualTo(100);
    }

    @Test
    void whenFindByUserIdOrderByTimestampDescForNonExistingUser_thenReturnEmptyList() {
        // given
        String userId = "3"; // non-existing user

        // when
        List<ScoreCard> scoreCards = scoreRepository.findByUserIdOrderByTimestampDesc(userId);

        // then
        then(scoreCards).isEmpty();
    }

    @Test
    void whenFindByUserIdOrderByTimestampDescForNull_thenReturnEmptyList() {
        // given
        String userId = null;

        // when
        List<ScoreCard> scoreCards = scoreRepository.findByUserIdOrderByTimestampDesc(userId);

        // then
        then(scoreCards).isEmpty();
    }

    @AfterEach
    public void tearDown() {
        scoreRepository.deleteAll(user1ScoreCards);
        scoreRepository.deleteAll(user2ScoreCards);
    }

}