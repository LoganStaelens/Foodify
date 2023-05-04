package businessPackage;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import controllerPackage.ILoginController;
import controllerPackage.LoginEventArgs;
import javafx.event.EventHandler;

public class ApplicationManager extends Thread implements ILoginController{

    Queue<Task<?>> tasksQueue;

    private static ApplicationManager instance;
    public static ApplicationManager getInstance() {
        if(instance == null)
        instance = new ApplicationManager();
        return instance;
    }

    private ApplicationManager() {
        this.tasksQueue = new ConcurrentLinkedQueue<Task<?>>();
        this.start();
    }

    @Override
    public void run() {

        while(this.isAlive()) {
            Task<?> task = tasksQueue.poll();

            if(task == null)
                continue;

            task.run();
        }
    }


    @Override
    public void Login(String user, String passwd, EventHandler<LoginEventArgs> handler) {
        tasksQueue.add(new LoginTask(user, passwd, handler));
    }
    
}
