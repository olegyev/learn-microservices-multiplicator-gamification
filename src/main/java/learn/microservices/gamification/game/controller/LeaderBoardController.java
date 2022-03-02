package learn.microservices.gamification.game.controller;

import learn.microservices.gamification.game.entity.LeaderBoardRow;
import learn.microservices.gamification.game.service.LeaderBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/leaders")
@RequiredArgsConstructor
@Slf4j
public class LeaderBoardController {

    private final LeaderBoardService leaderBoardService;

    @GetMapping
    public List<LeaderBoardRow> getLeaderBoard() {
        log.info("Get leaderboard request processing");
        return leaderBoardService.getCurrentLeaderBoard();
    }

}