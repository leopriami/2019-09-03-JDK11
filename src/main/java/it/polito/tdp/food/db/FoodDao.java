package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Edge;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDao {
	public List<Edge> listAllFoods(Integer C){
		String sql = "select lower(p1.portion_display_name) as port1, lower(p2.portion_display_name) as port2, count(*) as peso from portion as p1, portion as p2 where p1.food_code = p2.food_code and p1.portion_display_name < p2.portion_display_name and p1.calories < ? and p2.calories < ? group by p1.portion_display_name, p2.portion_display_name" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Edge> list = new ArrayList<>() ;
			
			st.setInt(1, C);
			st.setInt(2, C);
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Edge(res.getString("port1"), res.getString("port2"), res.getInt("peso")));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<String> listAllPortions(Integer C){
		String sql = "select distinct lower(portion_display_name) as port from portion where calories < ? order by port ASC" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<String> list = new ArrayList<>() ;
			
			st.setInt(1, C);
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(res.getString("port"));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	

}
