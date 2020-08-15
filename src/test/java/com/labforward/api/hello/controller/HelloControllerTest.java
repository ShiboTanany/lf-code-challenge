package com.labforward.api.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.labforward.api.common.MVCIntegrationTest;
import com.labforward.api.core.enums.Greetings;
import com.labforward.api.core.enums.Messages;
import com.labforward.api.hello.domain.Greeting;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloControllerTest extends MVCIntegrationTest {

    private static final String HELLO_LUKE = "Hello Luke";

    @Test
    public void getHelloIsOKAndReturnsValidJSON() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(Greetings.DEFAULT_ID.getGreeting())))
                .andExpect(jsonPath("$.message", is(Messages.DEFAULT_MESSAGE.getMessage())));
    }

    @Test
    public void returnsBadRequestWhenMessageMissing() throws Exception {
        String body = "{}";
        mockMvc.perform(post("/hello").content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.validationErrors", hasSize(1)))
                .andExpect(jsonPath("$.validationErrors[*].field", contains("message")));
    }

    @Test
    public void returnsBadRequestWhenUnexpectedAttributeProvided() throws Exception {
        String body = "{ \"tacos\":\"value\" }}";
        mockMvc.perform(post("/hello").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message", containsString(Messages.BAD_REQUEST.getMessage())));
    }

    @Test
    public void returnsBadRequestWhenMessageEmptyString() throws Exception {
        Greeting emptyMessage = new Greeting("");
        final String body = getGreetingBody(emptyMessage);

        mockMvc.perform(post("/hello").content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.validationErrors", hasSize(1)))
                .andExpect(jsonPath("$.validationErrors[*].field", contains("message")));
    }

    @Test
    public void createOKWhenRequiredGreetingProvided() throws Exception {
        Greeting hello = new Greeting(HELLO_LUKE);
        final String body = getGreetingBody(hello);

        mockMvc.perform(post("/hello").contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(hello.getMessage())));
    }

    @Test
    public void updateExistedGreeting() throws Exception {
        Greeting hello = new Greeting(HELLO_LUKE);
        final String body = getGreetingBody(hello);

        MvcResult mvcResult = mockMvc.perform(post("/hello").contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(hello.getMessage())))
                .andReturn();
        ;
        String newGreeting = "this new Greeting";

        String json = mvcResult.getResponse().getContentAsString();
        Greeting createdObject = new ObjectMapper().readValue(json, Greeting.class);
        createdObject.setMessage(newGreeting);
        final String newBody = getGreetingBody(createdObject);
        mockMvc.perform(put("/hello").contentType(MediaType.APPLICATION_JSON)
                .content(newBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(createdObject.getMessage())));
    }

    @Test
    public void updateShouldReturnsUnprocessedEntityWithErrorsWhenMessageIsMissing() throws Exception {
        String body = "{}";
        mockMvc.perform(put("/hello").content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.validationErrors", hasSize(1)))
                .andExpect(jsonPath("$.validationErrors[*].field", contains("message")));
    }

    @Test
    public void updateNotExistedGreeting() throws Exception {
        Greeting hello = new Greeting(HELLO_LUKE);
        final String body = getGreetingBody(hello);
        mockMvc.perform(put("/hello").contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isUnprocessableEntity());

    }


    private String getGreetingBody(Greeting greeting) throws JSONException {
        JSONObject json = new JSONObject().put("message", greeting.getMessage());

        if (greeting.getId() != null) {
            json.put("id", greeting.getId());
        }

        return json.toString();
    }

}
