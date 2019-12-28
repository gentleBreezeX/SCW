package com.gentle.atcrowdfunding.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gentle.atcrowdfunding.bean.TAdmin;
import com.gentle.atcrowdfunding.bean.TAdminExample;
import com.gentle.atcrowdfunding.bean.TAdminExample.Criteria;
import com.gentle.atcrowdfunding.mapper.TAdminMapper;

@RunWith(value=SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-bean.xml",
		"classpath:spring/spring-tx.xml","classpath:spring/spring-mybatis.xml"})
public class MybatisMapperTest {

	@Autowired
	private TAdminMapper adminMapper;
	//创建slf4j日志对象
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Test
	public void test1() throws Exception {
		
		logger.debug("开始测试，时间：{}", System.currentTimeMillis());
		
		TAdminExample adminExample = new TAdminExample();
		
		logger.info("即将查询数据库...");
		
		Criteria criteria = adminExample.createCriteria();
		criteria.andLoginacctEqualTo("zhangsan").andUserpswdEqualTo("123456");
		List<TAdmin> list = adminMapper.selectByExample(adminExample);
		
		logger.warn("查询数据库完毕，结果是：{}, 时间是：{}" , list, System.currentTimeMillis());
		
		logger.error("error级别日志");
		
	}
	
	@Test
	public void test() {
		// spring已经配置了commons-logging，默认输出info级别以上的日志
		//  debug-> info -> warn -> error
		//log.debug("开始测试....");
		logger.debug("开始测试，时间：{}", System.currentTimeMillis());
		TAdminExample e = new TAdminExample();
		e.createCriteria().andLoginacctEqualTo("zhangsan").andUserpswdEqualTo("123456");
		//log.info("即将查询数据库...");
		logger.info("即将查询数据库...");
		List<TAdmin> list = adminMapper.selectByExample(e );
		//log.warn("查询数据库完毕...");
		logger.warn("查询数据库完毕,结果：{} , 结束时间：{}", list , System.currentTimeMillis());
		//System.out.println(list);
		//log.error("查询结果："+list);
		logger.error("hehe");
	}
	
}
