package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AdminTest {

    @Test
    void constructorTest() {
        Admin admin = new Admin(null);
        assertEquals(null, admin.getAccounts());
        Collection <Account> testCollection = new ArrayList<Account>();
        testCollection.add(new CheckingAccount(50, "1234"));
        testCollection.add(new CheckingAccount(100,"123"));
        admin = new Admin(testCollection);
        assertEquals(testCollection,admin.getAccounts());

    }

    @Test
    //Incomplete, have to make Account use ID first
    void freezeAccountTest() {
        Account testAcc = new CheckingAccount(500, "abc");
        Collection<Account>  collection = new ArrayList<Account>();
        collection.add(testAcc);
        Admin admin = new Admin(collection);
        assertEquals(false, testAcc.getFrozenStatus());
        admin.freezeAccount("id");


    }



}