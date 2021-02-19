package dataStructure.tree.balanceTree;

/**
 * 平衡二叉树
 * 
 * 定义:首先它是一种特殊的二叉排序树，其次它的左子树和右子树都是平衡二叉树， 且左子树和右子树的深度之差不超过1
 * 平衡因子:可以定义为左子树的深度减去右子树的深度
 * 平衡因子为-1（右子树的深度大于左子树的深度）
 * 平衡因子为0（左右子树的深度相等）
 * 平衡因子为1（左子树的深度大于右子树的深度）
 * @author 李雅翔
 * @date 2017年9月30日
 */
public class BalanceTree<T extends Comparable<T>> {
	private TreeNode<T> root = null;
	
	private int size = 0;//元素个数
	
	public BalanceTree() {
		
	}
	
	public int size() {
		return size;
	}
	
	public TreeNode<T> put(T key){
		if (key != null) {
			TreeNode<T> node = new TreeNode<>(key, null, null, null);
			putTreeVal(node);
		}
		
		return root;
		
	}
	/**
	 * 添加元素
	 * @param node
	 * @return
	 */
	private TreeNode<T> putTreeVal(TreeNode<T> node) {
		int ph = 0;
		TreeNode<T> xp = null;
		//查找插入节点的位置
		for (TreeNode<T> p = root;p != null && p.key != null;) {
			xp = p;
			if ((ph = p.key.compareTo(node.key))>0) {
				p = p.left;
			}else if (ph<0) {
				p= p.right;
			}else if (ph == 0) {
				return p;
			}
		}
		node.parent = xp;
		if (xp != null) {
			if (ph > 0) {
				xp.left = node;
			}else if (ph < 0) {
				xp.right = node;
			}
		}else {
			root = node;
		}
		 //自下向上回溯，查找最近不平衡节点  
		while (xp != null) {
			ph = node.key.compareTo(xp.key);
			if (ph < 0) {
				//插入节点在parent的左子树中  
				xp.balance ++;
			}else {
				//插入节点在parent的右子树中  
				xp.balance --; 
			}
			if (xp.balance == 0) {
				//如果 此时子树 平衡因子 = 0，就不用调整了 
				/**
				 *     1X               0X
				 * 	   / \ 				/ \
				 *   0Y   Z  ->       0Y   Z -1
				 *   / \ 			  /\    \
				 *  M  N             M  N	 L 0
				 *  	
				 */
				break;
			}
			//Math.abs()取绝对值
			if (Math.abs(xp.balance) == 2) {
				fixAfterInsertion(xp);  
				break;
			}
			xp = xp.parent;
		}
		size++;
		return node;
	}

	public TreeNode<T> remove(T key){
		TreeNode<T> node = null;
		if ((node = find(key)) != null) {
			return deleteNode(node);
		}
		return null;
	}

	/**
	 * 删除节点 与红黑树删除步骤类似 所以可以参考 手写的TreeMap 红黑树 删除实现
	 * @param p
	 * @return
	 */
	private TreeNode<T> deleteNode(TreeNode<T> p) {
		size --;
		if (p.left != null && p.right != null) {
			//删除节点左右子节点不为空，找后继节点
			TreeNode<T> s = successor(p);
			p.key = s.key;
			p = s;
		}
		//当删除节点只有一子或没有子时，当p有左子则，肯定没有右子
		//当删除节点有两子时 ，p 实际上是 后继节点 则p肯定没有左子，replacement 是其右子或空
		TreeNode<T> replacement = (p.left != null?p.left:p.right);
		
		if (replacement != null) {
			//左右树不为空
			replacement.parent = p.parent;
			if (p.parent == null) { //如果p为root节点  
				root = replacement;
			}else if (p == p.parent.left) {
				p.parent.left = replacement;
			}else {
				 p.parent.right = replacement;  
			}
			p.left = p.right = p.parent = null;
			fixAfterDeletion(replacement);  
		}else if (p.parent == null) {
			root = null;
		}else {
            fixAfterDeletion(p);    //这里从p开始回溯  
            //将p节点从树中删除
            if (p.parent != null) {  
                if (p == p.parent.left)  
                    p.parent.left = null;  
                else if (p == p.parent.right)
                    p.parent.right = null;  
                p.parent = null;  
            } 
		}
		return null;
	}

	

