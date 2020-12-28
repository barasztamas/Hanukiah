package hanukiah;

import hanukiah.menorah.Menorah;
import hanukiah.menorah.PrismDirection;
import hanukiah.prism.Prism;
import hanukiah.prismset.PrismSet;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main {
    private final PrismSet prismSet = new PrismSet();
    //private final Prism firstPrism = new Prism(2,2,2);
    private Set<Menorah> menorahs = new HashSet<>();


    private Main() {
        setPrismSet();
        Arrays.stream(PrismDirection.values())
                .forEach(this::buildMenorah);
        menorahs.forEach(System.out::println);
    }

    private void buildMenorah(PrismDirection direction) {
        Menorah menorah = new Menorah(direction);
        tryBuild(menorah);
    }
    private void tryBuild(Menorah menorah) {
        //System.out.println(menorah.size());
        if (menorah.size() >= 8 & !menorahs.contains(menorah)) {
            menorahs.add(menorah.copy());
        }
        prismSet.fittingSides(menorah.rightSide())
                .forEach(
                        newSide -> {
                            if (!menorah.contains(newSide.getPrism())) {
                                menorah.addRight(newSide);
                                tryBuild(menorah);
                                menorah.removeRight();
                            }
                        }
        );
    }

    private void setPrismSet() {
        prismSet.add(new Prism(2,2,2));
        prismSet.add(new Prism(1,1,2));
        prismSet.add(new Prism(2,2,1));
        prismSet.add(new Prism(1,1,3));
        prismSet.add(new Prism(3,3,1));
        prismSet.add(new Prism(1,2,3));
        prismSet.add(new Prism(3,2,1));
        prismSet.add(new Prism(1,2,3));
        prismSet.add(new Prism(3,2,1));
    }

    public static void main(String[] args) {
        new Main();
    }
}
