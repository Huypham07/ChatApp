package Chat.swing;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class Background extends JPanel {

    public Background() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        int width = getWidth();
        int height = getHeight();
        g2.setPaint(new GradientPaint(0, 0, new Color(17, 86, 156), width, 0, new Color(0, 153, 153)));
        
        g2.fillRect(0, 0, width, height);
        g2.dispose();
        super.paintComponent(grphcs);
    }
}
