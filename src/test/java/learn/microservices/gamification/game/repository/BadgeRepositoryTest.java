package learn.microservices.gamification.game.repository;

import learn.microservices.gamification.game.entity.BadgeCard;
import learn.microservices.gamification.game.enumeration.BadgeType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
class BadgeRepositoryTest {

    @Autowired
    BadgeRepository badgeRepository;

    private final List<BadgeCard> userBadgeCards = new ArrayList<>(4);

    @BeforeEach
    public void setUp() {
        userBadgeCards.add(new BadgeCard(null, "1", System.currentTimeMillis(), BadgeType.FIRST_WON));
        userBadgeCards.add(new BadgeCard(null, "1", System.currentTimeMillis() + 10, BadgeType.BRONZE));
        userBadgeCards.add(new BadgeCard(null, "1", System.currentTimeMillis() + 20, BadgeType.SILVER));
        userBadgeCards.add(new BadgeCard(null, "1", System.currentTimeMillis() + 30, BadgeType.GOLD));

        badgeRepository.saveAll(userBadgeCards);
    }

    @Test
    void whenFindByUserIdOrderByTimestampDescForExistingUser_thenReturnCorrectListOfBadgeCards() {
        // given
        String userId = "1";

        // when
        List<BadgeCard> badgeCards = badgeRepository.findByUserIdOrderByTimestampDesc(userId);

        // then
        then(badgeCards.size()).isEqualTo(4);

        BadgeCard firstBadgeCard = badgeCards.get(0);
        then(firstBadgeCard.getUserId()).isEqualTo("1");
        then(firstBadgeCard.getType()).isEqualTo(BadgeType.GOLD);

        BadgeCard secondBadgeCard = badgeCards.get(1);
        then(secondBadgeCard.getUserId()).isEqualTo("1");
        then(secondBadgeCard.getType()).isEqualTo(BadgeType.SILVER);

        BadgeCard thirdBadgeCard = badgeCards.get(2);
        then(thirdBadgeCard.getUserId()).isEqualTo("1");
        then(thirdBadgeCard.getType()).isEqualTo(BadgeType.BRONZE);

        BadgeCard fourthBadgeCard = badgeCards.get(3);
        then(fourthBadgeCard.getUserId()).isEqualTo("1");
        then(fourthBadgeCard.getType()).isEqualTo(BadgeType.FIRST_WON);
    }

    @Test
    void whenFindByUserIdOrderByTimestampDescForNonExistingUser_thenReturnEmptyList() {
        // given
        String userId = "2"; // non-existing user

        // when
        List<BadgeCard> badgeCards = badgeRepository.findByUserIdOrderByTimestampDesc(userId);

        // then
        then(badgeCards).isEmpty();
    }

    @Test
    void whenFindByUserIdOrderByTimestampDescForNull_thenReturnEmptyList() {
        // given
        String userId = null;

        // when
        List<BadgeCard> badgeCards = badgeRepository.findByUserIdOrderByTimestampDesc(userId);

        // then
        then(badgeCards).isEmpty();
    }

    @AfterEach
    public void tearDown() {
        badgeRepository.deleteAll(userBadgeCards);
    }

}