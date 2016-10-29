package myjava.check;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.Random;


public class DataUtil  implements Serializable{
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static String addZero(String str, int len)
	  {
	    if (str.length() < len) {
	      int add_len = len - str.length();
	      for (int i = 0; i < add_len; i++) {
	        str = "0" + str;
	      }
	    }

	    return str;
	  }

	  public static String addZero(int number, int len)
	  {
	    return addZero(String.valueOf(number), len);
	  }

	  public static String getHtmlStr(int d)
	  {
	    return String.valueOf(d);
	  }

	  public static String getHtmlStr(int d, int def)
	  {
	    if (d == def) {
	      return "&nbsp;";
	    }
	    return String.valueOf(d);
	  }

	  public static String getHtmlStr(double d)
	  {
	    return String.valueOf(d);
	  }

	  public static String getHtmlStr(double d, double def)
	  {
	    if (d == def) {
	      return "&nbsp;";
	    }
	    return String.valueOf(d);
	  }

	  public static String objToStr(Object ob)
	  {
	    if (ob == null) {
	      return "";
	    }
	    return ob.toString();
	  }

	  public static boolean isNull(String str)
	  {
	    return (str == null) || (str.trim().length() == 0);
	  }

	  public static boolean isNotNull(String str)
	  {
	    return !isNull(str);
	  }

	  public static boolean isNotNull(Object ob) {
	    if (ob != null) {
	      return !isNull(ob.toString());
	    }
	    return false;
	  }

	  public static long strToLong(String str)
	  {
	    int r = 0;

	    if (isNull(str)) {
	      return r;
	    }

	    return Long.parseLong(str.trim());
	  }

	  public static double strToDouble(String str)
	  {
	    double r = 0.0D;

	    if (isNull(str)) {
	      return r;
	    }

	    return Double.parseDouble(str.trim());
	  }

	  public static float strToFloat(String str) {
	    float r = 0.0F;

	    if (isNull(str)) {
	      return r;
	    }

	    return Float.parseFloat(str.trim());
	  }

	  public static int strToInt(String str)
	  {
	    if (isNull(str)) {
	      return 0;
	    }
	    try
	    {
	      Integer r = new Integer(str);
	      return r.intValue(); } catch (NumberFormatException e) {
	    }
	    return 0;
	  }

	  public static String[][] split(String strs, String flag1, String flag2)
	  {
	    String[] s1 = split(strs, flag1);

	    String[][] res = (String[][])null;

	    if (s1 != null) {
	      res = new String[s1.length][];

	      for (int i = 0; i < s1.length; i++) {
	        String[] s2 = split(s1[i], flag2);
	        res[i] = s2;
	      }
	    }

	    return res;
	  }
	  @SuppressWarnings({ "rawtypes", "unchecked" })
	  public static String[] split(String strs, String flag) {
	    if (strs == null)
	      return null;
	    if (strs.indexOf(flag) == -1) {
	      return new String[] { strs };
	    }

	    
		ArrayList res = new ArrayList();
	    int s_index = 0;
	    int e_index = 0;
	    while (true)
	    {
	      e_index = strs.indexOf(flag, s_index);
	      if (e_index == -1)
	        break;
	      if (e_index == 0)
	        res.add("");
	      else
	        res.add(strs.substring(s_index, e_index).trim());
	      s_index = e_index + flag.length();
	    }
	    int last_index = strs.lastIndexOf(flag);

	    if (last_index >= 0)
	      res.add(strs.substring(last_index + flag.length()).trim());
	    else if (last_index == strs.length() - 1) {
	      res.add("");
	    }

	    String[] data = toArray(res);

	    return data;
	  }

	  @SuppressWarnings({ "rawtypes", "unchecked" })
	private static String[] toArray(ArrayList v)
	  {
	    if (v == null) return null;

	    String[] ary = new String[v.size()];
	    v.toArray(ary);

	    return ary;
	  }



	  public static String getPreString(String str, String flag) {
	    String res = "";

	    if (str != null) {
	      int index = str.indexOf(flag);
	      if (index > 0)
	        res = str.substring(0, index);
	      else {
	        res = str;
	      }
	    }
	    return res;
	  }

	  public static String toFirstUpper(String str)
	  {
	    if (isNull(str)) {
	      return "";
	    }
	    return str.substring(0, 1).toUpperCase() + str.substring(1);
	  }

	  public static String getIncludeString(String str)
	  {
	    if (isNull(str)) {
	      return "";
	    }

	    int pre_index = str.lastIndexOf("(");
	    if (pre_index > -1) {
	      int index = str.indexOf(")");
	      str = str.substring(pre_index + 1, index);

	      int fun_index = str.indexOf(",");
	      if (fun_index > -1) {
	        str = str.substring(0, fun_index);
	      }

	    }

	    return str;
	  }

	  public static String getStatememt(String str, int start)
	  {
	    int s_index = str.indexOf("(", start);
	    if (s_index == -1) return "";

	    int e_index = s_index + 1;
	    int num = 1;

	    while (num > 0) {
	      char c = str.charAt(e_index++);
	      if (c == '(') num++;
	      if (c == ')') num--;
	    }

	    return str.substring(start, e_index);
	  }

