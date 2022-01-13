package learn.microservices.gamification.game.controller;

import learn.microservices.gamification.game.dto.ChallengeSolvedDto;
import learn.microservices.gamification.game.enumeration.BadgeType;
import learn.microservices.gamification.game.service.GameService;
import learn.microservices.gamification.game.service.GameService.GameResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(GameController.class)
class GameControllerTest {

    @MockBean
    private GameService gameService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<ChallengeSolvedDto> jsonRequestChallengeSolved;

    @Test
    void whenPostValidResult_thenStatusIsOk() throws Exception {
        // given
        ChallengeSolvedDto requestDto = new ChallengeSolvedDto("1", true, 50, 60, "2", "3");
        GameResult expectedResponse = new GameResult(10, List.of(BadgeType.FIRST_WON));
        given(gameService.newAttemptForUser(eq(requestDto))).willReturn(expectedResponse);

        // when
        MockHttpServletResponse actualResponse = mvc.perform(
                post("/attempts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestChallengeSolved.write(requestDto).getJson())
        ).andReturn().getResponse();

        // then
        then(actualResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

}