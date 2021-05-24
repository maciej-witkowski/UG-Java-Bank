package test;

import main.Account;
import main.Client;
import main.Operation;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class OperationTest {

    private Operation operation;

    @Before
    public void setUp() throws Exception {
        Client client1 = new Client("Jan", "Kowalski");
        Account acc1 = new Account(client1, "79 1050 0028 1000 0024 1009 3963");
        operation = new Operation(client1, acc1, 100.0, acc1.getBalance());
    }

    @Test
    @DisplayName("Checking if the given string complies with the date format - success")
    public void testIsValidSuccess() {
        Assertions.assertTrue(operation.isValid("12-01-2002 18:33:20"));
    }

    @Test
    @DisplayName("Checking if the given string complies with the date format - failure")
    public void testIsValidFailure() {
        Assertions.assertFalse(operation.isValid("12/01/2002 28:33:20"));
    }

    @Test
    @DisplayName("Correct change of date")
    public void testChangeDate() throws ParseException {
        operation.changeDate("19-03-2021 22:10:05");
        SimpleDateFormat sdt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        Assertions.assertEquals("19-03-2021 22:10:05", sdt.format(operation.getDate()));
    }

}
