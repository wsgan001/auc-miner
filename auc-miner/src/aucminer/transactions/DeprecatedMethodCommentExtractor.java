package aucminer.transactions;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

/**
 * ��ȡָ����Ŀ�б����ΪDeprecated�ķ�����ע����Ϣ��
 */
public class DeprecatedMethodCommentExtractor extends BaseInfomationExtractor {
	
	private Map<String, String> comments;
	
	/**
	 * ���캯��
	 * @param projectName ��Ҫ��ȡ�����ΪDeprecated�ķ�����ע����Ϣ����Ŀ����
	 * @see BaseInfomationExtractor#BaseInfomationExtractor(String)
	 */
	public DeprecatedMethodCommentExtractor(String projectName) {
		super(projectName);
		comments = new HashMap<String, String>();
	}
	
	/**
	 * @see BaseInfomationExtractor#extract()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, String> extract() {
		return (Map<String, String>)super.extract();
	}

	/**
	 * @see BaseInfomationExtractor#getCompilationUnitParser(ICompilationUnit, CompilationUnit)
	 */
	@Override
	protected BaseCompilationUnitParser getCompilationUnitParser(ICompilationUnit sourceUnit, CompilationUnit astNodeUnit) {
		return new DeprecatedMethodCommentParser(sourceUnit, astNodeUnit);
	}

	/**
	 * @see BaseInfomationExtractor#appendResult(BaseCompilationUnitParser)
	 */
	@Override
	protected void appendResult(BaseCompilationUnitParser unitParser) {
		if (unitParser instanceof DeprecatedMethodCommentParser) {
			DeprecatedMethodCommentParser commentParser = (DeprecatedMethodCommentParser)unitParser;
			comments.putAll(commentParser.getResult());
		}
	}

	/**
	 * @see BaseInfomationExtractor#getResult()
	 */
	@Override
	protected Object getResult() {
		return comments;
	}
	
	/**
	 * �� {@link CompilationUnit} ����������ΪDeprecated�ķ�����ע�͡�
	 */
	class DeprecatedMethodCommentParser extends BaseCompilationUnitParser {
		
		private Map<String, String> comments;
		
		private String currentClass;
		//When encountering inner or anonymous class declaration, save current class name.
		private Stack<String> classStack;
		
		public DeprecatedMethodCommentParser(ICompilationUnit unit, CompilationUnit astNode) {
			super(unit, astNode);
			comments = new HashMap<String, String>();
			classStack = new Stack<String>();
		}
		
		/**
		 * @see BaseCompilationUnitParser#getResult()
		 */
		@Override
		public Map<String, String> getResult() {
			return comments;
		}
		
		@Override
		public boolean visit(TypeDeclaration node)
		{
			this.pushCurrentClassToStack();
			ITypeBinding binding = node.resolveBinding();
			if (binding != null) {
				this.currentClass = binding.getQualifiedName();
				if (currentClass.isEmpty()) {
					this.currentClass = binding.getDeclaringClass().getQualifiedName() + "." + node.getName().toString();
				}
			}
			else {
				String name = node.getName().toString();
				String path = unit.getResource().getRawLocation().toString();
				System.out.println("TypeDeclaration resolveBinding return null: " +name + ", " + path);
			}
			return super.visit(node);
		}
		
		@Override
		public void endVisit(TypeDeclaration node)
		{
			this.popCurrentClassFromStack();
			super.endVisit(node);
		}
		
		/**
		 * ��ȡ���ΪDeprecated�ķ�����ע�͡�
		 */
		@Override
		public boolean visit(MethodDeclaration node)
		{
			if (node != null) {
				if (isDeprecated(node)) {
					Javadoc doc = node.getJavadoc();
					String fullQualifiedName = getFullNameOfMethod(node.getName().toString(), node.resolveBinding());
					if (doc != null) {
						comments.put(fullQualifiedName, doc.toString());
					}
					else {
						System.out.println(String.format("Deprecated method %s have no javadoc", fullQualifiedName));
					}
				}
				return super.visit(node);
			}
			return false;
		}
		
		/**
		 * ��ȡ�����ΪDeprecated���ֶε�ע�͡�
		 */
		@Override
		public boolean visit(FieldDeclaration node) {
			if ((node != null) && (node.fragments().size() == 1)) {
				if (isDeprecated(node)) {
					Javadoc doc = node.getJavadoc();
					VariableDeclarationFragment fragment = (VariableDeclarationFragment)node.fragments().get(0);
					String fullQualifiedName = getFullNameOfField(fragment.getName().toString(), fragment.resolveBinding());
					if (doc != null) {
						comments.put(fullQualifiedName, doc.toString());
					}
					else {
						System.out.println(String.format("Deprecated field %s have no javadoc", fullQualifiedName));
					}
				}
				return super.visit(node);
			}
			return false;
		}
		
		private boolean isDeprecated(BodyDeclaration node) {
			if (node != null) {
				for(Object o : node.modifiers()) {
					if (o.toString().equals("@Deprecated")) {
						return true;
					}
				}
			}
			return false;
		}
		
		private String getFullNameOfMethod(String name, IMethodBinding methodBinding) {
			if (methodBinding != null)
			{
				ITypeBinding[] typeBinding = methodBinding.getParameterTypes();
				if (typeBinding != null)
				{
					StringBuilder sb = new StringBuilder();
					sb.append(methodBinding.getReturnType().getQualifiedName());
					sb.append("#");
					sb.append(this.currentClass);
					sb.append(".");
					sb.append(name);
					sb.append("(");
					if (typeBinding.length > 0) {
						sb.append(typeBinding[0].getQualifiedName());
						for (int i = 1; i < typeBinding.length; i++) {
							sb.append(",");
							sb.append(typeBinding[i].getQualifiedName());
						}
					}
					sb.append(")");
					return sb.toString();
				}
			}
			return null;
		}
		
		private String getFullNameOfField(String name, IVariableBinding fieldBinding) {
			ITypeBinding typeBinding = fieldBinding.getDeclaringClass();
			if ((fieldBinding != null) && (typeBinding != null)) {
				ITypeBinding fieldType = fieldBinding.getType();
				if (fieldType != null) {
					StringBuilder sb = new StringBuilder();
					sb.append(fieldType.getQualifiedName());
					sb.append("#");
					sb.append(getDeclaringClass(typeBinding, name));
					sb.append(".");
					sb.append(name);
					return sb.toString();
				}
			}
			return null;
		}
		
		private String getDeclaringClass(ITypeBinding binding, String anonymous) {
			String fullName = binding.getQualifiedName();
			if (fullName.isEmpty()) {
				StringBuilder sb = new StringBuilder();
				sb.append(getDeclaringClass(binding.getDeclaringClass(), "@"));
				sb.append(".");
				sb.append(anonymous);
				fullName = sb.toString();
			}
			return fullName;
		}

		private void pushCurrentClassToStack()
		{
			if (this.currentClass != null)
			{
				this.classStack.push(this.currentClass);
			}
		}
		
		private void popCurrentClassFromStack()
		{
			if (this.classStack.empty())
			{
				this.currentClass = null;
			}
			else
			{
				this.currentClass = this.classStack.pop();
			}
		}
	}
}
