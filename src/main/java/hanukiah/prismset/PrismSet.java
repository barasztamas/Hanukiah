package hanukiah.prismset;

import hanukiah.prism.Prism;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PrismSet {
    private Map<Prism.Side, Set<Prism.Side>> fittingSideDictionary = new HashMap<>();

    public Set<Prism.Side> fittingSides(Prism.Side side) {return fittingSideDictionary.get(side);}

    public void add(Prism prism) {
        prism.getSides().forEach(
                newSide -> {
                    //collect and amend fitting sides
                    Set<Prism.Side> fittingSides = fittingSideDictionary.entrySet()
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
                    fittingSideDictionary.put(newSide, fittingSides);
                }
        );
    }
}
