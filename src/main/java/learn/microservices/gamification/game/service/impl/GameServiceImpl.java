package learn.microservices.gamification.game.service.impl;

import learn.microservices.gamification.game.dto.ChallengeSolvedDto;
import learn.microservices.gamification.game.service.GameService;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {

    @Override
    public GameResult newAttemptForUser(final ChallengeSolvedDto challenge) {
        return null;
    }

}