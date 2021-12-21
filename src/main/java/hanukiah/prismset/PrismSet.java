package hanukiah.prismset;

import hanukiah.prism.Prism;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PrismSet {
    private final Map<Prism.Side, Set<Prism.Side>> fittingSideMap = new HashMap<>();
        //this is stored for performance, as will be queried many times
        //once this is stored, set of prisms doesn't need to be stored
    
    public Set<Prism.Side> fittingSides(Prism.Side side) {
        return side == null ? fittingSideMap.keySet() : fittingSideMap.get(side);
    }

    public void add(Prism prism) {
        prism.getSides().forEach(
                newSide -> {
                    //collect fitting sides,
                    Set<Prism.Side> fittingSides = fittingSideMap.keySet()
                            .stream()
                            .filter(side-> prism!=side.getPrism())
                            .filter(newSide::fits)
                            // add new side to fitting side's entry
                            // this is a side effect, therefore prism.getSides() stream can't be parallel
                            .peek(side -> fittingSideMap.get(side).add(newSide))
                            .collect(Collectors.toSet())
                    ;
                    //add new side to dictionary
                    fittingSideMap.put(newSide, fittingSides);
                }
        );
    }
}
