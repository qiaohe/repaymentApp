package com.huayuan.common;

import com.huayuan.web.dto.UserDto;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.util.regex.Pattern;

/**
 * Created by Richard on 14-8-22.
 */
public class ResourceFilter implements Filter {

    private String allowIps = null;

    private String header = "x-forwarded-for";

    private String loginPath = "/login.html";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        allowIps = filterConfig.getInitParameter("allowIps");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        // Clear cache on client-side
        httpServletResponse.setHeader("Cache-Control", "no-cache");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", -1);

        String requestURL =  httpServletRequest.getRequestURL().toString();
        if(!isWeixinRequest(requestURL) && (requestURL.endsWith(".html") || requestURL.endsWith(".htm"))) {
            String remoteIp = getIpAddr(httpServletRequest);
            if(matchIp(allowIps,remoteIp)) {
                if(requestURL.endsWith(loginPath)) {
                    chain.doFilter(request, response);
                } else {
                    Object userObj = httpServletRequest.getSession().getAttribute(CommonDef.LOGIN_USER);
                    UserDto userDto = (UserDto) userObj;
                    if(userDto != null && isAccessMenu(userDto.getAccessMenus(),httpServletRequest.getServletPath())) {
                        chain.doFilter(request, response);
                    } else {
                        httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+loginPath);
                    }
                }
            } else {
                httpServletResponse.setStatus(404);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    private boolean isAccessMenu(String menus, String servletPath) {
        if(menus == null || "".equals(menus)) {
            return false;
        }
        String[] menuArr = menus.split(",");
        for(int i=0; i<menuArr.length; i++) {
            if(servletPath.contains(menuArr[i])) {
                return true;
            }
        }
        return false;
    }

    private boolean isWeixinRequest(String requestURL) {
        if(requestURL.endsWith("index.html") || requestURL.endsWith("index2.html")
                || requestURL.endsWith("index.htm") || requestURL.endsWith("index2.htm")) {
            return true;
        }
        return false;
    }

    private String getIpAddr(HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getHeader(this.header) == null) {
            return httpServletRequest.getRemoteAddr();
        }
        return httpServletRequest.getHeader(this.header);
    }

    protected boolean matchIp(String allowIpDuration,String ip) {
        if(allowIpDuration == null) {
            return false;
        }
        allowIpDuration = allowIpDuration.replaceAll("\\s+", ""); // 去空格
        if("".equals(allowIpDuration)){
            return false;
        } else if("*".equals(allowIpDuration) || "*.*.*.*".equals(allowIpDuration)) {
            return true;
        }
        String[] ipPatterns = allowIpDuration.split(",");
        for (int i = 0; i < ipPatterns.length; i++) {
            if(matchPattern(ipPatterns[i],ip)) { // 只要满足一个即为满足
                return true;
            }
        }
        return false;
    }

    /**
     * ip去匹配每个模式
     * @param pattern
     * @param ip
     * @return
     */
    protected boolean matchPattern(String pattern,String ip) {
        if(pattern == null || "".equals(pattern)) {
            return false;
        }
        // 区间段
        if(pattern.contains("~")) {
            int index = pattern.indexOf("~");
            String startIp = pattern.substring(0, index);
            String endIp = pattern.substring(index+1);
            String ipRegex = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
            if(Pattern.matches(ipRegex, startIp) && Pattern.matches(ipRegex, endIp)
                    && parseIp(startIp).compareTo(parseIp(ip)) < 0 && parseIp(ip).compareTo(parseIp(endIp)) < 0) {
                return true;
            } else {
                return false;
            }
        } else {
            // 转换为正则
            String regex = pattern.replaceAll("\\.", "\\\\.").replaceAll("\\?", "\\\\d{1}").replaceAll("\\*", "\\\\d{1,3}");
            if(Pattern.matches(regex, ip)) {
                return true;
            } else {
                return false;
            }
        }
    }

    public String parseIp(String ip) {
        String[] ipPer = ip.split("\\.");
        BigInteger bigInteger = null;
        StringBuilder sb = new StringBuilder(32);
        for (int i = 0; i < ipPer.length; i++) {
            bigInteger = new BigInteger(ipPer[i]);
            String format = bigInteger.toString(2);
            sb.append(supply(8 - format.length()) + format); // 补位到8位
        }
        return sb.toString();
    }

    /**
     * 返回len个0，最多为len = 7
     * @param len
     * @return
     */
    public String supply(int len) {
        String supply = "";
        switch (len) {
            case 1:
                supply = "0";
                break;
            case 2:
                supply = "00";
                break;
            case 3:
                supply = "000";
                break;
            case 4:
                supply = "0000";
                break;
            case 5:
                supply = "00000";
                break;
            case 6:
                supply = "000000";
                break;
            case 7:
                supply = "00000000";
                break;
            default:
                break;
        }
        return supply;
    }

    @Override
    public void destroy() {
    }

}
