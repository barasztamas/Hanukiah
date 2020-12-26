package hanukiah.menorah;

import hanukiah.prism.Prism;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        List<Prism.Side> sides = leftSide.getPrism().getSides();
        return sides.get(Math.floorMod (sides.indexOf(leftSide)+ direction(i).rightOffset, Prism.PRISM_SIZE));
    }
    public Prism.Side rightSide() {
        return rightSide(leftSides.size()-1);
    }

    private PrismDirection direction(int i) {
        return PrismDirection.value(firstDirection.ordinal()+i);
    }

    public int size() {return leftSides.size();}

    @Override
    public String toString() {
        return firstDirection.name()+"\t"+ IntStream.range(0,size()).mapToObj(this::prismToString).collect(Collectors.joining("\t"));
    }

    private String prismToString(int i) {
        if (direction(i)==PrismDirection.BOTTOM) {
            return leftSides.get(i).getCorners(0)+","+leftSides.get(i).getCorners(1)+","+rightSide(i).getCorners(1);
        } else if (direction(i)==PrismDirection.TOP) {
            return leftSides.get(i).getCorners(1)+","+leftSides.get(i).getCorners(0)+","+rightSide(i).getCorners(0);
        } else {
            throw new IllegalStateException();
        }
    }
}
