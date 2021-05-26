package net.eucalypto.designpatterns.iterator.diner;

import net.eucalypto.designpatterns.iterator.Iterator;
import net.eucalypto.designpatterns.iterator.MenuItem;

public class DinerMenuIterator implements Iterator {

  MenuItem[] items;
  int position = 0;

  public DinerMenuIterator(MenuItem[] items) {
    this.items = items;
  }

  @Override
  public boolean hasNext() {
    if (position >= items.length || items[position] == null) {
      return false;
    } else {
      return true;
    }
  }

  @Override
  public MenuItem next() {
    var menuItem = items[position];
    position++;
    return menuItem;
  }
}
