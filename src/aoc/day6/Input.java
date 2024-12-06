package aoc.day6;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Input implements Cloneable {
    private List<List<Box>> map;
    private Guard guard;

    public Input(List<List<Box>> map, Guard guard) {
        this.map = map;
        this.guard = guard;
    }

    public List<List<Box>> getMap() {
        return map;
    }

    public Guard getGuard() {
        return guard;
    }

    public String mapToString() {
        return this.map.stream().map(line -> line.stream().map(Box::toString)
                        .collect(Collectors.joining(" ")))
                .collect(Collectors.joining("\n"));
    }

    @Override
    public Input clone() {
        try {
            Input clone = (Input) super.clone();
            clone.guard = guard.clone();
            List<List<Box>> cloneMap = new ArrayList<>();
            for (List<Box> innerList : map) {
                List<Box> innerClone = new ArrayList<>(innerList.stream().map(Box::clone).toList());
                cloneMap.add(innerClone);
            }
            clone.map = cloneMap;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
