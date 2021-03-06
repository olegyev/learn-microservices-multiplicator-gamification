package learn.microservices.gamification.game.dto;

import lombok.Value;

@Value
public class ChallengeSolvedEvent {

    String attemptId;
    boolean correct;
    int factorA;
    int factorB;
    String userId;
    String userAlias;

}