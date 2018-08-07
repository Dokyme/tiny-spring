package us.codecraft.tinyioc.beans.factory;

import us.codecraft.tinyioc.beans.BeanDefinition;
import us.codecraft.tinyioc.beans.BeanPostProcessor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yihua.huang@dianping.com
 */
public abstract class AbstractBeanFactory implements BeanFactory {

    /**
     * bean名字的集合，这里为了保持bean定义的顺序才选择用List
     */
    private final List<String> beanDefinitionNames = new ArrayList<String>();
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();
    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();

    /**
     * 根据name得到bean，bean的实例化是惰性的，但是beanDefinition是已经加载好的
     *
     * @param name
     * @return
     * @throws Exception
     */
    @Override
    public Object getBean(String name) throws Exception {
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        if (beanDefinition == null) {
            throw new IllegalArgumentException("No bean named " + name + " is defined");
        }
        //每个bean实例保存在beanDefinition里
        Object bean = beanDefinition.getBean();
        if (bean == null) {
            bean = doCreateBean(beanDefinition);
            bean = initializeBean(bean, name);
            //重复的？beanDefinition.setBean(bean);
        }
        return bean;
    }

    /**
     * 初始化bean并调用回掉函数
     *
     * @param bean
     * @param name
     * @return
     * @throws Exception
     */
    protected Object initializeBean(Object bean, String name) throws Exception {
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            bean = beanPostProcessor.postProcessBeforeInitialization(bean, name);
        }

        // TODO:call initialize method
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            bean = beanPostProcessor.postProcessAfterInitialization(bean, name);
        }
        return bean;
    }

    protected Object createBeanInstance(BeanDefinition beanDefinition) throws Exception {
        return beanDefinition.getBeanClass().newInstance();
    }

    /**
     * 注册（添加）一个beanDefinition
     *
     * @param name
     * @param beanDefinition
     * @throws Exception
     */
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) throws Exception {
        beanDefinitionMap.put(name, beanDefinition);
        beanDefinitionNames.add(name);
    }

    /**
     * 提前初始化单例bean
     *
     * @throws Exception
     */
    public void preInstantiateSingletons() throws Exception {
        for (Iterator it = this.beanDefinitionNames.iterator(); it.hasNext(); ) {
            String beanName = (String) it.next();
            getBean(beanName);
        }
    }

    /**
     * 创建bean实例并装配property
     *
     * @param beanDefinition
     * @return
     * @throws Exception
     */
    protected Object doCreateBean(BeanDefinition beanDefinition) throws Exception {
        Object bean = createBeanInstance(beanDefinition);
        beanDefinition.setBean(bean);
        applyPropertyValues(bean, beanDefinition);
        return bean;
    }

    protected void applyPropertyValues(Object bean, BeanDefinition beanDefinition) throws Exception {

    }

    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) throws Exception {
        this.beanPostProcessors.add(beanPostProcessor);
    }

    /**
     * 拿到该容器中type类或者type子类的所有bean
     *
     * @param type
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List getBeansForType(Class type) throws Exception {
        List beans = new ArrayList<Object>();
        for (String beanDefinitionName : beanDefinitionNames) {
            //找到type的所有对象或者子类对象
            if (type.isAssignableFrom(beanDefinitionMap.get(beanDefinitionName).getBeanClass())) {
                beans.add(getBean(beanDefinitionName));
            }
        }
        return beans;
    }

}
