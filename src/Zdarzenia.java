import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by Mońka on 2019-05-23.
 */
//Klasa tworząca zdarzenia przychodzące i wychodzące
public class Zdarzenia{

    private double czas;
    public enum typZdarzenia {PRZYJSCIE_ZDARZENIA, WYJSCIE_ZDARZENIA, SERWER_WLACZONY, SERWER_WYLACZONY}
    private typZdarzenia typ;
    private int id;

    public Zdarzenia(double czas, typZdarzenia typ) {
        this.czas = czas;
        this.typ = typ;
        id = -1;
    }
    public Zdarzenia(double czas, typZdarzenia typ, int id) {
        this.czas = czas;
        this.typ = typ;
        this.id = id;
    }

    //czas
    public double getCzas() {
        return czas;
    }
    public void setCzas(double czas) {this.czas = czas;}
    //typZdarzenia
    public typZdarzenia getTyp() {
        return typ;
    }
   // public void setTyp(typZdarzenia) {this.typ = typ;}
    //id
    public int getId() {
        return id;
    }
    public void setId(int id) {this.id = id;}

    @Override
    public String toString() {
        return "Zdarzenia{" + "czas =" + czas + " " + "type = " + typ + '}';
    }


    private LinkedList <Zdarzenia> zdarzeniaPrzychodzace;
    private LinkedList <Zdarzenia> zdarzeniaWychodzace;

    public Zdarzenia(){
        zdarzeniaWychodzace = new LinkedList<Zdarzenia>();
        zdarzeniaPrzychodzace = new LinkedList<Zdarzenia>();
    }

    public Zdarzenia(LinkedList <Zdarzenia> zdarzeniaPrzychodzace, LinkedList <Zdarzenia> zdarzeniaWychodzace){
        this.zdarzeniaPrzychodzace = zdarzeniaPrzychodzace;
        sortowanieZdarzen(zdarzeniaPrzychodzace);
        this.zdarzeniaWychodzace = zdarzeniaWychodzace;
        sortowanieZdarzen(zdarzeniaWychodzace);

    }

    public void sortowanieZdarzen(LinkedList <Zdarzenia> listaZdarzen){
        Collections.sort(listaZdarzen, new PorownywanieZdarzen());
    }
    public void sortowanieZdarzen (){Collections.sort(this.zdarzeniaPrzychodzace, new PorownywanieZdarzen());}

    public Zdarzenia get(){
        if(!this.zdarzeniaPrzychodzace.isEmpty()){
            Zdarzenia nastepne = this.zdarzeniaPrzychodzace.removeLast();
            this.zdarzeniaWychodzace.add(nastepne);
            return nastepne;
        }
        else
            return null;
    }


    public void put (Zdarzenia Zdarzenie){
        zdarzeniaPrzychodzace.addLast(Zdarzenie);
        sortowanieZdarzen(zdarzeniaPrzychodzace);
    }

    public LinkedList<Zdarzenia> getZdarzeniaPrzychodzace(){return zdarzeniaPrzychodzace;}
    public LinkedList<Zdarzenia> getZdarzeniaWychodzace (){return this.zdarzeniaWychodzace;}
}
