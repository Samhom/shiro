package com.sanhu.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.sanhu.entity.User;
import com.sanhu.service.UserService;

/**
 * Created by zhangboxun on 2017/4/1.
 */
@Controller
@RequestMapping("rbac/users")
public class UsersAction {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;
    
    /**
     * 跳转到用户维护内嵌页面
     * @return html
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String redirectIndex(){
    	return "rbac/index";
    }
    
    /**
     * 获取全部用户,封装并返回json数据，用于用户维护内嵌页面展示
     * @param model
     * @return json
     */
    @RequestMapping(value = "data", method = RequestMethod.GET)
	public Object getUserList(String limit, String offset, ModelMap model) {
		List<User> list = userService.findUserList(offset, limit);
		int count = userService.getUserCount();
		logger.info("return data:[{}]", list);
		logger.info("data count:[{}]", count);
		model.addAttribute("list", list);
		model.addAttribute("size", count);
		return "rbac/list";
	}
    
}
