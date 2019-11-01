import javax.imageio.ImageIO;
import javax.swing.*; 
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
public class Render extends Canvas {  
	
	//Display
	private BufferStrategy strategy;
	private BufferedImage[] spriteList;
	private JFrame fr = new JFrame();
	
	
	//Geometry
	private ArrayList<Shape> listShapes;
	private int spaceInstances;
	private ShapeBag sBag;
	
	private Shape smain; //current shape being transformed
	
	
	
	//toggle variables
	private boolean disp; //toggles vector display
	private boolean dispOrder; //toggles experimental drawing
	private boolean space;
	private int mode;
	private Random rand;
	private int fps;
	
	//input booleans
	private boolean[] inp; //uldr wasd qe
	
	public Render() {  
		smain = new Shape(-1); //dummy shape
		sBag = new ShapeBag();
		listShapes = sBag.getShapeList();
		spaceInstances = 0;
		fps = 0;
		mode = 1;
		disp = false;
		dispOrder = false;
		space = false;
		rand = new Random();
		inp = new boolean[18];
		JFrame frame =new JFrame();
		this.setSize(500,500);
		frame.add(this);
		frame.pack();
		frame.setVisible(true);//making the frame visible  
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIgnoreRepaint(true);
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		fr = frame;
		initSprite();
		addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				 if (e.getKeyCode() == KeyEvent.VK_F1) {
					mode = 1;
				 }
				 if (e.getKeyCode() == KeyEvent.VK_F2) {
					mode = 2;
				 }
				 if (e.getKeyCode() == KeyEvent.VK_F3) {
					mode = 3;
				 }
				 if (e.getKeyCode() == KeyEvent.VK_1) {
					smain = generateCube();
				 }
				 if (e.getKeyCode() == KeyEvent.VK_2) {
					smain = generatePyramid();
				 }
				 if (e.getKeyCode() == KeyEvent.VK_3) {
					smain = generateArwing();
				 }
				 if (e.getKeyCode() == KeyEvent.VK_4) {
					generateSpace();
				 }
				 if (e.getKeyCode() == KeyEvent.VK_5) {
						smain = generatePlane(30);
				 }
				 if (e.getKeyCode() == KeyEvent.VK_6) {
					 smain = generateBlock("cobble");
				 }
				 if (e.getKeyCode() == KeyEvent.VK_W) {
					 inp[4] = true;
				 }
				 if (e.getKeyCode() == KeyEvent.VK_A) {
					 inp[5] = true;
				 }
				 if (e.getKeyCode() == KeyEvent.VK_S) {
					 inp[6] = true;
				 }
				 if (e.getKeyCode() == KeyEvent.VK_D) {
					 inp[7] = true;
				 }
				 if (e.getKeyCode() == KeyEvent.VK_E) {
					 inp[9] = true;
				 }
				 if (e.getKeyCode() == KeyEvent.VK_Q) {
					 inp[8] = true;
				 }
				 if (e.getKeyCode() == KeyEvent.VK_I) {
					 inp[10] = true;
				 }
				 if (e.getKeyCode() == KeyEvent.VK_J) {
					 inp[11] = true;
				 }
				 if (e.getKeyCode() == KeyEvent.VK_K) {
					 inp[12] = true;
				 }
				 if (e.getKeyCode() == KeyEvent.VK_L) {
					 inp[13] = true;
				 }
				 if (e.getKeyCode() == KeyEvent.VK_U) {
					 inp[14] = true;
				 }
				 if (e.getKeyCode() == KeyEvent.VK_O) {
					 inp[15] = true;
				 }
				 if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					 inp[3] = true;
				 }
				 if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					 inp[1] = true;
				 }
				 if (e.getKeyCode() == KeyEvent.VK_UP) {
					 inp[0] = true;
				 }
				 if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					 inp[2] = true;
				 }
				 if (e.getKeyCode() == KeyEvent.VK_NUMPAD0) {
					 inp[16] = true;
				 }
				 if (e.getKeyCode() == KeyEvent.VK_NUMPAD1) {
					 inp[17] = true;
				 }
				 if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					 System.exit(0);
				 }
				 if (e.getKeyCode() == KeyEvent.VK_F9) {
					 smain.toggleWire();
				 }
				 if (e.getKeyCode() == KeyEvent.VK_F8) {
					 if (space)
					 {
						 space = false;
						 for (int i = 0; i < listShapes.size();) {
							 if (listShapes.get(i).getID() == 3)
								 listShapes.remove(i);
							 else
								 i++;
							 spaceInstances= 0;
						 }
					 }
					 else
					 {
						 space = true;
					 }
				 }
				 if (e.getKeyCode() == KeyEvent.VK_F11) {
					 if (disp)
					 {
						 disp = false;
					 }
					 else
					 {
						 disp = true;
					 }
				 }
				 if (e.getKeyCode() == KeyEvent.VK_F12) {
					 if (dispOrder)
					 {
						 dispOrder = false;
					 }
					 else
					 {
						 dispOrder = true;
					 }
				 }
				 if (e.getKeyCode() == KeyEvent.VK_OPEN_BRACKET) {
					 smain.scale(1.1);
				 }
				 if (e.getKeyCode() == KeyEvent.VK_CLOSE_BRACKET) {
					 smain.scale(0.9);
				 }
				 if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
					 if (listShapes.indexOf(smain) + 1 == listShapes.size())
					 {
						 smain = listShapes.get(0);
					 }
					 else if  (listShapes.indexOf(smain) != -1)
					 {
						 smain = listShapes.get(listShapes.indexOf(smain) + 1);
					 }
				 }
				 if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					 	if (!listShapes.isEmpty())
					 		listShapes.remove(listShapes.indexOf(smain));
						if (!listShapes.isEmpty())
							smain = listShapes.get(0);
						else
							smain = new Shape(-1);
				 }
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					 inp[0] = false;
				 }
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					 inp[3] = false;
				 }
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					 inp[1] = false;
				 }
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					 inp[2] = false;
				 }
				 if (e.getKeyCode() == KeyEvent.VK_W) {
					 inp[4] = false;
				 }
				 if (e.getKeyCode() == KeyEvent.VK_A) {
					 inp[5] = false;
				 }
				 if (e.getKeyCode() == KeyEvent.VK_S) {
					 inp[6] = false;
				 }
				 if (e.getKeyCode() == KeyEvent.VK_D) {
					 inp[7] = false;
				 }
				 if (e.getKeyCode() == KeyEvent.VK_E) {
					 inp[9] = false;
				 }
				 if (e.getKeyCode() == KeyEvent.VK_Q) {
					 inp[8] = false;
				 }
				 if (e.getKeyCode() == KeyEvent.VK_I) {
					 inp[10] = false;
				 }
				 if (e.getKeyCode() == KeyEvent.VK_J) {
					 inp[11] = false;
				 }
				 if (e.getKeyCode() == KeyEvent.VK_K) {
					 inp[12] = false;
				 }
				 if (e.getKeyCode() == KeyEvent.VK_L) {
					 inp[13] = false;
				 }
				 if (e.getKeyCode() == KeyEvent.VK_U) {
					 inp[14] = false;
				 }
				 if (e.getKeyCode() == KeyEvent.VK_O) {
					 inp[15] = false;
				 }
				 if (e.getKeyCode() == KeyEvent.VK_NUMPAD0) {
					 inp[16] = false;
				 }
				 if (e.getKeyCode() == KeyEvent.VK_NUMPAD1) {
					 inp[17] = false;
				 }
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}  
	public void loop()
	{	
		int frames = 0;
		long second = System.currentTimeMillis();
		long last = System.currentTimeMillis();
		while (true)
		{
			frames++;
			long delta = System.currentTimeMillis() - last;
			last = System.currentTimeMillis();
			//System.out.println(delta);
			Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());
			//generateSpace().rotateZ(rand.nextInt(360));
			
			
			update(delta);
			input();
			
			
			draw(g);
			g.dispose();
			strategy.show();
			if (last >= second + 1000)
			{
				fps = frames;
				frames = 0;
				second = System.currentTimeMillis();
			}
				
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void update(long delta)
	{
		if (space)
		{
			generateSpace();
		}
		for (int i = 0; i < listShapes.size(); i++)
		{
			listShapes.get(i).behave();
		}
	}
	public void initSprite()
	{
		spriteList = new BufferedImage[5];
		//spriteList[0] = Toolkit.getDefaultToolkit().getImage("resources/tex.png");
		//spriteList[1] = Toolkit.getDefaultToolkit().getImage("resources/mariocircuit.png");
		
		try {
			spriteList[0] = ImageIO.read(new File("resources/tex.png"));
			spriteList[1] = ImageIO.read(new File("resources/mariocircuit.png"));
			spriteList[2] = ImageIO.read(new File("resources/skyline.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public void input()
	{
		switch (mode)
		{
		case 1:
			if (inp[0]) //up
				smain.translate(0.0, 1.0);
			if (inp[3])
				smain.translate(1.0, 0.0);
			if (inp[1])
				smain.translate(-1.0, 0.0);
			if (inp[2])
				smain.translate(0.0, -1.0);
			if (inp[4])
				smain.rotateX(0.05);
			if (inp[5])
				smain.rotateY(-0.05);
			if (inp[6])
				smain.rotateX(-0.05);
			if (inp[7])
				smain.rotateY(0.05);
			if (inp[8])
				smain.rotateZ(-0.05);
			if (inp[9])
				smain.rotateZ(0.05);
			if (inp[10]) //i
				smain.rotXT(0.1);
			if (inp[11]) //j
				smain.rotYT(-0.1);
			if (inp[12]) //k
				smain.rotXT(-0.1);
			if (inp[13]) //l
				smain.rotYT(0.1);
			if (inp[14])
				smain.rotZT(-0.1);
			if (inp[15])
				smain.rotZT(0.1);
			if (inp[16])
				smain.translate(0, 0, 1);
			if (inp[17])
				smain.translate(0, 0, -1);
			break;
		case 2:
			break;
		case 3:
			break;
		}
		
	}
	public void draw(Graphics2D g)
	{
		g.setColor(Color.WHITE);
		g.drawString(Integer.toString(fps), 20, 20);
		
		double vert = 0;
		if (smain.getRotX() > 0)
		{
			vert = smain.getRotX() * 5;
		}
		else
		{
			vert = smain.getRotX() * 10;
		}
		AffineTransform at = new AffineTransform();
		at.translate(0,20);
		//AffineTransform at = AffineTransform.getTranslateInstance(100, 100 - vert * 5);
	//	at.rotate(smain.getRotZ(), spriteList[0].getWidth(this) / 2, spriteList[0].getHeight(this) / 2);
		//at.scale(1, -Math.abs(smain.getRotX() * .6) + 1);
		//System.out.println(smain.getRotX());
		//at.scale(2, 2);
		spriteList[3] = spriteList[1].getSubimage(850, 0, 172, 850);
	//	g.drawImage(spriteList[3], at, this);
	//	g.drawImage(spriteList[2], 0, 0, this);
		for (int i = 0; i < spriteList[3].getHeight(); i++)
		{
			
		}
		
		
		
		
		
		
		
		if (dispOrder)
		{
			
			g.drawImage(spriteList[0], 50, 20, 80, 50, this);
			for (int i = 0; i < listShapes.size(); i++)
			{
				int modifX = getWidth() / 2;
				int modifY = getHeight() / 2;
				

				Shape s = listShapes.get(i);
				
				g.setColor(Color.RED);
				for (int p = 0; p < s.getVectors().size(); p++)
				{
					//experimental vector rendering
					//g.drawLine(modifX + s.getTX(), modifY + s.getTY(), modifX + (int)(s.getVectors().get(p).getX()), modifY - (int)(s.getVectors().get(p).getY()));
					//vector rendering
					if (disp)
					g.drawLine((int)(250 + s.getTX()), (int)(250 - s.getTY()), 250 + (int)(s.getVectors().get(p).getX()), 250 - (int)(s.getVectors().get(p).getY()));
				}
				for (int k = 0; k < s.getMatrices().size(); k++)
				{
					ThreeXThree a = s.getMatrices().get(k);
					//draw Lines between matrices
					
					switch (a.getColor())
					{
						case 0:
							if (s == smain)
							{
								g.setColor(Color.GREEN);
							}
							else
							{
								g.setColor(Color.BLUE);
							}
							break;
						case 1:
							g.setColor(Color.PINK);
							break;
						case 2:
							g.setColor(Color.CYAN);
							break;
						case 3:
							g.setColor(Color.BLUE);
							break;
						case 4:
							g.setColor(Color.ORANGE);
							break;
						case 5:
							g.setColor(Color.YELLOW);
							break;
						case 6:
							g.setColor(Color.GREEN);
							break;
						case 7:
							g.setColor(Color.MAGENTA);
							break;
						case 8:
							g.setColor(Color.RED);
							break;
						case 9:
							g.setColor(Color.WHITE);
							break;
						case 10:
							g.setColor(Color.GRAY);
							break;
						default:
							break;
					}	
					g.drawLine(250 + (int)(a.getX1()), 250 - (int)(a.getY1()), 250 + (int)(a.getX1()), 250 - (int)(a.getY1()));

					g.drawLine(250 + (int)(a.getX1()), 250 - (int)(a.getY1()), 250 + (int)(a.getX2()), 250 - (int)(a.getY2()));

					g.drawLine(250 + (int)(a.getX1()), 250 - (int)(a.getY1()), 250 + (int)(a.getX3()), 250 - (int)(a.getY3()));
					int[] xs = {250 + (int)(a.getX1()), 250 + (int)a.getX2(), 250 + (int)a.getX3()};
					int[] ys = {250 - (int)a.getY1(), 250 - (int)a.getY2(), 250 - (int)a.getY3()};
					g.fillPolygon(xs, ys, 3);
		
				}

			}
		} else
		{
			int modifX = getWidth() / 2;
			int modifY = getHeight() / 2;
			
			for (int i = 0; i < listShapes.size(); i++)
			{
				//System.out.println(listShapes.get(i).getTZ());
				listShapes.get(i).applyRot();
				listShapes.get(i).scaleVisible(listShapes.get(i).getTZ() * 0.001 + 1.0);
				
			}
			sBag.arrange();
			ArrayList<ThreeXThree> polygons = sBag.getVectors();
			for (int i = 0; i < polygons.size(); i++)
			{
				colorSet(polygons.get(i), g);
				int[] xs = {250 + (int)(polygons.get(i).getX1()), 250 + (int)polygons.get(i).getX2(), 250 + (int)polygons.get(i).getX3()};
				int[] ys = {250 - (int)polygons.get(i).getY1(), 250 - (int)polygons.get(i).getY2(), 250 - (int)polygons.get(i).getY3()};
				
				if (polygons.get(i).getShape().getWire())
				{
					g.setColor(Color.WHITE);
					g.drawPolygon(xs, ys, 3);
				}
				else
				{
					g.fillPolygon(xs, ys, 3);
					
					if (disp)
					{
						g.setColor(Color.BLACK);
						g.drawPolygon(xs, ys, 3);
					}
				}
				
				
				
				
			}
			for (int i = 0; i < listShapes.size(); i++)
			{
				//System.out.println(listShapes.get(i).getTZ());
				listShapes.get(i).scaleRestore(listShapes.get(i).getTZ() * 0.001 + 1.0);
				listShapes.get(i).deplyRot();
			}
		}
		
		
	}
		
	//INDIVIDUAL VECTOR MATRICES
	public void colorSet(ThreeXThree a, Graphics2D g)
	{
		switch (a.getColor())
		{
			case 0:
				g.setColor(Color.GREEN);
				break;
			case 1:
				g.setColor(Color.PINK);
				break;
			case 2:
				g.setColor(Color.CYAN);
				break;
			case 3:
				g.setColor(Color.BLUE);
				break;
			case 4:
				g.setColor(Color.ORANGE);
				break;
			case 5:
				g.setColor(Color.YELLOW);
				break;
			case 6:
				g.setColor(Color.GREEN);
				break;
			case 7:
				g.setColor(Color.MAGENTA);
				break;
			case 8:
				g.setColor(Color.RED);
				break;
			case 9:
				g.setColor(Color.WHITE);
				break;
			case 10:
				g.setColor(Color.LIGHT_GRAY);
				break;
			case -1:
				g.setColor(a.colorO);
			default:
				break;
		}
	}
	
	public Shape generateCube()
	{
		Shape s = new Shape(0);
		//old vector arrangements
		Vector3D vect = new Vector3D(50, 50, 50);
		Vector3D vect2 = new Vector3D(50, -50, 50);
		Vector3D vect3 = new Vector3D(-50, -50, 50);
		Vector3D vect4 = new Vector3D(-50, 50, 50);
		Vector3D vect5 = new Vector3D(50, 50, -50);	
		Vector3D vect6 = new Vector3D(50, -50, -50);
		Vector3D vect7 = new Vector3D(-50, -50, -50);
		Vector3D vect8 = new Vector3D(-50, 50, -50);
		

		s.addVector(vect);
		s.addVector(vect2);
		s.addVector(vect3);
		s.addVector(vect4);
		s.addVector(vect5);
		s.addVector(vect6);
		s.addVector(vect7);
		s.addVector(vect8);
		
		s.addMatrix(1, 4, 2);
		s.addMatrix(3, 4, 2);
		s.addMatrix(1, 5, 6, 9);
		s.addMatrix(2, 1, 6, 9);
		s.addMatrix(7, 3, 8, 7);
		s.addMatrix(4, 8, 3, 7);
		s.addMatrix(7, 8, 6, 4);
		s.addMatrix(5, 8, 6, 4);

		listShapes.add(s);
		return s;
	}
	
	public Shape generateBlock(String block)
	{
		Shape s = new Shape(51);
		BufferedImage sprite = null;
		try {
			sprite = ImageIO.read(new File("resources/" + block + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int[] allRGB = sprite.getRGB(0, 0, sprite.getWidth(),sprite.getHeight(), null, 0, sprite.getWidth());
		int temp = 80;
		for (int w = 0; w < 6; w++)
		{
			for (int i = -8; i < 8; i++)
			{
				for (int k = -8; k < 8; k++)
				{
					Vector3D vect = new Vector3D(i * 10, k * 10, temp);
					Vector3D vect2 = new Vector3D(i * 10 + 10, k * 10, temp);
					Vector3D vect3 = new Vector3D(i * 10, k * 10 + 10, temp);
					s.addVector(vect); s.addVector(vect2); s.addVector(vect3);
					Vector3D vect4 = new Vector3D(i * 10 + 10, k * 10 + 10, temp);
					s.addVector(vect4);
				}
			}
			if (w < 3)
				s.rotateYO(30);
			else if (w == 3)
				s.rotateXO(30);
			else
				s.rotateXO(60);
		}
		for (int i = 1; i < s.numberOfVectors(); i += 4)
		{
			Color c = new Color(allRGB[(i / 4) % 256]);
			s.addMatrix(i, i + 1, i + 2, c);
			s.addMatrix(i + 3, i + 1, i + 2, c);
		}
		listShapes.add(s);
		return s;
		
	}
	public Shape generateSquare()
	{
		Shape s = new Shape(99);
		//old vector arrangements
		Vector3D vect = new Vector3D(50, 50, 50);
		Vector3D vect2 = new Vector3D(50, -50, 50);
		Vector3D vect4 = new Vector3D(-50, 50, 50);
		Vector3D vect5 = new Vector3D(50, 50, -50);
		
		Vector3D vect8 = new Vector3D(-50, 50, -50);
		//Vector3D vect4 = new Vector3D(-50, 50, 50);
		//Vector3D vect5 = new Vector3D(50, 50, -50);
		Vector3D vect7 = new Vector3D(-50, -50, -50);
		
		Vector3D vect3 = new Vector3D(-50, -50, 50);
		//Vector3D vect2 = new Vector3D(50, -50, 50);
		//Vector3D vect7 = new Vector3D(-50, -50, -50);
		//Vector3D vect4 = new Vector3D(-50, 50, 50);
		
		Vector3D vect6 = new Vector3D(50, -50, -50);
		//Vector3D vect2 = new Vector3D(50, -50, 50);
		//Vector3D vect7 = new Vector3D(-50, -50, -50);
		//Vector3D vect5 = new Vector3D(50, 50, -50);
		s.addVector(vect);
		s.addVector(vect2);
		s.addVector(vect3);
		s.addVector(vect4);
		s.addVector(vect5);
		s.addVector(vect6);
		s.addVector(vect7);
		s.addVector(vect8);
		
		s.addMatrix(8, 4, 5);
		s.addMatrix(1, 5, 4);
		s.addMatrix(3, 4, 7);
		s.addMatrix(7, 8, 6);
		s.addMatrix(6, 5, 2);
		s.addMatrix(2, 1, 3);
		listShapes.add(s);
		return s;
	}
	public Shape generatePyramid()
	{
		Shape s = new Shape(1);
		
		Vector3D vect = new Vector3D(-50, 0, 50);
		Vector3D vect2 = new Vector3D(50, 0, 50);
		Vector3D vect3 = new Vector3D(50, 0, -50);
		Vector3D vect4 = new Vector3D(-50, 0, -50);
		Vector3D vect5 = new Vector3D(0, 50, 0);
		
		
		
		
		
		
		
		s.addVector(vect);
		s.addVector(vect2);
		s.addVector(vect3);
		s.addVector(vect4);
		s.addVector(vect5);
		s.addMatrix(1, 2, 4);
		s.addMatrix(3, 2, 4);
		s.addMatrix(5, 3, 2, 8);
		s.addMatrix(5, 1, 2, 3);
		s.addMatrix(5, 1, 4, 8);
		s.addMatrix(5, 4, 3, 3);
		listShapes.add(s);
		return s;
	}
	public Shape generateArwing()
	{
		Shape s = new Shape(2);
		
		//main ship chunk
		Vector3D vect1 = new Vector3D(0,-13,0); //bottom
		Vector3D vect2 = new Vector3D(24,0,0); //right
		Vector3D vect3 = new Vector3D(-24,0,0); //left
		Vector3D vect4 = new Vector3D(0,0,90); //tip
		Vector3D vect5 = new Vector3D(0,21,0); //top
		Vector3D vect6 = new Vector3D(0,12,-30); //back tip
		s.addVector(vect1);
		s.addVector(vect2);
		s.addVector(vect3);
		s.addVector(vect4);
		s.addVector(vect5);
		s.addVector(vect6);
		

		s.addMatrix(6, 2, 3, 4);
		s.addMatrix(1, 2, 3, 10);
		s.addMatrix(6, 2, 5, 10);
		s.addMatrix(6, 3, 5, 10);
		s.addMatrix(4, 5, 2, 10);
		s.addMatrix(4, 5, 3, 10);
		s.addMatrix(4, 1, 2, 10);
		s.addMatrix(4, 1, 3, 10);

		
		//left wing
		s.addVector(new Vector3D(-48, 0, 20)); //north - 7
		s.addVector(new Vector3D(-119, 0, -39)); //tip - 8
		s.addVector(new Vector3D(-64, 6, -6)); //top - 9
		s.addVector(new Vector3D(-64, -6, -6)); //bottom - 10
		//top polys
			s.addMatrix(9, 7, 3, 10);
			s.addMatrix(9, 8, 3, 10);
			s.addMatrix(9, 8, 7, 10);
			//bottom polys
			s.addMatrix(10, 7, 3, 10);
			s.addMatrix(10, 8, 3, 10);
			s.addMatrix(10, 8, 7, 10);

		//right wing
		s.addVector(new Vector3D(48, 0, 20)); //north -11
		s.addVector(new Vector3D(119, 0, -39)); //tip -12
		s.addVector(new Vector3D(64, 6, -6)); //top -13
		s.addVector(new Vector3D(64, -6, -6)); //bottom -14
		
			//top polys
			s.addMatrix(13, 2, 11, 10);
			s.addMatrix(13, 2, 12, 10);
			s.addMatrix(13, 12, 11, 10);
			//bottom polys
			s.addMatrix(14, 2, 11, 10);
			s.addMatrix(14, 2, 12, 10);
			s.addMatrix(14, 12, 11, 10);
		
		//left thingy
		s.addVector(new Vector3D(-24,10,0)); //top vector 15
		s.addVector(new Vector3D(-24,0,-10)); //bottom 16
		s.addVector(new Vector3D(-27,18,-50)); //tip 17
		s.addVector(new Vector3D(-26,3,-7)); //left 18
		s.addVector(new Vector3D(-22,3,-7)); //right 19
			//left shell
			s.addMatrix(18, 3, 16, 3);
			s.addMatrix(18, 3, 15, 3);
			s.addMatrix(18, 17, 15, 3);
			s.addMatrix(18, 17, 16, 3);
			//right
			s.addMatrix(19, 3, 16, 3);
			s.addMatrix(19, 3, 15, 3);
			s.addMatrix(19, 17, 15,3);
			s.addMatrix(19, 17, 16, 3);
		

		
		//right thingy
		s.addVector(new Vector3D(24,10,0)); //Vector 20
		s.addVector(new Vector3D(24,0,-10)); //bottom 21
		s.addVector(new Vector3D(27,18,-50)); //tip 22
		s.addVector(new Vector3D(26,3,-7)); //right 23
		s.addVector(new Vector3D(22,3,-7)); //left 24
			//right shell
			s.addMatrix(23, 2, 21, 3);
			s.addMatrix(23, 2, 20, 3);
			s.addMatrix(23, 22, 20, 3);
			s.addMatrix(23, 22, 21, 3);
			//left
			s.addMatrix(24, 2, 21, 3);
			s.addMatrix(24, 2, 20, 3);
			s.addMatrix(24, 22, 20, 3);
			s.addMatrix(24, 22, 21, 3);
			
		//left blaster
		s.addVector(new Vector3D(-24, 0, 7)); //top Vector 25
		s.addVector(new Vector3D(-24, -6, 7)); // back 26
		s.addVector(new Vector3D(-24, -6, 17)); //front 27
		s.addVector(new Vector3D(-27, -2, 7)); //left 28
		s.addVector(new Vector3D(-23, -2, 7)); //right Vector 29
			
			//left shell
			s.addMatrix(28, 3, 25, 3);
			s.addMatrix(28, 3, 26, 3);
			s.addMatrix(28, 27, 25, 3);
			s.addMatrix(28, 27, 26, 3);
			//right
			s.addMatrix(29, 3, 25, 3);
			s.addMatrix(29, 3, 26, 3);
			s.addMatrix(29, 27, 25, 3);
			s.addMatrix(29, 27, 26, 3);
		
		//right blaster
		s.addVector(new Vector3D(24, 0, 7)); // top Vector 30
		s.addVector(new Vector3D(24, -6, 7)); //back 31
		s.addVector(new Vector3D(24, -6, 17)); //front 32
		s.addVector(new Vector3D(27, -2, 7)); //right 33
		s.addVector(new Vector3D(23, -2, 7)); //left 34
			//right shell
			s.addMatrix(33, 2, 30, 3);
			s.addMatrix(33, 2, 31, 3);
			s.addMatrix(33, 32, 30, 3);
			s.addMatrix(33, 32, 31, 3);
			//left
			s.addMatrix(34, 2, 30, 3);
			s.addMatrix(34, 2, 31, 3);
			s.addMatrix(34, 32, 30, 3);
			s.addMatrix(34, 32, 31, 3);

		listShapes.add(s);
		return s;
	}
	public Shape generateSpace()
	{
		Shape s = new Shape(3);
		s.addVector(new Vector3D(0,0,-500));
		s.addVector(new Vector3D(0, rand.nextInt(30) + 20, -500));
		s.addMatrix(1, 2, 2);
		s.toggleWire();
		s.setRotZO(rand.nextInt(360));
		listShapes.add(s);
		spaceInstances++;
		
		if (spaceInstances >= 150)
		{

			for (int i = 0; i < listShapes.size(); i++)
			{
				if (listShapes.get(i).getID() == 3)
				{
					listShapes.remove(i);
					i = listShapes.size();
					spaceInstances--;
				}
				
			}
		}
		return s;
	}
	public Shape generatePlane(int size)
	{
		int heightv = 15;
		int vectorC = 0;
		Shape s = new Shape(4);
		for (int i = 0; i <= size; i++)
		{
			s.addVector(new Vector3D(-5 * size + (i * 10), -5 * size, rand.nextInt(heightv)));
			vectorC++;
			for (int k = 0; k < size; k++)
			{
				s.addVector(new Vector3D(-5 * size + (i * 10), -5 * size + ((k + 1) * 10), rand.nextInt(heightv)));
				vectorC++;
			}
		}

		for (int k = 0; k < size; k++)
		{
			for (int i = size * k + k + 1; i <= size * (k+1) + k; i++)
			{
				s.addMatrix(i, i+1, i+size+1, rand.nextInt(10));
				s.addMatrix(i+1, i+size+1, i+size+2, rand.nextInt(10));
			}
		}
		
		

		listShapes.add(s);
		return s;
	}
	//finds the diagonal of the parlalthingy for three 3D vectors
	public Vector3D diagonal3D(ThreeXThree mat)
	{
		double x = mat.getX1() + mat.getX2() + mat.getX3();
		double y = mat.getY1() + mat.getY2() + mat.getY3();
		double z = mat.getZ1() + mat.getZ2() + mat.getZ3();
		return new Vector3D(x, y, z);
	}
}