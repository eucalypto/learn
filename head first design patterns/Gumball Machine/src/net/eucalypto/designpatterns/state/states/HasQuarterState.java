package net.eucalypto.designpatterns.state.states;

import net.eucalypto.designpatterns.state.GumballMachine;

public class HasQuarterState implements State {

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
    gumballMachine.setState(gumballMachine.getSoldState());
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
