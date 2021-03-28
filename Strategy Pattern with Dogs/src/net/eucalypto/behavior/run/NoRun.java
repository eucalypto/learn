package net.eucalypto.behavior.run;

public class NoRun implements RunBehavior {
    @Override
    public void performRun() {
        System.out.println("I cannot run, so I won't.");
    }
}
