package nmoku;

import static nmoku.Constants.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Strategy {

    private boolean numP(char x) {
        if (('0' <= x) && (x <= '9')) {
            return true;
        }
        return false;
    }
    private int fromA(char x) {
        return (int) (x - 'a');
    }
    int prompt(String name) {
        // 標準入力から
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int num = -1;
        String fmt = null, instr = null;
        do {
            fmt = String.format("\n石を置く場所 xy を指定（ %s の番です ）：", name);
            System.out.print(fmt);
            try {
                instr = br.readLine();
                char cx = instr.trim().charAt(0);
                int nx;
                if (numP(cx)) {
                    nx = Integer.parseInt(String.valueOf(cx));
                } else {
                    nx = fromA(cx) + 10;
                }
                char cy = instr.trim().charAt(1);
                int ny;
                if (numP(cy)) {
                    ny = Integer.parseInt(String.valueOf(cy));
                } else {
                    ny = fromA(cy) + 10;
                }
                num = nx * WIDTH + ny;
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!(0 <= num && num < WIDTH * WIDTH)) {
                System.out.println("やり直し！ 場所を xy で指定して ）");
                continue;
            }
            break;
        } while (true);
        return num;
    }
    int random(String name) {
        System.out.printf("。。。　%s 思考中　。。。", name);
        return (int) (Math.random() * (WIDTH * WIDTH));
    }
    private float minimax(Board board, int depth, int turn, Player player) {
        // 三目並べと同じ
        return (float)0.0;
    }
    int bestMoveMM(Board board, Player player) {
        // 三目並べと同じ
        return 0;
    }
    private float alphabeta(Board board, int depth, int turn, Player player, float alpha, float beta) {

        if (board.isWin() || board.isDraw() || depth == 0) {
            float score = board.evaluate(player, turn);
            //System.out.println("evaluated score="+score+", depth="+depth);
            return score;
        }

        for (int i = 0; i < WIDTH * WIDTH; i++) {
            Stone s = new Stone(i, turn);
            if (board.canPut(s)) {

                //board.print();
                //if(turn==MARU)System.out.println("Turn=O");
                //else System.out.println("Turn=X");

                board.setBoard(s);
                float score = alphabeta(board, depth - 1, -turn, player, alpha, beta);
                board.setEmpty(i);
                //System.out.println("k1="+i+", score="+score);

                if (turn == MAX) {
                    alpha = Float.max(score, alpha);
                    if( beta <= alpha )break;
                } else {
                    beta = Float.min(score, beta);
                    if( beta <= alpha )break;
                }
            }
        }
        if(turn==MAX)return alpha;
        return beta;
    }
    int bestMoveAB(Board board, Player player) {
        float bestEval = (float) Integer.MIN_VALUE;
        float alpha = (float) Integer.MIN_VALUE;
        float beta = (float) Integer.MAX_VALUE;
        int bestMove = -1;
        for (int i = 0; i < WIDTH * WIDTH; i++) {
            Stone s = new Stone(i, player.getColor());
            if (board.canPut(s)) {
                //board.print();
                board.setBoard(s);  //BLACKに打たせるときは、-player.getColor()の負号をとり、
                float eval = alphabeta(board, 6, -player.getColor(), player, alpha, beta);
                board.setEmpty(i);  //Board.evaluate で -1 と 1 を入れ替える
                //System.out.println("k="+i+", eval="+eval);
                if (eval > bestEval) {
                    bestEval = eval;
                    bestMove = i;
                }
            }
            //System.out.print(i+"\t");
        }
        return bestMove;
    }
}

