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

package org.konjure.toolkit;

import org.apache.commons.cli.*;
import org.konjure.toolkit.plugin.KonjurePlugin;
import org.konjure.toolkit.plugin.KonjurePluginManager;

/**
 * @author Connor Hollasch
 * @since 5/9/2018
 */
public class KonjureToolkitContext
{
    private final String[] unparsedCliArgs;

    private KonjurePluginManager pluginManager;
    private final CommandLineParser commandLineParser;

    public KonjureToolkitContext (final KonjurePluginManager pluginManager, final String[] args)
    {
        this.pluginManager = pluginManager;
        this.unparsedCliArgs = args;

        this.commandLineParser = new BasicParser();

        if (args.length == 0) {
            KonjureToolkit.getLogger().error("Must specify a toolkit module to execute. List of registered modules:");
            printAllModules();
            return;
        }

        final String module = args[0].toLowerCase();
        final KonjurePlugin plugin = this.pluginManager.getPluginByName(module);

        if (plugin == null) {
            KonjureToolkit.getLogger().error("No such module as " + module + ". List of registered modules:");
            printAllModules();
            return;
        }

        final HelpFormatter helpFormatter = new HelpFormatter();
        final Options options = new Options();

        plugin.populateOptionSpec(options);

        final Option helpOption = new Option("help", "help", false, "Displays the help page");
        options.addOption(helpOption);

        try {
            final String[] nArgs = new String[args.length - 1];
            System.arraycopy(args, 1, nArgs, 0, nArgs.length);

            final CommandLine commandLine = this.commandLineParser.parse(options, nArgs);

            if (commandLine.hasOption("help")) {
                helpFormatter.printHelp(module, options);
                return;
            }

            plugin.execute(commandLine);
            return;
        } catch (final ParseException e) {
            System.out.println(e.getMessage());
            helpFormatter.printHelp(module, options);
        }
    }

    private void printAllModules ()
    {
        int idx = 1;
        for (final String module : this.pluginManager.getPlugins().keySet()) {
            KonjureToolkit.getLogger().error(" " + (idx++) +". " + module);
        }
    }
}
