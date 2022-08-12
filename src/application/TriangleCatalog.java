package application;

import java.util.ArrayList;


public class TriangleCatalog {
	private ArrayList<Triangle> triangleList;
	
	TriangleCatalog(){
		triangleList = new ArrayList<Triangle>();
	}
	
	Triangle getPreviousTriangle(Triangle currentTriangle){
		if (triangleList.indexOf(currentTriangle) > 0) {
			return triangleList.get(triangleList.indexOf(currentTriangle)-1);
		}
		return null;
	}
	
	Triangle getNextTriangle(Triangle currentTriangle){
		if(triangleList.indexOf(currentTriangle) < triangleList.size()-1)
			return triangleList.get(triangleList.indexOf(currentTriangle)+1);
		return null;
	}
	
	void addTriangle(Triangle triangleToAdd){
		triangleList.add(triangleToAdd);
	}
	
	void removeTriangle(Triangle triangleToRemove) {
		if(triangleList.contains(triangleToRemove)) triangleList.remove(triangleToRemove);
	}
	
	Triangle getTriangle(int index) {
		if(index < triangleList.size()) {
			return triangleList.get(index);
		}
		return triangleList.get(0);
	}
	
	int getIndexInList(Triangle triangle) {
		if (triangleList.contains(triangle)) {
			for(int i = 0; i < triangleList.size(); i++) {
				if(triangleList.get(i) == triangle) return i;
			}
		}
		return 0;
	}
	
	int getListSize(){
		return triangleList.size();
	}
}
