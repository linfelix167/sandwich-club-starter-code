package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private ImageView imageView;
    private TextView originalTextView;
    private TextView alsoKnownTextView;
    private TextView descriptionTextView;
    private LinearLayout ingredientsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageView = findViewById(R.id.image_iv);
        originalTextView = findViewById(R.id.origin_tv);
        alsoKnownTextView = findViewById(R.id.also_known_tv);
        descriptionTextView = findViewById(R.id.description_tv);
        ingredientsContainer = findViewById(R.id.ingredients_container);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(imageView);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        if (sandwich != null) {

            String placeOfOrigin = "";
            if (sandwich.getPlaceOfOrigin().isEmpty()) {
                placeOfOrigin += "Unknown";
                originalTextView.setText(placeOfOrigin);
            } else {
                placeOfOrigin += sandwich.getPlaceOfOrigin();
                originalTextView.setText(placeOfOrigin);
            }

            if (sandwich.getAlsoKnownAs().isEmpty()) {
                TextView textView = findViewById(R.id.also_known_label);
                textView.setVisibility(View.INVISIBLE);
            } else {
                for (String alsoKnown : sandwich.getAlsoKnownAs()) {
                    alsoKnownTextView.setText(alsoKnown);
                }
            }

            descriptionTextView.setText(sandwich.getDescription());

            for (String ingredient : sandwich.getIngredients()) {
                TextView textView = new TextView(this);
                textView.setText(ingredient);
                textView.setTextSize(16);
                textView.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                ingredientsContainer.addView(textView);
            }
        }
    }
}
