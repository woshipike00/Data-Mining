
public class Element {
	private double[] p;
	
	public Element(double[] p){
		this.p=p;
	}
	
	public double[] getp(){
		return p;
	}
	
	public static double distance(Element e1,Element e2){
		double[] p1=e1.getp();
		double[] p2=e2.getp();
		double s=0;
		for (int i=0;i<FileRead.attribute_num;i++){
			s+=Math.pow(Math.abs(p1[i]-p2[i]),2);
		}
		return Math.sqrt(s);
	}
	
	public static int absolute_error(Element e1,Element e2){
		int E=0;
		for (int i=0;i<FileRead.attribute_num;i++){
			E+=Math.abs(e1.getp()[i]-e2.getp()[i]);					
		}
		return E;
	}

}
