/*
 * MIT License
 *
 * Copyright (c) 2018 Konjure
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.konjure.toolkit.gui;

import org.konjure.toolkit.plugin.KonjurePlugin;
import org.konjure.toolkit.plugin.KonjurePluginManager;
import org.konjure.toolkit.plugin.context.gui.KonjureGUIOptions;
import org.konjure.toolkit.plugin.context.gui.KonjureGUIPluginContext;
import org.konjure.toolkit.util.KonjureColorUtil;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.konjure.toolkit.util.KonjureComponentUtil.buttonWithActionListener;
import static org.konjure.toolkit.util.KonjureComponentUtil.decorateButton;

/**
 * @author Connor Hollasch
 * @since 5/14/2018
 */
public class KonjureUI
{
    private final JFrame frame;

    private JPanel contentPanel;
    private JPanel contextPanel;

    private KonjurePluginManager pluginManager;

    public KonjureUI (final KonjurePluginManager pluginManager)
    {
        this.pluginManager = pluginManager;
        this.frame = new JFrame("Konjure™ Toolkit");

        this.frame.setSize(new Dimension(750, 500));
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.frame.setLocationRelativeTo(null);
        this.frame.setIconImage(KonjureAssets.FRAME_LOGO);

        createContentPanel();
        doHomeContext();

        this.frame.setVisible(true);
    }

    private void createContentPanel ()
    {
        this.contentPanel = new JPanel()
        {
            @Override
            public void paint (final Graphics g)
            {
                offloadFullScreenBGPaint(g, KonjureAssets.BACKGROUND);
                super.paint(g);
            }
        };

        this.contentPanel.setBackground(new Color(0, 0, 0, 0));
        this.contentPanel.setOpaque(true);

        this.contentPanel.setLayout(new BorderLayout());
        this.frame.setContentPane(this.contentPanel);
    }

    private void contextSwitch ()
    {
        this.contentPanel.removeAll();

        this.contextPanel = new JPanel()
        {
            @Override
            protected void paintComponent (Graphics g)
            {
                g.setColor(getBackground());
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };

        this.contextPanel.setBackground(new Color(0, 0, 0, 0));
        this.contextPanel.setOpaque(true);

        final JPanel wrapper = new JPanel();
        wrapper.setBackground(new Color(0, 0, 0, 0));
        wrapper.setOpaque(true);

        final JLabel copyright = new JLabel("Copyright \u00a9 2018 Konjure, all rights reserved.");
        copyright.setFont(KonjureAssets.KONJURE_FONT.deriveFont(12f));
        copyright.setAlignmentX(Component.CENTER_ALIGNMENT);
        copyright.setForeground(KonjureAssets.KONJURE_GREEN.darker());

        final JSeparator separator = new JSeparator();
        separator.setBackground(KonjureAssets.KONJURE_BLACK.brighter());
        separator.setForeground(KonjureAssets.KONJURE_BLACK.darker());

        wrapper.add(separator);
        wrapper.add(copyright);
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));

        this.contentPanel.add(wrapper, BorderLayout.PAGE_END);

