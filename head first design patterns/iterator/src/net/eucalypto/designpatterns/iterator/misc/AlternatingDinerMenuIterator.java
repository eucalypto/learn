package net.eucalypto.designpatterns.iterator.misc;

import java.util.Calendar;
import java.util.Iterator;
import net.eucalypto.designpatterns.iterator.MenuItem;

public class AlternatingDinerMenuIterator implements
    Iterator<MenuItem> {

  MenuItem[] items;
  int position;

  public AlternatingDinerMenuIterator(MenuItem[] items) {
    this.items = items;
    position = Calendar.DAY_OF_WEEK % 2;
  }

  @Override
  public MenuItem next() {
    var menuItem = items[position];
    position += 2;
    return menuItem;
  }

  @Override
  public boolean hasNext() {
    if (position >= items.length || items[position] == null) {
      return false;
    } else {
      return true;
    }
  }
}
