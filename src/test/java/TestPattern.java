import domain.Pattern;
import org.junit.Test;

import static domain.Key.COLOR;
import static domain.Key.POSITION;
import static domain.Pattern.pattern;
import static domain.Peg.*;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class TestPattern {

    @Test
    public void takesManyPegs() {
        Pattern pattern = new Pattern(BLUE, GREEN, RED, WHITE);
        assertEquals(4, pattern.getPegs().size());
    }

    @Test
    public void matchesPegPosition() {
        assertThat(pattern(BLUE, GREEN, RED, WHITE).match(pattern(BLUE, GREEN, RED, WHITE)),
                is(asList(POSITION, POSITION, POSITION, POSITION)));
    }

    @Test
    public void matchesTwoPositionsAndNoColors() {
        assertThat(pattern(BLUE, GREEN, RED, WHITE).match(pattern(BLUE, GREEN, YELLOW, YELLOW)),
                is(asList(POSITION, POSITION)));
    }

    @Test
    public void matchesPegColors() {
        assertThat(pattern(BLUE, GREEN, YELLOW, WHITE).match(pattern(GREEN, WHITE, BLUE, YELLOW)),
                is(asList(COLOR, COLOR, COLOR, COLOR)));
    }
}
