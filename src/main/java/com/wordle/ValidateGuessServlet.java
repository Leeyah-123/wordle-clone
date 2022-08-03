package com.wordle;

import com.wordle.dao.TodaysWordDB;
import com.wordle.dao.UsedWordsDB;
import com.wordle.dao.WordsDB;
import com.wordle.utils.RandomWord;

import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;

@WebServlet(name = "ValidateGuessServlet", value="/validate-guess-servlet")
public class ValidateGuessServlet extends HttpServlet {

    public void init() {

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        String userGuess = request.getParameter("guess");

        PrintWriter out = response.getWriter();
        List<String> validated = new ArrayList<>();

        Date currentDay = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        if (!((dateFormat.format(TodaysWordDB.getDate())).equals(dateFormat.format(currentDay)))) {
            int randomId = RandomWord.randomId();
            TodaysWordDB.setTodaysWord(randomId, WordsDB.getWord(randomId));
            UsedWordsDB.addUsedWord(randomId, WordsDB.getWord(randomId));
        }

        if (WordsDB.wordExists(userGuess.toLowerCase()) == 0) {
            validated.add("");
            out.write(String.valueOf(validated));
            return;
        }

        if (Objects.equals(TodaysWordDB.getTodaysWord(), userGuess.toLowerCase())) {
            validated.add("correct");
            out.write(String.valueOf(validated));
            return;
        }

        char[] guessArray = userGuess.toLowerCase().toCharArray();
        Map<Integer, Character> todaysWordDictionary = new HashMap<>();
        for (int j = 0; j < Objects.requireNonNull(TodaysWordDB.getTodaysWord()).length(); j++) {
            todaysWordDictionary.put(j, TodaysWordDB.getTodaysWord().charAt(j));
        }

        for (int i = 0; i < guessArray.length; i++) {
            String outcome;
            ArrayList<String> outcomeList = new ArrayList<>();
            for (int j = 0; j <= 4; j++) {
                if (!todaysWordDictionary.containsKey(j)) continue;
                if (guessArray[i] == todaysWordDictionary.get(j)) {
                    if (i == j) {
                        outcomeList.add("correct");
                    }
                    else {
                        outcomeList.add("present");
                    }
                    todaysWordDictionary.remove(j);
                } else {
                    outcomeList.add("absent");
                }
            }
            if (outcomeList.contains("correct")) outcome = "correct";
            else if (outcomeList.contains("present")) outcome = "present";
            else outcome = "absent";

            validated.add(outcome);
        }
        out.write(String.valueOf(validated));
    }

    public void destroy() {
    }
}