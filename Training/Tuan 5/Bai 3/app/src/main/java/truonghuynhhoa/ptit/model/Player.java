package truonghuynhhoa.ptit.model;

import java.io.Serializable;

public class Player implements Serializable {
    private int imagePlayer;
    private String name;
    private String birthday;
    private int imageCountry;

    public Player() {
    }

    public Player(int imagePlayer, String name, String birthday, int imageCountry) {
        this.imagePlayer = imagePlayer;
        this.name = name;
        this.birthday = birthday;
        this.imageCountry = imageCountry;
    }

    public int getImagePlayer() {
        return imagePlayer;
    }

    public void setImagePlayer(int imagePlayer) {
        this.imagePlayer = imagePlayer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getImageCountry() {
        return imageCountry;
    }

    public void setImageCountry(int imageCountry) {
        this.imageCountry = imageCountry;
    }
}
