package com.drassero.bfcompiler.util;

import java.util.Iterator;
import java.util.Optional;

public class Interpreter {

    public static final Optional<String> INVALID_LOOPS_ERROR = Optional.of("Invalid use of loops operators"),
            INVALID_INPUT = Optional.of("Invalid input ! Only ASCII characters permitted"),
            INVALID_OUTPUT = Optional.of("Invalid output ! Only ASCII characters permitted"),
            NO_ERROR = Optional.empty();

    private final String statement;
    private final String input;
    private final int length;

    public Interpreter(String statement, String input, int length) {
        this.statement = statement;
        this.input = input;
        this.length = length;
    }

    private StringBuilder output;
    private int[] bytes;
    private int ptr;
    private Iterator<Integer> inputIterator;

    public Interpreter init() {
        output = new StringBuilder();
        bytes = new int[length];
        ptr = 0;
        inputIterator = input.chars().iterator();
        return this;
    }

    public String run() {
        run(statement);
        return output.toString();
    }

    private void run(String statement) {
        if(statement.isEmpty()) {
            return;
        }
        int activeLoops = 0;
        StringBuilder loopStatement = new StringBuilder();
        for(char c : statement.toCharArray()) {
            if(activeLoops > 0) {
                if(c == '[') {
                    activeLoops++;
                } else if(c == ']' && --activeLoops == 0) {
                    while(bytes[ptr] > 0) {
                        run(loopStatement.toString());
                    }
                    loopStatement.setLength(0);
                    continue;
                }
                loopStatement.append(c);
                continue;
            }
            switch(c) {
                case '>' -> ptr = ptr + 1 == length ? 0 : ptr + 1;
                case '<' -> ptr = ptr - 1 == -1 ? length -1 : ptr - 1;
                case '+' -> bytes[ptr] = bytes[ptr] + 1 == 256 ? 0 : bytes[ptr] + 1;
                case '-' -> bytes[ptr] = bytes[ptr] - 1 == -1 ? 255 : bytes[ptr] - 1;
                case '.' -> output.append((char) bytes[ptr]);
                case ',' -> {
                    if(inputIterator.hasNext()) {
                        bytes[ptr] = inputIterator.next();
                    } else {
                        bytes[ptr] = 0;
                    }
                }
                case '[' -> activeLoops++;
            }
        }
    }

    public Optional<String> isValid() {
        int activeLoops = 0;
        for(char c : statement.toCharArray()) {
            switch(c) {
                case '[' -> activeLoops++;
                case ']' -> activeLoops--;
            }
        }
        if(activeLoops != 0) {
            return INVALID_LOOPS_ERROR;
        }
        for(char c : input.toCharArray()) {
            if(c > 127) {
                return INVALID_INPUT;
            }
        }
        for(char c : output.toString().toCharArray()) {
            if(c > 127) {
                return INVALID_OUTPUT;
            }
        }
        return NO_ERROR;
    }

}
