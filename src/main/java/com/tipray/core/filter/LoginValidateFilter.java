package com.tipray.core.filter;

import com.tipray.bean.Session;
import com.tipray.core.BasicAuthAccountConfig;
import com.tipray.core.GridProperties;
import com.tipray.util.HttpRequestUtil;
import com.tipray.util.SessionUtil;
import com.tipray.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class LoginValidateFilter implements Filter {

	private String errorMessage;
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		try {
			HttpServletRequest request = (HttpServletRequest) servletRequest;
			HttpServletResponse response = (HttpServletResponse) servletResponse;

			String url = request.getRequestURI();

			// 基本认证
			if (isBasicAuthentication(url, request)) {
                boolean isAuth = checkBasicAuth(request);
                if (isAuth) {
                    chain.doFilter(servletRequest, servletResponse);
                } else {
                    response.setStatus(401);
                    response.setHeader("WWW-authenticate", "Basic Realm=\"pltone.com\"");
                }
                return;
            }

            // 登录验证白名单
			if (isResourceRequest(url) || isNotLoginValidate(url, request)) {
				chain.doFilter(servletRequest, servletResponse);
				return;
			}

			// 判断用户是否在登录状态
			if (isLogin(request, response)) {
				// 更新session时间：心跳检测的请求不更新
				if (url.indexOf("/manage/session/isAlive.do") < 0) {
					SessionUtil.updateSession(request);
				}
				chain.doFilter(servletRequest, servletResponse);
			} else {
				// 判断是否为ajax请求
                String requestType = request.getHeader("X-Requested-With");
                boolean isAjaxRequest = requestType != null && ("XMLHttpRequest").equals(requestType);
                if (isAjaxRequest) {
                    response.setStatus(601);
                    return;
                }
                SessionUtil.removeSession(request);
				response.setContentType("text/html");
				String path = request.getContextPath();
				String requestUrl = HttpRequestUtil.getRequestUrl(request);
				requestUrl = URLEncoder.encode(requestUrl, "UTF-8");

				StringBuffer responsePage = new StringBuffer("<script>document.location.href='").append(path)
						.append("/login.jsp?requestUrl=").append(requestUrl)
						.append(StringUtil.isEmpty(errorMessage) ? "'" : "&errorMessage=' + encodeURIComponent ('" + errorMessage + "')")
						.append("</script>");
				response.getWriter().print(responsePage.toString());
			}
		} catch (Exception e) {
			log.error("登录过滤器异常:{}", e);
			throw new ServletException(e);
		}
	}

	private boolean isLogin(HttpServletRequest request, HttpServletResponse response) {
		Session session = SessionUtil.getSession(request);
		// 未登录
		if (session == null) {
			errorMessage = "";
			Session oldSession = SessionUtil.getOldSession(request);
			if (oldSession != null) {
				errorMessage = "<div style=\"font-size:15px;line-height:20px;\">您的账号在异地登录(" + oldSession.getIp()
						+ ")<br>如非授权，建议修改密码</div>";
			}
			return false;
		}
		// 登录超时
		if (SessionUtil.isLoginTimeout(session)) {
			SessionUtil.removeSession(session);
			errorMessage = "因长时间未操作，系统已自动退出，请重新登录";
			return false;
		}
		return true;
	}

	/**
	 * 不用过滤的额外配置 没有登录时，有些请求是必须的，因此不用过滤
	 * 
	 * @param url
	 * @param request
	 * @return
	 */
	private boolean isNotLoginValidate(String url, HttpServletRequest request) {
		for (String path : GridProperties.NOT_VALIDATE_PATH) {
			if (url.startsWith(request.getContextPath() + path)) {
				return true;
			}
		}
		return false;
	}

    /**
     * 是否需要HTTP基本认证
     * @param url
     * @param request
     * @return
     */
	private boolean isBasicAuthentication(String url, HttpServletRequest request) {
        for (String path : GridProperties.BASIC_AUTH_PATH) {
            if (url.startsWith(request.getContextPath() + path)) {
                return true;
            }
        }
        return false;
    }

    /**
     * HTTP基本认证
     * @param request
     * @return
     */
    private boolean checkBasicAuth(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        if (auth == null || auth.length() < 7) {
            return false;
        }
        String account = new String(Base64.getDecoder().decode(auth.substring(6)), StandardCharsets.UTF_8);
        return BasicAuthAccountConfig.checkAccount(account);
    }

	/**
	 * 资源请求
	 * 
	 * @param url
	 * @return
	 */
	private boolean isResourceRequest(String url) {
		if (url.endsWith(".jpg") || url.endsWith(".gif") || url.endsWith(".css") || url.endsWith(".js")
				|| url.endsWith(".png") || url.endsWith(".bmp") || url.endsWith(".ico") || url.endsWith(".txt")
				|| url.endsWith(".apk") || url.endsWith("bootstrap.min.css.map")) {
			return true;
		}
		return false;
	}

	@Override
	public void destroy() {
		this.errorMessage = "";
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.errorMessage = "";
	}

}
