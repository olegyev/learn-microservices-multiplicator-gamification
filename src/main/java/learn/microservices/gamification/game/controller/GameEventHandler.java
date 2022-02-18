package learn.microservices.gamification.game.controller;

import learn.microservices.gamification.game.dto.ChallengeSolvedEvent;
import learn.microservices.gamification.game.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameEventHandler {

    private final GameService gameService;

    @RabbitListener(queues = "${amqp.queue.gamification}")
    public void handleChallengeSolvedEvent(final ChallengeSolvedEvent event) {
        try {
            gameService.newAttemptForUser(event);
        } catch (Exception e) {
            // Avoid the event to be re-queued and reprocessed.
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }

}