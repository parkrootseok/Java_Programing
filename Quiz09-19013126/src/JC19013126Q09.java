import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;

class MyLabel extends JLabel {
	
	private int barSize = 0;
	private int maxBarSize;
	
	public MyLabel(int maxBarSize) {
		this.maxBarSize = maxBarSize;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.MAGENTA);
		
		int width = (int) (((double)(this.getWidth())) / maxBarSize * barSize);
		if(width == 0) return;
		g.fillRect(0, 0, width, this.getHeight());
	}
	
	synchronized public void fill() {
		if (barSize == maxBarSize) {
			try {
				wait();
			} catch (InterruptedException e) { return; }
		}
		
		barSize++;
		repaint();
		notify();
	}
	
	synchronized public void consume() {
		if(barSize == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				return ;
			}
		}
		
		barSize--;
		repaint();
		notify();
	}
}

class ConsumerThread implements Runnable {
	
	private MyLabel bar;
	
	public ConsumerThread(MyLabel bar) {
		this.bar = bar;
	}

	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(200);
				bar.consume();
			} catch(InterruptedException e) {
				return ;
			}
		}
		
	}
	
}

public class JC19013126Q09 extends JFrame {
	
	private MyLabel bar = new MyLabel(100);
	
	public JC19013126Q09(String title) {
		super(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container c = getContentPane();
		c.setLayout(null);
		bar.setBackground(Color.ORANGE);
		bar.setOpaque(true);
		bar.setLocation(20, 50);
		bar.setSize(300, 20);
		c.add(bar);
		
		c.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				bar.fill();
			}
		});
		
		setSize(350, 200);
		setVisible(true);
		
		c.setFocusable(true);
		c.requestFocus();
		ConsumerThread runnable = new ConsumerThread(bar);
		Thread th = new Thread(runnable);
		th.start();
	}
	
	public static void main(String[] args) {
		new JC19013126Q09("아무키나 빨리 눌러 바 채우기");
	}

}
