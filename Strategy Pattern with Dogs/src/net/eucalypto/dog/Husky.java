package net.eucalypto.dog;

import net.eucalypto.behavior.bark.Bark;
import net.eucalypto.behavior.run.Run;

public class Husky extends Dog {

    public Husky() {
        barkBehavior = new Bark();
        runBehavior = new Run();
    }


    @Override
    public void display() {
        System.out.println("I'm a Husky. I'm friendly and full of energy.");
    }
}
