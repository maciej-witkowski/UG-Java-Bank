package main;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Operation {

    private final String id;
    private final Client cltFrom;
    private final Client cltTo;
    private final Account accFrom;
    private final Account accTo;
    private final double amount;
    private final double balance;
    private final String type;
    private Date date;

    public Operation(Client cltFrom, Account accFrom, Account accTo, Client cltTo, double amount, double balance) {
        this.cltFrom = cltFrom;
        this.cltTo = cltTo;
        this.accFrom = accFrom;
        this.accTo = accTo;
        this.amount = amount;
        this.balance = balance;
        this.type = "transfer";
        this.date = new Date();
        this.id = UUID.randomUUID().toString();
    }

    public Operation(Client cltFrom, Account accFrom, double amount, double balance) {
        this.cltFrom = cltFrom;
        this.cltTo = null;
        this.accFrom = accFrom;
        this.accTo = null;
        this.amount = amount;
        this.balance = balance;
        this.type = "in/out";
        this.date = new Date();
        this.id = UUID.randomUUID().toString();
    }

    public Date getDate() {
        return this.date;
    }

    public boolean isValid(String value) {
        DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            sdf.parse(value);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public void changeDate(String newDate) throws ParseException {
        if (this.isValid(newDate)) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            this.date = sdf.parse(newDate);
        }
    }

    public void print() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        if (this.type.equals("transfer")) {
            System.out.println(
                    "\n######################################################################" +
                    "\nTranskacja -" + this.id + "-" +
                    "\n\nDane płatnika:" +
                    "\nNr. rachunku: " + this.accFrom.getNumber() +
                    "\nImię i nazwisko: " + this.cltFrom.getFullName() +
                    "\n\nDane odbiorcy:" +
                    "\nNr. rachunku: " + this.accTo.getNumber() +
                    "\nImię i nazwisko: " + this.cltTo.getFullName() +
                    "\n\nDane transakcji:" +
                    "\nData transakcji: " + formatter.format(this.date) +
                    "\nKwota transakcji: " + this.amount +
                    "\nSaldo po transkacji: " + this.balance +
                    "\n######################################################################"
            );
        } else {
            System.out.println(
                    "\n######################################################################" +
                    "\nTranskacja -" + this.id + "-" +
                    "\n\nDane właściciela:" +
                    "\nNr. rachunku: " + this.accFrom.getNumber() +
                    "\nImię i nazwisko: " + this.cltFrom.getFullName() +
                    "\n\nDane transakcji:" +
                    "\nData transakcji: " + formatter.format(this.date) +
                    "\nKwota transakcji: " + this.amount +
                    "\nSaldo po transkacji: " + this.balance +
                    "\n######################################################################"
            );
        }
    }
}
