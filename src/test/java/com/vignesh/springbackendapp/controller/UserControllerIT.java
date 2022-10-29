package com.vignesh.springbackendapp.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vignesh.springbackendapp.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIT {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  public static final String USER_ID = "vigneshun";

  @Test
  public void getUser() throws Exception {
    MvcResult mvcResult =
        mockMvc
            .perform(get("/api/users/1").with(user(USER_ID)).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    User user = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);
    assertEquals("vignesh@examplecom", user.getEmail());
  }
}
