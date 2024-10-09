import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class BankApp3 {
    public static void main(String[] args) {
        Person p1 = new Person("Simon", "Andersen", 89, "102948-1298");
        Person p2 = new Person("Heidi", "Andersen", 90, "300824-1876");

        Borneopsparing bo1 = new Borneopsparing(p2, 4, p1, LocalDate.of(2024,9,17), 30000);
        bo1.addbal(100);
        bo1.addbal(100);
        bo1.addbal(100);
        bo1.addbal(100);
        bo1.addbal(100);
        bo1.outputoverview();
        bo1.wdbal(200);
        bo1.outputoverview();

        Millikonto mk1 = new Millikonto(p1);
        Millikonto mk2 = new Millikonto(p2);
        Millikonto mk3 = new Millikonto(p1);
        Millikonto mk4 = new Millikonto(p2);
        Millikonto mk5 = new Millikonto(p1);
        Millikonto mk6 = new Millikonto(p2);
        Millikonto mk7 = new Millikonto(p1);
        Millikonto mk8 = new Millikonto(p2);

        mk1.addinterest();
        mk2.addinterest();

        millionærlotteri();

        mk1.outputoverview();
        mk2.outputoverview();
        mk3.outputoverview();
        mk4.outputoverview();
        mk5.outputoverview();
        mk6.outputoverview();
        mk7.outputoverview();
        mk8.outputoverview();

/*
        Indlaanskonto ik1 = new Indlaanskonto(p1, 3);
        ik1.addbal(200);
        ik1.wdbal(300);
        ik1.addbal(200);
        ik1.wdbal(300);
        System.out.println(ik1.balance);
        ik1.outputoverview();

        Hojrentrekonto hk1 = new Hojrentrekonto(p1, 10, 100000, LocalDate.of(2029,12,31));
        hk1.outputoverview();
        System.out.println(p1.konti);
        hk1.addbal(1000);
        hk1.wdbal(1000);
        hk1.outputoverview();

        hk1.releasedate=LocalDate.of(2023,06,20);
        hk1.wdbal(50000);
        hk1.outputoverview();


        for (Account a : p1.konti){
            a.addinterest();
            a.outputoverview();
        }

        Kreditkonto kk1 = new Kreditkonto(p1, 2, 25000);
        kk1.addbal(10000); //Ida har fået løn
        kk1.wdbal(15000); //Ida købet en brugt motorcykel
        kk1.wdbal(30000); //Ida får en bøde fra biblo
        kk1.outputoverview();

 */
    }

static void millionærlotteri(){
        Random gen = new Random();
        int vindernummer = gen.nextInt(Millikonto.millikontoantal)+1;
        for (Millikonto k: Millikonto.lotterypool){
            k.lotterycheck(vindernummer);
        }
}

}

class Indlaanskonto extends Account{

    Indlaanskonto(Person own, double ir){
        super(own, ir);
        System.out.println("Indlånskonto skabt succes");
    }

    void wdbal(double withdrawamount){
        if (balance<withdrawamount){
            System.out.println("Du har ikke nok penge til at hæve " + withdrawamount + ",- kroner");
            transactions.add(new Transaction("block", withdrawamount, balance));
        }
        else {
            super.wdbal(withdrawamount);
        }
    }
}

class Hojrentrekonto extends Account{
    LocalDate releasedate;

    Hojrentrekonto(Person own, double ir, double startkapital, LocalDate releasedate){
        super(own, ir);
        this.releasedate=releasedate;
        this.balance = startkapital;
        Transaction t = new Transaction("Indsat", startkapital, balance);
        transactions.add(t);
    }


    void addbal(double addamount){
        System.out.println("DET MÅ DU IKKE");
        transactions.add(new Transaction("block", addamount, balance));
    }


    void wdbal(double withdrawamount){
        LocalDate currentdate=LocalDate.now();

        if (currentdate.isBefore(releasedate)){
            System.out.println("DET MÅ DU IKKE, pengene er bundet indtil: " + releasedate);
            transactions.add(new Transaction("block", withdrawamount, balance));
        }
        else {
            super.wdbal(withdrawamount);
        }
    }
}

class Kreditkonto extends Account{
double overdraftlimit;

    Kreditkonto(Person own, double ir, double overdraftlimit){
        super(own, ir);
        this.overdraftlimit=overdraftlimit;
    }

    void wdbal(double withdrawamount){
        if (balance-withdrawamount<(-25000)){
//            System.out.println("HALLÅ DET MÅ DU IKKE");
            super.wdbal(withdrawamount);
            System.out.println("Brev sendt til kunde.");
            this.balance=this.balance-1000;
            transactions.add(new Transaction("bøde", 1000, balance));
        }
        else {
            super.wdbal(withdrawamount);
        }
    }

}

class Borneopsparing extends Account{
    Person opretter;
    LocalDate birthdate;
    LocalDate releasedate;

    Borneopsparing (Person own, double ir, Person opretter, LocalDate birthdate, double startpenge){
        super(own, ir);
        this.balance=startpenge;
        this.birthdate = birthdate;
        this.releasedate = LocalDate.of(birthdate.getYear()+18, birthdate.getMonth(), birthdate.getDayOfMonth());
        this.opretter=opretter;
    }

    void wdbal(double withdrawamount){
        LocalDate today = LocalDate.now();
        if (today.isBefore(releasedate)){
            System.out.println("Du er endnu ikke myndig, og kan derfor ikke hæve penge. Dato for myndighed: " + releasedate);
        }
        else {
            super.wdbal(withdrawamount);
        }
    }
}

class Millikonto extends Account{
    static int millikontoantal = 0;
    private int lotteriplads;
    static ArrayList<Millikonto> lotterypool = new ArrayList<>();

    Millikonto(Person own){
        super(own, 0);
        millikontoantal++;
        this.lotteriplads=millikontoantal;
        lotterypool.add(this);
    }

    void lotterycheck(int vn){
        if (vn==this.lotteriplads){
            System.out.println("Tillykke " + owner.fornavn + ", de har vundet lotteriet, og modtager da en million kroner.");
            this.balance=this.balance+1000000;
            Transaction t = new Transaction("Gevinst:", 1000000, balance);
        }
        else {
            System.out.println("Desværre " + owner.fornavn + ", du har ikke vundet i denne omgang.");
        }
    }

    void addinterest(){
        System.out.println("Da de har en millionær konto modtager de ikke rente.");
        /*
        Random gen = new Random();
        int vindertal = gen.nextInt(millikontoantal)+1;
        if (vindertal==this.lotteriplads){
            System.out.println("TILLYKKE");
            this.balance=balance+1000000;
            Transaction lotteri = new Transaction("Vinder", 1000000, balance);
            transactions.add(lotteri);
        }
        else{
            System.out.println("Desværre...");
        }

         */
    }
}