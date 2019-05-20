/**
 * om1pa
 * Omid Pakdel
 */

package Main;


import java.util.HashSet;
import java.util.Set;

public class Board {

    static final int BOARD_WIDTH = 3;

    public enum State {Blank, X, O}
    private State[][] board;
    private State playersTurn;
    private State winner;
    private Set<Integer> movesAvailable ;

    private int moveCount;
    private boolean gameOver;

    Board() {
        board = new State[BOARD_WIDTH][BOARD_WIDTH];
        movesAvailable = new HashSet<>();
        reset();
    }

    /**
     * خالی کردن خونه ها در اول بازی برای شروع
     */
    private void initialize () {
        for (int row = 0; row < BOARD_WIDTH; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                board[row][col] = State.Blank;
            }
        }

        movesAvailable.clear();

        for (int i = 0; i < BOARD_WIDTH*BOARD_WIDTH; i++) {
            movesAvailable.add(i);
        }
    }

    /**
     * ریست
     */
    void reset () {
        moveCount = 0;
        gameOver = false;
        playersTurn = State.X;
        winner = State.Blank;
        initialize();
    }

    /**
     * جای گذاری x یا o در محلی که کاربر یا کامپیوتر انتخاب میکند
     */
    public boolean move (int index) {
        return move(index% BOARD_WIDTH, index/ BOARD_WIDTH);
    }

    /**
     * چک کردن محل قرار گذاری و همچنین نوبت
     */
    private boolean move (int x, int y) {

        if (gameOver) {
            throw new IllegalStateException("Bazi Tamam Ast.");
        }

        if (board[y][x] == State.Blank) {
            board[y][x] = playersTurn;
        } else {
            return false;
        }

        moveCount++;
        movesAvailable.remove(y * BOARD_WIDTH + x);

        // مساوی
        if (moveCount == BOARD_WIDTH * BOARD_WIDTH) {
            winner = State.Blank;
            gameOver = true;
        }

        // چک کردن برنده
        checkRow(y);
        checkColumn(x);
        checkDiagonalFromTopLeft(x, y);
        checkDiagonalFromTopRight(x, y);

        playersTurn = (playersTurn == State.X) ? State.O : State.X;
        return true;
    }

    /**
     * چک کردن پایان بازی
     */
    public boolean isGameOver () {
        return gameOver;
    }


    /**
     * چک کردن نوبت
     */
    public State getTurn () {
        return playersTurn;
    }

    /**
     * چه کسی برنده شده است
     */
    public State getWinner () {
        if (!gameOver) {
            throw new IllegalStateException("Bazi Hanuz Edame Darad.");
        }
        return winner;
    }

    /**
     * خانه های مجاز برای حرکت
     */
    public Set<Integer> getAvailableMoves () {
        return movesAvailable;
    }

    /**
     * برنده در خط افقی
     */
    private void checkRow (int row) {
        for (int i = 1; i < BOARD_WIDTH; i++) {
            if (board[row][i] != board[row][i-1]) {
                break;
            }
            if (i == BOARD_WIDTH -1) {
                winner = playersTurn;
                gameOver = true;
            }
        }
    }

    /**
     * برنده در خط عمودی
     */
    private void checkColumn (int column) {
        for (int i = 1; i < BOARD_WIDTH; i++) {
            if (board[i][column] != board[i-1][column]) {
                break;
            }
            if (i == BOARD_WIDTH -1) {
                winner = playersTurn;
                gameOver = true;
            }
        }
    }

    /**
     * برنده در خط چپ قطری
     */
    private void checkDiagonalFromTopLeft (int x, int y) {
        if (x == y) {
            for (int i = 1; i < BOARD_WIDTH; i++) {
                if (board[i][i] != board[i-1][i-1]) {
                    break;
                }
                if (i == BOARD_WIDTH -1) {
                    winner = playersTurn;
                    gameOver = true;
                }
            }
        }
    }

    /**
     * برنده در خط راست قطری
     */
    private void checkDiagonalFromTopRight (int x, int y) {
        if (BOARD_WIDTH -1-x == y) {
            for (int i = 1; i < BOARD_WIDTH; i++) {
                if (board[BOARD_WIDTH -1-i][i] != board[BOARD_WIDTH -i][i-1]) {
                    break;
                }
                if (i == BOARD_WIDTH -1) {
                    winner = playersTurn;
                    gameOver = true;
                }
            }
        }
    }

    /**
     * کپی از کل بازی
     */
    public Board getCopy() {
        Board board             = new Board();

        for (int i = 0; i < board.board.length; i++) {
            board.board[i] = this.board[i].clone();
        }

        board.playersTurn       = this.playersTurn;
        board.winner            = this.winner;
        board.movesAvailable    = new HashSet<>();
        board.movesAvailable.addAll(this.movesAvailable);
        board.moveCount         = this.moveCount;
        board.gameOver          = this.gameOver;
        return board;
    }

    @Override
    public String toString () {
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < BOARD_WIDTH; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {

                if (board[y][x] == State.Blank) {
                    sb.append("-");
                } else {
                    sb.append(board[y][x].name());
                }
                sb.append(" ");

            }
            if (y != BOARD_WIDTH -1) {
                sb.append("\n");
            }
        }

        return new String(sb);
    }

}