import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class BowlingFrameGame {
    
    private final String playerName;
    private int currentFrameId = 0;
    private int numberOfRolls = 0;
    private int pinLeft = 10;
    private int haveRollsInLastFrame = 2;
    private boolean freeRollLastFrame = false;
    final static int LAST_FRAME_INDEX = 9;

    public ArrayList<Integer> scoreTable;
    public ArrayList<Integer> finalScoreTable;
    public ArrayList<GameFrame> gameFrames;
    public ArrayList<BonusCounterFrame> bonusCounterLists;
    public String getPlayerName() {
        return playerName;
    }
    public GameFrame getFrame(int i) {
        return gameFrames.get(i);
    }
    public String getFrameToStrings() {
        String result = "";
        for (GameFrame f: gameFrames) {
            result += f.toString();
        }
        return result;
    }

    public BowlingFrameGame(String playerName) {
        this.playerName = playerName;
        this.gameFrames = new ArrayList<GameFrame>();
        this.scoreTable = new ArrayList<Integer>(10);
        this.finalScoreTable = new ArrayList<Integer>(10);
        this.bonusCounterLists = new ArrayList<BonusCounterFrame>(3);
        for (int i = 0; i < LAST_FRAME_INDEX+1; i++) {
            gameFrames.add(new GameFrame(i));
            scoreTable.add(0);
            finalScoreTable.add(0);
        }

    }


    public void rollIt(int roll) {

        if (currentFrameId == LAST_FRAME_INDEX) {
            //System.out.println(currentFrameId +" " + LAST_FRAME_INDEX);
            rollLastframe(roll);
        } else {

            if (checkHavePins(roll)) {
                this.gameFrames.get(currentFrameId).addRoll(roll);
                pinLeft -= roll;

                calcScore(currentFrameId, roll);

                if (checkFrameFull()) {
                    currentFrameId++;
                    pinLeft = 10;
                }

            } else {
                //System.out.println("Give right number of pins.");
            }
        }
    }

    public void calcScore(int currentFrameId, int roll) {
        int temp;
        if (bonusCounterLists.size() > 0) {
            for (BonusCounterFrame bf: bonusCounterLists) {
                temp = scoreTable.get(bf.id);
                scoreTable.set(bf.id, temp+roll);
//                for (int ind=bf.id; ind<=currentFrameId; ind++) {
//                    temp = scoreTable.get(ind);
//                    scoreTable.set(ind, temp + roll);
//                }
                bf.counter--;
            }
            clearBonusCounterList();

        }
        if (currentFrameId > 0 && scoreTable.get(currentFrameId) == 0) {
            temp = scoreTable.get(currentFrameId-1);
            temp += scoreTable.get(currentFrameId);
        } else {
            temp = scoreTable.get(currentFrameId);
        }

        scoreTable.set(currentFrameId, temp+roll);

        if (gameFrames.get(currentFrameId).isStrike()) {
            BonusCounterFrame strikeBonusCounter = new BonusCounterFrame(currentFrameId, 2);
            bonusCounterLists.add(strikeBonusCounter);
        }

        if (gameFrames.get(currentFrameId).isSpare()) {
            BonusCounterFrame spearBonusCounter = new BonusCounterFrame(currentFrameId, 1);
            bonusCounterLists.add(spearBonusCounter);
        }

        updateFinalScoreTable();

    }

    private void updateFinalScoreTable() {

        for (int i=0; i<=currentFrameId; i++) {
            //System.out.println(scoreTable.toString());
            //System.out.println(i + " " + currentFrameId);
            if (i == 0){
                finalScoreTable.set(0, scoreTable.get(0));
            } else {
                int temp = scoreTable.get(i-1);
                finalScoreTable.set(i,temp+scoreTable.get(i));
            }

        }
    }

    private void clearBonusCounterList() {

        for (Iterator<BonusCounterFrame> iterator = bonusCounterLists.iterator(); iterator.hasNext();) {
            BonusCounterFrame bf = iterator.next();
            if (bf.counter == 0) {
                // Remove the current element from the iterator and the list.
                iterator.remove();
            }
        }

    }

    private void rollLastframe(int roll) {
        if (haveRollsInLastFrame > 0) {
            if (correctRoll(roll)) {
                this.gameFrames.get(LAST_FRAME_INDEX).addRoll(roll);
                haveRollsInLastFrame--;
            }
            if (!freeRollLastFrame) {
                if (this.gameFrames.get(LAST_FRAME_INDEX).isStrike() || this.gameFrames.get(LAST_FRAME_INDEX).isSpare()){
                    freeRollLastFrame = true;
                    haveRollsInLastFrame++;
                }
            }
            calcScore(currentFrameId, roll);
        } else {
            //System.out.println("Do not have more rolls!");
        }


    }

    private boolean correctRoll(int roll) {
        return (roll >= 0 && roll < 10);
    }

    private boolean checkHavePins(int roll) {
        return roll <= pinLeft;
    }

    private boolean checkFrameFull() {
        if (this.getFrame(currentFrameId).getRolls().size() == 2) {
            return true;
        } else if (this.getFrame(currentFrameId).getRolls().get(0) == 10) {
            return true;
        } else {
            return false;
        }
    }

    public int getTotalScore() {
        int score = 0;
        int doubleScorePins = 0;
        for (GameFrame gf : this.gameFrames){

            for (int pins: gf.getRolls()) {

                if (doubleScorePins > 0) {
                    score += pins*2;
                    doubleScorePins--;

                } else {
                    score += pins;
                    //System.out.println(score);
                }

            }

            if (gf.isSpare()) {
                doubleScorePins++;
            }
            if (gf.isStrike()) {
                doubleScorePins += 2;
            }
        }
        return score;
    }



    public ArrayList<Integer> getScoreTable() {

        return scoreTable;
    }
    public int getFrameScore(int frameIndex) {
        return scoreTable.get(frameIndex);
    }


}

class GameFrame {
    private int id;
    private ArrayList<Integer> rolls = new ArrayList<Integer>();
    private int score;
    private int totalScoreToThis;
    public int getId() {
        return this.id;
    }

    public GameFrame(int id) {
        this.id = id;
    }

    public String toString() {
        String strRolls = "";
        for (int roll: rolls){
            strRolls = strRolls + roll + " ";
        }

        return ("id:" + id + ", Rolls: " + strRolls);
    }

    public ArrayList<Integer> getRolls() {
        return rolls;
    }

    public void addRoll(int roll) {
        rolls.add(roll);
    }

    public void setRoll(int id, int roll) {
        rolls.set(id, roll);
    }


    public boolean isSpare() {
        if (getRolls().size() >= 2) {
            return (this.getRolls().get(0) + this.getRolls().get(1) == 10);
        } else return false;

    }

    public boolean isStrike() {
        if (getRolls().size() >= 1) {
            return (this.getRolls().get(0) == 10);
        } else return false;
    }
}

class BonusCounterFrame {
    public int id;
    public int counter;

    public BonusCounterFrame (int id, int counter) {
        this.id = id;
        this.counter = counter;
    }

    public String toString() {
        return (id + " " + counter);
    }
}