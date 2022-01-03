package learn.microservices.gamification.game.service.impl;

import learn.microservices.gamification.game.entity.LeaderBoardRow;
import learn.microservices.gamification.game.repository.BadgeRepository;
import learn.microservices.gamification.game.repository.ScoreRepository;
import learn.microservices.gamification.game.service.LeaderBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaderBoardServiceImpl implements LeaderBoardService {

    private final ScoreRepository scoreRepository;
    private final BadgeRepository badgeRepository;

    @Override
    public List<LeaderBoardRow> getCurrentLeaderBoard() {
        // Get scores only first.
        List<LeaderBoardRow> onlyScores = getLeaderBoard();

        // Merge with badges.
        return onlyScores.stream().map(row -> {
            List<String> badges =
                    badgeRepository.findByUserIdOrderByTimestampDesc(row.getUserId()).stream()
                            .map(b -> b.getType().getDescription())
                            .collect(Collectors.toList());
            return row.withBadges(badges);
        }).collect(Collectors.toList());
    }

    private List<LeaderBoardRow> getLeaderBoard() {
        return scoreRepository.findFirst10().getMappedResults();
    }

}