	  public static boolean isNumber(String str)
	  {
	    if (str == null) {
	      return false;
	    }

	    str = str.trim();
	    if (str.length() == 0) {
	      return false;
	    }

	    String str_number = "0123456789";

	    String s_str = str.substring(0, 1);
	    if ((s_str.equals("-")) || (s_str.equals("+"))) {
	      str = str.substring(1);
	    }

	    String[] a_str = split(str, ".");
	    if (a_str.length > 2) {
	      return false;
	    }

	    for (int i = 0; i < a_str.length; i++) {
	      for (int j = 0; j < a_str[i].length(); j++) {
	        if (str_number.indexOf(a_str[i].substring(j, j + 1)) == -1) {
	          return false;
	        }
	      }
	    }

	    return true;
	  }

	  public static boolean isEquals(String str1, String str2)
	  {
	    if ((str1 == null) && (str2 == null)) {
	      return true;
	    }
	    if ((str1 == null) && (str2 != null)) {
	      return false;
	    }
	    if ((str1 != null) && (str2 == null)) {
	      return false;
	    }

	    return str1.equals(str2);
	  }

	  public static int compare(String str1, String str2)
	  {
	    return str1.compareTo(str2);
	  }

	  public static String replace(String str, String str1, String str2)
	  {
	    int index = str.indexOf(str1);
	    if (index > -1) {
	      str = str.substring(0, index) + str2 + str.substring(index + str1.length());
	    }
	    return str;
	  }

	  public static String replace(String str, String str1, String str2, int findex)
	  {
	    int index = str.indexOf(str1, findex);
	    if (index > -1) {
	      str = str.substring(0, index) + str2 + str.substring(index + str1.length());
	    }
	    return str;
	  }

	  public static String replace(String str, String str1, int s_index, int e_index)
	  {
	    str = str.substring(0, s_index) + str1 + str.substring(e_index);
	    return str;
	  }

	  public static String replaceAll(String str, String str1, String str2)
	  {
	    int index = 0;
	    while (true) {
	      index = str.indexOf(str1, index);
	      if (index <= -1) break;
	      str = str.substring(0, index) + str2 + str.substring(index + str1.length());
	      index += str2.length();
	    }

	    return str;
	  }

	  public static void replaceAll(StringBuffer str, String str1, String str2) {
	    int index = 0;
	    while (true) {
	      index = str.indexOf(str1, index);
	      if (index <= -1) break;
	      int start = index;
	      int end = index + str1.length();

	      str.replace(start, end, str2);

	      index += str2.length();
	    }
	  }

	  public static String[] append(String[] strs, String str)
	  {
	    if (str == null) return strs;
	    if (strs == null) strs = new String[0];

	    String[] res = new String[strs.length + 1];
	    for (int i = 0; i < strs.length; i++)
	      res[i] = strs[i];
	    res[strs.length] = str;
	    return res;
	  }

	  public static String[][] append(String[][] strs, String[] str)
	  {
	    if (str == null) return strs;
	    if (strs == null) strs = new String[0][];

	    String[][] res = new String[strs.length + 1][];
	    for (int i = 0; i < strs.length; i++)
	      res[i] = strs[i];
	    res[strs.length] = str;
	    return res;
	  }

	  public static String[] append(String[] strs, String[] str)
	  {
	    if ((strs == null) && (str == null)) return null;
	    if ((strs == null) && (str != null)) return str;
	    if ((strs != null) && (str == null)) return strs;

	    String[] res = new String[strs.length + str.length];
	    for (int i = 0; i < strs.length; i++)
	      res[i] = strs[i];
	    for (int i = 0; i < str.length; i++)
	      res[(strs.length + i)] = str[i];
	    return res;
	  }

	  public static String[][] append(String[][] strs, String[][] str)
	  {
	    if ((strs == null) && (str == null)) return null;
	    if ((strs == null) && (str != null)) return str;
	    if ((strs != null) && (str == null)) return strs;

	    String[][] res = new String[strs.length + str.length][];
	    for (int i = 0; i < strs.length; i++)
	      res[i] = strs[i];
	    for (int i = 0; i < str.length; i++)
	      res[(strs.length + i)] = str[i];
	    return res;
	  }

	  public static String getNextChar(String str)
	  {
	    int c = str.charAt(0) + '\001';
	    str = String.valueOf((char)c);
	    return str;
	  }

	  public static int getRandom(int start, int end)
	  {
	    Random r = new Random();
	    int n = 0;

	    while (n < start) {
	      n = r.nextInt(end);
	    }

	    return n;
	  }

