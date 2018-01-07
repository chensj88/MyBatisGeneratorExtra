/**
 *    Copyright 2006-2017 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.generator.codegen.mybatis3.javamapper.elements.ext;

import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;

/**
* @ClassName: DeleteBySelectKeyMethodGenerator
* @Description: 按照实体对象的属性来删除数据JAVA方法生成器
* @author thinkpad
* @date 2018年1月7日
*
 */
public class SelectBySelectKeyMethodGenerator extends
        AbstractJavaMapperMethodGenerator {


    public SelectBySelectKeyMethodGenerator(boolean isSimple) {
        super();
    }

    @Override
    public void addInterfaceElements(Interface interfaze) {
    	 //获取所有实体类的属性
        FullyQualifiedJavaType parameterType = introspectedTable.getRules().calculateAllFieldsClass();
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        //导入List集合
        importedTypes.add(FullyQualifiedJavaType.getNewListInstance());
        importedTypes.add(parameterType);
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        
        //LIST 返回
        FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getNewListInstance();
        FullyQualifiedJavaType listType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        importedTypes.add(listType);
        returnType.addTypeArgument(listType);
        method.setReturnType(returnType);
        
        method.setName(introspectedTable.getSelectBySelectiveKeyStatementId());
        String domainObjectName = introspectedTable.getTableConfiguration().getDomainObjectName();
        String paramName = domainObjectName.substring(0,1).toLowerCase()+domainObjectName.substring(1);
        method.addParameter(new Parameter(parameterType, paramName));
        context.getCommentGenerator().addGeneralMethodComment(method,introspectedTable);
        addMapperAnnotations(method);
        
        addExtraImports(interfaze);
        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(method);
    }

    public void addMapperAnnotations(Method method) {
    }

    public void addExtraImports(Interface interfaze) {
    }
}
