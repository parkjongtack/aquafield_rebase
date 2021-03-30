package com.soft.web.controller.admin;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.soft.web.base.GenericController;

@Controller
@RequestMapping({"/secu_admaf"})
public class AdminManagerController extends GenericController {
	
	protected Logger logger = LoggerFactory.getLogger(AdminManagerController.class);

	@RequestMapping(value = "/admin/manager/index.af")
    public String home(Model model, HttpSession session) {
        return "/admin/manager/index";
    }	

}
