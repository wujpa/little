package com.wujq.springboot.service;


import com.wujq.springboot.mbg.model.PmsBrand;

import java.util.List;

public interface PmsBrandService {
	List<PmsBrand> listAllBrand();

	int createBrand(PmsBrand brand);

	int updateBrand(Long id, PmsBrand brand);

	int deleteBrand(Long id);

	List<PmsBrand> listBrand(int pageNum, int pageSize);

	PmsBrand getBrand(Long id);
}
