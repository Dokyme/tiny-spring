package us.codecraft.tinyioc;

/**
 * Bean引用实体类，封装了<property name="xxx" ref="xxx"/>中的ref对象
 *
 * @author yihua.huang@dianping.com
 */
public class BeanReference {

    private String name;

    private Object bean;

    public BeanReference(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }
}
