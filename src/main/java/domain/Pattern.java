package domain;

import java.util.ArrayList;
import java.util.List;

import static domain.Key.COLOR;
import static domain.Key.POSITION;
import static java.util.Arrays.asList;

public class Pattern {
    private List pegs;

    public Pattern(Peg... pegs) {
        this.pegs = asList(pegs);
    }

    public List<Peg> getPegs() {
        return new ArrayList<Peg>(pegs);
    }

    public List<Key> match(Pattern pattern) {
        ArrayList<Key> positionKeys = new ArrayList<Key>();

        List<Peg> pegsToRemove = new ArrayList<Peg>();
        List<Peg> inputPegs = pattern.getPegs();
        for (int x = 0; x < inputPegs.size(); x++) {
            if (inputPegs.get(x) == pegs.get(x)){
                positionKeys.add(POSITION);
                pegsToRemove.add(inputPegs.get(x));
            }
        }

        List<Peg> pegs1 = pattern.getPegs();
        pegs1.removeAll(pegsToRemove);

        ArrayList<Key> colorKeys = new ArrayList<Key>();
        for (Peg peg : pegs1) {
            if (pegs.contains(peg))
                colorKeys.add(COLOR);
        }
        List<Key> colors = colorKeys;

        positionKeys.addAll(colors);
        return positionKeys;
    }

    public static Pattern p(Peg... pegs) {
        return new Pattern(pegs);
    }
}
