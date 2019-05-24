import java.util.Comparator;

/**
 * Created by Mońka on 2019-05-23.
 */
public class PorownywanieZdarzen implements Comparator<Zdarzenia> {
    @Override
    public int compare (Zdarzenia Zdarzenie_1, Zdarzenia Zdarzenie_2) {
        if (Zdarzenie_1.getCzas() > Zdarzenie_2.getCzas()) {
            return 1;
        }
        else if(Zdarzenie_1.getCzas() == Zdarzenie_2.getCzas()) {

            if ((Zdarzenie_1.getTyp() == Zdarzenia.typZdarzenia.PRZYJSCIE_ZDARZENIA ||
                    Zdarzenie_1.getTyp() == Zdarzenia.typZdarzenia.WYJSCIE_ZDARZENIA)
                    &&
                    (Zdarzenie_2.getTyp() == Zdarzenia.typZdarzenia.SERWER_WLACZONY ||
                            Zdarzenie_2.getTyp() == Zdarzenia.typZdarzenia.SERWER_WYLACZONY))
                return 1;
            else
                return -1;
        }
        else
            return-1;
    }
}
