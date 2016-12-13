/**  
 * Copyright © 2016 Seven Color. All rights reserved.
 *
 * @Description: 服务端（DOA、Service、Controller统一部署在一起，统称服务端）抛出的统一检查异常，由Controller层统一处理
 * @author: yinhong  
 * @date: 2016年11月26日 上午11:16:17
 * @version: V1.0  
 */
package com.sevencolor.service.comm;

import com.sevencolor.comm.exception.BaseException;

/**
 * @Description: 服务端统一检查异常，Controller层统一处理
 */
public class BusinessException extends BaseException {

	private static final long serialVersionUID = 8126645039537170707L;

	public BusinessException(String code, Throwable cause, Object[] messageArgs) {
		super(code, cause, messageArgs);
	}

	public BusinessException(String code, Throwable cause) {
		super(code, cause);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}
}
