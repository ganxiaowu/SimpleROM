package com.bjsx.orm.bean;

import java.util.List;
import java.util.Map;

/**
 * 存储表信息
 */
public class TableInfo {
    /**
     * 表名
     */
    private String tname;
    /**
     * 所有字段信息
     */
    private Map<String, ColumnInfo> columns;
    /**
     * 唯一主键
     */
    private ColumnInfo onlyPrikey;

    /**
     * 联合主键
     */
    private List<ColumnInfo> prikey;

    public TableInfo(String tname, Map<String, ColumnInfo> columns, ColumnInfo onlyPrikey, List<ColumnInfo> prikey) {
        this.tname = tname;
        this.columns = columns;
        this.onlyPrikey = onlyPrikey;
        this.prikey = prikey;
    }

    public TableInfo(String tname, Map<String, ColumnInfo> columns, List<ColumnInfo> prikey) {
        this.tname = tname;
        this.columns = columns;
        this.prikey = prikey;
    }

    public TableInfo() {
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public Map<String, ColumnInfo> getColumns() {
        return columns;
    }

    public void setColumns(Map<String, ColumnInfo> columns) {
        this.columns = columns;
    }

    public ColumnInfo getOnlyPrikey() {
        return onlyPrikey;
    }

    public void setOnlyPrikey(ColumnInfo onlyPrikey) {
        this.onlyPrikey = onlyPrikey;
    }

    public List<ColumnInfo> getPrikey() {
        return prikey;
    }

    public void setPrikey(List<ColumnInfo> prikey) {
        this.prikey = prikey;
    }
}
