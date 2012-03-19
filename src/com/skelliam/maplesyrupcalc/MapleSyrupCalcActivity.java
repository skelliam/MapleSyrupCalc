package com.skelliam.maplesyrupcalc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MapleSyrupCalcActivity extends Activity implements OnClickListener {
    
    private EditText txtDiam;
    private EditText txtHeight;
    private EditText txtSugar;
    private EditText txtSapVolume;
    private EditText txtSyrupVol;
    private EditText txtEvapTime;
    private Button btnCalculate;
    
    private double height;
    private double diam;
    private double volume;
    private double volume_gals;
    private double final_volume_gals;
    private double starting_sugar_brix;
    private double starting_sugar_sg;
    private double evap_time;
    private double test;
    
    final private double PI = 3.14159;
    final private double CUIN_TO_GALS = 231;
    final private double FINAL_SUGAR = 62;
    final private double EVAP_RATE = 0.4;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    private void getInputs() {
        DensityConverter dc = new DensityConverter();
        height = Double.parseDouble(txtHeight.getText().toString());
        diam = Double.parseDouble(txtDiam.getText().toString());
        starting_sugar_brix = Double.parseDouble(txtSugar.getText().toString());
        starting_sugar_sg = dc.getSGFromBrix(starting_sugar_brix);
        test = dc.getBrixFromSG(1.1);
        // final sugar comes from prefs
    }
    
    private void calcVolume() {
        volume = (PI * Math.pow(diam/2, 2)) * height;  /* cuin */
        volume_gals = volume/CUIN_TO_GALS;
    }
    
    /* Starting_Volume/Final_Syrup_Volume = Final_Sugar/Starting_Sugar */
    private void calcFinalVolume() {
        if ( (FINAL_SUGAR != starting_sugar_brix) && (FINAL_SUGAR != 0) )
            final_volume_gals = volume_gals/(FINAL_SUGAR/starting_sugar_brix);
    }
    
    private void calcEvapTime() {
        if (EVAP_RATE != 0)
        evap_time = (volume_gals-final_volume_gals)/EVAP_RATE;
    }
    
    public void calculateAll() {
        if (starting_sugar_brix < 0.1) {
            this.starting_sugar_brix = 0.1;
        }
        getInputs();
        calcVolume();
        calcFinalVolume();
        calcEvapTime();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnCalculate:
                calculateAll();
                txtSapVolume.setText(String.format("%f", volume_gals));
                //txtSyrupVol.setText(String.format("%f", final_volume_gals));
                //txtEvapTime.setText(String.format("%f", evap_time));
                
                /* Debugging */
                txtEvapTime.setText(String.format("%f", starting_sugar_sg));
                txtSyrupVol.setText(String.format("%f", test));
        }
    }
    
    public void onResume() {
        
        super.onResume();
        setContentView(R.layout.main);
        
        txtDiam = (EditText)findViewById(R.id.txtDiam);
        txtHeight = (EditText)findViewById(R.id.txtHeight);
        txtSugar = (EditText)findViewById(R.id.txtSugar);
        txtSapVolume = (EditText)findViewById(R.id.txtSapVol);
        txtSyrupVol = (EditText)findViewById(R.id.txtSyrupVol);
        txtEvapTime = (EditText)findViewById(R.id.txtEvapTime);
        
        btnCalculate = (Button)findViewById(R.id.btnCalculate);
        
        /* add click listeners */
        btnCalculate.setOnClickListener(this);
    }
    
}