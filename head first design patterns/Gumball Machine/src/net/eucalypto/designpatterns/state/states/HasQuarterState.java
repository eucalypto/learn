package net.eucalypto.designpatterns.state.states;

import java.util.Random;
import net.eucalypto.designpatterns.state.GumballMachine;

public class HasQuarterState implements State {

  Random randomGenerator = new Random(System.currentTimeMillis());
  GumballMachine gumballMachine;

  public HasQuarterState(
      GumballMachine gumballMachine) {
    this.gumballMachine = gumballMachine;
  }

  @Override
  public void insertQuarter() {
    System.out.println("You can't insert another quarter");
  }

  @Override
  public void ejectQuarter() {
    System.out.println("Quarter returned");
    gumballMachine.setState(gumballMachine.getNoQuarterState());
  }

  @Override
  public void turnCrank() {
    System.out.println("You turned...");

    var randomInt = randomGenerator.nextInt(10);
    var isWinner = randomInt == 0;
    var hasEnoughBalls = gumballMachine.getGumballCount() >= 2;

    if (isWinner && hasEnoughBalls) {
      gumballMachine.setState(gumballMachine.getWinnerState());
    } else {
      gumballMachine.setState(gumballMachine.getSoldState());
    }
  }

  @Override
  public void dispense() {
    System.out.println("You need to turn the crank");
  }

  @Override
  public String toString() {
    return "Has Quarter";
  }
}
