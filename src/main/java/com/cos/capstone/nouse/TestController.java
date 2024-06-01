package com.cos.capstone.nouse;


import java.io.FileReader;
import java.nio.charset.Charset;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cos.capstone.coordinate.ConvertGRID;
import com.cos.capstone.coordinate.LatXLngY;
import com.cos.capstone.coordinate.RegionCode;
import com.cos.capstone.repository.RegionCodeRepository;

import com.opencsv.bean.CsvToBeanBuilder;

@RestController
public class TestController {

	@Autowired
	private RegionCodeRepository regionCodeRepository;
	
	@GetMapping("/")
	public String forTest() {
		System.out.println("hi");
		return "<h1>캡스톤 시작</h1>";
	}

    @GetMapping("/region")
    public String region(@RequestParam("lat") double latitude, 
    		@RequestParam("lng") double longitude) {
		LatXLngY tmp = ConvertGRID.convertGRID(latitude,  longitude);
		List<RegionCode> region = regionCodeRepository.findByXAndY((int)tmp.x,(int)tmp.y);
		if(!region.isEmpty()) {
			String regionName = region.get(0).getRegion();
			String regionCode = "11B10101";
			switch(regionName) {
			  case "강원도":	// 철원
				  regionCode = "11D10101";
	            break;
			  case "경기도": //과천
				  regionCode = "11B10102";
	            break;
			  case "경상북도":		//울진
				  regionCode = "11H10101";
	            break;
			  case "경상남도":		//양산
				  regionCode = "11H20102";
	            break;
			  case "광주광역시":	//광주
				  regionCode = "11F20501";
	            break;
			  case "대구광역시":	//대구
				  regionCode = "11H10701";
	            break;
			  case "대전광역시":	//대전
				  regionCode = "11C20401";
	            break;
			  case "부산광역시":	//부산
				  regionCode = "11H20201";
	            break;
			  case "서울특별시":	//서울
				  regionCode = "11B10101";
	            break;
			  case "세종특별자치시":	//세종
				  regionCode = "11C20404";
	            break;
			  case "울산광역시":	//울산
				  regionCode = "11H20101";
	            break;
			  case "인천광역시":	//인천
				  regionCode = "11B20201";
	            break;
			  case "전라남도":		//함평
				  regionCode = "21F20101";
	            break;
			  case "전라북도":		//전주
				  regionCode = "11F10201";
	            break;
			  case "제주특별자치도"://제주
				  regionCode = "11G00201";
	            break;
			  case "충청북도":		//충주
				  regionCode = "11C10101";
	            break;
			  case "충청남도":		//서산
				  regionCode = "11C20101";
	            break;
	        default:			//서울
	        	regionCode = "11B10101";
			}
			return "<h1> nx : " +(int)tmp.x+" , ny : "+(int)tmp.y+" , 지역 : "+regionName+", 지역코드 : "+regionCode+"<h1>";
		}
		else {
			return "<h1> 지역을 찾을 수 없습니다.. <h1>";
		}
    }
	
    @GetMapping("/region/enroll")
    public void regionEnroll() {
    	Boolean isInServer = false;
    	String csvFile = "C:\\workspacespring\\RainyDayBackEnd\\src\\main\\resources\\region.csv";
		if(isInServer == true) {
			csvFile = "/home/ubuntu/region.csv";
		}
		Charset.forName("UTF-8");

		try {
			List<RegionCode> regions = new CsvToBeanBuilder<RegionCode>(new FileReader(csvFile))
					.withType(RegionCode.class).build().parse();
				regionCodeRepository.saveAll(regions);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
    }
}
