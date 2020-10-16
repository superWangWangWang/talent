package com.jiantai.talent.controller;

import com.jiantai.talent.entity.User;
import com.jiantai.talent.service.serviceImpl.UserServiceImpl;
import com.jiantai.talent.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 所有的用户控制器
 */
@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;

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

    /**
     * 应聘者修改其简历是否公开
     * @param u
     * @param request
     * @return
     */
    @RequestMapping("changeResumeState")
    @ResponseBody
    public ResultVo changeResumeState(User u,HttpServletRequest request){
        ResultVo resultVo = new ResultVo();
        User user = (User)request.getSession().getAttribute("user");
        if (u.getResumeState() == 0 || u.getResumeState() == 1){
            user.setResumeState(u.getResumeState());
            userServiceImpl.updateResumeState(user);
            resultVo.setCode(0);
            resultVo.setMsg("更新简历公开状态成功");
            request.getSession().setAttribute("user",user);//重新存进session--因为修改了数据
        }else {
            resultVo.setMsg("更新简历公开状态失败");
        }


        return resultVo;
    }
}
