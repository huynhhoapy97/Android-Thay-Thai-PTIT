package truonghuynhhoa.ptit.model;

import java.io.Serializable;

public class Menu implements Serializable {
    private int image;
    private String description;

    public Menu() {
    }

    public Menu(int image, String description) {
        this.image = image;
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
