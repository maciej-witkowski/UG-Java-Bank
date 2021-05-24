package test;

import main.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ClientTest {

    private Client client;

    @BeforeEach
    public void setUp() {
        client = new Client("Jan", "Kowalski");
    }

    @Test
    @DisplayName("Extend the database with new account")
    public void testNewAccount() throws Exception {
        client.newAccount("79 1050 0028 1000 0024 1009 3963");

        int currAmount = client.getAmountOfAccounts();

        client.newAccount("09 1040 0158 1000 0034 3089 4520");

        Assertions.assertEquals(currAmount + 1, client.getAmountOfAccounts());
    }

    @Test
    @DisplayName("Attempting to create another account with the same account number")
    public void testNewAccountSameNum() throws Exception {
        client.newAccount("79 1050 0028 1000 0024 1009 3963");

        Exception thrown = Assertions.assertThrows(
                Exception.class,
                () -> client.newAccount("79 1050 0028 1000 0024 1009 3963"),
                "An account with this number already exists!"
        );

        Assertions.assertEquals(thrown.getMessage(), "An account with this number already exists!");

    }

    @Test
    @DisplayName("Get full name of particular client")
    public void testGetFullName() {
        Assertions.assertEquals("Jan Kowalski", client.getFullName());
    }

    @Test
    @DisplayName("Correctly calculated number of accounts")
    public void testGetAmountOfAccounts() throws Exception {
        client.newAccount("09 1040 0158 1000 0034 3089 4520");
        client.newAccount("79 1050 0028 1000 0024 1009 3963");

        Assertions.assertEquals(2, client.getAmountOfAccounts());
    }

    @Test
    @DisplayName("Accounts information in the form of a map - size")
    public void testInfoSize() throws Exception {
        client.newAccount("09 1040 0158 1000 0034 3089 4520");
        client.newAccount("79 1050 0028 1000 0024 1009 3963");
        client.newAccount("09 1060 0158 1000 0034 3089 4520");
        client.newAccount("42 1010 0008 1000 0056 2009 1748");

        assertThat(client.info().size(), is(4));
    }

    @Test
    @DisplayName("Accounts information in the form of a map - content")
    public void testInfoContent() throws Exception {
        client.newAccount("09 1040 0158 1000 0034 3089 4520");
        client.newAccount("79 1050 0028 1000 0024 1009 3963");
        client.newAccount("09 1060 0158 1000 0034 3089 4520");
        client.newAccount("42 1010 0008 1000 0056 2009 1748");

        Assertions.assertEquals(client.info().get("2"), "09 1060 0158 1000 0034 3089 4520");
    }
}
