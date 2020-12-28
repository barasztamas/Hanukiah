package hanukiah.menorah;

import hanukiah.prism.Prism;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
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
        return this.size() == 0 ? null : rightSide(leftSides.size() - 1);
    }

    private PrismDirection direction(int i) {
        return PrismDirection.value(firstDirection.ordinal() + i);
    }

    public int size() {
        return leftSides.size();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Menorah)) {
            return false;
        }

        BiFunction<Prism.Side, Prism.Side, Boolean> areEqual = (side1, side2) ->
                side1.getCorners(0) == side2.getCorners(0)
                        && side1.getCorners(1) == side2.getCorners(1);
        BiFunction<Prism.Side, Prism.Side, Boolean> areMirrored = (side1, side2) ->
                side1.getCorners(0) == side2.getCorners(1)
                        && side1.getCorners(1) == side2.getCorners(0);
        Menorah other = (Menorah) obj;

        if (other.size() != this.size()) {
            return false;
        }
        return other.toString().equals(this.toString())
                || (
                Math.abs(other.firstDirection.ordinal() - this.firstDirection.ordinal()) == size() % 2
                        && new StringBuilder(other.prismsToString()).reverse().toString().equals(this.prismsToString())); //works for cyclycally symmetric menorahs, if Corner size in [0,9]
    }

    @Override
    public int hashCode() {
        return this.size();
    }

    @Override
    public String toString() {
        return firstDirection.name() + "\t" + prismsToString();
    }

    private String prismsToString() {
        return IntStream.range(0, size()).mapToObj(this::prismToString).collect(Collectors.joining("\t"));
    }

    private String prismToString(int i) {
        if (direction(i) == PrismDirection.BOTTOM) {
            return leftSides.get(i).getCorners(0) + "," + leftSides.get(i).getCorners(1) + "," + rightSide(i).getCorners(1);
        } else if (direction(i) == PrismDirection.TOP) {
            return leftSides.get(i).getCorners(1) + "," + leftSides.get(i).getCorners(0) + "," + rightSide(i).getCorners(0);
        } else {
            throw new IllegalStateException();
        }
    }

    public Menorah copy() {
        Menorah m = new Menorah(firstDirection);
        m.leftSides = new ArrayList<>(leftSides);
        return m;
    }
}
