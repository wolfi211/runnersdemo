package hu.gde.runnersdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
class RunnerRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getRunner()  throws Exception {
        this.mockMvc.perform(get("/api/v1/runner/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.runnerId",is(1)));
    }


}