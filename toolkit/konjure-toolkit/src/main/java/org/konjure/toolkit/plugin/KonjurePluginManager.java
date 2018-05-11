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

package org.konjure.toolkit.plugin;

import org.konjure.toolkit.plugins.KonjureCompiler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Connor Hollasch
 * @since 5/10/2018
 */
public class KonjurePluginManager
{
    private Map<String, KonjurePlugin> plugins;

    public KonjurePluginManager ()
    {
        this.plugins = new HashMap<>();

        // Add plugins for application integration.
        addPlugin(new KonjureCompiler());
    }

    public KonjurePlugin getPluginByName (final String name)
    {
        return this.plugins.get(name.toLowerCase());
    }

    public Map<String, KonjurePlugin> getPlugins ()
    {
        return this.plugins;
    }

    private void addPlugin (final KonjurePlugin plugin)
    {
        final String name = plugin.cliName().toLowerCase();
        this.plugins.put(name, plugin);
    }
}
