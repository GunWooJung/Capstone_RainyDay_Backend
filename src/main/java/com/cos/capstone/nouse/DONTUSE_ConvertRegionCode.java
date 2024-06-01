package com.cos.capstone.nouse;
/*
 * package com.cos.capstone.tool;
 * 
 * import java.io.FileReader; import java.nio.charset.Charset; import
 * java.util.List; import com.opencsv.bean.CsvToBeanBuilder;
 * 
 * public class DONTUSE_ConvertRegionCode {
 * 
 * public static String ConvertToRegionCode(Boolean isInServer, double findX,
 * double findY) { String resultCode = "11B10101"; // 기본 값은 서울로 지정 String
 * resultRegion = "서울특별시"; String csvFile =
 * "C:\\workspacespring\\RainyDayBackEnd\\src\\main\\resources\\region.csv";
 * if(isInServer == true) { csvFile = "/home/ubuntu/region.csv"; }
 * Charset.forName("UTF-8");
 * 
 * try { List<RegionCode> regions = new CsvToBeanBuilder<RegionCode>(new
 * FileReader(csvFile)) .withType(RegionCode.class).build().parse(); for
 * (RegionCode findregion : regions) { if(((int)findX) ==
 * Integer.parseInt(findregion.x) && ((int)findY) ==
 * Integer.parseInt(findregion.y)) { resultRegion = findregion.region; break; }
 * 
 * } } catch(Exception e) { e.printStackTrace(); } switch(resultRegion) { case
 * "강원도": // 철원 resultCode = "11D10101"; break; case "경기도": //과천 resultCode =
 * "11B10102"; break; case "경상북도": //울진 resultCode = "11H10101"; break; case
 * "경상남도": //양산 resultCode = "11H20102"; break; case "광주광역시": //광주 resultCode =
 * "11F20501"; break; case "대구광역시": //대구 resultCode = "11H10701"; break; case
 * "대전광역시": //대전 resultCode = "11C20401"; break; case "부산광역시": //부산 resultCode =
 * "11H20201"; break; case "서울특별시": //서울 resultCode = "11B10101"; break; case
 * "세종특별자치시": //세종 resultCode = "11C20404"; break; case "울산광역시": //울산 resultCode
 * = "11H20101"; break; case "인천광역시": //인천 resultCode = "11B20201"; break; case
 * "전라남도": //함평 resultCode = "21F20101"; break; case "전라북도": //전주 resultCode =
 * "11F10201"; break; case "제주특별자치도"://제주 resultCode = "11G00201"; break; case
 * "충청북도": //충주 resultCode = "11C10101"; break; case "충청남도": //서산 resultCode =
 * "11C20101"; break; default: //서울 resultCode = "11B10101"; } return
 * resultCode; }
 * 
 * public static String ConvertToRegion(Boolean isInServer, double findX, double
 * findY) { String resultCode = "11B10101"; // 기본 값은 서울로 지정 String resultRegion
 * = "서울특별시"; String csvFile =
 * "C:\\workspacespring\\RainyDayBackEnd\\src\\main\\resources\\region.csv";
 * if(isInServer == true) { csvFile = "/home/ubuntu/region.csv"; }
 * Charset.forName("UTF-8");
 * 
 * try { List<RegionCode> regions = new CsvToBeanBuilder<RegionCode>(new
 * FileReader(csvFile)) .withType(RegionCode.class).build().parse(); for
 * (RegionCode findregion : regions) { if(((int)findX) ==
 * Integer.parseInt(findregion.x) && ((int)findY) ==
 * Integer.parseInt(findregion.y)) { resultRegion = findregion.region; break; }
 * 
 * } } catch(Exception e) { e.printStackTrace(); } return resultRegion; } }
 */