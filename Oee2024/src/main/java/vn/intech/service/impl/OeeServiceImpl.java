package vn.intech.service.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import vn.intech.dto.OeeDto;
import vn.intech.service.OeeService;

public class OeeServiceImpl implements OeeService{

	DriverManagerDataSource dataSource = null;
	private static OeeServiceImpl instance;
	private OeeServiceImpl() {
		dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		dataSource.setUrl("jdbc:sqlserver://QC-03;databaseName= OEE;trustServerCertificate=true");
		dataSource.setUsername("sa");
		dataSource.setPassword("123456");
		setDataSource(dataSource);
	}
	public static OeeServiceImpl getInstance() {
		if(instance == null) {
			instance = new OeeServiceImpl();
		}
		return instance;
	}
	
	private SimpleJdbcCall procReadOee, procReadOeeTrend;

	private void setDataSource(DataSource dataSource) {
		
		this.procReadOee = new SimpleJdbcCall(dataSource)
				.withProcedureName("prGetOeeTable")
				.returningResultSet("OeeTable", new RowMapper<OeeDto>() {

					@Override
					public OeeDto mapRow(ResultSet rs, int rowNum) throws SQLException {
						OeeDto oee = new OeeDto();
						oee.setName(rs.getString("name"));
						oee.setIdMachine(rs.getString("idMachine"));
						oee.setDate(rs.getDate("date"));
						oee.setF0(rs.getFloat("f0"));
						oee.setF00(rs.getFloat("f00"));
						oee.setF1(rs.getFloat("f1"));
						oee.setF2(rs.getFloat("f2"));
						oee.setF3(rs.getFloat("f3"));
						oee.setF4(rs.getFloat("f4"));
						oee.setF5(rs.getFloat("f5"));
						oee.setF6(rs.getFloat("f6"));
						oee.setF7(rs.getFloat("f7"));
						oee.setF8(rs.getFloat("f8"));
						oee.setF9(rs.getFloat("f9"));
						oee.setF10(rs.getFloat("f10"));
						oee.setDin(0); //default
						oee.setA(rs.getFloat("A"));
						oee.setP(rs.getFloat("P"));
						oee.setQ(rs.getFloat("Q"));
						oee.setOee(rs.getFloat("OEE"));
						oee.setDownTime(rs.getFloat("DownTime"));
						oee.setRunTime(rs.getFloat("RunTime"));
						return oee;
					}
				});
		
		this.procReadOeeTrend = new SimpleJdbcCall(dataSource)
				.withProcedureName("prGetOeeTrend")
				.returningResultSet("OeeTrend", new RowMapper<OeeDto>() {

					@Override
					public OeeDto mapRow(ResultSet rs, int rowNum) throws SQLException {
						OeeDto oee = new OeeDto();
						oee.setName(rs.getString("name"));
						oee.setIdMachine(rs.getString("idMachine"));
						oee.setDate(Date.valueOf(LocalDate.MIN)); // default
						oee.setF0(rs.getFloat("f0"));	
						oee.setF00(rs.getFloat("f00"));
						oee.setF1(rs.getFloat("f1"));
						oee.setF2(rs.getFloat("f2"));
						oee.setF3(rs.getFloat("f3"));
						oee.setF4(rs.getFloat("f4"));
						oee.setF5(rs.getFloat("f5"));
						oee.setF6(rs.getFloat("f6"));
						oee.setF7(rs.getFloat("f7"));
						oee.setF8(rs.getFloat("f8"));
						oee.setF9(rs.getFloat("f9"));
						oee.setF10(rs.getFloat("f10"));
						oee.setDin(rs.getInt("din"));
						oee.setA(rs.getFloat("A"));
						oee.setP(rs.getFloat("P"));
						oee.setQ(rs.getFloat("Q"));
						oee.setOee(rs.getFloat("OEE"));
						oee.setDownTime(rs.getFloat("DownTime"));
						oee.setRunTime(rs.getFloat("RunTime"));
						return oee;
					}
				});
	}

	@Override
	public List<OeeDto> readOeeTable(String idMachine, Date start, Date end) {		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("idMachine", idMachine);
		params.addValue("start", start);
		params.addValue("end", end);
		Map<String, Object> out = procReadOee.execute(params);
		List<OeeDto> listOee = (List<OeeDto>) out.get("OeeTable");
		return listOee;
	}
	@Override
	public List<OeeDto> readOeeTrend(Date start, Date end) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("start", start);
		params.addValue("end", end);
		Map<String, Object> out = procReadOeeTrend.execute(params);
		List<OeeDto> listOee = (List<OeeDto>) out.get("OeeTrend");
		return listOee;
	}	
	
}
