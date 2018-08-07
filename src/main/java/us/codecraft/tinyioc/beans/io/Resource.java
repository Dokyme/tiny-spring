package us.codecraft.tinyioc.beans.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * Resource是spring内部定位资源的接口。
 *
 * @author yihua.huang@dianping.com
 */
public interface Resource {

    /**
     * 获得资源输入流，其子类可以以自己的方式从不同的资源、以不同的方式返回输入流。
     *
     * @return
     * @throws IOException
     */
    InputStream getInputStream() throws IOException;
}
