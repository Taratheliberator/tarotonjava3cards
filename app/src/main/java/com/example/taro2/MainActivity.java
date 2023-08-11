package com.example.taro2;

import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private LinearLayout cardImagesLayout;
    private Button openCardButton;
    private TextView[] cardDescriptionTextViews;
    private ArrayList<TarotCard> tarotCards = new ArrayList<>();
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardImagesLayout = findViewById(R.id.cardImagesLayout);
        openCardButton = findViewById(R.id.openCardButton);
        cardDescriptionTextViews = new TextView[] {
                findViewById(R.id.cardDescriptionTextView1),
                findViewById(R.id.cardDescriptionTextView2),
                findViewById(R.id.cardDescriptionTextView3)
        };

        for (TextView cardDescriptionTextView : cardDescriptionTextViews) {
            cardDescriptionTextView.setMovementMethod(new ScrollingMovementMethod());
        }

        AssetManager assetManager = getAssets();
        try {
            String[] jsonFiles = assetManager.list("");
            for (String jsonFile : jsonFiles) {
                if (jsonFile.endsWith(".json")) {
                    InputStream inputStream = assetManager.open(jsonFile);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    bufferedReader.close();
                    inputStream.close();

                    String jsonString = stringBuilder.toString();

                    JSONObject jsonObject = new JSONObject(jsonString);

                    String cardName = jsonObject.getString("cardName");
                    String cardClassName = jsonObject.getString("cardClassName");
                    JSONArray generalShortArray = jsonObject.getJSONArray("generalShort");
                    JSONArray generalArray = jsonObject.getJSONArray("general");
                    JSONArray loveArray = jsonObject.getJSONArray("love");
                    JSONArray situationArray = jsonObject.getJSONArray("situation");
                    String cardOfTheDay = jsonObject.getString("cardOfTheDay");
                    String advice = jsonObject.getString("advice");
                    String cardImage = jsonObject.getString("cardImage");

                    TarotCard tarotCard = new TarotCard(MainActivity.this, cardImage, cardName, generalArray.toString());
                    tarotCards.add(tarotCard);
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        openCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRandomCards();
            }
        });
    }

    private void openRandomCards() {
        int numCards = 3;
        HashSet<Integer> randomIndices = new HashSet<>();

        while (randomIndices.size() < numCards) {
            int randomIndex = random.nextInt(tarotCards.size());
            randomIndices.add(randomIndex);
        }

        List<Integer> randomIndicesList = new ArrayList<>(randomIndices);
        Collections.shuffle(randomIndicesList);

        cardImagesLayout.removeAllViews();

        int desiredWidth = dpToPx(300); // Desired width of the card images (in pixels)
        int desiredHeight = dpToPx(500); // Desired height of the card images (in pixels)

        for (int i = 0; i < numCards; i++) {
            int index = randomIndicesList.get(i);
            TarotCard randomCard = tarotCards.get(index);

            boolean isReversed = random.nextBoolean();

            ImageView cardImageView = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(desiredWidth, desiredHeight);
            params.setMargins(0, 0, dpToPx(16), 0); // Adjust margins as needed
            cardImageView.setLayoutParams(params);

            cardImageView.setScaleType(ImageView.ScaleType.FIT_XY); // Adjust scale type as needed

            cardImageView.setRotationX(isReversed ? 180 : 0);
            Drawable cardImage = randomCard.getCardImage();
            cardImageView.setImageDrawable(cardImage);

            cardImagesLayout.addView(cardImageView);

            String cardName = randomCard.getCardName();
            String cardGeneral = randomCard.getGeneral();
            cardDescriptionTextViews[i].setText(cardName + "\n" + cardGeneral);
            cardDescriptionTextViews[i].scrollTo(0, 0);
        }
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

}
