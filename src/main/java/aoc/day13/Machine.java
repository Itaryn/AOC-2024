package aoc.day13;

public class Machine {
    private final Position buttonA;
    private final Position buttonB;
    private final Position prize;

    public Machine(Position buttonA, Position buttonB, Position prize) {
        this.buttonA = buttonA;
        this.buttonB = buttonB;
        this.prize = prize;
    }

    public Position getButtonA() {
        return buttonA;
    }

    public Position getButtonB() {
        return buttonB;
    }

    public Position getPrize() {
        return prize;
    }
}