	  public static String getNextCode(String code, int i)
	  {
	    String prefix = code.substring(0, i);
	    int num = strToInt(code.substring(i)) + 1;
	    code = prefix + addZero(num, code.length() - i);

	    return code;
	  }

	
	  public static String getHexNextString(String s) {
	    if (isNumber(s)) {
	      if (strToInt(s) < 9)
	        s = String.valueOf(strToInt(s) + 1);
	      else {
	        s = "a";
	      }
	    }
	    else if (s.equals("f"))
	      s = "0";
	    else {
	      s = Integer.toHexString(Integer.parseInt(s, 16) + 1);
	    }

	    return s;
	  }

	  public static String getCharNextString(String s) {
	    if (isNumber(s)) {
	      if (strToInt(s) < 9)
	        s = String.valueOf(strToInt(s) + 1);
	      else {
	        s = "a";
	      }
	    }
	    else if (s.equals("z")) {
	      s = "0";
	    } else {
	      char c = s.charAt(0);
	      s = String.valueOf(c = (char)(c + '\001'));
	    }

	    return s;
	  }

	  public static String getTabSpace(int count)
	  {
	    String str = "\t";

	    for (int i = 1; i < count; i++) {
	      str = str + str;
	    }
	    return str;
	  }

	  public static String getEnter(int count)
	  {
	    String str = "\n";

	    for (int i = 1; i < count; i++) {
	      str = str + str;
	    }
	    return str;
	  }

	  public static String createOID()
	  {
	    UID id = new UID();
	    return id.toString();
	  }

	  public static String createOID(String flag)
	  {
	    UID id = new UID();
	    String res = id.toString();
	    if (flag.equals(":")) {
	      return res;
	    }
	    return replaceAll(res, ":", flag);
	  }

	  public static String createOID(short num)
	  {
	    UID id = new UID(num);
	    return id.toString();
	  }

	  public static int getLength(String str) {
	    int len = 0;
	    for (int i = 0; i < str.length(); i++)
	    {
	      char c = str.charAt(i);
	      if ((c & 0xFF00) > 0) len += 2; else
	        len++;
	    }
	    return len;
	  }

	  public static String substring(String str, int start, int end) {
	    String res = "";
	    int len = 0;
	    for (int i = 0; i < str.length(); i++)
	    {
	      char c = str.charAt(i);
	      if ((c & 0xFF00) > 0) {
	        len += 2;
	        if (len <= start + 1) continue; 
	      }
	      else { len++;
	        if (len <= start)
	          continue;
	      }
	      if (len <= end)
	        res = res + c;
	    }
	    return res;
	  }

	  public static String toGBString(String s) {
	    if (s != null) {
	      try {
	        s = new String(s.getBytes("iso-8859-1"), "GB2312");
	      }
	      catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	      }
	    }
	    return s;
	  }

	  public static String toHtmlString(String s, String encode) {
	    if (s != null) {
	      try {
	        s = new String(s.getBytes("iso-8859-1"), encode);
	      }
	      catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	      }
	    }
	    return s;
	  }

	  public static String restoreHTML(String s)
	  {
	    StringBuffer buffer = new StringBuffer();

	    int len = s.length();
	    for (int i = 0; i < len; i++) {
	      char ch = s.charAt(i);
	      if (s.charAt(i) == '&') {
	        String code = s.substring(i, i + 6);
	        if (code.equals("&#060;")) {
	          buffer.append('<');
	          i += 5;
	        } else if (code.equals("&#062;")) {
	          buffer.append('>');
	          i += 5;
	        } else if (code.equals("&nbsp;")) {
	          buffer.append(' ');
	          i += 5;
	        } else if (code.equals("&#038;")) {
	          buffer.append('&');
	          i += 5;
	        } else if (code.equals("&#034;")) {
	          buffer.append('"');
	          i += 5;
	        } else if (code.equals("&#039;")) {
	          buffer.append('\'');
	          i += 5;
	        } else if (code.equals("&#x00D;")) {
	          buffer.append('\r');
	          i += 6;
	        } else if (code.equals("&#x00A;")) {
	          buffer.append('\n');
	          i += 6;
	        }
	        else
	        {
	          buffer.append(ch);
	        }
	      } else {
	        buffer.append(ch);
	      }
	    }
	    return buffer.toString();
	  }

	  public static String toString(String[] s) {
	    String res = null;

	    if (s != null) {
	      res = "";
	      for (int i = 0; i < s.length; i++) {
	        res = res + (i == 0 ? "" : ",") + s[i];
	      }
	    }
	    return res;
	  }

	  public static String toString(Object[] s) {
	    String res = null;

	    if (s != null) {
	      res = "";
	      for (int i = 0; i < s.length; i++) {
	        String str = (String)s[i];
	        res = res + (i == 0 ? "" : ",") + str;
	      }
	    }

	    return res;
	  }

	  public static String createProperty(String code)
	  {
	    if (isNull(code)) return null;

	    String[] strs = split(code, "_");
	    String res = "";

	    for (int i = 0; i < strs.length; i++) {
	      res = res + (i == 0 ? strs[i] : toFirstUpper(strs[i]));
	    }
	    return res;
	  }

	  public static String trim(String str) {
	    if (isNotNull(str)) {
	      return str.trim();
	    }
	    return str;
	  }
	}
