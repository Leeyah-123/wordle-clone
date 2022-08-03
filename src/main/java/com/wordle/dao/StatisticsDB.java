package com.wordle.dao;

import com.wordle.utils.DBConstants;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StatisticsDB {
    Statement statement = null;
    static PreparedStatement preparedStatement = null;
    private static final Connection connection = DBConnection.getInstance().connection();

    public StatisticsDB() {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static ResultSet getStatistics(int id) {
        String query = "SELECT * FROM " + DBConstants.TABLE_STATISTICS + " WHERE " + DBConstants.USER_ID + " = ?;";
        ResultSet rs;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();
        }  catch (SQLException e) {
            Logger.getLogger(TodaysWordDB.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            return null;
        }
        return rs;
    }

    public static int generateID() {
        String query = "INSERT INTO " + DBConstants.TABLE_STATISTICS + "(one, two, three, four, five, six, times_played, times_won, times_lost, win_percentage, current_streak, max_streak, last_completed) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_DATE) RETURNING user_id;";
        int id = 0;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, 0);
            preparedStatement.setInt(2, 0);
            preparedStatement.setInt(3, 0);
            preparedStatement.setInt(4, 0);
            preparedStatement.setInt(5, 0);
            preparedStatement.setInt(6, 0);
            preparedStatement.setInt(7, 0);
            preparedStatement.setInt(8, 0);
            preparedStatement.setInt(9, 0);
            preparedStatement.setInt(10, 0);
            preparedStatement.setInt(11, 0);
            preparedStatement.setInt(12, 0);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) id = rs.getInt("user_id");
        }  catch (SQLException e) {
            Logger.getLogger(TodaysWordDB.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return id;
    }

    public static int updateStatistics(int one, int two, int three, int four, int five, int six, int timesPlayed, int timesWon, int timesLost, int winPercentage, int currentStreak, int maxStreak, Date lastCompleted, int id) {
        String query = "UPDATE " + DBConstants.TABLE_STATISTICS + " SET one = ?, two = ?, three = ?, four = ?, five = ?, six = ?, times_played = ?, times_won = ?, times_lost = ?, win_percentage = ?, current_streak = ?, max_streak = ?, last_completed = ? WHERE user_id = ?;";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, one);
            preparedStatement.setInt(2, two);
            preparedStatement.setInt(3, three);
            preparedStatement.setInt(4, four);
            preparedStatement.setInt(5, five);
            preparedStatement.setInt(6, six);
            preparedStatement.setInt(7, timesPlayed);
            preparedStatement.setInt(8, timesWon);
            preparedStatement.setInt(9, timesLost);
            preparedStatement.setInt(10, winPercentage);
            preparedStatement.setInt(11, maxStreak);
            preparedStatement.setInt(12, currentStreak);
            preparedStatement.setDate(13, lastCompleted);
            preparedStatement.setInt(14, id);
            return preparedStatement.executeUpdate();

        }  catch (SQLException e) {
            Logger.getLogger(TodaysWordDB.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            return -1;
        }
    }

    public static int updateStatistics(int one, int two, int three, int four, int five, int six, int timesPlayed, int timesWon, int timesLost, int winPercentage, int currentStreak, int maxStreak, int id) {
        String query = "UPDATE " + DBConstants.TABLE_STATISTICS + " SET one = ?, two = ?, three = ?, four = ?, five = ?, six = ?, times_played = ?, times_won = ?, times_lost = ?, win_percentage = ?, current_streak = ?, max_streak = ?, last_completed = CURRENT_DATE WHERE user_id = ?;";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, one);
            preparedStatement.setInt(2, two);
            preparedStatement.setInt(3, three);
            preparedStatement.setInt(4, four);
            preparedStatement.setInt(5, five);
            preparedStatement.setInt(6, six);
            preparedStatement.setInt(7, timesPlayed);
            preparedStatement.setInt(8, timesWon);
            preparedStatement.setInt(9, timesLost);
            preparedStatement.setInt(10, winPercentage);
            preparedStatement.setInt(11, maxStreak);
            preparedStatement.setInt(12, currentStreak);
            preparedStatement.setInt(13, id);
            return preparedStatement.executeUpdate();

        }  catch (SQLException e) {
            Logger.getLogger(TodaysWordDB.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            return -1;
        }
    }

}
