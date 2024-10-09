public class BankApp1 {
    public static void main(String[] args) {
        String navn = "Bøllemand";
        double saldo = 125644.95;
        int knr = 46;

        System.out.println("Hej "+navn+", du har " +saldo+ ",- kr Stående på din konto, med kontonummer: " +knr);

        saldo = saldo + 100;

        System.out.println("Hej "+navn+", du har sat penge ind på din konto, og har nu " +saldo+ ",- kr Stående på din konto, med kontonummer: " +knr);

        saldo = saldo*1.05;

        System.out.println("Hej "+navn+", du har modtaget renter, og har nu " +saldo+ ",- kr Stående på din konto, med kontonummer: " +knr);
        System.out.println("konto " + knr + ":" + " Saldo = " +saldo+ " " +navn);
    }
}
