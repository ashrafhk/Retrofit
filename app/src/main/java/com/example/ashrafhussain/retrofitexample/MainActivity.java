package com.example.ashrafhussain.retrofitexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    List<Hero> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        data = new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, HeroDetails.class);

                intent.putExtra("name",data.get(position).getName());
                intent.putExtra("realname",data.get(position).getRealname());
                intent.putExtra("team",data.get(position).getTeam());
                intent.putExtra("firstappearance",data.get(position).getFirstappearance());
                intent.putExtra("createdby",data.get(position).getCreatedby());
                intent.putExtra("publisher",data.get(position).getPublisher());
                intent.putExtra("imageurl",data.get(position).getImageurl());
                intent.putExtra("bio",data.get(position).getBio());
                startActivity(intent);
            }
        });
        //calling the method to display the heroes
        getHeroes();
    }

    private void getHeroes(){

        //Creating a retrofit object
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL)
                //Here we are using the GsonConverterFactory to directly convert json data to object
                .addConverterFactory(GsonConverterFactory.create()).build();

        //creating the api interface
        Api api = retrofit.create(Api.class);

        //now making the call object
        //Here we are using the api method that we created inside the api interface
        Call<List<Hero>> call = api.getHeroes();

        //then finallly we are making the call using enqueue()
        //it takes callback interface as an argument
        //and callback is having two methods onRespnose() and onFailure
        //if the request is successfull we will get the correct response and onResponse will be executed
        //if there is some error we will get inside the onFailure() method
        call.enqueue(new Callback<List<Hero>>() {
            @Override
            public void onResponse(Call<List<Hero>> call, Response<List<Hero>> response) {

                //In this point we got our hero list
                List<Hero> heroList = response.body();

                //Creating an String array for the ListView
                String[] herosnames = new String[heroList.size()];

                //looping through all the heroes and inserting the names inside the string array
                for (int i = 0; i < heroList.size(); i++){

                    herosnames[i] = heroList.get(i).getName();

                    data.add(new Hero(  heroList.get(i).getName(),
                                        heroList.get(i).getRealname(),
                                        heroList.get(i).getTeam(),
                                        heroList.get(i).getFirstappearance(),
                                        heroList.get(i).getCreatedby(),
                                        heroList.get(i).getPublisher(),
                                        heroList.get(i).getImageurl(),
                                        heroList.get(i).getBio()
                    ));
                }

                //displaying the string array into listview
                listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, herosnames));
            }

            @Override
            public void onFailure(Call<List<Hero>> call, Throwable t) {

                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
