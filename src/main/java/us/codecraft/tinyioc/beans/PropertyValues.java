package us.codecraft.tinyioc.beans;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 包装一个对象所有的PropertyValue。<br/>
 * 为什么封装而不是直接用List?因为可以封装一些操作。
 *
 * @author yihua.huang@dianping.com
 */
public class PropertyValues {

    /**
     * 为什么不用Map呢
     */
    private final List<PropertyValue> propertyValueList = new ArrayList<PropertyValue>();

    private final Set<String> propertyNameSet = new HashSet<String>();

    public PropertyValues() {
    }

    public void addPropertyValue(PropertyValue pv) {
        if (propertyNameSet.contains(pv.getName())) {
            throw new RuntimeException("Duplicated property name:" + pv.getName());
        } else {
            propertyNameSet.add(pv.getName());
            this.propertyValueList.add(pv);
        }
    }

    public List<PropertyValue> getPropertyValues() {
        return this.propertyValueList;
    }

}
