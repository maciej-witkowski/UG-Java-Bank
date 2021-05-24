package main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

public class History {
    public final ArrayList<Operation> history;

    public History() {
        this.history = new ArrayList<>();
    }

    public void addOperation(Operation operation) {
        this.history.add(operation);
    }

    public int sum() {
        return this.history.size();
    }

    public ArrayList<Operation> printOperations() {
        return this.history;
    }

    public ArrayList<Operation> printOperations(String fromDate, String toDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        Calendar c = Calendar.getInstance();

        c.setTime(sdf.parse(fromDate));
        c.add(Calendar.DATE, -1);

        Date fromFormatted = c.getTime();

        c.setTime(sdf.parse(toDate));
        c.add(Calendar.DATE, 1);

        Date toFormatted = c.getTime();

        return (ArrayList<Operation>) this.history.stream()
                .filter(date -> date.getDate().before(toFormatted) && date.getDate().after(fromFormatted))
                .collect(Collectors.toList());
    }

    public ArrayList<Operation> printOperations(String fromDate, String fromTime, String toDate, String toTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        Date fromFormatted = sdf.parse(fromDate + " " + fromTime);
        Date toFormatted = sdf.parse(toDate + " " + toTime);

        return (ArrayList<Operation>) this.history.stream()
                .filter(date -> date.getDate().before(toFormatted) && date.getDate().after(fromFormatted))
                .collect(Collectors.toList());
    }

}
