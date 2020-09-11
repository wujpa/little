package com.wujq.springboot.service;

import com.wujq.springboot.common.api.CommonResult;

/**
 * 会员管理
 */
public interface UmsMemberService {
	/**
	 * 生成验证码
	 * @param telphone
	 * @return
	 */
	CommonResult generateAuthCode(String telphone);

	/**
	 * 判断验证码和手机号码是否匹配
	 * @param telphone
	 * @param authCode
	 * @return
	 */
	CommonResult verifyAuthCode(String telphone, String authCode);
}
