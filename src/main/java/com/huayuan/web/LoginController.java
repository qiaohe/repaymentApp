package com.huayuan.web;

import com.huayuan.common.App;
import com.huayuan.common.CommonDef;
import com.huayuan.domain.loanapplication.Staff;
import com.huayuan.repository.credit.StaffRepositoryCustom;
import com.huayuan.web.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Richard on 14-8-22.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Inject
    private StaffRepositoryCustom staffRepositoryCustom;

    private static final String MENU = "menu.";

    private static final String ROLE = "role.";

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestBody UserDto userDto,HttpServletRequest request) {
//        Staff staff = staffRepositoryCustom.findByPassword(userDto.getUsername(),userDto.getPassword());
        Staff staff = new Staff();
        staff.setStaffId("001");
        if(staff != null) {
            String menuUrls = getMenuUrls(staff.getStaffId());
            userDto.setAccessMenus(menuUrls);
            request.getSession().setAttribute(CommonDef.LOGIN_USER,userDto);
            return menuUrls;
        } else {
            return "";
        }
    }

    private String getMenuUrls(String userId) {
        App app = App.getInstance();
        Iterator<String> keys = app.getMenu().getKeys();
        StringBuffer sb = new StringBuffer();
        while (keys.hasNext()) {
            String key = keys.next();
            if(key.startsWith(ROLE) && app.getInMenu(key).contains(userId)) {
                sb.append(","+app.getInMenu(MENU + key.substring(ROLE.length())));
            }
        }
        return sb.length() == 0 ? "" : sb.substring(1);
    }

}
