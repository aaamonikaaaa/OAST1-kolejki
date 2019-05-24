/**
 * Created by Mońka on 2019-05-23.
 */

//kolejka M/M/1
public class Serwer {
    int liczbaKlientow;
    int calkowitaLiczbaKlientow;
    int sredniCzasWlaczonego = 35;
    int sredniCzasWylaczonego = 40;
    public int getSredniCzasWlaczonego(){return sredniCzasWlaczonego;}
    public int getSredniCzasWylaczonego(){return sredniCzasWylaczonego;}


    public Serwer(){
        liczbaKlientow = 0;
        calkowitaLiczbaKlientow = 0;
    }

    public int getLiczbaKlientow(){return liczbaKlientow;}

    public int usunKlienta(){
        if (liczbaKlientow > 0)
        liczbaKlientow --;
        return liczbaKlientow;
    }

    public int dodajKlienta(){
        liczbaKlientow ++;
        calkowitaLiczbaKlientow ++;
        return calkowitaLiczbaKlientow;
    }
}
