﻿(function(name, context, definition) {
    'use strict';
    if (typeof window.define === 'function' && window.define.amd) {
        window.define(definition)
    } else if (typeof module !== 'undefined' && module.exports) {
        module.exports = definition()
    } else if (context.exports) {
        context.exports = definition()
    } else {
        context[name] = definition()
    }
})('PCHOMEFingerprint', this, function() {
    'use strict';
    var PCHOMEFingerprint = function(options) {
        if (!(this instanceof PCHOMEFingerprint)) {
            return new PCHOMEFingerprint(options)
        }
        var defaultOptions = {
            swfContainerId: 'fingerprintjs2',
            swfPath: 'flash/compiled/FontList.swf',
            detectScreenOrientation: true,
            sortPluginsFor: [/palemoon/i],
            userDefinedFonts: []
        };
        this.options = this.extend(options, defaultOptions);
        this.nativeForEach = Array.prototype.forEach;
        this.nativeMap = Array.prototype.map
    };
    PCHOMEFingerprint.prototype = {
        extend: function(source, target) {
            if (source == null) {
                return target
            }
            for (var k in source) {
                if (source[k] != null && target[k] !== source[k]) {
                    target[k] = source[k]
                }
            }
            return target
        },
        get: function(done) {
            var that = this;
            var keys = {
                data: [],
                addPreprocessedComponent: function(pair) {
                    var componentValue = pair.value;
                    if (typeof that.options.preprocessor === 'function') {
                        componentValue = that.options.preprocessor(pair.key, componentValue)
                    }
                    keys.data.push({
                        key: pair.key,
                        value: componentValue
                    })
                }
            };
            keys = this.userAgentKey(keys);
            keys = this.languageKey(keys);
            keys = this.colorDepthKey(keys);
            keys = this.deviceMemoryKey(keys);
            keys = this.pixelRatioKey(keys);
            keys = this.hardwareConcurrencyKey(keys);
            keys = this.screenResolutionKey(keys);
            keys = this.availableScreenResolutionKey(keys);
            keys = this.timezoneOffsetKey(keys);
            keys = this.sessionStorageKey(keys);
            keys = this.localStorageKey(keys);
            keys = this.indexedDbKey(keys);
            keys = this.addBehaviorKey(keys);
            keys = this.openDatabaseKey(keys);
            keys = this.cpuClassKey(keys);
//            keys = this.platformKey(keys);
//            keys = this.doNotTrackKey(keys);
//            keys = this.pluginsKey(keys);
            keys = this.canvasKey(keys);
            keys = this.webglKey(keys);
//            keys = this.webglVendorAndRendererKey(keys);
//            keys = this.adBlockKey(keys);
//            keys = this.hasLiedLanguagesKey(keys);
//            keys = this.hasLiedResolutionKey(keys);
//            keys = this.hasLiedOsKey(keys);
//            keys = this.hasLiedBrowserKey(keys);
//            keys = this.customEntropyFunction(keys);
            this.fontsKey(keys, function(newKeys) {
                var values = [];
                that.each(newKeys.data, function(pair) {
                    var value = pair.value;
                    if (value && typeof value.join === 'function') {
                        value = value.join(';')
                    }
                    values.push(value)
                });
                var murmur = that.x64hash128(values.join('~~~'), 31);
                return done(murmur, newKeys.data)
            })
        },
        customEntropyFunction: function(keys) {
            if (typeof this.options.customFunction === 'function') {
                keys.addPreprocessedComponent({
                    key: 'custom',
                    value: this.options.customFunction()
                })
            }
            return keys
        },
        userAgentKey: function(keys) {
            if (!this.options.excludeUserAgent) {
                keys.addPreprocessedComponent({
                    key: 'user_agent',
                    value: this.getUserAgent()
                })
            }
            return keys
        },
        getUserAgent: function() {
            return navigator.userAgent
        },
        languageKey: function(keys) {
            if (!this.options.excludeLanguage) {
                keys.addPreprocessedComponent({
                    key: 'language',
                    value: navigator.language || navigator.userLanguage || navigator.browserLanguage || navigator.systemLanguage || ''
                })
            }
            return keys
        },
        colorDepthKey: function(keys) {
            if (!this.options.excludeColorDepth) {
                keys.addPreprocessedComponent({
                    key: 'color_depth',
                    value: window.screen.colorDepth || -1
                })
            }
            return keys
        },
        deviceMemoryKey: function(keys) {
            if (!this.options.excludeDeviceMemory) {
                keys.addPreprocessedComponent({
                    key: 'device_memory',
                    value: this.getDeviceMemory()
                })
            }
            return keys
        },
        getDeviceMemory: function() {
            return navigator.deviceMemory || -1
        },
        pixelRatioKey: function(keys) {
            if (!this.options.excludePixelRatio) {
                keys.addPreprocessedComponent({
                    key: 'pixel_ratio',
                    value: this.getPixelRatio()
                })
            }
            return keys
        },
        getPixelRatio: function() {
            return window.devicePixelRatio || ''
        },
        screenResolutionKey: function(keys) {
            if (!this.options.excludeScreenResolution) {
                return this.getScreenResolution(keys)
            }
            return keys
        },
        getScreenResolution: function(keys) {
            var resolution;
            if (this.options.detectScreenOrientation) {
                resolution = (window.screen.height > window.screen.width) ? [window.screen.height, window.screen.width] : [window.screen.width, window.screen.height]
            } else {
                resolution = [window.screen.width, window.screen.height]
            }
            keys.addPreprocessedComponent({
                key: 'resolution',
                value: resolution
            });
            return keys
        },
        availableScreenResolutionKey: function(keys) {
            if (!this.options.excludeAvailableScreenResolution) {
                return this.getAvailableScreenResolution(keys)
            }
            return keys
        },
        getAvailableScreenResolution: function(keys) {
            var available;
            if (window.screen.availWidth && window.screen.availHeight) {
                if (this.options.detectScreenOrientation) {
                    available = (window.screen.availHeight > window.screen.availWidth) ? [window.screen.availHeight, window.screen.availWidth] : [window.screen.availWidth, window.screen.availHeight]
                } else {
                    available = [window.screen.availHeight, window.screen.availWidth]
                }
            }
            if (typeof available !== 'undefined') {
                keys.addPreprocessedComponent({
                    key: 'available_resolution',
                    value: available
                })
            }
            return keys
        },
        timezoneOffsetKey: function(keys) {
            if (!this.options.excludeTimezoneOffset) {
                keys.addPreprocessedComponent({
                    key: 'timezone_offset',
                    value: new Date().getTimezoneOffset()
                })
            }
            return keys
        },
        sessionStorageKey: function(keys) {
            if (!this.options.excludeSessionStorage && this.hasSessionStorage()) {
                keys.addPreprocessedComponent({
                    key: 'session_storage',
                    value: 1
                })
            }
            return keys
        },
        localStorageKey: function(keys) {
            if (!this.options.excludeSessionStorage && this.hasLocalStorage()) {
                keys.addPreprocessedComponent({
                    key: 'local_storage',
                    value: 1
                })
            }
            return keys
        },
        indexedDbKey: function(keys) {
            if (!this.options.excludeIndexedDB && this.hasIndexedDB()) {
                keys.addPreprocessedComponent({
                    key: 'indexed_db',
                    value: 1
                })
            }
            return keys
        },
        addBehaviorKey: function(keys) {
            if (!this.options.excludeAddBehavior && document.body && document.body.addBehavior) {
                keys.addPreprocessedComponent({
                    key: 'add_behavior',
                    value: 1
                })
            }
            return keys
        },
        openDatabaseKey: function(keys) {
            if (!this.options.excludeOpenDatabase && window.openDatabase) {
                keys.addPreprocessedComponent({
                    key: 'open_database',
                    value: 1
                })
            }
            return keys
        },
        cpuClassKey: function(keys) {
            if (!this.options.excludeCpuClass) {
                keys.addPreprocessedComponent({
                    key: 'cpu_class',
                    value: this.getNavigatorCpuClass()
                })
            }
            return keys
        },
        platformKey: function(keys) {
            if (!this.options.excludePlatform) {
                keys.addPreprocessedComponent({
                    key: 'navigator_platform',
                    value: this.getNavigatorPlatform()
                })
            }
            return keys
        },
        doNotTrackKey: function(keys) {
            if (!this.options.excludeDoNotTrack) {
                keys.addPreprocessedComponent({
                    key: 'do_not_track',
                    value: this.getDoNotTrack()
                })
            }
            return keys
        },
        canvasKey: function(keys) {
            if (!this.options.excludeCanvas && this.isCanvasSupported()) {
                keys.addPreprocessedComponent({
                    key: 'canvas',
                    value: this.getCanvasFp()
                })
            }
            return keys
        },
        webglKey: function(keys) {
            if (!this.options.excludeWebGL && this.isWebGlSupported()) {
                keys.addPreprocessedComponent({
                    key: 'webgl',
                    value: this.getWebglFp()
                })
            }
            return keys
        },
        webglVendorAndRendererKey: function(keys) {
            if (!this.options.excludeWebGLVendorAndRenderer && this.isWebGlSupported()) {
                keys.addPreprocessedComponent({
                    key: 'webgl_vendor',
                    value: this.getWebglVendorAndRenderer()
                })
            }
            return keys
        },
        adBlockKey: function(keys) {
            if (!this.options.excludeAdBlock) {
                keys.addPreprocessedComponent({
                    key: 'adblock',
                    value: this.getAdBlock()
                })
            }
            return keys
        },
        hasLiedLanguagesKey: function(keys) {
            if (!this.options.excludeHasLiedLanguages) {
                keys.addPreprocessedComponent({
                    key: 'has_lied_languages',
                    value: this.getHasLiedLanguages()
                })
            }
            return keys
        },
        hasLiedResolutionKey: function(keys) {
            if (!this.options.excludeHasLiedResolution) {
                keys.addPreprocessedComponent({
                    key: 'has_lied_resolution',
                    value: this.getHasLiedResolution()
                })
            }
            return keys
        },
        hasLiedOsKey: function(keys) {
            if (!this.options.excludeHasLiedOs) {
                keys.addPreprocessedComponent({
                    key: 'has_lied_os',
                    value: this.getHasLiedOs()
                })
            }
            return keys
        },
        hasLiedBrowserKey: function(keys) {
            if (!this.options.excludeHasLiedBrowser) {
                keys.addPreprocessedComponent({
                    key: 'has_lied_browser',
                    value: this.getHasLiedBrowser()
                })
            }
            return keys
        },
        fontsKey: function(keys, done) {
            return this.jsFontsKey(keys, done)
        },
        jsFontsKey: function(keys, done) {
            var that = this;
            return setTimeout(function() {
                var baseFonts = ['monospace', 'sans-serif', 'serif'];
                var fontList = ['Arial'];
                fontList = fontList.concat(that.options.userDefinedFonts);
                var testString = 'mmmmmmmmmmlli';
                var testSize = '72px';
                var h = document.getElementsByTagName('body')[0];
                var baseFontsDiv = document.createElement('div');
                var fontsDiv = document.createElement('div');
                var defaultWidth = {};
                var defaultHeight = {};
                var createSpan = function() {
                    var s = document.createElement('span');
                    s.style.position = 'absolute';
                    s.style.left = '-9999px';
                    s.style.fontSize = testSize;
                    s.style.lineHeight = 'normal';
                    s.innerHTML = testString;
                    return s
                };
                var createSpanWithFonts = function(fontToDetect, baseFont) {
                    var s = createSpan();
                    s.style.fontFamily = "'" + fontToDetect + "'," + baseFont;
                    return s
                };
                var initializeBaseFontsSpans = function() {
                    var spans = [];
                    for (var index = 0, length = baseFonts.length; index < length; index++) {
                        var s = createSpan();
                        s.style.fontFamily = baseFonts[index];
                        baseFontsDiv.appendChild(s);
                        spans.push(s)
                    }
                    return spans
                };
                var initializeFontsSpans = function() {
                    var spans = {};
                    for (var i = 0, l = fontList.length; i < l; i++) {
                        var fontSpans = [];
                        for (var j = 0, numDefaultFonts = baseFonts.length; j < numDefaultFonts; j++) {
                            var s = createSpanWithFonts(fontList[i], baseFonts[j]);
                            fontsDiv.appendChild(s);
                            fontSpans.push(s)
                        }
                        spans[fontList[i]] = fontSpans
                    }
                    return spans
                };
                var isFontAvailable = function(fontSpans) {
                    var detected = false;
                    for (var i = 0; i < baseFonts.length; i++) {
                        detected = (fontSpans[i].offsetWidth !== defaultWidth[baseFonts[i]] || fontSpans[i].offsetHeight !== defaultHeight[baseFonts[i]]);
                        if (detected) {
                            return detected
                        }
                    }
                    return detected
                };
                var baseFontsSpans = initializeBaseFontsSpans();
                h.appendChild(baseFontsDiv);
                for (var index = 0, length = baseFonts.length; index < length; index++) {
                    defaultWidth[baseFonts[index]] = baseFontsSpans[index].offsetWidth;
                    defaultHeight[baseFonts[index]] = baseFontsSpans[index].offsetHeight
                }
                var fontsSpans = initializeFontsSpans();
                h.appendChild(fontsDiv);
                var available = [];
                for (var i = 0, l = fontList.length; i < l; i++) {
                    if (isFontAvailable(fontsSpans[fontList[i]])) {
                        available.push(fontList[i])
                    }
                }
                h.removeChild(fontsDiv);
                h.removeChild(baseFontsDiv);
                keys.addPreprocessedComponent({
                    key: 'js_fonts',
                    value: available
                });
                done(keys)
            }, 1)
        },
        pluginsKey: function(keys) {
            if (!this.options.excludePlugins) {
                if (this.isIE()) {
                    if (!this.options.excludeIEPlugins) {
                        keys.addPreprocessedComponent({
                            key: 'ie_plugins',
                            value: this.getIEPlugins()
                        })
                    }
                } else {
                    keys.addPreprocessedComponent({
                        key: 'regular_plugins',
                        value: this.getRegularPlugins()
                    })
                }
            }
            return keys
        },
        getRegularPlugins: function() {
            var plugins = [];
            if (navigator.plugins) {
                for (var i = 0, l = navigator.plugins.length; i < l; i++) {
                    if (navigator.plugins[i]) {
                        plugins.push(navigator.plugins[i])
                    }
                }
            }
            if (this.pluginsShouldBeSorted()) {
                plugins = plugins.sort(function(a, b) {
                    if (a.name > b.name) {
                        return 1
                    }
                    if (a.name < b.name) {
                        return -1
                    }
                    return 0
                })
            }
            return this.map(plugins, function(p) {
                var mimeTypes = this.map(p, function(mt) {
                    return [mt.type, mt.suffixes].join('~')
                }).join(',');
                return [p.name, p.description, mimeTypes].join('::')
            }, this)
        },
        getIEPlugins: function() {
            var result = [];
            if ((Object.getOwnPropertyDescriptor && Object.getOwnPropertyDescriptor(window, 'ActiveXObject')) || ('ActiveXObject' in window)) {
                var names = ['AcroPDF.PDF', 'Adodb.Stream', 'AgControl.AgControl', 'DevalVRXCtrl.DevalVRXCtrl.1', 'MacromediaFlashPaper.MacromediaFlashPaper', 'Msxml2.DOMDocument', 'Msxml2.XMLHTTP', 'PDF.PdfCtrl', 'QuickTime.QuickTime', 'QuickTimeCheckObject.QuickTimeCheck.1', 'RealPlayer', 'RealPlayer.RealPlayer(tm) ActiveX Control (32-bit)', 'RealVideo.RealVideo(tm) ActiveX Control (32-bit)', 'Scripting.Dictionary', 'SWCtl.SWCtl', 'Shell.UIHelper', 'ShockwaveFlash.ShockwaveFlash', 'Skype.Detection', 'TDCCtl.TDCCtl', 'WMPlayer.OCX', 'rmocx.RealPlayer G2 Control', 'rmocx.RealPlayer G2 Control.1'];
                result = this.map(names, function(name) {
                    try {
                        new window.ActiveXObject(name);
                        return name
                    } catch (e) {
                        return null
                    }
                })
            }
            if (navigator.plugins) {
                result = result.concat(this.getRegularPlugins())
            }
            return result
        },
        pluginsShouldBeSorted: function() {
            var should = false;
            for (var i = 0, l = this.options.sortPluginsFor.length; i < l; i++) {
                var re = this.options.sortPluginsFor[i];
                if (navigator.userAgent.match(re)) {
                    should = true;
                    break
                }
            }
            return should
        },
        touchSupportKey: function(keys) {
            if (!this.options.excludeTouchSupport) {
                keys.addPreprocessedComponent({
                    key: 'touch_support',
                    value: this.getTouchSupport()
                })
            }
            return keys
        },
        hardwareConcurrencyKey: function(keys) {
            if (!this.options.excludeHardwareConcurrency) {
                keys.addPreprocessedComponent({
                    key: 'hardware_concurrency',
                    value: this.getHardwareConcurrency()
                })
            }
            return keys
        },
        hasSessionStorage: function() {
            try {
                return !!window.sessionStorage
            } catch (e) {
                return true
            }
        },
        hasLocalStorage: function() {
            try {
                return !!window.localStorage
            } catch (e) {
                return true
            }
        },
        hasIndexedDB: function() {
            try {
                return !!window.indexedDB
            } catch (e) {
                return true
            }
        },
        getHardwareConcurrency: function() {
            if (navigator.hardwareConcurrency) {
                return navigator.hardwareConcurrency
            }
            return 'unknown'
        },
        getNavigatorCpuClass: function() {
            if (navigator.cpuClass) {
                return navigator.cpuClass
            } else {
                return 'unknown'
            }
        },
        getNavigatorPlatform: function() {
            if (navigator.platform) {
                return navigator.platform
            } else {
                return 'unknown'
            }
        },
        getDoNotTrack: function() {
            if (navigator.doNotTrack) {
                return navigator.doNotTrack
            } else if (navigator.msDoNotTrack) {
                return navigator.msDoNotTrack
            } else if (window.doNotTrack) {
                return window.doNotTrack
            } else {
                return 'unknown'
            }
        },
        getCanvasFp: function() {
            var result = [];
            var canvas = document.createElement('canvas');
            canvas.width = 2000;
            canvas.height = 200;
            canvas.style.display = 'inline';
            var ctx = canvas.getContext('2d');
            ctx.rect(0, 0, 10, 10);
            ctx.rect(2, 2, 6, 6);
            result.push('canvas winding:' + ((ctx.isPointInPath(5, 5, 'evenodd') === false) ? 'yes' : 'no'));
            ctx.textBaseline = 'alphabetic';
            ctx.fillStyle = '#f60';
            ctx.fillRect(125, 1, 62, 20);
            ctx.fillStyle = '#069';
            if (this.options.dontUseFakeFontInCanvas) {
                ctx.font = '11pt Arial'
            } else {
                ctx.font = '11pt no-real-font-123'
            }
            ctx.fillText('Cwm fjordbank glyphs vext quiz, \ud83d\ude03', 2, 15);
            ctx.fillStyle = 'rgba(102, 204, 0, 0.2)';
            ctx.font = '18pt Arial';
            ctx.fillText('Cwm fjordbank glyphs vext quiz, \ud83d\ude03', 4, 45);
            ctx.globalCompositeOperation = 'multiply';
            ctx.fillStyle = 'rgb(255,0,255)';
            ctx.beginPath();
            ctx.arc(50, 50, 50, 0, Math.PI * 2, true);
            ctx.closePath();
            ctx.fill();
            ctx.fillStyle = 'rgb(0,255,255)';
            ctx.beginPath();
            ctx.arc(100, 50, 50, 0, Math.PI * 2, true);
            ctx.closePath();
            ctx.fill();
            ctx.fillStyle = 'rgb(255,255,0)';
            ctx.beginPath();
            ctx.arc(75, 100, 50, 0, Math.PI * 2, true);
            ctx.closePath();
            ctx.fill();
            ctx.fillStyle = 'rgb(255,0,255)';
            ctx.arc(75, 75, 75, 0, Math.PI * 2, true);
            ctx.arc(75, 75, 25, 0, Math.PI * 2, true);
            ctx.fill('evenodd');
            if (canvas.toDataURL) {
                result.push('canvas fp:' + canvas.toDataURL())
            }
            return result.join('~')
        },
        getWebglFp: function() {
            var gl;
            var fa2s = function(fa) {
                gl.clearColor(0.0, 0.0, 0.0, 1.0);
                gl.enable(gl.DEPTH_TEST);
                gl.depthFunc(gl.LEQUAL);
                gl.clear(gl.COLOR_BUFFER_BIT | gl.DEPTH_BUFFER_BIT);
                return '[' + fa[0] + ', ' + fa[1] + ']'
            };
            var maxAnisotropy = function(gl) {
                var ext = gl.getExtension('EXT_texture_filter_anisotropic') || gl.getExtension('WEBKIT_EXT_texture_filter_anisotropic') || gl.getExtension('MOZ_EXT_texture_filter_anisotropic');
                if (ext) {
                    var anisotropy = gl.getParameter(ext.MAX_TEXTURE_MAX_ANISOTROPY_EXT);
                    if (anisotropy === 0) {
                        anisotropy = 2
                    }
                    return anisotropy
                } else {
                    return null
                }
            };
            gl = this.getWebglCanvas();
            if (!gl) {
                return null
            }
            var result = [];
            var vShaderTemplate = 'attribute vec2 attrVertex;varying vec2 varyinTexCoordinate;uniform vec2 uniformOffset;void main(){varyinTexCoordinate=attrVertex+uniformOffset;gl_Position=vec4(attrVertex,0,1);}';
            var fShaderTemplate = 'precision mediump float;varying vec2 varyinTexCoordinate;void main() {gl_FragColor=vec4(varyinTexCoordinate,0,1);}';
            var vertexPosBuffer = gl.createBuffer();
            gl.bindBuffer(gl.ARRAY_BUFFER, vertexPosBuffer);
            var vertices = new Float32Array([-0.2, -0.9, 0, 0.4, -0.26, 0, 0, 0.732134444, 0]);
            gl.bufferData(gl.ARRAY_BUFFER, vertices, gl.STATIC_DRAW);
            vertexPosBuffer.itemSize = 3;
            vertexPosBuffer.numItems = 3;
            var program = gl.createProgram();
            var vshader = gl.createShader(gl.VERTEX_SHADER);
            gl.shaderSource(vshader, vShaderTemplate);
            gl.compileShader(vshader);
            var fshader = gl.createShader(gl.FRAGMENT_SHADER);
            gl.shaderSource(fshader, fShaderTemplate);
            gl.compileShader(fshader);
            gl.attachShader(program, vshader);
            gl.attachShader(program, fshader);
            gl.linkProgram(program);
            gl.useProgram(program);
            program.vertexPosAttrib = gl.getAttribLocation(program, 'attrVertex');
            program.offsetUniform = gl.getUniformLocation(program, 'uniformOffset');
            gl.enableVertexAttribArray(program.vertexPosArray);
            gl.vertexAttribPointer(program.vertexPosAttrib, vertexPosBuffer.itemSize, gl.FLOAT, !1, 0, 0);
            gl.uniform2f(program.offsetUniform, 1, 1);
            gl.drawArrays(gl.TRIANGLE_STRIP, 0, vertexPosBuffer.numItems);
            try {
                result.push(gl.canvas.toDataURL())
            } catch (e) {}
            result.push('extensions:' + (gl.getSupportedExtensions() || []).join(';'));
            result.push('webgl aliased line width range:' + fa2s(gl.getParameter(gl.ALIASED_LINE_WIDTH_RANGE)));
            result.push('webgl aliased point size range:' + fa2s(gl.getParameter(gl.ALIASED_POINT_SIZE_RANGE)));
            result.push('webgl alpha bits:' + gl.getParameter(gl.ALPHA_BITS));
            result.push('webgl antialiasing:' + (gl.getContextAttributes().antialias ? 'yes' : 'no'));
            result.push('webgl blue bits:' + gl.getParameter(gl.BLUE_BITS));
            result.push('webgl depth bits:' + gl.getParameter(gl.DEPTH_BITS));
            result.push('webgl green bits:' + gl.getParameter(gl.GREEN_BITS));
            result.push('webgl max anisotropy:' + maxAnisotropy(gl));
            result.push('webgl max combined texture image units:' + gl.getParameter(gl.MAX_COMBINED_TEXTURE_IMAGE_UNITS));
            result.push('webgl max cube map texture size:' + gl.getParameter(gl.MAX_CUBE_MAP_TEXTURE_SIZE));
            result.push('webgl max fragment uniform vectors:' + gl.getParameter(gl.MAX_FRAGMENT_UNIFORM_VECTORS));
            result.push('webgl max render buffer size:' + gl.getParameter(gl.MAX_RENDERBUFFER_SIZE));
            result.push('webgl max texture image units:' + gl.getParameter(gl.MAX_TEXTURE_IMAGE_UNITS));
            result.push('webgl max texture size:' + gl.getParameter(gl.MAX_TEXTURE_SIZE));
            result.push('webgl max varying vectors:' + gl.getParameter(gl.MAX_VARYING_VECTORS));
            result.push('webgl max vertex attribs:' + gl.getParameter(gl.MAX_VERTEX_ATTRIBS));
            result.push('webgl max vertex texture image units:' + gl.getParameter(gl.MAX_VERTEX_TEXTURE_IMAGE_UNITS));
            result.push('webgl max vertex uniform vectors:' + gl.getParameter(gl.MAX_VERTEX_UNIFORM_VECTORS));
            result.push('webgl max viewport dims:' + fa2s(gl.getParameter(gl.MAX_VIEWPORT_DIMS)));
            result.push('webgl red bits:' + gl.getParameter(gl.RED_BITS));
            result.push('webgl renderer:' + gl.getParameter(gl.RENDERER));
            result.push('webgl shading language version:' + gl.getParameter(gl.SHADING_LANGUAGE_VERSION));
            result.push('webgl stencil bits:' + gl.getParameter(gl.STENCIL_BITS));
            result.push('webgl vendor:' + gl.getParameter(gl.VENDOR));
            result.push('webgl version:' + gl.getParameter(gl.VERSION));
            try {
                var extensionDebugRendererInfo = gl.getExtension('WEBGL_debug_renderer_info');
                if (extensionDebugRendererInfo) {
                    result.push('webgl unmasked vendor:' + gl.getParameter(extensionDebugRendererInfo.UNMASKED_VENDOR_WEBGL));
                    result.push('webgl unmasked renderer:' + gl.getParameter(extensionDebugRendererInfo.UNMASKED_RENDERER_WEBGL))
                }
            } catch (e) {}
            if (!gl.getShaderPrecisionFormat) {
                return result.join('~')
            }
            result.push('webgl vertex shader high float precision:' + gl.getShaderPrecisionFormat(gl.VERTEX_SHADER, gl.HIGH_FLOAT).precision);
            result.push('webgl vertex shader high float precision rangeMin:' + gl.getShaderPrecisionFormat(gl.VERTEX_SHADER, gl.HIGH_FLOAT).rangeMin);
            result.push('webgl vertex shader high float precision rangeMax:' + gl.getShaderPrecisionFormat(gl.VERTEX_SHADER, gl.HIGH_FLOAT).rangeMax);
            result.push('webgl vertex shader medium float precision:' + gl.getShaderPrecisionFormat(gl.VERTEX_SHADER, gl.MEDIUM_FLOAT).precision);
            result.push('webgl vertex shader medium float precision rangeMin:' + gl.getShaderPrecisionFormat(gl.VERTEX_SHADER, gl.MEDIUM_FLOAT).rangeMin);
            result.push('webgl vertex shader medium float precision rangeMax:' + gl.getShaderPrecisionFormat(gl.VERTEX_SHADER, gl.MEDIUM_FLOAT).rangeMax);
            result.push('webgl vertex shader low float precision:' + gl.getShaderPrecisionFormat(gl.VERTEX_SHADER, gl.LOW_FLOAT).precision);
            result.push('webgl vertex shader low float precision rangeMin:' + gl.getShaderPrecisionFormat(gl.VERTEX_SHADER, gl.LOW_FLOAT).rangeMin);
            result.push('webgl vertex shader low float precision rangeMax:' + gl.getShaderPrecisionFormat(gl.VERTEX_SHADER, gl.LOW_FLOAT).rangeMax);
            result.push('webgl fragment shader high float precision:' + gl.getShaderPrecisionFormat(gl.FRAGMENT_SHADER, gl.HIGH_FLOAT).precision);
            result.push('webgl fragment shader high float precision rangeMin:' + gl.getShaderPrecisionFormat(gl.FRAGMENT_SHADER, gl.HIGH_FLOAT).rangeMin);
            result.push('webgl fragment shader high float precision rangeMax:' + gl.getShaderPrecisionFormat(gl.FRAGMENT_SHADER, gl.HIGH_FLOAT).rangeMax);
            result.push('webgl fragment shader medium float precision:' + gl.getShaderPrecisionFormat(gl.FRAGMENT_SHADER, gl.MEDIUM_FLOAT).precision);
            result.push('webgl fragment shader medium float precision rangeMin:' + gl.getShaderPrecisionFormat(gl.FRAGMENT_SHADER, gl.MEDIUM_FLOAT).rangeMin);
            result.push('webgl fragment shader medium float precision rangeMax:' + gl.getShaderPrecisionFormat(gl.FRAGMENT_SHADER, gl.MEDIUM_FLOAT).rangeMax);
            result.push('webgl fragment shader low float precision:' + gl.getShaderPrecisionFormat(gl.FRAGMENT_SHADER, gl.LOW_FLOAT).precision);
            result.push('webgl fragment shader low float precision rangeMin:' + gl.getShaderPrecisionFormat(gl.FRAGMENT_SHADER, gl.LOW_FLOAT).rangeMin);
            result.push('webgl fragment shader low float precision rangeMax:' + gl.getShaderPrecisionFormat(gl.FRAGMENT_SHADER, gl.LOW_FLOAT).rangeMax);
            result.push('webgl vertex shader high int precision:' + gl.getShaderPrecisionFormat(gl.VERTEX_SHADER, gl.HIGH_INT).precision);
            result.push('webgl vertex shader high int precision rangeMin:' + gl.getShaderPrecisionFormat(gl.VERTEX_SHADER, gl.HIGH_INT).rangeMin);
            result.push('webgl vertex shader high int precision rangeMax:' + gl.getShaderPrecisionFormat(gl.VERTEX_SHADER, gl.HIGH_INT).rangeMax);
            result.push('webgl vertex shader medium int precision:' + gl.getShaderPrecisionFormat(gl.VERTEX_SHADER, gl.MEDIUM_INT).precision);
            result.push('webgl vertex shader medium int precision rangeMin:' + gl.getShaderPrecisionFormat(gl.VERTEX_SHADER, gl.MEDIUM_INT).rangeMin);
            result.push('webgl vertex shader medium int precision rangeMax:' + gl.getShaderPrecisionFormat(gl.VERTEX_SHADER, gl.MEDIUM_INT).rangeMax);
            result.push('webgl vertex shader low int precision:' + gl.getShaderPrecisionFormat(gl.VERTEX_SHADER, gl.LOW_INT).precision);
            result.push('webgl vertex shader low int precision rangeMin:' + gl.getShaderPrecisionFormat(gl.VERTEX_SHADER, gl.LOW_INT).rangeMin);
            result.push('webgl vertex shader low int precision rangeMax:' + gl.getShaderPrecisionFormat(gl.VERTEX_SHADER, gl.LOW_INT).rangeMax);
            result.push('webgl fragment shader high int precision:' + gl.getShaderPrecisionFormat(gl.FRAGMENT_SHADER, gl.HIGH_INT).precision);
            result.push('webgl fragment shader high int precision rangeMin:' + gl.getShaderPrecisionFormat(gl.FRAGMENT_SHADER, gl.HIGH_INT).rangeMin);
            result.push('webgl fragment shader high int precision rangeMax:' + gl.getShaderPrecisionFormat(gl.FRAGMENT_SHADER, gl.HIGH_INT).rangeMax);
            result.push('webgl fragment shader medium int precision:' + gl.getShaderPrecisionFormat(gl.FRAGMENT_SHADER, gl.MEDIUM_INT).precision);
            result.push('webgl fragment shader medium int precision rangeMin:' + gl.getShaderPrecisionFormat(gl.FRAGMENT_SHADER, gl.MEDIUM_INT).rangeMin);
            result.push('webgl fragment shader medium int precision rangeMax:' + gl.getShaderPrecisionFormat(gl.FRAGMENT_SHADER, gl.MEDIUM_INT).rangeMax);
            result.push('webgl fragment shader low int precision:' + gl.getShaderPrecisionFormat(gl.FRAGMENT_SHADER, gl.LOW_INT).precision);
            result.push('webgl fragment shader low int precision rangeMin:' + gl.getShaderPrecisionFormat(gl.FRAGMENT_SHADER, gl.LOW_INT).rangeMin);
            result.push('webgl fragment shader low int precision rangeMax:' + gl.getShaderPrecisionFormat(gl.FRAGMENT_SHADER, gl.LOW_INT).rangeMax);
            return result.join('~')
        },
        getWebglVendorAndRenderer: function() {
            try {
                var glContext = this.getWebglCanvas();
                var extensionDebugRendererInfo = glContext.getExtension('WEBGL_debug_renderer_info');
                return glContext.getParameter(extensionDebugRendererInfo.UNMASKED_VENDOR_WEBGL) + '~' + glContext.getParameter(extensionDebugRendererInfo.UNMASKED_RENDERER_WEBGL)
            } catch (e) {
                return null
            }
        },
        getAdBlock: function() {
            var ads = document.createElement('div');
            ads.innerHTML = ' ';
            ads.className = 'adsbox';
            var result = false;
            try {
                document.body.appendChild(ads);
                result = document.getElementsByClassName('adsbox')[0].offsetHeight === 0;
                document.body.removeChild(ads)
            } catch (e) {
                result = false
            }
            return result
        },
        getHasLiedLanguages: function() {
            if (typeof navigator.languages !== 'undefined') {
                try {
                    var firstLanguages = navigator.languages[0].substr(0, 2);
                    if (firstLanguages !== navigator.language.substr(0, 2)) {
                        return true
                    }
                } catch (err) {
                    return true
                }
            }
            return false
        },
        getHasLiedResolution: function() {
            if (window.screen.width < window.screen.availWidth) {
                return true
            }
            if (window.screen.height < window.screen.availHeight) {
                return true
            }
            return false
        },
        getHasLiedOs: function() {
            var userAgent = navigator.userAgent.toLowerCase();
            var oscpu = navigator.oscpu;
            var platform = navigator.platform.toLowerCase();
            var os;
            if (userAgent.indexOf('windows phone') >= 0) {
                os = 'Windows Phone'
            } else if (userAgent.indexOf('win') >= 0) {
                os = 'Windows'
            } else if (userAgent.indexOf('android') >= 0) {
                os = 'Android'
            } else if (userAgent.indexOf('linux') >= 0) {
                os = 'Linux'
            } else if (userAgent.indexOf('iphone') >= 0 || userAgent.indexOf('ipad') >= 0) {
                os = 'iOS'
            } else if (userAgent.indexOf('mac') >= 0) {
                os = 'Mac'
            } else {
                os = 'Other'
            }
            var mobileDevice;
            if (('ontouchstart' in window) || (navigator.maxTouchPoints > 0) || (navigator.msMaxTouchPoints > 0)) {
                mobileDevice = true
            } else {
                mobileDevice = false
            } if (mobileDevice && os !== 'Windows Phone' && os !== 'Android' && os !== 'iOS' && os !== 'Other') {
                return true
            }
            if (typeof oscpu !== 'undefined') {
                oscpu = oscpu.toLowerCase();
                if (oscpu.indexOf('win') >= 0 && os !== 'Windows' && os !== 'Windows Phone') {
                    return true
                } else if (oscpu.indexOf('linux') >= 0 && os !== 'Linux' && os !== 'Android') {
                    return true
                } else if (oscpu.indexOf('mac') >= 0 && os !== 'Mac' && os !== 'iOS') {
                    return true
                } else if ((oscpu.indexOf('win') === -1 && oscpu.indexOf('linux') === -1 && oscpu.indexOf('mac') === -1) !== (os === 'Other')) {
                    return true
                }
            }
            if (platform.indexOf('win') >= 0 && os !== 'Windows' && os !== 'Windows Phone') {
                return true
            } else if ((platform.indexOf('linux') >= 0 || platform.indexOf('android') >= 0 || platform.indexOf('pike') >= 0) && os !== 'Linux' && os !== 'Android') {
                return true
            } else if ((platform.indexOf('mac') >= 0 || platform.indexOf('ipad') >= 0 || platform.indexOf('ipod') >= 0 || platform.indexOf('iphone') >= 0) && os !== 'Mac' && os !== 'iOS') {
                return true
            } else if ((platform.indexOf('win') === -1 && platform.indexOf('linux') === -1 && platform.indexOf('mac') === -1) !== (os === 'Other')) {
                return true
            }
            if (typeof navigator.plugins === 'undefined' && os !== 'Windows' && os !== 'Windows Phone') {
                return true
            }
            return false
        },
        getHasLiedBrowser: function() {
            var userAgent = navigator.userAgent.toLowerCase();
            var productSub = navigator.productSub;
            var browser;
            if (userAgent.indexOf('firefox') >= 0) {
                browser = 'Firefox'
            } else if (userAgent.indexOf('opera') >= 0 || userAgent.indexOf('opr') >= 0) {
                browser = 'Opera'
            } else if (userAgent.indexOf('chrome') >= 0) {
                browser = 'Chrome'
            } else if (userAgent.indexOf('safari') >= 0) {
                browser = 'Safari'
            } else if (userAgent.indexOf('trident') >= 0) {
                browser = 'Internet Explorer'
            } else {
                browser = 'Other'
            } if ((browser === 'Chrome' || browser === 'Safari' || browser === 'Opera') && productSub !== '20030107') {
                return true
            }
            var tempRes = eval.toString().length;
            if (tempRes === 37 && browser !== 'Safari' && browser !== 'Firefox' && browser !== 'Other') {
                return true
            } else if (tempRes === 39 && browser !== 'Internet Explorer' && browser !== 'Other') {
                return true
            } else if (tempRes === 33 && browser !== 'Chrome' && browser !== 'Opera' && browser !== 'Other') {
                return true
            }
            var errFirefox;
            try {
                throw 'a'
            } catch (err) {
                try {
                    err.toSource();
                    errFirefox = true
                } catch (errOfErr) {
                    errFirefox = false
                }
            }
            if (errFirefox && browser !== 'Firefox' && browser !== 'Other') {
                return true
            }
            return false
        },
        isCanvasSupported: function() {
            var elem = document.createElement('canvas');
            return !!(elem.getContext && elem.getContext('2d'))
        },
        isWebGlSupported: function() {
            if (!this.isCanvasSupported()) {
                return false
            }
            var glContext = this.getWebglCanvas();
            return !!window.WebGLRenderingContext && !!glContext
        },
        isIE: function() {
            if (navigator.appName === 'Microsoft Internet Explorer') {
                return true
            } else if (navigator.appName === 'Netscape' && /Trident/.test(navigator.userAgent)) {
                return true
            }
            return false
        },
        hasSwfObjectLoaded: function() {
            return typeof window.swfobject !== 'undefined'
        },
        hasMinFlashInstalled: function() {
            return window.swfobject.hasFlashPlayerVersion('9.0.0')
        },
        addFlashDivNode: function() {
            var node = document.createElement('div');
            node.setAttribute('id', this.options.swfContainerId);
            document.body.appendChild(node)
        },
        loadSwfAndDetectFonts: function(done) {
            var hiddenCallback = '___fp_swf_loaded';
            window[hiddenCallback] = function(fonts) {
                done(fonts)
            };
            var id = this.options.swfContainerId;
            this.addFlashDivNode();
            var flashvars = {
                onReady: hiddenCallback
            };
            var flashparams = {
                allowScriptAccess: 'always',
                menu: 'false'
            };
            window.swfobject.embedSWF(this.options.swfPath, id, '1', '1', '9.0.0', false, flashvars, flashparams, {})
        },
        getWebglCanvas: function() {
            var canvas = document.createElement('canvas');
            var gl = null;
            try {
                gl = canvas.getContext('webgl') || canvas.getContext('experimental-webgl')
            } catch (e) {}
            if (!gl) {
                gl = null
            }
            return gl
        },
        each: function(obj, iterator, context) {
            if (obj === null) {
                return
            }
            if (this.nativeForEach && obj.forEach === this.nativeForEach) {
                obj.forEach(iterator, context)
            } else if (obj.length === +obj.length) {
                for (var i = 0, l = obj.length; i < l; i++) {
                    if (iterator.call(context, obj[i], i, obj) === {}) {
                        return
                    }
                }
            } else {
                for (var key in obj) {
                    if (obj.hasOwnProperty(key)) {
                        if (iterator.call(context, obj[key], key, obj) === {}) {
                            return
                        }
                    }
                }
            }
        },
        map: function(obj, iterator, context) {
            var results = [];
            if (obj == null) {
                return results
            }
            if (this.nativeMap && obj.map === this.nativeMap) {
                return obj.map(iterator, context)
            }
            this.each(obj, function(value, index, list) {
                results[results.length] = iterator.call(context, value, index, list)
            });
            return results
        },
        x64Add: function(m, n) {
            m = [m[0] >>> 16, m[0] & 0xffff, m[1] >>> 16, m[1] & 0xffff];
            n = [n[0] >>> 16, n[0] & 0xffff, n[1] >>> 16, n[1] & 0xffff];
            var o = [0, 0, 0, 0];
            o[3] += m[3] + n[3];
            o[2] += o[3] >>> 16;
            o[3] &= 0xffff;
            o[2] += m[2] + n[2];
            o[1] += o[2] >>> 16;
            o[2] &= 0xffff;
            o[1] += m[1] + n[1];
            o[0] += o[1] >>> 16;
            o[1] &= 0xffff;
            o[0] += m[0] + n[0];
            o[0] &= 0xffff;
            return [(o[0] << 16) | o[1], (o[2] << 16) | o[3]]
        },
        x64Multiply: function(m, n) {
            m = [m[0] >>> 16, m[0] & 0xffff, m[1] >>> 16, m[1] & 0xffff];
            n = [n[0] >>> 16, n[0] & 0xffff, n[1] >>> 16, n[1] & 0xffff];
            var o = [0, 0, 0, 0];
            o[3] += m[3] * n[3];
            o[2] += o[3] >>> 16;
            o[3] &= 0xffff;
            o[2] += m[2] * n[3];
            o[1] += o[2] >>> 16;
            o[2] &= 0xffff;
            o[2] += m[3] * n[2];
            o[1] += o[2] >>> 16;
            o[2] &= 0xffff;
            o[1] += m[1] * n[3];
            o[0] += o[1] >>> 16;
            o[1] &= 0xffff;
            o[1] += m[2] * n[2];
            o[0] += o[1] >>> 16;
            o[1] &= 0xffff;
            o[1] += m[3] * n[1];
            o[0] += o[1] >>> 16;
            o[1] &= 0xffff;
            o[0] += (m[0] * n[3]) + (m[1] * n[2]) + (m[2] * n[1]) + (m[3] * n[0]);
            o[0] &= 0xffff;
            return [(o[0] << 16) | o[1], (o[2] << 16) | o[3]]
        },
        x64Rotl: function(m, n) {
            n %= 64;
            if (n === 32) {
                return [m[1], m[0]]
            } else if (n < 32) {
                return [(m[0] << n) | (m[1] >>> (32 - n)), (m[1] << n) | (m[0] >>> (32 - n))]
            } else {
                n -= 32;
                return [(m[1] << n) | (m[0] >>> (32 - n)), (m[0] << n) | (m[1] >>> (32 - n))]
            }
        },
        x64LeftShift: function(m, n) {
            n %= 64;
            if (n === 0) {
                return m
            } else if (n < 32) {
                return [(m[0] << n) | (m[1] >>> (32 - n)), m[1] << n]
            } else {
                return [m[1] << (n - 32), 0]
            }
        },
        x64Xor: function(m, n) {
            return [m[0] ^ n[0], m[1] ^ n[1]]
        },
        x64Fmix: function(h) {
            h = this.x64Xor(h, [0, h[0] >>> 1]);
            h = this.x64Multiply(h, [0xff51afd7, 0xed558ccd]);
            h = this.x64Xor(h, [0, h[0] >>> 1]);
            h = this.x64Multiply(h, [0xc4ceb9fe, 0x1a85ec53]);
            h = this.x64Xor(h, [0, h[0] >>> 1]);
            return h
        },
        x64hash128: function(key, seed) {
            key = key || '';
            seed = seed || 0;
            var remainder = key.length % 16;
            var bytes = key.length - remainder;
            var h1 = [0, seed];
            var h2 = [0, seed];
            var k1 = [0, 0];
            var k2 = [0, 0];
            var c1 = [0x87c37b91, 0x114253d5];
            var c2 = [0x4cf5ad43, 0x2745937f];
            for (var i = 0; i < bytes; i = i + 16) {
                k1 = [((key.charCodeAt(i + 4) & 0xff)) | ((key.charCodeAt(i + 5) & 0xff) << 8) | ((key.charCodeAt(i + 6) & 0xff) << 16) | ((key.charCodeAt(i + 7) & 0xff) << 24), ((key.charCodeAt(i) & 0xff)) | ((key.charCodeAt(i + 1) & 0xff) << 8) | ((key.charCodeAt(i + 2) & 0xff) << 16) | ((key.charCodeAt(i + 3) & 0xff) << 24)];
                k2 = [((key.charCodeAt(i + 12) & 0xff)) | ((key.charCodeAt(i + 13) & 0xff) << 8) | ((key.charCodeAt(i + 14) & 0xff) << 16) | ((key.charCodeAt(i + 15) & 0xff) << 24), ((key.charCodeAt(i + 8) & 0xff)) | ((key.charCodeAt(i + 9) & 0xff) << 8) | ((key.charCodeAt(i + 10) & 0xff) << 16) | ((key.charCodeAt(i + 11) & 0xff) << 24)];
                k1 = this.x64Multiply(k1, c1);
                k1 = this.x64Rotl(k1, 31);
                k1 = this.x64Multiply(k1, c2);
                h1 = this.x64Xor(h1, k1);
                h1 = this.x64Rotl(h1, 27);
                h1 = this.x64Add(h1, h2);
                h1 = this.x64Add(this.x64Multiply(h1, [0, 5]), [0, 0x52dce729]);
                k2 = this.x64Multiply(k2, c2);
                k2 = this.x64Rotl(k2, 33);
                k2 = this.x64Multiply(k2, c1);
                h2 = this.x64Xor(h2, k2);
                h2 = this.x64Rotl(h2, 31);
                h2 = this.x64Add(h2, h1);
                h2 = this.x64Add(this.x64Multiply(h2, [0, 5]), [0, 0x38495ab5])
            };
            k1 = [0, 0];
            k2 = [0, 0];
            switch (remainder) {
                case 15:
                    k2 = this.x64Xor(k2, this.x64LeftShift([0, key.charCodeAt(i + 14)], 48));
                case 14:
                    k2 = this.x64Xor(k2, this.x64LeftShift([0, key.charCodeAt(i + 13)], 40));
                case 13:
                    k2 = this.x64Xor(k2, this.x64LeftShift([0, key.charCodeAt(i + 12)], 32));
                case 12:
                    k2 = this.x64Xor(k2, this.x64LeftShift([0, key.charCodeAt(i + 11)], 24));
                case 11:
                    k2 = this.x64Xor(k2, this.x64LeftShift([0, key.charCodeAt(i + 10)], 16));
                case 10:
                    k2 = this.x64Xor(k2, this.x64LeftShift([0, key.charCodeAt(i + 9)], 8));
                case 9:
                    k2 = this.x64Xor(k2, [0, key.charCodeAt(i + 8)]);
                    k2 = this.x64Multiply(k2, c2);
                    k2 = this.x64Rotl(k2, 33);
                    k2 = this.x64Multiply(k2, c1);
                    h2 = this.x64Xor(h2, k2);
                case 8:
                    k1 = this.x64Xor(k1, this.x64LeftShift([0, key.charCodeAt(i + 7)], 56));
                case 7:
                    k1 = this.x64Xor(k1, this.x64LeftShift([0, key.charCodeAt(i + 6)], 48));
                case 6:
                    k1 = this.x64Xor(k1, this.x64LeftShift([0, key.charCodeAt(i + 5)], 40));
                case 5:
                    k1 = this.x64Xor(k1, this.x64LeftShift([0, key.charCodeAt(i + 4)], 32));
                case 4:
                    k1 = this.x64Xor(k1, this.x64LeftShift([0, key.charCodeAt(i + 3)], 24));
                case 3:
                    k1 = this.x64Xor(k1, this.x64LeftShift([0, key.charCodeAt(i + 2)], 16));
                case 2:
                    k1 = this.x64Xor(k1, this.x64LeftShift([0, key.charCodeAt(i + 1)], 8));
                case 1:
                    k1 = this.x64Xor(k1, [0, key.charCodeAt(i)]);
                    k1 = this.x64Multiply(k1, c1);
                    k1 = this.x64Rotl(k1, 31);
                    k1 = this.x64Multiply(k1, c2);
                    h1 = this.x64Xor(h1, k1)
            };
            h1 = this.x64Xor(h1, [0, key.length]);
            h2 = this.x64Xor(h2, [0, key.length]);
            h1 = this.x64Add(h1, h2);
            h2 = this.x64Add(h2, h1);
            h1 = this.x64Fmix(h1);
            h2 = this.x64Fmix(h2);
            h1 = this.x64Add(h1, h2);
            h2 = this.x64Add(h2, h1);
            return (('00000000' + (h1[0] >>> 0).toString(16)).slice(-8) + ('00000000' + (h1[1] >>> 0).toString(16)).slice(-8) + ('00000000' + (h2[0] >>> 0).toString(16)).slice(-8) + ('00000000' + (h2[1] >>> 0).toString(16)).slice(-8))
        }
    };
    return PCHOMEFingerprint
})