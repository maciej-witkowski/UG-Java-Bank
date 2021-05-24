package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Bank {

    private final ArrayList<Client> allClients;

    public Bank() {
        this.allClients = new ArrayList<>();
    }

    public Client newClient(String firstName, String lastName) {
        Client client = new Client(firstName, lastName);
        this.allClients.add(client);
        return client;
    }

    public void callTransfer(Client cltFrom, Account accFrom, Account accTo, Client cltTo, double amount) throws Exception {
        newTransfer(cltFrom, accFrom, accTo, cltTo, amount);
    }

    private void newTransfer(Client cltFrom, Account accFrom, Account accTo, Client cltTo, double amount) throws Exception {
        if (amount > accFrom.getBalance()) {
            throw new Exception("You cannot withdraw more from your account than you have!");
        }

        if (accFrom == accTo) {
            throw new Exception("You cannot make a transfer to yourself!");
        }

        accFrom.balance -= amount;
        accTo.balance += amount;

        Operation fromOperation = new Operation(cltFrom, accFrom, accTo, cltTo, amount, accFrom.balance);
        Operation toOperation = new Operation(cltFrom, accFrom, accTo, cltTo, amount, accTo.balance);

        accFrom.history.addOperation(fromOperation);
        accTo.history.addOperation(toOperation);
    }

    public int getAmountOfClients() {
        return this.allClients.size();
    }

    public Map<String, String> info() {
        Map<String, String> dictionary = new HashMap<>();

        this.allClients.forEach((client) -> {
            System.out.println(client.getFullName());
            dictionary.put(String.valueOf(this.allClients.indexOf(client)), client.getFullName());
        });

        return dictionary;
    }

}
