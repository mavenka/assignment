/*
 * SQLiteTest.java
 * 
 * Copyright 2017 mani <mani@HomePC>
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 * 
 * 
 */
package com.mavenka.assignment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AssignmentWithDenorm {
    /**
     * Connect to a sample database
     */
    public static void connect() {
		Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:assignment.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            conn.setAutoCommit(false);

            PreparedStatement ps1 = conn.prepareStatement("SELECT TERR_ID, GEO_PATH, GENDER_CODE, HOME_OWNERSHIP_CODE from TERR_DENORM");
            PreparedStatement ps2 = conn.prepareStatement("SELECT TERR_ID from ACCT_DENORM WHERE GEO_PATH LIKE ? AND GENDER_CODE LIKE ? AND HOME_OWNERSHIP_CODE like ?");
            PreparedStatement ps3 = conn.prepareStatement("INSERT INTO ACCOUNT_TERRITORIES (ACCT_ID, TERR_ID) VALUES (?, ?)");

            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
            	ps2.setString(1, rs1.getString(2)+"%");
            	ps2.setString(2, rs1.getString(3)+"%");
            	ps2.setString(3, rs1.getString(4)+"%");
 
                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                	ps3.setString(1, rs2.getString(1));
                	ps3.setString(2, rs1.getString(1));
			ps3.executeUpdate();
                }
            }
            
            conn.commit();
        } catch (SQLException e) {
			e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        connect();
    }
}

