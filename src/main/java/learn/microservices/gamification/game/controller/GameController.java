package learn.microservices.gamification.game.controller;

import learn.microservices.gamification.game.dto.ChallengeSolvedDto;
import learn.microservices.gamification.game.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attempts")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void postResult(@RequestBody ChallengeSolvedDto dto) {
        gameService.newAttemptForUser(dto);
    }

}