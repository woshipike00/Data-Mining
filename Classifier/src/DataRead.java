import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;


public class DataRead {
	private DataInputStream instance,label;
	private double[][] attributes;
	private int[] labels;
	private double[][] testattributes;
	private int trainnum=700;
	private int testnum=400;
	
	public DataRead(String instancefile,String labelfile) throws IOException{
		attributes=new double[trainnum][42];
		labels=new int[trainnum];
		instance=new DataInputStream(new FileInputStream(instancefile));
		label=new DataInputStream(new FileInputStream(labelfile));
	}
	
	//对训练数据进行转换
	public void DataParse() throws IOException{
		for (int i=0;i<trainnum;i++){
			String temp=instance.readLine();
			String temp2=label.readLine();
			String[] attr=temp.split("\\s");
			for (int j=0;j<42;j++)
				attributes[i][j]=Double.parseDouble(attr[j]);
			labels[i]=Integer.parseInt(temp2);
		}
		instance.close();
		label.close();
	}
	
	//	读取测试数据
	public void ReadTestData(String filename) throws IOException{
		testattributes=new double[testnum][42];
		DataInputStream test=new DataInputStream(new FileInputStream(filename));
		for (int i=0;i<testnum;i++){
			String temp=test.readLine();
			String[] attr=temp.split("\\s");
			for (int j=0;j<42;j++)
				testattributes[i][j]=Double.parseDouble(attr[j]);
		}
		test.close();
	}
	
	public double[][] getattr(){
		return attributes;
	}
	
	public int[] getlabel(){
		return labels;
	}
	
	public double[][] gettestaddr(){
		return testattributes;
	} 

}
