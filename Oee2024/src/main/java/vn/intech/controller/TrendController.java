package vn.intech.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.intech.dto.OeeDto;
import vn.intech.entity.Factors;
import vn.intech.entity.Machines;
import vn.intech.repositories.FactorRepository;
import vn.intech.repositories.MachineRepository;
import vn.intech.service.impl.OeeServiceImpl;
@Controller
@RequestMapping("trending-page")
public class TrendController {
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private FactorRepository factorRepo;
	@Autowired
	private MachineRepository machineRepo;
	
	
	private OeeServiceImpl oeeService = OeeServiceImpl.getInstance();
	
	@GetMapping()
	public String index(Model model) {		
		List<Machines> lstMachine = this.machineRepo.findAll();
		Machines machineRemoved =  lstMachine.remove(8);
		
		model.addAttribute("lstMachine", lstMachine);
	
		request.setAttribute("namePage", "Biểu đồ");
		return "trend";
	}
	
	@PostMapping()
	public ResponseEntity<String> getOeeTrend() throws IOException{
		JSONObject rec = (JSONObject) JSONValue.parse(request.getParameter("param"));
		String trendTypeReceived = rec.get("trendtype").toString();
		String machineIdReceived = rec.get("mid").toString();
		String startStrReceived = rec.get("start").toString();
		String endStrReceived = rec.get("end").toString();
		
		int trendType = -1;
		try {
			trendType = Integer.parseInt(trendTypeReceived);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		
		Timestamp startTimeStamp = Timestamp.valueOf(startStrReceived);
		Timestamp endTimeStamp = Timestamp.valueOf(endStrReceived);	
		
		Date startDate = new Date(startTimeStamp.getTime());
		Date endDate = new Date(endTimeStamp.getTime());
				
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		List<OeeDto> lstOee = this.oeeService.readOeeTrend(startDate, endDate);
		Object[] oeeTrendData;
		
		if(trendType == 0) {
			oeeTrendData = new Object[lstOee.size() + 1];
			Object[] oeeColumns = new Object[5];
			oeeColumns[0] = "Tên máy";
			oeeColumns[1] = "Thời gian dừng không có đơn hàng (Dvt: giờ)";
			oeeColumns[2] = "Thời gian máy làm việc (Dvt: giờ)";
			oeeColumns[3] = "Oee (Dvt: %)";
			oeeColumns[4] = "Oee trung bình (Dvt: %)";
			oeeTrendData[0] = oeeColumns;
			
			double sumOee = 0, avgOee = 0;
			int count = 0;
			for(int j = 0; j < lstOee.size(); j++) {
				sumOee += lstOee.get(j).getOee();
				count++;
			}
			avgOee = sumOee/count;
			
			int i = 1;
			for (OeeDto oee : lstOee) {
				Object[] oeeRows = new Object[5];
				oeeRows[0] = oee.getName();
				oeeRows[1] = oee.getF00()/60; 
				oeeRows[2] = oee.getRunTime()/60;
				oeeRows[3] = oee.getOee()<0 ? 0.0 : oee.getOee();
				oeeRows[4] = avgOee;
				oeeTrendData[i] = oeeRows;
				i++;
			}
			String oeeTrendDataJson = gson.toJson(oeeTrendData);
			out.print(oeeTrendDataJson);
			
		}else if(trendType == 1) {
			List<Factors> lstFactor = factorRepo.findAll();
			oeeTrendData = new Object[12];
			Object[] oeeColumns = new Object[2];
			oeeColumns[0] = "Tên thời gian lãng phí";
			oeeColumns[1] = "Giá trị (Dvt: giờ)";
			oeeTrendData[0] =  oeeColumns;
			
			//Biến tạm lưu tổng tg lãng phí của tất cả các máy CNC
			float f0 = 0, f00 = 0, f1 = 0, f2 = 0, f3 = 0, f4 = 0, f5 = 0, f6 = 0, f7 = 0, f8 = 0, f9 = 0, f10 = 0;
			
			for (OeeDto oee : lstOee) {
				if(oee.getIdMachine().equals(machineIdReceived)) {//biểu đồ thống kê Lãng phí dừng máy theo mid.
					int j = 1;
					for(int i = 0; i < lstFactor.size(); i++) {
						if(lstFactor.get(i).getId().equals("f0")) //bỏ qua tổng tg sản xuất
							continue;
						Object[] oeeRows = new Object[2];
						oeeRows[0] = lstFactor.get(i).getName();
						switch (lstFactor.get(i).getId()) {
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
					break;
				}else if(machineIdReceived.equals("all")){ //biểu đồ thống kê Lãng phí dừng của tất cả các máy CNC.
					int j = 1;
					for(int i = 0; i < lstFactor.size(); i++) {
						if(lstFactor.get(i).getId().equals("f0"))
							continue;
						Object[] oeeRows = new Object[2];
						oeeRows[0] = lstFactor.get(i).getName();
						switch (lstFactor.get(i).getId()) {
//						case "f0": {
//							f0 += oee.getF0()/60;
//							oeeRows[1] = f0;
//							oeeTrendData[j] = oeeRows;
//							break;
//						}
						case "f00": {
							f00 += oee.getF00()/60;
							oeeRows[1] = f00;
							oeeTrendData[j] = oeeRows;
							break;
						}
						case "f1":{
							f1 += oee.getF1()/60;
							oeeRows[1] = f1;
							oeeTrendData[j] = oeeRows;
							break;
						}
						case "f2":{
							f2 += oee.getF2()/60;
							oeeRows[1] = f2;
							oeeTrendData[j] = oeeRows;
							break;
						}
						case "f3":{
							f3 += oee.getF3()/60;
							oeeRows[1] = f3;
							oeeTrendData[j] = oeeRows;
							break;
						}
						case "f4":{
							f4 += oee.getF4()/60;
							oeeRows[1] = f4;
							oeeTrendData[j] = oeeRows;
							break;
						}
						case "f5":{
							f5 += oee.getF5()/60;
							oeeRows[1] = f5;
							oeeTrendData[j] = oeeRows;
							break;
						}
						case "f6":{
							f6 += oee.getF6()/60;
							oeeRows[1] = f6;
							oeeTrendData[j] = oeeRows;
							break;
						}
						case "f7":{
							f7 += oee.getF7()/60;
							oeeRows[1] = f7;
							oeeTrendData[j] = oeeRows;
							break;
						}
						case "f8":{
							f8 += oee.getF8()/60;
							oeeRows[1] = f8;
							oeeTrendData[j] = oeeRows;
							break;
						}
						case "f9":{
							f9 += oee.getF9()/60;
							oeeRows[1] = f9;
							oeeTrendData[j] = oeeRows;
							break;
						}
						case "f10":{
							f10 += oee.getF10()/60;
							oeeRows[1] = f10;
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
			oeeTrendData = new Object[lstOee.size() + 1];
			Object[] oeeColumns = new Object[3];
			oeeColumns[0] = "Tên máy";
			oeeColumns[1] = "Thời gian làm việc (Dvt: giờ)";
			oeeColumns[2] = "Thời gian không làm việc (Dvt: giờ)";
			oeeTrendData[0] = oeeColumns;
			int i = 1;
			for (OeeDto oee : lstOee) {
				Object[] oeeRows = new Object[3];
				oeeRows[0] = oee.getName();
				oeeRows[1] = oee.getRunTime()/60; 
				oeeRows[2] = oee.getDownTime()/60;
				oeeTrendData[i] = oeeRows;
				i++;
			}
			String oeeTrendDataJson = gson.toJson(oeeTrendData);
			out.print(oeeTrendDataJson);
		}	
		return new ResponseEntity<String>(HttpStatus.OK);
	}
}
