/**
 * Developer: Abed Almoradi
 * email: abd.almoradi@gmail.com
 * Date: 12/10/2014
 * A class that represents an item of the gridview of the board (a cell)
 */

package com.robox.my;

import com.robox.my.model.Board;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private Board board=Board.getBoard();
    private int[] iconArray={R.drawable.yellow,R.drawable.red,R.drawable.purple,
    						 R.drawable.drak_blue,R.drawable.pink,R.drawable.blue,
    						 R.drawable.orange,R.drawable.green,R.drawable.dark_green};
    public ImageAdapter() {

	}
    
    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return 16;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
    	
        TextView textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        if (convertView != null)  // if it's not recycled, initialize some attributes
        	textView= (TextView)convertView;
        float density = mContext.getResources().getDisplayMetrics().density;
        String oldValue= textView.getText().toString();
        textView.setTypeface(null, Typeface.BOLD);      	
        textView.setLayoutParams(new LayoutParams((int)(90* density + 0.5f),(int)(90* density + 0.5f)));//set the dimensions in the unit of density pixels (dp)
        
        
        if(!board.getMatrixEndGame()[position/4][position%4])
    	{
        
        
	        if(board.getItemAt(position/4, position%4 )!=0) // non-empty cell
	        {
	        	int index=log2(board.getItemAt(position/4, position%4 ));
	        	textView.setText(String.valueOf(board.getItemAt(position/4, position%4 )));
	        	if(oldValue!=null && oldValue!="" &&board.getMatrixB()[position/4][position%4])// merge operation happened in this cell
	        	{
	        		Animation mAnim = AnimationUtils.loadAnimation(mContext, R.anim.animate);
	                textView.startAnimation(mAnim);
	                textView.setBackgroundResource(iconArray[index%iconArray.length]);	
	        	}
	        	else 
	        	{
	        		
	        		TranslateAnimation in = new TranslateAnimation(
	        			    Animation.RELATIVE_TO_SELF,board.getMatrixC()[position/4][position%4] ,
	        			    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, board.getMatrixD()[position/4][position%4],  Animation.RELATIVE_TO_SELF, 0.0f
	        			    );
	        		 in.setDuration(200);
	        		 in.setFillAfter( true );
	        		 in.setZAdjustment(Animation.ZORDER_TOP);
	        		 textView.startAnimation(in);
	        		 textView.setBackgroundResource(iconArray[index%iconArray.length]);
	        	}
	        }
	        else // empty cell
	        { 
	        	textView.setText("");
	        	Animation mAnim = AnimationUtils.loadAnimation(mContext, R.anim.alpha);
	            textView.startAnimation(mAnim);
	            textView.setBackgroundResource(R.drawable.soft_grey);
	        }
    	}
        else
        {
        	TranslateAnimation in = new TranslateAnimation(
    			    Animation.RELATIVE_TO_SELF,0.0f ,
    			    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,  Animation.RELATIVE_TO_SELF, 10.0f
    			    );
    		 in.setDuration(900);
    		 in.setFillAfter( true );
    		 in.setZAdjustment(Animation.ZORDER_TOP);
    		 
    		 textView.startAnimation(in);
    	//	 board.getMatrixEndGame()[position/4][position%4]=false;
    	//	 textView.setBackgroundResource(R.drawable.soft_grey);
        }
   
        textView.setTextColor(Color.WHITE);
        return textView;
    }
    
    
    // To compute the Lg of a number
    private  int log2( int a)
    {
    	if(a==0)
    		return 0;
    	double d= (Math.log(a) / Math.log(2));
    	return (int)(d);
    }

   
}