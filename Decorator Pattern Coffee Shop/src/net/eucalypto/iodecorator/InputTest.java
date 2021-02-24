package net.eucalypto.iodecorator;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputTest {

    public static void main(String[] args) {
        try {
            printFromTestFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printFromTestFile() throws IOException {
        int c;
        InputStream in = new LowerCaseInputStream(new BufferedInputStream(
                new FileInputStream("src/net/eucalypto/iodecorator/test.txt")));

        while ((c = in.read()) >= 0)
            System.out.print((char) c);
    }
}
