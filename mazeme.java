import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.lang.Math;

public class mazeme {

	private int rows;
	private int col;
	private char[][] maze;
	private static int count1 = 0;
	private static int hit = 0;
	private static int num_intrud = 0;
	/*
	 * Constructors and functions
	 */
	public mazeme(){
		this.rows= 11;
		this.col = 11;
		this.maze = new char[rows][col];
	}
	public mazeme(int r, int c){
		this.rows = r;
		this.col = c;
		this.maze = new char[rows][col];
	}
	public char[][] getMaze(){
		return maze;
	}
	public void setMaze(char[][] m){
		this.maze = m;
	}
	public int getRows(){
		return rows;
	}
	public int getCol(){
		return col;
	}
	public void setRows(int r){
		this.rows = r;
	}
	public void setCol(int c){
		this.col = c;
	}

	/****************************************
	 * Function: prn()		print the maze
	 * @param maze
	 */
	public void prn (char[][] maze){
		for(int i = 0; i< getRows(); i++){
			System.out.println();
			for(int j = 0; j< getCol(); j++)
				System.out.print(maze[i][j]);
		}

		System.out.println();	
	}
	
	/****************************************
	 * Function: prn1() , this function is to print the position of walls
	 * @param m			: Array of rows of walls
	 * @param n			: Array of columns of walls
	 * @param r			: The count of walls
	 * Objective:		: for debugging purposes.
	 */
	public void prn1 (int[] m,int[] n,  int r){
		for(int i = 0; i< r; i++){
			
			System.out.print(" "+m[i]);
		}
		System.out.println();
		for(int i = 0; i< r; i++){
			
			System.out.print(" "+n[i]);
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);

		System.out.print("Do you want to play?\n");
		System.out.print("Put \"y\" for yes, and \"n\" for no, without the quote :");
		String keep = scan.next().toLowerCase();

		boolean ans = false;
		if (keep.equals("y")|| keep.equals("Y")){
			ans = true;
		}else{
			ans = false;
			System.exit(0);
		}

		System.out.print("enter the length of the maze (a number) :");
		String rt = scan.next();
		boolean check_num= isStringInt(rt);
		int r = 0;
		
		while(check_num == false){
			System.out.print("Number entered is not an integer");
			System.out.print("\nenter the length of the maze (a number) :");
			
			rt = scan.next();
			check_num= isStringInt(rt);
		}
		r = Integer.parseInt(rt);
		
		System.out.print("enter the percentage of wall inside the maze from 0-100 :");
		String perc = scan.next();
		check_num= isStringInt(perc);
		int percent = 0;
		
		while(check_num == false){
			System.out.print("Number entered is not an integer");
			System.out.print("\nenter the length of the maze (a number) :");
			
			perc = scan.next();
			check_num= isStringInt(perc);
		}
		percent = Integer.parseInt(perc);
		
		if(r <0){
			r = r *(-1);
		}else if(r % 2 == 0){
			r++;
		}else if(r == 1){
			r = 3;
		}
		int c = r;
		ArrayList<mazeme> list = new ArrayList<>();
		char[][] maze = new char[r][c];
		while(ans){
			
			boolean isSolve = false;
			maze = makeMaze(r,c, percent);
			list.add(new mazeme(1,0));
			isSolve = solveMaze(1,1,maze,list);
			
			if(isSolve){
				System.out.println("The VRunner has reached its target");
			}else{
				System.out.println("The VRunner has not found its target");
				
			//System.out.println("The maze has no solving path");
			}
			num_intrud = 0;
			list.clear();
			count1=0;

			System.out.print("Do you want to play again? \n");
			System.out.print("Put \"y\" for yes, and \"n\" for no, without the quote :");
			keep = scan.next().toLowerCase();

			ans = false;
			if (keep.equals("y")|| keep.equals("Y")){
				ans = true;
			}else{
				ans = false;
				System.exit(0);
			}

			System.out.print("enter the length of the maze (a number) :");
			rt = scan.next();
			
			check_num= isStringInt(rt);
//			r = 0;
			
			while(check_num == false){
				System.out.print("Number entered is not an integer");
				System.out.print("\nenter the length of the maze (a number) :");
				
				rt = scan.next();
				check_num= isStringInt(rt);
			}
			r = Integer.parseInt(rt);
			
			System.out.print("enter the percentage of wall inside the maze from 0-100 :");
			perc = scan.next();
			check_num= isStringInt(perc);
			percent = 0;
			
			while(check_num == false){
				System.out.print("Number entered is not an integer");
				System.out.print("\nenter the length of the maze (a number) :");
				
				perc = scan.next();
				check_num= isStringInt(perc);
			}
			percent = Integer.parseInt(perc);
			if(r<0){
				r = r*(-1);
			}else if(r % 2 == 0){
				r++;
			}else if(r == 1){
				r = 3;
			}
			c = r;
		}

	}
	/****************************************
	 * Function: prn_path()		print the maze directions
	 * @param l
	 */
	private static void prn_path(ArrayList<String> l){
		System.out.println(" The Direction to follow is below:");
		for(int i = 0; i< l.size(); i++){
			if(i%10 !=0){
				System.out.print(l.get(i)+"-->");
			}else if((i % 10 ==0)){
				System.out.println();
				System.out.print(l.get(i)+"-->");
			}
		}
		System.out.println();	
	}


