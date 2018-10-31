package com.bjsx.orm.bean;

/**
 * 管理配置信息
 * @author ganwu
 *
 */
public class Configuration {
    /*spring.datasource.driverClassName = com.mysql.jdbc.Driver
    spring.datasource.url = jdbc:mysql://localhost:3306/springboot?useSSL=false&useUnicode=true&characterEncoding=utf-8
    spring.datasource.username = admin
    spring.datasource.password = 123456*/
    /**
     * 驱动类
     */
    private String driver;
    /**
     * jdbc地址
     */
    private String url;
    /**
     * 数据库用户名
     */
    private String user;
    /**
     * 数据库用户密码
     */
    private String pwd;
    /**
     * 正在使用哪个数据库
     */
    private String usingDB;
    /**
     * 项目源码路径
     */
    private String srcPath;

    /**
     * 扫描生产java类的包(po：persistence object 持久化对象)
     */
    private String poPackage;
    /**
     * 获取查询对象
     */
    private String queryClass;
    /**
     * 最大连接池数
     */
    private Integer POOL_MAX_SIZE;
    /**
     * 最小连接池数
     */
    private Integer POOL_MIN_SIZE;

    public Integer getPOOL_MAX_SIZE() {
        return POOL_MAX_SIZE;
    }

    public void setPOOL_MAX_SIZE(Integer POOL_MAX_SIZE) {
        this.POOL_MAX_SIZE = POOL_MAX_SIZE;
    }

    public Integer getPOOL_MIN_SIZE() {
        return POOL_MIN_SIZE;
    }

    public void setPOOL_MIN_SIZE(Integer POOL_MIN_SIZE) {
        this.POOL_MIN_SIZE = POOL_MIN_SIZE;
    }

    public String getQueryClass() {
        return queryClass;
    }

    public void setQueryClass(String queryClass) {
        this.queryClass = queryClass;
    }

    public Configuration(String driver, String url, String user, String pwd, String usingDB, String srcPath, String poPackage, String queryClass) {
        this.driver = driver;
        this.url = url;
        this.user = user;
        this.pwd = pwd;
        this.usingDB = usingDB;
        this.srcPath = srcPath;
        this.poPackage = poPackage;
        this.queryClass = queryClass;
    }

    public Configuration() {
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUsingDB() {
        return usingDB;
    }

    public void setUsingDB(String usingDB) {
        this.usingDB = usingDB;
    }

    public String getSrcPath() {
        return srcPath;
    }

    public void setSrcPath(String srcPath) {
        this.srcPath = srcPath;
    }

    public String getPoPackage() {
        return poPackage;
    }

    public void setPoPackage(String poPackage) {
        this.poPackage = poPackage;
    }
}
