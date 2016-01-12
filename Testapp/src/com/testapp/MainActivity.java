package com.testapp;

import android.support.v7.app.ActionBarActivity;

import android.view.MenuItem;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//to inflate xml to activity
		setContentView(R.layout.activity_main);
		
		//to inflate button to our activity
		Button button=(Button) findViewById(R.id.button1);
	    TextView textview=(TextView) findViewById(R.id.textview);
	    ToggleButton toggelbutton =( ToggleButton) findViewById (R.id.toggelbutton);
	    RatingBar ratingbar= (RatingBar)findViewById (R.id.ratingbar);
	    CheckBox checkbox =(CheckBox)findViewById (R.id.checkbox);
	    RadioButton radiobutton =( RadioButton)findViewById (R.id.radiobutton);
	    ImageView imageview =( ImageView)findViewById (R.id.imageview);
	    ImageButton imagebutton =(ImageButton)findViewById (R.id.imagebutton);
		//Adding click to button
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//to dispaly the message
				Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_LONG).show();
			}
			
			
			
		});
button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//to dispaly the message
				Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_LONG).show();
			}
			
			
			
		});
textview.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//to dispaly the message
		Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_LONG).show();
	}
	
	
	
});
toggelbutton.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//to dispaly the message
		Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_LONG).show();
	}
	
	
	
});
ratingBar.setOnTouchListener(new OnTouchListener() {
    @Override
    public boolean onTouch(View v) {
        Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_LONG).show();

            // TODO perform your action here
   
    }
	
	
});
checkbox.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//to dispaly the message
		Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_LONG).show();
	}
	
	
	
});
radiobutton.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//to dispaly the message
		Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_LONG).show();
	}
	
	
	
});
imageview.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//to dispaly the message
		Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_LONG).show();
	}
	
	
	
});
		
imagebutton.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//to dispaly the message
		Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_LONG).show();
	}
	
	
	
});	
		
		
		
	}
	
	 
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


}









public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
