var cdn = require('./cdn.js');

cdn.configure({
    bucket: 'dist.konjure.org',
    region: 'us-east-1',
    devCredentials: './aws.credentials',
});

cdn.addAllToCdn([
    {
        file: 'konjure-ui.min.js',
        process: 'minify',
        value: {
            type: 'JS',
            compressor: 'uglifyjs',
            input: 'js/*.js',
            output: 'konjure-ui.min.js'
        }
    },
    {
        file: 'konjure-ui.min.css',
        process: 'minify',
        value: {
            type: 'CSS',
            compressor: 'yui-css',
            input: 'css/*.css',
            output: 'konjure-ui.min.css'
        }
    },
    {
        file: 'site',
        type: 'folder',
        value: {
            input: 'site',
            output: '.'
        }
    }
]);

cdn.publish();