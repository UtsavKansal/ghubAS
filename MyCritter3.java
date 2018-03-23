package assignment4;

import java.util.*;

public class MyCritter3 extends Critter.TestCritter {

    @Override
    public void doTimeStep() {
        int dir = getRandomInt(8);
        walk(dir);

        MyCritter3 offspring = new MyCritter3();
        reproduce(offspring,0);
    }

    @Override
    public boolean fight(String opponent) {
        if (getEnergy() > 10) return true;
        return false;
    }

    public String toString() {
        return "3";
    }
}