const compressor = require('node-minify');
const aws = require('aws-sdk');
const packageDetails = require('../package.json');
const path = require('path');
const fs = require('fs');
var mime = require('mime-types');

const cdnData = [];
var configuration = {};
var setup = false;

exports.srcPath = path.join(`${__dirname}/../`, 'src');
exports.distPath = path.join(`${__dirname}/../`, 'dist');
exports.cdnData = cdnData;

exports.header = `/*\n\t* Konjure UI $type$ Library ${packageDetails.version}\n\t* https://konjure.org/development/overview\n\n\t* Copyright (c) 2018 Konjure and other contributors\n\t* Released under the MIT license\n\t* https://opensource.org/licenses/MIT\n\n*/`;

exports.configure = function (config) {
    configuration = config;
    setup = true;
};

exports.addAllToCdn = function (data) {
    for (let i in data) {
        exports.addToCdn(data[i]);
    }
};

exports.addToCdn = function (data) {
    cdnData.push(data);
};

exports.publish = function () {
    if (!setup) {
        console.log('Configuration not setup, cannot publish to s3!');
        return;
    }

    var tos3 = process.argv[3] || 'false';
    var env = process.argv[2] || 'dev';

    if (env === 'dev') {
        aws.config.loadFromPath(configuration.devCredentials);
    }

    aws.config.region = configuration.region;
    var s3 = new aws.S3({
        params: {Bucket: configuration.bucket}
    });

    console.log('Preprocessing data...');
    preprocessData((data) => {
        if (tos3 === 'false') {
            return;
        }

        for (let i in data) {
            let datum = data[i];
            if (datum.raw) {
                console.log(`Skipping raw file (${datum.file})`);
                continue;
            }

            let flags = {
                Key: datum.file,
                Body: datum.value,
                ACL: 'public-read'
            };

            if (datum.type) {
                flags.ContentType = datum.type;
            }

            s3.upload(flags, (err, res) => {
                if (err) {
                    console.log(`Error while uploading file to s3 bucket: ${err.message}`);
                } else {
                    console.log(`Upload success for ${datum.file} (${datum.type})`);
                }
            });
        }

        console.log(`Publishing ${data.length} items to s3 bucket ${configuration.bucket}`)
    });
};

function sanatizeInput(data) {
    if (data.value.input) {
        data.value.input = path.join(exports.srcPath, data.value.input);
    }
    if (data.value.output && data.value.output !== true) {
        data.value.output = path.join(exports.distPath, data.value.output);
    }

    return data;
}

function preprocessData(onFinish) {
    // Convert all folders into individual files.
    let toAdd = [];
    for (let i in cdnData) {
        let rawOutput = cdnData[i].value.output;
        const data = sanatizeInput(cdnData[i]);

        if (data.type === 'folder') {
            data.raw = true;

            function walkSync(base, friendlyPath, fakePath, callback) {
                fs.readdirSync(base).forEach(function (name) {
                    var filePath = path.join(base, name);
                    friendlyPath = `${friendlyPath}/${name}`;
                    fakePath = `${fakePath}/${name}`;
                    var stat = fs.statSync(filePath);

                    if (stat.isFile()) {
                        callback(filePath, fakePath, friendlyPath);
                    } else if (stat.isDirectory()) {
                        walkSync(filePath, friendlyPath, fakePath, callback);
                    }
                })
            }

            walkSync(data.value.input, data.file, rawOutput, function (filePath, fakePath, friendlyPath) {
                let d = {
                    file: friendlyPath,
                    value: {
                        input: filePath
                    }
                };

                if (data.value.output) {
                    if (fakePath.startsWith('./')) {
                        fakePath = fakePath.substring(2);
                    }
                    d.file = fakePath;
                    d.value.output = path.join(exports.distPath, fakePath);
                }

                cdnData.push(d);
            });
        }
    }

    var promises = [];

    for (let i in cdnData) {
        const data = cdnData[i];
        promises.push(new Promise(resolve => {
            if (data.process) {
                switch (data.process) {
                    case 'minify':
                        minify(data.value).then((val) => {
                            let type = data.value.type;
                            var size = Buffer.byteLength(val);

                            console.log(`Minified ${data.value.input} into ${size} bytes`);
                            data.value = val;

                            switch (type) {
                                case 'JS':
                                    data.type = "application/javascript";
                                    break;
                                case 'CSS':
                                    data.type = "text/css";
                                    break;
                            }

                            stampHeader(data, type);
                            resolve();
                        }, (err) => {
                            console.log(`Error while minifying input file: ${data.value.input}, ${err}`);
                        });
                }
            } else if (data.value.input) {
                if (data.type === 'folder') {
                    resolve();
                }

                let result = fs.readFileSync(data.value.input);
                let type = data.value.input;

                if (data.value.output) {
                    function ensureDirectoryExistence(filePath) {
                        var dirname = path.dirname(filePath);
                        if (fs.existsSync(dirname)) {
                            return true;
                        }
                        ensureDirectoryExistence(dirname);
                        fs.mkdirSync(dirname);
                    }

                    ensureDirectoryExistence(data.value.output);
                    fs.writeFile(data.value.output, result);
                }

                data.value = result;
                data.type = mime.lookup(type);

                resolve();
            } else {
                console.log(`Nothing to do for ${data}`);
                resolve();
            }
        }));
    }

    Promise.all(promises).then(() => {
        onFinish(cdnData);
    }, (err) => {
        console.log(`Error occurred while preprocessing data`);
        console.error(err);
    });
}

function stampHeader(data, type) {
    data.value = `${exports.header}\n\n${data.value}`.replace('$type$', type);
    return data;
}

function minify(data) {
    return new Promise((resolve, reject) => {
        data.callback = (err, data) => {
            if (err) {
                reject(err);
            } else {
                resolve(data);
            }
        };

        compressor.minify(data);
    });
}
