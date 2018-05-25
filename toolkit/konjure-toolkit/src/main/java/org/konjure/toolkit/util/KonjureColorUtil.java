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

import java.awt.Color;

/**
 * @author Connor Hollasch
 * @since 5/24/2018
 */
public class KonjureColorUtil
{
    public static Color adjustBrightness (final Color color, final float factor)
    {
        final float r = Math.min(255, color.getRed() * factor);
        final float g = Math.min(255, color.getGreen() * factor);
        final float b = Math.min(255, color.getBlue() * factor);

        return new Color((int) r, (int) g, (int) b, color.getAlpha());
    }

    public static Color setAlpha (final Color input, final int alpha)
    {
        final int r = input.getRed();
        final int g = input.getGreen();
        final int b = input.getBlue();

        return new Color(r, g, b, alpha);
    }
}
