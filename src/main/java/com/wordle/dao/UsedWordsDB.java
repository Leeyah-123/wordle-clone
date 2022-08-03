package com.wordle.dao;

import com.wordle.utils.DBConstants;
import com.wordle.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsedWordsDB {
    Statement statement = null;
    static PreparedStatement preparedStatement = null;
    private static final Connection connection = DBConnection.getInstance().connection();

    public UsedWordsDB() {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static int addUsedWord(int id, String word) {
        String query = "INSERT INTO " + DBConstants.TABLE_USED_WORDS + " VALUES (?, ?);";
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

    public static int wordUsed(int id) {
        String query = "SELECT COUNT(" + DBConstants.WORD_ID + ") FROM " + DBConstants.TABLE_USED_WORDS  + " WHERE " + DBConstants.WORD_ID + " = " + id + ";";
        return DBUtil.getInstance().counter(query);
    }
}
