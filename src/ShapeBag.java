import java.util.ArrayList;

public class ShapeBag {
	private ArrayList<Shape> listShapes;
	private ArrayList<ThreeXThree> listVectors;
	public ShapeBag()
	{
		listShapes = new ArrayList<Shape>();
		listVectors = new ArrayList<ThreeXThree>();
	}
	public void addShape(Shape s)
	{
		
	}
	public ArrayList<Shape> getShapeList()
	{
		return listShapes;
	}
	public ArrayList<ThreeXThree> getVectors()
	{
		return listVectors;
	}
	public void arrange()
	{
		pullVectors();
		//if (listVectors.isEmpty())
		//	return;
		
		
		ArrayList<ThreeXThree> dummyList = new ArrayList<ThreeXThree>();
		while (!listVectors.isEmpty())
		{
			int depth = 1000000000;
			int bot = -1;
			for (int i = 0; i < listVectors.size(); i++)
			{
				
				if (listVectors.get(i).getZ1() + listVectors.get(i).getZ2() + listVectors.get(i).getZ3() < depth)
				{
					depth = (int)listVectors.get(i).getZ1() + (int)listVectors.get(i).getZ2() + (int)listVectors.get(i).getZ3();
					bot = i;
				}
			}
			if (bot != -1)
			{
				dummyList.add(listVectors.get(bot));
				listVectors.remove(bot);
			}
		}
		listVectors = dummyList;
	}
	public void pullVectors()
	{
		listVectors.clear();
		for (Shape i : listShapes)
		{
			
			for (ThreeXThree k : i.getMatrices())
			{
				listVectors.add(k);
			}
		}
	}
}
