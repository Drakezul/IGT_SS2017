package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * https://stackoverflow.com/a/299555
 * 
 * @author bcash et al.
 *
 */
public class ImagePanel extends JPanel {

	public ImagePanel() {
		super(new BorderLayout());
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2024440443499827632L;
	private BufferedImage image;

	public ImagePanel(String imagePath) {
		try {
			image = ImageIO.read(new File(imagePath));
		} catch (IOException ex) {
			// handle exception...
		}
	}

	public Dimension getPreferredSize() {
		if (image != null) {
			return new Dimension(image.getWidth(), image.getHeight());
		} else {
			return new Dimension(500, 500);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(image != null){
			int xPos = ((int)this.getSize().getWidth() / 2) - (image.getWidth() / 2);
			if(xPos < 0){
				xPos = 0;
			}
			int yPos = ((int)this.getSize().getHeight() / 2) - (image.getHeight() / 2);
			if(yPos < 0){
				yPos = 0;
			}
			g.drawImage(image, xPos, yPos, this);	
		}		
	}

}