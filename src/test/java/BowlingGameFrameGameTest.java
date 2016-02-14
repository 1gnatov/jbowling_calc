import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
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

    @Test @Ignore
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
        assertEquals(bfg.finalScoreTable.get(1).toString(), "12");

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
        assertEquals(bfg.scoreTable.get(9).toString(), "18");
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
        assertEquals(bfg.finalScoreTable.get(0).toString(), "13");
        assertEquals(bfg.finalScoreTable.get(1).toString(), "20");

    }


    @Test
    public void check3StrikesInRow() {
        bfg.rollIt(10);
        //System.out.println(bfg.scoreTable.toString());
        bfg.rollIt(10);
        //System.out.println(bfg.scoreTable.toString());
        bfg.rollIt(10);

        //System.out.println(bfg.scoreTable.toString()); // [30, 50, 60]
       // System.out.println(bfg.finalScoreTable.toString());
        assertEquals(bfg.finalScoreTable.toString(), "[30, 50, 60, 0, 0, 0, 0, 0, 0, 0]");
    }

    @Test
    public void testAllStrikes() {
        for (int i=0; i<12; i++) {
            bfg.rollIt(10);
//            System.out.println(bfg.gameFrames.toString());
//            System.out.println(bfg.scoreTable.toString());
        }
        //System.out.println(bfg.finalScoreTable.toString());
        assertEquals(bfg.finalScoreTable.toString(), "[30, 60, 90, 120, 150, 180, 210, 240, 270, 300]");
    }

    @Test
    public void testAllSpears() {
        for (int i=0; i<23; i++) {
            bfg.rollIt(5);
        }
//        System.out.println(bfg.scoreTable.toString());
//        System.out.println(bfg.finalScoreTable.toString());
//        System.out.println(bfg.gameFrames.toString());
        assertEquals(bfg.finalScoreTable.toString(), "[15, 30, 45, 60, 75, 90, 105, 120, 135, 150]");
    }

    @Test
    public void realGameChampTest1() {
        int[] array = new int[]{10, 10, 8, 1, 10, 8, 2, 7, 2, 9, 1, 9, 1, 7, 3, 10, 9, 1};
        for (int i: array) {
            bfg.rollIt(i);
        }
        assertEquals(bfg.finalScoreTable.toString(), "[28, 47, 56, 76, 93, 102, 121, 138, 158, 178]");
    }

    @Test
    public void realGameChampTest2() {
        int[] array = new int[]{9, 1, 10, 9, 1, 10, 7, 3, 10, 8, 1, 9, 1, 9, 1, 5, 3};
        for (int i: array) {
            bfg.rollIt(i);
        }
        //System.out.println(bfg.finalScoreTable.toString());
        assertEquals(bfg.finalScoreTable.toString(), "[20, 40, 60, 80, 100, 119, 128, 147, 162, 170]");
    }

    @Test
    public void realGameChampTest3() {
        int[] array = new int[]{10, 9, 1, 10, 10, 9, 1, 9, 1, 7, 2, 9, 1, 9, 1, 10, 10, 7};
        for (int i: array) {
            bfg.rollIt(i);
        }
        //System.out.println(bfg.finalScoreTable.toString());
        assertEquals(bfg.finalScoreTable.toString(), "[20, 40, 69, 89, 108, 125, 134, 153, 173, 200]");
    }

    @Test
    public void realGameChampTest4() {
        int[] array = new int[]{9, 0, 8, 2, 10, 9, 1, 8, 2, 9, 1, 9, 0, 9, 1, 9, 1, 10, 10, 10};
        for (int i: array) {
            bfg.rollIt(i);
        }
        //System.out.println(bfg.finalScoreTable.toString());
        //assertEquals(bfg.finalScoreTable.toString(), "[20, 40, 69, 89, 108, 125, 134, 153, 173, 200]");
    }

    @Test
    public void testUnusedMethods() {
        bfg.rollIt(10);
        bfg.rollIt(1);
        assertEquals(bfg.bonusCounterLists.get(0).toString(), "0 1");
        assertEquals(bfg.getFrame(1).toString(), "id:1, Rolls: 1 " );




    }

}

