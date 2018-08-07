package us.codecraft.tinyioc.beans;

/**
 * 从配置中读取BeanDefinition
 *
 * @author yihua.huang@dianping.com
 */
public interface BeanDefinitionReader {

    /**
     * 从简单路径字符串读取BeanDefinition
     *
     * @param location
     * @throws Exception
     */
    void loadBeanDefinitions(String location) throws Exception;
}
