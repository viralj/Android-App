package io.github.viralj.guessing.game;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;


public class StartActivity extends Activity {

    private TextView gameTtl, gameBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        gameBy  = (TextView) findViewById(R.id.game_by);
        gameTtl = (TextView) findViewById(R.id.game_ttl);


        Typeface custFont = Typeface.createFromAsset(getAssets(), "fonts/mavenpro_regular.ttf");

        gameBy.setTypeface(custFont);
        gameTtl.setTypeface(custFont);


        Thread timer = new Thread(){

            public void run(){
                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally {
                    Intent openStartPoint = new Intent("io.github.viralj.guessing.game.GameActivity");
                    startActivity(openStartPoint);
                }
            }

        };
        timer.start();
    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }

}