	/** 
     * 返回以中序遍历方式遍历树时，t的直接后继 
     */  
     TreeNode<T> successor(TreeNode<T> t) {  
        if (t == null)  
            return null;  
        else if (t.right != null) { //往右，然后向左直到尽头  
            TreeNode<T> p = t.right;  
            while (p.left != null)  
                p = p.left;  
            return p;  
        } else {  
        	System.out.println("进入莫名的领域");
        	System.exit(0);
        	//right为空，如果t是p的左子树，则p为t的直接后继  
            TreeNode<T> p = t.parent;  
            TreeNode<T> ch = t;  
            while (p != null && ch == p.right) {    //如果t是p的右子树，则继续向上搜索其直接后继  
                ch = p;  
                p = p.parent;  
            }  
            return p;  
        }  
    }  
	public TreeNode<T> get(T key){
		return find(key);
	}
	private TreeNode<T> find(T key){
		TreeNode<T> p = root;
//		int i = 0;
		while(p != null) {
//			i++;
			int ph;
			T pk;
			TreeNode<T> pl = p.left,pr = p.right;
			if ((ph = p.key.compareTo(key))>0) {
				p = pl;
			}else if (ph<0) {
				p = pr;
			}else if ((pk = p.key ) == key || (key != null && key.equals(pk))) {
//				System.out.println("总共进行了："+i+"次查找");
				return p;
			}
		}
		return null;
	}
	/********************************************************/
	/***************         维持树的平衡                       **************/
	/********************************************************/
	//左旋
	private void  rotateLeft(TreeNode<T> x) {
		 // 设置x的右孩子为y
		TreeNode<T> r,pp,rl;
	    if (x != null &&(r = x.right) != null) {    //r->Z
			if ((rl = x.right = r.left)!= null) {   //r.left ->β  rl->β  X的右子指向 β
				//one
				rl.parent = x;                      // β 的父节点指向  X
			}
			if ((pp = r.parent = x.parent) == null) {//Z.parent = X.parent  如果是根节点 
				root = r;
			}else if (pp.left == x) {//如果 要左旋的节点是父节点的左节点
				pp.left = r;
			}else {
				pp.right = r;
			}
			r.left = x;
			x.parent = r;
		}
	}
 
   //右旋

	private void  rotateRight(TreeNode<T> x) {
		TreeNode<T> l,pp,lr;
		if (x != null && (l = x.left) != null) {//l -> α
			if ((lr = x.left = l.right) != null) {//lr x的左节点的右节点 
				lr.parent = x;
			}
			if ((pp = l.parent = x.parent) == null) {
				root = l;
			}else if (pp.right == x) {
				pp.right = l;
			}else {
				pp.left = l;
			}
			l.right = x;
			x.parent = l;
		}
	}
 
	/** 
	 * X:插入的节点   R：最小不平衡子树的根
     * 调整的方法： 
     * 1.当最小不平衡子树的根(以下简称R)为2时，即左子树高于右子树： 
     * 如果R的左子树的根节点的BF为1时，做右旋,如下所示：R是Y的左子  ，X是Y的左子
     * 		R
     *     / 
     *    Y 
     *   / 
     *  X       ;
     * 如果R的左子树的根节点的BF为-1时，先左旋然后再右旋 ,如下所示：R是Y的左子  ，X是Y的右子
     * 		R
     *     / 
     *    Y 
     *     \
     *      X     ;
     * 2.R为-2时，即右子树高于左子树： 
     * 如果R的右子树的根节点的BF为1时，先右旋后左旋 
     * 如果R的右子树的根节点的BF为-1时，做左旋 
     *  
     * 至于调整之后，各节点的BF变化见代码 
     */  
	private void fixAfterInsertion(TreeNode<T> x) {
		if (x.balance ==2) {
			//左子树高于右子树
			leftBalance(x);
		}else if (x.balance == -2) {
			rightBalance(x); 
		}
		
	}
	
	/**
	 * 删除平衡树
	 * @param x
	 */
	private void fixAfterDeletion(TreeNode<T> x) {
		 boolean heightLower = true;  //看最小子树调整后，它的高度是否发生变化，如果减小，继续回溯  
		 TreeNode<T> xp = x.parent;
		 int ph;
		 while (xp != null && heightLower) {
			ph = x.key.compareTo(xp.key);
			if (ph >0) { //x是父节点的右子,则x处删除了一个节点，平衡因子+1;如果是添加一个节点，平衡因子-1
				xp.balance++;//与添加正好相反
			}else {
				xp.balance--;
			}
			TreeNode<T> r = xp;  
			if (xp.balance == 2) {
				heightLower = leftBalance(r);  
			}else if (xp.balance == -2) {
				heightLower = rightBalance(r);
			}
			xp = xp.parent;
		}	
	}
	
