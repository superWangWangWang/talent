package com.jiantai.talent.controller;

import com.jiantai.talent.entity.*;
import com.jiantai.talent.service.serviceImpl.UserServiceImpl;
import com.jiantai.talent.util.MyUtils;
import com.jiantai.talent.vo.InfoVo;
import com.jiantai.talent.vo.ResultVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 所有的用户控制器
 */
@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;


    /**
     * 根据session中的用户账号，实时查询用户的信息
     * @return
     */
    private User getUserInfo(){
        User user = (User)request.getSession().getAttribute("user");
        return  userServiceImpl.findUserByAccount(user.getAccount());
    }

    /**
     * 跳转到 "我的" 页面
     * @return
     */
    @RequestMapping("me")
    public ModelAndView me(){
        ModelAndView mv = new ModelAndView();
        User user = getUserInfo();
        switch (user.getType()){//1=求职者，2=招聘者，3=管理员，4=超级管理员（不作日志记录）
            case 1:
                mv.setViewName("user/staff/me");
                StaffInfo staffInfo = userServiceImpl.findStaffInfoByUid(user.getId());
                mv.addObject("info",staffInfo);
                break;
            case 2:
                mv.setViewName("user/boss/me");
                BossInfo bossInfo = userServiceImpl.findBossInfoByUid(user);
                mv.addObject("info",bossInfo);
                break;
        }

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
        mv.setViewName("user/staff/message");
        User userInfo = getUserInfo();
        mv.addObject("user",userInfo);
        mv.addObject("selected","message");//用来标识前端目前点击了下面的那个按钮，将其高亮
        return mv;
    }

    /**
     * 退出登录
     * @return
     */
    @RequestMapping("logout")
    public String logout(){
        request.getSession().removeAttribute("user");

        Cookie cookie1 = new Cookie("talent_account",null);
        Cookie cookie2 = new Cookie("talent_password",null);
        cookie1.setMaxAge(0);//设置存活时间，“0”即马上消失
        cookie1.setPath("/");
        cookie2.setMaxAge(0);//设置存活时间，“0”即马上消失
        cookie2.setPath("/");
        response.addCookie(cookie1);
        response.addCookie(cookie2);

        return "redirect:/public/index";
    }

    /**
     * 返回图片到页面上，type：photo=头像
     * @param response
     * @throws IOException
     */
    @RequestMapping("img")
    public void img(HttpServletResponse response) throws IOException {
        FileInputStream fileIs = null;
        User userInfo = getUserInfo();

        String type = request.getParameter("role");

        try {

            switch (type){
                case "staff":
                    StaffInfo staffInfo = userServiceImpl.findStaffInfoByUid(userInfo.getId());
                    fileIs = new FileInputStream(staffInfo.getPhoto());
                    break;
                case "boss":
                    BossInfo bossInfo= userServiceImpl.findBossInfoByUid(userInfo);
                    fileIs = new FileInputStream(bossInfo.getPhoto());
                    break;
            }


        } catch (Exception e) {
            return;
        }
        int i = fileIs.available();
        // 得到文件大小
        byte data[] = new byte[i];
        fileIs.read(data);
        // 读数据
        response.setContentType("image/*");
        // 设置返回的文件类型
        OutputStream outStream = response.getOutputStream();
        // 得到向客户端输出二进制数据的对象
        outStream.write(data);
        // 输出数据
        outStream.flush();
        outStream.close();
        fileIs.close();
    }


    /**
     * 统一上传接口，类型type，0=求职者pdf简历
     * @return
     */
    @RequestMapping("upload")
    @ResponseBody
    public ResultVo upload(MultipartFile file){
        ResultVo resultVo = new ResultVo();
        String type = request.getParameter("type");
        User user = getUserInfo();
        String path =  "D://upload/talent/";//上传文件存放的磁盘位置


        if (file.isEmpty()) {
            resultVo.setMsg("上传失败，文件为空");
        }else {
            String originalFilename = file.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));//文件后缀，已经带 点
            String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + suffix;//文件名用年月日时分秒替换

            //=======================根据type类型，生成文件路径（带文件名）
            if ("0".equals(type)){
                path = path + "resume/" + DigestUtils.md5DigestAsHex(user.getId().toString().getBytes()) + "/" + fileName;
            }else {
                resultVo.setMsg("上传失败，type参数有误");
                return resultVo;
            }

            //=====================下面是存文件到磁盘上

            MyUtils.mkdir(path);//处理一下，防止文件夹不存在报错，
            File f = new File(path);

            try {
                file.transferTo(f);//上传到文件夹
                //type：0=求职者pdf自制简历
                switch (type){
//                    case "0":
//                        StaffInfo info = userServiceImpl.findStaffInfoByUid(user.getId());
//                        info.setResume(path);
//                        try {
//                            userServiceImpl.updateStaffInfo(info);
//                            resultVo.setMsg("操作成功");
//                            resultVo.setCode(0);
//
//                        }catch (Exception e){
//                            resultVo.setMsg("系统出现未知错误，上传那文件失败");
//                        }
//                        break;
                }




            } catch (IOException e) {
                resultVo.setMsg("系统出现未知错误，上传那文件失败");
            }


        }
        //System.out.println("==============");
        return resultVo;
    }

    /**
     * 统一下载接口，type：0=pdf自制简历
     * @param response
     */
    @RequestMapping("download")
    public void download(HttpServletResponse response){
        String type = request.getParameter("type");
        User user = getUserInfo();
        String path = "";
        switch (type){
//            case "0":
//                //获取自制简历的磁盘地址
//                StaffInfo info = userServiceImpl.findStaffInfoByUid(user.getId());
//                path = info.getResume();
//                break;
        }

        if (path != "")
             MyUtils.downloadFile(response,path,"utf-8");
    }

    @RequestMapping("/preview1")
    public void er(HttpServletResponse response){
        File file = new File("D://upload/talent/resume/c4ca4238a0b923820dcc509a6f75849b/20201029163550.pdf");
        if (file.exists()){
            byte[] data = null;
            try {
                FileInputStream input = new FileInputStream(file);
                data = new byte[input.available()];
                input.read(data);
                response.getOutputStream().write(data);
                input.close();
            } catch (Exception e) {
                System.out.println(e);
            }

        }else{
            return;
        }

    }


}
