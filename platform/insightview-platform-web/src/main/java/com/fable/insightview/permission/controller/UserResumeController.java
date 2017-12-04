package com.fable.insightview.permission.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.platform.mybatis.entity.STUserResume;
import com.fable.insightview.platform.mybatis.entity.UserLearningExp;
import com.fable.insightview.platform.mybatis.entity.UserProjectExp;
import com.fable.insightview.platform.mybatis.entity.UserTrainingExp;
import com.fable.insightview.platform.mybatis.entity.UserWorkingExp;
import com.fable.insightview.platform.service.UserResumeService;
import com.fable.insightview.platform.sysinit.SystemParamInit;

/**
 * @author Li jiuwei
 * @date   2015年4月8日 下午2:25:02
 */
@Controller
@RequestMapping("/userResume")
public class UserResumeController {
	@Autowired
	UserResumeService userResumeService;
	
	/**
	 * String转化为需要的Date形式
	 * 
	 * @param request
	 * @param binder
	 * @throws Exception
	 * @author sanyou
	 */
	@InitBinder
	protected void init(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
	
	@RequestMapping("/toResume")
	public ModelAndView toResume(Integer userId) {
		STUserResume resume = userResumeService.getResumeByUserId(userId);
		if(resume == null) {
			resume = new STUserResume();
			resume.setUserID(userId);
			resume.setGender(1);
			userResumeService.insertEmtpyResume(resume);
			
			resume = userResumeService.getUserInfoByUserId(userId);
		}
		
		resume.setUserIconURL(SystemParamInit.getValueByKey("fileServerURL") + resume.getUserIcon());
		ModelAndView mv = new ModelAndView();
		mv.addObject("userResume", resume);
		mv.setViewName("permission/resume");
		return mv;
	}
	
	@RequestMapping("/toResumeView")
	public ModelAndView toResumeView(Integer userId) {
		STUserResume resume = userResumeService.getResumeByUserId(userId);
		if(resume == null) {
			resume = new STUserResume();
			resume.setUserID(userId);
			userResumeService.insertEmtpyResume(resume);
			
			resume = userResumeService.getUserInfoByUserId(userId);//这里获取的只是SysUserInfo里的数据,STUserResume是空的
			resume.setGender(1);
		}
		
		resume.setUserIconURL(SystemParamInit.getValueByKey("fileServerURL") + resume.getUserIcon());
		ModelAndView mv = new ModelAndView();
		mv.addObject("userResume", resume);
		mv.setViewName("permission/resume_view");
		return mv;
	}
	
	@RequestMapping("/isResumeAdded")
	@ResponseBody
	public String isResumeAdded(Integer userId) {
		STUserResume resume = userResumeService.getResumeByUserId(userId);
		if(resume == null) {
			return "false";
		} else {
			if(resume.getGender() == null) {
				return "false";
			} else {
				return "true";
			}
		}
	}
	
	@RequestMapping("/updateResume")
	@ResponseBody
	public String updateResume(STUserResume resume) {
		userResumeService.updateResume(resume);
		userResumeService.updateUserInfo(resume);
		return "OK";
	}
	
	//////////////////////////学习经历/////////////////////////////////////
	@RequestMapping("/toAddUserLearningExp")
	public ModelAndView toAddUserLearningExp(Integer resumeID) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("permission/userLearningExp");
		
		UserLearningExp obj = new UserLearningExp();
		obj.setResumeID(resumeID);
		mv.addObject("userLearningExp",obj);
		
		return mv;
	}
	
	@RequestMapping("/toEditUserLearningExp")
	public ModelAndView toEditUserLearningExp(Integer learningExpId) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("permission/userLearningExp");
		
		if(learningExpId != null) {
			UserLearningExp obj = userResumeService.getUserLearningExp(learningExpId);
			mv.addObject("userLearningExp", obj);
		}
		
