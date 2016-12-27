package com.sevencolor.domain.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sevencolor.domain.pojo.RebalanceStatisticsInfo;

@Repository
public interface RebalanceStatisticsInfoMapper {
	int deleteByPrimaryKey(Long dbid);

	int insert(RebalanceStatisticsInfo record);

	int insertSelective(RebalanceStatisticsInfo record);

	RebalanceStatisticsInfo selectByPrimaryKey(Long dbid);

	List<RebalanceStatisticsInfo> selectByCreateTime(Long createtime);

	int updateByPrimaryKeySelective(RebalanceStatisticsInfo record);

	int updateByPrimaryKey(RebalanceStatisticsInfo record);
}
