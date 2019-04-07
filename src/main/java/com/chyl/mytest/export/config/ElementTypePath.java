package com.chyl.mytest.export.config;

/**
 * @author only-lilei
 * @create 2018-01-22 下午6:07
 **/
public enum ElementTypePath {
    SET("java.util.Set"),MAP("java.util.Map"),LIST("java.util.List");

    private String path;

    private ElementTypePath(String path){
        this.path = path;
    }

    /**
     *
     * 获取元素
     * @param path
     * @return  ElementTypePath 返回类型
     * @throws
     */
    public static ElementTypePath getElementTypePath(String path){
        for(ElementTypePath elementTypePath:ElementTypePath.values()){
            if(elementTypePath.path.equals(path)){
                return elementTypePath;
            }
        }
        return null;

    }

    @SuppressWarnings("static-access")
    @Override
    public String toString() {
        return this.valueOf(path).getPath();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
