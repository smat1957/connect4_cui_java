package nmoku;

import static nmoku.Constants.*;

public class Player extends Strategy {

      private String Name = null;
      private int Senryaku = 0;
      private int Color = EMPTY;

      public Player(int i, String n, int s) {
          Color = i;
          Name = n;
          Senryaku = s;
      }
      void putStone(Board board) {
          Stone s = null;
          do {
              int te = Te(board);
              s = new Stone(te, Color);
              if (board.canPut(s)) {
                  break;
              }
          } while (true);
          board.setBoard(s);
      }
      private int Te(Board board) {
          int v = 0;
          switch (Senryaku) {
              case PROMPT:
                  v = prompt(Name);
                  break;
              case RANDOM:
                  v = random(Name);
                  break;
              case MINIMAX:
                  v = bestMoveMM(board, this);
                  break;
              case ALPHABETA:
                  v = bestMoveAB(board, this);
                  break;
              case MONTECARLO:
                  break;
          }
          return v;
      }
      int getColor(){
          return Color;
      }
      String getName(){
          return Name;
      }
}

