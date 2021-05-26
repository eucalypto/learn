package net.eucalypto.designpatterns.iterator;

import java.util.Iterator;

public class Waitress {

  Menu pancakeHouseMenu;
  Menu dinerMenu;

  public Waitress(Menu pancakeHouseMenu, Menu dinerMenu) {
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

  private void printMenu(Iterator<MenuItem> iterator) {
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
