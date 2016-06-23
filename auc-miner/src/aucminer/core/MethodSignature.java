package aucminer.core;

import java.util.List;

public class MethodSignature {
	private String returnType; //��������ֵ���ͻ��ֶ�����
	private String defClass;	//���巽�����ֶε���
	private String methodName;  //�������ֶε�����
	private List<String> paramTypeList; //���������б����Ϊ null ���ʾ�ֶΣ�Field��
	
	/*���������ֶΣ�����ȫ�޶���:
	 *[QualifiedReturnType#](QualifiedMethodName|QualifiedFieldName)([QualifiedParameterType{,QualifiedParameterType}])
	 */
	private String fullQualifiedName;
	
	public MethodSignature(String returnType, String defClass, String methodName, List<String> paramTypeList) {
		if((returnType == null) || (defClass == null) || (methodName == null)) {
			throw new NullPointerException("Arguments can not be null.");
		}
		
		this.returnType = returnType;
		this.defClass = defClass;
		this.methodName = methodName;
		this.paramTypeList = paramTypeList;
		
		fullQualifiedName = returnType + "#" + defClass + "." + methodName;
		if (paramTypeList != null) {
			fullQualifiedName += "(";
			for (int i = 0; i < paramTypeList.size() - 1; i++) {
				fullQualifiedName += (paramTypeList.get(i) + ", ");
			}
			if (paramTypeList.size() - 1 >= 0) {
				fullQualifiedName += paramTypeList.get(paramTypeList.size() - 1);
			}
			fullQualifiedName += ")";
		}
	}
	
	public String getReturnType() {
		return returnType;
	}
	
	public String getDefClass() {
		return defClass;
	}
	
	public String getMethodName() {
		return methodName;
	}
	
	public List<String> getParamTypeList() {
		return paramTypeList;
	}
	
	public String getFullQualifiedName() {
		return fullQualifiedName;
	}
	
	@Override
	public String toString() {
		return fullQualifiedName;
	}
	
	@Override
	public int hashCode()
	{
		return fullQualifiedName.hashCode();
	}

	@Override
	public boolean equals(Object o)
	{
		if (o instanceof MethodSignature) {
			return fullQualifiedName.equals(((MethodSignature)o).fullQualifiedName);
		}
		return false;
	}
}
