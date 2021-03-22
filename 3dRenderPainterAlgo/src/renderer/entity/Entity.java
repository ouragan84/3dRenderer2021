package renderer.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import renderer.point.MyPoint;
import renderer.shapes.MyPolygon;
import renderer.shapes.Polyhedron;
import renderer.shapes.WorldShape;

public class Entity implements IEntity {

	private List<Polyhedron> tetrahedrons;
	
	public Entity(List<Polyhedron> tetrahedrons) {
		this.tetrahedrons = new ArrayList<Polyhedron>();
		this.tetrahedrons.addAll(tetrahedrons);
	}

	@Override
	public void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees) {
		for(Polyhedron p : this.tetrahedrons) {
			p.rotate(CW, xDegrees, yDegrees, zDegrees);
		}
	}
	
	public List<WorldShape> getPolygons(){
		List<WorldShape> polys = new ArrayList<WorldShape>();
		System.out.println("    Entity " + this.tetrahedrons.size());
		for(Polyhedron p : this.tetrahedrons) {
			polys.addAll(Arrays.asList(p.getPolygons()));
		}
		return polys;
	}

	//please change to translateOffset and add real translate method
	public void translate(double x, double y, double z) {
		for(Polyhedron p : this.tetrahedrons) {
			p.translate(x, y, z);
		}
	}

	@Override
	public void setPos(double x, double y, double z) {
		for(Polyhedron p : this.tetrahedrons) {
			p.setPos(x, y, z);
		}
	}

	@Override
	public Object getEntity() {
		// TODO Auto-generated method stub
		return this;
	}
	
	public String toString() {
		MyPoint p = tetrahedrons.get(0).getPolygons()[0].getAveragePoint();
		return "pos : (" + p.x + ", " + p.y + ", " + p.z + ")" ;
	}

}
