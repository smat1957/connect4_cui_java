package nmoku;

import static nmoku.Constants.*;

public class Board {

    private static Stone[] board = new Stone[WIDTH * WIDTH];
    private static Stone recentStone = null;

    public Board() {
        for (int i = 0; i < WIDTH * WIDTH; i++) {
            board[i] = new Stone(i, EMPTY);
        }
    }
    void setBoard(Stone s) {
        recentStone = s;
        board[s.getLocate()] = s;
    }
    void setEmpty(int xy){
        board[xy]=new Stone(xy, EMPTY);
    }
    boolean canPut(Stone s) {
        if (board[s.getLocate()].getColor() != EMPTY) {
            return false;
        }
        return true;
    }
    int getz(int x, int y) {
        return y * WIDTH + x;	// 0<= x <WIDTH, 0<= y <WIDTH
    }
    int check() {
        return boardScan(recentStone.getLocate() % WIDTH,
                         recentStone.getLocate() / WIDTH);
    }
    void print() {
        // 盤面の表示印刷
        final String[] str = {".", "●", "○"};
    
        System.out.printf("\n    ");
        for (int x = 0; x < WIDTH; x++) {
            if (x < 10) {
                System.out.printf("%2d", x);
            } else {
                System.out.printf("%2c", (char) ('a' - 10 + x));
            }
        }
        System.out.printf("\n");
        for (int y = 0; y < WIDTH; y++) {
            if (y < 10) {
                System.out.printf("%2d: ", y);
            } else {
                System.out.printf("%2c: ", (char) ('a' - 10 + y));
            }
            for (int x = 0; x < WIDTH; x++) {
                int cl = board[getz(x, y)].getColor();
                int num=0;
                if(cl==WHITE)num=2;
                if(cl==BLACK)num=1;
                System.out.printf("%2s", str[num]);
            }
            System.out.printf("\n");
        }
    }
    private int boardScan(int x, int y) {
        //盤面の調査（5個並んだかの調査）
        int[] n = new int[4];     //8方向（直線4本分）に並んだ数
        //[＼]方向
        n[0] = boardScanSub(x, y, 1, 1);
        //[│]方向
        n[1] = boardScanSub(x, y, 0, 1);
        //[─]方向
        n[2] = boardScanSub(x, y, 1, 0);
        //[／]方向
        n[3] = boardScanSub(x, y, -1, 1);
        for (int i = 0; i < 4; i++) {
            if (n[i] == NMOKU) {
                return recentStone.getColor();
            }
        }
        return NEXT;
    }
    private int boardScanSub(int x, int y, int move_x, int move_y) {
        // 盤面調査の下請け
        int n = 1;          //置いた場所の1個分で初期化
        int i;
        for (i = 1; i < NMOKU; i++) {
            int z = getz(x + (move_x * i), y + (move_y * i));
            if (!(0 <= z && z < WIDTH * WIDTH))continue;
            if (board[z].getColor() == recentStone.getColor()) {
                n += 1;
            } else {
                break;
            }
        }
        for (i = 1; i < NMOKU; i++) {
            int z = getz(x + (-1 * move_x * i), y + (-1 * move_y * i));
            if (!(0 <= z && z < WIDTH * WIDTH))continue;
            if (board[z].getColor() == recentStone.getColor()) {
                n += 1;
            } else {
                break;
            }
        }
        return n;
    }
    boolean isWin(){
        if(check()!=NEXT)return true;
        return false;
    }
    boolean isDraw(){
        return false;
    }
    float evaluate(Player player, int turn){
        if(isWin()){
            if(turn==player.getColor())return (float)(-1.0);
            else if(turn!=player.getColor())return (float)(1.0);
        }
        return (float)0.0;
    }
}

