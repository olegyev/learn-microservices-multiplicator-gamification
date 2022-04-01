package learn.microservices.gamification.game.consumer;

import learn.microservices.gamification.game.dto.ChallengeSolvedEvent;
import learn.microservices.gamification.game.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * Consumes messages from the RabbitMQ queue.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GameEventHandler {

    private final GameService gameService;

    @RabbitListener(queues = "${amqp.queue.gamification}")
    public void handleChallengeSolvedEvent(final ChallengeSolvedEvent event) {
        try {
            gameService.newAttemptForUser(event);
        } catch (Exception e) {
            // Reject an event in case of exception.
            // Avoid the event to be re-queued and reprocessed.
            log.error("Error while handling event: {}", event, e);
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }

}