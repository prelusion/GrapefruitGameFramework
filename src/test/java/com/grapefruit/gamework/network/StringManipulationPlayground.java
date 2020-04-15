package com.grapefruit.gamework.network;

import org.junit.Test;

import java.util.Arrays;

public class StringManipulationPlayground {


    @Test
    public void getArgs(){
        String begin = "SVR GAMELIST ['Reversi', 'Tic-tac-toe', 'cod', 'gta'] ";
        int startArg = begin.indexOf("[");
        String[] arr = begin.substring(startArg + 1, begin.trim().length() - 1).split(", ");

        String[] result = new String[arr.length];
        int i = 0;
        for (String element : arr) {
            result[i] = element.substring(1, element.length() - 1);
            i++;
        }

        System.out.println(Arrays.toString(result));
    }
}