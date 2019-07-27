package com.example.contactList.repos;

import com.example.contactList.domain.Contact;
import com.example.contactList.domain.Email;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepo extends CrudRepository<Email, Long> {
    @Modifying
    @Query(value = "delete from Email where contact = :contact")
    void deleteEmailByContact(Contact contact);
}
