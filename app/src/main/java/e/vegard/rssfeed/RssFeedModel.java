package e.vegard.rssfeed;

import java.io.Serializable;

// how we structure our feed on the app
public class RssFeedModel implements Serializable {
    // title of the feed
    public String title;
    // link to the article
    public String link;
    // description of the feed
    public String description;
    // img link for picture
    public String img;

    public RssFeedModel(String title, String link, String desc, String img) {
        this.title = title;
        this.link = link;
        this.description = desc;
        this.img = img;
    }
}

