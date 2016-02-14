
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.util.ArrayList;

public class ConsoleIOBowlingFrameGame {
    public static void main (String[] args) throws IOException {

        System.out.println("Select type: (1) Frame-by-frame calc (2) Whole Game calc");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        boolean ready = false;
        while (!ready) {
            System.out.print("Enter Integer:\n");
            try{
                int i = Integer.parseInt(br.readLine());
                if (i == 1) {
                    ready = true;
                    frameByFrame();
                } else if (i == 2) {
                    ready = true;
                    wholeGame();
                }
            }catch(NumberFormatException nfe){
                System.err.println("Invalid Format!");
            }



        }
    }


    public static void frameByFrame() throws IOException {
        System.out.println("You choose 1!");
        System.out.println("Write number of players:");
        int numberOfPlayers = 0;
        BufferedReader brFrameByFrame = new BufferedReader(new InputStreamReader(System.in));
        boolean ready = false;
        while (!ready) {

            try{
                int i = Integer.parseInt(brFrameByFrame.readLine());
                if (i>0) {
                    numberOfPlayers = i;
                    ready = true;
                }

            }catch(NumberFormatException nfe){
                System.err.println("Invalid Format!\n");
            }
        }
        ArrayList<BowlingFrameGame> ListOfBowlingFrameGames = new ArrayList<BowlingFrameGame>(numberOfPlayers);
        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.println("Write player name:");
            BufferedReader brPlayerName = new BufferedReader(new InputStreamReader(System.in));
            ListOfBowlingFrameGames.add(new BowlingFrameGame(brPlayerName.readLine()));
        }

        for (int globalFrameIndex = 0; globalFrameIndex < 11; globalFrameIndex++)
            for (BowlingFrameGame game: ListOfBowlingFrameGames) {
                while (game.currentFrameId == globalFrameIndex) {
                    System.out.println("Current player: " + game.getPlayerName());
                    System.out.println("Roll: ");
                    BufferedReader brRoll = new BufferedReader(new InputStreamReader(System.in));
                    try {
                        int r = Integer.parseInt(brRoll.readLine());
                        if (r >= 0 && r <= 10) {
                            game.rollIt(r);
                        }
                    } catch (NumberFormatException nfe) {
                        System.err.println("Invalid Format!\n");
                    }
                    System.out.println(game.getPlayerName() + " " + game.finalScoreTable.toString());

                    //System.out.println(game.haveRollsInLastFrame + " " + game.getPlayerName());
                    if (game.haveRollsInLastFrame == 0) {
                        break;
                    }

                }


        }

    }

    private static void wholeGame() {
        System.out.println("You choose 2!");
        BufferedReader brRollsString = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Write string with pins, separated with spaces:");
        BowlingFrameGame whGame = new BowlingFrameGame("WholeGameCalc");
        try {
            String[] strArr = brRollsString.readLine().split(" ");
            for (String strPin : strArr) {
                int pin = Integer.parseInt(strPin);
                whGame.rollIt(pin);
            }
        } catch (IOException e) {
            System.out.println("Invalid pins list!");
        }
        System.out.println(whGame.finalScoreTable.toString());
    }

}
