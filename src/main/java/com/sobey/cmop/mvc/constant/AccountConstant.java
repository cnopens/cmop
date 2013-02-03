package com.sobey.cmop.mvc.constant;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * Account 模块的静态常量
 * 
 * @author liukai
 * 
 */
public class AccountConstant {

	/**
	 * 加密方式 : SHA-1
	 */
	public static final String HASH_ALGORITHM = "SHA-1";

	/**
	 * 迭代次数 : 1024
	 */
	public static final int HASH_INTERATIONS = 1024;

	/**
	 * salt的数组大小 : 8
	 */
	public static final int SALT_SIZE = 8;

	/**
	 * 默认密码:111111
	 */
	public static final String defaultPassword = "111111";

	/**
	 * 用户状态 <br>
	 * 1:enabled 有效 <br>
	 * 0:disabled 无效
	 * 
	 * @author liukai
	 * 
	 */
	public enum UserStatus implements CommonEnum {

		ENABLED(1), DISABLED(0);

		private int code;

		// 构造器只能私有private,绝对不允许有public构造器.

		private UserStatus(int code) {
			this.code = code;
		}

		// 该方法根据传入的参数,返回enum中的文本. 实现该方法后
		// Map集合,将enum中的值迭代至Map集合中,key为enum的输入参数.value为enum的值.最后用Map集合的get方法获得获得value
		// 也可以直接用 .map方法返回一个HashMap的返回值

		public static final Map<Integer, String> map = Maps.newLinkedHashMap();
		static {
			for (UserStatus e : UserStatus.values()) {

				map.put(e.code, e.name());

			}
		}

		public static String get(Integer code) {
			return map.get(code);
		}

		// 覆写toString 方法，在该方法中返回从构造函数中传入的参数

		@Override
		public String toString() {
			return String.valueOf(this.code);
		}

		// 同toString方法,不过返回的是Integer类型.可查看toInteger的说明得到更详细的信息

		@Override
		public Integer toInteger() {
			return this.code;
		}

	}

	/**
	 * 默认的Group类型
	 * <p>
	 * 1.admin 超级管理员<br>
	 * 2.apply 申请人 <br>
	 * 3.audit 审批人 <br>
	 * </P>
	 * 
	 * @author liukai
	 * 
	 */
	public enum DefaultGroups implements CommonEnum {

		admin(1), apply(2), audit(3);

		private int code;

		private DefaultGroups(int code) {
			this.code = code;
		}

		public static final Map<Integer, String> map = Maps.newLinkedHashMap();
		static {
			for (DefaultGroups e : DefaultGroups.values()) {

				map.put(e.code, e.name());

			}
		}

		public static String get(Integer code) {
			return map.get(code);
		}

		@Override
		public String toString() {
			return String.valueOf(this.code);
		}

		@Override
		public Integer toInteger() {
			return this.code;
		}

	}

	/**
	 * 用户类型<br>
	 * 
	 * <pre>
	 * 1-管理员 admin
	 * 2-申请人 apply
	 * 3-审核人 audit
	 * 4-运维人 operation
	 * </pre>
	 * 
	 * @author liukai
	 * 
	 */
	public enum UserTypes implements CommonEnum {

		admin(1), apply(2), audit(3), operation(4);

		private int code;

		private UserTypes(int code) {
			this.code = code;
		}

		public static final Map<Integer, String> map = Maps.newLinkedHashMap();
		static {
			for (UserTypes e : UserTypes.values()) {

				map.put(e.code, e.name());

			}
		}

		public static String get(Integer code) {
			return map.get(code);
		}

		@Override
		public String toString() {
			return String.valueOf(this.code);
		}

		@Override
		public Integer toInteger() {
			return this.code;
		}

	}

	/**
	 * 枚举的示例
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// System.out.println(UserStatus.get(1));
		// System.out.println(UserStatus.get(3));
		// System.out.println(UserStatus.admin);
		// System.out.println(UserStatus.admin.name());
		// System.out.println(UserStatus.map);
	}
}