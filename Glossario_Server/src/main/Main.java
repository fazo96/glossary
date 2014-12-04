package main;

import util.FileUtil;

public class Main {
    public static void main(String args[]){
        System.out.println("Glossary Server");
        // Test
        FileUtil.writeFile("file.txt", "CIAO\nDUERIGHE");
        System.out.println(FileUtil.readFile("file.txt"));
    }
}
