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
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sevencolor.comm.util.MessageUtil;
import com.sevencolor.domain.dao.XQDailyCubeRebStatDao;
import com.sevencolor.domain.dao.XQMonthlyCubeRebStatDao;
import com.sevencolor.domain.dao.XQSummeryCubeDao;
import com.sevencolor.domain.dao.XQYearlyCubeRebStatDao;
import com.sevencolor.domain.pojo.CubeRebalanceStatisticsInfo;

/**
 * @Description: 根据组合中的股票信息，按天、周、月生成柱状报表图片
 */
@Component
public class CubeRebHistogramTask {

	private static final Logger logger = LoggerFactory.getLogger(CubeRebHistogramTask.class);

	@Autowired
	private XQDailyCubeRebStatDao xqDailyCubeRebStatDao;
	@Autowired
	private XQMonthlyCubeRebStatDao xqMonthlyCubeRebStatDao;
	@Autowired
	private XQYearlyCubeRebStatDao xqYearlyCubeRebStatDao;
	@Autowired
	private XQSummeryCubeDao xqSummeryCubeDao;

	/**
	 * 
	 * @Description: 日收益排名靠前的组合报表
	 * @return: void
	 */
	public void xqDailyTopNCubeHistogram() {
		xqDailyCubeHistogramLastDay();
		xqDailyCubeHistogramLastWeek();
		xqDailyCubeHistogramLastMonth();
	}

	/**
	 * 
	 * @Description: 月收益排名靠前的组合报表
	 * @return: void
	 */
	public void xqMonthlyTopNHistogram() {
		xqMonthlyCubeHistogramLastDay();
		xqMonthlyCubeHistogramLastWeek();
		xqMonthlyCubeHistogramLastMonth();
	}

	/**
	 * 
	 * @Description: 年收益排名靠前组合的报表
	 * @return: void
	 */
	public void xqYearlyTopNHistogram() {
		xqYearlyCubeHistogramLastDay();
		xqYearlyCubeHistogramLastWeek();
		xqYearlyCubeHistogramLastMonth();
	}

	/**
	 * 
	 * @Description: 年收益与月收益排名靠前组合所共同拥有股票的报表（2天内）
	 * @return: void
	 */
	public void xqSummeryTopNHistogram() {
		xqSummeryCubeHistogramLastWeek();
	}

