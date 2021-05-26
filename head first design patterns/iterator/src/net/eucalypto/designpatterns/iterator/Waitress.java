package net.eucalypto.designpatterns.iterator;

import net.eucalypto.designpatterns.iterator.diner.DinerMenu;
import net.eucalypto.designpatterns.iterator.pancakehouse.PancakeHouseMenu;

public class Waitress {

  PancakeHouseMenu pancakeHouseMenu;
  DinerMenu dinerMenu;

  public Waitress(
      PancakeHouseMenu pancakeHouseMenu,
      DinerMenu dinerMenu) {
    this.pancakeHouseMenu = pancakeHouseMenu;
    this.dinerMenu = dinerMenu;
  }

  public void printMenu() {
    var pancakeIterator = pancakeHouseMenu.createIterator();
    var dinerIterator = dinerMenu.createIterator();
    System.out.println("MENU\n----\nBREAKFAST");
    printMenu(pancakeIterator);
    System.out.println("\nLUNCH");
    printMenu(dinerIterator);
  }

  private void printMenu(Iterator iterator) {
    while (iterator.hasNext()) {
      var menuItem = iterator.next();
      System.out.println(
          menuItem.getName() + ", "
              + menuItem.getPrice() + " -- "
              + menuItem.getDescription());
    }
  }

  // other methods here
}
