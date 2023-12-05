package com.ee172.team2.nemo.service;

import java.util.List;
import java.util.Optional;


import com.ee172.team2.nemo.model.WeddingCouple;

public interface WeddingCoupleService {


	// 得到weddingCouple實例
	public List<WeddingCouple> findAll();

	// 根據WEB-ID查找
	public Optional<WeddingCouple> findById(Integer id);

	// 儲存
	public WeddingCouple save(WeddingCouple couple);
	

	//删除
    public void delete(Integer id);
}



