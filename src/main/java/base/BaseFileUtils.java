package base;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BaseFileUtils {
	    public static void refreshFolder(String folderPath) {
	        File folder = new File(folderPath);
	        String tempName = folder.getName() + System.currentTimeMillis(); // Generate a temporary name
	        
	        File tempFolder = new File(folder.getParent(), tempName);
	        folder.renameTo(tempFolder); // Rename the folder temporarily
	        
	        tempFolder.renameTo(folder); // Rename the folder back to its original name
	        System.out.println("Folder refreshed: " + folderPath);
	    }

	
    public static void initializeDownloadDirectory(String downloadDirectory) {
        File directory = new File(downloadDirectory);

        try {
            if (directory.exists()) {
                org.apache.commons.io.FileUtils.forceDelete(directory);
                System.out.println("Existing download directory deleted: " + downloadDirectory);
            }

            if (directory.mkdirs()) {
                System.out.println("Download directory created: " + downloadDirectory);
            } else {
                System.out.println("Failed to create the download directory: " + downloadDirectory);
            }
        } catch (IOException e) {
            System.out.println("Failed to delete or create the download directory: " + downloadDirectory);
            e.printStackTrace();
        }
    }
}
