package com.wordle.dao;

import com.wordle.utils.DBConstants;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TodaysWordDB {
    Statement statement = null;
    static PreparedStatement preparedStatement = null;
    private static final Connection connection = DBConnection.getInstance().connection();

    public TodaysWordDB() {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    // getting the word of the day from the database
    public static String getTodaysWord() {
        String query = "SELECT * FROM " + DBConstants.TABLE_TODAYS_WORD + ";";
        String todays_word = "";
        try {
            preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                todays_word = rs.getString(DBConstants.WORD);
            }
        }  catch (SQLException e) {
            Logger.getLogger(TodaysWordDB.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            return null;
        }
        return todays_word;
    }

    public static int setTodaysWord(int id, String word) {
        String query = "UPDATE " + DBConstants.TABLE_TODAYS_WORD + " SET " + DBConstants.WORD_ID + " = ?, " + DBConstants.WORD + " = ?, " + DBConstants.TODAYS_DATE + " = CURRENT_DATE;";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, word);
            return preparedStatement.executeUpdate();
        }  catch (SQLException e) {
            Logger.getLogger(TodaysWordDB.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            return -1;
        }
    }

    public static Date getDate() {
        String query = "SELECT " + DBConstants.TODAYS_DATE + " FROM " + DBConstants.TABLE_TODAYS_WORD + ";";
        Date date = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                date = rs.getDate(DBConstants.TODAYS_DATE);
            }
        }  catch (SQLException e) {
            Logger.getLogger(TodaysWordDB.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return date;
    }
}
