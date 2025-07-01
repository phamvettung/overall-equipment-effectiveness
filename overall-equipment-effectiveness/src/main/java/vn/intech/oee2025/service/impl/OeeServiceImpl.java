package vn.intech.oee2025.service.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import vn.intech.oee2025.dto.MachineDataDto;
import vn.intech.oee2025.dto.OeeDto;
import vn.intech.oee2025.service.OeeService;

public class OeeServiceImpl implements OeeService{

	DriverManagerDataSource dataSource = null;
	private static OeeServiceImpl instance;
	private SimpleJdbcCall prDailyOee, prMonthlyOee, prMachineDataCollection;
	
	private OeeServiceImpl() {	
				
		dataSource = new DriverManagerDataSource();	
		dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		dataSource.setUrl("jdbc:sqlserver://DESKTOP-CPDLD2L;databaseName= OEE2025;trustServerCertificate=true");
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
		
	private void setDataSource(DataSource dataSource) {
		this.prDailyOee = new SimpleJdbcCall(dataSource)
				.withProcedureName("prDailyOee")
				.returningResultSet("DailyOee", new RowMapper<OeeDto>() {
					@Override
					public OeeDto mapRow(ResultSet rs, int rowNum) throws SQLException {
						OeeDto oee = new OeeDto();
						oee.setMachineName(rs.getString("machine_name"));
						oee.setMachineId(rs.getInt("machine_id"));
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
						oee.setA(rs.getFloat("a"));
						oee.setP(rs.getFloat("p"));
						oee.setQ(rs.getFloat("q"));
						oee.setOee(rs.getFloat("oee"));
						oee.setDowntime(rs.getFloat("downtime"));
						oee.setRuntime(rs.getFloat("runtime"));
						return oee;
				}
			});
		
		this.prMonthlyOee = new SimpleJdbcCall(dataSource)
				.withProcedureName("prMonthlyOee")
				.returningResultSet("MonthlyOee", new RowMapper<OeeDto>() {
					@Override
					public OeeDto mapRow(ResultSet rs, int rowNum) throws SQLException {
						OeeDto oee = new OeeDto();
						oee.setMachineName(rs.getString("machine_name"));
						oee.setMachineId(rs.getInt("machine_id"));
						oee.setDate(Date.valueOf(LocalDate.MIN)); //default value
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
						oee.setA(rs.getFloat("a"));
						oee.setP(rs.getFloat("p"));
						oee.setQ(rs.getFloat("q"));
						oee.setOee(rs.getFloat("oee"));
						oee.setDowntime(rs.getFloat("downtime"));
						oee.setRuntime(rs.getFloat("runtime"));
						return oee;
				}
			});
		
		this.prMachineDataCollection = new SimpleJdbcCall(dataSource)
				.withProcedureName("prMachineDataCollection")
				.returningResultSet("MachineDataCollection", new RowMapper<MachineDataDto>() {
					@Override
					public MachineDataDto mapRow(ResultSet rs, int rowNum) throws SQLException {
						MachineDataDto mdc = new MachineDataDto();
						mdc.setMachineName(rs.getString("machine_name"));
						mdc.setMachineId(rs.getInt("machine_id"));
						mdc.setQ200(rs.getFloat("Q200"));
						mdc.setQ201(rs.getFloat("Q201"));
						mdc.setQ300(rs.getFloat("Q300"));
						mdc.setQ301(rs.getFloat("Q301"));
						mdc.setQ500(rs.getFloat("Q500"));
						return mdc;
				}
			});
	}
	
	@Override
	public List<OeeDto> getDailyOee(String machineId, Date start, Date end) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("machineId", machineId);
		params.addValue("start", start);
		params.addValue("end", end);
		Map<String, Object> out = prDailyOee.execute(params);
		List<OeeDto> oees = (List<OeeDto>) out.get("DailyOee");
		return oees;
	}

	@Override
	public List<OeeDto> getMonthlyOee(Date start, Date end) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("start", start);
		params.addValue("end", end);
		Map<String, Object> out = prMonthlyOee.execute(params);
		List<OeeDto> oees = (List<OeeDto>) out.get("MonthlyOee");
		return oees;
	}

	@Override
	public List<MachineDataDto> getMDC(Date start, Date end) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("start", start);
		params.addValue("end", end);
		Map<String, Object> out = prMachineDataCollection.execute(params);
		List<MachineDataDto> mdc = (List<MachineDataDto>) out.get("MachineDataCollection");
		return mdc;
	}
	
}
