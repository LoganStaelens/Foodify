package controllerPackage;

import javafx.scene.image.ImageView;

public class LoadingCommonZone {
    private ImageView steamImage;
    private ImageView lidImage;
    private ImageView kettleImage;
    private boolean loading = false;

    public LoadingCommonZone(ImageView steamImage, ImageView lidImage, ImageView kettleImage) {
        this.steamImage = steamImage;
        this.lidImage = lidImage;
        this.kettleImage = kettleImage;
        this.loading = false;
    }

    public ImageView getSteamImage() {
        return steamImage;
    }
    
    public ImageView getLidImage() {
        return lidImage;
    }
    
    public ImageView getKettleImage() {
        return kettleImage;
    }
    
    public boolean isLoading() {
        return loading;
    }
    
    public void loadingComplete() {
        this.loading = true;
    }

    
}
