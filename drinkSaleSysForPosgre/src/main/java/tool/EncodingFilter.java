package tool;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Servlet Filter implementation class EncodingFilter
 */
//｢@WebFilter("/*")｣と指定すると､css/jQuery/JavaScript等の外部読込みがエラーとなる
@WebFilter(urlPatterns = { "/action/*", "/bean/*", "/dao/*", "/tool/*", "/WEB-INF/*" })
public class EncodingFilter implements Filter {
	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// リクエストの文字エンコーディング指定
		request.setCharacterEncoding("UTF-8");
		// レスポンスのMIME形式/文字エンコーディング指定
		response.setContentType("text/html; charset=UTF-8");
		chain.doFilter(request, response);
	}

	public EncodingFilter() {
		// TODO Auto-generated constructor stub
	}

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
