package com.wordle.utils.config.db;

import com.wordle.utils.Constants;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class DBDataSource {
    private static final DBDataSource instance = new DBDataSource();
    private final Properties properties = new Properties();

    public DBDataSource() {
        try (FileReader reader = new FileReader(Constants.DATABASE_CONFIG_DIR)) {
            properties.load(reader);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DBDataSource getInstance() {
        return instance;
    }

    public String getUser() {
        return properties.getProperty("db.username");
    }

    public String getPassword() {
        return properties.getProperty("db.password");
    }

    public String getDatasource() {
        return properties.getProperty("db.datasource");
    }
}
