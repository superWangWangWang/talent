package com.jiantai.talent.controller;

import com.github.pagehelper.PageHelper;
import com.jiantai.talent.entity.*;
import com.jiantai.talent.service.serviceImpl.UserServiceImpl;
import com.jiantai.talent.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * 共用控制器--登录页面，注册页面
 */
@Controller
@RequestMapping("public")
public class PublicController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private HttpServletRequest request;



    /**
     * 跳转到首页
     * @return
     */
    @RequestMapping("index")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView();
        User u = (User)request.getSession().getAttribute("user");
        if(u != null){
            User user = userServiceImpl.findUserByAccount(u.getAccount());
            mv.addObject("user",user);//用来判断当前的用户登录没，没登录-下面的按钮显示（求职）和登录的求职者一样，招聘者则显示（招聘）
        }
        System.out.println("============index");

        mv.setViewName("public/index");

        mv.addObject("selected","index");//用来标识前端目前点击了下面的那个按钮，将其高亮
        //回显最新的招聘信息
        PageHelper.startPage(0,5);//只显示最新5条
        List<Recruit> recruitList = userServiceImpl.getRecruit();
        mv.addObject("recruitList",recruitList);

        //获取福利列表
        List<Welfare> welfareList = userServiceImpl.getWelfare();
        mv.addObject("welfareList",welfareList);
        //获取薪资表
        List<Salary> salaryList = userServiceImpl.getSalary();
        mv.addObject("salaryList",salaryList);
//        //获取学历表
//        List<Education> educationList = userServiceImpl.getEducation();
//        mv.addObject("educationList",educationList);
//        //获取经验表
//        List<Experience> experienceList = userServiceImpl.getExperience();
//        mv.addObject("experienceList",experienceList);
        //获取公告
        List<Notice> noticeList = userServiceImpl.getNotice();
        mv.addObject("noticeList",noticeList);
        return mv;
    }

    /**
     * 跳转到求职面
     * @return
     */
    @RequestMapping("job")
    public ModelAndView job(){
        ModelAndView mv = new ModelAndView();
        User u = (User)request.getSession().getAttribute("user");
        if(u != null){
            User user = userServiceImpl.findUserByAccount(u.getAccount());
            mv.addObject("user",user);//用来判断当前的用户登录没，没登录-下面的按钮显示（求职）和登录的求职者一样，招聘者则显示（招聘）
        }

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
    public ResultVo loginPost(HttpServletRequest request,HttpServletResponse response){
        ResultVo resultVo = new ResultVo();
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        User user = userServiceImpl.findUserByAccount(account);

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

                Cookie cookie1 = new Cookie("talent_account",user.getAccount());
                Cookie cookie2 = new Cookie("talent_password",user.getPwd());
                cookie1.setMaxAge(60 * 60 * 24 * 7);
                cookie2.setMaxAge(60 * 60 * 24 * 7);
                cookie1.setPath("/");
                cookie2.setPath("/");
                response.addCookie(cookie1);
                response.addCookie(cookie2);

                user.setLoginCount(user.getLoginCount() + 1);
                userServiceImpl.updateUser(user);
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
     * @param request
     * @return
     */
    @PostMapping("register")
    @ResponseBody
    public ResultVo registerPost(HttpServletRequest request,HttpServletResponse response){
        ResultVo resultVo = new ResultVo();
        String nickName = request.getParameter("nickName");
        String account = request.getParameter("account");
        String pwd = request.getParameter("pwd");

        //resultVo.setMsg("注册成功");
//        User user = new User();
//        user.setAccount("aaa");
        //先查有没有注册过此账号
        User user = userServiceImpl.findUserByAccount(account);
        if (user != null){
            //已被注册
            resultVo.setMsg("该账号已被注册");
        }else {
            //密码使用MD5加密
            String md5Pwd = DigestUtils.md5DigestAsHex(pwd.getBytes());
            User u = new User();
            u.setPwd(md5Pwd);
            u.setAccount(account);
            //插入user表
            System.out.println("==========="+u);
            userServiceImpl.addUser(u);
            System.out.println("==========="+u);
            //插入staffinfo表
            //StaffInfo staffInfo = userServiceImpl.findStaffInfoByUid(u.getId());
            userServiceImpl.addStaffInfo(u);
            //查staff表
            StaffInfo staffInfo = userServiceImpl.findStaffInfoByUid(u.getId());
            //修改昵称
            staffInfo.setNickName(nickName);
            userServiceImpl.updateStaffInfo(staffInfo);

            //存到cookies中
            Cookie cookie1 = new Cookie("talent_account",account);
            Cookie cookie2 = new Cookie("talent_password",pwd);
            cookie1.setMaxAge(60 * 60 * 24 * 7);
            cookie2.setMaxAge(60 * 60 * 24 * 7);
            cookie1.setPath("/");
            cookie2.setPath("/");
            response.addCookie(cookie1);
            response.addCookie(cookie2);

            //存到session中
            request.getSession().setAttribute("user",u);
            resultVo.setCode(0);
            resultVo.setMsg("注册成功");
        }


        return resultVo;
    }
    @RequestMapping("recruitDetail")
    public ModelAndView recruitDetail(){
        ModelAndView mv = new ModelAndView();

        mv.setViewName("public/detail-recruit");
        return mv;
    }
}
