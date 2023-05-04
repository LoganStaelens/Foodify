
package viewPackage;

import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class Window {
    protected Scene fxmlWindow;
    protected Stage mainStage, popupStage;


    public Window(Stage mainStage, Stage popupStage, Scene fxmlWindow) {
        this.mainStage = mainStage;
        this.popupStage = popupStage;
        this.fxmlWindow = fxmlWindow;
    }

    public abstract void show();
}
