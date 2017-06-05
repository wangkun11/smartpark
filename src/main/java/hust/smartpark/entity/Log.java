package hust.smartpark.entity;

import hust.smartpark.util.MyUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 日志类-记录用户操作行为
 * 
 * @author lin.r.x
 *
 */
public class Log implements Serializable {
	private static final long serialVersionUID = 1L;

	private String logId; // 日志主键
	private String type; // 日志类型
	private String title; // 日志标题
	private String remoteAddr; // 请求地址
	private String requestUri; // URI
	private String method; // 请求方式
	private String params; // 提交参数
	private String exception; // 异常
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private String operateDate; // 开始时间
	private String timeout; // 结束时间
	private String userId; // 用户ID

	public String getLogId() {
		return MyUtils.isBlank(logId) ? logId : logId.trim();
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getType() {
		return MyUtils.isBlank(type) ? type : type.trim();
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return MyUtils.isBlank(title) ? title : title.trim();
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRemoteAddr() {
		return MyUtils.isBlank(remoteAddr) ? remoteAddr : remoteAddr.trim();
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public String getRequestUri() {
		return MyUtils.isBlank(requestUri) ? requestUri : requestUri.trim();
	}

	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}

	public String getMethod() {
		return MyUtils.isBlank(method) ? method : method.trim();
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParams() {
		return MyUtils.isBlank(params) ? params : params.trim();
	}

	public void setParams(String params) {
		this.params = params;
	}

	/**
	 * 设置请求参数
	 * 
	 * @param paramMap
	 */
	public void setMapToParams(Map<String, String[]> paramMap) {
		if (paramMap == null) {
			return;
		}
		StringBuilder params = new StringBuilder();
		for (Map.Entry<String, String[]> param : paramMap.entrySet()) {
			params.append(("".equals(params.toString()) ? "" : "&")+ param.getKey() + "=");
			String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
			params.append(paramValue);
		}
		this.params = params.toString();
	}

	public String getException() {
		return MyUtils.isBlank(exception) ? exception : exception.trim();
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
	}

	public String getTimeout() {
		return MyUtils.isBlank(timeout) ? timeout : timeout.trim();
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public String getUserId() {
		return MyUtils.isBlank(userId) ? userId : userId.trim();
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Log [logId=" + logId + ", type=" + type + ", title=" + title
				+ ", remoteAddr=" + remoteAddr + ", requestUri=" + requestUri
				+ ", method=" + method + ", params=" + params + ", exception="
				+ exception + ", operateDate=" + operateDate + ", timeout="
				+ timeout + ", userId=" + userId + "]";
	}
	
}