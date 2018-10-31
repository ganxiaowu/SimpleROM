package com.bjsx.orm.po;

import java.sql.*;
import java.util.*;

public class Dept{

	private Integer id;
	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		this.id=id;
	}
	private String dept_name;
	public String getDept_name(){
		return dept_name;
	}
	public void setDept_name(String dept_name){
		this.dept_name=dept_name;
	}
}
