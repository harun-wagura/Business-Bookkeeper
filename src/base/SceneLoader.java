package base;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class SceneLoader {

    private Pane view;

    public Pane getView(String filename) throws IOException {
        URL url = Main.class.getResource(filename + ".fxml");
        if (url == null) {
            throw new FileNotFoundException("Check url");
        }
        view = FXMLLoader.load(url);
        view.getStylesheets().add(getClass().getResource("/Styles/Add.css").toExternalForm());
        return view;
    }
}
