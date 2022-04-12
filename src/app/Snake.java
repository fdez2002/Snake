package app;

import javax.swing.*;
import javax.swing.JFrame; // Crea una ventana
import java.awt.event.*; // Importa los eventos
import java.util.ArrayList;
import java.util.Random;
import java.awt.Toolkit; // Nos da el tamaño de las ventanas
import java.awt.Dimension;
import java.awt.Point; //Obtenemos el punto en el pplano
import java.awt.Color;
import java.awt.Graphics;

public class Snake extends JFrame {

		int width = 640;
		int height = 480;
		
		Point snake;
		Point comida;
		
		boolean gameOver = false;
		
		int widhtPoint = 10, heightPoint =10;
		
		ImagenSnake imagenSnake;

		ArrayList<Point> lista = new ArrayList<Point>();
		
		long frecuencia = 60; 
		
		int direccion = KeyEvent.VK_LEFT;
		
		public  Snake() {
			setTitle("Snake");
			
			setSize(width, height);
			
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			this.setLocation(dim.width/2-width/2, dim.height/2-height/2);//Obtenemos la pantalla en el centro
			
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Cuando cierra la ventana sale de la aplicacion
			
			Teclas teclas = new Teclas();
			this.addKeyListener(teclas);
			
			startGame();
			
			imagenSnake = new ImagenSnake();
			this.getContentPane().add(imagenSnake);
			
			setVisible(true);
			
			Momento momento = new Momento();
			Thread trid = new Thread(momento);
			trid.start();
}
public void crearComida() {
	Random rnd = new Random();
	
	comida.x = (rnd.nextInt(width)) + 5;
	if((comida.x % 5) > 0) {
		comida.x = comida.x - (comida.x % 5);
	}

	if(comida.x < 5) {
		comida.x = comida.x + 10;
	}
	if(comida.x > width) {
		comida.x = comida.x - 10;
	}

	comida.y = (rnd.nextInt(height)) + 5;
	if((comida.y % 5) > 0) {
		comida.y = comida.y - (comida.y % 5);
	}	

	if(comida.y > height) {
		comida.y = comida.y - 10;
	}
	if(comida.y < 0) {
		comida.y = comida.y + 10;
	}
	
}
public void startGame() {
	comida = new Point(200, 200);
	snake = new Point(width/2, height/2);
	
	lista = new ArrayList<Point>();
	lista.add(snake);
	
	crearComida();

}
		
/**
 * 
 * 
 *
 */
		
public class Teclas extends KeyAdapter{ //Evalua los eventos de las teclas
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}else if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (direccion != KeyEvent.VK_DOWN) {
				direccion = KeyEvent.VK_UP;
			}
			
		}else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (direccion != KeyEvent.VK_UP) {
				direccion = KeyEvent.VK_DOWN;
			}
			
		}else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (direccion != KeyEvent.VK_RIGHT) {
				direccion = KeyEvent.VK_LEFT;
			}
			
			
		}else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (direccion != KeyEvent.VK_LEFT) {
				direccion = KeyEvent.VK_RIGHT;
			}
			
		}
	}
}
public void actualizar() {
	imagenSnake.repaint();
	
	lista.add(0, new Point (snake.x, snake.y));
	lista.remove(lista.size()-1);
	
	for(int i=1; i<lista.size(); i++) {
		Point p = lista.get(i);
		
		if(snake.x == p.x && snake.y == p.y) {
			gameOver = true;
		}
	}
	
	if((snake.x > (comida.x -10)) && (snake.x < (comida.x +10)) && (snake.y > (comida.y -10)) && (snake.y > (comida.y -10))){
		lista.add(0, new Point (snake.x, snake.y));
		crearComida();
	}
}

public class ImagenSnake extends JPanel{
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(new Color(0,0,255));
		g.fillRect(snake.x, snake.y, widhtPoint, heightPoint);
		
		for(int i=0; i<lista.size(); i++) {
			Point p = (Point)lista.get(i);
			g.fillRect(p.x, p.y, widhtPoint, heightPoint);
		}
		
		g.setColor(new Color(255, 0, 0));
		g.fillRect(comida.x, comida.y, widhtPoint, heightPoint);
		
		if(gameOver) {
			g.drawString("GAME OVER", 200, 320);
		}
		

	}
}
public class Momento extends Thread{
	long last = 0;
	public void run() {
		while(true) {
			if (java.lang.System.currentTimeMillis() - last > frecuencia) {
				if(!gameOver) {
					if (direccion == KeyEvent.VK_UP) {
						snake.y = snake.y - heightPoint;
						
						if (snake.y < 0) {
							snake.y = height - heightPoint;
						}
						
						if(snake.y > height) {
							snake.y = 0;
						}
					}else if(direccion == KeyEvent.VK_DOWN) {
						snake.y = snake.y + heightPoint;
						
						if (snake.y < 0) {
							snake.y = height - heightPoint;
						}
						
						if(snake.y > height) {
							snake.y = 0;
						}
					}else if(direccion == KeyEvent.VK_LEFT) {
						snake.x = snake.x - widhtPoint;
						
						if (snake.x < 0) {
							snake.x = width - widhtPoint;
						}
						
						if(snake.x > width) {
							snake.y = 0;
						}
					}else if(direccion == KeyEvent.VK_RIGHT) {
						snake.x = snake.x + widhtPoint;
						
						if (snake.x < 0) {
							snake.x = width - widhtPoint;
						}
						
						if(snake.x > width) {
							snake.y = 0;
						}
					}
					
				}
				
				actualizar();
				last = java.lang.System.currentTimeMillis();
			}
		}
	}
}
/**
* 
* @param args
*/
public static void main(String[] args) {
		// TODO Auto-generated method stub
		Snake juego = new Snake();
	}

}
