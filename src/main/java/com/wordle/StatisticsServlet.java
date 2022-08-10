package com.wordle;

import com.google.gson.Gson;
import com.wordle.dao.StatisticsDB;

import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

@WebServlet(name = "StatisticsServlet", value = "/statistics-servlet")
public class StatisticsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String event = request.getParameter("event");
        int rowIndex = Integer.parseInt(request.getParameter("rowIndex"));
        int id = Integer.parseInt(request.getParameter("userID"));
        int one = 0;
        int two = 0;
        int three = 0;
        int four = 0;
        int five = 0;
        int six = 0;
        int timesPlayed = 0;
        int timesWon = 0;
        int timesLost = 0;
        int winPercentage;
        int currentStreak = 0;
        int maxStreak = 0;
        Date lastCompleted = null;
        java.util.Date currentDate = new java.util.Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        LocalDate currentLocalDate = LocalDate.parse(dateFormat.format(currentDate));

        if (id == 0) {
            id = StatisticsDB.generateID();
            Cookie userIDCookie = new Cookie("__u_id", String.valueOf(id));
            userIDCookie.setPath("/");
            userIDCookie.setMaxAge(60 * 60 * 24 * 365 * 10);
            response.addCookie(userIDCookie);
        }

        ResultSet rs = StatisticsDB.getStatistics(id);
        try {
            assert rs != null;
            if (rs.next()) {
                one = rs.getInt("one");
                two = rs.getInt("two");
                three = rs.getInt("three");
                four = rs.getInt("four");
                five = rs.getInt("five");
                six = rs.getInt("six");
                timesPlayed = rs.getInt("times_played");
                timesWon = rs.getInt("times_won");
                timesLost = rs.getInt("times_lost");
                currentStreak = rs.getInt("current_streak");
                maxStreak = rs.getInt("max_streak");
                lastCompleted = rs.getDate("last_completed");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

//        LocalDate lastCompletedLocalDate = LocalDate.parse(dateFormat.format(lastCompleted));
//        long daysBetween = ChronoUnit.DAYS.between(lastCompletedLocalDate, currentLocalDate);
//        if (daysBetween != 1) {
//            currentStreak = 0;
//        }

        if (event.equals("win")) {
            if (rowIndex == 0) {
                one += 1;
            } else if (rowIndex == 1) {
                two += 1;
            } else if (rowIndex == 2) {
                three += 1;
            } else if (rowIndex == 3) {
                four += 1;
            } else if (rowIndex == 4) {
                five += 1;
            } else if (rowIndex == 5) {
                six += 1;
            }
            timesWon += 1;
            currentStreak += 1;
            if (maxStreak < currentStreak) maxStreak = currentStreak;
        } else {
            currentStreak = 0;
            timesLost += 1;
        }
        timesPlayed += 1;
        winPercentage = (int) (Math.ceil(((double)timesWon/(double)timesPlayed)*100));

        Gson gson = new Gson();

        String dictionary = "{" +
                "one: " + one + "," +
                "two: " + two + "," +
                "three: " + three + "," +
                "four: " + four + "," +
                "five: " + five + "," +
                "six: " + six + "," +
                "timesPlayed: " + timesPlayed + "," +
                "winPercentage: " + winPercentage + "," +
                "currentStreak: " + currentStreak + "," +
                "maxStreak: " + maxStreak +
                "}";

        Cookie cookie = new Cookie("stats", gson.toJson(dictionary));
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 365 * 10);
        response.addCookie(cookie);

        if (event.equals("WIN")) StatisticsDB.updateStatistics(one, two, three, four, five, six, timesPlayed, timesWon, timesLost, winPercentage, currentStreak, maxStreak, id);
        else StatisticsDB.updateStatistics(one, two, three, four, five, six, timesPlayed, timesWon, timesLost, winPercentage, currentStreak, maxStreak, lastCompleted, id);
    }
}
