package io.github.viralj.finalandroidapp;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;


import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Random;


//main activity class which extends activity and implements on click listener
public class MainActivity extends Activity implements View.OnClickListener{

    private int score = 0;                  //score variable to save user score
    private int totalQuestionsAsked = 0;    //total questions asked


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //to keep screen light on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        //Setting up button and image
        final Button btnOne = (Button)findViewById(R.id.btn_one);
        final Button btnTwo = (Button)findViewById(R.id.btn_two);
        final Button btnThree = (Button)findViewById(R.id.btn_three);
        final Button btnFour = (Button)findViewById(R.id.btn_four);
        final ImageView flagImg = (ImageView)findViewById(R.id.flag_img);
        final TextView qsTV = (TextView)findViewById(R.id.qs_num);


        //JSON array to read country and flag file so later we can use them for country name
        //in buttons and to fetch flags from drawable
        JSONArray country, flag;


        //setting up JSON arrays
        country = getFilesArray()[0];
        flag = getFilesArray()[1];

        if(country != null){


            //creating our first question and incrementing the counter
            totalQuestionsAsked++;
            createCountryElements(flag, country, btnOne, btnTwo, btnThree, btnFour, flagImg, qsTV);

        }



    }

    //method to create json array by reading json files
    private JSONArray[] getFilesArray(){
        JSONArray country, flag;

        try {
            country = new JSONArray(loadJSONFromAsset("countries.json"));
            flag = new JSONArray(loadJSONFromAsset("images.json"));

        } catch (JSONException e) {
            e.printStackTrace();

            country = null;
            flag = null;

        }

        JSONArray[] x = {country, flag};

        return x;
    }


    //finishing activity on pause
    protected void onPause(){
        super.onPause();
        finish();
    }

    //creating country elements
    private void createCountryElements(JSONArray flag, JSONArray country,
                                       Button btnOne, Button btnTwo, Button btnThree, Button btnFour,
                                       ImageView flagImg, TextView qsTV){
        int[] vals = createValsArray(country);

        try {

            //setting up question number text view
            qsTV.setText("Question " + String.valueOf(totalQuestionsAsked) + " out of 10");

            //setting button text, country names
            btnOne.setText(String.valueOf(country.get(vals[0])));
            btnTwo.setText(String.valueOf(country.get(vals[1])));
            btnThree.setText(String.valueOf(country.get(vals[2])));
            btnFour.setText(String.valueOf(country.get(vals[3])));

            //setting button tag
            btnOne.setTag(vals[0]);
            btnTwo.setTag(vals[1]);
            btnThree.setTag(vals[2]);
            btnFour.setTag(vals[3]);

            //changing back button's colour to transparent
            btnOne.setBackgroundColor(0x00000000);
            btnTwo.setBackgroundColor(0x00000000);
            btnThree.setBackgroundColor(0x00000000);
            btnFour.setBackgroundColor(0x00000000);

            //changing button font color
            btnOne.setTextColor(Color.parseColor("#3f51b5"));
            btnTwo.setTextColor(Color.parseColor("#3f51b5"));
            btnThree.setTextColor(Color.parseColor("#3f51b5"));
            btnFour.setTextColor(Color.parseColor("#3f51b5"));

            //setting click listener
            btnOne.setOnClickListener(this);
            btnTwo.setOnClickListener(this);
            btnThree.setOnClickListener(this);
            btnFour.setOnClickListener(this);


            //selecting random valuse from vals array to select flag and set image
            int rndInt = vals[(new Random()).nextInt(vals.length)];

            String imgFile = flag.get(rndInt).toString();
            flagImg.setTag(rndInt);


            imgFile = imgFile.replace(".png", "");

            int imageResource = getResources().getIdentifier(imgFile, "drawable", getPackageName());


            //setting up flag image
            try {
                flagImg.setImageResource(imageResource);
            } catch (Exception e) {
                e.printStackTrace();
            }


//            System.out.println("-------");
//            System.out.println("-");
//            System.out.println("-");
//            System.out.println(vals[0]);
//            System.out.println(vals[1]);
//            System.out.println(vals[2]);
//            System.out.println(vals[3]);
//            System.out.println(rndInt);
//            System.out.println("-");
//            System.out.println("-");
//            System.out.println(imgFile);
//            System.out.println("-");
//            System.out.println("-");
//            System.out.println(imageResource);
//            System.out.println("-");
//            System.out.println("-------");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //creating integer array
    private int[] createValsArray(JSONArray x){
        int[] vals = new int[4];

        for(int i = 0; i<4; i++){
            int a = 0 + (int) (Math.random() * ((x.length() - 1) + 1));

            if(!Arrays.asList(vals).contains(String.valueOf(a))){
                vals[i] = a;
            }else{
                i--;
            }

        }

        return vals;

    }

    //loading json file into string to send back json data
    private String loadJSONFromAsset(String fileName) {
        String json = null;
        try {

            InputStream is = getAssets().open(fileName);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    //on click method
    @Override
    public void onClick(View v) {


        //we will check for how many questions asked. If 10 questions asked we will show dialog
        // to user displaying score, result and option to replay or exit.
        if(totalQuestionsAsked <= 10){
            if(score >= 10)
                score = 10;

            //Setting up button and image
            final Button btnOne = (Button)findViewById(R.id.btn_one);
            final Button btnTwo = (Button)findViewById(R.id.btn_two);
            final Button btnThree = (Button)findViewById(R.id.btn_three);
            final Button btnFour = (Button)findViewById(R.id.btn_four);
            final ImageView flagImg = (ImageView)findViewById(R.id.flag_img);
            final TextView qsTV = (TextView)findViewById(R.id.qs_num);

            final JSONArray country = getFilesArray()[0];
            final JSONArray flag = getFilesArray()[1];

            final View view = v;

            //changing button color when clicked
            switch (v.getId()){
                case R.id.btn_one:
                    btnOne.setBackgroundColor(Color.parseColor("#00b0ff"));
                    break;
                case R.id.btn_two:
                    btnTwo.setBackgroundColor(Color.parseColor("#00b0ff"));
                    break;
                case R.id.btn_three:
                    btnThree.setBackgroundColor(Color.parseColor("#00b0ff"));
                    break;
                case R.id.btn_four:
                    btnFour.setBackgroundColor(Color.parseColor("#00b0ff"));
                    break;
            }

            //creating handler and runners to run on main ui thread so we can change color of buttons
            // when based on result
            final Handler handler = new Handler(Looper.getMainLooper());
            final Runnable runnerTwo = new Runnable() {
                @Override
                public void run() {
                    if(totalQuestionsAsked <= 10){

                        createCountryElements(flag, country, btnOne, btnTwo, btnThree, btnFour, flagImg, qsTV);
                    }else{

                        //showing dialog
                        showDialog();
                    }
                }
            };

            Runnable runnerOne = new Runnable() {
                @Override
                public void run() {

                    if(view.getTag().equals(flagImg.getTag())){
                        score++;

                        switch (view.getId()){
                            case R.id.btn_one:
                                btnOne.setBackgroundColor(Color.parseColor("#00e676"));
                                btnOne.setTextColor(Color.parseColor("#FFFFFF"));
                                break;
                            case R.id.btn_two:
                                btnTwo.setBackgroundColor(Color.parseColor("#00e676"));
                                btnTwo.setTextColor(Color.parseColor("#FFFFFF"));
                                break;
                            case R.id.btn_three:
                                btnThree.setBackgroundColor(Color.parseColor("#00e676"));
                                btnThree.setTextColor(Color.parseColor("#FFFFFF"));
                                break;
                            case R.id.btn_four:
                                btnFour.setBackgroundColor(Color.parseColor("#00e676"));
                                btnFour.setTextColor(Color.parseColor("#FFFFFF"));
                                break;
                        }

                    }else{

                        switch (view.getId()){
                            case R.id.btn_one:
                                btnOne.setBackgroundColor(Color.parseColor("#d32f2f"));
                                btnOne.setTextColor(Color.parseColor("#FFFFFF"));
                                break;
                            case R.id.btn_two:
                                btnTwo.setBackgroundColor(Color.parseColor("#d32f2f"));
                                btnTwo.setTextColor(Color.parseColor("#FFFFFF"));
                                break;
                            case R.id.btn_three:
                                btnThree.setBackgroundColor(Color.parseColor("#d32f2f"));
                                btnThree.setTextColor(Color.parseColor("#FFFFFF"));
                                break;
                            case R.id.btn_four:
                                btnFour.setBackgroundColor(Color.parseColor("#d32f2f"));
                                btnFour.setTextColor(Color.parseColor("#FFFFFF"));
                                break;
                        }
                    }
                    totalQuestionsAsked++;

                    handler.postDelayed(runnerTwo, 1000);

                }
            };


            handler.postDelayed(runnerOne, 500);


        }else{

            //showing dialog
            showDialog();
        }

    }

    //creating dialog to show result, score and options
    private void showDialog(){
        String result = (score >= 7)? "passed" : "failed";

        //creating dialog to show user score
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

        dialog.setCancelable(false);
        dialog.setTitle("Your score!");
        dialog.setMessage("Your total score is "+ String.valueOf(score) +"/10, and you have "+
                result +" the test!");
        dialog.setPositiveButton("Play again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        dialog.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        dialog.create().show();
    }

}
