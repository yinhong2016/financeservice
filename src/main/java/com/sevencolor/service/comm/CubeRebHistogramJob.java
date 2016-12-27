/**
 * Copyright © 2016 Seven Color. All rights reserved.
 *
 * @Description: 服务端定义的JOB
 * @author: yinhong
 * @date: 2016年11月29日 下午3:50:13
 * @version: V1.0
 */
package com.sevencolor.service.comm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 用于定时生成组合在仓股票柱状图
 */
public class CubeRebHistogramJob {

	private static final Logger logger = LoggerFactory.getLogger(CubeRebHistogramJob.class);

	@Autowired
	private CubeRebHistogramTask cubeRebHistogramTask;

	/**
	 * @Description: 定时生成组合在仓股票柱状图
	 * @return: void
	 */
	public void createHistogram() {
		try {
			cubeRebHistogramTask.histogramByDay();
			cubeRebHistogramTask.histogramByWeek();
			cubeRebHistogramTask.histogramByMonth();
		} catch (Exception e) {
			logger.error("create histogram failed.", e);
		}
	}
}
