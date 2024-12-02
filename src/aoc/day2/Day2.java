package aoc.day2;

import aoc.DailyExercise;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day2 implements DailyExercise {

    public static final int DIFF_MIN = 1;
    public static final int DIFF_MAX = 3;
    public static final int ACCEPTABLE_BAD_VALUES = 1;

    @Override
    public String getFirstAnswer() {
        List<Report> reports = readInput();
        setReportSafe(reports, false);
        long numberOfSaveReport = reports.stream().filter(Report::isSafe).count();
        return Long.toString(numberOfSaveReport);
    }

    @Override
    public String getSecondAnswer() {
        List<Report> reports = readInput();
        setReportSafe(reports, true);
        long numberOfSaveReport = reports.stream().filter(Report::isSafe).count();
        return Long.toString(numberOfSaveReport);
    }

    private List<Report> readInput() {
        List<Report> reports = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("./src/aoc/day2/input.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                reports.add(new Report().setReportValues(Arrays.stream(line.split(" ")).map(Integer::parseInt).toList()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return reports;
    }

    private void setReportSafe(List<Report> reports, boolean allowBadValues) {
        reports.forEach(
                report -> report.setSafe(isReportSafe(report.getReportValues(), allowBadValues ? ACCEPTABLE_BAD_VALUES : 0))
        );
    }

    private boolean isReportSafe(List<Integer> reportValues, int acceptableBadValues) {
        boolean asc = reportValues.get(0) < reportValues.get(1);
        for (int i = 0; i < reportValues.size() - 1; i++) {
            int diff = Math.abs(reportValues.get(i) - reportValues.get(i + 1));
            if (diff < DIFF_MIN || diff > DIFF_MAX || !hasSameOrder(reportValues.get(i), reportValues.get(i + 1), asc)) {
                if (acceptableBadValues == 0) {
                    return false;
                }
                List<Integer> subList1 = reportValues.subList(1, reportValues.size()); // Allow possible bad ordering calculation
                List<Integer> subList2 = new ArrayList<>(reportValues.subList(0, i));
                subList2.addAll(reportValues.subList(i + 1, reportValues.size()));
                List<Integer> subList3 = new ArrayList<>(reportValues.subList(0, i + 1));
                subList3.addAll(reportValues.subList(i + 2, reportValues.size()));
                return isReportSafe(subList1, acceptableBadValues - 1) ||
                        isReportSafe(subList2, acceptableBadValues - 1) ||
                        isReportSafe(subList3, acceptableBadValues - 1);
            }
        }
        return true;
    }

    private boolean hasSameOrder(Integer v1, Integer v2, boolean asc) {
        return (v1 < v2 && asc) || (v1 > v2 && !asc);
    }
}
