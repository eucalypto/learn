package net.eucalypto.designpatterns.state.states;

import net.eucalypto.designpatterns.state.GumballMachine;

public class SoldState implements State {

  GumballMachine gumballMachine;

  public SoldState(
      GumballMachine gumballMachine) {
    this.gumballMachine = gumballMachine;
  }

  @Override
  public void insertQuarter() {
    System.out.println(
        "Please wait, we're already giving you a gumball");
  }

  @Override
  public void ejectQuarter() {
    System.out.println(
        "Sorry, you already turned the crank");
  }

  @Override
  public void turnCrank() {
    System.out
        .println("Turning twice doesn't get you another gumball!");
  }

  @Override
  public void dispense() {
    System.out.println("A gumball comes rolling out the slot");
    gumballMachine.removeGumball();
    if (gumballMachine.getGumballCount() == 0) {
      System.out.println("Out of gumballs");
      gumballMachine.setState(gumballMachine.getSoldOutState());
    } else {
      gumballMachine.setState(gumballMachine.getNoQuarterState());
    }
  }

  @Override
  public String toString() {
    return "Sold";
  }
}
