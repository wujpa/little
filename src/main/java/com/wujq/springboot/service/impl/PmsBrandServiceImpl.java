package com.wujq.springboot.service.impl;

import com.github.pagehelper.PageHelper;
import com.wujq.springboot.mbg.mapper.PmsBrandMapper;
import com.wujq.springboot.mbg.model.PmsBrand;
import com.wujq.springboot.mbg.model.PmsBrandExample;
import com.wujq.springboot.service.PmsBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PmsBrandServiceImpl implements PmsBrandService {
	@Autowired
	private PmsBrandMapper pmsBrandMapper;
	@Override
	public List<PmsBrand> listAllBrand() {
		return pmsBrandMapper.selectByExample(new PmsBrandExample());
	}

	@Override
	public int createBrand(PmsBrand brand) {
		return pmsBrandMapper.insertSelective(brand);
	}

	@Override
	public int updateBrand(Long id, PmsBrand brand) {
		brand.setId(id);
		return pmsBrandMapper.updateByPrimaryKeySelective(brand);
	}

	@Override
	public int deleteBrand(Long id) {
		return pmsBrandMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<PmsBrand> listBrand(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		return pmsBrandMapper.selectByExample(new PmsBrandExample());
	}

	@Override
	public PmsBrand getBrand(Long id) {
		return pmsBrandMapper.selectByPrimaryKey(id);
	}
}
