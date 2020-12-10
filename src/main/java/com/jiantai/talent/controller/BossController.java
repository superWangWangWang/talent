package com.jiantai.talent.controller;

import com.jiantai.talent.entity.*;
import com.jiantai.talent.service.serviceImpl.UserServiceImpl;
import com.jiantai.talent.util.MyUtils;
import com.jiantai.talent.vo.ResultVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@Controller
@RequestMapping("boss")
public class BossController {
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
     * 跳转到招聘列表页面
     * @return
     */
    @RequestMapping("recruit")
    public ModelAndView recruit(){
        ModelAndView mv = new ModelAndView();
        User user = getUserInfo();
        List<Recruit> recruitList = userServiceImpl.findRecruitByUid(user);
        mv.addObject("recruitList",recruitList);
        mv.setViewName("user/boss/recruit");
        return mv;
    }

    /**
     * 跳到创建招聘页面
     * @return
     */
    @RequestMapping("createRecruit")
    public ModelAndView createRecruit(){
        ModelAndView mv = new ModelAndView();
        //获取福利列表
        List<Welfare> welfareList = userServiceImpl.getWelfare();
        mv.addObject("welfareList",welfareList);
        //获取薪资表
        List<Salary> salaryList = userServiceImpl.getSalary();
        mv.addObject("salaryList",salaryList);
        //获取学历表
        List<Education> educationList = userServiceImpl.getEducation();
        mv.addObject("educationList",educationList);
        //获取经验表
        List<Experience> experienceList = userServiceImpl.getExperience();
        mv.addObject("experienceList",experienceList);

        mv.setViewName("user/boss/recruit-create");
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
    public ResultVo updateInfo(BossInfo infoVo) throws UnsupportedEncodingException {
        //String type = request.getParameter("type");
        ResultVo resultVo = new ResultVo();
        User user = getUserInfo();

        if (StringUtils.isNotBlank(infoVo.getPhoto())){
            String src1 = "D:\\upload\\talent\\photo\\boss\\" + DigestUtils.md5DigestAsHex(user.getId().toString().getBytes());
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
        BossInfo boss = userServiceImpl.findBossInfoByUid(user);
        MyUtils.copyBeanNotNullAttribute(infoVo,boss);
        System.out.println(infoVo);
        System.out.println(boss);
        try {
            userServiceImpl.updateBossInfo(boss);
            resultVo.setCode(0);
            resultVo.setMsg(SUCCESS);
        }catch (Exception e){
            resultVo.setMsg(ERROR);
        }
        return resultVo;
    }

    /**
     * 跳转到用户信息页面
     * @return
     */
    @RequestMapping("info")
    public ModelAndView info(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("user/boss/info");
        User user = getUserInfo();
        BossInfo bossInfo = userServiceImpl.findBossInfoByUid(user);
        mv.addObject("info",bossInfo);
        return mv;
    }

    /**
     * 创建招聘
     * @param recruit
     * @return
     */
    @RequestMapping("addRecruit")
    @ResponseBody
    public ResultVo addRecruit(Recruit recruit){
        User user = getUserInfo();
        recruit.setUid(user.getId());
        System.out.println(recruit);
        ResultVo resultVo = new ResultVo();
        try {
            userServiceImpl.addRecruit(recruit);
            resultVo.setCode(0);
            resultVo.setMsg(SUCCESS);
        }catch (Exception e){
            resultVo.setMsg(ERROR);
        }

        return resultVo;
    }

    /**
     * 不公开该招聘
     * @param recruit
     * @return
     */
    @RequestMapping("hiddenRecruit")
    @ResponseBody
    public ResultVo hiddenRecruit(Recruit recruit){
        ResultVo resultVo = new ResultVo();
        User user = getUserInfo();
        recruit.setUid(user.getId());
        try {

            //先查
            Recruit r = userServiceImpl.findRecruitById(recruit);
            if (r != null){
                if (r.getShow() == 0) {
                    r.setShow(1);
                } else {
                    r.setShow(0);
                }
                userServiceImpl.updateRecruit(r);
            }else {
                resultVo.setMsg(ERROR);
                return resultVo;
            }

            resultVo.setCode(0);
            resultVo.setMsg(SUCCESS);
        }catch (Exception e){
            resultVo.setMsg(ERROR);
        }

        return resultVo;
    }
    @RequestMapping("editRecruit")
    public ModelAndView editRecruit(Integer id){
        ModelAndView mv = new ModelAndView();
        User user = getUserInfo();

        Recruit r = new Recruit();
        r.setId(id);
        r.setUid(user.getId());
        Recruit recruit = userServiceImpl.findRecruitById(r);
        mv.addObject("recruit",recruit);

        //获取学历表
        List<Education> educationList = userServiceImpl.getEducation();
        mv.addObject("educationList",educationList);
        //获取福利列表
        List<Welfare> welfareList = userServiceImpl.getWelfare();
        mv.addObject("welfareList",welfareList);
        //获取薪资表
        List<Salary> salaryList = userServiceImpl.getSalary();
        mv.addObject("salaryList",salaryList);

        //获取经验表
        List<Experience> experienceList = userServiceImpl.getExperience();
        mv.addObject("experienceList",experienceList);

        mv.setViewName("user/boss/recruit-edit");
        return mv;
    }
    @RequestMapping("updateRecruit")
    @ResponseBody
    public ResultVo updateRecruit(Recruit recruit){
        ResultVo resultVo = new ResultVo();
        System.out.println("===================="+recruit);
        User user = getUserInfo();
        recruit.setUid(user.getId());

        try {
            //根据传来的 id 和 uid 查一次
            Recruit r = userServiceImpl.findRecruitById(recruit);
            if (r != null){//查得到这个招聘信息
                //使用工具类更新
                MyUtils.copyBeanNotNullAttribute(recruit,r);
                userServiceImpl.updateRecruit(r);
                resultVo.setCode(0);
                resultVo.setMsg(SUCCESS);
            }else{
                resultVo.setMsg(ERROR);
            }

        }catch (Exception e){
            resultVo.setMsg(ERROR);
        }


        return resultVo;
    }
}
