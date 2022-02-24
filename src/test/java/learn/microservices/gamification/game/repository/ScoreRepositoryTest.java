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
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
class ScoreRepositoryTest {

    @Autowired
    ScoreRepository scoreRepository;

    private final List<ScoreCard> user1ScoreCards = new ArrayList<>(3);
    private final List<ScoreCard> user2ScoreCards = new ArrayList<>(3);
    private final List<ScoreCard> user3ScoreCards = new ArrayList<>(3);
    private final List<ScoreCard> user4ScoreCards = new ArrayList<>(3);
    private final List<ScoreCard> user5ScoreCards = new ArrayList<>(3);
    private final List<ScoreCard> user6ScoreCards = new ArrayList<>(3);
    private final List<ScoreCard> user7ScoreCards = new ArrayList<>(3);
    private final List<ScoreCard> user8ScoreCards = new ArrayList<>(3);
    private final List<ScoreCard> user9ScoreCards = new ArrayList<>(3);
    private final List<ScoreCard> user10ScoreCards = new ArrayList<>(3);
    private final List<ScoreCard> user11ScoreCards = new ArrayList<>(3);
    private final List<ScoreCard> user12ScoreCards = new ArrayList<>(3);

    @BeforeEach
    public void setUp() {
        user1ScoreCards.add(new ScoreCard(null, "1", "1", System.currentTimeMillis(), 8000));
        user1ScoreCards.add(new ScoreCard(null, "1", "2", System.currentTimeMillis() + 10, 9000));
        user1ScoreCards.add(new ScoreCard(null, "1", "3", System.currentTimeMillis() + 20, 10000));

        user2ScoreCards.add(new ScoreCard(null, "2", "4", System.currentTimeMillis(), 7000));
        user2ScoreCards.add(new ScoreCard(null, "2", "5", System.currentTimeMillis() + 10, 8000));
        user2ScoreCards.add(new ScoreCard(null, "2", "6", System.currentTimeMillis() + 20, 9000));

        user3ScoreCards.add(new ScoreCard(null, "3", "7", System.currentTimeMillis(), 6000));
        user3ScoreCards.add(new ScoreCard(null, "3", "8", System.currentTimeMillis() + 10, 7000));
        user3ScoreCards.add(new ScoreCard(null, "3", "9", System.currentTimeMillis() + 20, 8000));

        user4ScoreCards.add(new ScoreCard(null, "4", "10", System.currentTimeMillis(), 5000));
        user4ScoreCards.add(new ScoreCard(null, "4", "11", System.currentTimeMillis() + 10, 6000));
        user4ScoreCards.add(new ScoreCard(null, "4", "12", System.currentTimeMillis() + 20, 7000));

        user5ScoreCards.add(new ScoreCard(null, "5", "13", System.currentTimeMillis(), 4000));
        user5ScoreCards.add(new ScoreCard(null, "5", "14", System.currentTimeMillis() + 10, 5000));
        user5ScoreCards.add(new ScoreCard(null, "5", "15", System.currentTimeMillis() + 20, 6000));

        user6ScoreCards.add(new ScoreCard(null, "6", "16", System.currentTimeMillis(), 3000));
        user6ScoreCards.add(new ScoreCard(null, "6", "17", System.currentTimeMillis() + 10, 4000));
        user6ScoreCards.add(new ScoreCard(null, "6", "18", System.currentTimeMillis() + 20, 5000));

        user7ScoreCards.add(new ScoreCard(null, "7", "19", System.currentTimeMillis(), 2000));
        user7ScoreCards.add(new ScoreCard(null, "7", "20", System.currentTimeMillis() + 10, 3000));
        user7ScoreCards.add(new ScoreCard(null, "7", "21", System.currentTimeMillis() + 20, 4000));

        user8ScoreCards.add(new ScoreCard(null, "8", "22", System.currentTimeMillis(), 1000));
        user8ScoreCards.add(new ScoreCard(null, "8", "23", System.currentTimeMillis() + 10, 2000));
        user8ScoreCards.add(new ScoreCard(null, "8", "24", System.currentTimeMillis() + 20, 3000));

        user9ScoreCards.add(new ScoreCard(null, "9", "25", System.currentTimeMillis(), 900));
        user9ScoreCards.add(new ScoreCard(null, "9", "26", System.currentTimeMillis() + 10, 1000));
        user9ScoreCards.add(new ScoreCard(null, "9", "27", System.currentTimeMillis() + 20, 2000));

        user10ScoreCards.add(new ScoreCard(null, "10", "28", System.currentTimeMillis(), 800));
        user10ScoreCards.add(new ScoreCard(null, "10", "29", System.currentTimeMillis() + 10, 900));
        user10ScoreCards.add(new ScoreCard(null, "10", "30", System.currentTimeMillis() + 20, 1000));

        user11ScoreCards.add(new ScoreCard(null, "11", "31", System.currentTimeMillis(), 700));
        user11ScoreCards.add(new ScoreCard(null, "11", "32", System.currentTimeMillis() + 10, 800));
        user11ScoreCards.add(new ScoreCard(null, "11", "33", System.currentTimeMillis() + 20, 900));

        user12ScoreCards.add(new ScoreCard(null, "12", "34", System.currentTimeMillis(), 600));
        user12ScoreCards.add(new ScoreCard(null, "12", "35", System.currentTimeMillis() + 10, 700));
        user12ScoreCards.add(new ScoreCard(null, "12", "36", System.currentTimeMillis() + 20, 800));

        scoreRepository.saveAll(user1ScoreCards);
        scoreRepository.saveAll(user2ScoreCards);
        scoreRepository.saveAll(user3ScoreCards);
        scoreRepository.saveAll(user4ScoreCards);
        scoreRepository.saveAll(user5ScoreCards);
        scoreRepository.saveAll(user6ScoreCards);
        scoreRepository.saveAll(user7ScoreCards);
        scoreRepository.saveAll(user8ScoreCards);
        scoreRepository.saveAll(user9ScoreCards);
        scoreRepository.saveAll(user10ScoreCards);
        scoreRepository.saveAll(user11ScoreCards);
        scoreRepository.saveAll(user12ScoreCards);
    }

