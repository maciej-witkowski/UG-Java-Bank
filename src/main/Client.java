package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Client {

    private final String firstName;
    private final String lastName;
    private final List<Account> accounts;

    public Client(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.accounts = new ArrayList<>();
    }

    public Account newAccount(String num) throws Exception {
        if (this.accounts.stream().map(Account::getNumber).anyMatch(num::equals)) {
            throw new Exception("An account with this number already exists!");
        }

        Account account = new Account(this, num);
        this.accounts.add(account);
        return account;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public int getAmountOfAccounts() {
        return this.accounts.size();
    }

    public Map<String, String> info() {
        Map<String, String> dictionary = new HashMap<>();

        this.accounts.forEach((acc) -> {
            System.out.println(acc.getNumber());
            dictionary.put(String.valueOf(this.accounts.indexOf(acc)), acc.getNumber());
        });

        return dictionary;
    }
}
