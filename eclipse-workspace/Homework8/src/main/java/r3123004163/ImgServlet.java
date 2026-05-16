package r3123004163;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 作业8：图片 Servlet，映射 /img
 * 示例：http://localhost:8080/Homework8/img?picture1.jpg
 */
@WebServlet("/img")
public class ImgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ImgServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String filename = request.getQueryString();
		if (filename == null || filename.isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "请在 URL 中指定图片文件名");
			return;
		}
		if (filename.contains("..") || filename.contains("/") || filename.contains("\\")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "非法文件名");
			return;
		}

		ServletContext context = getServletContext();
		String mimeType = context.getMimeType(filename);
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}

		try (InputStream in = context.getResourceAsStream("/" + filename)) {
			if (in == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "找不到图片: " + filename);
				return;
			}
			response.setContentType(mimeType);
			try (OutputStream out = response.getOutputStream()) {
				byte[] buffer = new byte[8192];
				int len;
				while ((len = in.read(buffer)) != -1) {
					out.write(buffer, 0, len);
				}
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}