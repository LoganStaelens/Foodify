package businessPackage;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public abstract class Task<T extends ActionEvent> {

    protected EventHandler<T> onActionPerformed;

    public Task(EventHandler<T> onActionPerformed) {
        this.onActionPerformed = onActionPerformed;
    }

    public abstract void run();
}
