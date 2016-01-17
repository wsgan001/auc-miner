package cn.edu.nju.raua.fpmining.closetplus.fptree;

import cn.edu.nju.raua.core.transactions.Item;

/**
 * ͷ����
 * @author fei
 *
 */
public class HeaderTableItem {
	
	private Item item;                  //�����Ӧ��Item.
	private int supportCount;           //Item��֧�ֶȼ���
	private FPTreeNode sideLinkPointer; //�ڵ�����ָ���Item��FP-tree�е�λ�á�
	
	public HeaderTableItem(Item item, int support) {
		if (item == null) {
			throw new IllegalArgumentException("Argument 'item' cannot be null.");
		}
		if (support <= 0) {
			throw new IllegalArgumentException("Argument 'support' must be positive.");
		}
		this.item = item;
		this.supportCount = support;
		this.sideLinkPointer = null;
	}

	public FPTreeNode getSideLinkPointer() {
		return sideLinkPointer;
	}

	public void setSideLinkPointer(FPTreeNode sideLinkPointer) {
		this.sideLinkPointer = sideLinkPointer;
	}

	public Item getItem() {
		return item;
	}
	
	public int getSupportCount() {
		return supportCount;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object instanceof HeaderTableItem) {
			HeaderTableItem tItem = (HeaderTableItem)object;
			return item.equals(tItem.getItem()) && (supportCount == tItem.getSupportCount());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (item.hashCode() << 7) ^ new Integer(supportCount);
	}
}
