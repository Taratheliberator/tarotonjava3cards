package com.example.taro2;



import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView cardImageView;
    private Button openCardButton;

    private int[] tarotCards = {
            R.drawable.card1,
            R.drawable.card2,
            R.drawable.card3,
            R.drawable.card4,
            R.drawable.card5,
            R.drawable.card6,
            R.drawable.card7,
            R.drawable.card8
            // ... добавьте остальные карты Таро
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardImageView = findViewById(R.id.cardImageView);
        openCardButton = findViewById(R.id.openCardButton);

        openCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRandomCard();
            }
        });
    }


    private void openRandomCard() {
        Random random = new Random();
        int randomIndex = random.nextInt(tarotCards.length);
        int randomCard = tarotCards[randomIndex];

        boolean isReversed = random.nextBoolean();
        if (isReversed) {
            cardImageView.setRotationX(180);  // Flip the card by rotating it 180 degrees around the X-axis
        } else {
            cardImageView.setRotationX(0);    // Reset the rotation if not reversed
        }

        cardImageView.setImageResource(randomCard);
        cardImageView.setVisibility(View.VISIBLE);
    }


}

