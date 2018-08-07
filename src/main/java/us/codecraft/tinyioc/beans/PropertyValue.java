package us.codecraft.tinyioc.beans;

/**
 * 用于bean的属性注入
 * <property name="n" value="val"/>
 * <property name="n" ref="ref1"/>
 *
 * @author yihua.huang@dianping.com
 */
public class PropertyValue {

    private final String name;

    /**
     * 可以是内置数据类型的包装类，也可以是BeanReference
     */
    private final Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
