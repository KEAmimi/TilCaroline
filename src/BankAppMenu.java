import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BankAppMenu {
    static Scanner tastatur = new Scanner(System.in);
    static int userInput = 9;
    static ArrayList<Person> brugere = new ArrayList<>();
    static ArrayList<Account> accounts = new ArrayList<>();
    public static void main(String[] args) {
        System.out.println("Velkommen til bank-appen! Vælg venligst hvad du gerne vil gøre:");
        boolean runProgram = true;

        while (runProgram){
            System.out.println("    1: Opret Konto");
            System.out.println("    2: Print Kontoudskrift");
            System.out.println("    3: Indsæt penge");
            System.out.println("    4: Hæv penge");
            System.out.println("    5: Årlig rentetilskrivning");
            System.out.println("    6: Opret bruger ");
            System.out.println("    0: Afslut programmet");

                    userInput = newUserInput();

                switch (userInput){
                    case 1:
                        try{
                            createAccount();
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 2:
                        try {
                            outPutTransactions();
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 3:
                        try {
                            depositMoney();
                        }
                        catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 4:
                        try {
                            withdrawMoney();
                        }
                        catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 5:
                        try{
                            yearlyInterest();
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 6:
                        opretBruger();
                        break;
                    case 0:
                        System.out.println("Du har valgt at lukke programmet. Tak fordi du brugte BankAppen.");
                        runProgram = false;
                        break;
                    default:
                        System.out.println("Invalid input, try again");
                        break;
                }
        }
    }

    static int newUserInput(){
        try{
            userInput = tastatur.nextInt();
        }
        catch (InputMismatchException e){
            System.out.println("Fejl");
            tastatur.next();
        }
        return userInput;
    }

    static void createAccount() throws Exception{
        System.out.println("Du har valgt at lave en ny konto. Indtast venligst bruger ID på den bruger du vil knytte kontoen til: ");
        int inputID = tastatur.nextInt();
        Person foundPerson = null;
        for (Person x : brugere){
            if (x.getId() == inputID){
                foundPerson = x;
            }
        }
        if (foundPerson == null){
            throw new Exception("Mistake encountered: Person not found");
        }
        System.out.println("Please enter your desired interest rate: ");
        double desiredInterestRate = tastatur.nextDouble();
        Account a = new Account(foundPerson, desiredInterestRate);
        System.out.println("Succesfully created account: \n" + a);
        accounts.add(a);
    }

    static void outPutTransactions() throws Exception{
        System.out.println("Du har valgt at printe en kontoudskrift. Indtast kontonummer på den konto der skal udskrives: ");
        int printAccountnr = tastatur.nextInt();
        Account printAccount = null;
        for (Account aPrint : accounts){
            if (aPrint.getAccountnr() == printAccountnr){
                printAccount = aPrint;
            }
        }
        if (printAccount == null){
            throw new Exception("Account not found");
        }
        printAccount.outputoverview();
    }

    static void depositMoney() throws Exception{
        System.out.println("Du har valgt at indsætte penge. Indtast kontonummer på den konto der skal have penge indsat: ");
        int depositAccountnr = tastatur.nextInt();
        Account depositAccount = null;
        for (Account aDeposit : accounts){
            if (aDeposit.getAccountnr() == depositAccountnr){
                depositAccount = aDeposit;
            }
        }
        if (depositAccount == null){
            throw new Exception("Account not found");
        }
        System.out.println("Indtast den mængde penge du gerne vil sætte ind på kontoen.");
        double depositAmount = tastatur.nextDouble();
        depositAccount.addbal(depositAmount);
        System.out.println("Du har sat " + depositAmount + " ind på konto: " + depositAccount);
    }

    static void withdrawMoney() throws Exception{
        System.out.println("Du har valgt at hæve penge. Indtast kontonummer på den konto der skal have hæves fra: ");
        int withdrawAccountnr = tastatur.nextInt();
        Account withdrawAccount = null;
        for (Account awithdraw : accounts){
            if (awithdraw.getAccountnr() == withdrawAccountnr){
                withdrawAccount = awithdraw;
            }
        }
        if (withdrawAccount == null){
            throw new Exception("Account not found");
        }
        System.out.println("Indtast den mængde penge du gerne vil sætte ind på kontoen.");
        double withdrawAmount = tastatur.nextDouble();
        withdrawAccount.wdbal(withdrawAmount);
    }

    static void yearlyInterest() throws Exception{
        System.out.println("Du har valgt at lave årlig rentetilskrivning. Laver rentetilskrivning til alle konti");
        try {
            for (Account interestAccount : accounts) {
                interestAccount.addinterest();
                System.out.println("Tilføjet rente til " + interestAccount + "\n");
            }
        } catch (Exception e) {
            throw new Exception("No accounts registered.");
        }
    }

    static void opretBruger(){
        System.out.println("Du har valgt at oprette en ny bruger. Indtast venligst brugerens fornavn.");
        String newBrugerFornavn = tastatur.next();
        System.out.println("Indtast den nye brugers efternavn:");
        String newBrugerEfternavn = tastatur.next();
        System.out.println("Indtast den nye brugers ID:");
        int newBrugerID = newUserInput();
        System.out.println("Indtast den nye brugers CPR-nummer:");
        String newBrugerCPR = tastatur.next();

        Person p = new Person(newBrugerFornavn, newBrugerEfternavn, newBrugerID, newBrugerCPR);
        brugere.add (p);
        System.out.println("Du har tilføjet: " + p);

    }
}
