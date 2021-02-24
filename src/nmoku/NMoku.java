package nmoku;

public class NMoku {
    public static void main(String[] args) {
        boolean reverse = false;
        if (args.length > 0) {
            if (args[0].equals("-r")) {
                reverse = true;
            }
        }
        Game g = new Game(reverse);
        g.Start();
    }
}

