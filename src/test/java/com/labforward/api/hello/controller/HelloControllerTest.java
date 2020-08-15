package com.labforward.api.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.labforward.api.common.GreetingUtils;
import com.labforward.api.common.MVCIntegrationTest;
import com.labforward.api.core.enums.Greetings;
import com.labforward.api.core.enums.Messages;
import com.labforward.api.hello.domain.Greeting;
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
    public void testGetHelloIsOKAndReturnsValidJSON() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(Greetings.DEFAULT_ID.getGreeting())))
                .andExpect(jsonPath("$.message", is(Messages.DEFAULT_MESSAGE.getMessage())));
    }

    @Test
    public void testCreateGreetingReturnsBadRequestWhenMessageMissing() throws Exception {
        String body = "{}";
        mockMvc.perform(post("/hello").content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.validationErrors", hasSize(1)))
                .andExpect(jsonPath("$.validationErrors[*].field", contains("message")));
    }

    @Test
    public void testCreateGreetingReturnsBadRequestWhenUnexpectedAttributeProvided() throws Exception {
        String body = "{ \"tacos\":\"value\" }}";
        mockMvc.perform(post("/hello").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message", containsString(Messages.BAD_REQUEST.getMessage())));
    }

    @Test
    public void testCreateGreetingReturnsBadRequestWhenMessageEmptyString() throws Exception {
        Greeting emptyMessage = new Greeting("");
        final String body = GreetingUtils.getGreetingBody(emptyMessage);

        mockMvc.perform(post("/hello").content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.validationErrors", hasSize(1)))
                .andExpect(jsonPath("$.validationErrors[*].field", contains("message")));
    }

    @Test
    public void testCreateOKWhenRequiredGreetingProvided() throws Exception {
        Greeting hello = new Greeting(HELLO_LUKE);
        final String body = GreetingUtils.getGreetingBody(hello);

        mockMvc.perform(post("/hello").contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is(hello.getMessage())));
    }

    @Test
    public void testUpdateExistedGreeting() throws Exception {
        Greeting hello = new Greeting(HELLO_LUKE);
        final String body = GreetingUtils.getGreetingBody(hello);

        MvcResult mvcResult = mockMvc.perform(post("/hello").contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is(hello.getMessage())))
                .andReturn();
        ;
        String newGreeting = "this new Greeting";

        String json = mvcResult.getResponse().getContentAsString();
        Greeting createdObject = new ObjectMapper().readValue(json, Greeting.class);
        createdObject.setMessage(newGreeting);
        final String newBody = GreetingUtils.getGreetingBody(createdObject);
        mockMvc.perform(put("/hello").contentType(MediaType.APPLICATION_JSON)
                .content(newBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(createdObject.getMessage())));
    }

    @Test
    public void testUpdateShouldReturnsUnprocessedEntityWithErrorsWhenMessageIsMissing() throws Exception {
        String body = "{}";
        mockMvc.perform(put("/hello").content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.validationErrors", hasSize(1)))
                .andExpect(jsonPath("$.validationErrors[*].field", contains("message")));
    }

    @Test
    public void testUpdateNotExistedGreeting() throws Exception {
        Greeting hello = new Greeting(HELLO_LUKE);
        final String body = GreetingUtils.getGreetingBody(hello);
        mockMvc.perform(put("/hello").contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    public void testDeleteExistedGreeting() throws Exception {
        Greeting hello = new Greeting(HELLO_LUKE);
        final String body = GreetingUtils.getGreetingBody(hello);

        MvcResult mvcResult = mockMvc.perform(post("/hello").contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is(hello.getMessage())))
                .andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        Greeting createdObject = new ObjectMapper().readValue(json, Greeting.class);
        final String anotherBody = GreetingUtils.getGreetingBody(createdObject);
        mockMvc.perform(delete("/hello").contentType(MediaType.APPLICATION_JSON)
                .content(anotherBody))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteNonExistedGreeting() throws Exception {
        Greeting hello = new Greeting(HELLO_LUKE);
        final String body = GreetingUtils.getGreetingBody(hello);
        mockMvc.perform(delete("/hello").contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteShouldReturnsUnprocessedEntityWithErrorsWhenMessageIsMissing() throws Exception {
        String body = "{}";
        mockMvc.perform(delete("/hello").content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.validationErrors", hasSize(1)))
                .andExpect(jsonPath("$.validationErrors[*].field", contains("message")));
    }

}
