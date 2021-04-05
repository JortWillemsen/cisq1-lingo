package nl.hu.cisq1.lingo.trainer.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.trainer.application.GameService;
import nl.hu.cisq1.lingo.trainer.domain.Attempt;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.exception.GameNotFoundException;
import nl.hu.cisq1.lingo.trainer.presentation.dto.AttemptRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Import(CiTestConfiguration.class)
@Transactional
class GameControllerIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GameService gameService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void initialize() {
        var controller = new GameController(this.gameService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testShouldStartNewGame() throws Exception {
        var request = MockMvcRequestBuilders.post("/lingo/game");

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.hint").exists());
    }

    @Test
    void testShouldStartNewRound() throws Exception {
        this.gameService.startGame();
        this.gameService.makeAttempt(this.gameService.getActiveGame().getActiveRound().getWordToGuess());

        var request = MockMvcRequestBuilders.post("/lingo/round");

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testMakeAttemptShouldReturnAttempt() throws Exception {
        this.gameService.startGame();

        var body = new AttemptRequestDto();
        body.guess = "apper";
        RequestBuilder request = MockMvcRequestBuilders.post("/lingo/attempt")
                .content(this.objectMapper.writeValueAsString(body)).contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testFinishGameShouldReturn200() throws Exception {
        this.gameService.startGame();

        var request = MockMvcRequestBuilders.patch("/lingo/game/active/finish");

        this.mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    void testFinishGameShouldThrow() throws Exception {
        var request = MockMvcRequestBuilders.patch("/lingo/game/active/finish");

        this.mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    void testStartGameShouldThrow() throws Exception {
        this.gameService.startGame();

        var request = MockMvcRequestBuilders.post("/lingo/game");
        this.mockMvc.perform(request)
                .andExpect(status().isConflict());
    }

    @Test
    void testMakeAttemptShouldThrow() throws Exception {
        var body = new AttemptRequestDto();
        body.guess = "apper";

        var request = MockMvcRequestBuilders.post("/lingo/attempt")
                .content(this.objectMapper.writeValueAsString(body)).contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    void testMakeAttemptShouldThrowInvalidAttemptException() throws Exception {
        this.gameService.startGame();

        var body = new AttemptRequestDto();
        body.guess = "appere";

        var request = MockMvcRequestBuilders.post("/lingo/attempt")
                .content(this.objectMapper.writeValueAsString(body)).contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
                .andExpect(status().isConflict());
    }
}
