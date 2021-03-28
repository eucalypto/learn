package net.eucalypto.nameinverter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class NameInverterTest {

  private void assertInverted(String originalName, String invertedName) {
    assertEquals(invertedName, invertName(originalName));
  }

  @Test
  void givenNull_returnEmptyString() {
    assertInverted(null, "");
  }

  @Test
  void givenEmptyString_returnEmptyString() {
    assertInverted("", "");
  }

  @Test
  void givenSimpleName_returnSimpleName() {
    assertInverted("Name", "Name");
  }

  @Test
  void givenSimpleNameWithSpaces_ShouldReturnSimpleNameWithoutSpaces() {
    assertInverted(" Name ", "Name");
  }

  @Test
  void givenFirstLast_returnLastFirst() {
    assertInverted("First Last", "Last, First");
  }

  @Test
  void givenFirstLastWithExtraSpaces_returnLastFirst() {
    assertInverted(" First  Last  ", "Last, First");
  }

  @Test
  void ignoreHonorifics() {
    assertInverted("Mr. First Last", "Last, First");
    assertInverted("Mrs. First Last", "Last, First");
  }

  @Test
  void postNominals_stayAtEnd() {
    assertInverted("First Last Sr.", "Last, First Sr.");
    assertInverted("First Last BS. PhD.", "Last, First BS. PhD.");
  }

  @Test
  void integration() {
    assertInverted("  John Doe     III   esq.    ", "Doe, John III esq.");
  }

  private String invertName(String name) {
    if (name == null) {
      return "";
    }

    var nameParts = getNameParts(name);

    if (nameParts.size() == 1) {
      return nameParts.get(0);
    }

    removeHonorificsFrom(nameParts);

    if (nameParts.size() > 2) {
      return getFormattedNameWithPostNominals(nameParts);
    }

    return nameParts.get(1) + ", " + nameParts.get(0);
  }

  private String getFormattedNameWithPostNominals(ArrayList<String> nameParts) {
    var returnString = new StringBuilder();
    returnString.append(nameParts.get(1))
        .append(", ")
        .append(nameParts.get(0));

    for (int i = 2; i < nameParts.size(); i++) {
      returnString.append(" ").append(nameParts.get(i));
    }

    return returnString.toString();
  }

  private void removeHonorificsFrom(ArrayList<String> nameParts) {
    if (isHonorific(nameParts.get(0))) {
      nameParts.remove(0);
    }
  }

  private boolean isHonorific(String word) {
    return word.equals("Mr.") || word.equals("Mrs.");
  }

  private ArrayList<String> getNameParts(String name) {
    return new ArrayList<>(Arrays.asList(name.strip().split("\\s+")));
  }
}
