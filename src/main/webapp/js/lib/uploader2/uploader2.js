/**
 * 一个基于 plupload (mOxie) 的上传工具 及 缩略图.
 *
 * @update 2016.06.13 增加兼容 阿里云 OSS 上传, 对上传流程修改为: 先获取策略配置, 然后进行上传(通过policy:true, 指定url为policy url)
 * @author vacoor
 * @version 0.3
 */
/*-
 * CHANGELOG:
 * --------------------------------------------------------------------------------------------------
 * 0.3 (2016.06.13):
 *   0. 删除 value 参数(用来指定表单值是url中哪个key)
 *   1. 删除 baseUrl 参数
 *   2. 修改 参数 thumbFn 为 thumb_fn, 参数从 [response_value, data, file, up] 修改为 [url, file, up]
 *   3. 增加兼容阿里云形式文件上传 (通过policy:true)
 *      3.1 增加 policy 参数, 用于指定 url 参数是否是预上传url(获取上传参数)
 *      3.2 policy=true 时, 上传文件的 key 由前台生成
 *      3.3 在保存文件路径为 plicy url 中返回的 file_host/image_host/download_host + key
 *   4. 移除 embed 方法, 统一使用 Uploader 构造器/工厂来创建.
 *   5. 增加 naming_strategy 参数用于指定命名方式(当 policy:true 有效).
 *   6. 增加 message_target 参数用于指定错误消息的提示位置.
 *   7. 其他样式优化等.
 *
 * --------------------------------------------------------------------------------------------------
 */
