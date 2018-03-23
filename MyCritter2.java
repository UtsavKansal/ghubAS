package assignment4;

import java.util.*;

public class MyCritter2 extends Critter.TestCritter {

    @Override
    public void doTimeStep() {
        walk(2);
    }

    @Override
    public boolean fight(String opponent) {
        if (getEnergy() > 5) return true;
        return false;
    }

    public String toString() {
        return "2";
    }
}