import java.time.LocalDate;
import java.util.ArrayList;

public class BankApp2 {
    public static void main(String[] args) {
        Person p1 = new Person("Jimmy", "Hendrix", 12938, "129181-4398");
        Person p2 = new Person("Bobby", "Brown", 19283, "140389-1999");

        Account account1 = new Account( p1, 2.5);

        Account account2 = new Account( p2, 2.5);

        account1.addbal(100);
        account1.wdbal(20);
        account1.addbal(100);
        account1.wdbal(20);
        account1.addbal(100);
        account1.wdbal(20);
        account1.addbal(100);
        account1.wdbal(20);
        account1.addbal(100);
        account1.wdbal(20);
        account1.addinterest();
        account1.addbal(100);
        account1.wdbal(20);
        account1.addbal(100);
        account1.wdbal(20);

        account1.outputoverview();
        System.out.println();
        account2.outputoverview();
    }
}

//En klasse der kan lave bank-konti
class Account {
    protected int accountnr;              //kontonummer
    protected Person owner;               //Ejer defineret efter navn
    protected double balance;             //Beholdning i kroner
    protected double interestrate;        //Rantesats i procent
    protected ArrayList<Transaction> transactions = new ArrayList<>();
    static int noOfAccounts=0;

    public Account(Person own, double ir){
        noOfAccounts++;
        accountnr = noOfAccounts;
        owner = own;
        interestrate = ir;
        System.out.println("The account belonging to " + own + " has the number " + accountnr);
        own.konti.add(this);
    }

    public int getAccountnr(){
        return this.accountnr;
    }

    void addbal(double addamount){
        if (addamount>0) {
            balance = balance + addamount;
            transactions.add(new Transaction("indsat", addamount, balance));
        }
        else System.out.println("Invalid input");
    }

    void wdbal(double withdrawamount){
        if (withdrawamount>0) {
            balance = balance - withdrawamount;
            transactions.add(new Transaction("hævet", withdrawamount, balance));
        }
        else System.out.println("Invalid input");
    }

    void addinterest(){
        double interestamount = (balance/100)*interestrate;
        balance = balance+interestamount;
        transactions.add(new Transaction("Rente", interestamount, balance));
    }

    void outputoverview(){
        System.out.println(this);
        if (transactions.size()!= 0) {
            System.out.println("    Text\tDato\t\tBeløb\tSaldo");
            for (int x = 1; x <= 10; x++) {
                if ((transactions.size() - x) < 0)
                    continue;
                System.out.println("    " + transactions.get(transactions.size() - x));
            }
        }
        else {
            System.out.println("    Der er ingen bevægelser på denne konto");
        }
    }

    public String toString(){
        return "kontonr: " + accountnr + "\n    Ejet af: " + owner + "\n    Saldo: " + balance + ",- kr \n";
    }
}

//En klasse der kan lave personer
class Person {
    public String fornavn;
    private String efternavn;
    private String cpr;
    private int id;
    ArrayList<Account> konti = new ArrayList<>();

    public Person (String brugerfornavn, String brugerefternavn, int brugerID, String brugercpr){
        fornavn = brugerfornavn;
        efternavn = brugerefternavn;
        cpr = brugercpr;
        id = brugerID;
    }

    public int getId(){
        return this.id;
    }

    public void navneskift(String fn, String en){
        fornavn = fn;
        efternavn = en;
    }

    public String toString() {
        return fornavn + " " + efternavn + " (" + cpr + ") ID: " + id;
    }
}

//En klasse som kan lave transaktionsobjekter
class Transaction {
    String text;
    double amount;
    LocalDate date;
    double newbalance;

    public Transaction(String text, double amount, double newbalance){
        this.text = text;
        this.amount = amount;
        date = LocalDate.now();
        this.newbalance = newbalance;
    }

    public String toString(){
        return text + ":" + "\t" + date + "\t" + amount + "\t" + newbalance;
    }
}