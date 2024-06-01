package com.cos.capstone.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.nio.charset.StandardCharsets;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.capstone.coordinate.ConvertGRID;
import com.cos.capstone.coordinate.LatXLngY;
import com.cos.capstone.coordinate.RegionCode;
import com.cos.capstone.enumlist.DBState;
import com.cos.capstone.model.Location;
import com.cos.capstone.model.Schedule;

import com.cos.capstone.repository.LocationRepository;
import com.cos.capstone.repository.RegionCodeRepository;
import com.cos.capstone.repository.ScheduleRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class LocationService {

	@Autowired
	private ScheduleRepository scheduleRepository;

	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private RegionCodeRepository regionCodeRepository;
	
	public static final String API_KEY = "API키 입력";

	public String getRegion(double latitude, double longitude) {
		LatXLngY tmp = ConvertGRID.convertGRID(latitude, longitude);
		List<RegionCode> region = regionCodeRepository.findByXAndY((int) tmp.x, (int) tmp.y);
		if (!region.isEmpty()) {
			String regionName = region.get(0).getRegion();
			String regionCode = "지역 없음";
			switch (regionName) {
			case "강원도": // 철원
				regionCode = "11D10000";
				break;
			case "경기도": // 과천
				regionCode = "11B00000";
				break;
			case "경상북도": // 울진
				regionCode = "11H10000";
				break;
			case "경상남도": // 양산
				regionCode = "11H20000";
				break;
			case "광주광역시": // 광주
				regionCode = "11F20000";
				break;
			case "대구광역시": // 대구
				regionCode = "11H10000";
				break;
			case "대전광역시": // 대전
				regionCode = "11C20000";
				break;
			case "부산광역시": // 부산
				regionCode = "11H20000";
				break;
			case "서울특별시": // 서울
				regionCode = "11B00000";
				break;
			case "세종특별자치시": // 세종
				regionCode = "11C20000";
				break;
			case "울산광역시": // 울산
				regionCode = "11H20000";
				break;
			case "인천광역시": // 인천
				regionCode = "11B00000";
				break;
			case "전라남도": // 함평
				regionCode = "11F20000";
				break;
			case "전라북도": // 전주
				regionCode = "11F10000";
				break;
			case "제주특별자치도":// 제주
				regionCode = "11G00000";
				break;
			case "충청북도": // 충주
				regionCode = "11C10000";
				break;
			case "충청남도": // 서산
				regionCode = "11C20000";
				break;
			default: // 서울
				regionCode = "지역 없음";
			}
			return regionCode;
		} else {
			return "지역 없음";
		}
	}
	
	public static int durationTime(double departLat, double departLng, double destLat, double destLng,
			LocalDateTime departureTime) {
		String url = "https://routes.googleapis.com/directions/v2:computeRoutes";
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// 요청 메서드 설정
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json"); // 바디의 타입 지정
			con.setRequestProperty("X-Goog-Api-Key", API_KEY);
			con.setRequestProperty("X-Goog-FieldMask", "routes.duration");

			// LocalDateTime을 OffsetDateTime으로 변환 (서울 시간대인 +09:00 지정)
			OffsetDateTime offsetDateTime = departureTime.atOffset(ZoneOffset.ofHours(9));

			// OffsetDateTime을 문자열로 변환 (ISO 8601 형식)
			String formattedDateTime = offsetDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
			// POST 데이터
            String jsonString = String.format("{\n" +
                    "    \"origin\": {\n" +
                    "        \"location\": {\n" +
                    "            \"latLng\": {\n" +
                    "                \"latitude\": %.6f,\n" +  // %.6f는 소수점 이하 6자리까지 표시하는 부동 소수점 형식입니다.
                    "                \"longitude\": %.6f\n" +
                    "            }\n" +
                    "        }\n" +
                    "    },\n" +
                    "    \"destination\": {\n" +
                    "        \"location\": {\n" +
                    "            \"latLng\": {\n" +
                    "                \"latitude\": %.6f,\n" +
                    "                \"longitude\": %.6f\n" +
                    "            }\n" +
                    "        }\n" +
                    "    },\n" +
                    "    \"travelMode\": \"TRANSIT\",\n" +
                    "    \"departureTime\": \"%s\",\n" +
                    "    \"languageCode\": \"ko\",\n" +
                    "    \"regionCode\": \"KR\"\n" +
                    "}", departLat, departLng, destLat, destLng,
                    formattedDateTime);
            System.out.println(jsonString);
			// 바디 데이터 전송
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			byte[] jsonBytes = jsonString.getBytes(StandardCharsets.UTF_8); // UTF-8로 인코딩된 바이트 배열 얻기
			wr.write(jsonBytes, 0, jsonBytes.length); // 바이트 배열을 전송
			wr.flush();
			wr.close();

			// 응답 코드 확인 (옵션)
			int responseCode = con.getResponseCode();
			System.out.println("Response Code : " + responseCode);

			// 응답 바디 읽기
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			System.out.println(response.toString());
			ObjectMapper objectMapper = new ObjectMapper();

			// JSON 문자열을 JsonNode로 변환
			JsonNode rootNode = objectMapper.readTree(response.toString());

			// "routes" 배열에서 첫 번째 요소 추출
			JsonNode firstRoute = rootNode.get("routes").get(0);

			// "duration" 값을 추출
			String durationSecond = firstRoute.get("duration").asText();
			int toIntSecond = Integer.parseInt(durationSecond.split("s")[0]);
			return toIntSecond;
		} catch (Exception e) {

			e.printStackTrace();
		}
		return 0;
	}
	
	@Transactional(readOnly = true)
	public List<Location> locationLoad(long scheduleId) {

		Optional<Schedule> optional_schedule = scheduleRepository.findById(scheduleId);
		if (!optional_schedule.isPresent()) {
			return null;
		}
		Schedule schedule = optional_schedule.get();
		if(schedule.getDBState() == DBState.NOT_USE.getValue()) {
			return null;
		}
		List<Location> list = locationRepository.findByScheduleId(schedule);
		return list;
	}
