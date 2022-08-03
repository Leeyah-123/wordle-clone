package com.wordle.dao;

import com.wordle.utils.DBConstants;
import com.wordle.utils.config.db.DBDataSource;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
    /*
     * getting the connection data from the dbconfigclass
     * @Reference DBDataSource()
     */
    private final String CONNECTION_STRING = DBDataSource.getInstance().getDatasource();
    private final String USER = DBDataSource.getInstance().getUser();
    private final String PASSWORD = DBDataSource.getInstance().getPassword();

    private static final DBConnection instance = new DBConnection();

    public static DBConnection getInstance() {
        return instance;
    }

    public DBConnection() {
        createTables();
    }

    // connection method for connecting to the database
    public Connection connection() {
        // load the driver
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException cnf) {
            System.out.println("Class could not be loaded because: " + cnf.getMessage());
        }

        // connect to the database
        try {
            Connection connection = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
            Logger.getLogger(getClass().getName()).log(Level.INFO, "Database Connection success");
            return connection;
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return null;
        }
    }

    // automating table creation
    private void createTables() {
        try {
            Statement statement = connection().createStatement();

            // words table
            String wordsTable = "CREATE TABLE IF NOT EXISTS public." + DBConstants.TABLE_WORDS + "(" +
                    DBConstants.WORD_ID + " SERIAL PRIMARY KEY NOT NULL," +
                    DBConstants.WORD + " VARCHAR UNIQUE NOT NULL" +
                    ");";

            // used words table
            String usedWordsTable = "CREATE TABLE IF NOT EXISTS public." + DBConstants.TABLE_USED_WORDS + "(" +
                    DBConstants.WORD_ID + " INTEGER PRIMARY KEY NOT NULL," +
                    DBConstants.WORD + " VARCHAR UNIQUE NOT NULL" +
                    ");";

            // statistics table
            String statisticsTable = "CREATE TABLE IF NOT EXISTS public." + DBConstants.TABLE_STATISTICS + "(" +
                    DBConstants.USER_ID + " SERIAL PRIMARY KEY NOT NULL," +
                    DBConstants.ONE + " INTEGER NOT NULL," +
                    DBConstants.TWO + " INTEGER NOT NULL," +
                    DBConstants.THREE + " INTEGER NOT NULL," +
                    DBConstants.FOUR + " INTEGER NOT NULL," +
                    DBConstants.FIVE + " INTEGER NOT NULL," +
                    DBConstants.SIX + " INTEGER NOT NULL," +
                    DBConstants.TIMES_PLAYED + " INTEGER NOT NULL," +
                    DBConstants.TIMES_WON + " INTEGER NOT NULL," +
                    DBConstants.TIMES_LOST + " INTEGER NOT NULL," +
                    DBConstants.WIN_PERCENTAGE + " INTEGER NOT NULL," +
                    DBConstants.CURRENT_STREAK + " INTEGER NOT NULL," +
                    DBConstants.MAX_STREAK + " INTEGER NOT NULL," +
                    DBConstants.LAST_COMPLETED + " DATE NOT NULL DEFAULT CURRENT_DATE" +
                    ");";

            // today's word table
            String todaysWordTable = "CREATE TABLE IF NOT EXISTS public." + DBConstants.TABLE_TODAYS_WORD + "(" +
                    DBConstants.WORD_ID + " INTEGER PRIMARY KEY NOT NULL," +
                    DBConstants.WORD + " VARCHAR UNIQUE NOT NULL," +
                    DBConstants.TODAYS_DATE + " DATE NOT NULL DEFAULT CURRENT_DATE" +
                    ");";

            // executing the "create table" queries
            statement.executeUpdate(wordsTable);
            statement.executeUpdate(usedWordsTable);
            statement.executeUpdate(statisticsTable);
            statement.executeUpdate(todaysWordTable);

            Logger.getLogger(getClass().getName()).log(Level.INFO, "All tables successfully loaded");
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            e.printStackTrace();
        }
    }

}
