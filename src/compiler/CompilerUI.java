package compiler.konjure.org;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class CompilerUI extends JFrame implements ActionListener {
	
	/* Initialize components */
	JPanel mainPanel, logoPanel, txtPanel, lblPanel, btnPanel, legalPanel;
	JTextField verTXT, cssTXT, jsTXT;
	JLabel legalLBL, verLBL, cssLBL, jsLBL, errorLBL, logoLBL;
	JButton compileBtn;
	JFrame frame;
	
	public CompilerUI() {
		configMenu();
        startMenu();
    }

	/* Set Up Frame */
	private void configMenu() {
		frame = new JFrame("Konjure UI Compiler");
		frame.setVisible(true);
        frame.setSize(750, 422);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        URL url = null;
        Image ico = null;
		try {
			url = new URL("https://imgur.com/ToX4arj.png");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
    	try {
			ico = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
        frame.setIconImage(ico);
        frame.setResizable(false);
	}
	
	/* Set Up Content */
    private void startMenu() {
    	
    	/* Initialize components */
        mainPanel = new JPanel();
        logoPanel = new JPanel();
        txtPanel = new JPanel();
        lblPanel = new JPanel();
        btnPanel = new JPanel();
        legalPanel = new JPanel(); 
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        frame.getContentPane().add(mainPanel);
    	
        URL url = null;
        Image logo = null;
		try {
			url = new URL("https://i.imgur.com/L1d6kIY.png");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
    	try {
			logo = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	JLabel picLabel = new JLabel(new ImageIcon(logo));
    	logoPanel.add(picLabel);
        
    	verTXT = new JTextField(15);
    	txtPanel.add(verTXT);
    	
    	cssTXT = new JTextField(15);
    	txtPanel.add(cssTXT);
    	
    	jsTXT = new JTextField(15);
    	txtPanel.add(jsTXT);
    	
        verLBL = new JLabel("Version No.");
        verLBL.setAlignmentX(100);
        lblPanel.add(verLBL);
        
        cssLBL = new JLabel("CSS Directory");
        cssLBL.setBorder(new EmptyBorder(0,100,0,0));
        lblPanel.add(cssLBL);
        
        jsLBL = new JLabel("JS Directory");
        jsLBL.setBorder(new EmptyBorder(0,100,0,0));
        lblPanel.add(jsLBL);
        
        compileBtn = new JButton("Compile");
        compileBtn.setAlignmentX(100);
        compileBtn.addActionListener(this);
        btnPanel.add(compileBtn);
        
        legalLBL = new JLabel("Copyright \u00a9 2018 Konjure, all rights reserved.");
        legalPanel.add(legalLBL);
        
        
        mainPanel.add(logoPanel);
        mainPanel.add(txtPanel);
        mainPanel.add(lblPanel);
        mainPanel.add(btnPanel);
        mainPanel.add(legalPanel);
        frame.getContentPane().add(mainPanel);
    }
    
    /* Invoke Function */
    public static void main(String[] args) {

        EventQueue.invokeLater(() -> 
        {
            CompilerUI ui = new CompilerUI();
            
        });

    }

	@Override
	public void actionPerformed(ActionEvent e) {
		
		/* TODO: Add action on Compile. */
		
	}
	
}


