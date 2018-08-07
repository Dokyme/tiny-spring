package us.codecraft.tinyioc.beans.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * URL资源实体
 *
 * @author yihua.huang@dianping.com
 */
public class UrlResource implements Resource {

    private final URL url;

    public UrlResource(URL url) {
        this.url = url;
    }

    /**
     * 从外部资源URL获得数据的输入流。
     *
     * @return
     * @throws IOException
     */
    @Override
    public InputStream getInputStream() throws IOException {
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();
        return urlConnection.getInputStream();
    }
}
