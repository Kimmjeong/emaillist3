package com.hanains.emaillist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hanains.emaillist.dao.EmailListDao;
import com.hanains.emaillist.vo.EmailListVo;

@Controller
public class EmailListController {
	
	@Autowired
	private EmailListDao emailListDao;
	
	@RequestMapping("/")
	public String index(Model model){
		
		List<EmailListVo> list=emailListDao.getList();
		model.addAttribute("list",list);
		
		return "/WEB-INF/views/index.jsp";
	}
	
	@RequestMapping("/form")
	public String form(Model model){
		return "/WEB-INF/views/form.jsp";
	}
	
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public String insert(@ModelAttribute EmailListVo vo){

		emailListDao.insert(vo);
		return "redirect:/";
	}
}
