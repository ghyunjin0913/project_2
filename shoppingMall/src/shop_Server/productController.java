package shop_Server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import shop_Server.product_VO;
import shop_Server.member_VO;

public class productController {
	Connection conn; // DB와 연결된 객체
	PreparedStatement pstm; // SQL문을 담는 객체
	ResultSet rs; // SQL문 결과를 담는 객체

	public List<product_VO> productList(){  // 상품 리스트
		List<product_VO> productList = new ArrayList<>();
		String sql = "SELECT * FROM product";
	    try {
	        conn = DBConnecter.getConnection();
	        pstm = conn.prepareStatement(sql);
	        rs = pstm.executeQuery();
	        
	        while(rs.next()) {
	        	product_VO product = new product_VO();
	        	product.setPno(rs.getInt("pno"));
	            product.setPname(rs.getString("pname"));
	            product.setPrice(rs.getInt("price"));
	            productList.add(product);
	        }
	        
	    } catch (SQLException e) {
	        System.out.println("product sql문 오류");
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
		return productList;
	}
	
	public List<discount_VO> discountList(){
		String sql = "SELECT * FROM discount";
		List<discount_VO> disList = new ArrayList<>();
	    try {
	        conn = DBConnecter.getConnection();
	        pstm = conn.prepareStatement(sql);
	        rs = pstm.executeQuery();

	        while(rs.next()) {
	        	discount_VO dis = new discount_VO();
	        	dis = new discount_VO();
	        	dis.setRank(rs.getInt("rank"));
	            dis.setDc(rs.getFloat("dc"));
	            disList.add(dis);
	        }
	        
	    } catch (SQLException e) {
	        System.out.println("discount sql문 오류");
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
		return disList;
	}
	
	public int dis_pro(int price) {
		int total = price;
		for (discount_VO dis : discountList()) {
			int rank = dis.getRank();
			float dc = dis.getDc();
			
			if(rank == member_VO.getRank()) {
				total = (int)(total * dc);
			}
		}
		return total;
	}
	
	public product_VO productSearch(String product) {
		for (product_VO word : productList()) {
            if (word.getPname().equals(product)) {
                return word;
            }
        }
		return null;
	}
}
