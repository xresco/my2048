/**
 * Developer: Abed Almoradi
 * email: abd.almoradi@gmail.com
 * Date: 12/10/2014
 * An entity class that represents the logic of the board 
 */

package com.robox.my.model;

import java.util.ArrayList;
import java.util.Random;



//Singlton
public class Board {
	private Random rand=new Random(Integer.MAX_VALUE );
	private int[][] matrix=new int[4][4];
	private boolean[][] matrixB=new boolean[4][4];
	private float[][] matrixC=new float[4][4];// Right-Left translation buffer (tells the number of steps that the cell took to reach this point)
	private float[][] matrixD=new float[4][4];// Up-Down translation buffer
	private static Board board=null;
	
	//getters
	public float[][] getMatrixD() {
		return matrixD;
	}
	public float[][] getMatrixC() {
		return matrixC;
	}
	public static Board getBoard()
	{
		if(board==null)
			board=new Board();
		return board;
	}
	public boolean[][] getMatrixB() {
		return matrixB;
	}
	public int[][] getMatrix() {
		
		return matrix;
	}
	
	public int getItemAt(int index1,int index2)
	{
		return matrix[index1][index2];
	}
	
	
	private Board() {
		rand.setSeed(System.currentTimeMillis() );
		for(int i=0;i<4;i++)
		{
			for(int j=0;j<4;j++)
			{
				matrix[i][j]=0;
				matrixB[i][j]=false;
			}
		}
		
		//cett the value of two random cells to 2
		int position=rand.nextInt(16);
		matrix[position/4][ position%4]=2;
		position=rand.nextInt(16);
		matrix[position/4][ position%4]=2;
	}
	
	//clear the buffers
	public void clearMatrices()
	{
		for(int i=0;i<4;i++)
			for(int j=0;j<4;j++)
			{
				matrixB[i][j]=false;
				matrixC[i][j]=0;
				matrixD[i][j]=0;
			}
	}
	
	//Tests whether the games has ended or not
	public boolean checkGameOver()
	{
		for(int i=0;i<4;i++)
		{
			for(int j=0;j<4;j++)
			{
				if(matrix[i][j]==0)
					return false;
				if(i!=0)
					if(matrix[i][j]==matrix[i-1][j])
						return false;
				if(j!=0)
					if(matrix[i][j]==matrix[i][j-1])
						return false;
				
			}
		}
		return true;
	}
	//Left Operations 
	public  Boolean swipeLeftOnce() {
		Boolean isShift=false;
		//shifting
		for(int i=0;i<4;i++)
			for(int j=1;j<4;j++)
				if(matrix[i][j-1]==0&&matrix[i][j]!=0)
				{
					matrix[i][j-1]=matrix[i][j];
					matrixC[i][j-1]=matrixC[i][j]+1;
					matrix[i][j]=0;
					matrixC[i][j]=0;
					isShift=true;
				}
		return isShift;
	}
	public int accumelateLeft()
	{
		int accumelate=0;
		for(int i=0;i<4;i++)
			for(int j=1;j<4;j++)
				if(matrix[i][j]==0)
					j=4;
				else if(matrix[i][j-1]!=0&&matrix[i][j]!=0&&matrix[i][j-1]==matrix[i][j])
				{
					matrix[i][j-1]+=matrix[i][j];
					matrixB[i][j-1]=true;
					matrix[i][j]=0;
					accumelate+=matrix[i][j-1];
				}
		return accumelate;
	}
	
	//Right  Operations 
	public  Boolean swipeRightOnce() {
		Boolean isShift=false;
		//shifting
		for(int i=0;i<4;i++)
			for(int j=3;j>0;j--)
				if(matrix[i][j-1]!=0&&matrix[i][j]==0)
				{
					matrix[i][j]=matrix[i][j-1];
					matrixC[i][j]=matrixC[i][j-1]-1;
					matrix[i][j-1]=0;
					matrixC[i][j-1]=0;
					isShift=true;
				}
		return isShift;
	}
	public int accumelateRight()
	{
		int acc=0;
		for(int i=0;i<4;i++)
			for(int j=3;j>0;j--)
				if(matrix[i][j]==0)
					j=0;
				else if(matrix[i][j-1]!=0&&matrix[i][j]!=0&&matrix[i][j-1]==matrix[i][j])
				{
					matrix[i][j]+=matrix[i][j-1];
					matrixB[i][j]=true;
					matrix[i][j-1]=0;
					acc+=matrix[i][j];
				}
		return acc;
	}
	
	
	//Up Operations 
	public  Boolean swipeUpOnce() {
		Boolean isShift=false;
		//shifting
		for(int i=1;i<4;i++)
			for(int j=0;j<4;j++)
				if(matrix[i-1][j]==0&&matrix[i][j]!=0)
				{
					matrix[i-1][j]=matrix[i][j];
					matrixD[i-1][j]=matrixD[i][j]+1;
					matrix[i][j]=0;
					matrixD[i][j]=0;
					isShift=true;
				}
		return isShift;
	}
	public int accumelateUp()
	{
		int acc=0;
		for(int j=0;j<4;j++)
			for(int i=1;i<4;i++)
				if(matrix[i][j]==0)
					i=4;
				else if(matrix[i-1][j]!=0&&matrix[i][j]!=0&&matrix[i-1][j]==matrix[i][j])
				{
					matrix[i-1][j]+=matrix[i][j];
					matrixB[i-1][j]=true;
					matrix[i][j]=0;
					acc+=matrix[i-1][j];
				}
		return acc;
	}
	
	
	//Down  Operations 
	public  Boolean swipeDownOnce() {
		Boolean isShift=false;
		//shifting
		for(int i=3;i>0;i--)
			for(int j=0;j<4;j++)
				if(matrix[i-1][j]!=0&&matrix[i][j]==0)
				{
					matrix[i][j]=matrix[i-1][j];
					matrixD[i][j]=matrixD[i-1][j]-1;
					matrix[i-1][j]=0;
					matrixD[i-1][j]=0;
					isShift=true;
				}
		return isShift;
	}
	public int accumelateDown()
	{
		int acc=0;
		for(int j=0;j<4;j++)
			for(int i=3;i>0;i--)
				if(matrix[i][j]==0)
					i=0;
				else if(matrix[i-1][j]!=0&&matrix[i][j]!=0&&matrix[i-1][j]==matrix[i][j])
				{
					matrix[i][j]+=matrix[i-1][j];
					matrixB[i][j]=true;
					matrix[i-1][j]=0;
					acc+=matrix[i][j];
					
				}
		return acc;
	}
		
//place the new "2" randomly in the array
	public void addRandomly() {
		ArrayList<Integer> zeroCells=new ArrayList<Integer>();
		int counter=0;
		for(int i=0;i<4;i++)
		{
			for(int j=0;j<4;j++)
			{
				if(matrix[i][j]==0)
					zeroCells.add(counter);
				counter++;
			}
		}
		int random=rand.nextInt(zeroCells.size());
		random=zeroCells.get(random);
		matrix[random/4][random%4]=2;
	}
	
	

}
