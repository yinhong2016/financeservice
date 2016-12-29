package com.sevencolor.domain.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sevencolor.domain.pojo.CubeRebalanceStatisticsInfo;

@Repository
public interface XQSummeryCubeDao {

	int insert(CubeRebalanceStatisticsInfo record);

	int insertSelective(CubeRebalanceStatisticsInfo record);

	List<CubeRebalanceStatisticsInfo> selectYearlyCubeRebalanceStatistics(Long createTime);

	List<CubeRebalanceStatisticsInfo> selectMonthlyCubeRebalanceStatistics(Long createTime);

	List<CubeRebalanceStatisticsInfo> selectDailyCubeRebalanceStatistics(Long createTime);

	void truncateSummeryCubeRebalanceStatistics();

	List<CubeRebalanceStatisticsInfo> selectSummeryCubeRebalanceStatistics();
}
