package aucminer.transactions;

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaModel;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

/**
 * ��Դ������ȡ�����Ϣ�Ļ���
 */
public abstract class BaseInfomationExtractor {
	
	/**
	 * ��ʾ������ȡ��Ϣ��Java��Ŀ��
	 */
	protected IJavaProject project;
	
	/**
	 * ���캯��
	 * @param projectName ��Ҫ��ȡ��Ϣ����Ŀ���ơ�
	 */
	public BaseInfomationExtractor(String projectName) {
		IWorkspaceRoot workspace = ResourcesPlugin.getWorkspace().getRoot();
		IJavaModel javaModel = JavaCore.create(workspace);
		project = javaModel.getJavaProject(projectName);
	}
	
	/**
	 * ��ָ����Ŀ��AST����ȡ������Ϣ��
	 * @return ������ȡ����Ϣ
	 */
	public Object extract() {
		IPackageFragmentRoot[] roots;
		try {
			roots = project.getAllPackageFragmentRoots();
			for (int i = 0; i < roots.length; i++)
			{
				IPackageFragmentRoot root = roots[i];
				switch (root.getKind())
				{
					case IPackageFragmentRoot.K_SOURCE:
						IJavaElement[] elem = root.getChildren();
						for (int j = 0; j < elem.length; j++)
						{
							IJavaElement currentElement = elem[j];
							if (currentElement.getElementType() == IJavaElement.PACKAGE_FRAGMENT) {
								process((IPackageFragment)currentElement);
							}
						}
						break;
					case IPackageFragmentRoot.K_BINARY:
						break;
				}
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		
		return getResult();
	}
	
	protected final void process(IPackageFragment fragment) throws JavaModelException
	{
		try
		{
			ICompilationUnit[] units = fragment.getCompilationUnits();

			for (ICompilationUnit compilationUnit : units)
			{
				ASTParser parser = ASTParser.newParser(AST.JLS8);
				parser.setProject(this.project);
				parser.setResolveBindings(true);
				parser.setBindingsRecovery(true);
				parser.setSource(compilationUnit);
				try
				{
					ASTNode node = parser.createAST(null);
					if (node instanceof CompilationUnit) {
						BaseCompilationUnitParser unitParser = getCompilationUnitParser(compilationUnit, (CompilationUnit)node);
						node.accept(unitParser);
						appendResult(unitParser);
					}
				}
				catch (RuntimeException thr)
				{
					throw thr;
				}
			}
		}
		catch (RuntimeException thr)
		{
			thr.printStackTrace();
		}
	}
	
	/**
	 * �������ڴ�CompilationUnit����ȡ��Ϣ��{@link cn.edu.nju.raua.comment.parser.BaseCompilationUnitParser}��
	 * @param unit ��Ӧ��Java compilation unit(source file with one of the Java-like extensions).
	 * @param astNode {@link org.eclipse.jdt.core.dom.CompilationUnit}���͵�ʵ��
	 * @return һ��{@link cn.edu.nju.raua.comment.parser.BaseCompilationUnitParser}ʵ��
	 */
	protected abstract BaseCompilationUnitParser getCompilationUnitParser(ICompilationUnit unit, CompilationUnit astNode);
	
	/**
	 * ����ĳ��{@link org.eclipse.jdt.core.dom.CompilationUnit}����ȡ����Ϣ���浽���յĽ����
	 * @param unitParser {@link cn.edu.nju.raua.comment.parser.BaseCompilationUnitParser}��һ��ʵ����������
	 * ��ĳ�� {@link org.eclipse.jdt.core.dom.CompilationUnit}����ȡ����Ϣ
	 */
	protected abstract void appendResult(BaseCompilationUnitParser unitParser);
	
	/**
	 * �������յ���ȡ��Ϣ��
	 * @return �������յĽ��
	 */
	protected abstract Object getResult();
}
