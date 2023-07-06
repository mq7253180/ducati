package com.hce.ducati.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hce.ducati.dto.UserDto;
import com.hce.ducati.entity.UserEntity;
import com.hce.ducati.mapper.TestMapper;
import com.hce.ducati.service.CustomerService;
import com.quincy.sdk.SnowFlakeUtil;

@Controller
@RequestMapping("/user")
public class CustomerController {
	@Autowired
	private CustomerService customerService;

	@PostMapping("/add")
	@ResponseBody
	public Long add(@RequestBody UserEntity e) {
		Random r = new Random();
		Long userId = new SnowFlakeUtil().nextId()+r.nextInt();
		System.out.println("userId=============="+userId);
		e.setId(userId);
		e.setCreationTime(null);
		e.setJsessionid(null);
		e.setLastLogined(null);
		customerService.add(userId, e);
		return userId;
	}

	@PostMapping("/update")
	@ResponseBody
	public UserEntity update(@RequestBody UserEntity e) {
		return customerService.update(e.getId(), e);
	}

	@GetMapping("/get")
	@ResponseBody
	public UserEntity get(@RequestParam(required = true, name = "userid")Long userId) {
		return customerService.find(userId);
	}

	@RequestMapping("/test")
	public ModelAndView test() {
		if(true)
			throw new RuntimeException("xxx");
		return new ModelAndView("test");
	}

	@Autowired
	private TestMapper testMapper;

	@RequestMapping("/all")
	@ResponseBody
	public List<UserDto> all() {
		List<UserDto> list = testMapper.findAllUsers();
		for(UserDto user:list) {
			System.out.println(user.getId()+"---"+user.getIdStr());
		}
		return list;
	}

	public static void main(String[] args) {
		long[] ll = {157754022764351488l, 157754176032608256l, 157754306160889856l, 157755380452167680l};
		for(long l:ll) {
			System.out.println(l+"------------"+(l%4));
		}
	}
}