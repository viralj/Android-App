package io.github.viralj.calcvilator;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;



/**
 * Created by Viral on 2/8/2015.
 */
enum Operation {
    NoOp,
    Add,
    Sub,
    Mult,
    Div
}

public class RegCalcActivity extends Activity {

    private TextView inputKB;               // getting input from custom keyboard and will display to EditText
    private TextView calcDisplay;           // To display calculated values
    private NumberFormat formatter;         // Decimal number formatter

    private double holder;
    private Operation op = Operation.NoOp;


    private boolean backPressedToExitOnce = false;      // We need this for quitting app
    private Toast toast = null;                         // We need this for quitting app

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_reg_calc);

        holder = 0.0;

        calcDisplay = (TextView) findViewById(R.id.calc_display);

        formatter = new DecimalFormat(",##0.##############");


        //We need to disable default soft keyboard and below codes will help to disable it
        inputKB = (TextView)findViewById(R.id.calc_ent);

        /*
        inputKB.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = inputKB.getInputType(); // backup the input type
                inputKB.setInputType(InputType.TYPE_NULL); // disable soft input
                inputKB.onTouchEvent(event); // call native handler
                inputKB.setInputType(inType); // restore input type

                return true; // consume touch even
            }
        });

        */

        //We need to set on click listeners for our custom keyboard buttons

        //if input is not empty


            //For Clear button
            ((Button)findViewById(R.id.cal_CLS)).setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    if((inputKB.getText().toString() != null && inputKB.getText().toString().length() > 0)
                            || (calcDisplay.getText().toString() != null && calcDisplay.getText().toString().length() > 0)) {
                        inputKB.setText("");
                        calcDisplay.setText("");
                    }
                    holder = 0.0;
                    op = Operation.NoOp;
                }

            });

            //For Plus button
            ((Button)findViewById(R.id.cal_PL)).setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {

                    try {
                        if (holder != 0.0) {
                            checkOP();
                        } else {
                            holder = Double.parseDouble(inputKB.getText().toString());
                        }
                        calcDisplay.setText(String.valueOf(formatter.format(holder)));

                        inputKB.setText("");

                    } catch(NumberFormatException nfe) {
                        //We are doing nothing here
                    }

                    op = Operation.Add;
                }
            });

            //For Minus button
            ((Button)findViewById(R.id.cal_MI)).setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    try {
                        if (holder != 0.0) {
                            checkOP();
                        } else {
                            holder = Double.parseDouble(inputKB.getText().toString());
                        }

                        calcDisplay.setText(String.valueOf(formatter.format(holder)));

                        inputKB.setText("");

                    } catch(NumberFormatException nfe) {
                        //We are doing nothing here
                    }

                    op = Operation.Sub;
                }
            });

            //For Multiply button
            ((Button)findViewById(R.id.cal_MUL)).setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {

                    try {
                        if (holder != 0.0) {
                            checkOP();
                        } else {
                            holder = Double.parseDouble(inputKB.getText().toString());
                        }
                        calcDisplay.setText(String.valueOf(formatter.format(holder)));

                        inputKB.setText("");

                    } catch(NumberFormatException nfe) {
                        //We are doing nothing here
                    }
                    op = Operation.Mult;

                }
            });

            //For Divide button
            ((Button)findViewById(R.id.cal_DIV)).setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {

                    try {
                        if (holder != 0.0) {
                            checkOP();
                        } else {
                            holder = Double.parseDouble(inputKB.getText().toString());
                        }
                        calcDisplay.setText(String.valueOf(formatter.format(holder)));

                        inputKB.setText("");

                    } catch(NumberFormatException nfe) {
                        //We are doing nothing here
                    }

                    op = Operation.Div;
                }
            });

            //For Equals button
            ((Button)findViewById(R.id.cal_EQ)).setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    if(inputKB.getText().toString().length() > 0){
                        try {
                            checkOP();
                            calcDisplay.setText(String.valueOf(formatter.format(holder)));
                            inputKB.setText("");
                            op = Operation.NoOp;
                        } catch(NumberFormatException nfe) {
                            calcDisplay.setText("Error");
                            inputKB.setText("");
                            op = Operation.NoOp;
                        }
                    }
                    else if(calcDisplay.getText().toString().length() > 0 && inputKB.getText().toString().length() < 1){
                        calcDisplay.setText(calcDisplay.getText().toString());
                        inputKB.setText("");
                    }
                    else{
                        calcDisplay.setText(String.valueOf(formatter.format(holder)));
                        inputKB.setText("");
                        op = Operation.NoOp;
                    }
                }
            });




        //For Square root button
        ((Button)findViewById(R.id.cal_SR)).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //converting square root to string
                //String sr = String.valueOf(formatter.format(Math.sqrt(new Double(inputKB.getText().toString()))));

                if(inputKB.getText().toString() != null && inputKB.getText().toString().length()>0) {
                    /*
                    if(sr.contains("."))
                        calcDisplay.setText(NumberFormat.getInstance().format(sr));
                    else
                        calcDisplay.setText(formatter.format(sr));
                    */

                    String sr = String.valueOf(formatter.format(Math.sqrt(new Double(inputKB.getText().toString().replace(",", "")))));
                    calcDisplay.setText(sr);

                    inputKB.setText("");
                }
                else if(calcDisplay.getText().toString() != null && calcDisplay.getText().toString().length()>0) {

                    String sr = String.valueOf(formatter.format(Math.sqrt(new Double(calcDisplay.getText().toString().replace(",", "")))));
                    calcDisplay.setText(sr);

                    inputKB.setText("");
                }
                else{
                    calcDisplay.setText("Error");
                }

                holder = Double.parseDouble(calcDisplay.getText().toString().replace(",", ""));
            }
        });


        //For Backspace button
        ((Button)findViewById(R.id.cal_DLT)).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(inputKB.getText().toString().length() > 0)
                    inputKB.setText(inputKB.getText().toString().substring(0, inputKB.getText().toString().length()-1));
            }
        });



        //For Zero button
        ((Button)findViewById(R.id.cal_N0)).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                inputKB.setText(inputKB.getText().toString()+0);
            }
        });

        //For One button
        ((Button)findViewById(R.id.cal_N1)).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                inputKB.setText(inputKB.getText().toString()+1);
            }
        });

        //For Two button
        ((Button)findViewById(R.id.cal_N2)).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                inputKB.setText(inputKB.getText().toString()+2);
            }
        });

        //For Three button
        ((Button)findViewById(R.id.cal_N3)).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                inputKB.setText(inputKB.getText().toString()+3);
            }
        });

        //For Four button
        ((Button)findViewById(R.id.cal_N4)).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                inputKB.setText(inputKB.getText().toString()+4);
            }
        });

        //For Five button
        ((Button)findViewById(R.id.cal_N5)).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                inputKB.setText(inputKB.getText().toString()+5);
            }
        });


        //For Six button
        ((Button)findViewById(R.id.cal_N6)).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                inputKB.setText(inputKB.getText().toString()+6);
            }
        });

        //For Seven button
        ((Button)findViewById(R.id.cal_N7)).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                inputKB.setText(inputKB.getText().toString()+7);
            }
        });

        //For Eight button
        ((Button)findViewById(R.id.cal_N8)).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                inputKB.setText(inputKB.getText().toString()+8);
            }
        });

        //For Nine button
        ((Button)findViewById(R.id.cal_N9)).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                inputKB.setText(inputKB.getText().toString()+9);
            }
        });

        //For Dot button
        ((Button)findViewById(R.id.cal_DOT)).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                if(inputKB.getText().toString().contains(".") == false)
                    inputKB.setText(inputKB.getText().toString()+".");
            }
        });

    }

    private void checkOP(){
        switch (op) {
            case NoOp:
                break;
            case Add:
                holder += Double.parseDouble(inputKB.getText().toString());
                break;
            case Sub:
                holder -= Double.parseDouble(inputKB.getText().toString());
                break;
            case Mult:
                holder *= Double.parseDouble(inputKB.getText().toString());
                break;
            case Div:
                holder /= Double.parseDouble(inputKB.getText().toString());
                break;
        }
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
