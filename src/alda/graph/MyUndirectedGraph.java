package alda.graph;
import java.util.ArrayList;
/**
 * ALDA07 - Grafer
 * Daniel Andersson - daan3440
 * @author Daniel Andersson - daan3440  
 **/
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

class MyNode<T>{
	//	MyNode<T> next;
	boolean visited = false;

	public MyNode(T data) {
		//		next = null;
		this.data = data;
	}

	public boolean known = false;
	public T data;
	public HashMap<MyNode<T>, Integer> neighbourMap = new HashMap<>();
	//	public LinkedList<MyNode<T>> neighbourList = new LinkedList<>();

	public int hashCode() {
		return data.toString().hashCode();
	}

	public boolean equals(Object other) {
		if (other instanceof MyNode<?>) {
			MyNode<T> inner = (MyNode<T>) other;
			if (data.equals(inner.data))
				return true;
		}
		return false;
	}


}

public class MyUndirectedGraph<T> implements UndirectedGraph<T> {
	//	private Map<T, Integer> nodes = new HashMap<T, Integer>();

	int nrNodes;
	int nrEdges;
	private HashSet<MyNode<T>> nodes = new HashSet<>();

	@Override
	public int getNumberOfNodes() {
		return nrNodes;
	}

	@Override
	public int getNumberOfEdges() {
		return nrEdges;
	}

	@Override
	public boolean add(T newNode) {
		MyNode<T> addedNode = new MyNode<T>(newNode);
		if (nodes.contains(addedNode)) {
			return false;
		} else {
			nodes.add(addedNode);
			++nrNodes;
			return true;
		}
	}

	@Override
	public boolean connect(T node1, T node2, int cost) {
		MyNode<T> n1 = new MyNode<T>(node1);
		MyNode<T> n2 = new MyNode<T>(node2);

		if (cost <= 0 || !nodes.contains(n1) || !nodes.contains(n2)) {
			return false;
		} else {
			for (MyNode<T> compare : nodes) {
				if (compare.equals(n1)) {
					compare.neighbourMap.put(n2, cost);
					break;
				}
			}
			for (MyNode<T> mn : nodes) {
				if (mn.equals(n2)) {
					mn.neighbourMap.put(n1, cost);
					break;
				}
			}
			return true;
		}
	}

	@Override
	public boolean isConnected(T node1, T node2) {
		MyNode<T> n1 = new MyNode<T>(node1);
		MyNode<T> n2 = new MyNode<T>(node2);
		for (MyNode<T> compare : nodes) {
			if (compare.equals(n1)) {
				return compare.neighbourMap.containsKey(n2);
			}
		}
		return false;
	}

	@Override
	public int getCost(T node1, T node2) {
		MyNode<T> n1 = new MyNode<T>(node1);
		MyNode<T> n2 = new MyNode<T>(node2);
		if (!nodes.contains(n1) || !nodes.contains(n2)) {
			return -1;
		} else {
			for (MyNode<T> compare : nodes) {
				if (compare.equals(n1)) {
					return compare.neighbourMap.get(n2);
				}
			}
			return -1;
		}
	}

	//Depth first
	private List<T> depthList = new LinkedList<>();
	private List<T> tempList = new ArrayList<>();


	@Override
	public List<T> depthFirstSearch(T start, T end) {
		//	MyNode<T> sNode = new MyNode<T>(start);
		//	MyNode<T> eNode = new MyNode<T>(end);
		//	depthList = null;
		depthFirstListBuilder(start);
		return depthList;
	}

	private void depthFirstListBuilder(T start) {
		MyNode<T> sNode = new MyNode<T>(start);	
//		System.out.print(node.data + " ");
		sNode.neighbourMap.containsValue(start);
//		List<MyNode<T>> neighbours= sNode.getNeighbours();
        sNode.visited=true;
       
			if(sNode!=null && !sNode.visited)
			{
				depthFirstListBuilder(value);
			}
        }
		}

public T MinValue(MyNode<T> start) {

    Map.Entry<MyNode<T>, Integer> minEntry = null;

    for (Entry<MyNode<T>, Integer> entry : start.neighbourMap.entrySet()) {

        if (minEntry == null
                || entry.getValue().compareTo(minEntry.getValue()) < 0) {
            minEntry = start;
        }
    }
    return minEntry;

}
		
//	}
////		HashMap<MyNode<T>, Integer> neighbours = sNode.neighbourMap;
//		//		LinkedList<MyNode<T>> neighList= sNode.neighbourList;
////		System.out.println(neighList.size());
//		sNode.visited = true;
//		depthList.add(start);
//		System.out.println(start.toString());
//		//	for (int i = 0; i < neighList.size(); i++) {
////		if(!(neighList.size() == 0)) {
//			for (MyNode<T> cmp: neighbourMap) {
//				T cmpT = cmp.data;
//				if(cmpT!=null && !cmp.visited)
//				{
//					depthFirstListBuilder(cmpT);
//				}
//			}
////		}
//	}
	//private void depthFirstListBuilder(T ns, T end) {
	////	Stack<Integer> stack = new Stack<Integer>();
	//	for(MyNode<T> compare : nodes){
	//	if(!nodes.contains(ns)) {
	////	                stack.push(startIndex);
	//	                depthList.add(ns);
	//	                
	//	while (stack.isEmpty() == false) {
	////	int nodeIndex = stack.pop();
	//	System.out.print(nodeIndex + " nodeIndex");
	//	
	//	HashSet<MyNode<T>> nodeList = nodes;
	////	LinkedList<Integer> nodeList = nodes[nodeIndex];
	//	for (MyNode<T> compare : nodes) {
	//	if (!nodes.contains(end)) {
	//		depthList.add(ns);
	//	                        }
	//	                    }
	//	                }
	//	            }
	//	        }
	//	System.out.println();
	//}
	@Override
	public List<T> breadthFirstSearch(T start, T end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UndirectedGraph<T> minimumSpanningTree() {
		// TODO Auto-generated method stub
		return null;
	}

}
