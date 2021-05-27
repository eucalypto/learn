package net.eucalypto.designpatterns.iterator;

import net.eucalypto.designpatterns.iterator.cafe.CafeMenu;
import net.eucalypto.designpatterns.iterator.diner.DinerMenu;
import net.eucalypto.designpatterns.iterator.pancakehouse.PancakeHouseMenu;

public class MenuTestDrive {

  public static void main(String[] args) {
    var pancakeHouseMenu = new PancakeHouseMenu();
    var dinerMenu = new DinerMenu();
    var cafeMenu = new CafeMenu();

    var waitress = new Waitress(pancakeHouseMenu, dinerMenu, cafeMenu);

    waitress.printMenu();
  }

}
