package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.json.JSONException;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import model.Model;
import model.Twitter;
import model.UploadPhoto;

@MultipartConfig
public class PostNewAction extends Action {

    private static final String UPLOAD_DIR = "uploads";
	
	public PostNewAction(Model model) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		return "postNew.do";
	}

	@Override
	public String perform(HttpServletRequest request) {

		String content = request.getParameter("content");
		System.out.println("content" + content);
		String[] reposts = request.getParameterValues("isRepost");
		for (String repost : reposts) {
			System.out.println("repost: " + repost);
		}

		File file = null;
		try {
			Part filePart = request.getPart("upload");
			String fileName = getFileName(filePart);
			System.out.println("file name " + fileName);
			InputStream fileContent = filePart.getInputStream();
			file = new File(fileName);
			System.out.println(file.getAbsolutePath());
			if (!file.exists()) {
				file.createNewFile();
			}
			OutputStream output = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int read = 0;
			while ((read = fileContent.read(buffer)) != -1) {
				output.write(buffer, 0, read);
			}
			fileContent.close();
			output.close();
			
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HttpSession session = request.getSession();
		Token requestToken = (Token) session.getAttribute("requestToken");
		OAuthService service = (OAuthService) session.getAttribute("service");
		
		Verifier verifier = new Verifier((String) session.getAttribute("oauth_verifier"));
		
		Token accessToken = service.getAccessToken(requestToken, verifier);
		
		String access_token = accessToken.getToken();
		String access_token_secret = accessToken.getSecret();
		
		
		System.out.println("access_token : "+access_token);
		System.out.println("access_token_secret : "+access_token_secret);
		
		if(reposts.length==2){
			try {
				
				UploadPhoto.uploadFlicker("-u yuexuanl -s "+content+ " "+file.getAbsolutePath()+" -apiKey 992c097a02257b03f4e1b7fac88fab5e -secret 85aeacf925c41b39");
				Twitter.updateStatusWithMedia(access_token, access_token_secret, content, new File(file.getAbsolutePath()));
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else if(reposts[0].equals("flickr")){
			try {
				UploadPhoto.uploadFlicker("-u yuexuanl -s "+content+ " "+file.getAbsolutePath()+" -apiKey 992c097a02257b03f4e1b7fac88fab5e -secret 85aeacf925c41b39");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(reposts[0].equals("twitter")){
			try {
				Twitter.updateStatusWithMedia(access_token, access_token_secret, content, new File(file.getAbsolutePath()));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
  

		
		

//		System.out.println("is upload? "
//				+ ServletFileUpload.isMultipartContent(request));
//
//		ServletFileUpload upload = new ServletFileUpload();
//		FileItemIterator it;
//		try {
//			it = upload.getItemIterator(request);
//			while (it.hasNext()) {
//				FileItemStream item = it.next();
//				String name = item.getFieldName();
//				System.out.println("name: " + name);
//			}
//		} catch (FileUploadException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		return "allPostsPage.do";
	}

	/**
	 * Utility method to get file name from HTTP header content-disposition
	 */
	private String getFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		System.out.println("content-disposition header= " + contentDisp);
		String[] tokens = contentDisp.split(";");
		for (String token : tokens) {
			if (token.trim().startsWith("filename")) {
				return token.substring(token.indexOf("=") + 2,
						token.length() - 1);
			}
		}
		return "";
	}
}
