package com.example.TheatreRoyal;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/*
 * 7 Steps to DB Connection
 * ---------------------------------------
 * 1. Load the driver in.
 * 2. Build a connection URL.
 * 3. Establish connection using the URL.
 * ---------------------------------------
 * 4. Prepare a query statement to run
 * 5. Execute query
 * 6. Process Results
 * ---------------------------------------
 * 7. Close connection
 */


/*The connection parameters (database URL, username and password) are nomore
hardcoded. Storing Information in text file is the correct way to store Info.*/

public class DBConnector {
    private Connection conn;

    public DBConnector() {
        conn = null;
    }

    public void connect() {
        String [] config = new String[3];
        //Storing Connection parameters in text file
        try {
            File file = new File("config.txt");
            Scanner sc = new Scanner(file);
            int i = 0;
            while (sc.hasNextLine()) {
                config[i] = sc.nextLine().split(":",2)[1];
                i++;
            }
        }catch(FileNotFoundException e ) {
            e.printStackTrace();
            return;
        }



        // Reading connection parameters from filer
        try {
            conn = DriverManager.getConnection(
                    config[0],
                    config[1],
                    config[2]
            );
        }
        catch (SQLException e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
            return;
        }

        if (conn != null) {
            System.out.println("Connection established.");
        }
        else {
            System.out.println("Connection null still.");
        }
    }

    /*
     * 4. Prepare a query statement to run - DONE :)
     * 5. Execute query - DONE
     */

    public ResultSet runQuery (String sql){
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql);
            pst.execute();
            return pst.getResultSet();
        } catch (SQLException e) {
            System.out.println(sql + "\n failed to run.");
            e.printStackTrace();
            return null;
        }

    }

    // 6. Process Results

	/*	Method printSqlQueryOutput() displays the column heads. No
	hardcoded strings*/

    public ArrayList<String> getQueryResult(ResultSet rs) {
        ArrayList<String> temp = new ArrayList<String>();
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = rs.getString(i);
                    temp.add(columnValue);
                }
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return temp;
    }

    public void close() {
        try {
            conn.close();
            System.out.println("Connection closed.");
        } catch (SQLException e) {
            System.out.println("Connection not closed.");
            e.printStackTrace();
        }
    }

}
