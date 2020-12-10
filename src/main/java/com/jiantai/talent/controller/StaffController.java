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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@Controller
@RequestMapping("staff")
public class StaffController {
    private static final String SUCCESS = "操作成功";
    private static final String ERROR = "操作失败，原因：系统出现未知错误，请联系管理员处理";

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
        return  userServiceImpl.findUserByAccount(user.getAccount());
    }

    /**
     * 应聘者修改其简历是否公开
     * @param state
     * @return
     */
    @RequestMapping("show")
    @ResponseBody
    public ResultVo show(Integer state){
        ResultVo resultVo = new ResultVo();
        User user = getUserInfo();
        if (state == 0 || state == 1){
            //user.setResumeState(state);
            //先查
            User u = userServiceImpl.findUserByAccount(user.getAccount());
            u.setResumeState(state);
            try {
                userServiceImpl.updateUser(u);
                resultVo.setCode(0);
                resultVo.setMsg(SUCCESS);
                //request.getSession().setAttribute("user",user);//重新存进session--因为修改了数据
            }catch (Exception e){
                resultVo.setMsg(ERROR);
            }


        }else {
            resultVo.setMsg(ERROR);
        }
        return resultVo;
    }

    /**
     * 跳转到员工信息页面
     * @return
     */
    @RequestMapping("info")
    public ModelAndView Info(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("user/staff/info");
        User user = getUserInfo();
        List<Education> educationList = userServiceImpl.getEducation();


//        String photo = "data:image/jpg;base64," + MyUtils.encodeImgageToBase64(user.getPhoto());
//        user.setPhoto(photo);
        StaffInfo staffInfo = userServiceImpl.findStaffInfoByUid(user.getId());
        mv.addObject("info",staffInfo);
        mv.addObject("educationList",educationList);
        return mv;
    }

    /**
     * 更新用户信息
     * @param infoVo
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("updateInfo")
    @ResponseBody
    public ResultVo updateInfo(InfoVo infoVo) throws UnsupportedEncodingException {
        //String type = request.getParameter("type");
        ResultVo resultVo = new ResultVo();
        User user = getUserInfo();

        if (StringUtils.isNotBlank(infoVo.getPhoto())){
            String src1 = "D:\\upload\\talent\\photo\\staff\\" + DigestUtils.md5DigestAsHex(user.getId().toString().getBytes());
            long time = System.currentTimeMillis();
            String src2 = time + ".jpg";
            String photo = URLDecoder.decode(infoVo.getPhoto().trim().replaceAll("%(?![0-9a-fA-F]{2})","%25"),"utf8");
            photo = photo.replace("data:image/jpeg;base64,", "");//替换无用前缀

            MyUtils.mkdir(src1);
            MyUtils.decodeBase64ToImage(photo,src1 +"\\",src2);
            infoVo.setPhoto(src1 + "\\" + src2);
        }else {
            infoVo.setPhoto(null);//因为复制对象""也复制过去了
        }
        //查员工表信息
        StaffInfo staff = userServiceImpl.findStaffInfoByUid(user.getId());
        MyUtils.copyBeanNotNullAttribute(infoVo,staff);
        System.out.println(infoVo);
        System.out.println(staff);
        try {
            userServiceImpl.updateStaffInfo(staff);
            resultVo.setCode(0);
            resultVo.setMsg(SUCCESS);
        }catch (Exception e){
            resultVo.setMsg(ERROR);
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
            StaffInfo staffInfo = userServiceImpl.findStaffInfoByUid(user.getId());
            mv.addObject("info", staffInfo);

            mv.setViewName("user/staff/resume");
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
            List<Education> educationList = userServiceImpl.getEducation();
            mv.addObject("educationList",educationList);

            List<Experience> experienceList = userServiceImpl.getExperience();
            mv.addObject("experienceList",experienceList);

            List<Salary> salaryList = userServiceImpl.getSalary();
            mv.addObject("salaryList",salaryList);
            mv.setViewName("user/staff/resume-create");
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
                resultVo.setMsg(SUCCESS);
            }catch (Exception e){
                resultVo.setMsg(ERROR);
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
            List<Resume> resume = userServiceImpl.findResume(user.getId());
            resume.forEach(r->{
                if (r.getId() == Integer.parseInt(resumeId)){
                    userServiceImpl.deleteResume(r);
                }

            });

            resultVo.setMsg(SUCCESS);
            resultVo.setCode(0);
        }catch (Exception e){
            resultVo.setMsg(ERROR);
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
            List<Education> educationList = userServiceImpl.getEducation();
            mv.addObject("educationList",educationList);

            List<Experience> experienceList = userServiceImpl.getExperience();
            mv.addObject("experienceList",experienceList);

            List<Salary> salaryList = userServiceImpl.getSalary();
            mv.addObject("salaryList",salaryList);
            mv.setViewName("user/staff/resume-edit");
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
            resultVo.setMsg(SUCCESS);
        }catch (Exception e){
            resultVo.setMsg(ERROR);
        }
        return resultVo;
    }


}