;
(function (factory) {
    if (typeof define === 'function' && define.amd) {
        // AMD. Register as an anonymous module.
        define(['jquery', 'plupload'], factory);
    } else if (typeof exports === 'object') {
        // Node/CommonJS style for Browserify
        module.exports = factory;
    } else {
        // Browser globals
        window.Uploader2 = factory(jQuery, plupload, mOxie);
    }
}(function ($, plupload, o) {
    o = o || mOxie;
    // 模板
    var ENTRY_TPL = '' +
        '<li class="upload-entry" id="{id}-entry">' +
        '  <div class="upload-thumb-wrap">' +
        '    <div class="upload-thumb"></div>' +
        '  </div>' +
        '  <div class="upload-entry-actions">' +
        '    <a href="javascript:void(0);" target="_blank" class="upload-download">下载</a>' +
        '    <a href="javascript:void(0);" class="upload-retry">重试</a>' +
        '    <a href="javascript:void(0);" class="upload-remove">删除</a>' +
        '  </div>' +
        '  <div class="upload-entry-name" title="{name}">{name}</div>' +
        '  <div class="upload-status">' +
        '    <div class="waiting-status">待上传,请稍后...</div>' +
        '    <div class="process-status">' +
        '      <div class="upload-progress">' +
        '        <span class="upload-progress-bar"><span class="upload-progress-bar-inner"></span></span>' +
        '        <span class="upload-progress-text">0%,  ??:??:??</span>' +
        '      </div>' +
        '    </div>' +
        '    <div class="success-status">上传成功</div>' +
        '    <div class="error-status">失败</div>' +
        '    <div class="saved-status">已存储</div>' +
        '  </div>' +
        '  <div class="upload-tip"></div>' +
        '</li>';
    var STATUS_CLASS = {
        WAITING: 'upload-waiting',          // 等待上传
        UPLOADING: 'upload-uploading',      // 上传中
        ERROR: 'upload-fail',                // 上传失败
        SUCCESS: 'upload-success',          // 上传成功
        SAVED: 'upload-saved',              // 历史上传
        DOWNLOAD: 'upload-complete'         // 上传完成
    };

    /**
     * plupload 默认配置
     */
    var CONTEXT_PATH = (window.contextPath || window.ctx || ''),
        FLASH_URL = CONTEXT_PATH + '/js/lib/plupload/Moxie.swf',
        SILVERLIGHT_URL = CONTEXT_PATH + '/js/lib/plupload/Moxie.xap',
        DEFAULTS = {
            runtimes: 'html5,flash,silverlight',
            flash_swf_url: FLASH_URL,               // flash 如果不配置, 无法使用 swf 上传.
            silverlight_xap_url: SILVERLIGHT_URL,
            file_data_name: 'file',                 // 文件上传 form element name, aliyun oss 必须为 file.
            url: undefined,                         // 文件上传 url
            multi_selection: true,                  // 允许一次选择多个文件
            browse_button: undefined,               // 浏览按钮(必须)
            drop_element: undefined,                // 拖放监听元素
            browse_button_active: 'active',         // 浏览按钮激活时 class
            browse_button_hover: 'hover',           // 浏览按钮 hover class
            filters: {
                mime_types: [{title: 'Allowd Files', extensions: '*'}]
            },
            // container: undefined,
            // multipart_params: {}                 // 文件上传的附加参数
            // 自定义参数
            extensions: undefined,      // 扩展名
            name: null,                 // 创建表单域名称
            queue: '',                  // 列表容器
            thumb: false,               // 是否生成缩略图
            max_file_count: '5',        // 最大文件数量
            max_file_size: '10M',       // 最大文件
            auto: true,                 // 自动开始
            remove_on_fail: false,      // 自动移除失败的文件
            download: true,             // 是否显示下载按钮
            naming_strategy: '',        // local 则使用原始文件名, 其他为自动生成.
            message_target: '',         // 错误消息容器 id
            thumb_fn: function (url, file, up) {
                return url;// + '_40x40';
            },
            /*init*/preinit: {
                PostInit: function (up) {
                    // 初始化历史(已保存的)上传
                    var settings = up.getOption(),
                        $queue = $(settings['queue']),
                        _files = up._files = up._files || [], idx;

                    o.each(up.getOption('browse_button'), function (btn) {
                        btn.uploader = up;  // 所有浏览按钮
                    });

                    // 拖拽
                    if (up.features.dragdrop) {
                        o.each(up.getOption('drop_element'), function (el) {
                            o.addEvent(el, 'dragenter', function (e) {
                                o.addClass(el, 'dragover');
                            });
                            o.addEvent(el, 'dragover', function (e) {
                                o.addClass(el, 'dragover');
                            });
                            o.addEvent(el, 'dragleave', function (e) {
                                o.removeClass(el, 'dragover');
                            });
                            o.addEvent(el, 'drop', function (e) {
                                o.removeClass(el, 'dragover');
                            });
                        });
                    }

                    /*-
                     * update 2015.12.08 修改默认的el元素为input为包含 data-saved-url 属性的元素
                     * 以便兼容附加表单项的提交, TODO 这个还不能这么搞, input 是隐藏
                     */
                    $queue.find('[data-saved-url]').each(function (i, el) {
                        //$queue.find('input[name="' + settings['name'] + '"]').each(function (i, el) {
                        var val = el.getAttribute('data-saved-url') || $(el).val(),
                            name = el.getAttribute('data-saved-name') || val;   // 保存的文件名称
                        _files.push({remote: true, url: val, name: name, el: el});
                    });
                    $queue.empty();
                    for (idx = 0; idx < _files.length; idx++) {
                        up.trigger("FileFiltered", _files[idx]);
                    }
                },
                FileFiltered: function (up, file) {
                    var queue = up.getOption('queue'),
                        auto = up.getOption('auto');

                    // 如果容器元素存在, 则添加 UI 元素
                    queue && queue.appendChild(createEntryEl(up, file));
                    // hash(file.getSource());

                    // 如果设置自动开始上传则启动上传
                    (1 == auto || true == auto) && up.start();
                },
                FilesRemoved: function (up, files) {
                    o.each(files, function (f) {
                        var dom = o.get(f.id + '-entry');
                        dom && dom.parentNode.removeChild(dom);
                    });
                },
                QueueChanged: function () {
                    var up = this,
                        maxCount = up.getOption('filters')['max_file_count'],
                        queueSize = up.files.length + (up._files || []).length,
                    // 如果最大允许数量大于1 且 文件数大于等于允许的最大值, 则禁止浏览
                        disabled = 1 < maxCount && queueSize >= maxCount;

                    up.disableBrowse(disabled);
                    o.each(up.getOption('browse_button'), function (el) {
                        el.disabled = disabled;
                        disabled ? o.addClass(el, 'disabled') : o.removeClass(el, 'disabled');
                    });
                    // TODO 优化这里
                    $('#' + up.getOption('message_target')).empty().removeClass('warn');
                },
                BeforeUpload: function (up, file) {
                    setStatus(file.id + '-entry', STATUS_CLASS.UPLOADING);
                    // TODO 优化这里
                    $('#' + up.getOption('message_target')).empty().removeClass('warn');
                },
                UploadProgress: function (up, file) {
                    // 上传进度
                    var $entry = $('#' + file.id + '-entry'),
                        $progress = $entry.find('.upload-progress'),
                        $tip = $entry.find('.upload-tip'),
                        now = new Date().getTime(),
                        time = now - (file.startTime || 0),
                        speed, remain;

                    file.startTime = file.startTime || now;

                    if (100 > file.percent) {
                        speed = file.loaded / time;
                        remain = Math.round((file.size - file.loaded) / speed);
                        speed = plupload.formatSize(Math.round(speed * 1E3)) + '/s';
                        remain = formatTime(Math.round(remain / 1E3));

                        $progress.find('.upload-progress-bar-inner').width(file.percent + '%');
                        $progress.find('.upload-progress-text').text(file.percent + '%, ' + remain);
                        $tip.html(file.name + ', ' + speed);
                    }
                },
                FileUploaded: function (up, file, result) { // 上传成功后
                    var name = up.getOption('name'),
                        multipartParams = up.getOption('multipart_params') || {},
                        url = up.getOption('url'),
                        download = up.getOption('download'),
                        thumb = up.getOption('thumb'),
                        assetUrl = multipartParams.asset_host || url,
                        imageUrl = multipartParams.image_host || url,
                        downloadUrl = multipartParams.download_host || url,
                        key = multipartParams.key,
                        $entry;

                    /*-
                     * @deprecated 兼容非策略上传模式.
                     */
                    if (result.response) {
                        key = $.parseJSON(result.response).url;
                        downloadUrl = assetUrl = imageUrl = CONTEXT_PATH;
                    }

                    if (key) {
                        key = key.replace('${filename}', encodeURIComponent(file.name));
                    }


                    $entry = $('#' + file.id + '-entry');
                    /*-
                     * 下载链接
                     */
                    downloadUrl = getUrl(downloadUrl, key);
                    // 更新状态
                    if (download && downloadUrl) {
                        setStatus($entry[0], [STATUS_CLASS.DOWNLOAD, STATUS_CLASS.SUCCESS]);
                        if (downloadUrl) {
                            $entry.find('.upload-download').attr('href', plupload.buildUrl(downloadUrl, {download: true}));
                        }
                    } else {
                        setStatus($entry[0], [STATUS_CLASS.SUCCESS]);
                    }

                    $entry.find('.upload-entry-status').html(plupload.formatSize(file.size) + ' 上传完成');

                    // 如果设置了名称且提供了值字段, 则创建相应隐藏域
                    if (name && key) {
                        $('<input type="hidden">').attr('name', name).val(key).appendTo($entry);
                    }

                    // 如果需要缩略图.
                    if (thumb) {
                        file.getSource() && (file.getSource().rendered = true);
                        url = up.getOption('thumb_fn').call(up, getUrl(imageUrl, key), file, up);
                        setEntryImage($entry[0], url);
                    }
                },
                Error: function onError(up, err) {
                    var $message = $('#' + up.getOption('message_target'));
                    if (plupload.INIT_ERROR == err.code) {
                        if (0 < $message.length) {
                            var h = $message.parent().height() + 'px';
                            console.log(h);
                            $message.addClass('error').append($('<span>').css('line-height', h).text(plupload.translate(err.message) || '组件无法加载.'));
                        }
                        console.log('Plupload init error', err);
                        return;
                    }
                    if (err.file) {
                        setStatus(err.file.id + '-entry', STATUS_CLASS.ERROR);
                        if (up.getOption('remove_on_fail')) {
                            up.removeFile(err.file);
                        }
                    }
                    if (0 < $message.length) {
                        $message.addClass('warn').text(plupload.translate(err.message) || '未知错误.');
                    } else {
                        alert(err.message);
                    }
                }
            }
        };

    function configure(up, file) {
        fetch(up, function (config) {
            var namingStrategy = up.getOption('naming_strategy'),
                filename = 'local' == namingStrategy ? /*'${filename}'*/ file.name : newFilename(file.name, 25),
                multipart_params = o.extend({}, config, {
                    key: config.dir + filename
                }),
                url = config.host;
            // 自适应 url
            if (/^\/\//.test(url)) {
                url = location.protocol + url;
            }
            up.setOption({url: url, multipart_params: multipart_params});
        }, false);
    }

    function newFilename(filename, len) {
        var chars = [
                /*- 48 进制.
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'h', 'i', 'j', 'k', 'm', 'n', 'p', 'r', 's', 't', 'w', 'x', 'y', 'z',
                '2', '3', '4', '5', '6', '7', '8'
                */
                /*- 36 */
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'W', 'X', 'Y', 'Z',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
            ],
            now = new Date().getTime(),
            val = now,
            result = '';
        len = len || 32;

        while (val >= chars.length) {
            result = chars[parseInt(val) % chars.length] + result;
            val /= chars.length;
        }
        if (val >= 0) {
            result = chars[parseInt(val)] + result;
        }
        while (result.length < len) {
            result = chars[parseInt(Math.random() * chars.length)] + result;
        }
        return result + getSuffix(filename);
    }

    function getSuffix(filename) {
        var pos = filename ? filename.lastIndexOf('.') : -1;
        return -1 < pos ? filename.substring(pos) : '';
    }

    function fetch(up, cb, async) {
        var now = new Date().getTime() / 1000,
            policy = up.getOption('policy'),
            postPolicy = up.getOption('postPolicy'),
            url = up.getOption('policyUrl') || up.getOption('url');

        if (!policy || !url) {
            return;
        }
        up.setOption('policyUrl', url);
        if (!postPolicy || postPolicy.expire <= now) {
            var xhr = new mOxie.XMLHttpRequest;
            xhr.onreadystatechange = function () {
                if (4 == xhr.readyState) {
                    var text = xhr.responseText,
                        config = ('object' == typeof JSON ? JSON.parse(text) : eval('(' + text + ')'));

                    postPolicy = config;
                    up.setOption('postPolicy', postPolicy);

                    if ('function' == typeof cb) {
                        cb(config);
                    }
                }
            };
            xhr.open('GET', url, async);
            xhr.send();
        } else {
            if ('function' == typeof cb) {
                cb(postPolicy);
            }
        }
    }


    // ///////////////////////////////////////////////////
    //
    // //////////////////////////////////////////////////

    /**
     * Uploader 构造器
     *
     * @param settings
     * @returns {Uploader}
     * @constructor
     */
    function Uploader(settings) {
        var me = this, uploader, btn;

        if (!(me instanceof Uploader)) {
            return new Uploader(settings);
        }

        settings = o.extend({}, DEFAULTS, settings || {});

        // 文件大小和数量过滤器配置
        o.extend(settings['filters'], {
            max_file_size: settings['max_file_size'],
            max_file_count: settings['max_file_count']
        });
        if (settings.extensions) {
            o.extend(settings['filters'], {
                mime_types: [{title: 'Allowed Files', extensions: settings.extensions.replace(' ', '')}]
            });
        }

        delete settings['max_file_size'];
        delete settings['max_file_count'];

        // 文件列表
        settings['queue'] = o.get(settings['queue']) || o.get(settings['list']);
        settings['thumb'] = settings['thumb'] || ('t' == settings['mode']);

        // 浏览按钮
        btn = o.get(settings['browse_button']);
        // 如果该按钮已经注册 uploader, 尝试销毁
        if (btn && btn.uploader && 'function' === typeof btn.uploader.destroy) {
            btn.uploader.destroy();
        }

        // 创建 pluploader 对象
        uploader = new plupload.Uploader(settings);

        uploader.bind('PostInit FileUploaded Error', function (up) {
            fetch(up, null, false);
        });
        uploader.bind('BeforeUpload', function (up, file) {
            configure(up, file);
        });

        this.uploader = uploader;
        uploader.init();
    }

    /**
     * 暴露事件绑定
     */
    o.extend(Uploader.prototype, {
        bind: function (event, callback) {
            this.uploader.bind(event, callback);
            return this;
        },
        clearQueue: function () {
            this.uploader.splice();
        }
    });

    function setStatus(entry, statusClass) {
        var status = ['upload-waiting', 'upload-uploading', 'upload-fail', 'upload-success', 'upload-saved'],
            i;
        entry = 'string' == typeof entry ? o.get(entry) : entry;
        if (entry) {
            for (i = 0; i < status.length; i++) {
                o.removeClass(entry, status[i]);
            }
            if ('[object Array]' != Object.prototype.toString.apply(statusClass)) {
                statusClass = [statusClass];
            }
            for (i = 0; i < statusClass.length; i++) {
                o.addClass(entry, statusClass[i]);
            }
        }
    }

    function createEntryEl(up, file) {
        file = file || {};
        file.id = file.id || ('_' + o.guid());

        var $entry = $(renderTpl(ENTRY_TPL, file)),
            status = [];

        if (file.remote) {
            status.push(STATUS_CLASS.SAVED);
            setStatus($entry[0], STATUS_CLASS.SAVED);
            if (up.getOption('download') && file.url) {
                status.push(STATUS_CLASS.DOWNLOAD);
                $entry.find('.upload-download').attr('href', plupload.buildUrl(file.url, {download: true}));
            }
        } else {
            status.push(STATUS_CLASS.WAITING);
        }
        setStatus($entry[0], status);

        // 如果是 根据 html input 元素创建, 则将该 input 移动到 entry 下
        if (file.el) {
            $entry.append(file.el);
        }

        // 删除
        $entry.find('.upload-remove').click(function () {
            var index;
            if (file.getSource) {
                up.removeFile(file);
            } else if (-1 < (index = o.inArray(file, up._files))) {
                up._files.splice(index, 1);
                up.trigger('FilesRemoved', [file]);
            }
        });

        // 重试
        $entry.find('.upload-retry').click(function () {
            file.status = plupload.QUEUED;
            //setStatus($entry[0], STATUS_CLASS.WAITING);
            up && up.start();
        });

        // 这里先添加, 是为了防止无法获取 size
        // document.body.appendChild($entry[0]);

        // 缩略图
        if (up && up.getOption('thumb')) {
            $entry.addClass('upload-image-entry');
            setEntryImage($entry[0], file, up)
        }

        $entry.hover(function () {
            $entry.addClass('hover')
        }, function () {
            $entry.removeClass('hover')
        });
        return $entry[0];
    }

    function getUrl(baseUrl, pathInUse) {
        baseUrl = baseUrl || '';
        if (pathInUse && null != baseUrl && !/^\//.test(pathInUse) && !/^(https?|file):/.test(pathInUse)) {
            pathInUse = /^\//.test(pathInUse) ? pathInUse : ("/" + pathInUse);
            pathInUse = /\/$/.test(baseUrl) ? (baseUrl.substring(1) + pathInUse) : baseUrl + pathInUse;
        }
        return pathInUse;
    }

    /**
     * 设置给定 entry 的缩略图
     *
     * @param entry
     * @param image url / file
     */
    function setEntryImage(entry, image) {
        var $entry = $(entry),
            wrap = $entry.find('.upload-thumb')[0],
            nativeSource, img, url;

        if ('function' == (typeof image.getSource)) {
            nativeSource = image.getSource();
        }

        function loadImageFailure() {
            $(wrap).html('<span style="color:#c73e3e;">预览失败</span>');
        }

        $(wrap).empty();
        // 如果是历史上传
        if (!nativeSource) {
            img = new Image();
            img.onload = function () {
                $(wrap).empty().append(img);
            };
            img.onerror = loadImageFailure;
            url = ('string' == typeof image) ? image : image.url;
            img.src = url;
        } else {
            img = new o.Image;
            img.bind('load', function () {
                var size = o.getSize(wrap);
                if (!nativeSource.rendered) { // 如果没有渲染服务端图片才渲染
                    img.embed(wrap, {width: size.w, height: size.h, crop: false});
                }
            });
            img.bind('error', loadImageFailure);
            img.load(image.getSource());
        }
    }

    /**
     * 文件数量控制 filter
     * 配置样例: { filter: { max_file_count: 5, mime_types: ... } }
     */
    plupload.addFileFilter('max_file_count', function (maxCount, file, cb) {
        var up = this,
            queueSize = up.files.length + (up._files || []).length;

        if (maxCount > 1) {  // allowed count > 1

            // 如果当前队列添加完当前元素刚满
            if (maxCount == queueSize + 1) {
                up.disableBrowse(true);
                o.each(up.getOption('browse_button'), function (el) {
                    el.disabled = true;
                    o.addClass(el, 'disabled');
                });
            }

            // 当前队列数量未满则添加
            cb(queueSize < maxCount);
        } else {
            // 只有一个文件时清空后添加
            up.splice();
            $(up.getOption('queue')).empty();

            cb(true);   // allow added
        }
    });

    plupload.addFileFilter('max_img_resolution', function (maxRes, file, cb) {
        // 如果不是图片或没有设置分辨率
        if (!/^image\//.test(file.type) || !maxRes) {
            cb(true);
        }

        var me = this, img = new o.Image();

        function finalize(result) {
            img.destroy();
            img = null;

            if (!result) {
                me.trigger('Error', {
                    code: plupload.IMAGE_DIMENSIONS_ERROR,
                    message: "Resolution exceeds the allowed limit of " + maxRes + " pixels.",
                    file: file
                });
            }
            cb(result);
        }

        img.onload = function () {
            finalize(img.width * img.height < maxRes);
        };
        img.onerror = function () {
            finalize(false);
        };
        img.load(file.getSource());
    });

    function renderTpl(tpl, data) {
        for (var p in data) {
            if (data.hasOwnProperty(p)) {
                tpl = tpl.replace(new RegExp('{' + p + '}', 'g'), data[p]);
            }
        }
        return tpl;
    }

    /**
     * 格式化时间
     *
     * @param seconds
     * @returns {string}
     */
    function formatTime(seconds) {
        var h = '00', m = '00', seconds = seconds || 0;

        if (seconds >= 60 * 60) {
            h = Math.round(seconds / 60 / 60);
            h = h > 9 ? h : '0' + h;
            seconds = seconds % (60 * 60);
        }

        if (seconds >= 60) {
            m = Math.round(seconds / 60);
            m = m > 9 ? m : '0' + m;
            seconds = seconds % 60;
        }

        seconds = seconds > 9 ? seconds : '0' + seconds;
        return h + ':' + m + ':' + seconds;
    }

    return Uploader;
}));
