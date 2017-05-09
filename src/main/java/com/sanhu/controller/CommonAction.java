package com.sanhu.controller;

import com.sanhu.entity.User;
import com.sanhu.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zhangboxun on 2017/4/1.
 */
@Controller
@RequestMapping("/")
public class CommonAction {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;
    
    /**
     * 跳转到登录界面
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage(){
    	return "login";
    }
    
    /**
     * 注册添加一个用户
     * @param user
     * @param model
     * @return
     */
    @RequestMapping(value = "registryer", method = RequestMethod.POST)
    public String createUser(User user, ModelMap model) {
        User userDb = userService.findByUsername(user.getUsername());
        if(userDb == null) {
            User newUser = userService.createUser(user);
            model.addAttribute("user", newUser);
            logger.info("注册成功：{}", newUser);
        }
        return "login";
    }
    
    /**
     * 处理登录请求
     * @param user
     * @return
     */
    @RequestMapping(value = "doLogin", method = {RequestMethod.GET, RequestMethod.POST})
    public String doLogin(@Valid User user, BindingResult result, ModelMap model, HttpServletRequest request) {
    	try {
	    	Subject subject = SecurityUtils.getSubject();
	    	// 如果已经登录，跳转到首页
	    	if(subject.isAuthenticated()) {
	    		return "index";
	    	}
	    	// 绑定错误入参
	    	if (result.hasErrors()) {
				model.addAttribute("error", "参数错误！");
				return "error";
			}
	    	// 进行身份验证
	    	subject.login(new UsernamePasswordToken(user.getUsername(), user.getPassword()));
	    	System.err.println(user.getPassword());
	    	// 验证成功在Session中保存用户信息
	    	final User authUserInfo = userService.findByUsername(user.getUsername());
			request.getSession().setAttribute("userInfo", authUserInfo);
			model.addAttribute("user", user);
    	} catch (AuthenticationException e) {
    		model.addAttribute("error", "用户名或密码错误 ！");
    		e.printStackTrace();
    		return "error";
    	}
    	return "index";
    }
    
    /**
	 * 用户登出
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.removeAttribute("userInfo");
		// 登出操作
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		logger.info("成功退出登录");
		// 这里需要使用redirect,因为直接return程序还是会查找刚才logout的sessionId，然后报：
		// org.apache.shiro.session.UnknownSessionException异常
		return "redirect:/login";
	}
}
