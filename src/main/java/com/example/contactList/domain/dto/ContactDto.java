package com.example.contactList.domain.dto;

import java.util.Set;

public class ContactDto {
    private String name;
    private Set<String> emails;
    private Set<String> numbers;

    @Override
    public String toString() {
        return "ContactDto{" +
                "name='" + name + '\'' +
                ", emsils=" + emails +
                ", numbers=" + numbers +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getEmails() {
        return emails;
    }

    public void setEmails(Set<String> emails) {
        this.emails = emails;
    }

    public Set<String> getNumbers() {
        return numbers;
    }

    public void setNumbers(Set<String> numbers) {
        this.numbers = numbers;
    }

    public ContactDto() {

    }

    public ContactDto(String name, Set<String> emails, Set<String> numbers) {
        this.name = name;
        this.emails = emails;
        this.numbers = numbers;
    }
}
