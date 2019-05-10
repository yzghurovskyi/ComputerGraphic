package lab5;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            Mouse window = new Mouse();

            window.setVisible(true);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
