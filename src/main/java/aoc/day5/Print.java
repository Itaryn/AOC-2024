package aoc.day5;

import java.util.ArrayList;
import java.util.List;

public class Print {
    private List<Integer> pages = new ArrayList<>();

    public List<Integer> getPages() {
        return pages;
    }

    public Print setPages(List<Integer> pages) {
        this.pages = pages;
        return this;
    }
}
