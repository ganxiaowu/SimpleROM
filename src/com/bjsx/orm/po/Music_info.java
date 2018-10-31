package com.bjsx.orm.po;

import java.sql.*;
import java.util.*;

public class Music_info{

	private Integer id;
	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		this.id=id;
	}
	private String music_name;
	public String getMusic_name(){
		return music_name;
	}
	public void setMusic_name(String music_name){
		this.music_name=music_name;
	}
	private String singer_name;
	public String getSinger_name(){
		return singer_name;
	}
	public void setSinger_name(String singer_name){
		this.singer_name=singer_name;
	}
	private String music_size;
	public String getMusic_size(){
		return music_size;
	}
	public void setMusic_size(String music_size){
		this.music_size=music_size;
	}
}
