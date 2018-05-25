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

import org.konjure.toolkit.KonjureToolkit;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

/**
 * @author Connor Hollasch
 * @since 5/14/2018
 */
public enum KonjureAssets
{;
    public static Image FRAME_LOGO;
    public static Image BACKGROUND;

    public static Color KONJURE_BLACK = new Color(30, 27, 29);
    public static Color KONJURE_GREEN = new Color(127, 195, 30);

    public static Font KONJURE_FONT;

    static {
        try {
            FRAME_LOGO = ImageIO.read(KonjureAssets.class.getResourceAsStream("/icon.png"));
            BACKGROUND = ImageIO.read(KonjureAssets.class.getResourceAsStream("/background.jpg"));

            try {
                KONJURE_FONT = Font.createFont(
                        Font.TRUETYPE_FONT,
                        KonjureAssets.class.getResourceAsStream("/OpenSans-Regular.ttf")
                );
            } catch (final FontFormatException e) {
                KonjureToolkit.getLogger().fatal("Could not create load font into toolkit GUI", e);
            }

        } catch (IOException e) {
            KonjureToolkit.getLogger().fatal("Could not read an asset for toolkit GUI", e);
            System.exit(1);
        }
    }
}
