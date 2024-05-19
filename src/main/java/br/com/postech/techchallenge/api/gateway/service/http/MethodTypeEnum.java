package br.com.postech.techchallenge.api.gateway.service.http;

public enum MethodTypeEnum {

	UNKNOWN("desconhecido"),
	GET("get"),
	POST("post"),
	PUT("put"),
	DELETE("delete");
	
	private String methodType;

	private MethodTypeEnum(String methodType) {
		this.methodType = methodType;
	}

	public String getMethodType() {
		return methodType;
	}
	
	public static MethodTypeEnum get(String name) {
		for (MethodTypeEnum method : MethodTypeEnum.values()) {
			if(method.getMethodType().equals(name)) {
				return method;
			}
		}
		
		return MethodTypeEnum.UNKNOWN;
	}
}
