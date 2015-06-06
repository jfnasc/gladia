package org.ganimede.dao;


import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class BaseDAO {
	
	/**
	 * 
	 */
	static ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

	/**
	 * 
	 * @return
	 */
	protected DataSource getDataSource() {
		return (DataSource) context.getBean("dataSource");
	}
}
