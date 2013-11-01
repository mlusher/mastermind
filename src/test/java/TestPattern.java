import domain.Pattern;
import org.junit.Test;

import static domain.Key.COLOR;
import static domain.Key.POSITION;
import static domain.Pattern.p;
import static domain.Peg.*;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class TestPattern {

    @Test
    public void takesManyPegs() {
        Pattern pattern = new Pattern(BLUE, GREEN, RED, WHITE);
        assertEquals(4, pattern.getPegs().size());
    }

    @Test
    public void matchesPegPosition() {
        Pattern pattern = new Pattern(BLUE, GREEN, RED, WHITE);
        assertEquals(
                asList(POSITION, POSITION, POSITION, POSITION),
                pattern.match(pattern)
        );
    }

    @Test
    public void matchesTwoPositionsAndNoColors() {
        Pattern pattern = new Pattern(BLUE, GREEN, RED, WHITE);
        assertEquals(
                asList(POSITION, POSITION),
                pattern.match(p(BLUE, GREEN, YELLOW, YELLOW))
        );
    }

    @Test
    public void matchesPegColors() {
        Pattern pattern = new Pattern(BLUE, GREEN, YELLOW, WHITE);
        assertEquals(
                asList(COLOR, COLOR, COLOR, COLOR),
                pattern.match(p(GREEN, WHITE, BLUE, YELLOW))
        );
    }
}
