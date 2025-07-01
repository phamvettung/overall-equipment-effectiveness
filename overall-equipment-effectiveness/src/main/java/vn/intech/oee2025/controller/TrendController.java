package vn.intech.oee2025.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.intech.oee2025.dto.OeeDto;
import vn.intech.oee2025.entity.Downtime;
import vn.intech.oee2025.entity.Machine;
import vn.intech.oee2025.repository.DowntimeRepository;
import vn.intech.oee2025.repository.MachineRepository;
import vn.intech.oee2025.service.impl.OeeServiceImpl;

@Controller
@RequestMapping("trend")
public class TrendController {
	@Autowired
	private MachineRepository machineRepo;
	@Autowired
	private DowntimeRepository downtimeRepo;	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	private OeeServiceImpl oeeService = OeeServiceImpl.getInstance();
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		List<Machine> machines = machineRepo.findAll();
		model.addAttribute("machines", machines);
		return "trend";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> getMonthlyOee() throws IOException{
		JSONObject rec = (JSONObject) JSONValue.parse(request.getParameter("param"));
		String trendTypeReceived= rec.get("trendtype").toString();
		String machineIdReceived = rec.get("mid").toString();
		String startDateReceived= rec.get("start").toString();
		String endDateReceived = rec.get("end").toString();
			
		int trendType = -1, machineId = 0;
		try {
			trendType = Integer.parseInt(trendTypeReceived);
			machineId = Integer.parseInt(machineIdReceived);
		}catch(Exception ex) {
			
		}
		
		Timestamp startTimeStamp = Timestamp.valueOf(startDateReceived);
		Timestamp endTimeStamp = Timestamp.valueOf(endDateReceived);	
		Date startDate = new Date(startTimeStamp.getTime());
		Date endDate = new Date(endTimeStamp.getTime());
		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		List<vn.intech.oee2025.dto.OeeDto> oees = this.oeeService.getMonthlyOee(startDate, endDate);
		Object[] oeeTrendData = null;
		
		if(trendType == 0) {
			oeeTrendData = new Object[oees.size() + 1];			
			Object[] oeeColumns = new Object[5];
			
			//create columns
			oeeColumns[0] = "Tên máy";
			oeeColumns[1] = "Thời gian dừng không có đơn hàng (Dvt: giờ)";
			oeeColumns[2] = "Thời gian máy làm việc (Dvt: giờ)";
			oeeColumns[3] = "Oee (Dvt: %)";
			oeeColumns[4] = "Oee trung bình (Dvt: %)";			
			oeeTrendData[0] = oeeColumns;	
			
			//compute the agvOee
			double sumOee = 0, avgOee = 0;
			int count = 0;
			for(int j = 0; j < oees.size(); j++) {
				sumOee += oees.get(j).getOee();
				count++;
			}
			avgOee = sumOee/count;	
			
			//row addition
			int i = 1;
			for (OeeDto oee : oees) {
				Object[] oeeRows = new Object[5];
				oeeRows[0] = oee.getMachineName();
				oeeRows[1] = oee.getF00()/60; 
				oeeRows[2] = oee.getRuntime()/60;
				oeeRows[3] = oee.getOee()<0 ? 0.0 : oee.getOee();
				oeeRows[4] = avgOee;
				oeeTrendData[i] = oeeRows;
				i++;
			}	
			String oeeTrendDataJson = gson.toJson(oeeTrendData);
			out.print(oeeTrendDataJson);
			
		} else if(trendType == 1) {
			List<Downtime> downtimes = downtimeRepo.findAll();
			oeeTrendData = new Object[12];
			Object[] oeeColumns = new Object[2];
			
			oeeColumns[0] = "Tên thời gian lãng phí";
			oeeColumns[1] = "Giá trị (Dvt: giờ)";
			oeeTrendData[0] =  oeeColumns;
			
			float sumf0 = 0, sumf00 = 0, 
				  sumf1 = 0, sumf2 = 0, sumf3 = 0, sumf4 = 0, sumf5 = 0, sumf6 = 0, sumf7 = 0, sumf8 = 0, sumf9 = 0, sumf10 = 0;
			for (OeeDto oee : oees) {
				if(machineId == oee.getMachineId()) {
					int j = 1;
					for(int i = 0; i < downtimes.size(); i++) {
						if(downtimes.get(i).getType().equals("f0")) //skip total production time
							continue;
						Object[] oeeRows = new Object[2];
						oeeRows[0] = downtimes.get(i).getName();
						switch (downtimes.get(i).getType()) {
//						case "f0": {
//							oeeRows[1] = oee.getF0()/60;
//							oeeTrendData[j] = oeeRows;
//							j++;
//							break;
//						}
						case "f00": {
							oeeRows[1] = oee.getF00()/60;
							oeeTrendData[j] = oeeRows;
							j++;
							break;
						}
						case "f1": {
							oeeRows[1] = oee.getF1()/60;
							oeeTrendData[j] = oeeRows;
							j++;
							break;
						}
						case "f2": {
							oeeRows[1] = oee.getF2()/60;
							oeeTrendData[j] = oeeRows;
							j++;
							break;
						}
						case "f3": {
							oeeRows[1] = oee.getF3()/60;
							oeeTrendData[j] = oeeRows;
							j++;
							break;
						}
						case "f4": {
							oeeRows[1] = oee.getF4()/60;
							oeeTrendData[j] = oeeRows;
							j++;
							break;
						}
						case "f5": {
							oeeRows[1] = oee.getF5()/60;
							oeeTrendData[j] = oeeRows;
							j++;
							break;
						}
						case "f6": {
							oeeRows[1] = oee.getF6()/60;
							oeeTrendData[j] = oeeRows;
							j++;
							break;
						}
						case "f7": {
							oeeRows[1] = oee.getF7()/60;
							oeeTrendData[j] = oeeRows;
							j++;
							break;
						}
						case "f8": {
							oeeRows[1] = oee.getF8()/60;
							oeeTrendData[j] = oeeRows;
							j++;
							break;
						}
						case "f9": {
							oeeRows[1] = oee.getF9()/60;
							oeeTrendData[j] = oeeRows;
							j++;
							break;
						}
						case "f10": {
							oeeRows[1] = oee.getF10()/60;
							oeeTrendData[j] = oeeRows;
							j++;
							break;
						}
						default:
							break;
						}
					}
					
				} else if(machineIdReceived.equals("all")) { //downtime all machine
					int j = 1;
					for(int i = 0; i < downtimes.size(); i++) {
						if(downtimes.get(i).getType().equals("f0"))
							continue;
						Object[] oeeRows = new Object[2];
						oeeRows[0] = downtimes.get(i).getName();
						switch (downtimes.get(i).getType()) {
//						case "f0": {
//							f0 += oee.getF0()/60;
//							oeeRows[1] = f0;
//							oeeTrendData[j] = oeeRows;
//							break;
//						}
						case "f00": {
							sumf00 += oee.getF00()/60;
							oeeRows[1] = sumf00;
							oeeTrendData[j] = oeeRows;
							break;
						}
						case "f1":{
							sumf1 += oee.getF1()/60;
							oeeRows[1] = sumf1;
							oeeTrendData[j] = oeeRows;
							break;
						}
						case "f2":{
							sumf2 += oee.getF2()/60;
							oeeRows[1] = sumf2;
							oeeTrendData[j] = oeeRows;
							break;
						}
						case "f3":{
							sumf3 += oee.getF3()/60;
							oeeRows[1] = sumf3;
							oeeTrendData[j] = oeeRows;
							break;
						}
						case "f4":{
							sumf4 += oee.getF4()/60;
							oeeRows[1] = sumf4;
							oeeTrendData[j] = oeeRows;
							break;
						}
						case "f5":{
							sumf5 += oee.getF5()/60;
							oeeRows[1] = sumf5;
							oeeTrendData[j] = oeeRows;
							break;
						}
						case "f6":{
							sumf6 += oee.getF6()/60;
							oeeRows[1] = sumf6;
							oeeTrendData[j] = oeeRows;
							break;
						}
						case "f7":{
							sumf7 += oee.getF7()/60;
							oeeRows[1] = sumf7;
							oeeTrendData[j] = oeeRows;
							break;
						}
						case "f8":{
							sumf8 += oee.getF8()/60;
							oeeRows[1] = sumf8;
							oeeTrendData[j] = oeeRows;
							break;
						}
						case "f9":{
							sumf9 += oee.getF9()/60;
							oeeRows[1] = sumf9;
							oeeTrendData[j] = oeeRows;
							break;
						}
						case "f10":{
							sumf10 += oee.getF10()/60;
							oeeRows[1] = sumf10;
							oeeTrendData[j] = oeeRows;
							break;
						}
						default:
							break;
						}
						j++;
					}			
				}
			}		
			String oeeTrendDataJson = gson.toJson(oeeTrendData);
			out.print(oeeTrendDataJson);
			
		}else if(trendType == 2) {
			oeeTrendData = new Object[oees.size() + 1];
			Object[] oeeColumns = new Object[3];
			oeeColumns[0] = "Tên máy";
			oeeColumns[1] = "Tổng thời gian chạy máy (Dvt: giờ)";
			oeeColumns[2] = "Thời gian dừng máy (Dvt: giờ)";
			oeeTrendData[0] = oeeColumns;
			int i = 1;
			for (OeeDto oee : oees) {
				Object[] oeeRows = new Object[3];
				oeeRows[0] = oee.getMachineName();
				oeeRows[1] = oee.getRuntime()/60; 
				oeeRows[2] = oee.getDowntime()/60;
				oeeTrendData[i] = oeeRows;
				i++;
			}
			String oeeTrendDataJson = gson.toJson(oeeTrendData);
			out.print(oeeTrendDataJson);
		}
				
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
}
