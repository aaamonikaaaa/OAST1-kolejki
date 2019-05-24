import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.PoissonDistribution;
import org.apache.commons.math3.random.RandomGenerator;
import sun.rmi.runtime.Log;

import java.util.Collections;
import java.util.LinkedList;
import java.util.logging.Logger;


/**
 * Created by Mońka on 2019-05-23.
 */


public class Symulacja {
    private Zdarzenia zdarzenia;
    private int liczbaZdarzen = 10;
    private Serwer serwer;
    private double sredniCzasObslugi;
    private double czasSymulacji = 1500;
    private double aktualnyCzas;
    private ZapisywanieWynikow zapisywanieWynikow;
    protected final Logger log = Logger.getLogger(getClass().getName());

    public static double getExpotentialDistribution(double lambda){
        ExponentialDistribution A = new ExponentialDistribution(40);
        return A.sample();
    }

    public Zdarzenia rozpocznijZdarzenia (double lambda, boolean onOff){
        if(lambda>0) {
            LinkedList<Zdarzenia> zdarzeniaPrzychodzace = new LinkedList<>();
            LinkedList<Zdarzenia> zdarzeniaWychodzace = new LinkedList<>();
            for (int i = 0; i < liczbaZdarzen; i++) {
                //DOROBIC GENEROWANIE POISSONA
                PoissonDistribution C = new PoissonDistribution(100 * 1000);
                double czasPrzyjscia = (double) (C.sample()) / 1000;
                if (zdarzeniaPrzychodzace.stream().anyMatch(zdarzenie -> zdarzenie.getCzas() == czasPrzyjscia)) {
                    i--;
                } else
                    zdarzeniaPrzychodzace.add(new Zdarzenia(czasPrzyjscia, Zdarzenia.typZdarzenia.PRZYJSCIE_ZDARZENIA));
            }

            //wyłaczanie i włączanie systemu
            if (czasSymulacji > serwer.getSredniCzasWlaczonego() && onOff)
                zdarzeniaPrzychodzace.add(new Zdarzenia(getExpotentialDistribution(serwer.getSredniCzasWlaczonego()), Zdarzenia.typZdarzenia.SERWER_WYLACZONY));
            Collections.sort(zdarzeniaPrzychodzace, new PorownywanieZdarzen());



            return new Zdarzenia (zdarzeniaPrzychodzace, zdarzeniaWychodzace);
        }
        else

        return new Zdarzenia();

        }

    public Symulacja(double lambda){
        sredniCzasObslugi = 0.125;
        aktualnyCzas = 0;
        serwer = new Serwer();
        zdarzenia = rozpocznijZdarzenia(lambda, false);
        System.out.println("Symulacja zaczeta");
    }

    public void rozpocznijSymulacje(){
        while(!zdarzenia.getZdarzeniaPrzychodzace().isEmpty()){
            obluzZdarzenie(zdarzenia.get());
        }
        zapisywanieWynikow = new ZapisywanieWynikow();
        zapisywanieWynikow.zapisywanieWynikowDoPliku(zdarzenia.getZdarzeniaWychodzace());
    }
    private void obluzZdarzenie(Zdarzenia zdarzenia2){
        aktualnyCzas = zdarzenia2.getCzas();

        if(zdarzenia2.getTyp() == Zdarzenia.typZdarzenia.PRZYJSCIE_ZDARZENIA){
            obsluzPrzyjscie(zdarzenia2);
        }
        else if(zdarzenia2.getTyp()== Zdarzenia.typZdarzenia.WYJSCIE_ZDARZENIA){
            obsluzWyjscie(zdarzenia2);
        }
        else if (zdarzenia2.getTyp() == Zdarzenia.typZdarzenia.SERWER_WLACZONY){
            obsluzSerwerWlaczony(zdarzenia2);
        }
        else if (zdarzenia2.getTyp() == Zdarzenia.typZdarzenia.SERWER_WYLACZONY){
            obsluzSerwerWylaczony(zdarzenia2);
        }
    }

    //serwer nie jest zajety jak mamy 0 lub 1 klienta
    //
    private void obsluzPrzyjscie(Zdarzenia zdarzenia2){
        if(serwer.getLiczbaKlientow() ==0 || serwer.getLiczbaKlientow() == 1){
            zdarzenia2.setId(serwer.dodajKlienta());
            zdarzenia.put(new Zdarzenia(aktualnyCzas + getExpotentialDistribution(sredniCzasObslugi), Zdarzenia.typZdarzenia.WYJSCIE_ZDARZENIA, zdarzenia2.getId()));
        }
        else{
            zdarzenia2.setId(serwer.dodajKlienta());
        }
    }
    private void obsluzWyjscie(Zdarzenia zdarzenia2){
        serwer.usunKlienta();
        if(serwer.getLiczbaKlientow()> 0){
            zdarzenia.put(new Zdarzenia(aktualnyCzas + getExpotentialDistribution(sredniCzasObslugi),Zdarzenia.typZdarzenia.WYJSCIE_ZDARZENIA,(zdarzenia2.getId()+1)));
        }
    }
    private void obsluzSerwerWlaczony(Zdarzenia zdarzenia2){
        zdarzenia.put(new Zdarzenia(getExpotentialDistribution(serwer.getSredniCzasWylaczonego()), Zdarzenia.typZdarzenia.SERWER_WYLACZONY));
        zdarzenia.sortowanieZdarzen();
    }
    private void obsluzSerwerWylaczony(Zdarzenia zdarzenia2){
        double start = zdarzenia2.getCzas();
        double trwanie = getExpotentialDistribution(serwer.getSredniCzasWlaczonego());
        double stop = zdarzenia2.getCzas() + trwanie;
        zdarzenia.put(new Zdarzenia(stop, Zdarzenia.typZdarzenia.SERWER_WLACZONY));
        for(Zdarzenia z: zdarzenia.getZdarzeniaPrzychodzace())
            if(z.getCzas() >=start && z.getTyp().equals(Zdarzenia.typZdarzenia.WYJSCIE_ZDARZENIA) && z.getCzas() < stop ){
            z.setCzas(z.getCzas() + trwanie);
            }

        zdarzenia.sortowanieZdarzen();
    }


}





