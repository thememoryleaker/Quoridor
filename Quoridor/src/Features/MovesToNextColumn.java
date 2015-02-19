package Features;

import main.GameBoard;
import main.Tile;
import Player.AiPlayer;
import Std.StdOut;

public class MovesToNextColumn implements Feature {
	
	GameBoard G;
	AiPlayer player;

	@Override
	public float evaluate(GameBoard G, AiPlayer player) {
		
		this.G = G;
		this.player = player;
		
		// determine direction towards goal
		int goalDirection = 0;
		if(player.win_x == 0) goalDirection = Tile.LEFT;
		if(player.win_x == GameBoard.DIM - 1) goalDirection = Tile.RIGHT;
		if(player.win_y == 0) goalDirection = Tile.UP;
		if(player.win_y == GameBoard.DIM - 1) goalDirection = Tile.DOWN;
		
		StdOut.println("Goal direction is " + goalDirection);
		
		return _stepsToNextColumn(goalDirection);
	}
	
	private int _stepsToNextColumn (int goalDirection) {
		int direct1, direct2;
		if(goalDirection == Tile.UP || goalDirection == Tile.DOWN) {
			direct1 = Tile.LEFT;
			direct2 = Tile.RIGHT;
		}
		else {
			direct1 = Tile.UP;
			direct2 = Tile.DOWN;
		}
		
		int a = _scanInDirection(goalDirection, direct1);
		int b = _scanInDirection(goalDirection, direct2);
		
		return Math.max(a, b);
	}
	
	private int _scanInDirection(int goalDirection, int directionToScan) {
		int steps = 1;
		Tile currentTile = G.board[player.x][player.y];
		Tile swap = currentTile;
		
		for(Tile t : swap.adj) StdOut.println(t + "");
		//StdOut.println("Adj:" + swap.adj[Tile.UP].x + "," + swap.adj[Tile.UP].y);
		
		while(swap.adj[goalDirection] == null) {
			if(swap.adj[directionToScan] == null) return Integer.MIN_VALUE;
			else swap = swap.adj[directionToScan];
			steps++;
		}
		
		return steps;
	}

}