        SwingUtilities.invokeLater(() -> {
            this.contentPanel.revalidate();
            this.contentPanel.repaint();
        });
    }

    public void doHomeContext ()
    {
        contextSwitch();

        // Create dynamic 5 x n grid for plugin buttons.
        int rows = (int) Math.max(3, Math.ceil(this.pluginManager.getPlugins().size() / 5.0));
        this.contextPanel.setLayout(new GridLayout(rows, 5, 10, 10));

        int used = 0;
        for (final KonjurePlugin plugin : this.pluginManager.getPlugins().values()) {
            final JButton pluginButton = decorateButton(plugin.guiName(), 16f);
            pluginButton.addActionListener(e -> doPluginContext(plugin));

            this.contextPanel.add(pluginButton);
            ++used;
        }

        while (used < (rows * 5)) {
            final JButton empty = new JButton();
            empty.setBackground(new Color(0, 0, 0, 0));
            empty.setBorderPainted(false);
            empty.setFocusPainted(false);
            empty.setEnabled(false);
            empty.setOpaque(true);

            this.contextPanel.add(empty);
            ++used;
        }

        this.contextPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.contentPanel.add(this.contextPanel);
    }

    public void doPluginContext (final KonjurePlugin plugin)
    {
        contextSwitch();

        final JScrollPane jsp = new JScrollPane(this.contextPanel)
        {
            @Override
            public void paint (final Graphics g)
            {
                offloadFullScreenBGPaint(g, KonjureAssets.BACKGROUND);
                super.paint(g);
            }
        };

        jsp.getVerticalScrollBar().setUI(new KonjureScrollBarUI());
        jsp.getVerticalScrollBar().setUnitIncrement(10);

        jsp.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        jsp.setBackground(new Color(0, 0, 0, 0));
        jsp.setBorder(BorderFactory.createEmptyBorder());
        jsp.setOpaque(true);
        jsp.setIgnoreRepaint(false);

        final JLabel header = new JLabel("Konjure " + plugin.guiName());
        header.setFont(KonjureAssets.KONJURE_FONT.deriveFont(32f).deriveFont(Font.BOLD));
        header.setForeground(KonjureAssets.KONJURE_GREEN);
        header.setBackground(KonjureColorUtil.setAlpha(KonjureAssets.KONJURE_BLACK, 150));
        header.setOpaque(true);

        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEtchedBorder(),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.contextPanel.add(Box.createVerticalStrut(10));
        this.contextPanel.add(header);
        this.contextPanel.add(Box.createVerticalStrut(15));

        final JSeparator headerToBodySeparator = new JSeparator();
        headerToBodySeparator.setBackground(KonjureAssets.KONJURE_GREEN);
        headerToBodySeparator.setForeground(KonjureAssets.KONJURE_GREEN.brighter());
        this.contextPanel.add(headerToBodySeparator);

        this.contextPanel.add(Box.createVerticalStrut(15));

        final KonjureGUIOptions guiOptions = new KonjureGUIOptions();
        plugin.populateGUIOptionSpec(guiOptions);

        for (final String key : guiOptions.getComponentMap().keySet()) {
            final JPanel wrapper = new JPanel()
            {
                @Override
                public void repaint ()
                {
                    KonjureUI.this.contentPanel.repaint();
                    super.repaint();
                }
            };

            final JComponent value = guiOptions.getComponentMap().get(key);
            value.setOpaque(true);

            final JLabel label = new JLabel(guiOptions.getComponentName(key));
            label.setFont(KonjureAssets.KONJURE_FONT.deriveFont(16f));
            label.setForeground(KonjureAssets.KONJURE_GREEN);
            label.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEtchedBorder(),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
            label.setBackground(KonjureColorUtil.setAlpha(KonjureAssets.KONJURE_BLACK, 150));
            label.setOpaque(true);

            wrapper.add(label);
            wrapper.add(Box.createHorizontalStrut(10));
            wrapper.add(value);
            wrapper.setBackground(new Color(0, 0, 0, 0));
            wrapper.setOpaque(true);

            this.contextPanel.add(wrapper);
        }

        final JPanel bottomOptions = new JPanel();

        bottomOptions.add(buttonWithActionListener(decorateButton("Back", 14f), e -> doHomeContext()));
        bottomOptions.add(new JLabel("      "));
        bottomOptions.add(buttonWithActionListener(decorateButton("Reset", 14f), e -> doPluginContext(plugin)));

        bottomOptions.add(new JLabel("      "));
        bottomOptions.add(buttonWithActionListener(decorateButton("Submit", 14f), e -> {
            final KonjureGUIPluginContext context = guiOptions.buildContext();

            try {
                final String status = plugin.execute(context);
                showOkDialog(status, "Plugin ran successfully!", JOptionPane.INFORMATION_MESSAGE);
            } catch (final Exception ex) {
                final StringWriter sw = new StringWriter();
                final PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);

                showOkDialog("An exception occurred while running the \"" + plugin.guiName() + "\" plugin.\n"
                        + sw.getBuffer().toString(), "Error!" ,JOptionPane.ERROR_MESSAGE);
            }
        }));

        bottomOptions.setBackground(KonjureColorUtil.setAlpha(KonjureAssets.KONJURE_BLACK, 150));
        bottomOptions.setOpaque(true);

        bottomOptions.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEtchedBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        bottomOptions.setLayout(new BoxLayout(bottomOptions, BoxLayout.X_AXIS));

        this.contextPanel.add(Box.createVerticalStrut(15));

        final JSeparator bodyToOptionsSeparator = new JSeparator();
        bodyToOptionsSeparator.setBackground(KonjureAssets.KONJURE_GREEN);
        bodyToOptionsSeparator.setForeground(KonjureAssets.KONJURE_GREEN.brighter());
        this.contextPanel.add(bodyToOptionsSeparator);

        this.contextPanel.add(Box.createVerticalStrut(15));
        this.contextPanel.add(bottomOptions);
        this.contextPanel.add(Box.createVerticalStrut(10));

        this.contextPanel.setLayout(new BoxLayout(this.contextPanel, BoxLayout.Y_AXIS));

        this.contentPanel.setOpaque(true);
        this.contentPanel.add(jsp);
        this.contentPanel.revalidate();
        this.frame.repaint();
    }

    private void offloadFullScreenBGPaint (final Graphics g, final Image image)
    {
        final int clipWidth = this.contentPanel.getWidth();
        final int clipHeight = this.contentPanel.getHeight();

        g.setColor(Color.black);
        g.fillRect(0, 0, clipWidth, clipHeight);

        g.setColor(KonjureAssets.KONJURE_BLACK);
        g.fillRect(0, 0, clipWidth, clipHeight);

        g.drawImage(image,
                0,
                0,
                clipWidth,
                clipHeight,
                null);

        g.setColor(new Color(0, 0, 0, 50));
        g.fillRect(0, 0, clipWidth, clipHeight);
    }

    private class KonjureScrollBarUI extends BasicScrollBarUI
    {
        private final Dimension d = new Dimension();

        @Override
        protected JButton createDecreaseButton (int orientation)
        {
            return new JButton()
            {
                @Override
                public Dimension getPreferredSize ()
                {
                    return d;
                }
            };
        }

        @Override
        protected JButton createIncreaseButton (int orientation)
        {
            return new JButton()
            {
                @Override
                public Dimension getPreferredSize ()
                {
                    return d;
                }
            };
        }

        @Override
        protected void paintTrack (final Graphics g, final JComponent c, final Rectangle r)
        {
            g.setColor(KonjureAssets.KONJURE_BLACK.darker());
            g.fillRect(r.x, r.y, r.width, r.height);
        }

        @Override
        protected void paintThumb (final Graphics g, final JComponent c, final Rectangle r)
        {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            JScrollBar sb = (JScrollBar) c;

            if (!sb.isEnabled() || r.width > r.height) {
                return;
            } else if (this.isDragging) {
                g2.setPaint(KonjureAssets.KONJURE_GREEN.darker());
            } else if (isThumbRollover()) {
                g2.setPaint(Color.DARK_GRAY);
            } else {
                g2.setPaint(KonjureAssets.KONJURE_BLACK.brighter());
            }

            g2.fillRoundRect(r.x + 1, r.y + 1, r.width - 2, r.height - 2, 15, 15);
            g2.drawRoundRect(r.x + 1, r.y + 1, r.width - 2, r.height - 2, 15, 15);

            g2.setPaint(KonjureColorUtil.adjustBrightness(g2.getColor(), 1.2f));
            g2.drawLine(r.x + 3, r.y + (r.height / 2) - 5, r.x + r.width - 3, r.y + (r.height / 2) - 5);
            g2.drawLine(r.x + 3, r.y + (r.height / 2), r.x + r.width - 3, r.y + (r.height / 2));
            g2.drawLine(r.x + 3, r.y + (r.height / 2) + 5, r.x + r.width - 3, r.y + (r.height / 2) + 5);

            g2.dispose();
        }

        @Override
        protected void setThumbBounds (int x, int y, int width, int height)
        {
            super.setThumbBounds(x, y, width, height);
            scrollbar.repaint();
        }
    }

    private void showOkDialog (final String message, final String title, final int messageType)
    {
        Object[] options = {"OK"};
        JOptionPane.showOptionDialog(this.frame,
                message, title,
                JOptionPane.PLAIN_MESSAGE,
                messageType,
                null,
                options,
                options[0]);
    }
}
