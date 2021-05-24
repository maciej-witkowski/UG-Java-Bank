package test;

import main.Account;
import main.Bank;
import main.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.mockito.verification.PrivateMethodVerification;
import org.powermock.core.classloader.annotations.PrepareForTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@PrepareForTest(Bank.class)
public class BankTest {

    private Bank bank;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
    }

    @Test
    @DisplayName("Extend the database with w new clients")
    public void testNewClient() {
        bank.newClient("Jan", "Kowalski");

        int currAmount = bank.getAmountOfClients();

        bank.newClient("Adam", "Nowak");

        Assertions.assertEquals(currAmount + 1, bank.getAmountOfClients());
    }

    @Test
    @DisplayName("Private method call correctly")
    public void testCallTransfer() throws Exception {
        bank = PowerMockito.spy(bank);

        Client client0 = new Client("Testowy", "klient0");
        Client client1 = new Client("Testowa", "klient1");
        Account acc0 = new Account(client0, "79 1050 0028 1000 0024 1009 3963");
        Account acc1 = new Account(client1, "09 1040 0158 1000 0034 3089 4520");

        acc0.deposit(600);

        bank.callTransfer(client0, acc0, acc1, client1, 250.0);
        PrivateMethodVerification privateMethodVerification = PowerMockito.verifyPrivate(bank);
        privateMethodVerification.invoke("newTransfer", client0, acc0, acc1, client1, 250.0);
    }

    @Test
    @DisplayName("Correct money flow")
    public void testNewTransferBalance() throws Exception {
        Client client0 = new Client("Testowy", "klient0");
        Client client1 = new Client("Testowa", "klient1");
        Account acc0 = new Account(client0, "79 1050 0028 1000 0024 1009 3963");
        Account acc1 = new Account(client1, "09 1040 0158 1000 0034 3089 4520");

        acc0.deposit(600);

        bank.callTransfer(client0, acc0, acc1, client1, 250.0);

        Assertions.assertEquals(350.0, acc0.getBalance());
        Assertions.assertEquals(250.0, acc1.getBalance());
    }

    @Test
    @DisplayName("Correct creation and allocation of transaction records")
    public void testNewTransferOperation() throws Exception {
        Client client0 = new Client("Testowy", "klient0");
        Client client1 = new Client("Testowa", "klient1");
        Account acc0 = new Account(client0, "79 1050 0028 1000 0024 1009 3963");
        Account acc1 = new Account(client1, "09 1040 0158 1000 0034 3089 4520");

        acc0.deposit(600);

        bank.callTransfer(client0, acc0, acc1, client1, 250.0);

        Assertions.assertEquals(2, acc0.sumOperations());
        Assertions.assertEquals(1, acc1.sumOperations());
    }

    @Test
    @DisplayName("Trying to withdraw more than possible from your account!")
    public void testNewTransferWrongAmount() throws Exception {
        Client client0 = new Client("Testowy", "klient0");
        Client client1 = new Client("Testowa", "klient1");
        Account acc0 = new Account(client0, "79 1050 0028 1000 0024 1009 3963");
        Account acc1 = new Account(client1, "09 1040 0158 1000 0034 3089 4520");

        acc0.deposit(600);

        Exception thrown = Assertions.assertThrows(
                Exception.class,
                () -> bank.callTransfer(client0, acc0, acc1, client1, 2250.0),
                "You cannot withdraw more from your account than you have!"
        );

        Assertions.assertEquals(thrown.getMessage(), "You cannot withdraw more from your account than you have!");

    }

    @Test
    @DisplayName("Attempting to transfer money to the same account!")
    public void testNewTransferSameAccount() throws Exception {
        Client client0 = new Client("Testowy", "klient0");
        Account acc0 = new Account(client0, "79 1050 0028 1000 0024 1009 3963");

        acc0.deposit(600);

        Exception thrown = Assertions.assertThrows(
                Exception.class,
                () -> bank.callTransfer(client0, acc0, acc0, client0, 250.0),
                "You cannot make a transfer to yourself!"
        );

        Assertions.assertEquals(thrown.getMessage(), "You cannot make a transfer to yourself!");

    }

    @Test
    @DisplayName("Correctly calculated number of clients")
    public void testGetAmountOfClients() {
        bank.newClient("Jan", "Kowalski");
        bank.newClient("Adam", "Nowak");

        Assertions.assertEquals(2, bank.getAmountOfClients());
    }

    @Test
    @DisplayName("Clients information in the form of a map - size")
    public void testInfoSize() {
        bank.newClient("Jan", "Kowalski");
        bank.newClient("Adam", "Nowak");
        bank.newClient("Krystian", "Banach");

        assertThat(bank.info().size(), is(3));
    }

    @Test
    @DisplayName("Clients information in the form of a map - content")
    public void testInfoContent() {
        bank.newClient("Jan", "Kowalski");
        bank.newClient("Adam", "Nowak");
        bank.newClient("Krystian", "Banach");

        Assertions.assertEquals(bank.info().get("1"), "Adam Nowak");
    }
}
