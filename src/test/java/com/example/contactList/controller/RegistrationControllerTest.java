package com.example.contactList.controller;

import com.example.contactList.domain.User;
import com.example.contactList.repos.UserRepo;
import com.example.contactList.service.util.JsonParser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-contact-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class RegistrationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    JsonParser jsonParser;

    @Autowired
    UserRepo userRepo;

    @Test
    public void addUser() throws Exception {
        User user = new User("Ban", "123");

        MvcResult result = this.mockMvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonParser.parse(user)))
                .andExpect(status().isOk()).andDo(print())
                .andReturn();

        Assert.assertEquals("true", result.getResponse().getContentAsString());

        Assert.assertTrue(userRepo.existsById((long) 3));
    }
}