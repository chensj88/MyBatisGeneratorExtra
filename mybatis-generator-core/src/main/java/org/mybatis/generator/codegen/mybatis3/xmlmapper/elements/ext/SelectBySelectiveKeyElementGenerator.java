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
package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.ext;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

/**
 * 
* @ClassName: DeleteBySelectiveKeyElementGenerator
* @Description: 按照实体对象的属性来删除数据XML元素生成器
* @author thinkpad
* @date 2018年1月7日
*
 */
public class SelectBySelectiveKeyElementGenerator extends
        AbstractXmlElementGenerator {


    public SelectBySelectiveKeyElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

        answer.addAttribute(new Attribute("id", introspectedTable.getSelectBySelectiveKeyStatementId())); //$NON-NLS-1$
        //指定参数类型
        answer.addAttribute(new Attribute("parameterType", introspectedTable.getRules().calculateAllFieldsClass().getFullyQualifiedName()));
        answer.addAttribute(new Attribute("resultMap", introspectedTable.getBaseResultMapId()));
        
        context.getCommentGenerator().addComment(answer);
        StringBuilder sb = new StringBuilder();
        sb.append("select "); 
        answer.addElement(new TextElement(sb.toString()));
        answer.addElement(getBaseColumnListElement());
        sb.setLength(0);
        sb.append("from "); 
        sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));
        
        XmlElement valuesTrimElement = new XmlElement("where");
        answer.addElement(valuesTrimElement);
        for (IntrospectedColumn introspectedColumn : 
        	ListUtilities.removeIdentityAndGeneratedAlwaysColumns(introspectedTable.getAllColumns())) {
        	 if (introspectedColumn.isSequenceColumn()
                     || introspectedColumn.getFullyQualifiedJavaType().isPrimitive()) {
        		 sb.setLength(0);
                 sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
                 sb.append(',');
                 valuesTrimElement.addElement(new TextElement(sb.toString()));
                 continue; 
        	 }
             sb.setLength(0);
             sb.append(introspectedColumn.getJavaProperty());
             sb.append(" != null"); //$NON-NLS-1$
             XmlElement valuesNotNullElement = new XmlElement("if"); //$NON-NLS-1$
             valuesNotNullElement.addAttribute(new Attribute("test", sb.toString())); //$NON-NLS-1$
             sb.setLength(0);
             sb.append("AND "+introspectedColumn.getActualColumnName() +" = ");
             sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
             valuesNotNullElement.addElement(new TextElement(sb.toString()));
             valuesTrimElement.addElement(valuesNotNullElement);
        }
        if (context.getPlugins()
                .sqlMapDeleteByPrimaryKeyElementGenerated(answer,
                        introspectedTable)) {
            parentElement.addElement(answer);
        }
    }
}
