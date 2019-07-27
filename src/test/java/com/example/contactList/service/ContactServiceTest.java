package com.example.contactList.service;

import com.example.contactList.domain.Contact;
import com.example.contactList.domain.Email;
import com.example.contactList.domain.Number;
import com.example.contactList.domain.User;
import com.example.contactList.domain.dto.ContactDto;
import com.example.contactList.repos.ContactRepo;
import com.example.contactList.repos.UserRepo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql", "/create-contact-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-contact-after.sql", "/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ContactServiceTest {
    @Autowired
    ContactService contactService;

    @Autowired
    ContactRepo contactRepo;

    @Autowired
    UserRepo userRepo;


    @Test
    public void getContacts() {
        User user = userRepo.findByUsername("Joni");

        Set<ContactDto> result = contactService.getContacts(user);

        Assert.assertEquals(2, result.size());

        user = new User();

        result = contactService.getContacts(user);

        Assert.assertEquals(0, result.size());
    }

    @Test
    public void addContact() {
        User user = userRepo.findByUsername("Joni");
        HashSet<String> emails = new HashSet<>();
        emails.add("itEmail@gmail.com");
        HashSet<String> numbers = new HashSet<>();
        numbers.add("380736482734");

        ContactDto contact = new ContactDto("Jho", emails, numbers);

        Assert.assertTrue(contactService.addContact(user, contact));

        Assert.assertEquals("Jho", contactRepo.findContactById((long) 7).getName());

        contact.setName("Jhone");
        Assert.assertFalse(contactService.addContact(user, contact));
    }

    @Test
    public void saveContact() {
        User user = userRepo.findByUsername("Joni");
        HashSet<String> emails = new HashSet<>();
        emails.add("itEmail@gmail.com");
        HashSet<String> numbers = new HashSet<>();
        numbers.add("380473658374");

        ContactDto contact = new ContactDto("Jho", emails, numbers);

        Assert.assertTrue(contactService.saveContact(user, contactRepo.findContactById((long) 1), contact));

        Assert.assertEquals("Jho", contactRepo.findContactById((long) 1).getName());

        Assert.assertFalse(contactService.saveContact(user, contactRepo.findContactById((long) 3), contact));
    }

    @Test
    public void deleteContact() {
        User user = userRepo.findByUsername("Joni");

        Assert.assertTrue(contactService.deleteContact(user, contactRepo.findContactById((long) 1)));

        Assert.assertNull(contactRepo.findContactById((long) 1));

        Assert.assertFalse(contactService.deleteContact(user, contactRepo.findContactById((long) 3)));

        Assert.assertNotNull(contactRepo.findContactById((long) 3));
    }

    @Test
    public void isContactValid() {
        User user = new User();

        Assert.assertFalse(contactService.isContactValid(user, new Contact()));

        HashSet<Email> emails = new HashSet<>();
        emails.add(new Email("itEmail@gmail.com"));
        HashSet<Number> numbers = new HashSet<>();
        numbers.add(new Number("380683423014"));

        Contact contact = new Contact("Jhon", numbers, emails);

        Assert.assertTrue(contactService.isContactValid(user, contact));

        HashSet<Contact> contacts = new HashSet<>();

        contacts.add(contact);
        user.setContacts(contacts);

        emails = new HashSet<>();
        numbers = new HashSet<>();
        emails.add(new Email("newEmail@mail.ru"));
        numbers.add(new Number("380564463926"));
        contact = new Contact("Frank", numbers, emails);


        Assert.assertTrue(contactService.isContactValid(user, contact));


        numbers = new HashSet<>();
        numbers.add(new Number("380746354836"));
        contact = new Contact();
        contact.setName("Jhon");
        contact.setNumbers(numbers);

        Assert.assertFalse(contactService.isContactValid(user, contact));

        emails = new HashSet<>();
        numbers = new HashSet<>();
        emails.add(new Email("newEmail@mail.ru"));
        contact = new Contact("Frank", numbers, emails);


        Assert.assertFalse(contactService.isContactValid(user, contact));

        emails = new HashSet<>();
        numbers = new HashSet<>();
        emails.add(new Email("itEmail@gmail.com"));
        numbers.add(new Number("380564463926"));
        contact = new Contact("Frank", numbers, emails);

        Assert.assertFalse(contactService.isContactValid(user, contact));

        emails = new HashSet<>();
        numbers = new HashSet<>();
        numbers.add(new Number("380564463926"));
        contact = new Contact("Frank", numbers, emails);

        Assert.assertTrue(contactService.isContactValid(user, contact));

        emails = new HashSet<>();
        numbers = new HashSet<>();
        emails.add(new Email("newEmail@gmail.com"));
        contact = new Contact("Frank", numbers, emails);

        Assert.assertFalse(contactService.isContactValid(user, contact));
    }

}
