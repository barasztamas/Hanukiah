package hanukiah.prismset;

import hanukiah.prism.Prism;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PrismSet {
    private Map<Prism.Side, Set<Prism.Side>> fittingSideMap = new HashMap<>();

    public Set<Prism.Side> fittingSides(Prism.Side side) {return fittingSideMap.get(side);}

    public Set<Prism.Side> getAllSides() {
        return fittingSideMap.keySet();
    }

    public void add(Prism prism) {
        prism.getSides().forEach(
                newSide -> {
                    //collect and amend fitting sides
                    Set<Prism.Side> fittingSides = fittingSideMap.entrySet()
                            .stream()
                            .filter(entry-> prism!=entry.getKey().getPrism())
                            .filter(entry-> newSide.fits(entry.getKey()))
                            .map(entry -> {
                                entry.getValue().add(newSide); // side effect
                                return entry.getKey();
                            })
                            .collect(Collectors.toSet())
                    ;
                    //add new side to dictionary
                    fittingSideMap.put(newSide, fittingSides);
                }
        );
    }
}
