package com.jiantai.talent.controller;

import com.jiantai.talent.entity.User;
import com.jiantai.talent.service.serviceImpl.UserServiceImpl;
import com.jiantai.talent.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 共用控制器--登录页面，注册页面
 */
@Controller
@RequestMapping("public")
public class PublicController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * 跳转到首页
     * @param request
     * @return
     */
    @RequestMapping("index")
    public ModelAndView index(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("public/index");
        mv.addObject("user",user);//用来判断当前的用户登录没，没登录-下面的按钮显示（求职）和登录的求职者一样，招聘者则显示（招聘）
        mv.addObject("selected","index");//用来标识前端目前点击了下面的那个按钮，将其高亮
        return mv;
    }

    /**
     * 跳转到求职面
     * @return
     */
    @RequestMapping("job")
    public ModelAndView job(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("public/job");
        mv.addObject("selected","job");//用来标识前端目前点击了下面的那个按钮，将其高亮
        return mv;
    }

    /**
     * 跳到登录页面--get请求
     * @return
     */
    @GetMapping("login")
    public String loginGet(){
        return "public/login";
    }

    /**
     * 登录方法--post请求
     * @param request
     * @return
     */
    @PostMapping("login")
    @ResponseBody
    public ResultVo loginPost(HttpServletRequest request){
        ResultVo resultVo = new ResultVo();
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        User user = userServiceImpl.findUser(account);
        if (user != null){
            //判断密码
            if (user.getPwd().equals(password)){
                resultVo.setCode(0);
                if (user.getType() == 1){
                    resultVo.setUserType("求职者");
                }else {
                    resultVo.setUserType("招聘者");
                }

                resultVo.setMsg("登录成功");
                //将用户放进session
                request.getSession().setAttribute("user",user);
            }else {
                resultVo.setMsg("账号或密码不匹配");
            }
        }else {
            //查询不到用户名
            resultVo.setMsg("账号或密码不匹配");
        }
        return resultVo;
    }

    /**
     * 跳到注册页面
     * @return
     */
    @GetMapping("register")
    public String registerGet(){
        return "public/register";
    }

    /**
     * 注册方法
     * @param u
     * @param request
     * @return
     */
    @PostMapping("register")
    @ResponseBody
    public ResultVo registerPost(User u,HttpServletRequest request){
        ResultVo resultVo = new ResultVo();

        resultVo.setMsg("注册成功");
//        User user = new User();
//        user.setAccount("aaa");
        //先查有没有注册过此账号
        User user = userServiceImpl.findUser(u.getAccount());
        if (user != null){
            //已被注册
            resultVo.setMsg("该账号已被注册");
        }else {
            //插入user表
            userServiceImpl.register(u);
            //先查出来
            User nuser = userServiceImpl.findUser(u.getAccount());
            nuser.setNickName(u.getNickName());
            //插入昵称到user_info表
            userServiceImpl.addUserNickName(nuser);
            //存到session中
            request.getSession().setAttribute("user",nuser);
            resultVo.setCode(0);
        }


        return resultVo;
    }
}
