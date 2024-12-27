package aoc.day17;

import java.util.List;

public class Input {
    private final List<Long> register;
    private final List<Integer> program;

    public Input(List<Long> register, List<Integer> program) {
        this.register = register;
        this.program = program;
    }

    public List<Long> getRegister() {
        return register;
    }

    public List<Integer> getProgram() {
        return program;
    }
}
