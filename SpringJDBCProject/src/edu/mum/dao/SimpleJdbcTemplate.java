package edu.mum.dao;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class SimpleJdbcTemplate extends SimpleJdbcDaoSupport {
	public int circleCount(){
		String sql="SELECT COUNT(*) FROM circle";
		return this.getJdbcTemplate().queryForInt(sql);
	}
}
