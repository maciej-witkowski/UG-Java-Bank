package main;

public class Main {
    public static void main(String[] args) throws Exception {

        Bank UGBank = new Bank();
        Client klient0 = UGBank.newClient("Maciej", "Witkowski");
        Client klient2 = UGBank.newClient("Filip", "Chmaj");
        Client klient3 = UGBank.newClient("Paweł", "Sienkowski");

        Account konto2_0 = klient2.newAccount("02 1050 1139 1000 0097 3204 7411");
        Account konto0_0 = klient0.newAccount("79 1050 0028 1000 0024 1009 3963");
        Account konto3_0 = klient3.newAccount("09 1040 0158 1000 0034 3089 4520");
        Account konto3_1 = klient3.newAccount("42 1010 0008 1000 0056 2009 1748");

        System.out.println("\nLista kont klienta3");
        klient3.info();

        System.out.println("\nSaldo dla konta0 klienta0");
        System.out.println(konto0_0.getBalance());

        System.out.println("\nNumer konta0 klienta2");
        System.out.println(konto2_0.getNumber());

        System.out.println("\nWpłata kwoty 853 zł na konta0 klienta3");
        konto3_0.deposit(853);

        System.out.println("\nWypłata kwoty 285.36 zł z konta0 klienta3");
        konto3_0.withdraw(285.36);

        System.out.println("\nSaldo dla konta0 klienta3 po wcześniejszych operacjach");
        System.out.println(konto3_0.getBalance());

        System.out.println("\nPrzelew na kwotę 69.02 zł z konta0 klienta3) na konto1 klienta3");
        konto3_0.transaction(klient3, konto3_1, 69.02);

        System.out.println("\nSaldo dla konta0 klienta3 po wcześniejszych operacjach");
        System.out.println(konto3_0.getBalance());

        System.out.println("\nSaldo dla konta1 klienta3 po wcześniejszych operacjach");
        System.out.println(konto3_1.getBalance());

        System.out.println("\nPrzelew na kwotę 20 zł z konto1 klienta3 na konto0 klienta2");
        konto3_1.transaction(klient2, konto2_0, 20);

        System.out.println("\nSaldo dla konta1 klienta3 po wcześniejszych operacjach");
        System.out.println(konto3_1.getBalance());

        System.out.println("\nSaldo dla konta0 klienta2 po wcześniejszych operacjach");
        System.out.println(konto2_0.getBalance());

        System.out.println("\nWyciąg dla konta0 klienta3 po wcześniejszych operacjach");
        konto3_0.accountStatement();

        System.out.println("\nWyciąg dla konta1 klienta3 po wcześniejszych operacjach");
        konto3_1.accountStatement();

        System.out.println("\nWszyscy klienci NASZEGO banku: ");
        UGBank.info();

        konto3_0.info();

        konto0_0.deposit(300);
        Operation oper1 = new Operation(klient0, konto0_0, konto2_0, klient2, 85.0, 215.0);
        Operation oper2 = new Operation(klient0, konto0_0, konto2_0, klient2, 65.0, 150.0);
        Operation oper3 = new Operation(klient0, konto0_0, konto2_0, klient2, 50.0, 100.0);

        konto0_0.history.addOperation(oper1);
        konto0_0.history.addOperation(oper2);
        konto0_0.history.addOperation(oper3);

        oper1.changeDate("16-03-2021 15:15:15");
        oper2.changeDate("17-03-2021 15:15:15");
        oper3.changeDate("18-03-2021 15:15:15");

        konto0_0.accountStatement("16-03-2021", "00:00:00", "17-03-2021", "15:15:16");
        konto0_0.accountStatement("17-03-2021", "18-03-2021");

    }
}