	private void xqSummeryCubeHistogramLastWeek() {

		List<CubeRebalanceStatisticsInfo> resultList = xqSummeryCubeDao.selectSummeryCubeRebalanceStatistics();

		// 生成柱状图片
		FileOutputStream fos = null;
		try {
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();

			if (resultList != null && !resultList.isEmpty()) {

				// 画出柱状图
				for (CubeRebalanceStatisticsInfo r : resultList) {
					dataset.addValue(Double.parseDouble(r.getTotalweight()), r.getStockname(), r.getStocksymbol());
				}
				setZhCnConfig();
				JFreeChart chart = ChartFactory.createBarChart3D(MessageUtil.getMessage("message.comm.stockinfo.2day"),
						MessageUtil.getMessage("message.comm.stock"), MessageUtil.getMessage("message.comm.weight"),
						dataset, PlotOrientation.VERTICAL, true, false, false);
				CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
				setAxisParam(categoryplot);

				// 判断文件是否存在，不存在则创建一个
				String resource = this.getClass().getResource("/").getFile();
				String root = new File(resource).getParentFile().getParentFile().getCanonicalPath();
				String histPic = root + "/xqsummerybar.png";
				if (!new File(histPic).exists()) {
					new File(histPic).createNewFile();
				}

				// 真正的柱状图输出
				fos = new FileOutputStream(histPic);
				ChartUtilities.writeChartAsPNG(fos, chart, 600, 600);
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
	 * @Description: 最近一天柱状报表
	 * @return: void
	 */
	private void xqDailyCubeHistogramLastDay() {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		// 设置为前一天
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		// 得到前一天的时间
		Date dBefore = calendar.getTime();

		// 查询前一天组合涉及所有股票的集合
		List<CubeRebalanceStatisticsInfo> resultList = xqDailyCubeRebStatDao.selectByCreateTime(dBefore.getTime());

		// 生成柱状图片
		FileOutputStream fos = null;
		try {
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();

			if (resultList != null && !resultList.isEmpty()) {

				// 画出柱状图
				for (CubeRebalanceStatisticsInfo r : resultList) {
					dataset.addValue(Double.parseDouble(r.getTotalweight()), r.getStockname(), r.getStocksymbol());
				}
				setZhCnConfig();
				JFreeChart chart = ChartFactory.createBarChart3D(MessageUtil.getMessage("message.comm.stockinfo.day"),
						MessageUtil.getMessage("message.comm.stock"), MessageUtil.getMessage("message.comm.weight"),
						dataset, PlotOrientation.VERTICAL, true, false, false);
				CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
				setAxisParam(categoryplot);

				// 判断文件是否存在，不存在则创建一个
				String resource = this.getClass().getResource("/").getFile();
				String root = new File(resource).getParentFile().getParentFile().getCanonicalPath();
				String histPic = root + "/xqdailybarbyday.png";
				if (!new File(histPic).exists()) {
					new File(histPic).createNewFile();
				}

				// 真正的柱状图输出
				fos = new FileOutputStream(histPic);
				ChartUtilities.writeChartAsPNG(fos, chart, 600, 600);
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
	 * @Description: 最近一天柱状报表
	 * @return: void
	 */
	private void xqMonthlyCubeHistogramLastDay() {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		// 设置为前一天
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		// 得到前一天的时间
		Date dBefore = calendar.getTime();

		// 查询前一天组合涉及所有股票的集合
		List<CubeRebalanceStatisticsInfo> resultList = xqMonthlyCubeRebStatDao.selectByCreateTime(dBefore.getTime());

		// 生成柱状图片
		FileOutputStream fos = null;
		try {
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();

			if (resultList != null && !resultList.isEmpty()) {

				// 画出柱状图
				for (CubeRebalanceStatisticsInfo r : resultList) {
					dataset.addValue(Double.parseDouble(r.getTotalweight()), r.getStockname(), r.getStocksymbol());
				}
				setZhCnConfig();
				JFreeChart chart = ChartFactory.createBarChart3D(MessageUtil.getMessage("message.comm.stockinfo.day"),
						MessageUtil.getMessage("message.comm.stock"), MessageUtil.getMessage("message.comm.weight"),
						dataset, PlotOrientation.VERTICAL, true, false, false);
				CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
				setAxisParam(categoryplot);

				// 判断文件是否存在，不存在则创建一个
				String resource = this.getClass().getResource("/").getFile();
				String root = new File(resource).getParentFile().getParentFile().getCanonicalPath();
				String histPic = root + "/xqmonthlybarbyday.png";
				if (!new File(histPic).exists()) {
					new File(histPic).createNewFile();
				}

				// 真正的柱状图输出
				fos = new FileOutputStream(histPic);
				ChartUtilities.writeChartAsPNG(fos, chart, 600, 600);
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
	 * @Description: 最近一天柱状报表
	 * @return: void
	 */
	private void xqYearlyCubeHistogramLastDay() {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		// 设置为前一天
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		// 得到前一天的时间
		Date dBefore = calendar.getTime();

		// 查询前一天组合涉及所有股票的集合
		List<CubeRebalanceStatisticsInfo> resultList = xqYearlyCubeRebStatDao.selectByCreateTime(dBefore.getTime());

		// 生成柱状图片
		FileOutputStream fos = null;
		try {
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();

			if (resultList != null && !resultList.isEmpty()) {

				// 画出柱状图
				for (CubeRebalanceStatisticsInfo r : resultList) {
					dataset.addValue(Double.parseDouble(r.getTotalweight()), r.getStockname(), r.getStocksymbol());
				}
				setZhCnConfig();
				JFreeChart chart = ChartFactory.createBarChart3D(MessageUtil.getMessage("message.comm.stockinfo.day"),
						MessageUtil.getMessage("message.comm.stock"), MessageUtil.getMessage("message.comm.weight"),
						dataset, PlotOrientation.VERTICAL, true, false, false);
				CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
				setAxisParam(categoryplot);

				// 判断文件是否存在，不存在则创建一个
				String resource = this.getClass().getResource("/").getFile();
				String root = new File(resource).getParentFile().getParentFile().getCanonicalPath();
				String histPic = root + "/xqyearlybarbyday.png";
				if (!new File(histPic).exists()) {
					new File(histPic).createNewFile();
				}

				// 真正的柱状图输出
				fos = new FileOutputStream(histPic);
				ChartUtilities.writeChartAsPNG(fos, chart, 600, 600);
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
	private void xqMonthlyCubeHistogramLastWeek() {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		// 设置为前一周
		calendar.add(Calendar.DAY_OF_MONTH, -7);
		// 得到前一周某一天的时间
		Date dBefore = calendar.getTime();

		// 查询前一周组合涉及所有股票的集合
		List<CubeRebalanceStatisticsInfo> resultList = xqMonthlyCubeRebStatDao.selectByCreateTime(dBefore.getTime());

		// 生成柱状图片
		FileOutputStream fos = null;
		try {
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();

			if (resultList != null && !resultList.isEmpty()) {

				// 画出柱状图
				for (CubeRebalanceStatisticsInfo r : resultList) {
					dataset.addValue(Double.parseDouble(r.getTotalweight()), r.getStockname(), r.getStocksymbol());
				}
				setZhCnConfig();
				JFreeChart chart = ChartFactory.createBarChart3D(MessageUtil.getMessage("message.comm.stockinfo.week"),
						MessageUtil.getMessage("message.comm.stock"), MessageUtil.getMessage("message.comm.weight"),
						dataset, PlotOrientation.VERTICAL, true, false, false);
				CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
				setAxisParam(categoryplot);

				// 判断文件是否存在，不存在则创建一个
				String resource = this.getClass().getResource("/").getFile();
				String root = new File(resource).getParentFile().getParentFile().getCanonicalPath();
				String histPic = root + "/xqmonthlybarbyweek.png";
				if (!new File(histPic).exists()) {
					new File(histPic).createNewFile();
				}

				// 真正的柱状图输出
				fos = new FileOutputStream(histPic);
				ChartUtilities.writeChartAsPNG(fos, chart, 600, 600);
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
	private void xqDailyCubeHistogramLastWeek() {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		// 设置为前一周
		calendar.add(Calendar.DAY_OF_MONTH, -7);
		// 得到前一周某一天的时间
		Date dBefore = calendar.getTime();

		// 查询前一周组合涉及所有股票的集合
		List<CubeRebalanceStatisticsInfo> resultList = xqDailyCubeRebStatDao.selectByCreateTime(dBefore.getTime());

		// 生成柱状图片
		FileOutputStream fos = null;
		try {
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();

			if (resultList != null && !resultList.isEmpty()) {

				// 画出柱状图
				for (CubeRebalanceStatisticsInfo r : resultList) {
					dataset.addValue(Double.parseDouble(r.getTotalweight()), r.getStockname(), r.getStocksymbol());
				}
				setZhCnConfig();
				JFreeChart chart = ChartFactory.createBarChart3D(MessageUtil.getMessage("message.comm.stockinfo.week"),
						MessageUtil.getMessage("message.comm.stock"), MessageUtil.getMessage("message.comm.weight"),
						dataset, PlotOrientation.VERTICAL, true, false, false);
				CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
				setAxisParam(categoryplot);

				// 判断文件是否存在，不存在则创建一个
				String resource = this.getClass().getResource("/").getFile();
				String root = new File(resource).getParentFile().getParentFile().getCanonicalPath();
				String histPic = root + "/xqdailybarbyweek.png";
				if (!new File(histPic).exists()) {
					new File(histPic).createNewFile();
				}

				// 真正的柱状图输出
				fos = new FileOutputStream(histPic);
				ChartUtilities.writeChartAsPNG(fos, chart, 600, 600);
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
	private void xqYearlyCubeHistogramLastWeek() {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		// 设置为前一周
		calendar.add(Calendar.DAY_OF_MONTH, -7);
		// 得到前一周某一天的时间
		Date dBefore = calendar.getTime();

		// 查询前一周组合涉及所有股票的集合
		List<CubeRebalanceStatisticsInfo> resultList = xqYearlyCubeRebStatDao.selectByCreateTime(dBefore.getTime());

		// 生成柱状图片
		FileOutputStream fos = null;
		try {
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();

			if (resultList != null && !resultList.isEmpty()) {

				// 画出柱状图
				for (CubeRebalanceStatisticsInfo r : resultList) {
					dataset.addValue(Double.parseDouble(r.getTotalweight()), r.getStockname(), r.getStocksymbol());
				}
				setZhCnConfig();
				JFreeChart chart = ChartFactory.createBarChart3D(MessageUtil.getMessage("message.comm.stockinfo.week"),
						MessageUtil.getMessage("message.comm.stock"), MessageUtil.getMessage("message.comm.weight"),
						dataset, PlotOrientation.VERTICAL, true, false, false);
				CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
				setAxisParam(categoryplot);

				// 判断文件是否存在，不存在则创建一个
				String resource = this.getClass().getResource("/").getFile();
				String root = new File(resource).getParentFile().getParentFile().getCanonicalPath();
				String histPic = root + "/xqyearlybarbyweek.png";
				if (!new File(histPic).exists()) {
					new File(histPic).createNewFile();
				}

				// 真正的柱状图输出
				fos = new FileOutputStream(histPic);
				ChartUtilities.writeChartAsPNG(fos, chart, 600, 600);
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
	private void xqDailyCubeHistogramLastMonth() {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		// 设置为前一个月
		calendar.add(Calendar.MONTH, -1);
		// 得到前一个月某一天的时间
		Date dBefore = calendar.getTime();

		// 查询前一月组合涉及所有股票的集合
		List<CubeRebalanceStatisticsInfo> resultList = xqDailyCubeRebStatDao.selectByCreateTime(dBefore.getTime());

		// 生成柱状图片
		FileOutputStream fos = null;
		try {
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();

			if (resultList != null && !resultList.isEmpty()) {

				// 画出柱状图
				for (CubeRebalanceStatisticsInfo r : resultList) {
					dataset.addValue(Double.parseDouble(r.getTotalweight()), r.getStockname(), r.getStocksymbol());
				}
				setZhCnConfig();
				JFreeChart chart = ChartFactory.createBarChart3D(MessageUtil.getMessage("message.comm.stockinfo.month"),
						MessageUtil.getMessage("message.comm.stock"), MessageUtil.getMessage("message.comm.weight"),
						dataset, PlotOrientation.VERTICAL, true, false, false);
				CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
				setAxisParam(categoryplot);

				// 判断文件是否存在，不存在则创建一个
				String resource = this.getClass().getResource("/").getFile();
				String root = new File(resource).getParentFile().getParentFile().getCanonicalPath();
				String histPic = root + "/xqdailybarbymonth.png";
				if (!new File(histPic).exists()) {
					new File(histPic).createNewFile();
				}

				// 真正的柱状图输出
				fos = new FileOutputStream(histPic);
				ChartUtilities.writeChartAsPNG(fos, chart, 600, 600);
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
	private void xqMonthlyCubeHistogramLastMonth() {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		// 设置为前一个月
		calendar.add(Calendar.MONTH, -1);
		// 得到前一个月某一天的时间
		Date dBefore = calendar.getTime();

		// 查询前一月组合涉及所有股票的集合
		List<CubeRebalanceStatisticsInfo> resultList = xqMonthlyCubeRebStatDao.selectByCreateTime(dBefore.getTime());

		// 生成柱状图片
		FileOutputStream fos = null;
		try {
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();

			if (resultList != null && !resultList.isEmpty()) {

				// 画出柱状图
				for (CubeRebalanceStatisticsInfo r : resultList) {
					dataset.addValue(Double.parseDouble(r.getTotalweight()), r.getStockname(), r.getStocksymbol());
				}
				setZhCnConfig();
				JFreeChart chart = ChartFactory.createBarChart3D(MessageUtil.getMessage("message.comm.stockinfo.month"),
						MessageUtil.getMessage("message.comm.stock"), MessageUtil.getMessage("message.comm.weight"),
						dataset, PlotOrientation.VERTICAL, true, false, false);
				CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
				setAxisParam(categoryplot);

				// 判断文件是否存在，不存在则创建一个
				String resource = this.getClass().getResource("/").getFile();
				String root = new File(resource).getParentFile().getParentFile().getCanonicalPath();
				String histPic = root + "/xqmonthlybarbymonth.png";
				if (!new File(histPic).exists()) {
					new File(histPic).createNewFile();
				}

				// 真正的柱状图输出
				fos = new FileOutputStream(histPic);
				ChartUtilities.writeChartAsPNG(fos, chart, 600, 600);
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
	private void xqYearlyCubeHistogramLastMonth() {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		// 设置为前一个月
		calendar.add(Calendar.MONTH, -1);
		// 得到前一个月某一天的时间
		Date dBefore = calendar.getTime();

		// 查询前一月组合涉及所有股票的集合
		List<CubeRebalanceStatisticsInfo> resultList = xqYearlyCubeRebStatDao.selectByCreateTime(dBefore.getTime());

		// 生成柱状图片
		FileOutputStream fos = null;
		try {
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();

			if (resultList != null && !resultList.isEmpty()) {

				// 画出柱状图
				for (CubeRebalanceStatisticsInfo r : resultList) {
					dataset.addValue(Double.parseDouble(r.getTotalweight()), r.getStockname(), r.getStocksymbol());
				}
				setZhCnConfig();
				JFreeChart chart = ChartFactory.createBarChart3D(MessageUtil.getMessage("message.comm.stockinfo.month"),
						MessageUtil.getMessage("message.comm.stock"), MessageUtil.getMessage("message.comm.weight"),
						dataset, PlotOrientation.VERTICAL, true, false, false);
				CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
				setAxisParam(categoryplot);

				// 判断文件是否存在，不存在则创建一个
				String resource = this.getClass().getResource("/").getFile();
				String root = new File(resource).getParentFile().getParentFile().getCanonicalPath();
				String histPic = root + "/xqyearlybarbymonth.png";
				if (!new File(histPic).exists()) {
					new File(histPic).createNewFile();
				}

				// 真正的柱状图输出
				fos = new FileOutputStream(histPic);
				ChartUtilities.writeChartAsPNG(fos, chart, 600, 600);
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

	private void setAxisParam(CategoryPlot categoryplot) {
		CategoryAxis domainAxis = categoryplot.getDomainAxis();
		// X轴上的Lable让其45度倾斜
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		// 设置距离图片左端距离
		domainAxis.setLowerMargin(0.0);
		// 设置距离图片右端距离
		domainAxis.setUpperMargin(0.0);
	}

	private void setZhCnConfig() {
		// 创建主题样式
		StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
		// 设置标题字体
		standardChartTheme.setExtraLargeFont(new Font("微软雅黑", Font.BOLD, 20));
		// 设置图例的字体
		standardChartTheme.setRegularFont(new Font("宋书", Font.PLAIN, 15));
		// 设置轴向的字体
		standardChartTheme.setLargeFont(new Font("仿宋", Font.PLAIN, 15));
		// 应用主题样式
		ChartFactory.setChartTheme(standardChartTheme);
	}

}
