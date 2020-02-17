package edu.ithaca.dragon.bank;

import java.util.Scanner;

enum ATMUIState {Login, Frozen, Logout, MainLoggedIn, Withdraw, Deposit, Transfer}

public class ATM_UI {

    private static BasicAPI atm;
    private static  CentralBank bank;
    private static ATMUIState currentUIState;
    private static Scanner in;

    public static void main(String[] args) {
        initializeATM();

        while (true) {
            if(currentUIState == ATMUIState.Login) {
                handleLoginUI();
            } else if(currentUIState == ATMUIState.Frozen) {
                handleFrozenUI();
            } else if(currentUIState == ATMUIState.MainLoggedIn) {
                System.out.println("Logged In");
                return;
            } else if(currentUIState == ATMUIState.Logout) {
                System.out.println("Logout");
                return;
            } else if(currentUIState == ATMUIState.Deposit) {
                System.out.println("Deposit");
                return;
            } else if(currentUIState == ATMUIState.Withdraw) {
                System.out.println("Withdraw");
                return;
            } else if(currentUIState == ATMUIState.Transfer) {
                System.out.println("Transfer");
                return;
            }
        }
    }

    static void initializeATM() {
        //Populate bank
        setupBank();

        //Create ATM
        atm = new ATM(bank);
        currentUIState = ATMUIState.Login;
        in = new Scanner(System.in);
    }

    static void setupBank() {
        bank = new CentralBank();

        Account acc = new CheckingAccount(100, "a@b.com", "1234");
        bank.getAccounts().put(acc.getID(), acc);
        acc = new CheckingAccount(100, "b@b.com", "1234");
        acc.setFrozen(true);
        bank.getAccounts().put(acc.getID(), acc);
        acc = new CheckingAccount(100, "c@b.com", "1234");
        bank.getAccounts().put(acc.getID(), acc);
        acc = new CheckingAccount(100, "d@b.com", "1234");
        bank.getAccounts().put(acc.getID(), acc);
    }

    static void handleLoginUI() {
        boolean hasLoggedin = false;

        do {
            System.out.println( "*************************************************\n" +
                                "               Welcome to ATM                    \n" +
                                "     Please input account ID and Password        \n");

            System.out.print("ID: ");
            String id = in.next();
            System.out.print("\nPassword: ");
            String pass = in.next();
            if(atm.confirmCredentials(id, pass)) {
                if(atm.getAccount(id).isFrozen) {
                    currentUIState = ATMUIState.Frozen;
                    return;
                } else {
                    currentUIState = ATMUIState.MainLoggedIn;
                    return;
                }
            } else {
                System.out.println("Incorrect ID and/or Password");
            }
        } while(!hasLoggedin);
    }

    static void handleFrozenUI() {
        boolean hasLoggedOut = false;

        do {
            System.out.println( "*************************************************\n" +
                                "               Your account is Frozen            \n" +
                                "     You are not allowed to take any action      \n" +
                                "        Please contact Customer Service at       \n" +
                                "                  1-888-555-1212                 \n");

            System.out.print("Enter 'Logout' to logout: ");
            String entry = in.next();
            if(entry.toLowerCase().equals("logout")) {
                currentUIState = ATMUIState.Logout;
                return;
            } else {

            }
        } while(!hasLoggedOut);
    }
}