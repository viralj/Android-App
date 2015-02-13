package io.github.viralj.calcvilator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

/**
 * Created by Viral on 2/7/2015.
 */
public class StartupActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Thread timer = new Thread(){

            public void run(){
                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally {
                    Intent openStartPoint = new Intent("io.github.viralj.calcvilator.RegCalcActivity");
                    startActivity(openStartPoint);
                }
            }

        };
        timer.start();
    }

    protected void onPause(){
        super.onPause();
        finish();
    }
}
