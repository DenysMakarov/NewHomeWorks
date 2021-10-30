package TaracanRaces.model;

public class Cockroach implements Runnable {
    private String name;
    private int dist;
    private int chunks = 0;
    private final int MIN = 2;
    private final int MAX = 5;
    private static String winner;

    public static String getWinner() {
        return winner;
    }

    public Cockroach(String name, int dist) {
        this.name = name;
        this.dist = dist;
    }

    private int randomMs(int min, int max){
        return (int) ((Math.random() * ( (max) - min )) + min);
    }

    public void setWinner(String name){
        synchronized (Cockroach.class){
            winner = name;
        }
    }

    @Override
    public void run() {
        int time;
        for (int i = 0; i < dist; i++) {
            time = randomMs(MIN, MAX);
            try {
                Thread.sleep(time);
//                System.out.println(name + " finished chunk " + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.chunks++;

            if (chunks == dist) {
                if (winner == null){
                    setWinner(name);
                }
                System.out.println("Finshed = " + name);
            }
        }
    }
}
