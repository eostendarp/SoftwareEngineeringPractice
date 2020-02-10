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
        assertEquals(null, admin.getBank());
        Collection <Account> testCollection = new ArrayList<Account>();
        testCollection.add(new CheckingAccount(50, "1234"));
        testCollection.add(new CheckingAccount(100,"123"));
        CentralBank testBank = new CentralBank();
        testBank.accounts = testCollection;
        admin = new Admin(testBank);
        assertEquals(testBank, admin.getBank());

    }

    @Test
    void freezeAccountTest() {
        Account abcAcc = new CheckingAccount(500, "abc");
        Account xyzAcc = new CheckingAccount(500, "xyz");
        Collection<Account>  collection = new ArrayList<Account>();
        collection.add(abcAcc);
        collection.add(xyzAcc);
        CentralBank testBank = new CentralBank();
        testBank.accounts = collection;
        Admin admin = new Admin(testBank);
        //Checking false frozen status after no change
        assertEquals(false, abcAcc.getFrozenStatus());
        assertEquals(false, xyzAcc.getFrozenStatus());
        admin.freezeAccount("abc");
        //Checking for true after frozen, and that other account unaffected
        assertEquals(true, abcAcc.getFrozenStatus());
        assertEquals(false, xyzAcc.getFrozenStatus());
        admin.freezeAccount("abc");
        //Checking still true if you attempt to freeze a frozen account
        assertEquals(true, abcAcc.getFrozenStatus());
        //Checking freezing other account
        admin.freezeAccount("xyz");
        assertEquals(true, xyzAcc.getFrozenStatus());
        //Checking with IDs not present in collection
        assertThrows(IllegalArgumentException.class, () -> admin.freezeAccount("123"));
        assertThrows(IllegalArgumentException.class, () -> admin.freezeAccount(""));
        assertThrows(IllegalArgumentException.class, () -> admin.freezeAccount("abcd"));

    }

    @Test
    void unFreezeAccountTest() {
        Account abcAcc = new CheckingAccount(500, "abc");
        Account xyzAcc = new CheckingAccount(500, "xyz");
        Collection<Account>  collection = new ArrayList<Account>();
        collection.add(abcAcc);
        collection.add(xyzAcc);
        CentralBank testBank = new CentralBank();
        testBank.accounts = collection;
        Admin admin = new Admin(testBank);
        //Checking true frozen status after change
        abcAcc.setFrozen(true);
        xyzAcc.setFrozen(true);
        assertEquals(true, abcAcc.getFrozenStatus());
        assertEquals(true, xyzAcc.getFrozenStatus());
        //Checking only one acc gets changed
        admin.unfreezeAcct("abc");
        assertEquals(false, abcAcc.getFrozenStatus());
        assertEquals(true, xyzAcc.getFrozenStatus());
        //Checking can unfreeze other acc
        admin.unfreezeAcct("xyz");
        assertEquals(false, xyzAcc.getFrozenStatus());
        //Checking that unfreezing an unfrozen account remains false
        admin.unfreezeAcct("xyz");
        assertEquals(false, xyzAcc.getFrozenStatus());
        //Checking with IDs not present in collection
        assertThrows(IllegalArgumentException.class, () -> admin.unfreezeAcct("123"));
        assertThrows(IllegalArgumentException.class, () -> admin.unfreezeAcct(""));
        assertThrows(IllegalArgumentException.class, () -> admin.unfreezeAcct("abcd"));

    }

    @Test
    void calcTotalAssetsTest(){
        CentralBank testBank = new CentralBank();
        Admin admin = new Admin(testBank);
        //Checking with no accounts
        assertEquals(0, admin.calcTotalAssets());
        //Checking with one account
        testBank.accounts.add(new CheckingAccount(100, "abc"));
        assertEquals(100, admin.calcTotalAssets());
        //Checking with multiple accounts
        testBank.accounts.add(new CheckingAccount(50.65, "xyz"));
        testBank.accounts.add(new CheckingAccount(156, "def"));
        assertEquals(306.65, admin.calcTotalAssets());
        //Check when there is an account with 0 assets
        testBank.accounts.add(new CheckingAccount(0, "ghi"));
        assertEquals(306.65, admin.calcTotalAssets());
    }

    @Test
    void integrationTest() throws InsufficientFundsException, AccountFrozenException{
        //Meant to test that freeze/unfreeze can work in conjunction, as well as calcTotalAssets working with the Account methods
        Account abcAcc = new CheckingAccount(500, "abc");
        Account xyzAcc = new CheckingAccount(500, "xyz");
        Collection<Account>  collection = new ArrayList<Account>();
        collection.add(abcAcc);
        collection.add(xyzAcc);
        CentralBank testBank = new CentralBank();
        testBank.accounts = collection;
        Admin admin = new Admin(testBank);
        assertEquals(1000, admin.calcTotalAssets());
        abcAcc.transfer(xyzAcc, 300);
        assertEquals(1000, admin.calcTotalAssets());
        abcAcc.deposit(300);
        assertEquals(1300, admin.calcTotalAssets());
        xyzAcc.withdraw(100.55);
        assertEquals(1199.45, admin.calcTotalAssets());

        admin.freezeAccount("abc");
        assertThrows(AccountFrozenException.class, ()->abcAcc.deposit(50));
        admin.unfreezeAcct("abc");
        abcAcc.deposit(50);
        assertEquals(1249.45, admin.calcTotalAssets());


    }




}
