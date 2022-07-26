package application;

public class Triangle {
	double h, o, a;
	Point center, ho, ha, oa;
	
	Triangle(double setH, double setO, double setA){
		h = setH;
		o = setO;
		a = setA;	
		center = new Point(a/2, o/2);
		ho = new Point(a,o);
		ha = new Point(-a,-o);
		oa = new Point(a,-o);
	}

}
