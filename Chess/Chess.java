// Coding Member: Chay Wen Ning

package Chess;
import Board.Board;
import Player.Player;

public class Chess {
    private Player[] players = new Player[2];
    private Board board;
    private int playerTurn;

    public Chess() {
        board = new Board();

        int offSetY = 4;
        boolean opposite = false;
        for (int i = 0; i < 2; i++) {
            players[i] = new Player();
            players[i].initializePieces(board, 0, offSetY, opposite);
            board.setPlayerPiece(players[i]);
            offSetY = 0;
            opposite = true;
        }

        playerTurn = 0; 
    }

    // Getters
    public Player getPlayer(int index) { return players[index]; }
    public Board getBoard() { return board; }
    public int getPlayerTurn() { return playerTurn; }


    public void switchTurn() {
        playerTurn = playerTurn == 1 ? 0 : 1;
        board.flip();
    }
}
