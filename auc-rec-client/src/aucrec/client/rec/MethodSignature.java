package aucrec.client.rec;

import java.util.List;


public class MethodSignature {
	protected String returnType;
	protected String defClass;
	protected String methodName;
	protected List<String> parameterList;
	
	/*���������ֶΣ�����ȫ�޶���:
	 *[QualifiedReturnType#](QualifiedMethodName|QualifiedFieldName)([QualifiedParameterType{,QualifiedParameterType}])
	 */
	protected String fullQualifiedName;
	
	public MethodSignature() {
		
	}
	
	public MethodSignature(String fullQualifiedName) {
		this.fullQualifiedName = fullQualifiedName;
		
		// TODO: ���� fullQualifiedName �� returnType, defClass, methodName �Լ� parameterList ��ֵ��
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String getDefClass() {
		return defClass;
	}

	public void setDefClass(String defClass) {
		this.defClass = defClass;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public List<String> getParameterList() {
		return parameterList;
	}

	public void setParameterList(List<String> paramTypeList) {
		this.parameterList = paramTypeList;
	}

	public String getFullQualifiedName() {
		return fullQualifiedName;
	}

	public void setFullQualifiedName(String fullQualifiedName) {
		this.fullQualifiedName = fullQualifiedName;
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
