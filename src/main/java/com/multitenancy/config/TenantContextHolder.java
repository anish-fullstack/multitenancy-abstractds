package com.multitenancy.config;

public class TenantContextHolder {

	private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

	public static void setTenantName(String tenantName) {
		threadLocal.set(tenantName);
	}

	public static String getTenantName() {
		return threadLocal.get();
	}

	public static void clear() {
		threadLocal.remove();
	}

}
