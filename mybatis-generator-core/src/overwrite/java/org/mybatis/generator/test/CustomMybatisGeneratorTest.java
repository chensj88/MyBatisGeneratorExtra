package org.mybatis.generator.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ShellRunner;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

public class CustomMybatisGeneratorTest {

	@Test
	public void test() throws IOException, XMLParserException, InvalidConfigurationException, SQLException, InterruptedException {
		List<String> warnings = new ArrayList<String>();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(this.getClass().getClassLoader().getResourceAsStream("GeneratorConfig.xml"));
            
        DefaultShellCallback shellCallback = new DefaultShellCallback(true);

        try {
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, shellCallback, warnings);
            myBatisGenerator.generate(null, null, null);
        } catch (InvalidConfigurationException e) {
            assertEquals(2, e.getErrors().size());
            throw e;
        }
	}
	
	/**
	 * 测试xml文件未重写的问题<br>
	 * 问题出现在下面
	 * @see org.mybatis.generator.api.MyBatisGenerator#getGeneratedXmlFiles()
	 * 增加了gxf.isMergeable()的判断导致xml未重写
	* @Title: testShellRunner
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param     参数
	* @return void    返回类型
	* @throws
	 */
	@Test
	public void testShellRunner() {
		String[] args = {"-configfile","D:\\code\\MyBatisGeneratorExtra\\mybatis-generator-core\\src\\overwrite\\java\\GeneratorConfig.xml","-overwrite"};
		ShellRunner.main(args);
	}
}
