package net.eucalypto.designpatterns.state.states;

public interface State {

  void insertQuarter();

  void ejectQuarter();

  void turnCrank();

  void dispense();

}
