import java.awt.Color;

public class ThreeXThree {
	private Shape sh;
	private Vector3D f;
	private Vector3D s;
	private Vector3D t;
	private int color;
	public Color colorO;
	public ThreeXThree(Vector3D f, Vector3D s, Vector3D t, Shape sh)
	{
		this.sh = sh;
		this.f = f;
		this.s = s;
		this.t = t;
		color = 0;
	}
	public ThreeXThree(Vector3D f, Vector3D s, Vector3D t, int color, Shape sh)
	{
		this.sh = sh;
		this.f = f;
		this.s = s;
		this.t = t;
		this.color = color;
	}
	public ThreeXThree(Vector3D f, Vector3D s, Vector3D t, Color c, Shape sh)
	{
		this.sh = sh;
		this.f = f;
		this.s = s;
		this.t = t;
		this.color = -1;
		colorO = c;
	}
	public Shape getShape()
	{
		return sh;
	}
	public int getColor()
	{
		return color;
	}
	public Vector3D getF()
	{
		return f;
	}
	public Vector3D getS()
	{
		return s;
	}
	public Vector3D getT()
	{
		return t;
	}
	public double getX1()
	{
		return f.getX();
	}
	public double getY1()
	{
		return f.getY();
	}
	public double getZ1()
	{
		return f.getZ();
	}
	public double getX2()
	{
		return s.getX();
	}
	public double getY2()
	{
		return s.getY();
	}
	public double getZ2()
	{
		return s.getZ();
	}
	public double getX3()
	{
		return t.getX();
	}
	public double getY3()
	{
		return t.getY();
	}
	public double getZ3()
	{
		return t.getZ();
	}
}
