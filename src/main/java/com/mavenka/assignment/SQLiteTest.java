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
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SQLiteTest {
    /**
     * Connect to a sample database
     */
    public static void connect() {
		Connection conn = null;
		int i = 0;
        try {
            // db parameters
            String url = "jdbc:sqlite:data/assignment.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
			conn.setAutoCommit(false);
			// get the territories to be assigned
            PreparedStatement ps = conn.prepareStatement("select a.id, b.path, c.code, d.code from territories a, geos b, genders c, home_ownerships d where a.geo_id = b.id and a.gender_id = c.id and a.home_ownership_id = d.id");
            ResultSet rs = ps.executeQuery();
            
            PreparedStatement ps2 = conn.prepareStatement("select a.id from accounts a, geos b, genders c, home_ownerships d where a.geo_id = b.id and b.path like ? and a.gender_id = c.id and c.code like ? and a.home_ownership_id = d.id and d.code like ?");
			PreparedStatement ps3 = conn.prepareStatement("insert into account_territories (ACCT_ID, TERR_ID) values (?, ?)");
        
            // for each territory, get the accounts qualified and write the results back
            while(rs.next() && i++ < 10) {
				String territoryId = rs.getString(1);
				String geoPath = rs.getString(2);
				String gender = rs.getString(3);
				String ho = rs.getString(4);
				
				ps2.setString(1, geoPath+"%");
				ps2.setString(2, gender);
				ps2.setString(3, ho);
				/*ResultSet rs2 = ps2.executeQuery();
				
				while(rs2.next()) {
					//ps3.setString(1, rs2.getString(1));
					//ps3.setString(2, territoryId);
					//ps3.executeUpdate();
				}*/
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
				ex.printStackTrace();
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

