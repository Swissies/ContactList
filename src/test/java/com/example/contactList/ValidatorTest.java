package com.example.contactList;

import com.example.contactList.service.util.Validator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ValidatorTest {
    @Autowired
    Validator validator;


    @Test
    public void validateEmail() {
        String email = "itEmail@gmail.com";
        Assert.assertTrue(validator.validateEmail(email));
        email = "23@d.";
        Assert.assertFalse(validator.validateEmail(email));
    }

    @Test
    public void validateNumber() {
        String number = "380846284637";
        Assert.assertTrue(validator.validateNumber(number));
        number = "4382";
        Assert.assertFalse(validator.validateNumber(number));
    }
}