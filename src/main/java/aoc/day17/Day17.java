package aoc.day17;

import aoc.DailyExercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
        long registerATest = (long) Math.pow(8, program.size()) - 1;
        do {
            registerATest -= 1;
            registerCopy = new ArrayList<>(register);
            registerCopy.set(0, registerATest);
            out = new Day17().run(registerCopy, program);
            if (out.size() != program.size()) {
                throw new RuntimeException("This should not happen");
            }
            if (out.subList(0, 8).stream().map(i -> Long.toString(i)).collect(joining(","))
                    .equals(program.subList(0, 8).stream().map(i -> Integer.toString(i)).collect(joining(",")))) {
                System.out.printf("%s gives %s\n", Long.toBinaryString(registerATest), out.stream().map(i -> Long.toString(i)).collect(joining(",")));
            }
            if (out.subList(8, 16).stream().map(i -> Long.toString(i)).collect(joining(","))
                    .equals(program.subList(8, 16).stream().map(i -> Integer.toString(i)).collect(joining(",")))) {
                System.out.printf("%s gives %s\n", Long.toBinaryString(registerATest), out.stream().map(i -> Long.toString(i)).collect(joining(",")));
            }
        } while (!out.stream().map(i -> Long.toString(i)).collect(joining(","))
                .equals(program.stream().map(i -> Integer.toString(i)).collect(joining(","))));
        return registerATest;
    }

    @Override
    public String getFirstAnswer() {
        Input input = getInput("/day17/input.txt");
        return run(input.getRegister(), input.getProgram()).stream().map(i -> Long.toString(i)).collect(joining(","));
    }

    @Override
    public String getSecondAnswer() {
        Input input = getInput("/day17/input.txt");
        return Long.toString(getRegisterABruteForce(input.getRegister(), input.getProgram()));
    }
}
