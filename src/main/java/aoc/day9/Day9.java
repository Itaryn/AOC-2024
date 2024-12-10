package aoc.day9;

import aoc.DailyExercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class Day9 implements DailyExercise {
    @Override
    public String getFirstAnswer() {
        List<Integer> input = getInput();
        List<Integer> memory = toFlat(input);
        return Long.toString(calculate(memory));
    }

    @Override
    public String getSecondAnswer() {
        List<Integer> input = getInput();
        List<Block> blocks = IntStream.range(0, input.size())
                .mapToObj(i -> new Block(input.get(i), i % 2 == 0 ? i / 2 : -1))
                .toList();
        List<Integer> memory = toFlat2(blocks);
        return Long.toString(calculate2(memory));
    }

    private List<Integer> getInput() {
        try (InputStream inputStream = this.getClass().getResourceAsStream("/resources/input_day9.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            return reader.readLine().chars().mapToObj(Character::getNumericValue).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Integer> toFlat(List<Integer> input) {
        List<Integer> res = new ArrayList<>();
        int i = 0;
        for (int v : input) {
            int index = i / 2;
            if (i % 2 != 0) {
                index = -1;
            }
            res.addAll(Collections.nCopies(v, index));
            i++;
        }

        return res;
    }

    private List<Integer> toFlat2(List<Block> input) {
        List<Block> blocks = new ArrayList<>(input.stream().map(block -> new Block(block.getSize(), block.getIndex())).toList());

        for (int i = input.size() - 1; i > 0; i -= 2) {
            int j = 1;
            boolean moved = false;
            int size = input.get(i).getSize();
            int finalI = i;
            Block blockToMove = blocks.stream().filter(block -> block.getIndex() == input.get(finalI).getIndex()).findFirst().get();
            while (j < blocks.indexOf(blockToMove) && !moved) {
                if (blocks.get(j).getSize() >= size) {
                    // Reset the old file block
                    blockToMove.setSize(size).setIndex(-1);

                    Block emptySpace = blocks.remove(j);

                    // Insert the file block and add space between
                    blocks.add(j, new Block(emptySpace.getSize() - size, -1));
                    blocks.add(j, input.get(i));
                    blocks.add(j, new Block(0, -1));
                    moved = true;
                }
                j += 2;
            }
        }

        return convert(blocks);
    }

    private List<Integer> convert(List<Block> blocks) {
        List<Integer> res = new ArrayList<>();
        for (Block v : blocks) {
            res.addAll(Collections.nCopies(v.getSize(), v.getIndex()));
        }

        return res;
    }

    private long calculate(List<Integer> memory) {
        int i = 0;
        int j = memory.size() - 1;
        long total = 0;
        while (i <= j) {
            int currentValue = 0;
            if (memory.get(i) == -1) {
                while (j > 0 && memory.get(j) == -1) {
                    j--;
                }
                currentValue = memory.get(j);
                j--;
            } else {
                currentValue = memory.get(i);
            }
            total += (long) i * currentValue;
            i++;
        }
        return total;
    }

    private long calculate2(List<Integer> memory) {
        long res = 0;
        for (int i = 0; i < memory.size(); i++) {
            int current = memory.get(i);
            if (current != -1) {
                res += (long) i * current;
            }
        }
        return res;
    }
}
