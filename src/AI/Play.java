/**
 * om1pa
 * Omid Pakdel
 */

package AI;

import Main.Board;

public class Play {

    private Play() {}

    public static void alphaBetaPruning (Board board) {
        AlphaBetaPruning.run(board.getTurn(), board);
    }

}
