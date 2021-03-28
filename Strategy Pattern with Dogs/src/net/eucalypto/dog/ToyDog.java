package net.eucalypto.dog;

import net.eucalypto.behavior.bark.NoBark;
import net.eucalypto.behavior.run.NoRun;

public class ToyDog extends Dog {

    public ToyDog(){
        barkBehavior = new NoBark();
        runBehavior = new NoRun();
    }

    @Override
    public void display() {
        System.out.println("I'm a toy dog. You can cuddle me.");
    }
}
