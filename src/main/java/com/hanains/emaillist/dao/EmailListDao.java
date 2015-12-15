package com.hanains.emaillist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanains.emaillist.vo.EmailListVo;

@Repository
public class EmailListDao {
	
	@Autowired
	private SqlSession sqlSession;

	public  List<EmailListVo> getList(){
		List<EmailListVo> list=sqlSession.selectList("emaillist.list");
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
