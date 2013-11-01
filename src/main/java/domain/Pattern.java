package domain;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Collections2.transform;
import static domain.Key.COLOR;
import static domain.Key.POSITION;
import static java.util.Arrays.asList;

public class Pattern {
    private List<Peg> pegs;

    public Pattern(Peg... pegs) {
        this.pegs = asList(pegs);
    }

    public List<Peg> getPegs() {
        return new ArrayList<Peg>(pegs);
    }

    public List<Key> match(Pattern pattern) {
        List<Peg> inputPegs = pattern.getPegs();


        Tuple<List<Key>, List<Peg>> positionMatches = extractPositionMatches(inputPegs);
        inputPegs.removeAll(positionMatches.getTwo());
        ArrayList<Key> colorKeys = extractColorKeys(inputPegs);


        ArrayList<Key> matchAnswer = new ArrayList<Key>();
        matchAnswer.addAll(positionMatches.getOne());
        matchAnswer.addAll(colorKeys);
        return matchAnswer;
    }

    private ArrayList<Key> extractColorKeys(List<Peg> inputPegs) {

        Predicate<Peg> pegExistsInPatternPegs = new Predicate<Peg>() {
            @Override
            public boolean apply(Peg input) {
                return pegs.contains(input);
            }
        };
        Collection<Peg> matchingPegs = filter(inputPegs, pegExistsInPatternPegs);

        Function<Peg, Key> colorKeyForEveryInputPeg = new Function<Peg, Key>() {
            @Override
            public Key apply(Peg peg) {
                return COLOR;
            }
        };
        Collection<Key> colorKeys = transform(matchingPegs, colorKeyForEveryInputPeg);

        return new ArrayList<Key>(colorKeys);
    }

    private Tuple<List<Key>, List<Peg>> extractPositionMatches(List<Peg> inputPegs) {


        List<Key> positionMatches = new ArrayList<Key>();
        List<Peg> pegMatches = new ArrayList<Peg>();

        for (int x = 0; x < inputPegs.size(); x++) {
            if (inputPegs.get(x) == pegs.get(x)) {
                positionMatches.add(POSITION);
                pegMatches.add(inputPegs.get(x));
            }
        }

        return new Tuple<List<Key>, List<Peg>>(positionMatches, pegMatches);
    }


    public static Pattern pattern(Peg... pegs) {
        return new Pattern(pegs);
    }

}

class Tuple<One, Two> {
    private final One one;
    private final Two two;

    Tuple(One one, Two two) {
        this.one = one;
        this.two = two;
    }

    One getOne() {
        return one;
    }

    Two getTwo() {
        return two;
    }
}
