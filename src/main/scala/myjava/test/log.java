package myjava.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class log {

	// 所有信息主集合
	private static List<String> totalInfo = new ArrayList<>();
	// 按天处理的信息集合
	private static Map<String, List<String>> dayInfoMap = new HashMap<String, List<String>>();
	// 每一次独立访问的集合
	private static List<List<String>>  accessTotal = new ArrayList<List<String>>();
	// 代表每一次独立访问
	private static List<String> accessList = new ArrayList<>();
	// 代表不属于独立访问的其它信息
	private static List<String> noAccessList = new ArrayList<>();
	
	/**
	 * 将所有信息处理存储为以天为单位的集合中，并将集合以日期为key可以放入map
	 */
	public static void dealTotalInfo(List<String> totalInfo){
		Set s=new HashSet();
		for(String info : totalInfo){
			String substring = info.substring(info.indexOf("[")+1, info.indexOf("[")+3);
			if(s.add(substring)){ // 插入成功说明为不重复的日期，创建新的list，以日期为key。
				List<String> list = new ArrayList<String>();
				list.add(info);
				dayInfoMap.put(substring, list);
			}else{ // 已存在的日期的集合
				List<String> list = dayInfoMap.get(substring);
				list.add(info);
			}
		}
	}
	
	/**
	 * 处理以天为单位的map集合，比较统计独立访问
	 * @throws Exception 
	 */
	public static void accessTimes() throws Exception{
		for(List<String> dateList : dayInfoMap.values()){ // 循环30、31天的map
			for(int i = 0; i<dateList.size()-1; i++){ // 选择排序，比较一天中第一条信息
				
				for(List<String> accessList : accessTotal){ // 如果每天的信息中，已经筛选到独立访问的集合中，则跳过
					for(String accessTime : accessList){
						if(getLineDate(accessTime) == getLineDate(dateList.get(i))){
							continue;
						}
					}
				}
				
				for(int j = i+1; j<dateList.size(); j++){
					if(Math.abs(getLineDate(dateList.get(i)) - getLineDate(dateList.get(j))) < 1800000){
						accessList.add(dateList.get(j));
					}else{
						noAccessList.add(dateList.get(j));
					}
					accessList.add(dateList.get(i));
				}
				recursionAccessTimes(accessList,noAccessList);
				
			}
			
		}
	}
	
	/**
	 * 获得代表一次独立访问的集合
	 * @throws Exception 
	 */
	public static void recursionAccessTimes(List<String> accessList,List<String> noAccessList) throws Exception{
		for(int i = 0; i<accessList.size(); i++){
			
			for(int j = 0; j<noAccessList.size(); j++){
				if(Math.abs(getLineDate(accessList.get(i)) - getLineDate(noAccessList.get(j))) < 1800000){
					accessList.add(noAccessList.get(j));
				}
			}
		}
		accessTotal.add(accessList);
	}
	
	/**
	 * 摘出行中时间信息的插件
	 * @throws Exception 
	 */
	public static Long getLineDate(String dayInfo) throws Exception{
		dayInfo = dayInfo.substring(dayInfo.length()-8,dayInfo.length()); // 获取日期字符串
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		long time = sdf.parse(dayInfo).getTime(); // 日期字符串转时间，并获取与1970的毫秒值
		return time;
	}
	
	/**
	 * 按行读取文件，存入主集合
	 */
	public static void readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
            	totalInfo.add(tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }
	
	public static void main(String[] args) throws Exception {
		readFileByLines("C:/Users/Administrator/Desktop/2013-05-30.log");
		dealTotalInfo(totalInfo);
		accessTimes();
		System.out.println("独立访问次数："+accessTotal.size());
		
		for(List<String> access : accessTotal){

			for(int i = 0; i<access.size()-1; i++){ // 对每一次的独立访问的集合进行冒泡排序
				for(int j = 0; j<access.size()-1-i; j++){
					if((getLineDate(access.get(j)) - getLineDate(access.get(j+1)))>0){
						access.set(j, access.get(j+1));
						access.set(j+1, access.get(j));
					}
				}
			}
			String first = access.get(0);
			String last = access.get(access.size()-1);
			System.out.println("起始请求时间:"+first.substring(20, first.length())+"    "
					+ "结束请求时间:"+last.substring(20, last.length())+"    "
					+ "停留时长"+(getLineDate(last)-getLineDate(first)));
		}
	}
}
