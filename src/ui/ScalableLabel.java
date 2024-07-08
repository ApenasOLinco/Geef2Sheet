package ui;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import main.App;

class ScalableLabel extends JLabel {
	public ScalableLabel(ImageIcon imageIcon) {
		super(imageIcon);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		ImageIcon icon = (ImageIcon) getIcon();
		if (icon != null)
			ImageUtil.drawImageScaledToCanvasRatio(icon.getImage(), this, g);
		App.getAppWindow().queueRepaint(this);
	}
}