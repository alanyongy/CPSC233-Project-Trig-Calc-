package application;

import java.util.ArrayList;


public class TriangleCatalog {
	private ArrayList<Triangle> triangleList;
	
	TriangleCatalog(){
		triangleList = new ArrayList<Triangle>();
	}
	
	Triangle getPreviousTriangle(Triangle currentTriangle){
		int i = triangleList.indexOf(currentTriangle);
		while (i > 0) {
			if(triangleList.get(i-1) != null) return triangleList.get(triangleList.indexOf(currentTriangle)-1);
		}
		return triangleList.get(0);
	}
	
	Triangle getNextTriangle(Triangle currentTriangle){
		if(triangleList.indexOf(currentTriangle) != triangleList.size()-1)
			return triangleList.get(triangleList.indexOf(currentTriangle)+1);
		return triangleList.get(triangleList.size()-1);
	}
	
	void addTriangle(Triangle oldTriangleStates, Triangle triangleStatesToAdd){
		triangleList.add(triangleStatesToAdd);
	}
	
	void removeTriangle(Triangle triangleStatesToRemove) {
		if(triangleList.contains(triangleStatesToRemove)) triangleList.remove(triangleStatesToRemove);
	}
	
	Triangle getTriangle(int index) {
		if(index < triangleList.size()) {
			return triangleList.get(index);
		}
		return triangleList.get(0);
	}
}
