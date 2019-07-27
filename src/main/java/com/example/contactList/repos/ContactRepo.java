package com.example.contactList.repos;

import com.example.contactList.domain.Contact;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepo extends CrudRepository<Contact, Long> {

    Contact findContactById(Long id);

    @Modifying
    @Query(value = "delete from Contact where id = :id")
    void deleteContactById(Long id);
}
