package com.jiantai.talent.controller;

import com.jiantai.talent.entity.DataEntity;
import com.jiantai.talent.entity.Resume;
import com.jiantai.talent.entity.StaffInfo;
import com.jiantai.talent.entity.User;
import com.jiantai.talent.service.serviceImpl.UserServiceImpl;
import com.jiantai.talent.util.MyUtils;
import com.jiantai.talent.vo.ResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
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

    /**
     * 根据session中的用户账号，实时查询用户的信息
     * @return
     */
    private User getUserInfo(){
        User user = (User)request.getSession().getAttribute("user");
        return  userServiceImpl.findUser(user.getAccount());
    }

    /**
     * 跳转到 "我的" 页面
     * @return
     */
    @RequestMapping("me")
    public ModelAndView me(){
        ModelAndView mv = new ModelAndView();
        User user = getUserInfo();
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
     * @return
     */
    @RequestMapping("logout")
    public String logout(){
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
     * @param state
     * @return
     */
    @RequestMapping("openResume")
    @ResponseBody
    public ResultVo openResume(Integer state){
        ResultVo resultVo = new ResultVo();
        User user = getUserInfo();
        if (state == 0 || state == 1){
            user.setResumeState(state);
            try {
                userServiceImpl.updateResumeState(user);
                resultVo.setCode(0);
                resultVo.setMsg("操作成功");
                //request.getSession().setAttribute("user",user);//重新存进session--因为修改了数据
            }catch (Exception e){
                resultVo.setMsg("操作失败,原因：系统出现未知错误");
            }


        }else {
            resultVo.setMsg("操作失败");
        }
        return resultVo;
    }

    /**
     * 跳转到员工信息页面
     * @return
     */
    @RequestMapping("staffInfo")
    public ModelAndView userInfo(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("user/info-staff");
        User user = getUserInfo();


//        String photo = "data:image/jpg;base64," + MyUtils.encodeImgageToBase64(user.getPhoto());
//        user.setPhoto(photo);
        mv.addObject("user",user);
        return mv;
    }

    @RequestMapping("showImg")
    public void ShowImg(HttpServletResponse response)
            throws IOException {
        FileInputStream fileIs = null;
        User userInfo = getUserInfo();
        String type = request.getParameter("type");

        try {
            if ("photo".equals(type)){
                fileIs = new FileInputStream(userInfo.getPhoto());
            }else {
                return;
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

    @RequestMapping("updateStaffInfo")
    @ResponseBody
    public ResultVo updateStaffInfo(){
        ResultVo resultVo = new ResultVo();
        String photo = request.getParameter("photo");
        User user = (User) request.getSession().getAttribute("user");

        String replace = photo.replace("data:image/png;base64,", "");
        //D:\\upload\\talent\\photo\\md5(id)\\1111.jpg
        String src1 = "D:\\upload\\talent\\photo\\" + DigestUtils.md5DigestAsHex(user.getId().toString().getBytes());
        MyUtils.mkdir(src1);
        long time = System.currentTimeMillis();
        String src2 = time + ".png";
        MyUtils.decodeBase64ToImage(replace,src1 +"\\",src2);
        try {
            user.setPhoto(src1 + "\\" + src2);
            userServiceImpl.addStaffPhoto(user);
            resultVo.setCode(0);
            resultVo.setMsg("操作成功");
        }catch (Exception e){
            resultVo.setMsg("操作失败，原因：系统出现未知错误");
        }


        return resultVo;
    }

    /**
     * 我的简历页面
     * @return
     */
    @RequestMapping("resume")
    public ModelAndView resume(){
        ModelAndView mv = new ModelAndView();
        User user = getUserInfo();
        //回显已经创建的简历
        try {
            List<Resume> resumeList = userServiceImpl.findResume(user.getId());
            mv.addObject("resumeList",resumeList);
            //自制pdf简历
            StaffInfo info = userServiceImpl.getStaffInfoByUid(user.getId());
            mv.addObject("info",info);

            mv.setViewName("user/resume");
        }catch (Exception e){
            mv.setViewName("error");
        }


        return mv;
    }

    /**
     * 跳转到界面新增简历页面
     * @return
     */
    @RequestMapping("createResume")
    public ModelAndView createResume(){
        ModelAndView mv = new ModelAndView();
        try {
            List<DataEntity> educationList = userServiceImpl.getEducation();
            mv.addObject("educationList",educationList);

            List<DataEntity> experienceList = userServiceImpl.getExperience();
            mv.addObject("experienceList",experienceList);

            List<DataEntity> salaryList = userServiceImpl.getSalary();
            mv.addObject("salaryList",salaryList);
            mv.setViewName("user/resume-create");
        }catch (Exception e){
            mv.setViewName("error");
        }


        return mv;
    }

    /**
     * 新增 个人简历
     * @param resume
     * @return
     */
    @RequestMapping("addResume")
    @ResponseBody
    public ResultVo addResume(Resume resume){
        ResultVo resultVo = new ResultVo();
        User user = getUserInfo();
        resume.setUid(user.getId());
        //先查询该用户有多少简历了--排除已删除的
        List<Resume> resumeList = userServiceImpl.findResume(user.getId());
        if (resumeList.size() >= 3){
            resultVo.setMsg("最多允许创建3份简历");
        } else {
            //有效简历3个内
            try {
                userServiceImpl.addResume(resume);
                resultVo.setCode(0);
                resultVo.setMsg("操作成功");
            }catch (Exception e){
                resultVo.setMsg("操作失败，原因：系统出现未知错误");
            }
        }



        return resultVo;
    }

    /**
     * 删除个人简历
     * @return
     */
    @RequestMapping("deleteResume")
    @ResponseBody
    public ResultVo deleteResume(){
        ResultVo resultVo = new ResultVo();
        User user = getUserInfo();
        String resumeId = request.getParameter("resumeId");
        try {
            userServiceImpl.deleteResume(resumeId,user.getId().toString());
            resultVo.setMsg("操作成功");
            resultVo.setCode(0);
        }catch (Exception e){
            resultVo.setMsg("操作失败，原因：系统出现未知错误");
        }
        return resultVo;
    }

    /**
     * 跳转到简历编辑界面，数据回显
     * @return
     */
    @GetMapping("editResume")
    public ModelAndView getEditResume(){
        ModelAndView mv = new ModelAndView();
        String resumeId = request.getParameter("resumeId");
        User user = getUserInfo();

        Resume resume = new Resume();

        List<Resume> resumeList = userServiceImpl.findResume(user.getId());
        for (int i = 0;i < resumeList.size();i++){
            if (resumeList.get(i).getId().toString().equals(resumeId)){//传来的resumeId 和 查出来的所有简历列表对比
                //找到了，回显
                resume = resumeList.get(i);
                break;
            }
        }
        mv.addObject("resumeInfo",resume);
        try {
            List<DataEntity> educationList = userServiceImpl.getEducation();
            mv.addObject("educationList",educationList);

            List<DataEntity> experienceList = userServiceImpl.getExperience();
            mv.addObject("experienceList",experienceList);

            List<DataEntity> salaryList = userServiceImpl.getSalary();
            mv.addObject("salaryList",salaryList);
            mv.setViewName("user/resume-edit");
        }catch (Exception e){
            mv.setViewName("error");
        }
        return mv;
    }

    /**
     * 保存编辑的简历
     * @return
     */
    @PostMapping("editResume")
    @ResponseBody
    public ResultVo postEditResume(Resume resume){
        ResultVo resultVo = new ResultVo();
        User user = getUserInfo();
        //根据简历id 和用户id 进行修改
        try {
            //先查一次
            List<Resume> resumeList = userServiceImpl.findResume(user.getId());
            Resume editResume = new Resume();
            for (int i = 0;i< resumeList.size();i++){
                if (resumeList.get(i).getId().equals(resume.getId())){
                    //传来的简历id能和查出来的匹配到
                    editResume = resumeList.get(i);
                    break;
                }
            }

            MyUtils.copyBeanNotNullAttribute(resume,editResume);

//            System.out.println("------"+resume);
//            System.out.println("------"+editResume);
            userServiceImpl.editResume(editResume);
            resultVo.setCode(0);
            resultVo.setMsg("操作成功");
        }catch (Exception e){
            resultVo.setMsg("操作失败，原因：系统出现未知错误");
        }
        return resultVo;
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
                    case "0":
                        StaffInfo info = userServiceImpl.getStaffInfoByUid(user.getId());
                        info.setResume(path);
                        try {
                            userServiceImpl.updateStaffInfo(info);
                            resultVo.setMsg("操作成功");
                            resultVo.setCode(0);

                        }catch (Exception e){
                            resultVo.setMsg("系统出现未知错误，上传那文件失败");
                        }
                        break;
                }




            } catch (IOException e) {
                resultVo.setMsg("系统出现未知错误，上传那文件失败");
            }


        }
        //System.out.println("==============");
        return resultVo;
    }
}