	/**
	 * 左平衡处理 
	 * @param x 最小不平衡子树的根
	 */
	private boolean leftBalance(TreeNode<T> x) {
        boolean heightLower = true;  
        TreeNode<T> xl = x.left;
        switch (xl.balance) {
		case LH://1 左树高  右旋
			/**
			 *       10(x)          5
			 *      /              / \
			 *     5(xl)    ->    1  10
			 *    / 
			 *   1  
			 */ 
			x.balance = xl.balance = EH;//0
			rotateRight(x);
			break;
		case RH://-1 右树高
			TreeNode<T> xlr = xl.right;//x节点的左子的右子
			switch (xlr.balance) {
			case LH://左子树高 
				/**
				 *          20 (2)                  20                 15
				 * 		 /     \                   /  \ 			  /  \
				 *      10(-1)  30(0)             15  30             10  20
				 *     / \                 ->    /             ->   / \    \
				 *    5  15(xlr  1)            10                  5  12    30
				 *       /                    / \ 
				 *      12                   5  12
				 *         
				 */
				x.balance = RH;//20
				xl.balance = EH;//10
				break;
			case EH://左右子树平衡
				/**
				 *       10(x 2)           10              6 (0)
				 *      /                  /              /    \
				 *     5(xl -1)      ->   6      ->     5(0)  10(x 0)
				 *      \                /
				 *       6(xlr 0)       5
				 */ 
				x.balance = xl.balance = EH;
				break;
			case RH:
				/**
				 *          20 (2)                  20                 15
				 * 		 /     \                   /  \ 			  /  \
				 *      10(-1)  30(0)             15  30             10  20
				 *     / \                 ->    / \          ->     /   / \
				 *    5  15(xlr -1)            10  17               5   17  30
				 *        \                    /  
				 *        17                  5  
				 *         
				 */
				x.balance = EH;//20
				xl.balance = LH;//10
				break;	
			}
			
			xlr.balance = EH;//15
			rotateLeft(xl);
			rotateRight(x);
			break;
        case EH:      //特殊情况4,这种情况在添加时不可能出现，只在移除时可能出现，旋转之后整体树高不变  
            xl.balance = RH;
            x.balance = LH;
            rotateRight(x);
            heightLower = false;
            break;
		}
        return heightLower;
	}

	/**
	 * 右平衡处理 同上
	 * @param x : 最小不平衡子树的根
	 */
	private boolean rightBalance(TreeNode<T> x) {
		boolean heightLower = true;  
        TreeNode<T> xr = x.right;  
        switch (xr.balance) {  
        case LH:            //左高，分情况调整  
            TreeNode<T> xrl = xr.left;  
            switch (xrl.balance) {   //调整各个节点的BF  
            case LH:    //情况1  
                x.balance = EH;  
                xr.balance = RH;  
                break;  
            case EH:    //情况2  
                x.balance = xr.balance = EH;  
                break;  
            case RH:    //情况3  
                x.balance = LH;  
                xr.balance = EH;  
                break;  
            }  
            xrl.balance = EH;  
            rotateRight(x.right);  
            rotateLeft(x);  
            break;  
        case RH:            //右高，左旋调整  
            x.balance = xr.balance = EH;  
            rotateLeft(x);  
            break;  
        case EH:       //特殊情况4  
            xr.balance = LH;  
            x.balance = RH;  
            rotateLeft(x);  
            heightLower = false;  
            break;  
        }  
        return heightLower;  
	}

	/********************************************************/
	/*****************        树节点                       *****************/
	/********************************************************/
	private static final int LH = 1; // 左高
	private static final int EH = 0; // 等高
	private static final int RH = -1; // 右高

	static final class TreeNode<T extends Comparable<T>> {
		T key;
		TreeNode<T> left; // 左子
		TreeNode<T> right; // 右子
		TreeNode<T> parent; // 父节点
		int balance = EH;

		public TreeNode() {
			
		}
		
		public TreeNode(T key,TreeNode<T> parent, TreeNode<T> left, TreeNode<T> right){  
			this.key = key;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }
		public TreeNode(T key,TreeNode<T> parent){  
            this.key = key;  
            this.parent = parent;  
        }  

  
        @Override  
        public String toString() {  
            return key+" 高度差="+balance;  
        }  
	}
}
