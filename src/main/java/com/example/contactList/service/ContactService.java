package com.example.contactList.service;

import com.example.contactList.domain.Contact;
import com.example.contactList.domain.Number;
import com.example.contactList.domain.User;
import com.example.contactList.repos.EmailRepo;
import com.example.contactList.repos.NumberRepo;
import com.example.contactList.domain.Email;
import com.example.contactList.domain.dto.ContactDto;
import com.example.contactList.repos.ContactRepo;
import com.example.contactList.service.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

@Service
public class ContactService {
    private final ContactRepo contactRepo;

    private final Validator validator;

    private final NumberRepo numberRepo;

    private final EmailRepo emailRepo;

    @Autowired
    public ContactService(ContactRepo contactRepo, Validator validator, NumberRepo numberRepo, EmailRepo emailRepo) {
        this.contactRepo = contactRepo;
        this.validator = validator;
        this.numberRepo = numberRepo;
        this.emailRepo = emailRepo;
    }

    public Set<ContactDto> getContacts(User user) {

        Set<Contact> contactsFromDb;

        if (user.getContacts() != null && !user.getContacts().isEmpty())
            contactsFromDb = user.getContacts();
        else return new HashSet<>();

        Set<ContactDto> result = new HashSet<>();

        for (Contact c : contactsFromDb) {
            HashSet<String> emails = new HashSet<>();
            HashSet<String> numbers = new HashSet<>();
            if(c.getEmails() != null)
            c.getEmails().forEach((a) -> emails.add(a.getEmail()));
            c.getNumbers().forEach((a) -> numbers.add(a.getNumber()));
            result.add(new ContactDto(c.getName(), emails, numbers));
        }

        return result;
    }

    public boolean addContact(User user, ContactDto contactDto) {
        Contact contact = new Contact();
        if (!StringUtils.isEmpty(contactDto.getName()))
            contact.setName(contactDto.getName());
        else return false;
        if (contactDto.getNumbers() != null && !contactDto.getNumbers().isEmpty()) {
            HashSet<Number> numbers = new HashSet<>();

            contactDto.getNumbers().forEach((a) -> numbers.add(new Number(a)));
            contact.setNumbers(numbers);
        } else return false;
        if (contactDto.getEmails() != null && !contactDto.getEmails().isEmpty()) {
            HashSet<Email> emails = new HashSet<>();

            contactDto.getEmails().forEach((a) -> emails.add(new Email(a)));
            contact.setEmails(emails);

        }

        if (isContactValid(user, contact)) {
            contact.setUser(user);
            contactRepo.save(contact);
            return true;
        }
        return false;
    }

    public boolean saveContact(User user, Contact contact, ContactDto contactDto) {
        if (contact.getUser().getId().equals(user.getId())) {
            if (!StringUtils.isEmpty(contactDto.getName()))
                contact.setName(contactDto.getName());
            if (contactDto.getEmails() != null && !contactDto.getEmails().isEmpty()) {
                HashSet<Number> numbers = new HashSet<>();
                contactDto.getNumbers().forEach((a) -> numbers.add(new Number(a)));
                contact.setNumbers(numbers);
            }
            if (contactDto.getNumbers() != null && !contactDto.getNumbers().isEmpty()) {
                HashSet<Email> emails = new HashSet<>();

                contactDto.getEmails().forEach((a) -> emails.add(new Email(a)));
                contact.setEmails(emails);

            }

            if (isContactValid(user, contact)) {
                contactRepo.save(contact);
                return true;
            }
        }
        return false;
    }

    @Transactional
    public boolean deleteContact(User user, Contact contact) {
        if (contact.getUser().getId().equals(user.getId())) {
            numberRepo.deleteNumberByContact(contact);
            emailRepo.deleteEmailByContact(contact);
            contactRepo.deleteContactById(contact.getId());
            return true;
        }
        return false;
    }

    public boolean isContactValid(User user, Contact contact) {

        if (contact.getName() == null || StringUtils.isEmpty(contact.getName()))
            return false;

        if (contact.getNumbers() != null && !contact.getNumbers().isEmpty()) {
            for (Number number : contact.getNumbers())
                if (!validator.validateNumber(number.getNumber()))
                    return false;
        } else return false;

        if (contact.getEmails() != null && !contact.getEmails().isEmpty())
            for (Email email : contact.getEmails())
                if (!validator.validateEmail(email.getEmail()))
                    return false;

        if (user.getContacts() != null && !user.getContacts().isEmpty()) {
            for (Contact a : user.getContacts()) {
                if (a.getName().equals(contact.getName()))
                    return false;
                if (a.getNumbers() != null && !a.getNumbers().isEmpty())
                    for (Number number : a.getNumbers()) {
                        if (contact.getNumbers().stream().anyMatch((n) -> n.getNumber().equals(number.getNumber())))
                            return false;
                    }
                if (a.getEmails() != null
                        && !a.getEmails().isEmpty()
                        && contact.getEmails() != null
                        && !contact.getEmails().isEmpty())
                    for (Email email : a.getEmails())
                        if (contact.getEmails().stream().anyMatch((e) -> e.getEmail().equals(email.getEmail())))
                            return false;
            }
        }
        return true;
    }
}
