package us.codecraft.tinyioc.beans.io;

import java.net.URL;

/**
 * URL资源加载器
 *
 * @author yihua.huang@dianping.com
 */
public class UrlResourceLoader implements ResourceLoader {

    /**
     * @param location
     * @return
     */
    @Override
    public Resource getResource(String location) {
        URL resource = this.getClass().getClassLoader().getResource(location);
        return new UrlResource(resource);
    }
}
