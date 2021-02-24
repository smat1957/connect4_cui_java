package nmoku;

import static nmoku.Constants.*;

public class Game {

    private Player[] player = null;
    private Board board = null;
    private int current_player = 0;
    private Player currPlayer = null;

    public Game(boolean reverse) {
        player = new Player[2];
        if (reverse) {
            player[0] = new Player(MARU, "PC", ALPHABETA);
            player[1] = new Player(BATSU, "あなた", PROMPT);
        } else {
            player[0] = new Player(BATSU, "あなた", PROMPT);
            player[1] = new Player(MARU, "PC", ALPHABETA);
        }
        currPlayer = player[0];
        board = new Board();
    }
    void Start() {
        int winner = NEXT;
        System.out.println("スタート！ [ " + NMOKU + "目並べ ]");
        do {
            board.print();
            currPlayer = player[current_player];
            currPlayer.putStone(board);
            winner = board.check();
            current_player = ++current_player % 2;
        } while (winner == NEXT);
        board.print();
        result(winner);
    }
    private void result(int winner) {
        // 結果の表示
        System.out.println();
        switch (winner) {
            case DRAW:
                System.out.print("引き分け\t");
                break;
            case MARU:
                System.out.print(currPlayer.getName() + "'O' の勝ち\t");
                break;
            case BATSU:
                System.out.print(currPlayer.getName() + "'X' の勝ち\t");
                break;
        }
        System.out.println("またね！");
    }
}

