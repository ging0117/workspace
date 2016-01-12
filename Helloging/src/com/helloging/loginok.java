package com.helloging;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class loginok extends Activity {
	
	private TextView tv;

	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginok.this.setContentView(R.layout.loginok);
        tv = (TextView).tv.findViewById(R.id.login2_textView1);
        tv.setText(loginok.this.getResources().getString(R.string.success));
    }
		
		
}
    
	
    
   
