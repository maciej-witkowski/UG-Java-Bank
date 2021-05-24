package test;

import main.Account;
import main.Client;
import main.History;
import main.Operation;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import java.text.ParseException;

public class HistoryTest {

    private History history;
    private Operation oper1;
    private Operation oper2;
    private Operation oper3;


    @Before
    public void setUp() throws Exception {
        history = new History();
        Client client0 = new Client("Testowy", "klient0");
        Client client1 = new Client("Testowa", "klient1");
        Account acc0 = new Account(client0, "79 1050 0028 1000 0024 1009 3963");
        Account acc1 = new Account(client1, "09 1040 0158 1000 0034 3089 4520");
        oper1 = new Operation(client0, acc0, 300, acc0.getBalance());
        oper2 = new Operation(client0, acc0, acc1, client1, 100, acc0.getBalance());
        oper3 = new Operation(client1, acc1, 300, acc1.getBalance());
    }

    @Test
    @DisplayName("Extend the database with new operation")
    public void testAddOperation() {
        history.addOperation(oper1);

        int currAmount = history.sum();

        history.addOperation(oper2);

        Assertions.assertEquals(currAmount + 1, history.sum());
    }

    @Test
    @DisplayName("Get size of records in history")
    public void testSum() {
        history.addOperation(oper1);
        history.addOperation(oper2);
        history.addOperation(oper3);

        Assertions.assertEquals(3, history.sum());
    }

    @Test
    @DisplayName("Get all records from history")
    public void testPrintOperations() {
        history.addOperation(oper1);
        history.addOperation(oper2);
        history.addOperation(oper3);

        Assertions.assertEquals(3, history.printOperations().size());
    }

    @Test
    @DisplayName("Get all records from history by date")
    public void testPrintOperationsWithDate() throws ParseException {
        history.addOperation(oper1);
        history.addOperation(oper2);
        history.addOperation(oper3);

        oper1.changeDate("17-03-2021 00:00:00");
        oper2.changeDate("18-03-2021 00:00:00");
        oper3.changeDate("19-03-2021 00:00:00");

        Assertions.assertEquals(2, history.printOperations("18-03-2021", "19-03-2021").size());
    }

    @Test
    @DisplayName("Get all records from history by datetime")
    public void testPrintOperationsWithDateTime() throws ParseException {
        history.addOperation(oper1);
        history.addOperation(oper2);
        history.addOperation(oper3);

        oper1.changeDate("17-03-2021 15:15:00");
        oper2.changeDate("18-03-2021 15:30:00");
        oper3.changeDate("19-03-2021 16:00:00");

        Assertions.assertEquals(1, history.printOperations("18-03-2021", "14:00:00", "19-03-2021", "10:50:00").size());
    }

    @Test
    @DisplayName("Wrong format during history filtering")
    public void testPrintOperationsWrongFormat() throws ParseException {
        history.addOperation(oper1);
        history.addOperation(oper2);
        history.addOperation(oper3);

        oper1.changeDate("17-03-2021 15:15:00");
        oper2.changeDate("18-03-2021 15:30:00");
        oper3.changeDate("19-03-2021 16:00:00");

        ParseException thrown = Assertions.assertThrows(
                ParseException.class,
                () -> history.printOperations("18/03/2021", "19/03/2021"),
                "Unparseable date: \"18/03/2021\""
        );

        Assertions.assertEquals(thrown.getMessage(), "Unparseable date: \"18/03/2021\"");
    }
}
