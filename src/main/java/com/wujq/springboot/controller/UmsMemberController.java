package com.wujq.springboot.controller;

import com.wujq.springboot.common.api.CommonResult;
import com.wujq.springboot.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "UmsMemberController",description = "会员登录注册管理")
@RequestMapping(value = "sso")
public class UmsMemberController {
	@Autowired
	private UmsMemberService umsMemberService;


	@ApiOperation("获取验证码")
	@GetMapping(value = "/getAuthCode")
	public CommonResult getAuthCode(String telphone){
		return umsMemberService.generateAuthCode(telphone);
	}

	@ApiOperation("判断验证码是否正确")
	@GetMapping(value = "/verifyAuthCode")
	public CommonResult verifyAuthCode(String telphone, String authCode){
		return umsMemberService.verifyAuthCode(telphone,authCode);
	}

}
