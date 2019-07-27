package com.example.contactList.repos;

import com.example.contactList.domain.Contact;
import com.example.contactList.domain.Number;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NumberRepo extends CrudRepository<Number, Long> {
    @Modifying
    @Query(value = "delete from Number where contact = :contact")
    void deleteNumberByContact(Contact contact);
}
