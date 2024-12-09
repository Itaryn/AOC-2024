package aoc.day2;

import java.util.List;

public class Report {
    private List<Integer> reportValues;
    private boolean safe;

    public List<Integer> getReportValues() {
        return reportValues;
    }

    public Report setReportValues(List<Integer> reportValues) {
        this.reportValues = reportValues;
        return this;
    }

    public boolean isSafe() {
        return safe;
    }

    public Report setSafe(boolean safe) {
        this.safe = safe;
        return this;
    }
}
