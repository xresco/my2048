/**
 * Developer: Abed Almoradi
 * email: abd.almoradi@gmail.com
 * Date: 12/10/2014
 * A class that represents the activity being displayed on the screen 
 */

package com.robox.my;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.robox.my.SimpleGestureFilter.SimpleGestureListener;
import com.robox.my.model.Board;


public class MainActivity extends Activity implements SimpleGestureListener{
	private SimpleGestureFilter detector;
	private ImageAdapter imgAda;
	private int score=0;
	private TextView txtScore;
	private GridView gridview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        detector = new SimpleGestureFilter(this,this);
        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.linearView);
       // linearLayout.setBackgroundColor(Color.rgb(34, 35, 38));
        linearLayout.setBackgroundColor(Color.rgb(250, 250, 250));
        
        gridview = (GridView) findViewById(R.id.gridview);
        
        gridview.setAdapter(imgAda=new ImageAdapter(this));
      //  gridview.setBackgroundColor(Color.rgb(34, 35, 38));
        gridview.setBackgroundColor(Color.rgb(250, 250, 250));
        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
       // getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80b702")));
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#626262")));
          
        txtScore=(TextView) findViewById(R.id.txtScore);
      //  txtScore.setBackgroundResource(R.drawable.grey);
        txtScore.setTextColor(Color.parseColor("#626262"));
        txtScore.setText("");
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
    
    @Override
    public boolean dispatchTouchEvent(MotionEvent me){
        // Call onTouchEvent of SimpleGestureFilter class
         this.detector.onTouchEvent(me);
       return super.dispatchTouchEvent(me);
    }
    @Override
     public void onSwipe(int direction) {

      int acc=0;
      int counter=0;
      Board.getBoard().clearMatrices();
      switch (direction) {
      
      case SimpleGestureFilter.SWIPE_RIGHT : 
    	  while(Board.getBoard().swipeRightOnce()){counter++;};
    	  acc+=Board.getBoard().accumelateRight();
    	  while(Board.getBoard().swipeRightOnce()){counter++;};
    	  if(counter>0||acc>0)
    	  	Board.getBoard().addRandomly();
          imgAda.notifyDataSetChanged();
          break;
      case SimpleGestureFilter.SWIPE_LEFT :  
    	  while(Board.getBoard().swipeLeftOnce()){counter++;};
    	   acc+=Board.getBoard().accumelateLeft();
    	  while(Board.getBoard().swipeLeftOnce()){counter++;};
    	  if(counter>0||acc>0)
    		  Board.getBoard().addRandomly();
          imgAda.notifyDataSetChanged();
          break;
      case SimpleGestureFilter.SWIPE_DOWN : 
    	  while(Board.getBoard().swipeDownOnce()){counter++;};
    	  acc+=Board.getBoard().accumelateDown();
    	  while(Board.getBoard().swipeDownOnce()){counter++;};
    	  if(counter>0||acc>0)
    		  Board.getBoard().addRandomly();
          imgAda.notifyDataSetChanged();
          break;
      case SimpleGestureFilter.SWIPE_UP :    
    	  while(Board.getBoard().swipeUpOnce()){counter++;};
    	  acc+=Board.getBoard().accumelateUp();
    	  while(Board.getBoard().swipeUpOnce()){counter++;};
    	  if(counter>0||acc>0)
    	  	Board.getBoard().addRandomly();
          imgAda.notifyDataSetChanged();
    	  break;
      
      }

      score+=acc;
      txtScore.setText("Score: "+score);
     
      if(Board.getBoard().checkGameOver())
    	  Toast.makeText(this, "Game Over!", Toast.LENGTH_SHORT).show();
     }
      
    public void pause() {
    	try {
			Thread.sleep(600);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
     @Override
     public void onDoubleTap() {
     //   Toast.makeText(this, "Double Tap", Toast.LENGTH_SHORT).show();
     }
}
