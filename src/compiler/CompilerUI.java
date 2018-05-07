package compiler.konjure.org;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class CompilerUI extends JFrame implements ActionListener {
	
	/* Initialize components */
	JPanel mainPanel, logoPanel, txtPanel, lblPanel, btnPanel, legalPanel;
	JTextField verTxt, cssTxt, jsTxt;
	JLabel legalLbl, verLbl, cssLbl, jsLbl, errorLbl, logoLbl;
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
			url = new URL("https://i.imgur.com/kIWSQSL.png");
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
        
    	verTxt = new JTextField(15);
    	verTxt.setBorder(BorderFactory.createEmptyBorder());
    	txtPanel.add(verTxt);
    	
    	cssTxt = new JTextField(15);
    	cssTxt.setBorder(BorderFactory.createEmptyBorder());
    	txtPanel.add(cssTxt);
    	
    	jsTxt = new JTextField(15);
    	jsTxt.setBorder(BorderFactory.createEmptyBorder());
    	txtPanel.add(jsTxt);
    	
        verLbl = new JLabel("Version No.");
        verLbl.setFont(new Font("Helvetica", Font.BOLD, 12));
        verLbl.setForeground(Color.WHITE);
        lblPanel.add(verLbl);
        
        cssLbl = new JLabel("CSS Directory");
        cssLbl.setFont(new Font("Helvetica", Font.BOLD, 12));
        cssLbl.setForeground(Color.WHITE);
        cssLbl.setBorder(new EmptyBorder(0,100,0,0));
        lblPanel.add(cssLbl);
        
        jsLbl = new JLabel("JS Directory");
        jsLbl.setFont(new Font("Helvetica", Font.BOLD, 12));
        jsLbl.setForeground(Color.WHITE);
        jsLbl.setBorder(new EmptyBorder(0,100,0,0));
        lblPanel.add(jsLbl);
        
        compileBtn = new JButton("Compile");
        compileBtn.setForeground(new Color(30, 27, 29));
        compileBtn.setBackground(new Color(127, 195, 30));
        compileBtn.setBorder(new LineBorder(Color.BLACK));
        Dimension d = new Dimension(170,35);
        compileBtn.setPreferredSize(d);
        compileBtn.addActionListener(this);
        btnPanel.add(compileBtn);
        
        legalLbl = new JLabel("Copyright \u00a9 2018 Konjure, all rights reserved.");
        legalLbl.setFont(new Font("Helvetica", Font.BOLD, 12));
        legalLbl.setForeground(Color.GRAY);
        legalPanel.add(legalLbl);
        
        
        logoPanel.setOpaque(false);
        txtPanel.setOpaque(false);
        lblPanel.setOpaque(false);
        btnPanel.setOpaque(false);
        legalPanel.setOpaque(false);
        
        
        mainPanel.add(logoPanel);
        mainPanel.add(txtPanel);
        mainPanel.add(lblPanel);
        mainPanel.add(btnPanel);
        mainPanel.add(legalPanel);
           
        mainPanel.setBackground(new Color(30, 27, 29));
        frame.setContentPane(mainPanel);
    }
    
    /* Invoke function */
    public static void main(String[] args) {

        EventQueue.invokeLater(() -> 
        {
            CompilerUI ui = new CompilerUI();
        });
        

    }

	@Override
	public void actionPerformed(ActionEvent e) {
		
		/* TODO: Add action on Compile. */
		if(e.getSource()==compileBtn)
		{
			/* TODO: Add conditions for compile button */
			try {
				compileCSS(cssTxt);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	public void compileCSS(JTextField directoryLbl) throws IOException {
		
		/* Beginning of CSS Text File development */
		String directory = directoryLbl.getText();
		String newFile = directory + "\\testFile.css";
		JOptionPane.showMessageDialog(null, directory);
		JOptionPane.showMessageDialog(null, newFile);
		
		String begText = "/*" + System.lineSeparator() + 
				"\t* Konjure UI CSS Library v0.1" + System.lineSeparator() +
				"\t* https://konjure.org/ui" +  System.lineSeparator() +  System.lineSeparator() +
				"\t* Copyright (c) 2018 Konjure and other contributors" + System.lineSeparator() +
				"\t* Released under the MIT license" + System.lineSeparator() +
				"\t* https://opensource.org/licenses/MIT" + System.lineSeparator() + System.lineSeparator() +
				"*/t";
		
		FileOutputStream out = new FileOutputStream(newFile);
		out.write(begText.getBytes());
		out.close();
	}
}


