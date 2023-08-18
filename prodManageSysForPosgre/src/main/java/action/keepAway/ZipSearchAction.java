package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ZipData;
import dao.ZipDAO;
import tool.Action;

public class ZipSearchAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String zipno= request.getParameter("zipno");
		ZipDAO zd = new ZipDAO();
		List<ZipData> lzd = zd.searchAllByZipNo(zipno);
		request.setAttribute("list", lzd);
		request.setAttribute("zipno", zipno);
		return "zipSearch.jsp";
	}

}
