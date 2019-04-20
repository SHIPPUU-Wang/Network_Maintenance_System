package com.ie.dao;

import com.ie.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDao")
public interface UserMapper {

	@Select("select * from user where user_name = #{userName}")
	List<User> findUserByUserName(String userName);

	@Insert("insert into user (user_name, password) values (#{userName},#{password})")
	void addUser(@Param("userName") String userName, @Param("password") String password);
}
