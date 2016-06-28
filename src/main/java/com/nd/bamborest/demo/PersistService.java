package com.nd.bamborest.demo;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import java.sql.*;



public class PersistService {

    private static final String JDBC_DRIVER_NAME = "com.mysql.jdbc.Driver";

    public static String initDataSource() {
    	Properties props = null;
		try {
			props = ResourceUtils.getResourceAsProperties("jdbc.properties");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}    
        String systemtime = "";
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try{
        	Class.forName(JDBC_DRIVER_NAME);
        	con = DriverManager.getConnection(ensurePropValueNotNull(props.getProperty("db.url")), ensurePropValueNotNull(props.getProperty("db.user")), ensurePropValueNotNull(props.getProperty("db.password")));
        	stmt = con.createStatement();                   
        	String sqlstr = "select now() as Systemtime";
            rs = stmt.executeQuery(sqlstr);
            ResultSetMetaData rsmd = rs.getMetaData();
            int j = 0;
            j = rsmd.getColumnCount();          
            while(rs.next()){
                for(int i=0;i<j;i++){
                	systemtime= rs.getString("Systemtime");
                }
               
            }
        }catch(Exception e2){
        	System.out.println("数据库存在异常！");
        	System.out.println(e2.toString());
        }finally{
        	try{
                if(rs != null) rs.close();
                if(stmt != null) stmt.close();
                if(con != null) con.close();
               }catch(SQLException e){
            	   System.out.println(e.toString());
               }            
       }
        return systemtime;
    }
    
    private static String ensurePropValueNotNull(String srcValue) {
        if (srcValue == null) {
            throw new IllegalArgumentException("property is illegal:" + srcValue);
        }

        return srcValue;
    }

}
