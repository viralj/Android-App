package io.github.viralj.finalandroidapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;


//activity class
public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //to keep screen light on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        //Setting up button
        final Button playBtn = (Button)findViewById(R.id.play_btn);


        //hiding button by setting visibility = invisible
        playBtn.setVisibility(View.INVISIBLE);


        //setting up button on click listener and we will start new activity/redirect user to play screen
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity();
            }
        });

        //creating timer to wait for three seconds and than we will show play button to user
        Thread timer = new Thread(){

            public void run(){
                try{
                    sleep(3000);

                }catch(InterruptedException e){
                    e.printStackTrace();

                }finally {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            playBtn.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }

        };
        timer.start();



    }


    //finishing activity on pause
    protected void onPause(){
        super.onPause();
        finish();
    }


    //method to start new activity
    protected void startMainActivity(){
        Intent openStartPoint = new Intent();
        openStartPoint.setClass(StartActivity.this, MainActivity.class);
        startActivity(openStartPoint);
    }


}
