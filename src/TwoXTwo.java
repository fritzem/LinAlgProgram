
public class TwoXTwo extends Vector2D{
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	public TwoXTwo(int x1, int y1, int x2, int y2)
	{
		super(x1, y1);
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	public TwoXTwo()
	{
		super(0, 0);
		x1 = 0;
		x2 = 0;
		y1 = 0;
		y2 = 0;
	}
	public int getX2()
	{
		return x2;
	}
	public int getY2()
	{
		return y2;
	}
}
