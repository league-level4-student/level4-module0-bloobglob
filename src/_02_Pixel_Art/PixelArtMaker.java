package _02_Pixel_Art;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PixelArtMaker implements MouseListener, ActionListener{
	private JFrame window;
	private GridInputPanel gip;
	private GridPanel gp;
	ColorSelectionPanel csp;
	JButton save = new JButton("Save");
	JButton load = new JButton("Load");
	
	public void start() {
		save.addActionListener(this);
		load.addActionListener(this);
		gip = new GridInputPanel(this);	
		window = new JFrame("Pixel Art");
		window.setLayout(new FlowLayout());
		window.setResizable(false);
		
		window.add(gip);
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}

	public void submitGridData(int w, int h, int r, int c) {
		gp = new GridPanel(w, h, r, c);
		csp = new ColorSelectionPanel();
		csp.add(save);
		csp.add(load);
		window.remove(gip);
		window.add(gp);
		window.add(csp);
		gp.repaint();
		gp.addMouseListener(this);
		window.pack();
	}
	
	public static void main(String[] args) {
		new PixelArtMaker().start();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		gp.setColor(csp.getSelectedColor());
		System.out.println(csp.getSelectedColor());
		gp.clickPixel(e.getX(), e.getY());
		gp.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==save) {
			save();
		}else if(e.getSource() == load) {
			load();
		}
	}
	private void save() {
		try (FileOutputStream fos = new FileOutputStream(new File("src/_02_Pixel_Art/save.dat")); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(gp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void load() {
		try (FileInputStream fis = new FileInputStream(new File("src/_02_Pixel_Art/save.dat")); ObjectInputStream ois = new ObjectInputStream(fis)) {
			GridPanel panel = (GridPanel) ois.readObject();
			gp.pixels = panel.pixels;
			gp.windowHeight = panel.windowHeight;
			gp.windowWidth = panel.windowWidth;
			gp.pixelHeight = panel.pixelHeight;
			gp.pixelWidth = panel.pixelWidth;
			gp.cols = panel.cols;
			gp.rows = panel.rows;
			gp.repaint();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// This can occur if the object we read from the file is not
			// an instance of any recognized class
			e.printStackTrace();
		}
	}
}
