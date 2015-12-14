package com.hanains.emaillist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanains.emaillist.vo.EmailListVo;

@Repository
public class EmailListDao {

	public  List<EmailListVo> getList(){
		
		List<EmailListVo> list=new ArrayList<EmailListVo>();
		
		Connection connection=null;
		Statement stmt=null;
		ResultSet rs=null;
		
		
		try {
			// 1. 드라이버 로딩(클래스 동적 로딩)
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2. db 연결
			String dbUrl="jdbc:oracle:thin:@localhost:1522:xe";
			connection=DriverManager.getConnection(dbUrl, "webdb","webdb");
			
			// 3. statement 생성
			stmt=connection.createStatement();
			
			// 4. SQL 실행
			String sql="select no, first_name, last_name, email from email_list order by no desc";
			rs=stmt.executeQuery(sql);
			
			// 5. 결과 가져오기
			while(rs.next()){
				Long no=rs.getLong(1);
				String firstName=rs.getString(2);
				String lastName=rs.getString(3);
				String email=rs.getString(4);
				
				EmailListVo vo=new EmailListVo();
				vo.setNo(no);
				vo.setFirstName(firstName);
				vo.setLastName(lastName);
				vo.setEmail(email);
				
				list.add(vo);
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패 - "+e);
		} catch (SQLException e) {
			System.out.println("에러 - "+e);
		} finally {
			try {
				
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
				if (connection != null) connection.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	public void insert(EmailListVo vo){
		
		Connection connection=null;
		PreparedStatement pstmt=null;
		
		try {
			// 1. 드라이버 로딩(클래스 동적 로딩)
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2. db 연결
			String dbUrl="jdbc:oracle:thin:@localhost:1522:xe";
			connection=DriverManager.getConnection(dbUrl, "webdb","webdb");
			
			// 3. statement 준비
			String sql="insert into email_list values(email_list_no_seq.nextval, ?,?,?)";
			pstmt=connection.prepareStatement(sql);
			
			// 4. binding
			pstmt.setString(1, vo.getFirstName());
			pstmt.setString(2, vo.getLastName());
			pstmt.setString(3, vo.getEmail());
			
			// 5. SQL 실행
			pstmt.executeUpdate();
			
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패 - "+e);
		} catch (SQLException e) {
			System.out.println("에러 - "+e);
		} finally {
			try {
				
				if (pstmt != null) pstmt.close();
				if (connection != null) connection.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
