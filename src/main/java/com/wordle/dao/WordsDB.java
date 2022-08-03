package com.wordle.dao;

import com.wordle.utils.DBConstants;
import com.wordle.utils.DBUtil;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WordsDB {
    Statement statement = null;
    static PreparedStatement preparedStatement = null;
    private static final Connection connection = DBConnection.getInstance().connection();

    public WordsDB() {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    // used once to populate db with 5-letter words
//    public static String populateTable() {
//        // pass the path to the file as a parameter
//        File file = new File("/home/leeyah/IdeaProjects/wordle/src/main/java/com/wordle/dao/5-letter-words.txt");
//        Scanner sc;
//        try {
//            sc = new Scanner(file);
//            while (sc.hasNextLine()) {
//                String word = sc.nextLine();
//                System.out.println(word);
//                String query = "INSERT INTO " + DBConstants.TABLE_WORDS + "(" + DBConstants.WORD + ") VALUES (?)";
//                preparedStatement = connection.prepareStatement(query);
//                preparedStatement.setString(1, word);
//                preparedStatement.executeUpdate();
//            }
//            return "Success";
//        } catch (FileNotFoundException | SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public static int wordExists(String word) {
        String query = "SELECT COUNT(" + DBConstants.WORD_ID + ") FROM " + DBConstants.TABLE_WORDS  + " WHERE " + DBConstants.WORD + " = '" + word + "';";
        return DBUtil.getInstance().counter(query);
    }

    public static String getWord(int id) {
        String query = "SELECT " + DBConstants.WORD + " FROM " + DBConstants.TABLE_WORDS + " WHERE " + DBConstants.WORD_ID + " = ?;";
        String word = "";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                word = rs.getString(DBConstants.WORD);
            }
        }  catch (SQLException e) {
            Logger.getLogger(TodaysWordDB.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            return null;
        }
        return word;
    }
}
