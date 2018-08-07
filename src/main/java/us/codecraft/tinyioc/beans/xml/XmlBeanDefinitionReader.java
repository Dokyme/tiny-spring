package us.codecraft.tinyioc.beans.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import us.codecraft.tinyioc.BeanReference;
import us.codecraft.tinyioc.beans.AbstractBeanDefinitionReader;
import us.codecraft.tinyioc.beans.BeanDefinition;
import us.codecraft.tinyioc.beans.PropertyValue;
import us.codecraft.tinyioc.beans.io.ResourceLoader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

/**
 * 从xml中读取bean定义的类
 *
 * @author yihua.huang@dianping.com
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public XmlBeanDefinitionReader(ResourceLoader resourceLoader) {
        super(resourceLoader);
    }

    /**
     * 从简单路径字符串加载beanDefinition
     *
     * @param location
     * @throws Exception
     */
    @Override
    public void loadBeanDefinitions(String location) throws Exception {
        InputStream inputStream = getResourceLoader().getResource(location).getInputStream();
        doLoadBeanDefinitions(inputStream);
    }

    /**
     * 从inputStream中读取bean配置文档并解析
     *
     * @param inputStream
     * @throws Exception
     */
    protected void doLoadBeanDefinitions(InputStream inputStream) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        // 解析bean，这里使用的是javax的xml解析器，也应该可以使用其他的解析器比如就jsoup？
        Document doc = docBuilder.parse(inputStream);
        // 遍历并注册doc中的所有bean
        registerBeanDefinitions(doc);
        inputStream.close();
    }

    public void registerBeanDefinitions(Document doc) {
        Element root = doc.getDocumentElement();

        parseBeanDefinitions(root);
    }

    protected void parseBeanDefinitions(Element root) {
        NodeList nl = root.getChildNodes();
        // 只遍历root节点的直接子节点（一级）
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (node instanceof Element) {
                Element ele = (Element) node;
                // 处理每一个bean
                processBeanDefinition(ele);
            }
        }
    }

    /**
     * 读取一个bean的定义并构造beanDefinition对象。
     *
     * @param ele
     */
    protected void processBeanDefinition(Element ele) {
        String beanId = ele.getAttribute("id");
        String className = ele.getAttribute("class");
        BeanDefinition beanDefinition = new BeanDefinition();
        processProperty(ele, beanDefinition);
        beanDefinition.setBeanClassName(className);
        getRegistry().put(beanId, beanDefinition);
    }

    /**
     * 处理一个bean节点下的property列表
     *
     * @param ele
     * @param beanDefinition
     */
    private void processProperty(Element ele, BeanDefinition beanDefinition) {
        //<bean>
        //  <property name="xxx1" value="xxx"/>
        //  <property name="xxx2" ref="xxxref"/>
        //</bean>
        NodeList propertyNode = ele.getElementsByTagName("property");
        for (int i = 0; i < propertyNode.getLength(); i++) {
            Node node = propertyNode.item(i);
            if (node instanceof Element) {
                Element propertyEle = (Element) node;
                String name = propertyEle.getAttribute("name");
                String value = propertyEle.getAttribute("value");
                //<property name="xxx" value="xxx"/>
                if (value != null && value.length() > 0) {
                    beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, value));
                } else {
                    //<property name="xxx" ref="xxx"/>
                    String ref = propertyEle.getAttribute("ref");
                    if (ref == null || ref.length() == 0) {
                        throw new IllegalArgumentException("Configuration problem: <property> element for property '"
                                + name + "' must specify a ref or value");
                    }
                    BeanReference beanReference = new BeanReference(ref);
                    beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, beanReference));
                }
            }
        }
    }
}
