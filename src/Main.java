public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        uruchomSymulacjeZad1();
        uruchomSymulacjeZad2();
        uruchomSymulacjeZad3();
    }

        public static void uruchomSymulacjeZad1(){
            double lambda = 0.5;
            while(lambda<6){
                Symulacja symulacja = new Symulacja(lambda);
                symulacja.rozpocznijSymulacje();
                lambda = lambda + 0.5;
            }
            zapiszDoPliku("Wyniki", wyniki);
        }

    public static void uruchomSymulacjeZad2(){}
    public static void uruchomSymulacjeZad3(){}
    }
