package lightspeed;

import lightspeed.entities.Wheel;
import lightspeed.object_cloner.Cloner;
import lightspeed.entities.Car;
import lightspeed.entities.CarEngine;
import lightspeed.entities.Man;

import java.util.Arrays;

/**
 * @author Sergei Aleshchenko
 */
public class Solution {
    public static void main(String[] args) {
        Cloner cloner = new Cloner();

        Man man = new Man("Sergei", 34,
                Arrays.asList(
                        "Harry Potter",
                        "Joshua Bloch",
                        "Something else"),
                Arrays.asList(
                        new Car("Audi", 2024, new CarEngine("model 1", 2000),
                                Arrays.asList(
                                        new Wheel(18,22),
                                        new Wheel(18,22),
                                        new Wheel(18,22),
                                        new Wheel(18,22))),
                        new Car("BMW", 2023, new CarEngine("model 2", 2500),
                                Arrays.asList(
                                        new Wheel(20,22),
                                        new Wheel(20,22),
                                        new Wheel(20,22),
                                        new Wheel(20,22))),
                        new Car("Volvo", 2020, new CarEngine("model 1", 5000),
                                Arrays.asList(
                                        new Wheel(24,22),
                                        new Wheel(24,22),
                                        new Wheel(24,22),
                                        new Wheel(24,22)))));


        Man copyOfMan = cloner.deepClone(man);

        System.out.println("===============================================================");
        System.out.println(man);
        System.out.println(copyOfMan);
    }
}