/*
	@Transactional
	public ResultCodeEnum createRoute(Long id, RouteRequestDto routeRequestDto) {

		Optional<Schedule> schedule = scheduleRepository.findById(id);
		if (!schedule.isPresent()) {
			return ResultCodeEnum.FAILURE;
		}

		Location route = new Location();
		route.setScheduleId(schedule.get());
		route.setRouteMode(RouteEnumMode.TRANSIT);
		//////////////출발지
		double departLat = routeRequestDto.getDepartLat();
		double departLng = routeRequestDto.getDepartLng();
		int departYear = routeRequestDto.getDepartYear();
		int departMonth = routeRequestDto.getDepartMonth();
		int departDay = routeRequestDto.getDepartDay();
		int departHour = routeRequestDto.getDepartHour();
		int departMinute = routeRequestDto.getDepartMinute();
		
		route.setDepartYear(departYear);
		route.setDepartMonth(departMonth);
		route.setDepartDay(departDay);
		route.setDepartHour(departHour);
		route.setDepartMinute(departMinute);
		route.setDepartName(routeRequestDto.getDepartName());
		route.setDepartLat(departLat);
		route.setDepartLng(departLng);
		route.setDepartAddress(routeRequestDto.getDepartAddress());
		LatXLngY xy = ConvertGRID.convertGRID(departLat,departLng);
		route.setDepartNx((int) xy.x);
		route.setDepartNy((int) xy.y);
		route.setDepartRegioncode(getRegion(departLat, departLng));
		/////////////출발지
		
		//////////////목적지
		double destLat = routeRequestDto.getDestLat();
		double destLng = routeRequestDto.getDestLng();
		LocalDateTime departTime = LocalDateTime.of(departYear, departMonth, departDay,
				departHour, departMinute, 0);
		long duration = durationTime(departLat, departLng, destLat, destLng, departTime);
		
		LocalDateTime destTime = departTime.plusSeconds(duration);
		System.out.println(duration);
	
		int destYear = destTime.getYear();
		int destMonth = destTime.getMonthValue();
		int destDay = destTime.getDayOfMonth();
		int destHour = destTime.getHour();
		int destMinute = destTime.getMinute();
		
		
		route.setDestYear(destYear);
		route.setDestMonth(destMonth);
		route.setDestDay(destDay);
		route.setDestHour(destHour);
		route.setDestMinute(destMinute);
		route.setDestName(routeRequestDto.getDestName());
		route.setDestLat(routeRequestDto.getDestLat());
		route.setDestLng(routeRequestDto.getDestLng());
		route.setDestAddress(routeRequestDto.getDestAddress());
		LatXLngY xy_ = ConvertGRID.convertGRID(routeRequestDto.getDestLat(),
				routeRequestDto.getDestLng());
		route.setDestNx((int) xy_.x);
		route.setDestNy((int) xy_.y);
		route.setDestRegioncode(getRegion(routeRequestDto.getDestLat(), 
				routeRequestDto.getDestLng()));
		/////////////목적지
		
		routeRepository.save(route);
		return ResultCodeEnum.SUCCESS;
	}
*/
}
