package us.codecraft.tinyioc.beans;

/**
 * Bean初始化回调
 */
public interface BeanPostProcessor {

    Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception;

    Object postProcessAfterInitialization(Object bean, String beanName) throws Exception;

}