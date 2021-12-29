package learn.microservices.gamification.game.service;

import learn.microservices.gamification.game.entity.LeaderBoardRow;

import java.util.List;

public interface LeaderBoardService {

    /**
     * @return the current leader board ranked in descending order
     */
    List<LeaderBoardRow> getCurrentLeaderBoard();

}