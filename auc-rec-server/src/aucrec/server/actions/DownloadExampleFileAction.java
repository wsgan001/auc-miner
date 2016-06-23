package aucrec.server.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.opensymphony.xwork2.ActionSupport;

public class DownloadExampleFileAction extends ActionSupport{
	private static final long serialVersionUID = 1L;

	private static final String fileStoreDir = "C:/Users/fei/Desktop/";
	  
    private String fileName;  //���·��
      
    public String getFileName() {  
        return fileName;  
    }  
  
    public void setFileName(String fileName) {  
        this.fileName = fileName;  
    }  
  
    //����һ������������Ϊһ���ͻ�����˵��һ���������������ڷ���������һ�������  
    public InputStream getDownloadFile() throws Exception {  
        String fileAbsolutePath = fileStoreDir + fileName;
        File file = new File(fileAbsolutePath);
    	return new FileInputStream(file);  
    }  
      
    @Override  
    public String execute() throws Exception {  
        return SUCCESS;  
    } 
}
