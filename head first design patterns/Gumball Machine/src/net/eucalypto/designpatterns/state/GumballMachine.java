package net.eucalypto.designpatterns.state;

import net.eucalypto.designpatterns.state.states.HasQuarterState;
import net.eucalypto.designpatterns.state.states.NoQuarterState;
import net.eucalypto.designpatterns.state.states.SoldOutState;
import net.eucalypto.designpatterns.state.states.SoldState;
import net.eucalypto.designpatterns.state.states.State;
import net.eucalypto.designpatterns.state.states.WinnerState;

public class GumballMachine {

  State noQuarterState = new NoQuarterState(this);
  State hasQuarterState = new HasQuarterState(this);
  State soldOutState = new SoldOutState(this);
  State soldState = new SoldState(this);
  State winnerState = new WinnerState(this);
  State state = noQuarterState;
  int gumballCount = 0;

  public GumballMachine(int gumballCount) {
    this.gumballCount = gumballCount;
    if (gumballCount > 0) {
      state = noQuarterState;
    }
  }

  public State getNoQuarterState() {
    return noQuarterState;
  }

  public State getHasQuarterState() {
    return hasQuarterState;
  }

  public State getSoldOutState() {
    return soldOutState;
  }

  public int getGumballCount() {
    return gumballCount;
  }

  public State getSoldState() {
    return soldState;
  }

  public State getWinnerState() {
    return winnerState;
  }

  public void insertQuarter() {
    state.insertQuarter();
  }

  public void ejectQuarter() {
    state.ejectQuarter();
  }

  public void turnCrank() {
    state.turnCrank();
    state.dispense();
  }

  public void setState(State state) {
    this.state = state;
  }

  public void releaseGumball() {
    System.out.println("A gumball comes rolling out the slot");
    gumballCount--;
  }

  @Override
  public String toString() {
    return "GumballMachine{" +
        "state=" + state +
        ", gumballCount=" + gumballCount +
        '}';
  }
}
