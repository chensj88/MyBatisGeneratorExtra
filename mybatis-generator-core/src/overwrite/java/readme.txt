主要修改如下：
1、org.mybatis.generator.api.IntrospectedTable 
	1.1、在[InternalAttribute]增加新的属性[InternalAttribute]
	1.2、在[calculateXmlAttributes]增加XML需要生成的方法
2、org.mybatis.generator.codegen.mybatis3.javamapper.JavaMapperGenerator
	针对上述修改增加新的类与方法
3、org.mybatis.generator.codegen.mybatis3.xmlmapper.XMLMapperGenerator
        针对上述修改增加新的类与方法