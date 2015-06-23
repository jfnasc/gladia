package org.ganimede.dao;

import java.text.SimpleDateFormat;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class BaseDAO {

    /**
	 * 
	 */
    static ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

    protected static SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");

    /**
     * 
     * @return
     */
    protected DataSource getDataSource() {
        return (DataSource) context.getBean("dataSource");
    }
}
