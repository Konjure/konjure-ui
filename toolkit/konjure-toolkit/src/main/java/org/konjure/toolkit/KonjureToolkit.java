/*
 * Copyright (C) 2018 konjure-toolkit - All Rights Reserved
 *
 * Unauthorized copying of this file, via any median is strictly prohibited
 * proprietary and confidential. For more information, please contact me at
 * connor@hollasch.net
 *
 * Written by Connor Hollasch <connor@hollasch.net>, May 2018
 */

package org.konjure.toolkit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.konjure.toolkit.plugin.KonjurePluginManager;

/**
 * @author Connor Hollasch
 * @since May 09, 4:55 PM
 */
public class KonjureToolkit
{
    private static final Logger logger = Logger.getLogger(KonjureToolkit.class);

    private KonjurePluginManager pluginManager;
    private KonjureToolkitContext toolkitContext;

    public KonjureToolkit (final String... args) throws Exception
    {
        this.pluginManager = new KonjurePluginManager();
        this.toolkitContext = new KonjureToolkitContext(this.pluginManager, args);
    }

    public static void main (final String... args)
    {
        PropertyConfigurator.configure("log4j.properties");

        try {
            new KonjureToolkit(args);
        } catch (final Exception e) {
            KonjureToolkit.logger.fatal("An unhandled exception occurred while using the toolkit", e);
        }
    }

    public static Logger getLogger ()
    {
        return KonjureToolkit.logger;
    }
}
