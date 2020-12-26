package hanukiah;

import hanukiah.menorah.Menorah;
import hanukiah.menorah.PrismDirection;
import hanukiah.prism.Prism;
import hanukiah.prismset.PrismSet;

import java.util.Arrays;

public class Main {
    private final PrismSet prismSet = new PrismSet();
    private final Prism firstPrism = new Prism(2,2,2);
    private Main() {
        setPrismSet();
//        buildMenorah(PrismDirection.BOTTOM);
        Arrays.stream(PrismDirection.values())
                .forEach(this::buildMenorah);
    }

    private void buildMenorah(PrismDirection direction) {
        Menorah menorah = new Menorah(direction);
        menorah.addRight(firstPrism.getSides().get(0));
        tryBuild(menorah);
    }
    private void tryBuild(Menorah menorah) {
        System.out.println(menorah.size());
        if (menorah.size() >= 8) {
            System.out.println(menorah.toString());
        }
        prismSet.fittingSides(menorah.rightSide()).forEach(
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
        prismSet.add(firstPrism);
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
