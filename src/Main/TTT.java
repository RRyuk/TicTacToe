/**
 * om1pa
 * Omid Pakdel
 */

package Main;

import AI.Play;

import java.util.Scanner;

public class TTT {

    private Board board;
    private Scanner sc = new Scanner(System.in);

    private TTT() {
        board = new Board();
    }

    /**
     * شروع
     */
    private void play () {

        System.out.println("Start");

        while (true) {
            printGameStatus();
            playMove();

            if (board.isGameOver()) {
                printWinner();

                if (!newGame()) {
                    break;
                }
            }
        }
    }

    /**
     * انتخاب نوبت o یا x
     */
    private void playMove () {
        if (board.getTurn() == Board.State.X) {
            getPlayerMove();
        } else {
            Play.alphaBetaPruning(board);
        }
    }
    /**
     * چاپ بورد و نوبت
     */
    private void printGameStatus () {
        System.out.println("\n" + board + "\n");
        System.out.println(board.getTurn().name() + "'s turn.");
    }

    /**
     *  شناسایی حرکت
     */
    private void getPlayerMove () {
        System.out.print("Shomare Harekat : ");

        int move = sc.nextInt();

        if (move < 0 || move >= Board.BOARD_WIDTH* Board.BOARD_WIDTH) {
            System.out.println("\nINVALID.");
            System.out.println("\nAdade Vared SHode Bayad Beyne 0 va  "
                    + (Board.BOARD_WIDTH * Board.BOARD_WIDTH - 1) + ", Bashad.");
        } else if (!board.move(move)) {
            System.out.println("\nINVALID.");
            System.out.println("\nKhane Qablan Grefte Shode Ast.");
        }
    }

    /**
     * چاپ برنده
     */
    private void printWinner () {
        Board.State winner = board.getWinner();

        System.out.println("\n" + board + "\n");

        if (winner == Board.State.Blank) {
            System.out.println("DRAW.");
        } else {
            System.out.println("Player " + winner.toString() + " Wins!");
        }
    }

    /**
     * بازی مجدد
     */
    private boolean newGame() {
        if (askNewGame()) {
            board.reset();
            System.out.println("Started new game.");
            System.out.println("X's turn.");
            return true;
        }

        return false;
    }

    private boolean askNewGame() {
        while (true) {
            System.out.print("Bazi Mojadad? (Y/N): ");
            String response = sc.next();
            if (response.equalsIgnoreCase("y")) {
                return true;
            } else if (response.equalsIgnoreCase("n")) {
                return false;
            }
            System.out.println("Invalid.");
        }
    }

    public static void main(String[] args) {
        TTT ticTacToe = new TTT();
        ticTacToe.play();
    }

}
