package dataStructure.tree.redBlackTree;

import dataStructure.tree.redBlackTree.RBTreeByHashMap.TreeNode;

/**
 * 自己手写第二次实现红黑树 根据 HashMap
 * @author 李雅翔
 * @date 2017年9月28日
 */
public class Red_BlackTree<T extends Comparable<T>> {
	private TreeNode<T> root;

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

	private TreeNode<T> putTreeVal(TreeNode<T> node) {
		int ph = 0;
		TreeNode<T> xp = null;
		for(TreeNode<T> p = root; p != null;) {
			xp = p;
			if ((ph = p.key.compareTo(node.key)) > 0) {
				p = p.left;
			}else if (ph < 0) {
				p = p.right;
			}else {
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
		node.red = true;
		root = balanceInsertion( node);
		return node.parent;
	}

	public void remove(T key) {
		TreeNode<T> node = find(key);
		if (node != null) {
			removeTreeNode(node);
		}
	}
	

	private void removeTreeNode(TreeNode<T> p) {
		TreeNode<T>	pl = p.left;
		//获取后继节点
		TreeNode<T> pr = p.right;
		TreeNode<T> replacement ;
		if (pl != null && pr != null) {
			TreeNode<T> s = pr,sl;
			//寻找后继节点
			while ((sl = s.left) != null) {
				s = sl;
			}
			//交换颜色
			boolean c = s.red;
			s.red = p.red;
			p.red = c;
			
			TreeNode<T> sr = s.right;//替换节点只有右子而没有左子，或者 替换节点是叶子节点
            TreeNode<T> pp = p.parent;
            
			//删除节点和替换节点交换位置
			if (s == pr) {
				//P[p] <- P[s]
				p.parent = s;
				s.right = p;
			}else {
				TreeNode<T> sp = s.parent;
				// 把删除节点的父亲设为  替换节点的父亲
				//P[p] <- P[s]
				if ((p.parent =sp) != null) {
					if (s == sp.left) {
						sp.left = p;
					}else {
						//理论上应该不会进入 hashMap这样写了
						sp.right = p;
					}
				}
				
				//把替换节点的右子 链接为 删除节点原来的右子
				//Right[s] <- Right[p]
				if ((s.right =pr) != null) {
					pr.parent = s;
				}
			}
			
			p.left = null;
			//把 删除节点的右子 链接为 替换节点原来的右子
			// Right[p] <- Right[s]
			if ((p.right = sr) != null) {
				sr.parent = p;
			}
			
			//将删除节点  替换为  为 s(后继节点)
			// Left[s] <- Left[p]
			if ((s.left = pl) != null) {
				pl.parent = s;
			}
			
			// P[s] <- P[p]
			if ((s.parent = pp) == null) {
				root = s;
			}else if ( p == pp.left ) {
				pp.left = s;
			}else {
				pp.right = s;
			}
			
			//确定替换节点位置
			if (sr != null) {
				replacement = sr;
			}else {
				replacement = p;
			}
			
		}else if (pl != null) {
			replacement = pr;
		}else if (pr != null) {
			replacement = pl;
		}else {
			// p是叶子节点时
			replacement = p;
		}
		
		if (replacement != p) {
			TreeNode<T> pp = replacement.parent = p.parent;
			if (pp == null) {
				root = replacement ;
			}else if (p == pp.left) {
				pp.left = replacement;
			}else {
				pp.right = replacement;
			}
			 p.left = p.right = p.parent = null;
		}
		// 可能会带 删除节点进行 重新着色
		root = p.red ? root : balanceDeletion(replacement); 
		
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
	 * 查找当前值所在节点
	 * @param key
	 * @return
	 */
	private TreeNode<T> find(T key){
		TreeNode<T> p = root;
		int i = 0;
		while(p != null) {
			i++;
			int ph;
			T pk;
			TreeNode<T> pl = p.left,pr = p.right;
			if ((ph = p.key.compareTo(key))>0) {
				p = pl;
			}else if (ph<0) {
				p = pr;
			}else if ((pk = p.key ) == key || (key != null && key.equals(pk))) {
				System.out.println("总共进行了："+i+"次查找");
				return p;
			}
		}
		
		return null;
	}
	
	public TreeNode<T> rotateLeft(TreeNode<T> x){
		TreeNode<T> r,pp,rl;
		if (x != null && (r = x.right)!=null) {
			if ((rl = x.right =r.left) != null) {
				rl.parent = x;
			}
			if ((pp = r.parent = x.parent) == null) {
				//根节点 旋转后设为红色
				(root = r).red = false;
			}else if (pp.left == x) {
				pp.left = r;
			}else {
				pp.right = r;
			}
			r.left = x;
			x.parent = r;
		}
		return root;
	}
	
	public TreeNode<T> rotateRight(TreeNode<T> x){
		TreeNode<T> l,lr,pp;
		if (x != null && (l = x.left) != null) {
			if ((lr = x.parent = l.right) != null) {
				lr.parent = x;
			}
			if((pp = l.parent = x.parent) == null) {
				l.red = false;
				root = l;
			}else if (pp.left == x) {
				pp.left = l;
			}else {
				pp.right = l;
			}
			
			l.right = x;
			x.parent = l;
		}
		return root;
	}
	
	/**
	 * 平衡原则：将红色的节点移到根节点；然后，将根节点设为黑色。
	 */
	private TreeNode<T> balanceInsertion(TreeNode<T> x) {
		x.red = true;
		for(TreeNode<T> xp,xpp,xppl,xppr;;) {
			if ((xp = x.parent) == null) {
				x.red = false;
				return x;
			}else if (!xp.red || (xpp = xp.parent) ==null) {
				//父节点为根节点 或者 父节点 是黑色
				//添加的子节点 是 红色 不影响性质
				return root;
			}
			if (xp == (xppl = xpp.left)) {
				//父节点是祖父的左子
				if ((xppr = xpp.right) != null && xppr.red) {
					//case 1
					//叔叔节点为红色,父节点也是红色
					xp.red = false;
					xppr.red = false;
					xpp.red = true;
					x = xpp;
				}else {
					//case 2 ->case3
					
					if (x == xp.right) {
						/**
						 * case2
						 * 	    xpp
						 * 	    / \
						 * 	  xp   xppr
						 *      \
						 *       x
						 *  以 父节点 左旋
						 */
						root = rotateLeft(x = xp);//左旋的同时将当前节点改为父节点
						xpp = (xp = x.parent) == null?null:xp.parent;//重定位父节点和祖父节点
					}
					if (xp != null) {
						//case3
						xp.red = false;
						if (xpp != null) {
							xpp.red = true;
							root = rotateRight(xpp);
						}
					}
				}
			}else {
                if (xppl != null && xppl.red) {
                    xppl.red = false;
                    xp.red = false;
                    xpp.red = true;
                    x = xpp;
                }
                else {
                    if (x == xp.left) {
                        root = rotateRight(x = xp);
                        xpp = (xp = x.parent) == null ? null : xp.parent;
                    }
                    if (xp != null) {
                        xp.red = false;
                        if (xpp != null) {
                            xpp.red = true;
                            root = rotateLeft( xpp);
                        }
                    }
                }
			}
		}
	}
	
	private TreeNode<T> balanceDeletion(TreeNode<T> x) {
		/**
		 * 带删除节点进入的情况
		 * 情况1：当 删除节点是叶子节点,且删除节点是黑色，则x是黑色
		 * 情况2：当后继节点没有右子时,且删除节点是黑色，则x是黑色
		 */
		for(TreeNode<T> xp,xpl,xpr;;) {
			if (x == null ||x == root) {
				// x == root : 删除节点是根节点，且当前树只有根节点
				// x == null :最开始传入的x不可能为空，下面的算法会将x置空，以便处理完后返回
				return root;
			}else if ((xp = x.parent) == null) {
				return x;
			}else if (x.red) {
				/**
				 * 当删除节点没有左右子时
				 * x = 删除节点
				 * x肯定是黑色,不会进入
				 */
				/**
				 * 当删除节点只有一子时
				 * x = 后继节点
				 * x是红色,则替换节点是红色，而删除节点是黑色
				 * 此时只有把替换节点变成黑色就可以了
				 */
				/**
				 * 当删除节点有两子时，且后继节点没有右子时（进行过互换颜色） 且  replacement = 删除节点
				 * x = replacement = 删除节点   而删除节点和后继节点互换过颜色 
				 * 首先能进入该方法 删除节点(换色之后)肯定是 黑色,则 x也是黑色,不会进入
				 */
				/**
				 * 当 删除节点有两子时，且 后继节点有右子时(此时后继节点点必定没有左子) replacement = 后继节点的右子
				 * 能进入方法说明 ：删除节点是黑色，则原来后继节点的是黑色，
				 * 此时 后继节点的右子是 红色，而它的父亲（后继节点）是黑色，当后继节点替换了删除节点以后，
				 * 后继节点原来父亲的左子下面少了一个黑色节点，只有一个红色节点，只要把 节点置为黑色就行
				 */
				x.red = false;
				return root;
			}else if ((xpl = xp.left) == x) {
				//case 1
				if ((xpr = xp.right) != null && xpr.red) {
					// x的兄弟节点是红色，则兄弟节点的子节点肯定为黑
					xpr.red = false;
					xp.red = true;//保证左旋之后与祖父节点不发生冲突
					root = rotateLeft(xp);
					xpr = (xp = x.parent) == null ? null : xp.right;//重新定位兄弟节点
				}
				if (xpr == null) {
					x = xp;
				}else {
					//兄弟节点是黑色
					TreeNode<T> sl = xpr.left,sr = xpr.right;
					
					if ((sl == null || !sl.red) && (sr == null || !sr.red)) {
						//兄弟子都为空或者都为黑  
						//case 2
						xpr.red = true;
						x = xp;
					}else {
						//case 3
						if (sr == null || !sr.red) {
							// 兄弟右子是黑色,且根据上面可知 兄弟左子时红色
							if (sl != null) {
								sl.red =false;
							}
							xpr.red = true;
							root = rotateRight(xpr);//右旋之后保证了 兄弟节点那块 左子时黑（或空） ，右子是红，转化为case 4 进行处理
							xpr = (xp = x.parent)==null?null:xp.right; 
						}
						//case 4   右子肯定是红色,左子红或黑
						if (xpr != null) {
							xpr.red = (xp == null) ? false:xp.red;
							if ((sr = xpr.right) != null) {
								sr.red = false;//(03) 将x兄弟节点的右子节设为“黑色”。
							}
						}
						if (xp != null) {
							xp.red = false;//(02) 将x父节点设为“黑色”
							root = rotateLeft(xp);//(04) 对x的父节点进行左旋。
						}
						x = root;//直接返回
					}
				}
			}else {
				//如果x是父节点的右子
                if (xpl != null && xpl.red) {
                    xpl.red = false;
                    xp.red = true;
                    root = rotateRight(xp);
                    xpl = (xp = x.parent) == null ? null : xp.left;
                }
                if (xpl == null)
                    x = xp;
                else {
                    TreeNode<T> sl = xpl.left, sr = xpl.right;
                    if ((sl == null || !sl.red) &&
                        (sr == null || !sr.red)) {
                        xpl.red = true;
                        x = xp;
                    }
                    else {
                        if (sl == null || !sl.red) {
                            if (sr != null)
                                sr.red = false;
                            xpl.red = true;
                            root = rotateLeft( xpl);
                            xpl = (xp = x.parent) == null ?null : xp.left;
                        }
                        if (xpl != null) {
                            xpl.red = (xp == null) ? false : xp.red;
                            if ((sl = xpl.left) != null)
                                sl.red = false;
                        }
                        if (xp != null) {
                            xp.red = false;
                            root = rotateRight(xp);
                        }
                        x = root;
                    }
                }
			}
		}
		
	}

}
