import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener{
	static final int Screen_Width = 600;
	static final int Screen_Height = 600;
	static final int Unit_size = 25;
	static final int Game_units = (Screen_Width * Screen_Height)/Unit_size;
	int Delay = 100;
	final int x[]= new int[Game_units];
	final int y[]= new int[Game_units];
	int bodyParts = 6;
	int applesEaten;
	int appleX;
	int appleY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(Screen_Width,Screen_Height));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	public void startGame() {
		newApple();
		running = true;
		timer = new Timer(Delay,this);
		timer.start();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
		
	}
	public void draw(Graphics g) {
		
		if(running) {
//			for (int i =0;i < Screen_Height / Unit_size;i++) {
//				g.drawLine(i*Unit_size, 0, i*Unit_size, Screen_Height);
//				g.drawLine(0, i*Unit_size, Screen_Width , i*Unit_size);
//			}
			g.setColor(Color.RED);
			g.fillOval(appleX, appleY, Unit_size, Unit_size);
			for(int i=0;i<bodyParts;i++) {
				if(i==0) {
					g.setColor(Color.GRAY);
					g.fillRect(x[i], y[i], Unit_size, Unit_size);
				}
				else {
					g.setColor(new Color(random.nextInt(225),random.nextInt(225),random.nextInt(225)));
					g.fillRect(x[i], y[i], Unit_size, Unit_size);
				}
			}
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free",Font.BOLD,75));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: "+applesEaten, (Screen_Width - metrics.stringWidth("Score: "+applesEaten))/2,g.getFont().getSize());
	   }
		else {
			gameOver(g);
		}
		
	}
	public void newApple() {
		appleX = random.nextInt((int)(Screen_Width/Unit_size))*Unit_size;
		appleY = random.nextInt((int)(Screen_Width/Unit_size))*Unit_size;
	}
	public void move() {
		for(int i=bodyParts;i>0;i--) {
			x[i]=x[i-1];
			y[i]=y[i-1];
		}
		switch (direction) {
		case 'U':
			y[0]=y[0]-Unit_size;
			break;
		case 'D':
			y[0]=y[0]+Unit_size;
			break;
		case 'L':
			x[0]=x[0]-Unit_size;
			break;
		case 'R':
			x[0]=x[0]+Unit_size;
			break;
		}
	}
	public void checkApple() {
		if(x[0]==appleX && y[0]==appleY) {
			bodyParts++;
			applesEaten++;
			Delay=Delay-5;
			newApple();
		}
		
	}
	public void checkCollisions() {
		// if heads collides the body
		for(int i=bodyParts;i>0;i--) {
			if(x[0]==x[i] && y[0]==y[i]) {
				running = false;
			}
		}
		//if head touches left border
		if(x[0]<0) {
			running = false;
		}
		//if head touches right border
		if(x[0]>Screen_Width) {
			running = false;
		}
		//if head touches bottom border
		if(y[0]<0) {
			running = false;
		}
		//if head touches top border
		if(y[0]>Screen_Height) {
			running = false;
		}
		if(!running) {
			timer.stop();
		}
		
	}
	public void gameOver(Graphics g) {
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free",Font.BOLD,75));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (Screen_Width - metrics1.stringWidth("Game Over"))/2, Screen_Height/2);
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free",Font.BOLD,75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Score: "+applesEaten, (Screen_Width - metrics2.stringWidth("Score: "+applesEaten))/2,g.getFont().getSize());
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
		
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction!='R') {
					direction='L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction !='L') {
					direction ='R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction !='D') {
					direction ='U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction !='U') {
					direction ='D';
				}
				break;
			}
		}
	}
	
}