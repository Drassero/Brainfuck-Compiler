package com.drassero.bfcompiler;

import com.drassero.bfcompiler.util.Compiler;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Statement ?");
        String statement = scanner.nextLine();
        System.out.println("Input ?");
        String input = scanner.nextLine();
        System.out.println("Length ?");
        int length = scanner.nextInt();
        Compiler compiler = new Compiler(statement, input, length).init();
        System.out.println(compiler.run());
    }

}