    @Test
    void whenGetTotalScoreForExistingUser_thenReturnCorrectTotalScore() {
        // given
        String userId = "1";

        // when
        AggregationResults<UserTotalScore> aggregationResult = scoreRepository.sumTotalScoreForUser(userId);

        // then
        Optional<Integer> userTotalScore = Optional
                .ofNullable(aggregationResult.getUniqueMappedResult())
                .map(UserTotalScore::getTotalScore);
        then(userTotalScore.isPresent()).isTrue();
        then(userTotalScore.get()).isEqualTo(8000L + 9000L + 10000L);
    }

    @Test
    void whenGetTotalScoreForNonExistingUser_thenReturnEmptyAggregationResults() {
        // given
        String userId = "13"; // non-existing user

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
        // Objects stored in DB.

        // when
        AggregationResults<LeaderBoardRow> aggregationResults = scoreRepository.findFirst10();

        // then
        List<LeaderBoardRow> leaderBoard = aggregationResults.getMappedResults();
        then(leaderBoard.size()).isEqualTo(10);
        then(leaderBoard.get(0)).isEqualTo(new LeaderBoardRow("1", 8000L + 9000L + 10000L));
        then(leaderBoard.get(1)).isEqualTo(new LeaderBoardRow("2", 7000L + 8000L + 9000L));
        then(leaderBoard.get(2)).isEqualTo(new LeaderBoardRow("3", 6000L + 7000L + 8000L));
        then(leaderBoard.get(3)).isEqualTo(new LeaderBoardRow("4", 5000L + 6000L + 7000L));
        then(leaderBoard.get(4)).isEqualTo(new LeaderBoardRow("5", 4000L + 5000L + 6000L));
        then(leaderBoard.get(5)).isEqualTo(new LeaderBoardRow("6", 3000L + 4000L + 5000L));
        then(leaderBoard.get(6)).isEqualTo(new LeaderBoardRow("7", 2000L + 3000L + 4000L));
        then(leaderBoard.get(7)).isEqualTo(new LeaderBoardRow("8", 1000L + 2000L + 3000L));
        then(leaderBoard.get(8)).isEqualTo(new LeaderBoardRow("9", 900L + 1000L + 2000L));
        then(leaderBoard.get(9)).isEqualTo(new LeaderBoardRow("10", 800L + 900L + 1000L));
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
        then(firstScoreCard.getScore()).isEqualTo(9000);

        ScoreCard secondScoreCard = scoreCards.get(1);
        then(secondScoreCard.getUserId()).isEqualTo("2");
        then(secondScoreCard.getAttemptId()).isEqualTo("5");
        then(secondScoreCard.getScore()).isEqualTo(8000);

        ScoreCard thirdScoreCard = scoreCards.get(2);
        then(thirdScoreCard.getUserId()).isEqualTo("2");
        then(thirdScoreCard.getAttemptId()).isEqualTo("4");
        then(thirdScoreCard.getScore()).isEqualTo(7000);
    }

    @Test
    void whenFindByUserIdOrderByTimestampDescForNonExistingUser_thenReturnEmptyList() {
        // given
        String userId = "13"; // non-existing user

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
        scoreRepository.deleteAll(user3ScoreCards);
        scoreRepository.deleteAll(user4ScoreCards);
        scoreRepository.deleteAll(user5ScoreCards);
        scoreRepository.deleteAll(user6ScoreCards);
        scoreRepository.deleteAll(user7ScoreCards);
        scoreRepository.deleteAll(user8ScoreCards);
        scoreRepository.deleteAll(user9ScoreCards);
        scoreRepository.deleteAll(user10ScoreCards);
        scoreRepository.deleteAll(user11ScoreCards);
        scoreRepository.deleteAll(user12ScoreCards);
    }

}