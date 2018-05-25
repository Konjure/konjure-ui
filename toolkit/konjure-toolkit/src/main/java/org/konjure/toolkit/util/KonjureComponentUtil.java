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

package org.konjure.toolkit.util;

import org.konjure.toolkit.gui.KonjureAssets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;

/**
 * @author Connor Hollasch
 * @since 5/24/2018
 */
public class KonjureComponentUtil
{
    public static JButton decorateButton (final String text, final float fontSize)
    {
        final JButton button = new JButton(text);

        button.setFocusPainted(false);
        button.setBackground(KonjureColorUtil.adjustBrightness(KonjureAssets.KONJURE_BLACK, 1.05f));
        button.setForeground(KonjureAssets.KONJURE_GREEN);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEtchedBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        button.setFont(KonjureAssets.KONJURE_FONT.deriveFont(fontSize));

        return button;
    }

    public static JButton buttonWithActionListener (final JButton button, final ActionListener actionListener)
    {
        button.addActionListener(actionListener);
        return button;
    }
}
