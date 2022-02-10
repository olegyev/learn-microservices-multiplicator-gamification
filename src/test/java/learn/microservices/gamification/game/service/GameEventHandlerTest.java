package learn.microservices.gamification.game.service;

import learn.microservices.gamification.game.dto.ChallengeSolvedEvent;
import learn.microservices.gamification.game.enumeration.BadgeType;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GameEventHandlerTest {

    private GameEventHandler gameEventHandler;

    @Mock
    private GameService gameService;

    @BeforeEach
    public void setUp() {
        gameEventHandler = new GameEventHandler(gameService);
    }

    @Test
    void whenConsumesCorrectEvent_thenServiceMethodIsCalled() {
        // given
        ChallengeSolvedEvent event = createTestChallengeSolvedEvent();
        given(gameService.newAttemptForUser(event)).willReturn(new GameService.GameResult(10, List.of(BadgeType.FIRST_WON)));

        // when
        gameEventHandler.handleChallengeSolvedEvent(event);

        // then
        verify(gameService).newAttemptForUser(event);
    }

    @Test
    void whenExceptionOccurred_thenAmqpExceptionIsRethrown() {
        // given
        ChallengeSolvedEvent event = createTestChallengeSolvedEvent();
        given(gameService.newAttemptForUser(event)).willThrow(new RuntimeException());

        // when -> then
        assertThatThrownBy(() -> gameEventHandler.handleChallengeSolvedEvent(event))
                .isInstanceOf(AmqpRejectAndDontRequeueException.class);
    }

    private ChallengeSolvedEvent createTestChallengeSolvedEvent() {
        return new ChallengeSolvedEvent("1", true, 20, 30, "2", "john_doe");
    }

}