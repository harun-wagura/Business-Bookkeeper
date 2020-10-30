package FilesManager;

import java.io.*;
import java.util.Hashtable;

@SuppressWarnings("ALL")
public class ResourceManager {

    public static String fileSignature = "Default"; // Default start
    private static Hashtable<String, Item> template = null;
    private static String filename = "FilesMapCustom.ser"; // CUSTOM
    private static String filename1 = "FilesMapJumia.ser"; // JUMIA
    private static String filename2 = "FilesMapGlovo.ser"; // GLOVO
    private static String filename3 = "FilesMapCatering.ser"; // CATERING
    private static String filenameDefault = "FilesMap.ser"; // DEFAULT

    private static File getFileLocation() {

        File file;
        switch (fileSignature) {

            case "Custom":
                file = new File(filename);
                return file;
            case "Jumia":
                file = new File(filename1);
                return file;
            case "Glovo":
                file = new File(filename2);
                return file;
            case "Catering":
                file = new File(filename3);
                return file;

            default:
                file = new File(filenameDefault);
                return file;
        }

    }

    // This class updates Item objects into a hashtable
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    public static void update(Item itm) throws IOException, ClassNotFoundException {
        Hashtable<String, Item> hashtable = read();
        hashtable.put(itm.getItemName(), itm);
        template = hashtable;
        write();
    }

    public static void replace(Item itm1, Item itm2) throws IOException, ClassNotFoundException {
        Hashtable<String, Item> hashtable = read();
        hashtable.remove(itm1.getItemName());
        hashtable.put(itm2.getItemName(), itm2);
        template = hashtable;
        write();
    }

    public static void delete(Item itm) throws IOException, ClassNotFoundException {
        Hashtable<String, Item> hashtable = read();
        hashtable.remove(itm.getItemName());
        template = hashtable;
        write();
    }

    public static void delete(String key) throws IOException, ClassNotFoundException {
        Hashtable<String, Item> hashtable = read();
        hashtable.remove(key);
        template = hashtable;
        write();
    }

    public static void write() {

        try {
            FileOutputStream foStream = new FileOutputStream(getFileLocation()); // DEFAULT
            ObjectOutputStream outputStream = new ObjectOutputStream(foStream);
            outputStream.writeObject(template);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Hashtable<String, Item> read() throws IOException, ClassNotFoundException {
        Hashtable<String, Item> hashtable = new Hashtable<>();
        if (getFileLocation().exists()) {
            FileInputStream fiStream = new FileInputStream(getFileLocation()); // DEFAULT
            ObjectInputStream inputStream = new ObjectInputStream(fiStream);
            hashtable = (Hashtable<String, Item>) inputStream.readObject();
            return hashtable;

        } else {
            return new Hashtable<>();
        }
    }
}

