package com.sevencolor.domain.pojo;

public class CubeRebalanceStatisticsInfo {
  private Long dbid;

  private String stocksymbol;

  private String stockname;

  private String totalweight;

  private Long createtime;

  public Long getDbid() {
    return dbid;
  }

  public void setDbid(Long dbid) {
    this.dbid = dbid;
  }

  public String getStocksymbol() {
    return stocksymbol;
  }

  public void setStocksymbol(String stocksymbol) {
    this.stocksymbol = stocksymbol == null ? null : stocksymbol.trim();
  }

  public String getStockname() {
    return stockname;
  }

  public void setStockname(String stockname) {
    this.stockname = stockname == null ? null : stockname.trim();
  }

  public String getTotalweight() {
    return totalweight;
  }

  public void setTotalweight(String totalweight) {
    this.totalweight = totalweight == null ? null : totalweight.trim();
  }

  public Long getCreatetime() {
    return createtime;
  }

  public void setCreatetime(Long createtime) {
    this.createtime = createtime == null ? null : createtime;
  }
}
