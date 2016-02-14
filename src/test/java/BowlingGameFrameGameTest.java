import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;



public class BowlingGameFrameGameTest {
    public BowlingFrameGame bfg;

    public void printFrames(BowlingFrameGame g) {
        for (GameFrame f : g.gameFrames) {
            System.out.println(f);
        }
    }

    @Before
    public void setUp() throws Exception {
        bfg = new BowlingFrameGame("Tema");
    }

    @Test
    public void createBowlGame() throws Exception {
        BowlingFrameGame bfg2 = new BowlingFrameGame("Vadik");

        assertEquals("Tema", bfg.getPlayerName());
        assertEquals("Vadik", bfg2.getPlayerName());
    }

    @Test
    public void rollOne() throws Exception {
        bfg.rollIt(7);
        assertEquals(bfg.getFrame(0).getRolls().toString(), "[7]");
    }

    @Test
    public void rollTwo() throws Exception {
        bfg.rollIt(5);
        bfg.rollIt(4);
        assertEquals(bfg.getFrame(0).getRolls().toString(), "[5, 4]");

    }

    @Test
    public void rollFive() throws Exception {
        for (int i=0; i < 5; i++) {
            bfg.rollIt(i);
        }
        //printFrames(bfg);
        assertEquals(bfg.getFrame(2).getRolls().toString(), "[4]");
    }

    @Test
    public void roll11InOneFrame() throws Exception {
        bfg.rollIt(4);
        bfg.rollIt(10); // do not register
        //printFrames(bfg);
        assertEquals(bfg.getFrame(0).getRolls().toString(), "[4]");
    }

    @Test
    public void rollStrike() throws Exception {
        bfg.rollIt(10);
        bfg.rollIt(3);
        assertEquals(bfg.getFrame(0).getRolls().toString(), "[10]");
        assertEquals(bfg.getFrame(1).getRolls().toString(), "[3]");
    }

    @Test
    public void getTotalScore() throws Exception {
        bfg.rollIt(4);
        bfg.rollIt(3);
        bfg.rollIt(9);
        assertEquals(bfg.getFrame(0).getRolls().toString(), "[4, 3]");
        assertEquals(bfg.getFrame(1).getRolls().toString(), "[9]");
        assertEquals(bfg.getTotalScore(), 16);
    }

    @Test
    public void getTotalScoreWithSpare() throws Exception {
        bfg.rollIt(5);
        bfg.rollIt(5);
        bfg.rollIt(4);
        //printFrames(bfg);
        assertEquals(bfg.getTotalScore(), 18);
    }

    @Test
    public void getTotalScoreWithStrike() throws Exception {
        bfg.rollIt(10);
        bfg.rollIt(1);
        bfg.rollIt(4);
        bfg.rollIt(3);
        assertEquals(bfg.getTotalScore(), 23);
    }

    @Test
    public void rollAllFrames() throws Exception {
        for (int i=0; i<20; i++) {
            bfg.rollIt(2);
        }
        assertEquals(bfg.getFrame(9).getRolls().toString(), "[2, 2]");
    }

    @Test
    public void rollMoreThanCan() {
        for (int i=0; i<20; i++) {
            bfg.rollIt(2);
        }

        bfg.rollIt(5);
        bfg.rollIt(5);
        assertEquals(bfg.getFrame(9).getRolls().toString(), "[2, 2]");
        //printFrames(bfg);
    }


    @Test
    public void rollSpareLastFrameAndGetFreeRoll() {
        for (int i=0; i<18; i++) {
            bfg.rollIt(2);
        }

        bfg.rollIt(5);
        bfg.rollIt(5);
        bfg.rollIt(5);
        assertEquals(bfg.getFrame(9).getRolls().toString(), "[5, 5, 5]");
        //printFrames(bfg);
    }

    @Test
    public void getBlankScoreFirstFrame() {
        for (int i=0; i<20; i++) {
            bfg.rollIt(0);
        }
        assertEquals(bfg.getScoreTable().toString(), "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0]");
        assertEquals(bfg.getFrameScore(0), 0);
    }

    @Test
    public void getScoreTwoCommonFrames() {
        bfg.rollIt(4);
        bfg.rollIt(5);
        bfg.rollIt(3);
        assertEquals(bfg.getFrameScore(0), 9);
        assertEquals(bfg.getFrameScore(1), 12);

    }

    @Test
    public void getTotalScoreAllGameWithThreeRollsLastFrame() {
        for (int i=0; i<18; i++) {
            bfg.rollIt(0);
        }
        bfg.rollIt(6);
        bfg.rollIt(4);
        bfg.rollIt(8);
        //printFrames(bfg);
        //System.out.println(bfg.scoreTable.get(9).toString());
        assertEquals(bfg.scoreTable.get(9).toString(), "26");
    }

    @Test
    public void checkCalcScore() {
        bfg.rollIt(1);
        bfg.rollIt(4);
        assertEquals(bfg.scoreTable.get(0).toString(), "5");
    }

    @Test
    public void checkCalcScoreWithSpare() {
        bfg.rollIt(4);
        bfg.rollIt(6);
        //System.out.println(bfg.scoreTable.toString());
        bfg.rollIt(3);
        //System.out.println(bfg.scoreTable.toString());
        bfg.rollIt(4);
        //System.out.println(bfg.scoreTable.toString());
        assertEquals(bfg.scoreTable.get(0).toString(), "13");
        assertEquals(bfg.scoreTable.get(1).toString(), "20");
    }


    @Test
    public void check3StrikesInRow() {
        bfg.rollIt(10);
        System.out.println(bfg.scoreTable.toString());
        bfg.rollIt(10);
        System.out.println(bfg.scoreTable.toString());
        //TODO CREATE FRAMESCORETABLE TO BE [30, 20, 10, 0, 0, 0, 0...] AND TOTALSCORE TABLE TO BE [30, 50, 60, 0, 0, 0, 0, 0, 0]
        System.out.println("CREATE FRAMESCORETABLE TO BE [30, 20, 10, 0, 0, 0, 0...] AND TOTALSCORE TABLE TO BE [30, 50, 60, 0, 0, 0, 0, 0, 0]");
        bfg.rollIt(10);

        System.out.println(bfg.scoreTable.toString()); // [30, 50, 60]
    }

    @Ignore @Test
    public void testAllStrikes() {
        for (int i=0; i<10; i++) {
            bfg.rollIt(10);
        }
        System.out.println(bfg.scoreTable.toString());
    }

}

