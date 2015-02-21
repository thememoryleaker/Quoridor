package main;

import java.awt.Color;
import java.util.Scanner;

import Features.MovesToNextColumn;
import Features.opponentMovesToNextColumn;
import Player.AiPlayer;
import Std.StdDraw;
import Std.StdOut;

public class MainActivity {

	private final int windowDim = 1000;
	private final int padding = 100;
	private final int scale = 100;

	private final Color p1 = Color.BLUE;
	private final Color p2 = Color.YELLOW;
	private final Color p3 = Color.GREEN;
	private final Color p4 = Color.RED;
	private final Color[] colors = { p1, p2, p3, p4 };

	private GameBoard board;

	public MainActivity() {

		// set the scale for drawing on screen
		StdDraw.setScale(0, windowDim);
		board = new GameBoard(2);
		board.reset();

	}

	// draw the board
	public void draw() {

		// draw the grids of the board
		for (int i = 0; i < GameBoard.DIM + 1; i++) {
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.setPenRadius(0.001);
			int x = padding / 2 + i * scale;
			int y1 = padding / 2;
			int y2 = windowDim - y1;
			StdDraw.line(x, y1, x, y2);
			StdDraw.line(y1, x, y2, x);
		}

		// draw the tile as dots
		for (int i = 0; i < GameBoard.DIM; i++) {
			for (int j = 0; j < GameBoard.DIM; j++) {
				// draw the dot
				StdDraw.setPenColor(StdDraw.BLACK);
				// if(i == 0 && j == 0) StdDraw.setPenColor(StdDraw.RED);
				StdDraw.setPenRadius(0.05);
				int x, y;
				x = padding + i * scale;
				y = padding + j * scale;
				StdDraw.point(x, y);
				// add the lines
				Tile t = this.board.board[i][j];
				if (t.hasNeighbour(Tile.UP)) {
					StdDraw.setPenRadius(0.001);
					StdDraw.line(x, y, x, y + scale);
				}
				if (t.hasNeighbour(Tile.RIGHT)) {
					StdDraw.setPenRadius(0.001);
					StdDraw.line(x, y, x + scale, y);
				}
				if (t.hasNeighbour(Tile.DOWN)) {
					StdDraw.setPenRadius(0.001);
					StdDraw.line(x, y, x, y - scale);
				}
				if (t.hasNeighbour(Tile.LEFT)) {
					StdDraw.setPenRadius(0.001);
					StdDraw.line(x, y, x - scale, y);
				}
			}
		}

		// draw all the walls
		int NW_x, NW_y;
		for (WallMove w : this.board.wallMoves) {
			NW_x = (int) ((w.x - 0.5) * scale + padding);
			NW_y = (int) ((w.y + 0.5) * scale + padding);
			StdDraw.setPenRadius(0.01);
			StdDraw.setPenColor(colors[w.player.playerNo]);
			if (w.wallOrientation == WallMove.HORIZONTAL) {
				StdDraw.line(NW_x - scale, NW_y, NW_x + scale, NW_y);
			} else {
				StdDraw.line(NW_x, NW_y - scale, NW_x, NW_y + scale);
			}
		}

		// add the numbers on the axis
		for (int x = 0; x < GameBoard.DIM; x++) {
			StdDraw.setPenColor(Color.BLACK);
			StdDraw.text(x * scale + padding, padding / 4, x + "");
			StdDraw.text(padding / 4, x * scale + padding, x + "");
		}

		// color the tiles with players
		for (AiPlayer p : board.players) {
			drawDot(board.board[p.x][p.y], p.playerNo);
		}

	}

	private void drawDot(Tile t, int playerNo) {
		int x, y;
		x = padding + t.x * scale;
		y = padding + t.y * scale;
		StdDraw.setPenColor(colors[playerNo]);
		StdDraw.setPenRadius(0.05);
		StdDraw.point(x, y);
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.text(x, y, playerNo + "");

	}

	private void playMove(Move move) {
		board.playMove(move);
		StdDraw.clear();
		draw();
	}

	// client to display board
	public static void main(String[] args) {
		MainActivity main = new MainActivity();
		main.draw();
		
		// test the shortest path search
		int[] coords = main.board.BFS(main.board.players[0]);
		StdOut.println("Next step is (" + coords[0] + "," + coords[1] + ")");
		
//		PlayerMove m1 = new PlayerMove(main.board.players[1], 8, 3);
//		WallMove w1 = new WallMove(main.board.players[0], 8, 3, WallMove.HORIZONTAL);
//		WallMove w2 = new WallMove(main.board.players[0], 8, 2, WallMove.VERTICAL);
//		main.playMove(w1);
//		main.playMove(w2);
//		main.playMove(m1);
//		StdOut.println("Moves to next column P1: " + new MovesToNextColumn().evaluate(main.board, main.board.players[1]));
//		StdOut.println("Oppo Steps to next column P0: " + new opponentMovesToNextColumn().evaluate(main.board, main.board.players[0]));
//		// test the move

		/*
		 * PlayerMove p1 = new PlayerMove(main.board.players[0], 4, 7);
		 * main.playMove(p1); StdOut.println("Score MovesToNextColumn: " + new
		 * MovesToNextColumn().evaluate(main.board, main.board.players[0]));
		 * StdOut.println("Score PositionFromBaseline: " + new
		 * PositionFromBaseline().evaluate(main.board, main.board.players[0]));
		 * StdOut.println("Score PositionFromBaseline: " + new
		 * PositionFromBaseline().evaluate(main.board, main.board.players[1]));
		 * StdOut.println("Score PositionDifference: " + new
		 * PositionDifference().evaluate(main.board, main.board.players[0]));
		 */

		// ensure that the edges have been added
		/*
		 * for(int i = 0 ; i < 9; i++) { for(int j = 0; j < 9; j++) {
		 * StdOut.print("Tile (" + main.board.board[i][j].x + "," +
		 * main.board.board[i][j].y + "):"); for(Tile t :
		 * main.board.board[i][j].adj) { if(t != null) StdOut.print(" (" + t.x +
		 * "," + t.y + ")"); } StdOut.println(); } }
		 */

		// ]StdDraw.show(10);

		// test the generate moves function WORKS!
		// main.board.generateMoves(main.board.players[0]);

		// lets try for 20 turns!

//		for (int i = 0; i < 1000; i++) {
//			StdOut.println("Turn " + i);
//			main.board.playTurn();
//			StdDraw.clear();
//			main.draw();
//			if (main.board.winningPlayer != Integer.MAX_VALUE) {
//				StdOut.println("Player " + main.board.winningPlayer
//						+ " has won after " + i + " turns");
//				break;
//			}
//			try {
//				Thread.sleep(100);
//				// 1000 milliseconds is one second.
//			} catch (InterruptedException ex) {
//				Thread.currentThread().interrupt();
//			}
//		}
		

		// test the evaluation features
		// StdOut.println("Score: " + new
		// PositionFromBaseline().evaluate(main.board, main.board.players[0]));
		// StdOut.println("Score: " + new
		// PositionDifference().evaluate(main.board, main.board.players[0]));
		/*
		 * StdOut.println("Score: " + new
		 * MovesToNextColumn().evaluate(main.board, main.board.players[1]));
		 */

	}

}
