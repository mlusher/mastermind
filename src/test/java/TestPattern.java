import domain.Pattern;
import org.junit.Test;

import static domain.Key.COLOR;
import static domain.Key.POSITION;
import static domain.Pattern.pattern;
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
        assertEquals(asList(POSITION, POSITION, POSITION, POSITION), pattern(BLUE, GREEN, RED, WHITE).match(pattern(BLUE, GREEN, RED, WHITE)));
    }

    @Test
    public void matchesTwoPositionsAndNoColors() {
        assertEquals(asList(POSITION, POSITION), pattern(BLUE, GREEN, RED, WHITE).match(pattern(BLUE, GREEN, YELLOW, YELLOW)));
    }

    @Test
    public void matchesPegColors() {
        assertEquals(asList(COLOR, COLOR, COLOR, COLOR), pattern(BLUE, GREEN, YELLOW, WHITE).match(pattern(GREEN, WHITE, BLUE, YELLOW)));
    }

    @Test
    public void doesntMatchWithSameColorWhenOnlyOneIsRight() {
        assertEquals(
                asList(POSITION),
                pattern(BLUE, BLUE, WHITE, RED).match(pattern(BLUE, GREEN, ORANGE, YELLOW))
        );
    }

    @Test
    public void matchesTwoSameColorInputsAsBothPositionAndColor() {
        assertEquals(
                asList(POSITION, COLOR),
                pattern(BLUE, BLUE, WHITE, RED).match(pattern(BLUE, GREEN, BLUE, YELLOW))
        );
    }

    @Test
    public void withTwoSameColorsOnlyMatchesOneColor() {
        assertEquals(
                asList(POSITION),
                pattern(BLUE, RED, WHITE).match(pattern(BLUE, GREEN, BLUE))
        );
    }

    @Test
    public void shouldTakeAnyNumberOfPegs() {
        assertEquals(
                asList(POSITION, POSITION, COLOR),
                pattern(BLUE, WHITE, RED, YELLOW, ORANGE).match(pattern(BLUE, WHITE, YELLOW, BLUE, GREEN))
        );
    }

}
