package com.bjsx.orm.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Administrator on 2018/10/29.
 */
public interface CallBack {
    public Object doExecute(Connection coon,PreparedStatement ps, ResultSet rs);
}
