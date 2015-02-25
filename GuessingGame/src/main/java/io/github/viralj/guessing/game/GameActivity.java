package io.github.viralj.guessing.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


import java.util.Random;

/**
 * Created by Viral on 2/17/2015.
 */
public class GameActivity extends Activity {

    private Button startBtn, submitGuessBtn, resetBtn, grtPpl, rules;        // For start and reset buttons

    private EditText lNum, hNum, guessNum;                    // For lowNum, highNumb edit texts

    private AlertDialog.Builder dialog;

    private int rNum = -1, guessedNum, counter = 10;

    private TextView guessedResult;

    private boolean backPressedToExitOnce = false;          // We need this for quitting app
    private Toast toast = null;                             // We need this for quitting app

    private Random r;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        startBtn            = (Button)findViewById(R.id.start_btn);
        submitGuessBtn      = (Button)findViewById(R.id.submit_guess);
        resetBtn            = (Button)findViewById(R.id.game_reset);
        grtPpl              = (Button)findViewById(R.id.great_ppl);
        rules               = (Button)findViewById(R.id.game_rules);

        lNum        = (EditText) findViewById(R.id.l_num);
        hNum        = (EditText) findViewById(R.id.h_num);
        guessNum    = (EditText) findViewById(R.id.game_guess);

        guessedResult    = (TextView) findViewById(R.id.guessed_result);


        startBtn.setEnabled(false);

        submitGuessBtn.setVisibility(View.INVISIBLE);
        resetBtn.setVisibility(View.INVISIBLE);
        guessNum.setVisibility(View.INVISIBLE);
        guessedResult.setVisibility(View.INVISIBLE);

        Typeface custFont = Typeface.createFromAsset(getAssets(), "fonts/mavenpro_regular.ttf");

        ((TextView)findViewById(R.id.game_hdr)).setTypeface(custFont);
        ((TextView)findViewById(R.id.game_txt)).setTypeface(custFont);

        guessedResult.setTypeface(custFont);

        r = new Random();


        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int limit = Integer.parseInt(hNum.getText().toString()) - Integer.parseInt(lNum.getText().toString());

                //Here we will check for some conditions then we will move on