		return mv;
	}
	
	@RequestMapping("/queryUserLearningExp")
	@ResponseBody
	public Map<String, Object> queryUserLearningExp(Integer resumeID) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<UserLearningExp> list =userResumeService.queryUserLearningExp(resumeID);
		result.put("rows", list);
		result.put("total", list.size());
		
		return result;
	}
	
	@RequestMapping("/saveUserLearningExp")
	@ResponseBody
	public void saveUserLearningExp(UserLearningExp userLearningExp) {
		if(userLearningExp.getLearningExpId() == null) {
			userResumeService.insertUserLearningExp(userLearningExp);
		} else {
			userResumeService.updateUserLearningExp(userLearningExp);
		}
	}
	
	@RequestMapping("/deleteUserLearningExp")
	@ResponseBody
	public void deleteUserLearningExp(Integer learningExpId) {
		userResumeService.deleteUserLearningExp(learningExpId);
	}
	
	/////////////////////////////参加培训及后续教育///////////////////////////////////////
	@RequestMapping("/toAddUserTrainingExp")
	public ModelAndView toAddUserTrainingExp(Integer resumeID) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("permission/userTrainingExp");
		
		UserTrainingExp obj = new UserTrainingExp();
		obj.setResumeID(resumeID);
		mv.addObject("userTrainingExp",obj);
		
		return mv;
	}
	
	@RequestMapping("/toEditUserTrainingExp")
	public ModelAndView toEditUserTrainingExp(Integer trainingExpId) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("permission/userTrainingExp");
		
		if(trainingExpId != null) {
			UserTrainingExp obj = userResumeService.getUserTrainingExp(trainingExpId);
			mv.addObject("userTrainingExp", obj);
		}
		
		return mv;
	}
	
	@RequestMapping("/queryUserTrainingExp")
	@ResponseBody
	public Map<String, Object> queryUserTrainingExp(Integer resumeID) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<UserTrainingExp> list = userResumeService.queryUserTrainingExp(resumeID);
		result.put("rows", list);
		result.put("total", list.size());
		
		return result;
	}
	
	@RequestMapping("/saveUserTrainingExp")
	@ResponseBody
	public void saveUserTrainingExp(UserTrainingExp userTrainingExp) {
		if(userTrainingExp.getTrainingExpId() == null) {
			userResumeService.insertUserTrainingExp(userTrainingExp);
		} else {
			userResumeService.updateUserTrainingExp(userTrainingExp);
		}
	}
	
	@RequestMapping("/deleteUserTrainingExp")
	@ResponseBody
	public void deleteUserTrainingExp(Integer trainingExpId) {
		userResumeService.deleteUserTrainingExp(trainingExpId);
	}
	
	//////////////////////////工作经历///////////////////////////////
	@RequestMapping("/toAddUserWorkingExp")
	public ModelAndView toAddUserWorkingExp(Integer resumeID) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("permission/userWorkingExp");
		
		UserWorkingExp obj = new UserWorkingExp();
		obj.setResumeID(resumeID);
		mv.addObject("userWorkingExp",obj);
		
		return mv;
	}
	
	@RequestMapping("/toEditUserWorkingExp")
	public ModelAndView toEditUserWorkingExp(Integer workingExpId) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("permission/userWorkingExp");
		
		if(workingExpId != null) {
			UserWorkingExp obj = userResumeService.getUserWorkingExp(workingExpId);
			mv.addObject("userWorkingExp", obj);
		}
		
		return mv;
	}
	
	@RequestMapping("/queryUserWorkingExp")
	@ResponseBody
	public Map<String, Object> queryUserWorkingExp(Integer resumeID) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<UserWorkingExp> list =userResumeService.queryUserWorkingExp(resumeID);
		result.put("rows", list);
		result.put("total", list.size());
		
		return result;
	}
	
	@RequestMapping("/saveUserWorkingExp")
	@ResponseBody
	public void saveUserWorkingExp(UserWorkingExp userWorkingExp) {
		if(userWorkingExp.getWorkingExpId() == null) {
			userResumeService.insertUserWorkingExp(userWorkingExp);
		} else {
			userResumeService.updateUserWorkingExp(userWorkingExp);
		}
	}
	
	@RequestMapping("/deleteUserWorkingExp")
	@ResponseBody
	public void deleteUserWorkingExp(Integer workingExpId) {
		userResumeService.deleteUserWorkingExp(workingExpId);
	}
	
	///////////////////////////参与项目建设/////////////////////////////////////
	@RequestMapping("/toAddUserProjectExp")
	public ModelAndView toAddUserProjectExp(Integer resumeID) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("permission/userProjectExp");
		
		UserProjectExp obj = new UserProjectExp();
		obj.setResumeID(resumeID);
		mv.addObject("userProjectExp",obj);
		
		return mv;
	}
	
	@RequestMapping("/toEditUserProjectExp")
	public ModelAndView toEditUserProjectExp(Integer projectExpId) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("permission/userProjectExp");
		
		if(projectExpId != null) {
			UserProjectExp obj = userResumeService.getUserProjectExp(projectExpId);
			mv.addObject("userProjectExp", obj);
		}
		
		return mv;
	}
	
	@RequestMapping("/queryUserProjectExp")
	@ResponseBody
	public Map<String, Object> queryUUserProjectExp(Integer resumeID) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<UserProjectExp> list =userResumeService.queryUserProjectExp(resumeID);
		result.put("rows", list);
		result.put("total", list.size());
		
		return result;
	}
	
	@RequestMapping("/saveUserProjectExp")
	@ResponseBody
	public void saveUserProjectExp(UserProjectExp userProjectExp) {
		if(userProjectExp.getProjectExpId() == null) {
			userResumeService.insertUserProjectExp(userProjectExp);
		} else {
			userResumeService.updateUserProjectExp(userProjectExp);
		}
	}
	
	@RequestMapping("/deleteUserProjectExp")
	@ResponseBody
	public void deleteUserProjectExp(Integer projectExpId) {
		userResumeService.deleteUserProjectExp(projectExpId);
	}
	
}
