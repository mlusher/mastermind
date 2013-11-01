package domain;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Collections2.transform;
import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Lists.newArrayList;
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
        Tuple<List<Peg>, List<Peg>> splitTuple = splitPositionPegs(pattern.getPegs());
        List<Peg> positionMatches = splitTuple.getOne();
        List<Peg> inputWithoutPositionMatches = splitTuple.getTwo();
        List<Peg> thisPegList = removePegs(positionMatches);

        return newArrayList(
                concat(
                        extractPositionKeys(positionMatches),
                        extractColorKeys(thisPegList, inputWithoutPositionMatches)
                )
        );
    }

    private List<Peg> removePegs(List<Peg> pegsToRemove) {
        List<Peg> allPegs = new ArrayList<Peg>(pegs);
        for (Peg peg : pegsToRemove) {
            for (int x = 0; x < allPegs.size(); x++) {
                if (allPegs.get(x) == peg) {
                    allPegs.remove(x);
                    break;
                }
            }
        }
        return allPegs;
    }

    private List<Key> extractPositionKeys(List<Peg> positionPegs) {
        Function<Peg, Key> transformAllToPositionKey = new Function<Peg, Key>() {
            @Override
            public Key apply(Peg from) {
                return POSITION;
            }
        };
        return new ArrayList<Key>(transform(positionPegs, transformAllToPositionKey));
    }

    private Tuple<List<Peg>, List<Peg>> splitPositionPegs(final List<Peg> inputPegs) {
        ArrayList<Peg> positionPegs = new ArrayList<Peg>();
        ArrayList<Peg> filteredPegs = new ArrayList<Peg>();
        for (int x = 0; x < inputPegs.size(); x++) {
            if (pegs.get(x) == inputPegs.get(x))
                positionPegs.add(inputPegs.get(x));
            else
                filteredPegs.add(inputPegs.get(x));
        }
        return new Tuple<List<Peg>, List<Peg>>(positionPegs, filteredPegs);
    }

    private List<Key> extractColorKeys(final List<Peg> remainingPegs, List<Peg> inputPegs) {

        Predicate<Peg> pegExistsInPatternPegs = new Predicate<Peg>() {
            @Override
            public boolean apply(Peg input) {
                return remainingPegs.contains(input);
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


    public static Pattern pattern(Peg... pegs) {
        return new Pattern(pegs);
    }

    @Override
    public String toString() {
        return "Pattern{" +
                "pegs=" + pegs +
                '}';
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

