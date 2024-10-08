package shop_Server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import shop_Server.member_VO;

public class joinController {
	Connection conn; // DB와 연결된 객체
	PreparedStatement pstm; // SQL문을 담는 객체
	ResultSet rs; // SQL문 결과를 담는 객체

	// 아이디 중복검사
	public boolean checkId(String id) {
		String query = "SELECT COUNT(id) FROM member WHERE id = ?";
		try {
			conn = DBConnecter.getConnection();
			pstm = conn.prepareStatement(query);
			pstm.setString(1, id);
			rs = pstm.executeQuery();

			rs.next(); // 행
			if (rs.getInt(1)/* 열 */ == 1) {
				return true;
			} 
		} catch (SQLException e) {
			System.out.println("checkId SQL문 오류");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstm != null) {
					pstm.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	// 회원가입
	// MNO NUMBER NOT NULL,
	// ID VARCHAR2(20),
	// PW VARCHAR2(2),
	// MNAME VARCHAR2(300),
	// RANK NUMBER
	public void join(member_VO user) {
		String query = "INSERT INTO member(mno, id, pw, mname, rank)"
				+ "VALUES(member_mno_seq.nextval, ?, ?, ?, 1)";
		try {
			conn = DBConnecter.getConnection();
			pstm = conn.prepareStatement(query);

			pstm.setString(1, user.getId());
			pstm.setString(2, user.getPw());
			pstm.setString(3, user.getMname());

			pstm.executeUpdate();

		} catch (SQLException e) {
			System.out.println("join SQL문 오류");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstm != null) {
					pstm.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}

	// 로그인
	public int login(String id, String pw) {
		String sql = "select * from member where id=? and pw =?";	
		try {
			conn = DBConnecter.getConnection();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, id);
			pstm.setString(2, pw);
			rs = pstm.executeQuery();
			
			member_VO user = new member_VO();
			if(rs.next()) {  // 블랙리스트
				int mno = rs.getInt("mno");
				user.setMname(rs.getString("mname"));
				user.setRank(rs.getInt("rank"));
	            List<Integer> blackList = blackList();
	            if (blackList.contains(mno)) {
					return 2;
				}
				return 1;
			}
			return 3;
			
		} catch (SQLException e) {
			System.out.println("login sql문 오류");
		}finally {
			try {
				if(rs!=null) {
					rs.close();
				}
				if(pstm!=null) {
					pstm.close();
				}
				if(conn!=null) {
					conn.close();
				}
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return 3;
	}
	
	// black list 
	public List<Integer> blackList() {
	    List<Integer> blackList = new ArrayList<>();
	    String sql = "SELECT m.mno FROM member m INNER JOIN black b ON m.mno = b.mno";
	    try {
	        conn = DBConnecter.getConnection();
	        pstm = conn.prepareStatement(sql);
	        rs = pstm.executeQuery();
	        
	        while (rs.next()) {
	            int mno = rs.getInt("mno");
	            blackList.add(mno);
	        }
	    } catch (SQLException e) {
	        System.out.println("blacklist sql문 오류");
	    } finally {
	    	try {
				if(rs!=null) {
					rs.close();
				}
				if(pstm!=null) {
					pstm.close();
				}
				if(conn!=null) {
					conn.close();
				}
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage());
			}
	    }
	    return blackList;
	}

}