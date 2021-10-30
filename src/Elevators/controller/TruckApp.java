package Elevators.controller;

import Elevators.model.Elevator;
import Elevators.model.Truck;


public class TruckApp {
    private static final int N_TRUCKS = 1000;
    private static final int N_RACES = 1;
    private static final int CAPACITY = 20;

    public static void main(String[] args) throws InterruptedException {
        Elevator elevator1 = new Elevator(" Elevator_1");
        Elevator elevator2 = new Elevator(" Elevator_2");
        long start = System.currentTimeMillis();

        Truck[] trucks = new Truck[N_TRUCKS];
        for (int i=0; i<trucks.length; i++){
            if (i < trucks.length/2){
                trucks[i] = new Truck(N_RACES, CAPACITY, elevator1, elevator2);
            }
            else {
                trucks[i] = new Truck(N_RACES, CAPACITY, elevator2, elevator1);
            }
        }

        Thread[] threads = new Thread[N_TRUCKS];
        for (int i=0; i<threads.length; i++){
            threads[i] = new Thread(trucks[i]);
        }

        for (int i=0; i<threads.length; i++){
                threads[i].start();
        }

        for (int i=0; i<threads.length; i++){
            threads[i].join();
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println("Elevator" + elevator1.getName() + " has " + elevator1.getCurrentValue() + " tonn");
        System.out.println("Elevator" + elevator2.getName() + " has " + elevator2.getCurrentValue() + " tonn");
    }
}
