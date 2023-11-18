package musacode;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class OpenDirectory {

    private String directoryPath;

    public OpenDirectory(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public void open() {
        try {
            // Create a File object pointing to the directory
            File directory = new File(directoryPath);

            // Check if the directory exists
            if (!directory.exists() || !directory.isDirectory()) {
                System.out.println("Directory does not exist: " + directoryPath);
                return;
            }

            // Get the desktop instance and open the directory
            Desktop desktop = Desktop.getDesktop();
            desktop.open(directory);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

