/**
 * Copyright © 2016 Seven Color. All rights reserved.
 *
 * @Description: 实体基类
 * @author: yinhong
 * @date: 2016年11月26日 上午11:16:17
 * @version: V1.0
 */
package com.sevencolor.domain.comm;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @Description: 实体基类
 */
public class BaseEntity implements Serializable {

  private static final long serialVersionUID = -4695343715095326027L;

  private Long id;

  // 0表示正常使用，1表示已经停止使用
  private String state;

  private Date createtime;
  private Date updatetime;

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public Date getCreatedtime() {
    return createtime;
  }

  public void setCreatedtime(Date createdtime) {
    this.createtime = createdtime;
  }

  public Date getUpdatedtime() {
    return updatetime;
  }

  public void setUpdatedtime(Date updatedtime) {
    this.updatetime = updatedtime;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    BaseEntity that = (BaseEntity) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return JSON.toJSONString(this, SerializerFeature.WriteMapNullValue);
  }

  public Set<Long> getIdSetBySelfCollection(Collection<? extends BaseEntity> baseEntityCollection) {
    Set<Long> idSet = new HashSet<Long>();
    if (CollectionUtils.isNotEmpty(baseEntityCollection)) {
      for (BaseEntity baseEntity : baseEntityCollection) {
        idSet.add(baseEntity.getId());
      }
    }
    return idSet;
  }

  public Map<Long, ? extends BaseEntity> getIdAndSelfMapBySelfCollection(
      Collection<? extends BaseEntity> baseEntityCollection) {
    Map<Long, BaseEntity> idAndSelfMap = new HashMap<Long, BaseEntity>();
    if (CollectionUtils.isNotEmpty(baseEntityCollection)) {
      for (BaseEntity baseEntity : baseEntityCollection) {
        idAndSelfMap.put(baseEntity.getId(), baseEntity);
      }
    }
    return idAndSelfMap;
  }

}
