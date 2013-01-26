import java.awt.List;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.SpringLayout.Constraints;

import org.w3c.dom.ls.LSException;


public class DecisionTree {
	
	private Node root;
	private double[][] attributes;
	private int[] labels;
	private int elenum=700;
	
	public DecisionTree(){
		root=new Node();
		root.D=new ArrayList<Integer>();
		//设置root节点元组
		for (int i=0;i<elenum;i++)
			root.D.add(i);
		attributes=new double[elenum][42];
		labels=new int[elenum];
		root.attribute_list=new ArrayList<Integer>();
		for (int i=0;i<42;i++){
			root.attribute_list.add(i);
		}
	}
	
	public void setattributes(double[][] a){
		attributes=a;
	}
	
	public void setlabels(int[] l){
		labels=l;
	}
	
	public Node getroot(){
		return root;
	}
	
	public static class Node{
		public Node(){
			left=null;
			right=null;
			splitting_attribute=-1;
			splitting_point=0;
			type=0;
		}
		//左右子树
		Node left,right;
		//标记所属类别，0表示中间节点，1,-1表示类别
		int type;
		//分裂属性,分裂点
		int splitting_attribute;
		double splitting_point;
		//该节点的数据划分
		ArrayList<Integer> D;
		ArrayList<Integer> attribute_list;
		
	}
	
	public void Generate_decision_tree(Node p){
		//D中的元组都是同一类tempc
		int tempc=SameClass(p.D);
		
		if(tempc!=0){
			p.type=tempc;
			return;
		}
		//属性为空标记为D中多数类
		if(p.attribute_list.size()==0){
			p.type=majority_voting(p.D);
			return;
		}
		
		attribute_selection_method(p);
		//System.out.println("l & r dsize: "+p.left.D.size()+","+p.right.D.size());
		
		//递归处理子树
		Generate_decision_tree(p.left);
		Generate_decision_tree(p.right);

	}
	
	public void attribute_selection_method(Node p){
		ArrayList<Integer> D=p.D;
		int attribute=-1;
		double splitting_point=0;
		double GainRatio=-10000;
		//存储一个属性的所有连续值
		ArrayList<Double> values=new ArrayList<Double>(); 
		double InfoD=getinfo(D);
	    ArrayList<Integer> D1=new ArrayList<Integer>();
	    ArrayList<Integer> D2=new ArrayList<Integer>();
	    ArrayList<Integer> tD1=new ArrayList<Integer>();
	    ArrayList<Integer> tD2=new ArrayList<Integer>();


		//对所有属性进行计算
		for (int i=0;i<p.attribute_list.size();i++){
			values.clear();
			for (int j=0;j<D.size();j++)
				values.add(attributes[D.get(j)][p.attribute_list.get(i)]);
			Collections.sort(values);
			/*for (int u=0;u<values.size();u++)
				System.out.print(u+":"+values.get(u)+"  ");
			System.out.println();*/
			//取排序后的相邻属性的中间值作为分割点
			for (int j=0;j<D.size()-1;j++){
				double split=(values.get(j)+values.get(j+1))/2;
				//System.out.println("split: "+split+"   "+values.get(j)+","+values.get(j+1));
			    double m=0,n=0;
			    tD1.clear();
			    tD2.clear();
			    //根据分裂点划分为D1，D2
			    for (int k=0;k<D.size();k++){
			    	if(attributes[D.get(k)][p.attribute_list.get(i)]<=split)
			    		{tD1.add(D.get(k));m++;}
			    	else{
				    	tD2.add(D.get(k));
				    	n++;    
			    	}
			    }
			    double p1=m/(m+n),p2=n/(m+n);
			    double InfoA=p1*getinfo(tD1)+p2*getinfo(tD2);
			    double GainA=InfoD-InfoA;
			    double SplitInfoA=-(p1*Math.log(p1)/Math.log(2)+p2*Math.log(p2)/Math.log(2));
				double GainRatioA=GainA/SplitInfoA;
				//System.out.println("tD1,tD2 size: "+tD1.size()+","+tD2.size());
				//System.out.println("info D,tD1,tD2: "+InfoD+","+getinfo(tD1)+","+getinfo(tD2));
				//System.out.println("p1,p2: "+p1+","+p2);
				//System.out.println("gaina: "+GainA);
				//System.out.println("splitinfoa: "+SplitInfoA);
				//System.out.println("gainratioa: "+GainRatioA);
				if (GainRatioA>GainRatio){
					//System.out.println("gaina>gain");
					attribute=p.attribute_list.get(i);
					splitting_point=split;
					GainRatio=GainRatioA;
					D1=(ArrayList<Integer>)tD1.clone();
					D2=(ArrayList<Integer>)tD2.clone();
				}				
			}
		}
		//删除划分属性
		p.attribute_list.remove(new Integer(attribute));
		System.out.println("split_attribute: "+attribute);
		p.type=0;
		p.splitting_attribute=attribute;
		p.splitting_point=splitting_point;
		p.left=new Node();
		p.right=new Node();
		p.left.D=D1;
		p.right.D=D2;
		p.left.attribute_list=(ArrayList<Integer>)p.attribute_list.clone();
		p.right.attribute_list=(ArrayList<Integer>)p.attribute_list.clone();
		//System.out.println("d1,d2 size: "+D1.size()+","+D2.size());
		//System.out.println("attr1,attr2 size: "+p.left.attribute_list.size()+","+p.right.attribute_list.size());

	}
			
