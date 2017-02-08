package com.mavenka.assignment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Set;

public class AssignmentInMem {
	
	public static class TreeNode {
		public String data;
		public Set<TreeNode> children;
		
		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			TreeNode node = (TreeNode) o;
			if (data != null) return data.equals(node.data);
			else return node.data == null;
    }

		@Override
		public int hashCode() {
			if (data != null) return data.hashCode();
			else return 0;
		}
	}
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
            PreparedStatement ps = conn.prepareStatement("select 'DIM:GEO/' || GEO_PATH || '/DIM:GNDR/' || GENDER_CODE || '/DIM:HO/' || HOME_OWNERSHIP_CODE || '/DIM:LEAF/' || TERR_ID from terr_denorm");
            ResultSet rs = ps.executeQuery();
               
            // for each territory
            while(rs.next() && i++ < 10) {
				String[] sa = rs.getString(1).split("/");
				
			}            
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