	private static boolean isStringInt(String rt) {
		try
	    {
	        Integer.parseInt(rt);
	        return true;
	    } catch (NumberFormatException ex)
	    {
	        return false;
	    }

	}
	private static void path_finder(ArrayList<mazeme> list){

		ArrayList<String> path = new ArrayList<>();
		System.out.println(list.size());
		for(int k1 =0; k1< list.size()-1; k1++){
			
			if(list.get(k1).getRows() == list.get(k1+1).getRows()){
				if(list.get(k1).getCol() < list.get(k1+1).getCol()){
					path.add("East");
				}else{
					path.add("West");
				}
				
			}else if(list.get(k1).getCol() == list.get(k1+1).getCol()){
				if(list.get(k1).getRows() < list.get(k1+1).getRows()){
					path.add("South");
				}else{
					path.add("North");
				}
			}
		}
		prn_path(path);
	}
	
	/***********************************************************************
	 * Function		: tearDown()
	 * @param row	:
	 * @param col	:
	 * @param m		:
	 */
	private static void tearDown(int row, int col, char[][] m) {
		mazeme mazeObject1 = new mazeme(m.length,m.length);
		char[][] maze1 = new char[m.length][m.length];
		maze1 = m;

		if (row % 2 == 1 && maze1[row][col-1] != maze1[row][col+1]) {
			// row is odd; wall separates rooms horizontally
			fill(row, col-1,mazeObject1.getMaze(), maze1[row][col-1], maze1[row][col+1]);
			maze1[row][col] = maze1[row][col+1];
			mazeObject1.setMaze(maze1);
			putSquare(row,col,mazeObject1.getMaze(),' ');
		}
		else if (row % 2 == 0 && maze1[row-1][col] != maze1[row+1][col]) {
			// row is even; wall separates rooms vertically
			fill(row-1, col,mazeObject1.getMaze(), maze1[row-1][col], maze1[row+1][col]);
			maze1[row][col] = maze1[row+1][col];
			mazeObject1.setMaze(maze1);
			putSquare(row,col,mazeObject1.getMaze(),' ');		
		}

	}

	private static void putSquare(int row, int col,char[][] m, char c) {
		
		char[][]maze= m;
		mazeme mazeObject1 = new mazeme(maze.length, maze.length);
		maze[row][col] = c;
		mazeObject1.setMaze(maze);
		//count1++;

	}

	private static void fill(int row, int col, char[][] m,char replace, char replaceWith) {
		
		char[][]maze = m;
		mazeme mazeObject2 = new mazeme(maze.length, maze.length);

		if (maze[row][col] ==replace) {
			maze[row][col] = replaceWith;
			mazeObject2.setMaze(maze);
			fill(row+1,col,m,replace,replaceWith);
			fill(row-1,col,m,replace,replaceWith);
			fill(row,col+1,m,replace,replaceWith);
			fill(row,col-1,m,replace,replaceWith);
		}

	}

