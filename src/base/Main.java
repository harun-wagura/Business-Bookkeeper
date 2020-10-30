package base;

import Notifications.Notify;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class Main extends Application {

    public Stage window = new Stage();
    Parent root = FXMLLoader.load(getClass().getResource("/Scenes/base.fxml"));
    public Scene scene = new Scene(root);

    public Main() throws IOException {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Platform.setImplicitExit(false);
        primaryStage.setTitle("RHYS DESERTS");
        scene.getStylesheets().add(Main.class.getResource("/Styles/Base.css").toExternalForm());
        primaryStage.setScene(scene);
        window = primaryStage;
        Progress.saved = true;
        ListViewManager.selectedItem = null;
        primaryStage.show();

        primaryStage.setOnCloseRequest((WindowEvent we) -> {
            if (!Progress.saved) {
                we.consume();
                Notify.warn("Unsaved changes! Save before exiting");
            } else {
                primaryStage.close();
                Progress.openStages--;
                if (Progress.openStages == 0) Platform.exit();
            }
        });
    }
}
