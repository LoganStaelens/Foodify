package controllerPackage;


public class ThreadLoading extends Thread {
    
    private LoadingCommonZone commonZone;

    public ThreadLoading(LoadingCommonZone commonZone) {
        super("LoadingConsumer");
        this.commonZone = commonZone;
    }

    @Override
    public void run() {
        synchronized(commonZone) {
            double x = Math.PI / 2.0;
            this.commonZone.getKettleImage().setVisible(true);
            this.commonZone.getLidImage().setVisible(true);
            this.commonZone.getSteamImage().setVisible(true);
            while(!commonZone.isLoading()) {
                
                try {
                    Thread.sleep((long)(1000 / 60.0));

                    commonZone.getSteamImage().setOpacity(Math.sin(x));
                    x += .1f;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            commonZone.getSteamImage().setOpacity(1);
            this.commonZone.getKettleImage().setVisible(false);
            this.commonZone.getLidImage().setVisible(false);
            this.commonZone.getSteamImage().setVisible(false);
        }
    }

}
