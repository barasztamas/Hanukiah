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

    public void addRight(Prism.Side leftSide) {
        if (contains(leftSide.getPrism())) {
            return;
        }
        leftSides.add(leftSide);
    }

    public boolean contains(Prism prism) {
        return leftSides.stream().map(Prism.Side::getPrism).anyMatch(prism::equals);
    }

    public void removeRight() {
        leftSides.remove(size() - 1).getPrism();
    }

    private Prism.Side rightSide(int i) {
        Prism.Side leftSide = leftSides.get(i);
        List<Prism.Side> sides = leftSide.getPrism().getSides();
        return sides.get(Math.floorMod(sides.indexOf(leftSide) + direction(i).rightOffset, Prism.PRISM_SIZE));
    }

    public Prism.Side rightSide() {
        return size() == 0 ? null : rightSide(size() - 1);
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
        Menorah other = (Menorah) obj;

        if (other.size() != this.size()) {
            return false;
        }
        return other.toString().equals(this.toString())
                || (
                Math.abs(other.firstDirection.ordinal() - this.firstDirection.ordinal()) == size() % 2
                        && new StringBuilder(other.prismsToString()).reverse().toString().equals(this.prismsToString())); //works for cyclically symmetric menorahs
    }

    @Override
    public int hashCode() {
        return size();
    }

    @Override
    public String toString() {
        return firstDirection.name() + "\t" + prismsToString();
    }

    private String prismsToString() {
        return IntStream.range(0, size()).mapToObj(this::prismToString).collect(Collectors.joining("\t"));
    }

    private String prismToString(int i) {
        if (direction(i) == PrismDirection.DOWN) {
            return leftSides.get(i).getCorners(0) + "," + leftSides.get(i).getCorners(1) + "," + rightSide(i).getCorners(1);
        } else if (direction(i) == PrismDirection.UP) {
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
