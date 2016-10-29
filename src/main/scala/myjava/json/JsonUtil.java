package myjava.json;

import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JsonUtil {

	private static ObjectMapper mapper = new ObjectMapper();

	public static String object2json(Object obj) {
		String jsonStr = bean2json(obj);
		return jsonStr;
	}

	/**
	 * 对象转为json
	 * 
	 * @param objs
	 *            对象序列
	 * @return json字符串
	 */
	public static String object2json(Object... objs) {

		return array2json(objs);
	}

	/**
	 * 对象转json
	 * 
	 * @param bean
	 * @return
	 */
	public static String bean2json(Object bean) {
		String jsonStr = "";
		try {
			jsonStr = mapper.writeValueAsString(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * list转json
	 * @param list
	 * @return [{"name":"Michael","age":null},{"name":"Andy","age":30},{"name":"Justin","age":19}]
	 */
	public static String list2json(List<?> list) {
		String jsonStr = bean2json(list);
		return jsonStr;
	}

	/**
	 * @param list
	 * @return {"name":"Michael","age":null};{"name":"Andy","age":30};{"name":"Justin","age":19};
	 */
	public static String list2json2(List<?> list) {
		StringBuilder json = new StringBuilder();
		// json.append("[");
		if (list != null && list.size() > 0) {
			for (Object obj : list) {
				json.append(object2json(obj));
				json.append(";");
			}
			// json.setCharAt(json.length() - 1, ']');
		} else {
			// json.append("]");
		}
		return json.toString();
	}

	public static String array2json(Object[] array) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (array != null && array.length > 0) {
			for (Object obj : array) {
				json.append(object2json(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}

	public static String map2json(Map<?, ?> map) {
		StringBuilder json = new StringBuilder();
		json.append("{");
		if (map != null && map.size() > 0) {
			for (Object key : map.keySet()) {
				json.append(object2json(key));
				json.append(":");
				json.append(object2json(map.get(key)));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, '}');
		} else {
			json.append("}");
		}
		return json.toString();
	}

	public static String set2json(Set<?> set) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (set != null && set.size() > 0) {
			for (Object obj : set) {
				json.append(object2json(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}

	public static String string2json(String s) {
		if (s == null)
			return "";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			switch (ch) {
			case '"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '/':
				sb.append("\\/");
				break;
			default:
				if (ch >= '\u0000' && ch <= '\u001F') {
					String ss = Integer.toHexString(ch);
					sb.append("\\u");
					for (int k = 0; k < 4 - ss.length(); k++) {
						sb.append('0');
					}
					sb.append(ss.toUpperCase());
				} else {
					sb.append(ch);
				}
			}
		}
		return sb.toString();
	}

	public static void main(String args[]) {
		ArrayList<Object> list = new ArrayList<>();
		Person p1 = new Person("Michael");
		Person p2 = new Person("Andy", 30);
		Person p3 = new Person("Justin", 19);
		list.add(p1);
		list.add(p2);
		list.add(p3);
		System.out.println("==========" + list2json2(list) + "===========");
		System.out.println("==========" + list2json(list) + "===========");

	}

}

class Person {

	public Person(String name, Integer age) {
		super();
		this.name = name;
		this.age = age;
	}

	public Person(String name) {
		super();
		this.name = name;
	}

	private String name;
	private Integer age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
}