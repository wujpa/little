package com.wujq.springboot.service.impl;

import com.wujq.springboot.common.api.CommonResult;
import com.wujq.springboot.service.RedisService;
import com.wujq.springboot.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Random;

@Service
public class UmsMemberServiceImpl implements UmsMemberService {
	@Autowired
	private RedisService redisService;

	@Value("${redis.key.prefix.authCode}")
	private String REDIS_KEY_PREFIX_AUTH_CODE;

	@Value("${redis.key.expire.authCode}")
	private Long AUTH_CODE_EXPIEE_SECONDS;

	@Override
	public CommonResult generateAuthCode(String telphone) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		sb.append(random.nextInt(1000000));

		redisService.set(REDIS_KEY_PREFIX_AUTH_CODE+telphone,sb.toString());
		redisService.expire(REDIS_KEY_PREFIX_AUTH_CODE+telphone,AUTH_CODE_EXPIEE_SECONDS);

		return CommonResult.success(sb.toString(),"获取验证码成功");
	}

	@Override
	public CommonResult verifyAuthCode(String telphone, String authCode) {
		if(StringUtils.isEmpty(authCode)){
			return CommonResult.failed("请输入验证码");
		}
		String realAuthCode = redisService.get(REDIS_KEY_PREFIX_AUTH_CODE+telphone);
		boolean result = authCode.equals(realAuthCode);
		if(result){
			return CommonResult.success(null,"验证码效验成功");
		}else {
			return CommonResult.failed("验证码不正确");
		}
	}
}
