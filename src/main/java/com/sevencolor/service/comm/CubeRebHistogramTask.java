/**
 * Copyright © 2016 Seven Color. All rights reserved.
 *
 * @Description: 定时生成柱状报表任务体
 * @author: yinhong
 * @date: 2016年11月29日 下午3:34:19
 * @version: V1.0
 */
package com.sevencolor.service.comm;

import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sevencolor.comm.util.MessageUtil;
import com.sevencolor.domain.dao.RebalanceStatisticsInfoMapper;
import com.sevencolor.domain.pojo.RebalanceStatisticsInfo;

/**
 * @Description: 根据组合中的股票信息，按天、周、月生成柱状报表图片
 */
@Component
public class CubeRebHistogramTask {

	private static final Logger logger = LoggerFactory.getLogger(CubeRebHistogramTask.class);

	@Autowired
	private RebalanceStatisticsInfoMapper rebalanceStatisticsInfo;

	/**
	 * @Description: 最近一天柱状报表
	 * @return: void
	 */
	public void histogramByDay() {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		// 设置为前一天
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		// 得到前一天的时间
		Date dBefore = calendar.getTime();

		// 查询前一天组合涉及所有股票的集合
		List<RebalanceStatisticsInfo> resultList = rebalanceStatisticsInfo.selectByCreateTime(dBefore.getTime());

		// 生成柱状图片
		FileOutputStream fos = null;
		try {
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();

			if (resultList != null && !resultList.isEmpty()) {

				// 画出柱状图
				for (RebalanceStatisticsInfo r : resultList) {
					dataset.addValue(Integer.parseInt(r.getTotalweight()), r.getStocksymbol(), "");
				}
				// setCnConfig();
				JFreeChart chart = ChartFactory.createBarChart(MessageUtil.getMessage("message.comm.stockinfo"),
						MessageUtil.getMessage("message.comm.stock"), MessageUtil.getMessage("message.comm.weight"),
						dataset, PlotOrientation.VERTICAL, true, false, false);

				// 判断文件是否存在，不存在则创建一个
				String resource = this.getClass().getResource("/").getFile();
				String root = new File(resource).getParentFile().getParentFile().getCanonicalPath();
				String histPic = root + "/barday.png";
				if (!new File(histPic).exists()) {
					new File(histPic).createNewFile();
				}

				// 真正的柱状图输出
				fos = new FileOutputStream(histPic);
				ChartUtilities.writeChartAsPNG(fos, chart, 400, 300);
			}
		} catch (IOException e) {
			logger.error(e.getLocalizedMessage(), e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
				}
			}
		}

	}

	/**
	 * @Description: 最近一周柱状报表
	 * @return: void
	 */
	public void histogramByWeek() {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		// 设置为前一周
		calendar.add(Calendar.DAY_OF_MONTH, -7);
		// 得到前一周某一天的时间
		Date dBefore = calendar.getTime();

		// 查询前一周组合涉及所有股票的集合
		List<RebalanceStatisticsInfo> resultList = rebalanceStatisticsInfo.selectByCreateTime(dBefore.getTime());

		// 生成柱状图片
		FileOutputStream fos = null;
		try {
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();

			if (resultList != null && !resultList.isEmpty()) {

				// 画出柱状图
				for (RebalanceStatisticsInfo r : resultList) {
					dataset.addValue(Integer.parseInt(r.getTotalweight()), r.getStocksymbol(), "");
				}
				// setCnConfig();
				JFreeChart chart = ChartFactory.createBarChart(MessageUtil.getMessage("message.comm.stockinfo"),
						MessageUtil.getMessage("message.comm.stock"), MessageUtil.getMessage("message.comm.weight"),
						dataset, PlotOrientation.VERTICAL, true, false, false);

				// 判断文件是否存在，不存在则创建一个
				String resource = this.getClass().getResource("/").getFile();
				String root = new File(resource).getParentFile().getParentFile().getCanonicalPath();
				String histPic = root + "/barweek.png";
				if (!new File(histPic).exists()) {
					new File(histPic).createNewFile();
				}

				// 真正的柱状图输出
				fos = new FileOutputStream(histPic);
				ChartUtilities.writeChartAsPNG(fos, chart, 400, 300);
			}
		} catch (IOException e) {
			logger.error(e.getLocalizedMessage(), e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
				}
			}
		}

	}

	/**
	 * @Description: 最近一个月柱状报表
	 * @return: void
	 */
	public void histogramByMonth() {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		// 设置为前一个月
		calendar.add(Calendar.MONTH, -1);
		// 得到前一个月某一天的时间
		Date dBefore = calendar.getTime();

		// 查询前一月组合涉及所有股票的集合
		List<RebalanceStatisticsInfo> resultList = rebalanceStatisticsInfo.selectByCreateTime(dBefore.getTime());

		// 生成柱状图片
		FileOutputStream fos = null;
		try {
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();

			if (resultList != null && !resultList.isEmpty()) {

				// 画出柱状图
				for (RebalanceStatisticsInfo r : resultList) {
					dataset.addValue(Integer.parseInt(r.getTotalweight()), r.getStocksymbol(), "");
				}
				// setCnConfig();
				JFreeChart chart = ChartFactory.createBarChart(MessageUtil.getMessage("message.comm.stockinfo"),
						MessageUtil.getMessage("message.comm.stock"), MessageUtil.getMessage("message.comm.weight"),
						dataset, PlotOrientation.VERTICAL, true, false, false);

				// 判断文件是否存在，不存在则创建一个
				String resource = this.getClass().getResource("/").getFile();
				String root = new File(resource).getParentFile().getParentFile().getCanonicalPath();
				String histPic = root + "/barmonth.png";
				if (!new File(histPic).exists()) {
					new File(histPic).createNewFile();
				}

				// 真正的柱状图输出
				fos = new FileOutputStream(histPic);
				ChartUtilities.writeChartAsPNG(fos, chart, 400, 300);
			}
		} catch (IOException e) {
			logger.error(e.getLocalizedMessage(), e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
				}
			}
		}

	}

	private void setCnConfig() {
		// 创建主题样式
		StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
		// 设置标题字体
		standardChartTheme.setExtraLargeFont(new Font("隶书", Font.BOLD, 20));
		// 设置图例的字体
		standardChartTheme.setRegularFont(new Font("宋书", Font.PLAIN, 15));
		// 设置轴向的字体
		standardChartTheme.setLargeFont(new Font("宋书", Font.PLAIN, 15));
		// 应用主题样式
		ChartFactory.setChartTheme(standardChartTheme);
	}

}
