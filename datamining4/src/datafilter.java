import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.print.attribute.standard.OutputDeviceAssigned;


public class datafilter {
	
	public static void main(String[] args) throws IOException{
		/*DataInputStream in=new DataInputStream(new FileInputStream("2.csv"));
		DataOutputStream out=new DataOutputStream(new FileOutputStream("21.csv"));
		String line=in.readLine();
		while (line!=null) {
			String[] attr=line.split(",");
			attr[2]=attr[2].replace("/", "");
			String newline="";
			int i=0;
			for(;i<3;i++){
				newline=newline+attr[i]+",";
			}
			newline=newline+attr[3]+"\n";
			System.out.print(newline);
			out.writeChars(newline);
			line=in.readLine();
		}*/
		
		
		DataInputStream in=new DataInputStream(new FileInputStream("4.csv"));
		DataOutputStream out=new DataOutputStream(new FileOutputStream("41.csv"));
		String line=in.readLine();
		System.out.println(line.split(",").length);
		while (line!=null) {
			String newline="";
			String[] attr=line.split(",");
			if(attr.length<5){
				newline=newline+line+"\n";
				//System.out.println(newline);
				out.writeChars(newline);
				line=in.readLine();
				continue;
			}
			String s=attr[4];
			s=s.split(" ")[0];
		    String[] t=s.split("-");
            if(t[1].length()==1){
			attr[4]=t[2]+"0"+t[1];
			//System.out.println(time);
		    }
		    else {
			attr[4]=t[2]+t[1];
			//System.out.println(time);
		    }
			int i=0;
			for(;i<4;i++){
				newline=newline+attr[i]+",";
			}
			newline=newline+attr[4]+"\n";
			//System.out.print(newline);
			out.writeChars(newline);
			line=in.readLine();
		}
		
		
		
	}

}
