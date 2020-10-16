package com.jiantai.talent.controller;

import com.jiantai.talent.entity.User;
import com.jiantai.talent.vo.ResultVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 所有的用户控制器
 */
@Controller
@RequestMapping("user")
public class UserController {
    /**
     * 跳转到 "我的" 页面
     * @return
     */
    @RequestMapping("me")
    public ModelAndView me(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        User user = (User) request.getSession().getAttribute("user");
        System.out.println(user);
        mv.setViewName("user/me");
        mv.addObject("user",user);
        mv.addObject("selected","me");//用来标识前端目前点击了下面的那个按钮，将其高亮
        return mv;
    }

    /**
     * 跳到消息页面
     * @return
     */
    @RequestMapping("message")
    public ModelAndView message(){

        ModelAndView mv = new ModelAndView();
        mv.setViewName("user/message");
        mv.addObject("selected","message");//用来标识前端目前点击了下面的那个按钮，将其高亮
        return mv;
    }

    /**
     * 退出登录
     * @param request
     * @return
     */
    @RequestMapping("logout")
    public String logout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return "public/index";
    }

    /**
     * 跳到发布招聘页面
     * @return
     */
    @RequestMapping("recruit")
    public ModelAndView recruit(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("user/recruit");
        return mv;
    }
    public ResultVo changeResumeState(){
        ResultVo resultVo = new ResultVo();

        return resultVo;
    }
}
