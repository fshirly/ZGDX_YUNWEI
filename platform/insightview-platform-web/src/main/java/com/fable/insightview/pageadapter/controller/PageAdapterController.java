package com.fable.insightview.pageadapter.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 页面适配器
 * @author 刘金兵 2015-07-14
 *
 */
@Controller
public class PageAdapterController {
	
	@RequestMapping(value="/page/adapter",method=RequestMethod.POST)
	public ModelAndView pageAdapter(HttpServletRequest request)
	{   
		String url=request.getParameter("url");
		ModelAndView mv=new ModelAndView();
		mv.addObject("url", url);
		mv.setViewName("/adapter");
		return mv;
	}
	
	@RequestMapping(value="/page/dialogAdapter",method=RequestMethod.GET)
	public ModelAndView pageDialogAdapter(@RequestParam("url") String url)
	{
		ModelAndView mv=new ModelAndView();
		mv.addObject("url", url);
		mv.setViewName("/dialogAdapter");
		return mv;
	}

}
