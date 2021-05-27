package net.eucalypto.designpatterns.iterator;

import java.util.Iterator;

public class Waitress {

  Menu pancakeHouseMenu;
  Menu dinerMenu;
  Menu cafeMenu;

  public Waitress(Menu pancakeHouseMenu, Menu dinerMenu,
      Menu cafeMenu) {
    this.pancakeHouseMenu = pancakeHouseMenu;
    this.dinerMenu = dinerMenu;
    this.cafeMenu = cafeMenu;
  }

  public void printMenu() {
    var pancakeIterator = pancakeHouseMenu.createIterator();
    var dinerIterator = dinerMenu.createIterator();
    var cafeIterator = cafeMenu.createIterator();
    System.out.println("MENU\n----\nBREAKFAST");
    printMenu(pancakeIterator);
    System.out.println("\nLUNCH");
    printMenu(dinerIterator);
    System.out.println("\nDINNER");
    printMenu(cafeIterator);
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
