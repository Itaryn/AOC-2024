package aoc.day17;

import aoc.DailyExercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.joining;

public class Day17 implements DailyExercise {
    private final static String REGEX_REGISTER = "^Register [ABC]: (\\d*)$";
    private final static String REGEX_PROGRAM = "^Program: ([\\d,]*)$";

    protected Input getInput(String path) {
        try (InputStream inputStream = this.getClass().getResourceAsStream(path);
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String line;
            List<Long> register = new ArrayList<>();
            List<Integer> program = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                Matcher registerMatcher = Pattern.compile(REGEX_REGISTER).matcher(line);
                Matcher programMatcher = Pattern.compile(REGEX_PROGRAM).matcher(line);
                if (registerMatcher.find()) {
                    register.add(Long.parseLong(registerMatcher.group(1)));
                } else if (programMatcher.find()) {
                    program.addAll(Arrays.stream(programMatcher.group(1).split(",")).map(Integer::valueOf).toList());
                }
            }
            return new Input(register, program);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected long getCombo(List<Long> register, int operand) {
        return switch (operand) {
            case 0, 1, 2, 3 -> operand;
            case 4, 5, 6 -> register.get(operand - 4);
            default -> throw new IllegalStateException("Unexpected value: " + operand);
        };
    }

    protected void xdv(List<Long> register, int index, int operand) {
        register.set(index, (long) Math.floor(register.getFirst() / (Math.pow(2, getCombo(register, operand)))));
    }

    protected void adv(List<Long> register, int operand) {
        xdv(register, 0, operand);
    }

    protected void bdv(List<Long> register, int operand) {
        xdv(register, 1, operand);
    }

    protected void cdv(List<Long> register, int operand) {
        xdv(register, 2, operand);
    }

    protected void bxl(List<Long> register, int operand) {
        register.set(1, register.get(1) ^ operand);
    }

    protected void bst(List<Long> register, int operand) {
        register.set(1, getCombo(register, operand) % 8);
    }

    protected int jnz(List<Long> register, int operand, int pointer) {
        if (register.getFirst() == 0) return pointer;
        return operand - 2;
    }

    protected void bxc(List<Long> register) {
        register.set(1, register.get(1) ^ register.get(2));
    }

    protected long out(List<Long> register, int operand) {
        return getCombo(register, operand) % 8;
    }

    protected List<Long> run(List<Long> register, List<Integer> program) {
        int pointer = 0;
        List<Long> out = new ArrayList<>();
        while (pointer < program.size()) {
            switch (program.get(pointer)) {
                case 0 -> adv(register, program.get(pointer + 1));
                case 1 -> bxl(register, program.get(pointer + 1));
                case 2 -> bst(register, program.get(pointer + 1));
                case 3 -> pointer = jnz(register, program.get(pointer + 1), pointer);
                case 4 -> bxc(register);
                case 5 -> out.add(out(register, program.get(pointer + 1)));
                case 6 -> bdv(register, program.get(pointer + 1));
                case 7 -> cdv(register, program.get(pointer + 1));
            }
            pointer += 2;
        }
        return out;
    }

    protected Long getRegisterABruteForce(List<Long> register, List<Integer> program) {
        List<Long> out;
        List<Long> registerCopy;
        long registerATest = (long) Math.pow(8, program.size() - 1);
        do {
            registerATest++;
            registerCopy = new ArrayList<>(register);
            registerCopy.set(0, registerATest);
            out = new Day17().run(registerCopy, program);
            if (out.size() != program.size()) {
                throw new RuntimeException("This should not happen");
            }
        } while (!out.stream().map(i -> Long.toString(i)).collect(joining(","))
                .equals(program.stream().map(i -> Integer.toString(i)).collect(joining(","))));
        return registerATest;
    }

    protected List<Integer> getPossibleValues(List<Integer> program, int numberToFound) {
        if (!List.of(5, 5, 0, 3, 3, 0).equals(program.subList(program.size() - 6, program.size()))) {
            throw new RuntimeException("This function can't found possible values for your program");
        }
        List<Integer> possibleValues = new ArrayList<>();
        // The limit is 1024 exclude because the number can't be shifted more than 7 times
        for (int i = 0; i < 1024; i++) {
            List<Long> register = new ArrayList<>(List.of((long) i, 0L, 0L));
            int pointer = 0;
            long out = -1;
            while (out == -1) {
                switch (program.get(pointer)) {
                    case 0 -> adv(register, program.get(pointer + 1));
                    case 1 -> bxl(register, program.get(pointer + 1));
                    case 2 -> bst(register, program.get(pointer + 1));
                    case 3 -> pointer = jnz(register, program.get(pointer + 1), pointer);
                    case 4 -> bxc(register);
                    case 5 -> out = out(register, program.get(pointer + 1));
                    case 6 -> bdv(register, program.get(pointer + 1));
                    case 7 -> cdv(register, program.get(pointer + 1));
                }
                pointer += 2;
            }
            if (out == numberToFound) {
                possibleValues.add(i);
            }
        }

        return possibleValues;
    }

    protected Map<Integer, List<Integer>> getPossibleValues(List<Integer> program) {
        Map<Integer, List<Integer>> possibleProgramValues = new HashMap<>();
        for (int i = program.size() - 1; i >= 0; i--) {
            List<Integer> possibleValues = getPossibleValues(program, program.get(i));
            // Last element should be less than 8
            if (i == program.size() - 1) {
                possibleValues = possibleValues.stream().filter(v -> v < 8).toList();
            } else if (i == program.size() - 2) {
                possibleValues = possibleValues.stream().filter(v -> v < Math.pow(8, 2)).toList();
            } else if (i == program.size() - 3) {
                possibleValues = possibleValues.stream().filter(v -> v < Math.pow(8, 3)).toList();
            }
            possibleProgramValues.put(i, possibleValues);
        }
        return possibleProgramValues;
    }

    protected Integer[] getForcedValues(List<Integer> program, Map<Integer, List<Integer>> possibleValues) {
        int byteSize = program.size() * 3;
        Integer[] forcedValues = new Integer[byteSize];
        Arrays.fill(forcedValues, -1);
        Map<Integer, List<Integer>> possibleValuesFiltered = new HashMap<>();
        for (int i = program.size() - 1; i >= 0; i--) {
            // Last element should be less than 8
            if (i == program.size() - 1) {
                possibleValuesFiltered.put(i, possibleValues.get(i).stream().filter(v -> v < 8).toList());
                setForcedValues(forcedValues, possibleValuesFiltered.get(i), 0, 3);
            } else if (i == program.size() - 2) {
                possibleValuesFiltered.put(i, possibleValues.get(i).stream().filter(v -> v < Math.pow(8, 2) &&
                        isValidWithForcedValues(List.of(forcedValues).subList(0, 6), v)).toList());
                setForcedValues(forcedValues, possibleValuesFiltered.get(i), 0, 6);
            } else if (i == program.size() - 3) {
                possibleValuesFiltered.put(i, possibleValues.get(i).stream().filter(v -> v < Math.pow(8, 3) &&
                        isValidWithForcedValues(List.of(forcedValues).subList(0, 9), v)).toList());
                setForcedValues(forcedValues, possibleValuesFiltered.get(i), 0, 9);
            } else {
                int finalI = i;
                possibleValuesFiltered.put(i, possibleValues.get(i).stream()
                        .filter(v -> isValidWithForcedValues(List.of(forcedValues).subList(byteSize - finalI * 3 - 10, byteSize - finalI * 3), v)).toList());
                setForcedValues(forcedValues, possibleValuesFiltered.get(i), byteSize - i * 3 - 10, byteSize - i * 3);
            }
        }
        return forcedValues;
    }

    protected boolean isValidWithForcedValues(List<Integer> forcedValues, Integer v) {
        for (int i = 0; i < forcedValues.size(); i++) {
            Integer bit = forcedValues.get(forcedValues.size() - 1 - i);
            if (bit != -1 && ((v & (1 << i)) > 0 && bit == 0 || (v & (1 << i)) == 0 && bit == 1)) {
                return false;
            }
        }
        return true;
    }

    private void setForcedValues(Integer[] forcedValues, List<Integer> possibleValues, int start, int end) {
        while (start < forcedValues.length && forcedValues[start] == 0) {
            start++;
        }
        for (int i = start; i < end; i++) {
            int finalI = i - start;
            if (possibleValues.stream().allMatch(v -> (v & (1 << finalI)) > 0)) {
                if (forcedValues[end - 1 - finalI] == 0) {
                    throw new RuntimeException("This should never happen, because possibleValues have been filtered");
                }
                forcedValues[end - 1 - finalI] = 1;
            } else if (possibleValues.stream().allMatch(v -> (v & (1 << finalI)) == 0)) {
                if (forcedValues[end - 1 - finalI] == 1) {
                    throw new RuntimeException("This should never happen, because possibleValues have been filtered");
                }
                forcedValues[end - 1 - finalI] = 0;
            }
        }
    }

    protected long getMinRegisterA(List<Integer> program) {
        Map<Integer, List<Integer>> possibleValues = getPossibleValues(program);
        possibleValues = getPossibleValuesFiltered(possibleValues, getForcedValues(program, possibleValues));
        List<Long> generatedValidValues = getValidValues(program, possibleValues);
        return generatedValidValues.stream().min(Long::compare).orElseThrow(() -> new RuntimeException("Something went wrong"));
    }

    protected List<Long> getValidValues(List<Integer> program, Map<Integer, List<Integer>> possibleValuesMap) {
        List<String> validValues = new ArrayList<>();
        for (int i = program.size() - 1; i >= 0; i--) {
            List<Integer> possibleValues = possibleValuesMap.get(i);
            if (validValues.isEmpty()) {
                validValues.addAll(possibleValues.stream()
                        .map(Long::toBinaryString)
                        .map(binaryString -> "0000000000".substring(binaryString.length()) + binaryString).toList());
            } else {
                List<String> newValidValues = new ArrayList<>();
                for (String v : validValues) {
                    for (Integer possibleValue : possibleValues) {
                        String binaryString = Integer.toBinaryString(possibleValue);
                        binaryString = "0000000000".substring(binaryString.length()) + binaryString;
                        String byteStart = binaryString.substring(0, binaryString.length() - 3);
                        String last3Bit = binaryString.substring(binaryString.length() - 3);
                        if (v.endsWith(byteStart)) {
                            newValidValues.add(v + last3Bit);
                        }
                    }
                }
                validValues = newValidValues;
            }
        }
        return validValues.stream().map(v -> Long.parseLong(v, 2)).toList();
    }

    protected Map<Integer, List<Integer>> getPossibleValuesFiltered(Map<Integer, List<Integer>> possibleValues, Integer[] forcedValues) {
        int byteSize = possibleValues.size() * 3;
        Map<Integer, List<Integer>> possibleValuesFiltered = new HashMap<>();
        for (int i = possibleValues.size() - 1; i >= 0; i--) {
            // Last element should be less than 8
            if (i == possibleValues.size() - 1) {
                possibleValuesFiltered.put(i, possibleValues.get(i).stream().filter(v -> v < 8).toList());
            } else if (i == possibleValues.size() - 2) {
                possibleValuesFiltered.put(i, possibleValues.get(i).stream().filter(v -> v < Math.pow(8, 2) &&
                        isValidWithForcedValues(List.of(forcedValues).subList(0, 6), v)).toList());
            } else if (i == possibleValues.size() - 3) {
                possibleValuesFiltered.put(i, possibleValues.get(i).stream().filter(v -> v < Math.pow(8, 3) &&
                        isValidWithForcedValues(List.of(forcedValues).subList(0, 9), v)).toList());
            } else {
                int finalI = i;
                possibleValuesFiltered.put(i, possibleValues.get(i).stream()
                        .filter(v -> isValidWithForcedValues(List.of(forcedValues).subList(byteSize - finalI * 3 - 10, byteSize - finalI * 3), v)).toList());
            }
        }
        return possibleValuesFiltered;
    }

    @Override
    public String getFirstAnswer() {
        Input input = getInput("/day17/input.txt");
        return run(input.getRegister(), input.getProgram()).stream().map(i -> Long.toString(i)).collect(joining(","));
    }

    @Override
    public String getSecondAnswer() {
        Input input = getInput("/day17/input.txt");
        return Long.toString(getMinRegisterA(input.getProgram()));
    }
}
