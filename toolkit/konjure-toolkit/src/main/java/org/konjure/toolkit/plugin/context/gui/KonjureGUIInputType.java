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

package org.konjure.toolkit.plugin.context.gui;

import org.konjure.toolkit.gui.KonjureAssets;
import org.konjure.toolkit.util.KonjureColorUtil;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.io.File;

import static org.konjure.toolkit.util.KonjureComponentUtil.buttonWithActionListener;
import static org.konjure.toolkit.util.KonjureComponentUtil.decorateButton;

/**
 * @author Connor Hollasch
 * @since 5/24/2018
 */
public enum KonjureGUIInputType
{
    BOOLEAN {
        @Override
        public JComponent buildComponent (final Object defaultValue)
        {
            final JCheckBox checkBox = new JCheckBox();
            checkBox.setSelected((Boolean) defaultValue);

            checkBox.setBackground(KonjureAssets.KONJURE_BLACK);
            checkBox.setMargin(new Insets(0, 0, 0, 0));

            checkBox.setOpaque(true);
            checkBox.setFocusPainted(false);

            return checkBox;
        }

        @Override
        public Object produceValue (final JComponent component)
        {
            return ((JCheckBox) component).isSelected();
        }
    },
    TEXT_FIELD {
        @Override
        public JComponent buildComponent (final Object defaultValue)
        {
            final JTextField textField = new JTextField();
            textField.setColumns(defaultValue.toString().length() + 10);
            textField.setText(defaultValue.toString());

            doInputFieldFormatting(textField);

            return textField;
        }

        @Override
        public Object produceValue (final JComponent component)
        {
            return ((JTextField) component).getText();
        }
    },
    TEXT_AREA {
        @Override
        public JComponent buildComponent (final Object defaultValue)
        {
            final JTextArea textArea = new JTextArea();
            textArea.setText(defaultValue.toString());

            doInputFieldFormatting(textArea);

            return textArea;
        }

        @Override
        public Object produceValue (final JComponent component)
        {
            return ((JTextArea) component).getText();
        }
    },
    NUMBER_INPUT {
        @Override
        public JComponent buildComponent (final Object defaultValue)
        {
            final JTextField textField = new JTextField();
            textField.setText(defaultValue.toString());
            textField.setColumns(defaultValue.toString().length() + 5);

            doInputFieldFormatting(textField);

            return textField;
        }

        @Override
        public Object produceValue (final JComponent component)
        {
            return ((JTextField) component).getText();
        }
    },
    FILE_INPUT {
        @Override
        public JComponent buildComponent (final Object defaultValue)
        {
            return KonjureGUIInputType.buildFileFolderChooserComponent(defaultValue, false);
        }

        @Override
        public Object produceValue (final JComponent component)
        {
            final JPanel panel = (JPanel) component;
            return ((JTextField) panel.getComponents()[0]).getText();
        }
    },
    FOLDER_INPUT {
        @Override
        public JComponent buildComponent (final Object defaultValue)
        {
            return KonjureGUIInputType.buildFileFolderChooserComponent(defaultValue, true);
        }

        @Override
        public Object produceValue (final JComponent component)
        {
            final JPanel panel = (JPanel) component;
            return ((JTextField) panel.getComponents()[0]).getText();
        }
    };

    public abstract JComponent buildComponent (Object defaultValue);

    public abstract Object produceValue (final JComponent component);

    private static void doInputFieldFormatting (final JComponent component)
    {
        component.setPreferredSize(new Dimension(-1, 35));

        component.setFont(KonjureAssets.KONJURE_FONT.deriveFont(12f));
        component.setForeground(KonjureColorUtil.adjustBrightness(KonjureAssets.KONJURE_GREEN, 1.5f));
        component.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEtchedBorder(),
                BorderFactory.createEmptyBorder(0, 5, 0, 0)
        ));
        component.setBackground(KonjureAssets.KONJURE_BLACK);
    }

    private static JComponent buildFileFolderChooserComponent (final Object defaultValue, final boolean folderChooser)
    {
        final JPanel fip = new JPanel();

        final JTextField inputField = new JTextField();
        inputField.setColumns(25);
        inputField.setText(defaultValue.toString());

        doInputFieldFormatting(inputField);

        fip.add(inputField);
        fip.add(buttonWithActionListener(decorateButton("Browse", 14f), e -> {
            final JFileChooser fc = new JFileChooser(inputField.getText());

            if (folderChooser) {
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            }

            int status = fc.showOpenDialog(null);

            if (status != JFileChooser.CANCEL_OPTION && status != JFileChooser.ERROR_OPTION) {
                final File sel = fc.getSelectedFile();
                inputField.setText(sel.getAbsolutePath());
            }
        }));

        fip.setBackground(new Color(0, 0, 0, 0));
        fip.setOpaque(true);
        fip.setBorder(BorderFactory.createEmptyBorder());

        return fip;
    }
}
