package application;

public class Triangle {
	private double h, o, a;
	private Point center, ho, ha, oa;
	private String info;
	
	Triangle(double setH, double setO, double setA, String setInfo){	
		h = setH;
		o = setO;
		a = setA;	
		info = setInfo;
		
		center = new Point(a/2, o/2);
		ho = new Point(a,o);
		ha = new Point(-a,-o);
		oa = new Point(a,-o);
	}
	
	public double getH() {
		return h;
	}

	public void setH(double h) {
		this.h = h;
	}

	public double getO() {
		return o;
	}

	public void setO(double o) {
		this.o = o;
	}

	public double getA() {
		return a;
	}

	public void setA(double a) {
		this.a = a;
	}

	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public Point getHo() {
		return ho;
	}

	public void setHo(Point ho) {
		this.ho = ho;
	}

	public Point getHa() {
		return ha;
	}

	public void setHa(Point ha) {
		this.ha = ha;
	}

	public Point getOa() {
		return oa;
	}

	public void setOa(Point oa) {
		this.oa = oa;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}
