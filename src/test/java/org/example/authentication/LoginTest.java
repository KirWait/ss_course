package org.example.authentication;

import org.example.controller.AuthenticationController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginTest {

    @Autowired
    private AuthenticationController controller;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldCorrectlyLogIn() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("""
            {
                "username": "USER",
                "password": "test"
            }
            """)).andExpect(status().isOk()).andReturn();
        String resultString = result.getResponse().getContentAsString();
        System.out.println(resultString.substring(resultString.indexOf(": ") + 2));
    }

    @Test()
    public void shouldNotLogIn() throws Exception {
        this.mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("""
            {
                "username": "USER",
                "password": "wrong_pass"
            }
            """)).andExpect(status().isForbidden());
    }


}
