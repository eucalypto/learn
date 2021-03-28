package net.eucalypto;

import net.eucalypto.behavior.bark.Bark;
import net.eucalypto.behavior.run.Run;
import net.eucalypto.dog.Dog;
import net.eucalypto.dog.Husky;
import net.eucalypto.dog.RobotDog;
import net.eucalypto.dog.ToyDog;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Dog> myDogs = new ArrayList<>();
        myDogs.add(new Husky());
        myDogs.add(new RobotDog());
        myDogs.add(new ToyDog());

        for (Dog dog : myDogs) {
            dog.display();
            dog.exist();
            dog.bark();
            dog.run();

            System.out.println("\n");
        }

        System.out.println("The Wizard performs a spell and gives\n" +
                "the toy dog the ability to run and bark.\n" +
                "Let's look at what it does now:\n");

        Dog toyDog = myDogs.get(2);
        toyDog.setBarkBehavior(new Bark());
        toyDog.setRunBehavior(new Run());

        toyDog.display();
        toyDog.exist();
        toyDog.bark();
        toyDog.run();

    }
}
