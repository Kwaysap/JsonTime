package com.example.jsontime;

import java.util.List;

public class ImageEvent {
    //Declares
    public final String imageUrl;
    public final List<String> imageUrls;

    //Initializes
    /**
     *  Constructor for ImageEvent
     * @param imageUrl single random Image Url
     * @param imageUrls list of all Image Urls
     */


    public ImageEvent(String imageUrl, List<String> imageUrls) {
        this.imageUrl = imageUrl;
        this.imageUrls = imageUrls;
    }

}
