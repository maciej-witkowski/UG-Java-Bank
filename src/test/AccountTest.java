package test;

import main.Account;
import main.Client;

import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.mockito.verification.PrivateMethodVerification;
import org.powermock.core.classloader.annotations.PrepareForTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@PrepareForTest(Account.class)
public class AccountTest {

    private Account acc;
    private Client client;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @BeforeEach
    public void setUp() throws Exception {
        client = new Client("Jan", "Kowalski");
        acc = new Account(client, "79 1050 0028 1000 0024 1009 3963");
    }

    @Test
    @DisplayName("Wrong length of account number")
    public void testAccountWrongLength() {
        Exception thrown = Assertions.assertThrows(
                Exception.class,
                () -> new Account(client,"79 1050 00281000 0024 1009 3963 8541"),
                "The length of account number must be 32!"
        );

        Assertions.assertEquals(thrown.getMessage(), "The length of account number must be 32!");
    }

    @Test
    @DisplayName("Wrong format of account number")
    public void testAccountWrongFormat() {
        Exception thrown = Assertions.assertThrows(
                Exception.class,
                () -> new Account(client,"abb 10500028 1000 0024 1009 8541"),
                "The account number provided does not meet the required format!"
        );

        Assertions.assertEquals(thrown.getMessage(), "The account number provided does not meet the required format!");
    }

    @Test
    @DisplayName("Correct change of balance after deposit")
    public void testDepositBalance() {
        acc.deposit(250.50);

        Assertions.assertEquals(250.50, acc.getBalance());
    }

    @Test
    @DisplayName("Extend the history of operations after deposit")
    public void testDepositOperation() {
        acc.deposit(58.62);

        Assertions.assertEquals(1, acc.sumOperations());
    }

    @Test
    @DisplayName("Trying to withdraw more than possible from your account")
    public void testWithdrawWrongAmount() {
        acc.deposit(150);

        Exception thrown = Assertions.assertThrows(
                Exception.class,
                () -> acc.withdraw(250.50),
                "You cannot withdraw more from your account than you have!"
        );

        Assertions.assertEquals(thrown.getMessage(), "You cannot withdraw more from your account than you have!");

    }

    @Test
    @DisplayName("Correct change of balance after withdraw")
    public void testWithdrawBalance() throws Exception {
        acc.deposit(150);
        acc.withdraw(50.50);

        Assertions.assertEquals(99.50, acc.getBalance());
    }

    @Test
    @DisplayName("Extend the history of operations after withdraw")
    public void testWithdrawOperation() throws Exception {
        acc.deposit(150);
        acc.withdraw(50.50);

        Assertions.assertEquals(2, acc.sumOperations());
    }

    @Test
    @DisplayName("Method from Bank class called correctly")
    public void testTransaction() throws Exception {
        acc = PowerMockito.spy(acc);

        Client client0 = new Client("Testowy", "klient0");
        Account acc0 = new Account(client0, "09 1040 0158 1000 0034 3089 4520");

        acc.deposit(600);

        acc.transaction(client0, acc0, 200);
        PrivateMethodVerification privateMethodVerification = PowerMockito.verifyPrivate(acc);
        privateMethodVerification.invoke("callTransfer", client, acc, acc0, client0, 200.0);
    }

    @Test
    @DisplayName("Get number od particular account")
    public void testGetNumber() {
        Assertions.assertEquals("79 1050 0028 1000 0024 1009 3963", acc.getNumber());
    }

    @Test
    @DisplayName("Get balance od particular account")
    public void testGetBalance() {
        Assertions.assertEquals(0.0, acc.getBalance());
    }

    @Test
    @DisplayName("Get amount of all operations")
    public void testSumOperations() throws Exception {
        acc.deposit(150);
        acc.withdraw(50.50);
        acc.deposit(50.80);

        Assertions.assertEquals(3, acc.sumOperations());
    }

    @Test
    @DisplayName("Single account information in the form of a map - size")
    public void testInfoSize() {
        assertThat(acc.info().size(), is(4));
    }

    @Test
    @DisplayName("Single account information in the form of a map - content")
    public void testInfoContent() throws Exception {
        acc.deposit(150);
        acc.withdraw(50.50);
        acc.deposit(50.80);

        Assertions.assertEquals(acc.info().get("sumOperations"), "3");
    }

}
