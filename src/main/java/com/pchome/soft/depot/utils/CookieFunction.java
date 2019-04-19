//package cookie;
package com.pchome.soft.depot.utils;

/**
 * @author Alex Lin
 * @date 2003/10/24  下午 12:03:31
 * @file  CookieFunction.java
 */
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CookieFunction {
    Socket connection;


    private static final int P_MIN_ID_LEN = 2;

    // private static final int P_MAX_ID_LEN = 16;
    private static final int P_MAX_ID_LEN = 100; // 帳號的長度上限增加到 100

    private static final String Cookie_Boundary = ".";

    private static final String Default_Service_Name = "pchome";

    private static final int Cookie_Ver_Plus = 2;

    private int MAX_KEY_ASS = 0;

    private String Static_Key[] = { "HelloWorld", "PC_World", "homeOnline",
            "OnlineServ", "PChome", "5678", "7515", "TechGroup", "Webtech",
            "worldCookie" };

    private String Base64_Key[] = {
            "ABCDEFGHIJKLabcdefghijkl+/MnopqrstuvwxyzmNOPQRSTUVWXYZ9876543210",
            "ABCDEFGHIJKLMnopqrstuvwxyzmNOPQRST543210UVWXYZ9876abcdefghijkl+/",
            "FGHIJKLMnopqrs+/tuvwxyzmNOPQRSTUVWXYZ98765ABCDE43210abcdefghijkl",
            "9876543210ABCDEFGHIJKLMnopqrstuvwxyzmNOPQRSTUVWXYZabcdefghijkl+/",
            "ABCDEFGHIJKLMnopqr0123456789stuvwxyzmNOPQRSTUVWXYZabcdefghij/+kl",
            "ABCDEF56789GHIJKLMnopqrstuvwxyz43210mNOPQRSTUVWXYZabcdefghijkl+/",
            "abcdefghi/+ABCDEFGHIJKLMnopqrstuvwxyzmNOPQRSTUVWXYZ9876543210jkl",
            "stuvwxyzABCDEFGHIJKLMnopqrmNOPQRSTUVWXYZ9876543210abcdefghijkl/+",
            "ABCDEFGHIJKLMnopqrstuvwxyzmNOPQRST5678901234UVWXYZabcdefghijkl+/",
            "ABCDEFGHIJKLMnopqrstuvwxyzmNOPQRSTUVWXYZ9876543210abcdefghijkl+/" };

    private String cookie_ord[][] = {
            { "order", "xor", "others", "key", "version", "login_time",
                    "login_serv" },
            { "order", "login_serv", "xor", "others", "key", "version",
                    "login_time" },
            { "order", "version", "login_time", "login_serv", "xor", "others",
                    "key" },
            { "order", "login_serv", "xor", "others", "version", "key",
                    "login_time" },
            { "order", "login_serv", "xor", "others", "version", "key",
                    "login_time" } };

    public char chr(int as) {
        byte a2[] = new byte[1];
        a2[0] = (byte) (as);
        String strout = new String(a2);
        return strout.charAt(0);
    }

    public int ord(char ch) {
        Character a1 = new Character(ch);
        return a1.hashCode();
    }

    public int random(int ran) {
        int ranret = (int) ((double) (ran) * Math.random());
        return ranret;
    }

    public void init_max_key_ass() {
        MAX_KEY_ASS = (Static_Key.length <= Base64_Key.length) ? Static_Key.length
                : Base64_Key.length;
    }

    public int Set_Ver(int ver) {
        return ((ver + Cookie_Ver_Plus) * Cookie_Ver_Plus);
    }

    public int Get_Ver(int ver) {
        return ((ver / Cookie_Ver_Plus) - Cookie_Ver_Plus);
    }

    public String strrev(String arg) {
        int arglen = arg.length();
        char ee[] = new char[arglen];
        for (int i = 0; i < arglen; i++)
            ee[i] = arg.charAt(arglen - 1 - i);
        String dd = new String(ee);
        return dd;
    }

    public String set_others_key(String login_id, int version) {
        String connstr = login_id + Cookie_Boundary + Static_Key[version];
        return connstr;
    }

    /**
     * Method gen_key.
     * 
     * @param src
     * @return String
     */
    public String gen_key(String src) {
        String assic = "abcdefghijklABCDEFGHIJKLmnopqrstuvwxyzMNOPQRSTUVWXYZ";
        int MAX_RAN_ASS = assic.length();
        int MAX_RAN_INT = 10;
        char buf;
        String out_string = "";
        char bufarray[] = new char[src.length()];

        if (src == null)
            return null;

        for (int i = 0; i < src.length(); i++) {
            if ((i % 2) == 0)
                buf = assic.charAt((int) ((double) (MAX_RAN_ASS) * Math
                        .random())); // 偶數字元做0到51random再來取字元
            else
                buf = chr((int) ((double) (MAX_RAN_INT) * Math.random())
                        + ord('0')); // 奇數字元做0到9random再加上48再轉為字元
            if (buf == src.charAt(i)) { // 若選到的字元與id中$i位置之字元相同
                if (((i + 1) < MAX_RAN_ASS) && (assic.charAt(i + 1) != buf))
                    buf = assic.charAt(i + 1);
                else if ((i != 0) && (assic.charAt(i - 1) != buf))
                    buf = assic.charAt(i - 1);
                else
                    buf += 1;
            }
            bufarray[i] = buf;
        }
        out_string = new String(bufarray);
        return out_string;
    }

    public char safe_substr(String buf, int pos, int len) {
        return (pos < len) ? buf.charAt(pos) : '\0';
    }

    /**
     * Method xoroperator.
     * 
     * @param first
     * @param second
     * @return String
     */
    public String xoroperator(String first, String second) {
        char azio1[] = first.toCharArray();
        char azio2[] = second.toCharArray();
        String s1 = "";
        String s2 = "";
        String j1 = "";
        String ss1 = "";
        String ss2 = "";
        for (int q = 0; q < azio1.length; q++) { // String to bits for
                                                    // pchome_id
            Character a1 = new Character(azio1[q]);
            ss1 = Integer.toBinaryString(a1.hashCode());
            for (int p = ss1.length(); p < 8; p++) {
                ss1 = "0" + ss1;
            }
            s1 += ss1;
        }
        for (int q = 0; q < azio2.length; q++) { // String to bits for key
            Character a2 = new Character(azio2[q]);
            ss2 = Integer.toBinaryString(a2.hashCode());
            for (int p = ss2.length(); p < 8; p++) {
                ss2 = "0" + ss2;
            }
            s2 += ss2;
        }
        for (int r = 0; r < s1.length(); r++) { // XOR後之bits
            j1 += String.valueOf(Integer.parseInt(s1.substring(r, r + 1))
                    ^ Integer.parseInt(s2.substring(r, r + 1)));
        }
        char jj[] = j1.toCharArray();
        int tao = 0; // 指數用
        int sha = 1; // 指數
        int bat = 0; // bit
        int bea = 0; // 總合8bits之值
        String totstr = ""; // 結合後之String
        char al[] = new char[jj.length / 8];
        int ex = 0;

        for (int k = 0; k < jj.length; k++) { // bits to String
            tao = k % 8;
            sha = 1;
            for (int l = 0; l < 7 - tao; l++) {
                sha *= 2;
            }
            if (jj[k] == '1') {
                bat = 1;
            } else {
                bat = 0;
            }
            bea += bat * sha;
            if (tao == 7) {
                al[ex] = chr(bea);
                bea = 0;
                ex++;
            }
        }
        totstr = String.copyValueOf(al);
        /*
         * test char azio3[] = totstr.toCharArray(); String s3 = ""; String ss3 =
         * ""; for(int q = 0 ; q < azio3.length ; q++) { //String to bits for
         * pchome_id Character a3 = new Character(azio3[q]); ss3 =
         * X4.toBinaryString(a3.hashCode()); for(int p = ss3.length() ; p < 8 ;
         * p++) { ss3 = "0" + ss3; } s3 += ss3; } System.out.println(s3);
         * for(int v=0; v<totstr.length(); v++)
         * System.out.println(totstr.charAt(v));
         */
        return totstr;
    }

    public boolean preg_match(String src) {
        int ret = -1;

        for (int i = 0; i < src.length(); i++) {
            ret = ord(src.charAt(i));
            if (ret < 43 || (ret > 43 && ret < 47) || (ret > 57 && ret < 61)
                    || (ret > 61 && ret < 65) || (ret > 90 && ret < 97)
                    || ret > 122)
                return true;
        }
        return false;
    }

    public String preg_replace(String oldstr, String newstr, String srcstr) {
        if ((oldstr == null) || (newstr == null) || (srcstr == null))
            return srcstr;
        int comp = -1;
        String rest = "";
        comp = srcstr.indexOf(oldstr);
        if (comp != -1)
            rest = srcstr.substring(0, comp).concat(newstr).concat(
                    srcstr.substring(comp + oldstr.length()));
        else
            rest = srcstr;
        return rest;
    }

    public String DecodeBase64(String src, int version) {

        if (src == null || src.equals(""))
            return null;
        src = src.trim();
        if ((src.length() % 4) != 0)
            return null;
        if (preg_match(src)) // 不能有A-Za-z0-9+=/以外之字元否則傳回true
                                // //與php之preg_match不同 !!注意
            return null;
        char Base64[] = Base64_Key[version].toCharArray();
        Vector<Character> rev_arr = new Vector<Character>();
        for (int x = 0, y = Base64.length; x < y; x++)
            rev_arr.addElement(new Character(Base64[x]));
        String ans = "";
        int i = 0;
        int j = src.length();
        while (i < (j - 3)) {
            if ((rev_arr.indexOf(new Character(safe_substr(src, i, j))) << 2) >= 0
                    && (rev_arr.indexOf(new Character(
                            safe_substr(src, i + 1, j))) >> 4) >= 0)
                ans += chr(rev_arr
                        .indexOf(new Character(safe_substr(src, i, j))) << 2
                        | rev_arr.indexOf(new Character(safe_substr(src, i + 1,
                                j))) >> 4);
            else {
                if ((rev_arr.indexOf(new Character(safe_substr(src, i, j))) << 2) >= 0)
                    ans += chr(rev_arr.indexOf(new Character(safe_substr(src,
                            i, j))) << 2);
                else if ((rev_arr.indexOf(new Character(safe_substr(src, i + 1,
                        j))) >> 4) >= 0)
                    ans += chr(rev_arr.indexOf(new Character(safe_substr(src,
                            i + 1, j))) >> 4);
            }
            // // ans += chr( rev_arr.indexOf(new
            // Character(safe_substr(src,i,j))) << 2 | rev_arr.indexOf(new
            // Character(safe_substr(src,i+1,j))) >> 4);
            if ((rev_arr.indexOf(new Character(safe_substr(src, i + 1, j))) << 4) >= 0
                    && (rev_arr.indexOf(new Character(
                            safe_substr(src, i + 2, j))) >> 2) >= 0)
                ans += chr(rev_arr.indexOf(new Character(safe_substr(src,
                        i + 1, j))) << 4
                        | rev_arr.indexOf(new Character(safe_substr(src, i + 2,
                                j))) >> 2);
            else {
                if ((rev_arr.indexOf(new Character(safe_substr(src, i + 1, j))) << 4) >= 0)
                    ans += chr(rev_arr.indexOf(new Character(safe_substr(src,
                            i + 1, j))) << 4);
                else if ((rev_arr.indexOf(new Character(safe_substr(src, i + 2,
                        j))) >> 2) >= 0)
                    ans += chr(rev_arr.indexOf(new Character(safe_substr(src,
                            i + 2, j))) >> 2);
            }
            // / ans += chr( rev_arr.indexOf(new
            // Character(safe_substr(src,i+1,j))) << 4 | rev_arr.indexOf(new
            // Character(safe_substr(src,i+2,j))) >> 2);
            if ((rev_arr.indexOf(new Character(safe_substr(src, i + 2, j))) << 6) >= 0
                    && (rev_arr.indexOf(new Character(
                            safe_substr(src, i + 3, j)))) >= 0)
                ans += chr(rev_arr.indexOf(new Character(safe_substr(src,
                        i + 2, j))) << 6
                        | rev_arr.indexOf(new Character(safe_substr(src, i + 3,
                                j))));
            else {
                if ((rev_arr.indexOf(new Character(safe_substr(src, i + 2, j))) << 6) >= 0)
                    ans += chr(rev_arr.indexOf(new Character(safe_substr(src,
                            i + 2, j))) << 6);
                else if ((rev_arr.indexOf(new Character(safe_substr(src, i + 3,
                        j)))) >= 0)
                    ans += chr(rev_arr.indexOf(new Character(safe_substr(src,
                            i + 3, j))));
            }
            // // ans += chr( rev_arr.indexOf(new
            // Character(safe_substr(src,i+2,j))) << 6 | rev_arr.indexOf(new
            // Character(safe_substr(src,i+3,j))));
            i += 4;
        }

        ans = preg_replace("\0", "", ans);

        return ans;
    }

    public String EncodeBase64(String src, int version) {

        if (src == null || src.equals(""))
            return null;
        String ans = "";
        //String ma = "";
        char Base64[] = Base64_Key[version].toCharArray();
        int i = 0;
        int j = 0;
        for (i = 0, j = src.length(); i < j; i += 3) {
            ans += Base64[ord(safe_substr(src, i, j)) >> 2];
            ans += Base64[((ord(safe_substr(src, i, j)) << 4) & 060)
                    | ((ord(safe_substr(src, i + 1, j)) >> 4) & 017)];
            ans += Base64[((ord(safe_substr(src, i + 1, j)) << 2) & 074)
                    | ((ord(safe_substr(src, i + 2, j)) >> 6) & 003)];
            ans += Base64[ord(safe_substr(src, i + 2, j)) & 077];
        }
        if (i == (j + 1)) {
            char al[] = ans.toCharArray();
            al[ans.length() - 1] = '=';
            ans = String.valueOf(al);
        } else if (i == (j + 2)) {
            char an[] = ans.toCharArray();
            an[ans.length() - 1] = '=';
            an[ans.length() - 2] = '=';
            ans = String.valueOf(an);
        }
        return ans;
    }

    /**
     * Method BASE64_Generetor.
     * 
     * @param pc_home
     * @param type
     * @return Hashtable
     */
    public Hashtable<String, String> BASE64_Generetor(Hashtable<String, String> pc_home, int type) {

        int version = Integer.parseInt(pc_home.get("version").toString());
        if (type == 0) {
            pc_home.put("key", EncodeBase64((String) pc_home.get("key"),
                    version));
            if (pc_home.get("key") == null)
                return null;
            pc_home.put("xor", EncodeBase64((String) pc_home.get("xor"),
                    version));
            if (pc_home.get("xor") == null)
                return null;
            pc_home.put("login_serv", EncodeBase64((String) pc_home
                    .get("login_serv"), version));
            if (pc_home.get("login_serv") == null)
                return null;
            pc_home.put("login_time", EncodeBase64((String) pc_home
                    .get("login_time"), version));
            if (pc_home.get("login_time") == null)
                return null;
            pc_home.put("others", EncodeBase64((String) pc_home.get("others"),
                    version));
            if (pc_home.get("others") == null)
                return null;
        } else if (type == 1) {
            try {
                pc_home.put("key", DecodeBase64((String) pc_home.get("key"),
                        version));
                if (pc_home.get("key") == null)
                    return null;
                pc_home.put("xor", DecodeBase64((String) pc_home.get("xor"),
                        version));
                if (pc_home.get("xor") == null)
                    return null;
                pc_home.put("login_serv", DecodeBase64((String) pc_home
                        .get("login_serv"), version));
                if (pc_home.get("login_serv") == null)
                    return null;
                pc_home.put("login_time", DecodeBase64((String) pc_home
                        .get("login_time"), version));
                if (pc_home.get("login_time") == null)
                    return null;
                pc_home.put("others", DecodeBase64((String) pc_home
                        .get("others"), version));
                if (pc_home.get("others") == null)
                    return null;
            } catch (Exception e) {
                return null;
            }
        } else
            return null;

        return pc_home;
    }

    public String[] preg_split(String pattern, String src) {
        if (src == null)
            return null;
        if (pattern == null)
            return null;
        Vector<String> v = new Vector<String>();
        int point = 0;
        int len = pattern.length();
        while (src.lastIndexOf(pattern) == src.length() - len
                && src.lastIndexOf(pattern) != -1) {
            src = src.substring(0, src.length() - len);
        }
        while (src.indexOf(pattern) != -1) {
            if (src.indexOf(pattern) != 0) {
                v.addElement(src.substring(0, src.indexOf(pattern)));
            }
            src = src.substring(src.indexOf(pattern) + len);
        }
        if (src != null && !src.equals(""))
            v.addElement(src);
        Enumeration<String> enumV = v.elements();
        String[] cookiestr = new String[v.size()];
        while (enumV.hasMoreElements()) {
            cookiestr[point] = enumV.nextElement().toString();
            point++;
        }
        return cookiestr;
    }

    public Hashtable<String, String> Cookie_Order(Hashtable<String, String> pc_home, boolean send) {
        int max_order_cat = 5;
        int order_cat = 0;

        if (send) {
            order_cat = random(max_order_cat); // 0到4
            Integer setver = new Integer(Set_Ver(Integer.parseInt(pc_home.get(
                    "version").toString()))); // 運算
            Integer ordercat = new Integer(order_cat);
            pc_home.put("version", String.valueOf(setver));
            pc_home.put("pchome_cookie", ordercat.toString());
            for (int i = 1; i < 7; i++) { // 取0到4之一的$cookie_ord做連接
                pc_home.put("pchome_cookie", (String) pc_home
                        .get("pchome_cookie")
                        + ".");
                pc_home.put("pchome_cookie", (String) pc_home
                        .get("pchome_cookie")
                        + (String) pc_home.get(cookie_ord[order_cat][i])
                                .toString());
            }
        } else {
            if (pc_home.get("pchome_cookie") != null) { // 分割$pc_home['pchome_cookie']值
                String pcstr = pc_home.get("pchome_cookie").toString();
                String cookies[] = preg_split(".", pcstr);
                if (cookies.length != 7)
                    return null;
                order_cat = Integer.parseInt(cookies[0]);
                for (int i = 1; i < 7; i++)
                    pc_home.put(cookie_ord[order_cat][i], cookies[i]); // 分割後存放
                pc_home.put("version", String.valueOf(new Integer(Get_Ver(Integer
                        .parseInt(pc_home.get("version").toString()))))); // 反運算$pc_home['version']值
            }
        } // end if(send)
        return pc_home;
    }

    public String Encode_PChome_Cookie(String login_id, String login_serv,
            String login_time) {
        if ((login_id == null) || login_id.equals(""))
            return null;
        /*
         * 註解掉此段程式意思是在 encode 時不作過濾@pchome.com.tw的動作 String checkid[] =
         * preg_split("@pchome.com.tw", login_id); if(checkid.length == 1) {
         * login_id = checkid[0]; } else if(checkid.length < 1) { return ""; }
         */
        if ((login_id == null) || (login_id.length() > P_MAX_ID_LEN)
                || (login_id.length() < P_MIN_ID_LEN))
            return null;

        Hashtable<String, String> pc_home = new Hashtable<String, String>();
        pc_home.put("pchome_id", login_id);
        if (login_serv == null || login_serv.equals("")) // service
            pc_home.put("login_serv", Default_Service_Name);
        else
            pc_home.put("login_serv", login_serv);
        if (login_time.equals("") || login_time == null) {
            Date pchome_date = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            pc_home.put("login_time", df.format(pchome_date));
        } else
            pc_home.put("login_time", login_time);
        pc_home.put("key", gen_key((String) pc_home.get("pchome_id"))); // 用id來產生key值
        if (pc_home.get("key") == null || pc_home.get("key").equals(""))
            return null;

        pc_home.put("xor", xoroperator((String) pc_home.get("pchome_id"),
                (String) pc_home.get("key"))); // 用id XOR key值成為xor值
        if (pc_home.get("xor") == null || pc_home.get("xor").equals(""))
            return null;
        pc_home.put("key", strrev((String) pc_home.get("key"))); // key值反轉
        init_max_key_ass();
        Integer maxkeyass = new Integer(random(MAX_KEY_ASS));
        pc_home.put("version", String.valueOf(maxkeyass)); // 0到9random一值
        pc_home.put("others", set_others_key((String) pc_home.get("pchome_id"),
                Integer.parseInt(pc_home.get("version").toString()))); // 傳回id.static_key
        pc_home = BASE64_Generetor(pc_home, 0); // 加密
        if (pc_home == null) // 加密
            return null;
        pc_home = Cookie_Order(pc_home, true); // 產生$pc_home['pchome_cookie']
        if (pc_home == null)
            return null;

        return (String) pc_home.get("pchome_cookie");
    }

    // //////////////////////////////////////////////////////////modify this
    // function on 4 Nov.
    /*
     * public String get_others_key(String others, int version) { String token[] =
     * preg_split(Cookie_Boundary, others); //加入'\'於特殊符號 if
     * (token[1].equals(Static_Key[version])) return token[0]; //return id值 else
     * return null; }
     */
    public String get_others_key(String others, int version) {
        String other_id = null;
        if (others.indexOf(Cookie_Boundary) >= 0) {
            String other_version = others.substring(others
                    .lastIndexOf(Cookie_Boundary) + 1);

            // check version is correct or not
            if (other_version.equals(Static_Key[version])) {
                other_id = others.substring(0, others
                        .lastIndexOf(Cookie_Boundary));
            } // end of if
        } // end of if
        return other_id;
    }

    // //////////////////////////////////////////////////////////modify this
    // function on 4 Nov.

    public String Decode_PChome_Cookie(String pchome_cookie) {
        init_max_key_ass();

        if (pchome_cookie == null || pchome_cookie.equals(""))
            return null;

        Hashtable<String, String> pc_home = new Hashtable<String, String>();
        pc_home.put("pchome_cookie", pchome_cookie);
        pc_home = Cookie_Order(pc_home, false); // 分割$pc_home['pchome_cookie']值
        if (pc_home == null)
            return null;
        if ((Integer.parseInt(pc_home.get("version").toString()) >= MAX_KEY_ASS)
                || (Integer.parseInt(pc_home.get("version").toString()) < 0))
            return null;
        pc_home = BASE64_Generetor(pc_home, 1); // 解密
        if (pc_home == null)
            return null;
        pc_home.put("key", strrev((String) pc_home.get("key"))); // key值反轉
        pc_home.put("pchome_id", xoroperator((String) pc_home.get("xor"),
                (String) pc_home.get("key")));
        if (pc_home.get("pchome_id") == null
                || pc_home.get("pchome_id").equals(""))
            return null;
        String pchome_id = get_others_key((String) pc_home.get("others"),
                Integer.parseInt(pc_home.get("version").toString())); // 得id值
        if (!pc_home.get("pchome_id").equals(pchome_id)) // 比較是否一樣
            return null;
        // test return " pc_home : " + pc_home.get("pchome_id") + " pchomeid : "
        // + pchome_id + " others : " + pc_home.get("others") + " key : " +
        // pc_home.get("key") + " xor : " + pc_home.get("xor") + " version : " +
        // pc_home.get("version");
        return (String) pc_home.get("pchome_id");
    }

    /**
     * Constructor for PortTalk.
     */
    public CookieFunction() {
        super();
    }

    /**
     * Get The Local Address
     */
    public String getLocalAddress() {
        String laddress = "";
        InetAddress localAddress = null;
        try {
            localAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
        }
        byte ipAddress[] = localAddress.getAddress();
        for (int i = 0; i < ipAddress.length; ++i)
            laddress += (ipAddress[i] + 256) % 256 + ".";
        return laddress;
    }

    /**
     * Get The Destination Address
     */
    public String getDestinationAddress() {
        String daddress = "";
        InetAddress destAddress = connection.getInetAddress();
        byte ipAddress[] = destAddress.getAddress();
        for (int i = 0; i < ipAddress.length; ++i)
            daddress += (ipAddress[i] + 256) % 256 + ".";
        return daddress;
    }

    /**
     * Method md5.
     * 
     * @param md5scr
     * @return String
     */
    public String md5(String md5scr) { // MD5

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
        }

        md.reset();
        md.update(md5scr.getBytes());
        byte[] mdDigest = md.digest();
        String s32code = decode16to32(mdDigest);

        return s32code;
    }

    /**
     * Method Simple_Encode.
     * 
     * @param usrid
     * @return String
     */
    public String Simple_Encode(String usrid) { // 簡易加密
        if ((usrid == null) || usrid.equals(""))
            return null;
        /*
         * 註解掉此段程式意思是在 encode 時不作過濾@pchome.com.tw的動作 String checkid[] =
         * preg_split("@pchome.com.tw", usrid); if(checkid.length == 1) { usrid =
         * checkid[0]; } else if(checkid.length > 1) { return null; }
         */
        if ((usrid == null) || (usrid.length() > P_MAX_ID_LEN)
                || (usrid.length() < P_MIN_ID_LEN))
            return null;

        Date pchome_date = new Date(); // 現在時間
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String nowtime = df.format(pchome_date);
        String randkey = "dnapchomev1";
        String randcode = randkey + usrid + nowtime; // key,id,time連結起來
        String mymd5 = md5(randcode); // 連結後做md5加密
        return mymd5 + nowtime + usrid; // 連結加密值,時間,id
    }

    /**
     * Method decode16to32.
     * 
     * @param in
     * @return String
     */
    private static String decode16to32(byte[] in) {
        if (in.length != 16)
            return "";

        String sOut = "";
        String sTmp = "";
        int ia = 0;
        for (int i = 0; i < in.length; i++) {
            ia = ((int) in[i]) & 0x000000ff;
            sTmp = Integer.toHexString(ia);
            if (sTmp.length() == 1)
                sOut += ("0" + sTmp);
            else
                sOut += sTmp;
        }

        if (sOut.length() != 32)
            return "";

        return sOut;

    } // private String decode16to32(byte[] in)

    public String Simple_Decode(String lowcookie) { // 簡易解密
        int cookielen = lowcookie.length();
        if (cookielen < 48) {
            return "false";
        }
        String getmd5 = lowcookie.substring(0, 32); // 字串裡取加密值@
        String gettime = lowcookie.substring(32, 46); // 字串裡取時間
        String getusrid = lowcookie.substring(46, cookielen); // 字串裡取id
        String randkey = "dnapchomev1";
        String randcode = randkey + getusrid + gettime; // key,id,time連結起來
        String mymd5 = md5(randcode); // 連結後做md5加密
        if (mymd5.equals(getmd5)) { // 加密後再與字串裡取加密值@做比較
            return getusrid; // 若一樣則return帳號
        } else {
            return "false";
        }
    }

       public static void main(String[] args) {
    }
}
