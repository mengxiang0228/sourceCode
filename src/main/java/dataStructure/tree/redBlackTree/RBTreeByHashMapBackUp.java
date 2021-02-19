package dataStructure.tree.redBlackTree;

/**
 * HashMap 红黑树 实现  备份
 */
public class RBTreeByHashMapBackUp <T extends Comparable<T>>{
	private TreeNode<T> root;
	private int size;
//	private static final boolean RED   = false;
//  private static final boolean BLACK = true;
	
    static final class TreeNode<T extends Comparable<T>>{
    	boolean red; //true : 红  ；false ： 黑
    	T key;
    	TreeNode<T> left;	//左子
    	TreeNode<T> right;	//右子
    	TreeNode<T> parent;	//父节点
    	TreeNode<T> root;
    	
    	public TreeNode() {
    		
    	}
    	public TreeNode(T key, boolean red, TreeNode<T> parent, TreeNode<T> left, TreeNode<T> right) {
    		 this.key = key;
             this.red = red;
             this.parent = parent;
             this.left = left;
             this.right = right;
    	}
    	
    	//查找根节点
    	private TreeNode<T> root(){
    		for(TreeNode<T> r = this,p;;) {
    			if ((p = r.parent) == null) {
    				return r;
    			}
    			r = p;
    		}
    	}
    	public TreeNode<T> getRoot(){
			return root();
    		
    	}
    	
		@Override
    	public String toString() {
    		return ""+key+":"+(red?"red":"black");
    	}
    }
    
  
	
	/**
	 * 新建节点key，将其插入红黑树
	 * @param key
	 */
	public  void put(T key) {
		TreeNode<T> node = new TreeNode<T>(key, true, null, null, null);
		if (node != null) {
			putTreeVal(node);
		}
	}
	
