package com.example.contactList.controller;

import com.example.contactList.domain.Contact;
import com.example.contactList.domain.User;
import com.example.contactList.service.ContactService;
import com.example.contactList.domain.dto.ContactDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/contacts")
public class ContactController {
    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PutMapping
    @ResponseBody
    public boolean addContact(
            @AuthenticationPrincipal User user,
            @RequestBody ContactDto contactDto) {
        return contactService.addContact(user, contactDto);
    }

    @GetMapping
    @ResponseBody
    public Set<ContactDto> getContacts(
            @AuthenticationPrincipal User user) {
        return contactService.getContacts(user);
    }

    @PostMapping
    @ResponseBody
    public boolean editContact(
            @AuthenticationPrincipal User user,
            @RequestParam("id") Contact contact,
            @RequestBody ContactDto contactDto
    ) {
        return contactService.saveContact(user, contact, contactDto);
    }

    @DeleteMapping
    @ResponseBody
    public boolean deleteContact(
            @AuthenticationPrincipal User user,
            @RequestParam("id") Contact contact) {
        return contactService.deleteContact(user, contact);
    }

}
