package com.example.taro2;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;

public class TarotCard {
    private Drawable cardImage;
    private String cardName;
    private String general;

    public TarotCard(Context context, String cardImageName, String cardName, String general) {
        this.cardImage = getDrawableFromAssets(context, cardImageName);
        this.cardName = cardName;
        this.general = general;
    }

    public Drawable getCardImage() {
        return cardImage;
    }

    public String getCardName() {
        return cardName;
    }

    public String getGeneral() {
        return general;
    }

    private Drawable getDrawableFromAssets(Context context, String imageName) {
        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open(imageName);
            return Drawable.createFromStream(inputStream, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}


//
//package com.example.taro2;
//
//import android.content.Context;
//import android.content.res.Resources;
//
//public class TarotCard {
//    private int imageResourceId;
//    private String cardImage;
//    private String cardName;
//    private String general;
//
//    public TarotCard(Context context, String cardImage, String cardName, String general) {
//        this.cardImage = cardImage;
//        this.cardName = cardName;
//        this.general = general;
//        this.imageResourceId = getResourceIdByName(context, cardImage);
//    }
//
//    public int getImageResourceId() {
//        return imageResourceId;
//    }
//
//    public String getCardImage() {
//        return cardImage;
//    }
//
//    public String getCardName() {
//        return cardName;
//    }
//
//    public String getGeneral() {
//        return general;
//    }
//
//    private int getResourceIdByName(Context context, String imageName) {
//        Resources resources = context.getResources();
//        String packageName = context.getPackageName();
//        return resources.getIdentifier(imageName, "drawable", packageName);
//    }
//}


//package com.example.taro2;
//
//public class TarotCard {
//    private int imageResourceId;
//    private String name;
//    private String general;
//
//    public TarotCard(int imageResourceId, String name, String general) {
//        this.imageResourceId = imageResourceId;
//        this.name = name;
//        this.general = general;
//    }
//
//    public int getImageResourceId() {
//        return imageResourceId;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getGeneral() {
//        return general;
//    }
//}
