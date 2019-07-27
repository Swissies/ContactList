package com.example.contactList.controller;


import com.example.contactList.domain.Contact;
import com.example.contactList.domain.dto.ContactDto;
import com.example.contactList.repos.ContactRepo;
import com.example.contactList.repos.UserRepo;
import com.example.contactList.service.ContactService;
import com.example.contactList.service.util.JsonParser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashSet;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql", "/create-contact-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-contact-after.sql", "/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@WithUserDetails("Joni")
public class ContactControllerTest {

    @Autowired
    ContactController contactController;

    @Autowired
    UserRepo userRepo;

    @Autowired
    ContactRepo contactRepo;

    @Autowired
    ContactService contactService;

    @Autowired
    JsonParser jsonParser;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void addContact() throws Exception {
        HashSet<String> emails = new HashSet<>();
        emails.add("itEmail@gmail.com");
        HashSet<String> numbers = new HashSet<>();
        numbers.add("380846284637");

        ContactDto contact = new ContactDto("Jhohub", emails, numbers);

        MvcResult result = this.mockMvc.perform(put("/contacts")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonParser.parse(contact)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        Assert.assertEquals("true", result.getResponse().getContentAsString());

        assertEquals(3, contactService.getContacts(userRepo.findByUsername("Joni")).size());
    }

    @Test
    public void getContacts() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/contacts"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String resString = result.getResponse().getContentAsString();

        Assert.assertTrue(resString.contains("Dave") && resString.contains("Jhone"));
    }

    @Test
    public void editContact() throws Exception {
        HashSet<String> emails = new HashSet<>();
        emails.add("itEmail@gmail.com");
        HashSet<String> numbers = new HashSet<>();
        numbers.add("380846284637");

        ContactDto contact = new ContactDto("Jhohub", emails, numbers);

        MvcResult result = this.mockMvc.perform(post("/contacts")
                .param("id", "1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonParser.parse(contact)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        Assert.assertEquals("true", result.getResponse().getContentAsString());

        assertEquals("Jhohub", contactRepo.findContactById((long) 1).getName());
    }

    @Test
    public void deleteContact() throws Exception {

        MvcResult result = this.mockMvc.perform(delete("/contacts")
                .param("id", "3"))
                .andExpect(status().isOk())
                .andReturn();

        Assert.assertEquals("false", result.getResponse().getContentAsString());

        assertEquals(2, userRepo.findByUsername("Joni").getContacts().size());

        result = this.mockMvc.perform(delete("/contacts")
                .param("id", "1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        Assert.assertEquals("true", result.getResponse().getContentAsString());

        Contact c = contactRepo.findContactById(((long) 1));
        assertEquals(1, userRepo.findByUsername("Joni").getContacts().size());
    }

}