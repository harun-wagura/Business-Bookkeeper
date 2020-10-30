package FilesManager;

import RecordsPackage.Data;

import java.io.*;
import java.util.ArrayList;

public class TablesManager {

    private static final File file = new File("Tables.ser");
    private static ArrayList<Data> template = null;

    public static void update(Data data) throws IOException, ClassNotFoundException {

        ArrayList<Data> list = read();
        list.add(data);
        template = list;
        write();
    }

    public static void delete(Data data) throws IOException, ClassNotFoundException {
        ArrayList<Data> list = read();
        list.remove(data);
    }

    public static void replace(Data original_data, Data new_data) throws IOException, ClassNotFoundException {
        ArrayList<Data> list = read();
        int j = (list.indexOf(original_data));
        list.remove(j);
        list.add(j, new_data);
    }

    public static void write() throws IOException {

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
        outputStream.writeObject(template);
        outputStream.close();
    }

    public static ArrayList<Data> read() throws IOException, ClassNotFoundException {

        if (file.exists()) {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            //noinspection unchecked
            template = (ArrayList<Data>) objectInputStream.readObject();
            objectInputStream.close();
            return template;
        } else {
            return new ArrayList<Data>();
        }
    }
}
