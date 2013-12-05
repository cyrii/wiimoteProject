/* 
 * Original code by NomNom 
 * 
 * @author Christopher Gorman, Harshil Patel, and Andrew Weiss
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;


public class paintModified {
	
	 
	public static File fileName;
	public static void main(String[] args){
				
		//Creates a frame with a title of "Wiimote Smartboard"
		JFrame frame = new JFrame("Wiimote Smartboard");
		
		// Sets the window as maximized
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		
		// Creates a new container
		Container content = frame.getContentPane();
		
		// Set the layout
		content.setLayout(new BorderLayout());
		
		// Creates the PadDraw object
		final PadDrawer drawPad = new PadDrawer();
		content.add(drawPad, BorderLayout.CENTER);
		
		// Creates the panel that will hold the buttons and sets its size
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(64, 68));
		panel.setMinimumSize(new Dimension(64, 68));
		panel.setMaximumSize(new Dimension(64, 68));
		
		// Creates the clear button
		JButton clearButton = new JButton("Clear All");
		clearButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				drawPad.clear();
			}
		});
		
		// Creates the eraser button
		JButton eraserButton = new JButton("Eraser");
		eraserButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				drawPad.eraser();
			}
		});
		
		// Creates the red button
		JButton redButton = new JButton();
		redButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				drawPad.red();
			}

		});
		
		// Creates the black button
		JButton blackButton = new JButton();
		blackButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				drawPad.black();
			}
		});
		
		// Creates the magenta button
		JButton magentaButton = new JButton();
		magentaButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				drawPad.magenta();
			}
		});
		
		// Creates the blue button
		JButton blueButton = new JButton();
		blueButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				drawPad.blue();
			}
		});
		
		// Creates the green button
		JButton greenButton = new JButton();
		greenButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				drawPad.green();
			}
		});
		
		// Creates the orange button
		JButton orangeButton = new JButton();
		orangeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				drawPad.orange();
			}
		});
		
		// Creates the gray button
		JButton grayButton = new JButton();
		grayButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				drawPad.gray();
			}
		});
		
		 // Creates the button to select directory to save the file
        JButton btnScreencap = new JButton("Folder");
  		btnScreencap.addActionListener(new ActionListener() {
  			public void actionPerformed(ActionEvent arg0) {
  				// Runs the main method of ScreenCap class
  				try {
  				//ScreenCap.main(null);
  				JFileChooser chooser = new JFileChooser(  );  // file chooser class
  			      chooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY ); // just select a direction or location with file name
  			      chooser.showSaveDialog(null);
  			      
  			      fileName = chooser.getSelectedFile();
  				} catch (Exception e) {
  					// TODO Auto-generated catch block
  					e.printStackTrace();
  				}
  			}
  		});
  		JButton btnScreencap2 = new JButton("Save All");
  		btnScreencap2.addActionListener(new ActionListener() {
  			public void actionPerformed(ActionEvent arg0) {
  				// Runs the main method of ScreenCap class
  				try {
  				   Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
  				   Rectangle screenRectangle = new Rectangle(screenSize);
  				   Robot robot = new Robot();
  				   BufferedImage image = robot.createScreenCapture(screenRectangle);
  					ImageIO.write(image, "PNG", fileName);
            
  				} catch (Exception e) {
  					// TODO Auto-generated catch block
  					e.printStackTrace();
  				}
  			}
  		});
  		

  		
  		// Sets the button sizes and tile colors
		blackButton.setPreferredSize(new Dimension(64, 64));
		blackButton.setBackground(Color.BLACK);
		magentaButton.setPreferredSize(new Dimension(64, 64));
		magentaButton.setBackground(Color.MAGENTA);
		redButton.setPreferredSize(new Dimension(64, 64));
		redButton.setBackground(Color.RED);
		blueButton.setPreferredSize(new Dimension(64, 64));
		blueButton.setBackground(Color.BLUE);
		greenButton.setPreferredSize(new Dimension(64,64));
		greenButton.setBackground(Color.GREEN);
		orangeButton.setPreferredSize(new Dimension(64,64));
		orangeButton.setBackground(Color.ORANGE);
		grayButton.setPreferredSize(new Dimension(64,64));
		grayButton.setBackground(Color.GRAY);
		
		// Adds the buttons to the JPanel
		panel.add(greenButton);
		panel.add(blueButton);
		panel.add(magentaButton);
		panel.add(blackButton);
		panel.add(redButton);
		panel.add(orangeButton);
		panel.add(grayButton);
		panel.add(eraserButton);
		panel.add(clearButton);
		panel.add(btnScreencap);
		panel.add(btnScreencap2);

		
		// Sets the JPanel on the left (West) side
		content.add(panel, BorderLayout.WEST);
		
		// Made frame size very large so entire screen is able to be drawn on
		frame.setSize(2000, 2000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
}



@SuppressWarnings("serial")
class PadDrawer extends JComponent{

	// The canvas
	Image image;
	
	// The pen
	Graphics2D graphics2D;
	
	// The mouse coordinates
	int currentX, currentY, oldX, oldY;
	
	// PadDraw constructor
	public PadDrawer(){
		setDoubleBuffered(false);
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				oldX = e.getX();
				oldY = e.getY();
			}
		});
		
		// Waits for the mouse to be dragged to begin painting
		addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseDragged(MouseEvent e){
				currentX = e.getX();
				currentY = e.getY();
				if(graphics2D != null)
				graphics2D.drawLine(oldX, oldY, currentX, currentY);
				repaint();
				oldX = currentX;
				oldY = currentY;
			}

		});
		
	}

	// Painting method that allows free drawing
	public void paintComponent(Graphics g){
		
		if(image == null){
			image = createImage(getSize().width, getSize().height);
			graphics2D = (Graphics2D)image.getGraphics();
			graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			clear();
		}
		g.drawImage(image, 0, 0, null);
	}

	// Clear method fills the screen with white
	public void clear(){
		graphics2D.setPaint(Color.white);
		graphics2D.fillRect(0, 0, getSize().width, getSize().height);
		graphics2D.setPaint(Color.black);
		repaint();
	}

	// Sets the pen as red
	public void red(){
		graphics2D.setPaint(Color.red);
		graphics2D.setStroke(new BasicStroke(3));
		repaint();
	}
	
	// Sets the pen as black
	public void black(){
		graphics2D.setPaint(Color.black);
		graphics2D.setStroke(new BasicStroke(3));
		repaint();
	}
	
	// Sets the pen as magenta
	public void magenta(){
		graphics2D.setPaint(Color.magenta);
		graphics2D.setStroke(new BasicStroke(3));
		repaint();
	}
	
	// Sets the pen as blue
	public void blue(){
		graphics2D.setPaint(Color.blue);
		graphics2D.setStroke(new BasicStroke(3));
		repaint();
	}
	
	// Sets the pen as green
	public void green(){
		graphics2D.setPaint(Color.green);
		graphics2D.setStroke(new BasicStroke(3));
		repaint();
		
	}
	
	// Sets the pen as orange
	public void orange(){
		graphics2D.setPaint(Color.orange);
		repaint();
	}
	
	// Sets the pen as gray
	public void gray(){
		graphics2D.setPaint(Color.gray);
		repaint();
	}
	
	// Sets the pen as white (eraser)
	public void eraser(){
		graphics2D.setPaint(Color.white);
		graphics2D.setStroke(new BasicStroke(8));
		repaint();
	}
	

}

