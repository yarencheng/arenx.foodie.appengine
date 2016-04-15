package net.arenx.api.bean;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

public class BaseBean {

	public BaseBean() {

	}

	public BaseBean(BaseBean bean) {
		try {
			BeanUtils.copyProperties(this, bean);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException("Failed to copy property", e);
		}
	}
	
	
}
