package edu.mum.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import edu.mum.model.Circle;

@Component
public class CircleDaoImpl {
	@Autowired
	private DataSource dataSource;	
	private JdbcTemplate jdbcTemplate;// = new JdbcTemplate();
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	
	public DataSource getDataSource() {
		return dataSource;
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		//this.dataSource = dataSource;
		this.jdbcTemplate =new JdbcTemplate(dataSource);
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}


	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}


	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


/*	public Circle  getCircle(int circleId){
		Connection conn = null;
		Circle circle = null;
		
		try{
		
		conn = dataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM circle WHERE id=?");
		ps.setInt(1,circleId);
		
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			circle = new Circle(circleId,rs.getString("name"));
		}
		rs.close();
		ps.close();
		
		}catch(Exception e){
			new RuntimeException(e);
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return circle;
	}
	*/
	public int getCircleCount(){
		String sql = "SELECT COUNT(*) FROM circle";
		//jdbcTemplate.setDataSource(getDataSource());
		return jdbcTemplate.queryForInt(sql);
	}
	
	public String gettCircle(int circleId){
		String sql = "SELECT name FROM circle where id=?";
		return jdbcTemplate.queryForObject(sql,new Object[]{circleId},String.class);
	}
	
	public Circle getCircleObject(int circleId){
		String sql="SELECT * FROM circle where id=?";
		return jdbcTemplate.queryForObject(sql,new Object[]{circleId},new CircleMapper());
	}
	public List<Circle> getAllCircle(){
		String sql = "SELECT distinct * FROM circle";
		return jdbcTemplate.query(sql, new CircleMapper());
	}
	
	public void insertCircle(Circle circle){
		String sql = "INSERT INTO circle (id,name) values(?,?)";
		jdbcTemplate.update(sql,new Object[]{circle.getId(),circle.getName()});
	}
	
	public void insertNamedCircle(Circle circle){
		String sql="INSERT INTO circle(id,name) values(:id,:name)";
		SqlParameterSource namedParameters = new MapSqlParameterSource("id",circle.getId())
													.addValue("name",circle.getName());
		namedParameterJdbcTemplate.update(sql,namedParameters);
	}
	
	private static final class CircleMapper implements RowMapper<Circle>{

		@Override
		public Circle mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
			System.out.println("row number: "+rowNumber);
			Circle circle = new Circle();
			circle.setId(resultSet.getInt("id"));
			circle.setName(resultSet.getString("name"));
			return circle;
		}
		
	}
}
