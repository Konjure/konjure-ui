package org.konjure;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class CompilerUI extends JFrame implements ActionListener, FocusListener
{
    private JPanel mainPanel, logoPanel, txtPanel, lblPanel, padPanel1, btnPanel, padPanel2, legalPanel;
    private JTextField verTxt, cssTxt, lnTxt;
    private JLabel verLbl, cssLbl, lnLbl, padLbl1, padLbl2, legalLbl;
    private JCheckBox minCB;
    private JButton compileBtn;
    private JFrame frame;

    public CompilerUI ()
    {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e) {}
        configMenu();
        startMenu();
    }

    /* Set Up Frame */
    private void configMenu ()
    {
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
    private void startMenu ()
    {
        /* Initialize components */
        mainPanel = new JPanel();
        logoPanel = new JPanel();
        txtPanel = new JPanel();
        lblPanel = new JPanel();
        padPanel1 = new JPanel();
        btnPanel = new JPanel();
        padPanel2 = new JPanel();
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

        /* Labels */
        verLbl = new JLabel("Version No.");
        verLbl.setFont(new Font("Helvetica", Font.BOLD, 12));
        verLbl.setForeground(Color.WHITE);
        verLbl.setBorder(new EmptyBorder(20, 0, 0, 0));
        lblPanel.add(verLbl);

        cssLbl = new JLabel("CSS Directory");
        cssLbl.setFont(new Font("Helvetica", Font.BOLD, 12));
        cssLbl.setForeground(Color.WHITE);
        cssLbl.setBorder(new EmptyBorder(20, 35, 0, 0));
        lblPanel.add(cssLbl);

        lnLbl = new JLabel("Line Break");
        lnLbl.setFont(new Font("Helvetica", Font.BOLD, 12));
        lnLbl.setForeground(Color.WHITE);
        lnLbl.setBorder(new EmptyBorder(20, 85, 0, 95));
        lblPanel.add(lnLbl);

        /* Text Fields */
        verTxt = new JTextField(9);
        verTxt.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(),
                BorderFactory.createEmptyBorder(0, 5, 0, 0)));
        verTxt.setMargin(new Insets(0,1,0,0));
        Dimension dVer = verTxt.getPreferredSize();
        dVer.height = (int) (dVer.height * 1.25);
        verTxt.setPreferredSize(dVer);

        verTxt.addKeyListener(new KeyAdapter()
        {
            public void keyTyped(KeyEvent e)
            {
                if (verTxt.getText().length() >= 4 || e.getKeyChar() != '.' && (e.getExtendedKeyCodeForChar(e.getKeyChar()) < 48 ||
                        e.getExtendedKeyCodeForChar(e.getKeyChar()) > 57))
                    e.consume();
            }
        });
        txtPanel.add(verTxt);

        cssTxt = new JTextField(15);
        cssTxt.setBorder(BorderFactory.createEmptyBorder());
        Dimension dCSS = cssTxt.getPreferredSize();
        dCSS.height = (int) (dCSS.height * 1.25);
        cssTxt.setPreferredSize(dCSS);
        txtPanel.add(cssTxt);

        lnTxt = new JTextField(9);
        lnTxt.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(),
                BorderFactory.createEmptyBorder(0, 5, 0, 0)));
        verTxt.setMargin(new Insets(0,1,0,0));
        Dimension dLn = lnTxt.getPreferredSize();
        dLn.height = (int) (dLn.height * 1.25);
        lnTxt.setPreferredSize(dLn);

        lnTxt.addKeyListener(new KeyAdapter()
        {
            public void keyTyped(KeyEvent e)
            {
                if (lnTxt.getText().length() >= 2 || e.getKeyChar() != '0' && (e.getExtendedKeyCodeForChar(e.getKeyChar()) < 48 ||
                        e.getExtendedKeyCodeForChar(e.getKeyChar()) > 57)) //TODO: Fix / optimize conditional statement
                    e.consume();
            }
        });
        txtPanel.add(lnTxt);

        /* Check Box */
        minCB = new JCheckBox("Minify");
        minCB.setFont(new Font("Helvetica", Font.BOLD, 12));
        minCB.setForeground(Color.WHITE);
        txtPanel.add(minCB);

        /* Padding */
        padLbl1 = new JLabel("");
        padLbl1.setBorder(new EmptyBorder(0, 0, 0, 0));
        padPanel1.add(padLbl1);

        /* Compiler Button */
        compileBtn = new JButton("Compile");
        compileBtn.setForeground(new Color(30, 27, 29));
        compileBtn.setBackground(new Color(127, 195, 30));
        Dimension d = new Dimension(170, 35);
        compileBtn.setPreferredSize(d);
        compileBtn.addActionListener(this);
        btnPanel.add(compileBtn);

        /* Padding */
        padLbl2 = new JLabel("");
        padLbl2.setBorder(new EmptyBorder(10, 0, 0, 0));
        padPanel2.add(padLbl2);

        legalLbl = new JLabel("Copyright \u00a9 2018 Konjure, all rights reserved.");
        legalLbl.setFont(new Font("Helvetica", Font.BOLD, 12));
        legalLbl.setForeground(Color.GRAY);
        legalPanel.add(legalLbl);

        /* Add focus listener to CSS text field */
        cssTxt.addFocusListener(this);

        logoPanel.setOpaque(false);
        txtPanel.setOpaque(false);
        lblPanel.setOpaque(false);
        padPanel1.setOpaque(false);
        btnPanel.setOpaque(false);
        padPanel2.setOpaque(false);
        legalPanel.setOpaque(false);

        mainPanel.add(logoPanel);
        mainPanel.add(lblPanel);
        mainPanel.add(txtPanel);
        mainPanel.add(padPanel1);
        mainPanel.add(btnPanel);
        mainPanel.add(padPanel2);
        mainPanel.add(legalPanel);

        mainPanel.setBackground(new Color(30, 27, 29));
        frame.setContentPane(mainPanel);
    }

    /* Invoke function */
    public static void main (String[] args)
    {

        EventQueue.invokeLater(() ->
        {
            CompilerUI ui = new CompilerUI();
        });
    }

    @Override
    public void focusGained (FocusEvent e)
    {

        if (e.getSource() == cssTxt) {
            final JFileChooser jc = new JFileChooser();
            jc.setCurrentDirectory(new java.io.File("."));
            jc.setDialogTitle("CSS Directory");
            jc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jc.setAcceptAllFileFilterUsed(false);

            if (jc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                System.out.println("CSS Directory selected: "
                        + jc.getSelectedFile());
                cssTxt.setText(jc.getSelectedFile().toString());
            }
            /* Reset focus */
            cssTxt.setFocusable(false);
            cssTxt.setFocusable(true);
        }
    }

    @Override
    public void focusLost (FocusEvent e)
    {}

    @Override
    public void actionPerformed (ActionEvent e)
    {
        if (e.getSource() == compileBtn) {
            if(cssTxt.getText().equals(""))
            {
                JOptionPane.showMessageDialog(frame,
                        "Please enter CSS directory.",
                        "Issue Compiling Directory",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    compileCSS(cssTxt); // Run compileCSS function
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        }
    }

    public void compileCSS (JTextField directoryLbl) throws IOException
    {
        /* Beginning of CSS Text File development */
        String directory = directoryLbl.getText();
        String newFile = directory + "\\konjure-ui.css";
        System.out.println("Creating Konjure-UI file at "
                + newFile + " ...");

        String begText = "/*" + System.lineSeparator() + System.lineSeparator() +
                "\t* Konjure UI CSS Library v0.1" + System.lineSeparator() +
                "\t* https://konjure.org/ui" + System.lineSeparator() + System.lineSeparator() +
                "\t* Copyright (c) 2018 Konjure and other contributors" + System.lineSeparator() +
                "\t* Released under the MIT license" + System.lineSeparator() +
                "\t* https://opensource.org/licenses/MIT" + System.lineSeparator() + System.lineSeparator() +
                "*/";

        FileOutputStream out = new FileOutputStream(newFile);
        out.write(begText.getBytes());
        out.close();

    }
}