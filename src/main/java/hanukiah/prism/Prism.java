package hanukiah.prism;

import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Prism {
    public static final int PRISM_SIZE = 3;
    @Getter
    private final List<Side> sides;

    public Prism(int[] corners) {
        sides = IntStream.range(0, PRISM_SIZE)
                .mapToObj(i-> new Side(corners[i], corners[(i + 1) % PRISM_SIZE]))
                .collect(Collectors.toUnmodifiableList())
        ;
    }

    public Prism(int corner0, int corner1, int corner2) {
        this(new int[]{corner0, corner1, corner2});
    }

    public class Side {
        private final int[] corners = new int[2];
        private Side(int corner0, int corner1) {
            this.corners[0] = corner0;
            this.corners[1] = corner1;
        }

        public int getCorners(int index) {
            return corners[index];
        }

        public boolean fits(Side other) {
            return this.corners[0] == other.corners[1] && this.corners[1] == other.corners[0];
        }

        public Prism getPrism() {return Prism.this;}
    }
}
