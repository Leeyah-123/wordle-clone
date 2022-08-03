package com.wordle.utils;

import com.wordle.dao.UsedWordsDB;

public class RandomWord {
    public static int randomId() {
        int id;
        while (true) {
            id = randomWordId();
            if (id > 5758 || UsedWordsDB.wordUsed(id) != 0) id = randomWordId();
            else break;
        }
        return id;
    }

    private static int randomWordId() {
        return (int) Math.ceil(Math.random()*6000);
    }
}
