

let header =
    `
/*

    * Konjure UI CSS Library ${packageDetails.version}
    * https://konjure.org/development/overview

    * Copyright (c) 2018 Konjure and other contributors
    * Released under the MIT license
    * https://opensource.org/licenses/MIT

*/
`;

function stampHeader(minified) {
    minified.value = `${header}\n\n${minified.value}`;
    return minified;
}

function minify(callback) {
    // Compress all JS files
    compressor.minify({
        compressor: 'uglifyjs',
        input: './src/js/*.js',
        output: './dist/konjure-ui.min.js',
        callback: function(err, min) {
            console.log("Minified all JS files.");
            callback(stampHeader({file: 'konjure-ui.min.js', type: 'application/javascript', value: min}));
        }
    });

    // Compress all CSS files
    compressor.minify({
        compressor: 'yui-css',
        input: './src/css/*.css',
        output: './dist/konjure-ui.min.css',
        callback: function(err, min) {
            console.log("Minified all CSS files.");
            callback(stampHeader({file: 'konjure-ui.min.css', type: 'text/css', value: min}));
        }
    });
}

function publish(min) {
    s3.upload({
        Key: min.file,
        Body: min.value,
        ContentType: min.type,
        ACL: 'public-read'
    }, function(err, data) {
        if (err) {
            console.log(`Error when uploading file to s3 bucket: ${err.message}`);
        }
        console.log(`Upload success for ${min.file}.`);
    });
}

minify(publish);