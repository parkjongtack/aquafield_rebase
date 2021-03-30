<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.soft.web.util.*" %>
<%@page import="com.oreilly.servlet.MultipartRequest, com.oreilly.servlet.multipart.DefaultFileRenamePolicy" %>
<%@ page import="java.util.Map, java.util.HashMap, java.util.List" %><%!

    public static String randomString(int rnd_length) {
        String possible = "01234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_-";
        String str = "";
        Random rnd = new Random();
        int rnd_pos ;
        while(str.length()< rnd_length)  {
            rnd_pos = (int)(rnd.nextDouble()*possible.length());
            str = str + possible.substring(rnd_pos, rnd_pos+1);
        }
        return str;
    }

%>
<%
	
	String enctype = "utf-8";
	String realFolder = "";
	String saveFolder = "/common/upload/webimage/";
	String dateString = "";
	String moveTargetName = "";
	
	String dirString = new java.text.SimpleDateFormat ("yyyyMM", java.util.Locale.KOREA).format(new java.util.Date());
	saveFolder += dirString + "/";
	
	int sizeLimit = 5 * 1024 * 1024;

	if(request.getContentLength() > sizeLimit ) {
	%>
		<script>
		alert("업로드 용량(총 <%=sizeLimit%>Mytes)을 초과하였습니다.");
		history.back();
		</script>
	<%
		return;
	}
	else {
		
	
		ServletContext context = getServletContext();
		realFolder = context.getRealPath(saveFolder);

		File dir = new File(realFolder);
		if (!dir.isDirectory()) {
			dir.mkdirs();
		}
		
		MultipartRequest multi =  new MultipartRequest(request, realFolder, sizeLimit, enctype, new DefaultFileRenamePolicy());
		
		int cnt = 1;
		String upfile = multi.getFilesystemName("Filedata");
		
		if (!upfile.equals("")) {
			
			dateString = new java.text.SimpleDateFormat ("yyyyMMdd", java.util.Locale.KOREA).format(new java.util.Date()) + randomString(5);
			moveTargetName = dateString + upfile.substring(upfile.lastIndexOf(".") );
			String fileExt = upfile.substring(upfile.lastIndexOf(".") + 1);
			File sourceFile = new File(request.getRealPath(saveFolder) + File.separator + upfile);
			File targetFile = new File(request.getRealPath(saveFolder) + File.separator + moveTargetName);
			sourceFile.renameTo(targetFile);
			
			//String realImageFullName = request.getRealPath(saveFolder) + File.separator + moveTargetName;
			/*
			Thumbnail.makeThumbnail(필수생성여부, 원본이미지, 타겟이미지, 가로크기, 세로크기);
			*/
			/*
			if(!Thumbnail.makeThumbnail(false, realImageFullName, realImageFullName, 600, 0) ){
				System.out.println("StmartEditor 이미지 줄임 에러");
			}
			*/
			
			//System.out.println(saveFolder + moveTargetName);
		}
	}
	
%>
<script type="text/javascript">
	opener.parent.pasteHTML('<img src="<%=saveFolder + moveTargetName%>">'); 
	window.close();
</script>