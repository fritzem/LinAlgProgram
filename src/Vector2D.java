
public class Vector2D {
	private int x;
	private int y;
	public Vector2D(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	public int[] getPlace()
	{
		int[] list = new int[2];
		list[0] = x;
		list[1] = y;
		return list;
	}
	public void move(int x, int y)
	{
		this.x += x;
		this.y += y;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
}
