package com.ie.pojo;

/**
 * 连接主机 实体类
 */
public class Host {

	// 主机ID
	private Integer hostId;

	// 主机地址
	private String hostAddress;

	// 端口号
	private Integer port;

	// 标识符（表明是路由器【1】或交换机【2】）
	private Integer identifier;

	public Integer getHostId() {
		return hostId;
	}

	public void setHostId(Integer hostId) {
		this.hostId = hostId;
	}

	public String getHostAddress() {
		return hostAddress;
	}

	public void setHostAddress(String hostAddress) {
		this.hostAddress = hostAddress;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Integer getIdentifier() {
		return identifier;
	}

	public void setIdentifier(Integer identifier) {
		this.identifier = identifier;
	}

	@Override
	public String toString() {
		return "Host{" +
				"hostId=" + hostId +
				", hostAddress='" + hostAddress + '\'' +
				", port=" + port +
				", identifier=" + identifier +
				'}';
	}
}
