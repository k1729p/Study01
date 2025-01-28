package kp.company.controller;

import kp.SampleDataset;
import kp.company.model.Title;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static kp.TestConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * The {@link TitlesController} tests using the {@link MockMvc}.
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(TitlesController.class)
class TitleControllerTests {

    @Autowired
    private MockMvc mockMvc;

    private static final boolean VERBOSE = false;

    /**
     * Should get the {@link Title}s.
     * <p>
     * Tests a <b>GET</b> request.
     * </p>
     *
     * @throws Exception the {@link Exception}
     */
    @Test
    void shouldGetTitles() throws Exception {
        // GIVEN
        SampleDataset.loadDataset(DEP_TEST_INDEX_UPPER_BOUND, EMP_TEST_INDEX_UPPER_BOUND);
        final MockHttpServletRequestBuilder requestBuilder = get(TITLES_PATH).accept(MediaType.APPLICATION_JSON_VALUE);
        // WHEN
        final ResultActions resultActions = mockMvc.perform(requestBuilder);
        // THEN
        if (VERBOSE) {
            resultActions.andDo(print());
        }
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
        resultActions.andExpect(jsonPath("$").isArray());
        resultActions.andExpect(jsonPath("$", Matchers.hasSize(Title.values().length)));
        resultActions.andExpect(
                MockMvcResultMatchers.content().string(Matchers.containsString(Title.ANALYST.name().toLowerCase())));
        resultActions.andExpect(
                MockMvcResultMatchers.content().string(Matchers.containsString(Title.DEVELOPER.name().toLowerCase())));
        resultActions.andExpect(
                MockMvcResultMatchers.content().string(Matchers.containsString(Title.MANAGER.name().toLowerCase())));
    }

}
