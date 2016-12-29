package com.sevencolor.domain.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sevencolor.domain.pojo.CubeRebalanceStatisticsInfo;

@Repository
public interface XQYearlyCubeRebStatDao {
	int deleteByPrimaryKey(Long dbid);

	int deleteByCreateTime(Long createtime);

	int insert(CubeRebalanceStatisticsInfo record);

	int insertSelective(CubeRebalanceStatisticsInfo record);

	CubeRebalanceStatisticsInfo selectByPrimaryKey(Long dbid);

	int updateByPrimaryKeySelective(CubeRebalanceStatisticsInfo record);

	int updateByPrimaryKey(CubeRebalanceStatisticsInfo record);

	List<CubeRebalanceStatisticsInfo> selectByCreateTime(Long createtime);

}
