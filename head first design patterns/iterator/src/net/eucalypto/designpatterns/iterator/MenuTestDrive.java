package net.eucalypto.designpatterns.iterator;

import net.eucalypto.designpatterns.iterator.diner.DinerMenu;
import net.eucalypto.designpatterns.iterator.pancakehouse.PancakeHouseMenu;

public class MenuTestDrive {

  public static void main(String[] args) {
    var pancakeHouseMenu = new PancakeHouseMenu();
    var dinerMenu = new DinerMenu();

    var waitress = new Waitress(pancakeHouseMenu, dinerMenu);

    waitress.printMenu();
  }

}
