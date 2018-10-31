package com.bjsx.orm.po;

import java.sql.*;
import java.util.*;

public class User{

	private Integer id;
	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		this.id=id;
	}
	private String faice;
	public String getFaice(){
		return faice;
	}
	public void setFaice(String faice){
		this.faice=faice;
	}
	private Integer age;
	public Integer getAge(){
		return age;
	}
	public void setAge(Integer age){
		this.age=age;
	}
	private String name;
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name=name;
	}
	private Integer dept;
	public Integer getDept(){
		return dept;
	}
	public void setDept(Integer dept){
		this.dept=dept;
	}
}
