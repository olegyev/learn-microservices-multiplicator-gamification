package learn.microservices.gamification.game.service.impl;

import learn.microservices.gamification.game.entity.BadgeCard;
import learn.microservices.gamification.game.entity.LeaderBoardRow;
import learn.microservices.gamification.game.enumeration.BadgeType;
import learn.microservices.gamification.game.repository.BadgeRepository;
import learn.microservices.gamification.game.repository.ScoreRepository;
import learn.microservices.gamification.game.service.LeaderBoardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

class LeaderBoardServiceImplTest {

    private LeaderBoardService leaderBoardService;

    @Mock
    private ScoreRepository scoreRepository;

    @Mock
    private BadgeRepository badgeRepository;

    @BeforeEach
    public void setUp() {
        leaderBoardService = new LeaderBoardServiceImpl(scoreRepository, badgeRepository);
    }

    @Test
    public void whenGetCurrentLeaderBoard_thenTopLeaderBoardRowsAreReturned() {
        // given
        String idForUser1 = "1";
        String idForUser2 = "2";
        String idForUser3 = "3";

        long totalScoresForUser1 = 400L;
        long totalScoresForUser2 = 150L;
        long totalScoresForUser3 = 50L;

        List<BadgeCard> badgesForUser1 = List.of(
                new BadgeCard(idForUser1, BadgeType.FIRST_WON),
                new BadgeCard(idForUser1, BadgeType.BRONZE),
                new BadgeCard(idForUser1, BadgeType.SILVER),
                new BadgeCard(idForUser1, BadgeType.GOLD)
        );

        List<BadgeCard> badgesForUser2 = List.of(
                new BadgeCard(idForUser2, BadgeType.FIRST_WON),
                new BadgeCard(idForUser2, BadgeType.BRONZE),
                new BadgeCard(idForUser2, BadgeType.SILVER)
        );

        List<BadgeCard> badgesForUser3 = List.of(
                new BadgeCard(idForUser3, BadgeType.FIRST_WON),
                new BadgeCard(idForUser3, BadgeType.BRONZE)
        );

        List<LeaderBoardRow> onlyScores = List.of(
                new LeaderBoardRow(idForUser1, totalScoresForUser1),
                new LeaderBoardRow(idForUser2, totalScoresForUser2),
                new LeaderBoardRow(idForUser3, totalScoresForUser3)
        );

        given(scoreRepository.findFirst10()).willReturn(onlyScores);
        given(badgeRepository.findByUserIdOrderByTimestampDesc(idForUser1)).willReturn(badgesForUser1);
        given(badgeRepository.findByUserIdOrderByTimestampDesc(idForUser2)).willReturn(badgesForUser2);
        given(badgeRepository.findByUserIdOrderByTimestampDesc(idForUser3)).willReturn(badgesForUser3);

        // when
        List<LeaderBoardRow> leaderBoardRows = leaderBoardService.getCurrentLeaderBoard();

        // then
        then(leaderBoardRows.size()).isEqualTo(onlyScores.size());

        then(leaderBoardRows.get(0)).isEqualTo(
                new LeaderBoardRow(
                        idForUser1,
                        totalScoresForUser1,
                        badgesForUser1.stream()
                                .map(BadgeCard::getType)
                                .map(BadgeType::getDescription)
                                .collect(Collectors.toList())
                )
        );

        then(leaderBoardRows.get(1)).isEqualTo(
                new LeaderBoardRow(
                        idForUser2,
                        totalScoresForUser2,
                        badgesForUser2.stream()
                                .map(BadgeCard::getType)
                                .map(BadgeType::getDescription)
                                .collect(Collectors.toList())
                )
        );

        then(leaderBoardRows.get(2)).isEqualTo(
                new LeaderBoardRow(
                        idForUser3,
                        totalScoresForUser3,
                        badgesForUser3.stream()
                                .map(BadgeCard::getType)
                                .map(BadgeType::getDescription)
                                .collect(Collectors.toList())
                )
        );
    }

}