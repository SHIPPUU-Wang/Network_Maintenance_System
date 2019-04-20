package com.ie.dao;

import com.ie.pojo.Host;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 主机信息 Mapper接口
 */
@Repository("hostDao")
public interface HostMapper {

	@Select("select * from host where host_id = #{userId}")
	Host findHost(Integer userId);

	// (host_id, host_address,port,identifier)
	@Insert("insert into host values (#{hostId}, #{hostAddress}, #{port}, #{identifier})")
	void addHost(Host host);

	/**
	 * 删除host表中所有数据
	 */
	@Delete("delete from host where host_id = #{userId}")
	void deleteHost(Integer userId);
}
