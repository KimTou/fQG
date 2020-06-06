package cjt.controller.filter;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

/**
 * @author cjt
 */
    public class XssRequestWrapper extends HttpServletRequestWrapper {
    HttpServletRequest orgRequest = null;

    public XssRequestWrapper(HttpServletRequest request) {
        super(request);
        orgRequest = request;
    }

    /**
     * 覆盖getParameter方法，将参数名和参数值都做xss过滤。<br/>
     * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取<br/>
     * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
     */
    @Override
    public String getParameter(String name) {
        String value = super.getParameter(xssEncode(name));
        if (value != null) {
            value = xssEncode(value);
        }
        return value;
    }

    /**
     * 覆盖getHeader方法，将参数名和参数值都做xss过滤。<br/>
     * 如果需要获得原始的值，则通过super.getHeaders(name)来获取<br/>
     * getHeaderNames 也可能需要覆盖
     */
    @Override
    public String getHeader(String name) {
        String value = super.getHeader(xssEncode(name));
        if (value != null) {
            value = xssEncode(value);
        }
        return value;
    }

    /**
     * 将容易引起xss漏洞的半角字符直接替换成全角字符
     * @param s
     * @return
     */
    private static String xssEncode(String s) {
        if (s == null || "".equals(s)) {
            return s;
        }
        StringBuilder sb = new StringBuilder(s.length() + 16);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '>':
                    //全角大于号
                    sb.append('＞');
                    break;
                case '<':
                    //全角小于号
                    sb.append('＜');
                    break;
                case '\'':
                    //全角单引号
                    sb.append('‘');
                    break;
                case '\"':
                    //全角双引号
                    sb.append('“');
                    break;
                case '&':
                    //全角
                    sb.append('＆');
                    break;
                case '\\':
                    //全角斜线
                    sb.append('＼');
                    break;
                case '#':
                    //全角井号
                    sb.append('＃');
                    break;
                case '(':
                    //全角井号
                    sb.append('（');
                    break;
                case ')':
                    //全角井号
                    sb.append('）');
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }
        String value = stripXss(sb.toString());
        return value;
    }

    /**
     *过滤script脚本
     * @param value
     * @return
     */
    private static String stripXss(String value){
        if(StringUtils.isNotEmpty(value)){
            Pattern scriptPattern = compile("<script>(.*?)</script>",
                    CASE_INSENSITIVE);
            value=scriptPattern.matcher(value).replaceAll("");

            scriptPattern = compile("</script>",
                    CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            scriptPattern = compile("<script(.*?)>",
                    CASE_INSENSITIVE | MULTILINE
                            | DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            scriptPattern = compile("javascript:",
                    CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            scriptPattern = compile("<iframe>(.*?)</iframe>",
                    CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            scriptPattern = compile("</iframe>",
                    CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            scriptPattern = compile("<iframe(.*?)>",
                    CASE_INSENSITIVE | MULTILINE
                            | DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            scriptPattern = compile("onload(.*?)=",
                    CASE_INSENSITIVE | MULTILINE
                            | DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            scriptPattern = compile("oninput(.*?)=",
                    CASE_INSENSITIVE | MULTILINE
                            | DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            scriptPattern = compile("onerror(.*?)=",
                    CASE_INSENSITIVE | MULTILINE
                            | DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            scriptPattern = compile("onclick(.*?)=",
                    CASE_INSENSITIVE | MULTILINE
                            | DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            scriptPattern = compile("confirm(.*?)",
                    CASE_INSENSITIVE | MULTILINE
                            | DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            scriptPattern = compile("onfocus(.*?)=",
                    CASE_INSENSITIVE | MULTILINE
                            | DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            scriptPattern = compile("alert(.*?)",
                    CASE_INSENSITIVE | MULTILINE
                            | DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

        }
        return value;
    }

    /**
     * 获取最原始的request
     *
     * @return
     */
    public HttpServletRequest getOrgRequest() {
        return orgRequest;
    }

    /**
     * 获取最原始的request的静态方法
     * @param req
     * @return
     */
    public static HttpServletRequest getOrgRequest(HttpServletRequest req) {
        if (req instanceof XssRequestWrapper) {
            return ((XssRequestWrapper) req).getOrgRequest();
        }
        return req;
    }

}
