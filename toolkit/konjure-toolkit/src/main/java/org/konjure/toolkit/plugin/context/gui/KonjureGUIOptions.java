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

import javax.swing.JComponent;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Connor Hollasch
 * @since 5/24/2018
 */
public class KonjureGUIOptions
{
    private LinkedHashMap<String, JComponent> componentMap;
    private Map<String, KonjureGUIInputType> inputTypes;
    private Map<String, String> keyToNameMap;

    public KonjureGUIOptions ()
    {
        this.componentMap = new LinkedHashMap<>();
        this.inputTypes = new HashMap<>();
        this.keyToNameMap = new HashMap<>();
    }

    public void addGUIOption (final String key, final String name, final KonjureGUIInputType inputType, final Object defaultValue)
    {
        addGUIOption(key, name, inputType, defaultValue, null);
    }

    public void addGUIOption (
            final String key,
            final String name,
            final KonjureGUIInputType inputType,
            final Object defaultValue,
            final String tooltip)
    {
        final JComponent component = inputType.buildComponent(defaultValue);

        // TODO Tooltip breaks rendering and leaves hole in some component without forcing a re-draw.
        if (tooltip != null && true) {
            component.setToolTipText(tooltip);
        }

        this.componentMap.put(key, component);
        this.inputTypes.put(key, inputType);
        this.keyToNameMap.put(key, name);
    }

    public KonjureGUIPluginContext buildContext ()
    {
        final Map<String, Object> contextMap = new HashMap<>();

        for (final String key : this.componentMap.keySet()) {
            final JComponent value = this.componentMap.get(key);
            contextMap.put(key, this.inputTypes.get(key).produceValue(value));
        }

        final KonjureGUIPluginContext pluginContext = new KonjureGUIPluginContext(contextMap);
        return pluginContext;
    }

    public Map<String, JComponent> getComponentMap ()
    {
        return this.componentMap;
    }

    public String getComponentName (final String key)
    {
        return this.keyToNameMap.get(key);
    }
}
