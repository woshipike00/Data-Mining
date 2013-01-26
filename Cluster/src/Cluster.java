import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Cluster {
	public static Element[] elements;
	public static final int k=3;
	private ArrayList<Integer> eles;
	private int middle;
	
	public Cluster(int middle){
		eles=new ArrayList<Integer>();
		this.middle=middle;
	}
	
	public void addelement(int i){
		eles.add(i);
	}
	
	public void removeelement(int i){
		eles.remove(i);
	}
	
	
	//计算新的中心点
	public int getnewmiddle(){
		int middle=eles.get(0);
		int E=10000000;
		for (int i=0;i<eles.size();i++){
			int tempE=0;
			for (int j=0;j<eles.size() && j!=i;j++){
				tempE+=Element.absolute_error(elements[eles.get(i)], elements[eles.get(j)]);
				//System.out.println("tempE: "+tempE);
			}
			if(tempE<E){
				middle=eles.get(i);
				//System.out.println("middle: "+middle);
				E=tempE;
			}
		}
		return middle;
	}
	
	public ArrayList<Integer> geteles(){
		return eles;
	}
	
	public void setmiddle(int m){
		middle=m;
	}
	
	public int getmiddle(){
		return middle;
	}
	
	public void clear(){
		eles.clear();
	}
	
	public static boolean compare(int[] e1,int[] e2){
		int i=0;
		for (;i<e1.length;i++){
			if(e1[i]!=e2[i])
				break;
		}
		if(i==e1.length)
			return true;
		else return false;
	}
	
	public static void main(String[] args) throws IOException{
		/*FileRead fileRead=new FileRead("clustering data");
		fileRead.readfile();
		double[][] matrix=fileRead.getmatrix();
		//初始化实例
		Cluster.elements=new Element[FileRead.instance_num];
		for(int i=0;i<FileRead.instance_num;i++){
			elements[i]=new Element(matrix[i]);
		}
		
		//任意选择两个种子划分为k类
		Cluster[] c=new Cluster[Cluster.k];
		int[] formermiddle=new int[Cluster.k];
		int[] currentmiddle=new int[Cluster.k];
		c[0]=new Cluster(0);
		c[1]=new Cluster(1);
		c[2]=new Cluster(2);
		formermiddle[0]=0;
		formermiddle[1]=1;
		formermiddle[2]=2;
		for (int i=0;i<FileRead.instance_num;i++){
			double[] d=new double[Cluster.k];
			int shortest=0;
			for (int m=0;m<Cluster.k;m++){
				d[m]=Element.distance(Cluster.elements[i], Cluster.elements[c[m].getmiddle()]);
				if(d[m]<d[shortest])
					shortest=m;
			}
			c[shortest].addelement(i);
		}
        //获取新的中心点
		for (int i=0;i<Cluster.k;i++){
			currentmiddle[i]=c[i].getnewmiddle();
			System.out.println("c size "+i+":"+c[i].geteles().size());
			System.out.println("currentmiddle "+i+":"+currentmiddle[i]);
			
		}
		//System.out.println("c size: "+c[0].geteles().size()+","+c[1].geteles().size()+","+c);
		//System.out.println("currentmiddle:"+currentmiddle[0]+","+currentmiddle[1]);
		
		while(!Cluster.compare(formermiddle, currentmiddle)){
			//更新簇
			for (int i=0;i<Cluster.k;i++){
				c[i].setmiddle(currentmiddle[i]);
				c[i].clear();
				formermiddle[i]=currentmiddle[i];
			}
			for (int i=0;i<FileRead.instance_num;i++){
				double[] d=new double[Cluster.k];
				int shortest=0;
				for (int m=0;m<Cluster.k;m++){
					d[m]=Element.distance(Cluster.elements[i], Cluster.elements[c[m].getmiddle()]);
					if(d[m]<d[shortest])
						shortest=m;
				}
				c[shortest].addelement(i);
			}
			//获取新的中心点
			for (int i=0;i<Cluster.k;i++){
				currentmiddle[i]=c[i].getnewmiddle();
				System.out.println("c size "+i+":"+c[i].geteles().size());
				System.out.println("currentmiddle "+i+":"+currentmiddle[i]);
			}
		}
		
		System.out.println("end");
	    int[] label=new int[1080];
	    for (int i=0;i<Cluster.k;i++){
	    	for (int j=0;j<c[i].geteles().size();j++){
	    		label[c[i].geteles().get(j)]=i;
	    	}
	    }
	    
	    DataOutputStream out=new DataOutputStream(new FileOutputStream("output1.txt"));
	    
		for (int i=0;i<1080;i++){
			out.writeUTF(String.valueOf(label[i])+'\n');

		}
	    out.close();*/
		FileReader reader=new FileReader(new File("output.txt"));
		FileReader reader1=new FileReader(new File("output1.txt"));

		Scanner scanner=new Scanner(reader);
		int count1=0,count2=0;
		while(scanner.hasNext()){
			count1++;
			scanner.nextLine();
		}
		
		scanner=new Scanner(reader1);
		while(scanner.hasNext()){
			count2++;
			scanner.nextLine();
		}
		
		System.out.println(count1+"\n"+count2);
		
	}
	
	

}
