package main;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Account extends Bank{
    private final Client clt;
    private final String num;
    protected final History history;
    protected double balance;

    public Account(Client clt, String num) throws Exception {
        if (num.length() != 32) {
            throw new Exception("The length of account number must be 32!");
        }

        Pattern p = Pattern.compile("\\d{2}\\s\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}");
        Matcher m = p.matcher(num);

        if (!m.find()) {
            throw new Exception("The account number provided does not meet the required format!");
        }

        this.clt = clt;
        this.num = num;
        this.balance = 0.0;
        this.history = new History();
    }

    public void deposit(double amount) {
        this.balance += amount;
        Operation newOperation = new Operation(clt, this, amount, this.balance);
        this.history.addOperation(newOperation);
    }

    public void withdraw(double amount) throws Exception {
        if (amount > this.balance) {
            throw new Exception("You cannot withdraw more from your account than you have!");
        }

        this.balance -= amount;
        Operation newOperation = new Operation(clt, this, amount, this.balance);
        this.history.addOperation(newOperation);
    }

    public void transaction(Client cltTo, Account accTo, double amount) throws Exception {
        this.callTransfer(clt, this, accTo, cltTo, amount);
    }

    public String getNumber() { return this.num; }

    public double getBalance() {
        return this.balance;
    }

    public int sumOperations() {
        return this.history.sum();
    }

    public void accountStatement() {
        ArrayList<Operation> result = this.history.printOperations();
        result.forEach(Operation::print);
    }

    public void accountStatement(String fromDate, String toDate) throws ParseException {
        ArrayList<Operation> result = this.history.printOperations(fromDate, toDate);
        result.forEach(Operation::print);
    }

    public void accountStatement(String fromDate, String fromTime, String toDate, String toTime) throws ParseException {
        ArrayList<Operation> result = this.history.printOperations(fromDate, fromTime, toDate, toTime);
        result.forEach(Operation::print);
    }

    @Override
    public Map<String, String> info() {
        Map<String, String> dictionary = new HashMap<>();

        System.out.println(
                "\n######################################################################" +
                "\nWłaściciel konta: " + this.clt.getFullName() +
                "\nNumer rachunku :" + this.getNumber() +
                "\nAktualne saldo: " + this.getBalance() +
                "\nLiczba operacji: " + this.sumOperations() +
                "\n######################################################################"
        );

        dictionary.put("owner", this.clt.getFullName());
        dictionary.put("accNum", this.getNumber());
        dictionary.put("balance", String.valueOf(this.getBalance()));
        dictionary.put("sumOperations", String.valueOf(this.sumOperations()));

        return dictionary;
    }
}