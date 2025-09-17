package idrive360.utilities;

import java.io.File;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class FileCleaner {

    /**
     * Deletes files older than X days in the given directory.
     * @param folderPath path to screenshots folder
     * @param days number of days before deletion (e.g., 30)
     */
    public static void deleteOldScreenshots(String folderPath, int days) {
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Invalid folder: " + folderPath);
            return;
        }

        File[] files = folder.listFiles();
        if (files == null) return;

        Instant cutoff = Instant.now().minus(days, ChronoUnit.DAYS);

        for (File file : files) {
            if (file.isFile()) {
                Instant fileTime = Instant.ofEpochMilli(file.lastModified());
                if (fileTime.isBefore(cutoff)) {
                    if (file.delete()) {
                        System.out.println("Deleted old screenshot: " + file.getName());
                    } else {
                        System.out.println("Failed to delete: " + file.getName());
                    }
                }
            }
        }
    }
}