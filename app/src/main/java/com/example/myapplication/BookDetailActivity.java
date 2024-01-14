package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.myapplication.database.DatabaseHandler;
import com.example.myapplication.models.BookModel;

import java.util.HashMap;
import java.util.List;

public class BookDetailActivity extends AppCompatActivity {

    ImageView imageAddToCart;
    TextView textAuthor, textPublishedYear, textDescription, textPrice, textToolbarTitle;
    SliderLayout sliderLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        sliderLayout = findViewById(R.id.slider);

       // imageBook = findViewById(R.id.image_book);
        imageAddToCart = findViewById(R.id.image_add_to_cart);
        textAuthor = findViewById(R.id.text_author);
        textPublishedYear = findViewById(R.id.text_published_year);
        textDescription = findViewById(R.id.text_description);
        textPrice = findViewById(R.id.text_price);
        textToolbarTitle = findViewById(R.id.text_toolbar_title);


      //  Picasso.with(getApplicationContext()).load(getIntent().getStringExtra("image"))
             //   .into(imageBook);

        textAuthor.setText("By :" + getIntent().getStringExtra("author"));
        textPublishedYear.setText("Published :" + getIntent().getStringExtra("publishedYear"));
        textDescription.setText(getIntent().getStringExtra("description"));
        textToolbarTitle.setText(getIntent().getStringExtra("name"));
        textPrice.setText("\u20B9" + getIntent().getStringExtra("price"));

        try {

            HashMap<String,String> url_maps = new HashMap<String, String>();
            url_maps.put("Image 1", getIntent().getStringExtra("image"));
            url_maps.put("Image 2", getIntent().getStringExtra("image2"));
            url_maps.put("Image 3", getIntent().getStringExtra("image3"));
            url_maps.put("Image 4", getIntent().getStringExtra("image4"));

            for(String name : url_maps.keySet()) {
                TextSliderView textSliderView = new TextSliderView(this);
                textSliderView
                       //.description(name)
                        .image(url_maps.get(name))
                        .setScaleType(BaseSliderView.ScaleType.CenterInside);

                sliderLayout.addSlider(textSliderView);

            }

            sliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
            sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);



            sliderLayout.setCustomAnimation(new DescriptionAnimation());


            sliderLayout.setDuration(3000);

        } catch (Exception ex) {

        }

        findViewById(R.id.image_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        imageAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookModel bookModel = new BookModel();
                bookModel.setId(getIntent().getStringExtra("id"));
                bookModel.setCategory(getIntent().getStringExtra("category"));
                bookModel.setName(getIntent().getStringExtra("name"));
                bookModel.setDescription(getIntent().getStringExtra("description"));
                bookModel.setAuthor(getIntent().getStringExtra("author"));
                bookModel.setImage(getIntent().getStringExtra("image"));
                bookModel.setPublished_year(getIntent().getStringExtra("publishedYear"));
                bookModel.setPrice(getIntent().getStringExtra("price"));

                DatabaseHandler databaseHandler = new DatabaseHandler(getApplicationContext());
                databaseHandler.addToCart(bookModel);
                Toast.makeText(BookDetailActivity.this, "Added to cart!", Toast.LENGTH_SHORT).show();


            }
        });


    }
}