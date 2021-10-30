package TaracanRaces.model;

import TaracanRaces.interfaces.IRace;

import java.io.*;

import static java.lang.System.*;

public class Race implements IRace {

    public void startRace(String ... args) {

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("src/results.txt"))) {
        } catch (IOException e) {
            e.printStackTrace();
        }

        int count = Integer.parseInt(args[0]);
        int dist = Integer.parseInt(args[1]);
        out.println("In competition " + count + " cockroaches and the distance: " + dist + " meters");
        out.println("...3 ...2 ...1 ...START");

        Cockroach[] cockroaches = new Cockroach[count];
        for (int i = 0; i < count; i++) {
            cockroaches[i] = new Cockroach("Tarakan № " + i, dist);
        }

        Thread[] threads = new Thread[count];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(cockroaches[i]);
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }

        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        out.println("---- Winner ---- : " + Cockroach.getWinner());
    }
}

