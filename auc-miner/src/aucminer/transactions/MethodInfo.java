package aucminer.transactions;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="DetailedInformation", propOrder={"startLine", "endLine", "fullQualifiedName"})
public class MethodInfo {
	
	@XmlElement(name="StartLine")
	//���������������ļ��е���ʼ����
	public int startLine;	
	@XmlElement(name="EndLine")
	//���������������ļ��еĽ�������
	public int endLine;	
	@XmlElement(name="FullQualifiedName")
	//�������÷�������ȫ�޶���:
	//  [QualifiedReturnType#](QualifiedMethodName|QualifiedFieldName)([QualifiedParameterType{,QualifiedParameterType}])
	public String fullQualifiedName;
	//�����������������ļ��ľ���·��
	@XmlTransient
	public String fileFullPath;
	
	public MethodInfo(int startLine, int endLine, String fullQualifiedName, String fileFullPath) {
		this.startLine = startLine;
		this.endLine = endLine;
		this.fullQualifiedName = fullQualifiedName;
		this.fileFullPath = fileFullPath;
	}
	
	@Override
	public int hashCode()
	{
		return fullQualifiedName.hashCode();
	}

	@Override
	public boolean equals(Object o)
	{
		if (o instanceof MethodInfo) {
			return fullQualifiedName.equals(((MethodInfo)o).fullQualifiedName);
		}
		return false;
	}
}
