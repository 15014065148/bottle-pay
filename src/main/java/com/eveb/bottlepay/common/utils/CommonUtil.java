package com.eveb.bottlepay.common.utils;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.druid.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonUtil {

	/**
	 * bigDecimal 保留上数位 系统固定2位 不进行四舍五入
	 * 
	 * @param bigDecimal
	 * @return
	 */
	public static BigDecimal adjustScale(BigDecimal bigDecimal) {
		return bigDecimal.setScale(2, BigDecimal.ROUND_DOWN);
	}

	/**
	 * 获取Ip
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ip = null;
		try {
			ip = request.getHeader("x-forwarded-for");
			if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (StringUtils.isEmpty(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		} catch (Exception e) {
		}

		// //使用代理，则获取第一个IP地址
		// if(StringUtils.isEmpty(ip) && ip.length() > 15) {
		// if(ip.indexOf(",") > 0) {
		// ip = ip.substring(0, ip.indexOf(","));
		// }
		// }

		return ip;
	}

	public static String localServerIp() {
		String host = null;
		try {
			host = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// log.error("get server host Exception e:", e);
		}
		return host;
	}
	
	public static String  requestUrl(String weburl)
	{
		java.net.URL url = null;
		try {
			url = new java.net.URL(weburl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		String host = url.getHost();// 获取主机名
		return host;

	}

	/**
	 * 去除空行和空格
	 * 
	 * @param item
	 * @return
	 */
	public static String remKong(String item) {

		return item != null ? item.replaceAll("\\n", "").replaceAll("\\s", "") : null;
	}

	public static String genRandomNum(int min,int max){  
	      int  maxNum = 36;  
	      int i;  
	      int count = 0;  
	      char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',  
	        'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',  
	        'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };      
	      StringBuffer pwd = new StringBuffer("");  
	      Random r = new Random(); 
	      int s= r.nextInt(max)%(max-min+1) + min;
	      while(count < s){  
	       i = Math.abs(r.nextInt(maxNum));     
	       if (i >= 0 && i < str.length) {  
	        pwd.append(str[i]);
	        count ++;  
	       }  
	      }  
	      return pwd.toString();  
	    }
	public static String genRandom(int min,int max){  
	      int  maxNum = 36;  
	      int i;  
	      int count = 0;  
	      char[] str = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };      
	      StringBuffer pwd = new StringBuffer("");  
	      Random r = new Random(); 
	      int s= r.nextInt(max)%(max-min+1) + min;
	      while(count < s){  
	       i = Math.abs(r.nextInt(maxNum));     
	       if (i >= 0 && i < str.length) {  
	        pwd.append(str[i]);
	        count ++;  
	       }  
	      }  
	      return pwd.toString();  
	    }

	public static String genRandomNum(int number){
		return genRandomNum(number, number);
	}	
	public static String getRandomNum() {
		return genRandomNum(12);
	}
	public static String getRandomCode() {
		return genRandom(5,5);
	}

	/*public static String getSchemaName(){
		if(RequestContextHolder.getRequestAttributes() != null) {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			if (request != null) {
				String siteCode = AESUtil.decrypt(request.getHeader(SystemConstants.STOKEN));
				if (StringUtils.isEmpty(siteCode)) {
					log.info("无法获取siteCode {}",request.getHeader(SystemConstants.STOKEN));
					//TODO 删除
					return  "test";
				}
				//配置新站点的时候增加判断，防止获取schema为空
				if (TCpSiteService.siteCode.get(siteCode) == null) {
					SpringContextUtils.getBean(SystemConstants.T_CP_SITE_SERVICE, TCpSiteService.class).initSchemaName();
					siteCode = AESUtil.decrypt(request.getHeader(SystemConstants.STOKEN));
					if (TCpSiteService.siteCode.get(siteCode) == null) {
						log.error("SToken wrong,SToken =" + request.getHeader("SToken"), "schemaName = " + siteCode);
						throw new RRException("SToken wrong ,siteCode  中不存在次schema对应");
					}
				}
				return TCpSiteService.siteCode.get(siteCode);

			}
		}
		return null;
	}*/

	/**
	 * 根据URL获取一级 domain
	 * @param url
	 * @return
	 */
	public static String getDomainForUrl(String url) {
		String domainUrl = null;
		if (url == null) {
			return null;
		} else if (url.contains("localhost")) {
			return "localhost";
		}else {
			Pattern p = Pattern.compile("((?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv|hk|co))",Pattern.CASE_INSENSITIVE);
			Matcher matcher = p.matcher(url);
			while(matcher.find()){
				domainUrl = matcher.group();
			}
			//增加对ip地址的支持
			if(!matcher.find()) {
				p = Pattern.compile("((?:(?:25[0-5]|2[0-4]\\d|(?:1\\d{2}|[1-9]?\\d))\\.){3}(?:25[0-5]|2[0-4]\\d|(?:1\\d{2}|[1-9]?\\d)))", Pattern.CASE_INSENSITIVE);
				matcher = p.matcher(url);
				while (matcher.find()) {

					domainUrl = requestUrl(url.startsWith("http://")?url:"http://"+url);
				}
			}
			return domainUrl;
		}
	}
}
