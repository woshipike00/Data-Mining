import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class FileRead {
	private DataInputStream in;
    public final static int instance_num=1080;
	public final static int attribute_num=856;
	private double[][] matrix;
	
	public FileRead(String filename) throws FileNotFoundException{
		in=new DataInputStream(new FileInputStream(filename));
	}
	
	public void readfile() throws IOException{
		matrix=new double[instance_num][attribute_num];
		for (int i=0;i<instance_num;i++){
			String instance=in.readLine();
			String[] attr=instance.split("\\s");
			for (int j=0;j<attribute_num;j++)
				matrix[i][j]=Double.parseDouble(attr[j]);
		}
	}
	
	public double[][] getmatrix(){
		return matrix;
	}

}
