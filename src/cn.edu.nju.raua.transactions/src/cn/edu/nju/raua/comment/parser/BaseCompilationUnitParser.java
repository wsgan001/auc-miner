package cn.edu.nju.raua.comment.parser;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;

/**��{@link org.eclipse.jdt.core.dom.CompilationUnit}���ʵ������ȡ��Ϣ�ĳ�����ࡣ
 * ������չ��{@link org.eclipse.jdt.core.dom.ASTVisitor}��
 */
public abstract class BaseCompilationUnitParser extends ASTVisitor {
	
	protected ICompilationUnit unit;
	protected CompilationUnit astNode;
	
	/**
	 * ���캯��
	 * @param unit ��Ӧ��Java compilation unit(source file with one of the Java-like extensions).
	 * @param astNode ��Ҫ������ȡ��Ϣ��{@link org.eclipse.jdt.core.dom.CompilationUnit}���ʵ��
	 */
	public BaseCompilationUnitParser(ICompilationUnit unit, CompilationUnit astNode) {
		this.unit = unit;
		this.astNode = astNode;
	}
	
	/**
	 * ��ȡ��org.eclipse.jdt.core.dom.CompilationUnit���ʵ������ȡ����Ϣ
	 * @return �����õ�����Ϣʵ��
	 */
	public abstract Object getResult();
}
