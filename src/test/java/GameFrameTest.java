import org.junit.Test;

import static org.junit.Assert.*;


public class GameFrameTest {

    private GameFrame currentGameFrame;

    @Test
    public void createTwoFrames() throws Exception {
        BowlingFrameGame bfg = new BowlingFrameGame("Tema");
        assertEquals(bfg.gameFrames.size(), 10);

        bfg.gameFrames.get(0).addRoll(5);
        bfg.gameFrames.get(0).addRoll(4);
        currentGameFrame = bfg.gameFrames.get(0);
        assertEquals(currentGameFrame.toString(), "ID: 0 and rolls: 5 4 ");
        //assertEquals(currentGameFrame.getScore(), 9);


        bfg.gameFrames.get(1).addRoll(3);
        bfg.gameFrames.get(1).addRoll(7);

        printFrames(bfg);

    }

    public void printFrames(BowlingFrameGame g) {
        for (GameFrame f : g.gameFrames) {
            System.out.println(f);
        }
    }

    @Test
    public void testGetRolls() throws Exception {

    }

    @Test
    public void testSetRoll() throws Exception {

    }
}