	//将节点放到 树中
	private TreeNode<T> putTreeVal(TreeNode<T> node){
		
		int ph = 0;
		TreeNode<T> xp = null;

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
			if (ph > 0) 
				xp.left = node;
			else if (ph<0) {
				xp.right = node;
			} 
		}else {
			root = node;
		}
		node.red = true;
		size++;
		root = balanceInsertion(root, node);
		return node.parent;
		
	} 	
	
	/**
	 * 查找树中的值
	 * @param key
	 * @return
	 */
	public TreeNode<T> get(T key){
		return find(key);
	}
	/**
	 * 查找当前值所在节点
	 * @param key
	 * @return
	 */
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
	
	public void remove(T key) {
		TreeNode<T> node = find(key);
		if (node != null) {
			removeTreeNode(node);
		}
	}
	/**
	 * 删除节点设为p
	 * 内部只负责将删除节点和替换节点互换位置
	 */
	private void removeTreeNode(TreeNode<T> p) {
		TreeNode<T>	pl = p.left;
		//获取后继节点
		TreeNode<T> pr = p.right;
		TreeNode<T> replacement;//替换节点
		if (pl != null && pr != null) {
			//左右子节点都存在,则它的后继是其右子树中最小的那个元素
			TreeNode<T> s = pr,sl;
			while ((sl = s.left) != null) {
				//找它右子树最小的元素
				s = sl;//s->pr.left 
			}
			//s 放的是要替换的节点信息
			//交换颜色   将删除节点颜色给了替换节点，保证替换以后如果删除的是黑色 不会少一个黑色节点
			boolean c = s.red;
			s.red = p.red;	//替换节点 颜色改为  p     颜色
			p.red = c;		//  p   颜色改为 替换节点 颜色
			TreeNode<T> sr = s.right;//替换节点只有右子而没有左子，或者 替换节点是叶子节点
            TreeNode<T> pp = p.parent;

            if (s == pr) {
            	//替换节点是p的子节点  此时s只有右子没有左子
            	/**
            	 *      p			s
	             *	   / \			 \
	             *	  pl  s     ->	  p
	             *	       \		 /
	             *			sr		pl   sr 
            	 */
				p.parent = s;
				s.right = p;	
			}else {
				/**
            	 *      p							p1(sp)			         s
            	 *       \							 \				          \
            	 *        p1						  p				           p1(sp)
	             *		 /		    ->                           ->		        \
	             *		s       s == sp.left       s         s.right = pr        p
	             *	     \							\
	             *		 sr		   					 sr                         sr
            	 */
				TreeNode<T> sp = s.parent; //sp = p1
				// 把删除节点的父亲设为  替换节点的父亲
				/**
				 *   p = X pl = Y  pr = Z s = M  sr=N  
				 * 		X            X
				 * 	   / \	        / \
				 *    Y   Z    ->  Y   Z
				 *       /			  /	
				 *      M			 X    M
				 *    	 \				   \
				 *        N	                N
				 */       
				if ((p.parent = sp) != null) {
					if (s == sp.left) {
						sp.left = p;
					}else {
						// 如果s 不是p的直接子节点 ，则s 是 sp的左子节点, 不应该存在 s是sp的右子
						//测试 60000个随机数 都没有进来
						//理论上应该不会进入 hashMap这样写了
						sp.right = p;
					}
				}
				//把替换节点的右子 链接为 删除节点原来的右子
				/**
				 *   p = X pl = Y  pr = Z s = M  sr=N  
				 * 		X               M
				 * 	   / \	             \
				 *    Y   Z      ->       Z
				 *       /			     /	
				 *      X   M		    X     
				 *     /    \		   /	   
				 *    Y      N	      Y        N
				 */ 
				if ((s.right = pr) != null) {
					pr.parent = s;
				}
			}	
            /**
			 *   p = X pl = Y  pr = Z s = M  sr=N    
			 * 		M               M
			 * 	     \	             \
			 *        Z      ->       Z
			 *       /			     /	
			 *      X   		    X     
			 *     /    		   	   
			 *    Y      N	      Y        N
			 */ 
            p.left = null;
            
            /**

             * 
             * 替换节点是p的子节点
        	 *    s			              s							        s
        	 *	   \		               \							   / \
        	 *		p         -> 	        p            ->               pl  p
        	 *            p.right = sr       \         s.left = pl             \
        	 *	  sr			             sr								   sr
        	 *
        	 * 替换节点是p的后继节点
			 *   p = X pl = Y s = M  sr=N   
			 * 		M               M
			 * 	     \	             \
			 *        Z      ->       Z
			 *       /			     /	
			 *      X   		    X     
			 *         		   	     \
			 *    Y      N	      Y   N
			 */ 
            // Right[p] <- Right[s]
            if ((p.right = sr) != null)
                sr.parent = p;
            /**
			 *  p = X pl = Y s = M  sr=N  
			 * 		M               M
			 * 	     \	           / \
			 *        Z      ->   Y   Z
			 *       /			     /	
			 *      X   		    X     
			 *       \ 		   	     \
			 *    Y   N	             N
			 */ 
            // Left[s] <- Left[p]
            if ((s.left = pl) != null)
                pl.parent = s;
            // P[s] <- P[p]
            if ((s.parent = pp) == null)
            	//如果p是根节点
                root = s;
            else if (p == pp.left)
            	//Left[P[p]] <- s
                pp.left = s;
            else
            	//Right[P[p]] <- s
                pp.right = s;
            if (sr != null)
                replacement = sr;
            else
                replacement = p;// s 没有子节点
		}else if (pl != null) {
			//当只有 左子时，其左子必定是叶子节点
			replacement = pl;
		}else if (pr != null) {
			//与上同理
			replacement = pr;
			//替换节点就是删除节点的子节点
			//如果 子节点是红的，父节点肯定是黑的；删除父节点 势必要重新调整节点  只需将子节点设为黑就行
		}else {
			//p是叶子节点
			replacement = p;
		}
		
		if (replacement != p) {
			//p 或者 替换节点(s)  节点不是叶子节点   replacement = sr
			/**
			 * 	  s				  s(pp)
			 * 	   \			   \
			 * 		p      -> 		sr
			 * 		 \				 
			 * 		  sr			  p
			 */
			 TreeNode<T> pp = replacement.parent = p.parent;//将replacement的父节点改为删除节点的父节点
			 if (pp == null) {
				root = replacement;
			}else if (p == pp.left) {
				//如果p是父的左子
				//将删除节点的父左子指向改为替换节点
				pp.left = replacement;
			}else {
				 pp.right = replacement;
			}
			 p.left = p.right = p.parent = null;//便于GC p节点内容全部置空,断开它与其他节点之间的链接
		}
		
		//因为删除节点p和替换节点s互换了颜色，所以p.red指的是替换节点的颜色
		//如果替换的节点是红色 则直接删除对树不会有影像
		//当替换节点只有一个或没有左右子时 p是红色的直接删除，子节点上移
		root = p.red ? root : balanceDeletion(root, replacement);//如果删除的p节点是黑色，就需要重新调整
		
		if (replacement == p) {
			//只有在p是根节点或者 p的替换节点没有右子
			 TreeNode<T> pp = p.parent;
             p.parent = null;//p的父节点置为看
             if (pp != null) {//父节点不为空,为空就是根节点，只要把p节点删了就行
                 if (p == pp.left)
                	 //当前节点是 父亲的左节点
                     pp.left = null;//删除父节点中p的链接
                 else if (p == pp.right)
                     pp.right = null;
             }
             if (root.right == null && root.left == null) {
				root = null;
			}
		}
		
	}
	
	/**
	 * 得到根节点
	 */
	public TreeNode<T> getRoot(){
		return root;
	}
	
	/**
	 * 元素个数
	 */
	public int size(){
		return size;
	}
	
    @Override
	public String toString() {
    	if (root != null) 
    		preOrder(root);
    	else
    		System.out.println("null");
		return null;
	}
    
    private void preOrder(TreeNode<T> tree) {
    	if (tree != null) {
    		System.out.println(tree+"   ");
    		preOrder(tree.left);
    		preOrder(tree.right);
		}
    }

    public void preOrder() {
    	preOrder(root);
    }
	
    
	/*************************************************/
	/*****              维持树的平衡                                     ******/
	/*************************************************/
	
	//左旋
	//			X                    X
	//		  /   \      one       /  |
	//		α		Z    -->  	 α	  |  Z 
	//			   /  \				  |   \	
	//			  β    γ		      β     γ
	private TreeNode<T>  rotateLeft(TreeNode<T> root,TreeNode<T> x) {
		 // 设置x的右孩子为y
		TreeNode<T> r,pp,rl;
	    if (x != null &&(r = x.right) != null) {    //r->Z
			if ((rl = x.right = r.left)!= null) {   //r.left ->β  rl->β  X的右子指向 β
				//one
				rl.parent = x;                      // β 的父节点指向  X
			}
			if ((pp = r.parent = x.parent) == null) {//Z.parent = X.parent  如果是根节点 
				(root = r).red = false;
			}else if (pp.left == x) {//如果 要左旋的节点是父节点的左节点
				pp.left = r;
			}else {
				pp.right = r;
			}
			r.left = x;
			x.parent = r;
		}
	    return root; 
	}
  
    //右旋
  	//			X               
  	//		  /   \           
  	//		α		Z      	 
  	//	   /  \				  
  	//	  β    γ     
	private TreeNode<T>  rotateRight(TreeNode<T> root,TreeNode<T> x) {
		TreeNode<T> l,pp,lr;
		if (x != null && (l = x.left) != null) {//l -> α
			if ((lr = x.left = l.right) != null) {//lr x的左节点的右节点 
				lr.parent = x;
			}
			if ((pp = l.parent = x.parent) == null) {
				 (root = l).red = false;
			}else if (pp.right == x) {
				pp.right = l;
			}else {
				pp.left = l;
			}
			l.right = x;
			x.parent = l;
		}
		return root;
	}
  
	//添加完成后平衡二叉树
	//平衡原则：将红色的节点移到根节点；然后，将根节点设为黑色。
	private TreeNode<T> balanceInsertion(TreeNode<T> root,TreeNode<T> x){
		x.red = true;//给x节点着色
		for(TreeNode<T> xp,xpp,xppl,xppr;;) {
			if((xp = x.parent) == null) {
				//如果当期节点是根节点
				x.red = false;
				return x;
			}else if (!xp.red || (xpp = xp.parent)==null) {
				//只有当循环到根节点时才返回，保证将红色的节点移到根节点
				//父节点是黑色，或者  (祖父节点为空 ->父节点为根节点，根据性质 根节点为 黑)
				//父节点是黑色 说明 多出的红色节点已经去除了，直接返回就行
				return root;
			}
			//父节点为红色
			if (xp == (xppl =xpp.left)) {
				//父节点是祖父的左子
				if ((xppr = xpp.right) != null && xppr.red) {
					//case 1
					//叔叔节点不为空 且叔叔节点 为红色
					//父和叔叔 设置为 黑色 ; 祖父设置为 红色
					xp.red = false;
					xpp.red =true;//父节点
					xppr.red = false;//叔叔节点
					x = xpp;//将当前节点改为祖父节点
				}else {
					//case 2        当前节点的父节点是红色，叔叔节点是黑色
					//case2  是为了转换为case3 进行处理
					//叔叔节点是黑色 或者 叔叔节点为空（黑）
					if (x == xp.right) {
						//x(当前节点) 是父的右子,父是祖父的左子
						root = rotateLeft(root, x = xp);//以父节点为支点左旋 
						//x已经是旋转后的了,重新定位父节点，祖父节点 
						xpp = (xp = x.parent) == null ? null : xp.parent;
					}
					if (xp != null) {
						xp.red = false;//父节点变黑
						if (xpp != null) {
							//没有祖父节点 父节点是根节点
							//case 3  当前节点的父节点是红色，叔叔节点是黑色，且当前节点是其父节点的左孩子
							xpp.red = true;
							root = rotateRight(root, xpp);//祖父节点右旋
						}
					}
				}
			}
			else {
				//父节点是祖父的右子
				if (xppl != null && xppl.red) {
					//case 1  
					//叔叔节点不为空 且叔叔节点 为红色
					//父和叔叔 设置为 红色 ; 祖父设置为 黑色
					xppl.red = false;
					xp.red = false;
                    xpp.red = true;
                    x = xpp;
				}else {
					if (x == xp.left) {
						//当前节点是 父的左子
						//case2 转换为case3
						 root = rotateRight(root, x = xp);
						 xpp = (xp = x.parent) == null ? null : xp.parent;
					}
					if (xp != null) {
						xp.red = false;
						if (xpp != null) {
							//case3
							xpp.red = true;//祖父设为红
							root = rotateLeft(root, xpp);//以祖父为节点左旋
						}
					}
				}
			}
		}
	}
	
	/**
	 * 删除完成后平衡树
	 * @param root
	 * @param replacement
	 * @return
	 */
	private TreeNode<T> balanceDeletion(TreeNode<T> root, TreeNode<T> x) {
		for(TreeNode<T> xp,xpl,xpr;;) {
			if (x == null || x == root) {
				//最开始传入的x应该不会为空，是下面算法中 将x置空 以便返回
				return root;
			}else if ((xp = x.parent) == null) {
				return x;
			}else if (x.red) {
				// 如果x是 红色，则改为黑色
				//此时x应该是 替换节点的右子节点 或者其他
				//如果x是红色的，则它的父节点是黑色的，而它的父节点就是替换节点，替换后 当前位置少了一个黑色节点,所以将节点置为黑色就行
				x.red = false;
				return root;
			}else if ((xpl = xp.left) == x) {
				//如果x是父节点的左子
				if((xpr = xp.right) != null && xpr.red) {
					/**    
 					 * case 1: x的兄弟节点是红色->x的兄弟节点的子节点都是黑+x的父亲是黑
					 * case 1 转 case 2
					 *     xp(黑)
					 *	   /  \
					 *  X(黑)   xpr(红)
					 *          /  \
					 *		         黑     黑
					 */
					xpr.red = false;
					xp.red = true;
					/**     xp(红)               xpr(黑)
					 *	   /  \				   /    \
					 *  X(黑)   xpr(黑)  ->  xp(红)   黑
					 *          /  \		 /  \	
					 *		         黑     黑                  X    黑
					 */
					root = rotateLeft(root, xp);
					xpr = (xp = x.parent) == null?null:xp.right;//重新定位 x的兄弟节点
					//如果x是根节点 则将 xpr 置空，在下一步中 再将 x设置为 xp =null,最后直接返回
				}
				//      xp
				//	   /  \
				//  X(黑)   xpr(黑)
				if (xpr == null) {
					x = xp;//将节点向上移动
				}else {
					TreeNode<T> sl = xpr.left,sr = xpr.right;//设置兄弟节点的左右子
					if ((sl == null || !sl.red) && (sr == null || !sr.red)) {
						//如果兄弟的左右子都是时黑色或者空（空也是代表黑色）
						//case 2  x的兄弟节点设为“红色+ 设置“x的父节点”为“新的x节点”
						xpr.red = true;
						x = xp;
					}else {
						if (sr == null || !sr.red) {
							//case 3
							//兄弟的右子 是黑色，左子红色
							// (01) 将x兄弟节点的左孩子设为“黑色”。
							// (02) 将x兄弟节点设为“红色”。
							// (03) 对x的兄弟节点进行右旋。
							// (04) 右旋后，重新设置x的兄弟节点。
							if (sl != null) {
								sl.red = false;
							}
							xpr.red = true;//x的兄弟节点是红色
							root = rotateRight(root, xpr);
							xpr = (xp = x.parent)==null?null:xp.right;
						}
						//case 4  x的兄弟节点是黑色  x的兄弟节点的右孩子是红色的
						//只要兄弟节点存在，首先 兄弟的右子肯定是红色的 ，因为上面的步骤就是保证了右子是红的
						if (xpr != null) {
							xpr.red = (xp == null)?false:xp.red;//(01) 将x父节点颜色 赋值给 x的兄弟节点。这样做保证了旋转以后，祖父节点和父节点颜色不会冲突
							if ((sr = xpr.right) != null) {
								sr.red = false; //(03) 将x兄弟节点的右子节设为“黑色”。
							}
						}
						if (xp != null) {
							xp.red = false;//(02) 将x父节点设为“黑色”
							root = rotateLeft(root, xp);//(04) 对x的父节点进行左旋。
						}
						x = root;//调整完成，直接返回
					}
				}
			}else {
				//如果x是父节点的右子
                if (xpl != null && xpl.red) {
                	//case 1  兄弟节点是红
                    xpl.red = false;
                    xp.red = true;
                    root = rotateRight(root, xp);
                    xpl = (xp = x.parent) == null ? null : xp.left;
                }
                if (xpl == null)
                    x = xp;
                else {
                    TreeNode<T> sl = xpl.left, sr = xpl.right;
                    if ((sl == null || !sl.red) &&
                        (sr == null || !sr.red)) {
                    	// case 2 黑 黑
                        xpl.red = true;
                        x = xp;
                    }
                    else {
                        if (sl == null || !sl.red) {
                            if (sr != null)
                                sr.red = false;
                            xpl.red = true;
                            root = rotateLeft(root, xpl);
                            xpl = (xp = x.parent) == null ?
                                null : xp.left;
                        }
                        if (xpl != null) {
                            xpl.red = (xp == null) ? false : xp.red;
                            if ((sl = xpl.left) != null)
                                sl.red = false;
                        }
                        if (xp != null) {
                            xp.red = false;
                            root = rotateRight(root, xp);
                        }
                        x = root;
                    }
                }
			}
		}
	}
}
