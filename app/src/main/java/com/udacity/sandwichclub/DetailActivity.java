package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);


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
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView mAlsoKnowAs = findViewById(R.id.tv_detail_also_known_as_label);
        TextView mPlaceOfOrigin = findViewById(R.id.tv_detail_place_of_origin);
        TextView mIngredients = findViewById(R.id.tv_detail_ingredients);
        TextView mDescription = findViewById(R.id.tv_description);

        //show also know as name
        showAlsoKnownAs(sandwich, mAlsoKnowAs);

        //add ingredients to the view
        showIngredients(sandwich, mIngredients);

        //add place of origin
        showOrigin(sandwich, mPlaceOfOrigin);

        //add description
        showDescription(sandwich, mDescription);
    }

    private  void showAlsoKnownAs(Sandwich sandwich, TextView mAlsoKnowAs ) {
        if (sandwich.getAlsoKnownAs() != null && sandwich.getAlsoKnownAs().size() > 0) {
            for (String name : sandwich.getAlsoKnownAs()) {
                mAlsoKnowAs.append(name);
                if (!sandwich.getAlsoKnownAs().get(sandwich.getAlsoKnownAs().size() -1).equals(name)) {
                    mAlsoKnowAs.append(", ");
                }
            }
        } else {
            mAlsoKnowAs.setText(R.string.no_answer);
        }
    }

    private  void showIngredients(Sandwich sandwich, TextView mIngredients) {
        if (sandwich.getIngredients() != null && sandwich.getIngredients().size() > 0) {
            for (String ingredient: sandwich.getIngredients()) {
                mIngredients.append(" - " + ingredient + "\n");
            }
        } else {
            mIngredients.setText(R.string.no_answer);
        }

    }

    private  void showOrigin(Sandwich sandwich, TextView mPlaceOfOrigin) {
        if (sandwich.getPlaceOfOrigin() != null && !sandwich.getPlaceOfOrigin().equals("")) {
            mPlaceOfOrigin.setText(sandwich.getPlaceOfOrigin());
        } else {
            mPlaceOfOrigin.setText(R.string.no_answer);
        }
    }

    private  void showDescription(Sandwich sandwich, TextView mDescription) {
        if (sandwich.getDescription() != null && !sandwich.getDescription().equals("")) {
            mDescription.setText(sandwich.getDescription());
        } else {
            mDescription.setText(R.string.no_answer);
        }
    }
}
