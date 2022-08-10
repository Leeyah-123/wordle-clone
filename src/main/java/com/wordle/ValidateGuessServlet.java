package com.wordle;

import com.wordle.dao.UsedWordsDB;
import com.wordle.dao.WordsDB;
import com.wordle.utils.RandomWord;

import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet(name = "ValidateGuessServlet", value="/validate-guess-servlet")
public class ValidateGuessServlet extends HttpServlet {

    public void init() {

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        int rowIndex = Integer.parseInt(request.getParameter("rowIndex"));
        String userGuess = request.getParameter("guess");
        String solution = "";

        PrintWriter out = response.getWriter();
        List<String> validated = new ArrayList<>();

//        Date currentDay = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//
//        if (!((dateFormat.format(TodaysWordDB.getDate())).equals(dateFormat.format(currentDay)))) {
//            int randomId = RandomWord.randomId();
//            TodaysWordDB.setTodaysWord(randomId, WordsDB.getWord(randomId));
//            UsedWordsDB.addUsedWord(randomId, WordsDB.getWord(randomId));
//        }

        if (rowIndex == 1) {
            int randomId = RandomWord.randomId();
            solution = WordsDB.getWord(randomId);
            Cookie solutionCookie = new Cookie("solution",solution);
            solutionCookie.setPath("/");
            solutionCookie.setMaxAge(60 * 60 * 24 * 365 * 10);
            response.addCookie(solutionCookie);
//            TodaysWordDB.setTodaysWord(randomId, WordsDB.getWord(randomId));
            UsedWordsDB.addUsedWord(randomId, WordsDB.getWord(randomId));
        } else {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("solution")) solution = cookie.getValue();
            }
        }

        if (WordsDB.wordExists(userGuess.toLowerCase()) == 0) {
            validated.add("");
            out.write(String.valueOf(validated));
            return;
        }

        if (Objects.equals(solution, userGuess.toLowerCase())) {
            validated.add("correct");
            out.write(String.valueOf(validated));
            return;
        }

        char[] guessArray = userGuess.toLowerCase().toCharArray();
        Map<Integer, Character> correctWordDictionary = new HashMap<>();
        for (int j = 0; j < solution.length(); j++) {
            correctWordDictionary.put(j, solution.charAt(j));
        }

        for (int i = 0; i < guessArray.length; i++) {
            String outcome;
            ArrayList<String> outcomeList = new ArrayList<>();
            for (int j = 0; j <= 4; j++) {
                if (!correctWordDictionary.containsKey(j)) continue;
                if (guessArray[i] == correctWordDictionary.get(j)) {
                    if (i == j) {
                        outcomeList.add("correct");
                    }
                    else {
                        outcomeList.add("present");
                    }
                    correctWordDictionary.remove(j);
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
}