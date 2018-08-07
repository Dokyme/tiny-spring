package us.codecraft.tinyioc.beans.io;

/**
 * 资源加载器接口
 */
public interface ResourceLoader {

    /**
     * 根据简单路径名（URL）构造一个Resource资源。
     *
     * @param location
     * @return
     */
    Resource getResource(String location);
}
