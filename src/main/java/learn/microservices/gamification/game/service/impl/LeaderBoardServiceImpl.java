package learn.microservices.gamification.game.service.impl;

import learn.microservices.gamification.game.entity.LeaderBoardRow;
import learn.microservices.gamification.game.repository.BadgeRepository;
import learn.microservices.gamification.game.repository.ScoreRepository;
import learn.microservices.gamification.game.service.LeaderBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaderBoardServiceImpl implements LeaderBoardService {

    private final ScoreRepository scoreRepository;
    private final BadgeRepository badgeRepository;

    @Override
    public List<LeaderBoardRow> getCurrentLeaderBoard() {
        return null;
    }

}