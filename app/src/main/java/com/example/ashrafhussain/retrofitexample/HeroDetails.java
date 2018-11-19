package com.example.ashrafhussain.retrofitexample;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

public class HeroDetails extends AppCompatActivity {

    TextView name, realname, team, firstappearance, createdby, publisher, bio;
    ImageView image;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_details);

        name             = findViewById(R.id.name);
        realname         = findViewById(R.id.realname);
        team             = findViewById(R.id.team);
        firstappearance  = findViewById(R.id.firstappearance);
        createdby        = findViewById(R.id.createdby);
        publisher        = findViewById(R.id.publisher);
        image            = findViewById(R.id.api_image);
        bio              = findViewById(R.id.bio);

        name.setText(getIntent().getStringExtra("name"));
        realname.setText(getIntent().getStringExtra("realname"));
        team.setText(getIntent().getStringExtra("team"));
        firstappearance.setText(getIntent().getStringExtra("firstappearance"));
        createdby.setText(getIntent().getStringExtra("createdby"));
        publisher.setText(getIntent().getStringExtra("publisher"));
        Glide.with(HeroDetails.this)
                .load(getIntent()
                        .getStringExtra("imageurl"))
                .into(image);
//        Picasso.with( HeroDetails.this ).load(getIntent().getStringExtra("imageurl")).into( image );
        bio.setText(getIntent().getStringExtra("bio"));

    }
}
