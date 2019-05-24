import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Mońka on 2019-05-24.
 */
public class ZapisywanieWynikow2 {
    public void zapisywanieWynikowDoPliku2(LinkedList<Zdarzenia> listaZdarzen) {

        HashMap<Integer, Double> czasObslugi = new HashMap<>();
        double suma = 0;
        double srednieOpoznienie;


        for (Zdarzenia zdarzenia1 : listaZdarzen) {
            if (zdarzenia1.getTyp() == Zdarzenia.typZdarzenia.PRZYJSCIE_ZDARZENIA) {
                czasObslugi.put(zdarzenia1.getId(), zdarzenia1.getCzas());
            } else if (zdarzenia1.getTyp() == Zdarzenia.typZdarzenia.WYJSCIE_ZDARZENIA) {
                czasObslugi.replace(zdarzenia1.getId(), zdarzenia1.getCzas() - czasObslugi.get(zdarzenia1.getId()));
            }
        }
        for (double D : czasObslugi.values()) {
            suma += D;
        }
        srednieOpoznienie = suma / czasObslugi.size();
        //String wyniki = "Srednie opozienie" + srednieOpoznienie  + "\n" + czasObslugi.toString();
        //zapiszDoPliku("Wyniki.txt");
        String wyniki = "\n" + czasObslugi.toString()+ "\n" +"Srednie opoznienie = " + srednieOpoznienie;
        zapiszDoPliku2("Wyniki2", wyniki);

    }
    // System.out.println("Srednie opoznienie:\t" + srednieOpoznienie + "\n" + czasObslugi.toString());

    //private void zapiszDoPliku() throws FileNotFoundException {
    //PrintWriter zapisz = new PrintWriter("c:\\Users\\Mońka\\Desktop\\Kolejka_Wynik\\wynik.txt");
    //zapisz.println("Srednie opoznienie:\t" + );

    private void zapiszDoPliku2 (String nazwa, String zapisz){

        File plik = new File(nazwa);
        BufferedWriter bufferedWriter = null;
        try{
            bufferedWriter = new BufferedWriter(new FileWriter(plik));
            bufferedWriter.write(zapisz);

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