                if(Integer.parseInt(lNum.getText().toString()) > Integer.parseInt(hNum.getText().toString())){
                    Toast.makeText(getApplicationContext(), "See rules", Toast.LENGTH_LONG).show();
                }
                else if(limit > 99){
                    Toast.makeText(getApplicationContext(), "See rules", Toast.LENGTH_LONG).show();
                }
                else if(limit < 19){
                    Toast.makeText(getApplicationContext(), "See rules", Toast.LENGTH_LONG).show();
                }
                else{
                    submitGuessBtn.setVisibility(View.VISIBLE);
                    resetBtn.setVisibility(View.VISIBLE);
                    guessNum.setVisibility(View.VISIBLE);
                    guessedResult.setVisibility(View.VISIBLE);



                    int Low = Integer.parseInt(lNum.getText().toString());
                    int High = Integer.parseInt(hNum.getText().toString());
                    rNum = r.nextInt(High-Low) + Low;

                    startBtn.setEnabled(false);
                    startBtn.setBackgroundColor(0);
                    startBtn.setVisibility(View.INVISIBLE);

                    lNum.setEnabled(false);
                    hNum.setEnabled(false);

                }


            }
        });

        submitGuessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                guessedNum = (guessNum.getText().toString().length() > 0) ? Integer.parseInt(guessNum.getText().toString()) : -1;


                if(counter >= 0){
                    try{
                        if(guessedNum == -1){
                            Toast.makeText(GameActivity.this, "Please enter your guess. " + counter + " chance(s).", Toast.LENGTH_SHORT).show();
                        }
                        else if(guessedNum > Integer.parseInt(hNum.getText().toString())){
                            Toast.makeText(GameActivity.this, "Your guess can't be bigger than High #. " + counter + " chance(s).", Toast.LENGTH_SHORT).show();
                        }
                        else if(guessedNum < Integer.parseInt(lNum.getText().toString())){
                            Toast.makeText(GameActivity.this, "Your guess can't be lower than Low #. " + counter + " chance(s).", Toast.LENGTH_SHORT).show();
                        }
                        else if(guessedNum == rNum){
                            guessedResult.setText("You guessed it!!!\nResetting game in 3 seconds.");
                            Thread timer = new Thread(){

                                public void run(){
                                    try{
                                        sleep(3000);
                                    }catch(InterruptedException e){
                                        e.printStackTrace();
                                    }finally {
                                        Intent intent = getIntent();
                                        finish();
                                        startActivity(intent);
                                    }
                                }
                            };
                            timer.start();
                        }
                        else if(guessedNum > rNum){
                            guessedResult.setText("That's too high. Try again. " + counter + " chance(s).");
                        }
                        else if(guessedNum < rNum){
                            guessedResult.setText("That's too low. Try again. " + counter + " chance(s).");
                        }
                    }catch (Exception e){
                        //nothing here
                    }
                    finally {
                        counter--;
                    }

                }else {
                    dialog = new AlertDialog.Builder(GameActivity.this);
                    dialog.setCancelable(true);
                    dialog.setTitle("Game Over !");
                    dialog.setMessage("You have used all your chances to guess correct number. Please try again.\n\nAns: "+rNum);
                    dialog.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }
                    });
                    dialog.setNegativeButton("Exit Game!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(GameActivity.this, "See you later!! Good Bye!!!", Toast.LENGTH_SHORT).show();

                            Thread timer = new Thread(){

                                public void run(){
                                    try{
                                        sleep(2000);
                                    }catch(InterruptedException e){
                                        e.printStackTrace();
                                    }finally {
                                        finish();
                                    }
                                }

                            };
                            timer.start();

                        }
                    });
                    dialog.create().show();
                }
            }
        });


    }

    @Override
    protected void onResume(){
        super.onResume();


        //Checking if user is inserting numbers in high number field to enable start game button
        lNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //We are doing nothing here
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(hNum.getText().toString().length() > 0 && lNum.getText().toString().length() > 0) {
                    startBtn.setEnabled(true);
                    startBtn.setBackgroundColor(Color.parseColor("#bdc3c7"));
                }
                else {
                    startBtn.setEnabled(false);
                    startBtn.setBackgroundColor(0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //We are doing nothing here
            }
        });

        hNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //We are doing nothing here
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(hNum.getText().toString().length() > 0 && lNum.getText().toString().length() > 0) {
                    startBtn.setEnabled(true);
                    startBtn.setBackgroundColor(Color.parseColor("#bdc3c7"));
                }
                else {
                    startBtn.setEnabled(false);
                    startBtn.setBackgroundColor(0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //We are doing nothing here
            }
        });


        grtPpl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new AlertDialog.Builder(GameActivity.this);
                dialog.setCancelable(true);
                dialog.setTitle("Great people!");
                dialog.setMessage("There are so many great people in this world.\n\"The Great Nicolaas (Johnny) tenBroek\" is one of them."
                        +"\nHe taught me how to create Android app.\n\nThanks Johnny ;)");
                dialog.setPositiveButton("Thanks to Johnny", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                dialog.create().show();

            }
        });

        rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new AlertDialog.Builder(GameActivity.this);
                dialog.setCancelable(true);
                dialog.setTitle("Game Rules !");
                dialog.setMessage("This game is very simple and made with very simple rules."
                        + "\n\n1) Low number can not be bigger than High number"
                        + "\n2) Range of numbers cannot be more than 100"
                        + "\n3) Range of numbers cannot be lower than 20");
                dialog.setPositiveButton("Got It!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                dialog.create().show();

            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

    }


    /*
     *
     * Killing activity from here when
     * user press back button.
     *
     * We handle it with toast by showing
     * a warning.
     *
     */

    @Override
    public void onBackPressed() {
        if (backPressedToExitOnce) {
            super.onBackPressed();
        } else {
            this.backPressedToExitOnce = true;
            showToast("Press again to exit");
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    backPressedToExitOnce = false;
                }
            }, 2000);
        }
    }


    private void showToast(String message) {
        if (this.toast == null) {
            // Create toast if found null, it would he the case of first call only
            this.toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);

        } else if (this.toast.getView() == null) {
            // Toast not showing, so create new one
            this.toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);

        } else {
            // Updating toast message is showing
            this.toast.setText(message);
        }

        // Showing toast finally
        this.toast.show();
    }

    private void killToast() {
        if (this.toast != null) {
            this.toast.cancel();
        }
    }


    @Override
    protected void onPause() {
        killToast();
        super.onPause();
    }



}

/*
*
* Rule 1) Low number can not be bigger then High number
*
* Rule 2) Range of numbers cannot be more then 100
*
* Rule 3) Range of numbers cannot be lower then 20
*
* */