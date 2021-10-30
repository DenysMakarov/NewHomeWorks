package TaracanRaces.controller;

import TaracanRaces.model.Race;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.lang.System.out;

public class RaceApp {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            out.println("Inter count of Taracans:");
            String countOfTarakans = br.readLine();
            out.println("Inter dist:");
            String dist = br.readLine();

            Race race = new Race();
            race.startRace(countOfTarakans, dist);
        }

    }

}

