package com.zk.function.ftp.entities;

/**
 * @author panbing@supcon.com
 * @create 2017/10/10 16:35
 * @description
 */
public class FtpEntity {

    private String id;
    private String ip;
    private Integer port;
    private String username;
    private String password;
    private Integer timeout;
    //文件名称(带后缀)
    private String filename;
    //存储文件的路径前缀(ftp代理地址的前缀,最后有分隔符!)
    private String prePath;
    //完整的访问路径
    private String urlPath;
    //文件类型
    private String fileType;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getPrePath() {
        return prePath;
    }

    public void setPrePath(String prePath) {
        this.prePath = prePath;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * +++++++++++++++++自定义属性 start++++++++++++++++++++++
     */


    private String c_ip;
    private Integer c_port;
    //文件名称(模糊)
    private String c_filename;
    //文件名称(精确)
    private String c_jq_filename;
    //存储文件的路径前缀(即所在的文件夹)
    private String c_prePath;
    //文件的类型
    private String c_fileType;

    private String c_filename_flag;

    public String getC_ip() {
        return c_ip;
    }

    public void setC_ip(String c_ip) {
        this.c_ip = c_ip;
    }

    public Integer getC_port() {
        return c_port;
    }

    public void setC_port(Integer c_port) {
        this.c_port = c_port;
    }

    public String getC_filename() {
        return c_filename;
    }

    public void setC_filename(String c_filename) {
        this.c_filename = c_filename;
    }

    public String getC_jq_filename() {
        return c_jq_filename;
    }

    public void setC_jq_filename(String c_jq_filename) {
        this.c_jq_filename = c_jq_filename;
    }

    public String getC_prePath() {
        return c_prePath;
    }

    public void setC_prePath(String c_prePath) {
        this.c_prePath = c_prePath;
    }

    public String getC_fileType() {
        return c_fileType;
    }

    public void setC_fileType(String c_fileType) {
        this.c_fileType = c_fileType;
    }

    public String getC_filename_flag() {
        return c_filename_flag;
    }

    public void setC_filename_flag(String c_filename_flag) {
        this.c_filename_flag = c_filename_flag;
    }

    /**
     * +++++++++++++++++自定义属性 end++++++++++++++++++++++
     */


    @Override
    public String toString() {
        return "FtpEntity{" +
                "id='" + id + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", filename='" + filename + '\'' +
                ", prePath='" + prePath + '\'' +
                ", fileType='" + fileType + '\'' +
                '}';
    }
}
