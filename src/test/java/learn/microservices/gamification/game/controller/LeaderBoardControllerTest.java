package learn.microservices.gamification.game.controller;

import learn.microservices.gamification.game.entity.LeaderBoardRow;
import learn.microservices.gamification.game.service.LeaderBoardService;
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
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(LeaderBoardController.class)
class LeaderBoardControllerTest {

    @MockBean
    private LeaderBoardService leaderBoardService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<List<LeaderBoardRow>> jsonResultListLeaderBoard;

    @Test
    void whenGetLeaderBoard_thenReturnsCorrectLeaderBoardInResponse() throws Exception {
        // given
        LeaderBoardRow firstPlace = new LeaderBoardRow("2", 500L);
        LeaderBoardRow secondPlace = new LeaderBoardRow("1", 200L);
        LeaderBoardRow thirdPlace = new LeaderBoardRow("3", 100L);
        given(leaderBoardService.getCurrentLeaderBoard()).willReturn(List.of(firstPlace, secondPlace, thirdPlace));

        // when
        MockHttpServletResponse actualResponse = mvc.perform(
                get("/leaders")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // then
        then(actualResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(actualResponse.getContentAsString()).isEqualTo(
                jsonResultListLeaderBoard.write(List.of(firstPlace, secondPlace, thirdPlace)).getJson()
        );
    }

}