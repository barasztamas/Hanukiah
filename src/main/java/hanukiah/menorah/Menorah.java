package hanukiah.menorah;

import hanukiah.prism.Prism;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class Menorah {
    private final PrismDirection firstDirection;
    private List<Prism.Side> leftSides = new ArrayList<>(9);
    public boolean addRight(Prism.Side leftSide) {
        if (contains(leftSide.getPrism())) { return false; }
        leftSides.add(leftSide);
        return true;
    }
    public boolean contains(Prism prism) {
        return leftSides.stream().map(Prism.Side::getPrism).anyMatch(prism::equals);
    }
    public Prism removeRight() {
        return leftSides.remove(leftSides.size()-1).getPrism();
    }
    private Prism.Side rightSide(int i) {
        Prism.Side leftSide = leftSides.get(i);
        PrismDirection direction = PrismDirection.value(firstDirection.ordinal()+i);
        List<Prism.Side> sides = leftSide.getPrism().getSides();
        return sides.get(Math.floorMod (sides.indexOf(leftSide)+ direction.rightOffset, Prism.PRISM_SIZE));
    }
    public Prism.Side rightSide() {
        return rightSide(leftSides.size()-1);
    }

    public int size() {return leftSides.size();}
    
}