	private static char[][] makeMaze(int r, int c, int percent_wall){
		mazeme mazeObject = new mazeme();

		mazeObject.setRows(r);
		mazeObject.setCol(c);
		
		char empty= 32;
		int empty1=0;
		/*Create a square maze where everything is wall */
		int i=0,j=0;
		//int emptyCt = 0; // number of rooms
		int wallCt = 0;  // number of walls
		int[] wallrow = new int[(mazeObject.getRows()*mazeObject.getCol())/2];  // position of walls between rooms
		int[] wallcol = new int[(mazeObject.getRows()*mazeObject.getCol())/2];

		char[][] mazet = new char[r][c];
		for(i = 0; i< mazeObject.getRows(); i++)
			for(j = 0; j< mazeObject.getCol(); j++)
				mazet[i][j] = 'x';        // every space is a wall

	
		for( i = 1; i<mazeObject.getRows()-1; i+=2)
			for(j = 1; j<mazeObject.getCol() -1; j+=2){
				empty++;
				mazet[i][j] = empty; 
				//mazet[i][j] =String.valueOf(empty1).;          // create a grid, with ASCII characters inside			

				if (i < mazeObject.getRows()-2) {  // record info about wall below this room
					wallrow[wallCt] = i+1;
					wallcol[wallCt] = j;
					wallCt++;
				}
				if (j < mazeObject.getCol()-2) {  // record info about wall to right of this room
					wallrow[wallCt] = i;
					wallcol[wallCt] = j+1;
					wallCt++;
				}
			}
//		mazeObject.prn1(wallrow, wallcol, wallCt);   // this display the walls position in the grid. 

		mazeObject.setMaze(mazet);
		
		/* Create randoms wall*/
		Random rand = new Random();
		
		// Randomly create wall based on user's level of difficulty input
		for (int k=wallCt-1; k>0; k--) {
			int rand_value = rand.nextInt(100);
			if(rand_value > percent_wall ){
				tearDown(wallrow[k],wallcol[k],mazeObject.getMaze());
			}
			
			
		}

		mazet = mazeObject.getMaze();

		// Replace all remaining characters that are not walls
		for(i =1; i<mazeObject.getRows()-1; i++)
			for(j =1; j<mazeObject.getRows()-1; j++){
				if(mazet[i][j] != 'x')
					mazet[i][j] = ' ';
			}
			/* Initialize the starting point */

		mazet[1][0] = '@';                 // position of the object

		mazeObject.setMaze(mazet);
		return mazeObject.getMaze();

	}
/*****************************************************************
 * Function: solveMaze()
 * @param row	:
 * @param col	:
 * @param m		:
 * @return		:
 * Goal			:Try to solve the maze by continuing current path from position
		         (row,col).  Return true if a solution is found.  The maze is
		         considered to be solved if the path reaches the lower right cell.
 */

	private static boolean solveMaze(int row, int col, char[][]m, ArrayList<mazeme> list) {
		// Try to solve the maze by continuing current path from position
		// (row,col).  Return true if a solution is found.  The maze is
		// considered to be solved if the path reaches the lower right cell.
		char[][] maze = m;
		mazeme mazeObject2 = new mazeme(m.length, m.length);

		int speedSleep = 500;
		

		if (maze[row][col] == ' ') {
			maze[row][col] = '@';      // add this cell to the path
			putSquare(row,col,m,'@');
			mazeObject2.setMaze(m);
			list.add(new mazeme(row,col));  // Add the position of the VRunner '@'
			count1++;
			mazeObject2.prn(mazeObject2.getMaze());
			if (row == mazeObject2.getRows()-2 && col == mazeObject2.getCol()-2){
				path_finder(list);
				return true;  // path has reached goal
			}
				
			try { Thread.sleep(speedSleep); }
			catch (InterruptedException e) { }
			if ( (solveMaze(row,col+1,m,list)) ||     // try to solve maze by extending path
					(solveMaze(row,col-1,m,list))   ||     //    in each possible direction
					(solveMaze(row+1,col,m,list))  ||
					solveMaze(row-1,col,m,list) )
				return true;
			// maze can't be solved from this cell, so backtrack out of the cell
			maze[row][col] = '.';   // mark cell as having been visited
			putSquare(row,col,m,'.');
			list.remove(count1);     //Remove the last position of the VRunner when backtracking
			count1--;
			mazeObject2.prn(mazeObject2.getMaze());
			//count1++;

			try {
				Thread.sleep(speedSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

}
