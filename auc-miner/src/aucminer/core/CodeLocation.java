package aucminer.core;

public class CodeLocation {
	private String fileFullPath; 	//�����ļ���·��
	private int startLine; 		//���ļ��е���ʼ����
	private int endLine;  			//���ļ��еĽ�������
	
	public String getFileFullPath() {
		return fileFullPath;
	}
	
	public int getStartLine() {
		return startLine;
	}
	
	public int getEndLine() {
		return endLine;
	}
}
