package ru.polskiy.in;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleInput implements  Input{

    private final BufferedReader br;

    public ConsoleInput() {
        br=new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public Object in() {
        String readData;
        try {
            readData = br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return readData;
    }

    @Override
    public void closeIn() {
        try {
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