	public double getinfo(ArrayList<Integer> D) {
		//System.out.println(D.size());
		double m=0,n=0;
		for (int i=0;i<D.size();i++){
			if(labels[D.get(i)]==1)
				m++;
			else n++;
		}
		double p1=m/(m+n),p2=n/(m+n);
		double info;
		if(p1==0 || p2==0)
			info=0;
		else
		    info=-(p1*Math.log(p1)/Math.log(2)+p2*Math.log(p2)/Math.log(2));
		return info;
	}
	
	
	//判断D中的元组是否是同一类
	public int SameClass(ArrayList<Integer> D){
		int tempc=labels[D.get(0)];
		if(D.size()==1)
			return tempc;
		int i=1;
		for (;i<D.size();i++){
			if(labels[D.get(i)]!=tempc)
				break;
		}
		if(i==D.size())
			return tempc;
		else 
			return 0;
	}
	
	//多数表决
	public int majority_voting(ArrayList<Integer> D){
		int n=0,m=0;
		for (int i=0;i<D.size();i++){
			if(labels[D.get(i)]==1)
				n++;
			else m++;
		}
		if(n>=m)
			return 1;
		else return -1;
	}
	
	public static int classify(double[] ele,Node root){
		Node node=root;
		int type=node.type;
		while(type==0){
			int splitattr=node.splitting_attribute;
			double splitpoint=node.splitting_point;
			//System.out.println("split attr,point: "+splitattr+","+splitpoint);
			if(ele[splitattr]<=splitpoint){
				node=node.left;
				type=node.type;
			}
			else{
				node=node.right;
				type=node.type;
			}
		}
		return type;
	}
	
	public static void main(String[] args) throws IOException{
		
		DataOutputStream output=new DataOutputStream(new FileOutputStream("output.txt"));
		DataRead dataread=new DataRead("train instance", "train label");
		dataread.DataParse();		
		DecisionTree DT=new DecisionTree();		
		DT.setattributes(dataread.getattr());
		DT.setlabels(dataread.getlabel());
		DT.Generate_decision_tree(DT.root);
		System.out.println("tree generated");
		dataread.ReadTestData("test instance");
		double[][] testdata=dataread.gettestaddr();
		int m=0,n=0;
		for (int i=0;i<400;i++){
			double[] element=testdata[i];
			int tclass=DecisionTree.classify(element, DT.getroot());
			output.writeUTF(Integer.toString(tclass));
			output.writeChar('\n');
			if(tclass==-1)
				m++;
			else n++;
			//System.out.println(tclass);		
		}
		System.out.println("m,n: "+m+","+n);
		output.close();
		
		
	}

}
