package com.drassero.bfcompiler.util;

import java.util.Iterator;

public class Compiler {

    private String statement;
    private String input;
    private int length;

    public Compiler(String statement, String input, int length) {
        this.statement = statement;
        this.input = input;
        this.length = length;
    }

    private StringBuilder output;
    private int[] bytes;
    private int ptr, activeLoops;
    private Iterator<Integer> inputIterator;

    public boolean isValid() {
        int activeLoops = 0;
        for(char c : statement.toCharArray()) {
            switch(c) {
                case '[' -> activeLoops++;
                case ']' -> activeLoops--;
            }
        }
        boolean onlyAscii = true;
        for(char c : input.toCharArray()) {
            if((int) c > 127) {
                onlyAscii = false;
                break;
            }
        }
        return activeLoops == 0 && onlyAscii;
    }

    public Compiler init() {
        output = new StringBuilder();
        bytes = new int[length];
        ptr = 0;
        inputIterator = input.chars().iterator();
        activeLoops = 0;
        return this;
    }

    public String run() {
        run(statement);
        return output.toString();
    }

    private void run(String statement) {
        StringBuilder loopStatement = new StringBuilder();
        for(char c : statement.toCharArray()) {
            if(activeLoops > 0) {
                if(c == ']' && --activeLoops == 0) {
                    while(bytes[ptr] > 0) {
                        run(loopStatement.toString());
                    }
                    continue;
                }
                loopStatement.append(c);
                continue;
            }
            switch(c) {
                case '>' -> ptr = ptr + 1 == length ? 0 : ptr + 1;
                case '<' -> ptr = ptr - 1 == -1 ? length - 1 : ptr - 1;
                case '+' -> bytes[ptr] = bytes[ptr] + 1 == 256 ? 0 : bytes[ptr] + 1;
                case '-' -> bytes[ptr] = bytes[ptr] - 1 == -1 ? 255 : bytes[ptr] - 1;
                case '.' -> output.append((char) bytes[ptr]);
                case ',' -> {
                    if(inputIterator.hasNext()) {
                        bytes[ptr] = inputIterator.next();
                    }
                }
                case '[' -> activeLoops++;
                case ']' -> activeLoops--;
            }
        }
    }

}
