/**
 * 全选
 * js-select-all
 * @param data-target-name
 *
 * @author vacoor
 */
;
window.parseSelectAll = (function ($, exports) {
    return function() {
        $('input:checkbox:enabled.js-select-all').each(function (i, el) {
            var $el = $(el),
                targetName = $el.attr('data-target-name'),

                allTargetSelector = '[name=' + targetName + ']:enabled:checkbox',
                checkedTargetSelector = allTargetSelector + ':checked',
                $allTarget = $(allTargetSelector);

            $el.change(function () {
                $allTarget.attr('checked', !!$el.attr('checked'));
            });

            $allTarget.change(function () {
                var checked = $(this).attr('checked');

                if (!checked) {
                    $el.attr('checked', false);
                } else {
                    $el.attr('checked', $allTarget.length == $(checkedTargetSelector).length);
                }
            }).change();
        });
    }
})(jQuery);

/**
 * 一个基于 plupload (mOxie) 的上传工具(未完善)
 * js-plupload
 * @param data-drag-target
 * @param data-list-target
 *
 * @author vacoor
 */
;window.parseUpload = (function ($, plupload, o) {
    var entryTpl = '<div class="upload-entry" id="{id}-entry" style="/*width: 307px;*/">' +
            // '<b class="upload-entry-icon"></b>' +
        '<div class="upload-entry-icon"></div>' +
        '<div class="upload-entry-name" title="{name}">{name}</div>' +
        '<div class="upload-op"></div>' +
        '<div class="upload-entry-status">待上传，请稍候..</div>' +
        '<div class="upload-tip" style="display: none;"></div>' +
        '</div>';

    // 进度信息
    var progressTpl = '<div class="upload-progress">' +
        '<span class="upload-progress-bar">' +
        '<span class="upload-progress-bar-inner" style="width:{percent}%;"></span>' +
        '</span>' +
        '<span class="upload-progress-text">' +
        '{percent}%,  {remainTime}' +
        '</span>' +
        '</div>';

    var btnTpl = '<a href="javascript:void(0);"></a>';

    // $('.js-plupload').each(function (i, el) {
    function parseUpload(i, el) {
        if (el.uploader) {
            return;
        }

        /**
         * list-target:
         * container:mode:uploadName:@url:value
         * eg:
         * file-container
         * file-container:list:file
         * file-container:list:file:@product/upload
         * file-container:list:file:@product/upload:url
         */
        var contextPath = (window.Glanway || window)['contextPath'] || '',
            $el = $(el),
            origName = $el.attr('name'),
            dropTarget = $el.attr('data-drop-target'),
        targetId = $el.attr('data-content-id'),
            listTarget = $el.attr('data-list-target'),
            listOpts = (listTarget || '').split(':'),
            listTargetEl = o.get(listOpts[0]),  // container
            listMode = 'thumb' == listOpts[1] ? 'thumb' : 'list',   // display mode
            uploadName = listOpts[2] || 'file', // upload file data name
            hasUrl = (listOpts[3] || '').charAt(0) == '@',
            uploadUrl = hasUrl ? listOpts[3].substring(1) : ('img' == uploadName ? '/storage/images/ul' : '/storage/files/ul'),
            valueField = (hasUrl ? listOpts[4] : listOpts[3]) || 'url', // value field
        //
            maxFileCount = 1,
            autoStart = true,
            opts = {
                url: contextPath + uploadUrl,
                file_data_name: uploadName,
                browse_button: el,
                drop_element: dropTarget,
                // container: document.body,
                multi_selection: false,
                runtimes: 'html5,flash,silverlight', // 'html5,flash,silverlight,html4
                filters: {
                    max_file_size: '5mb',
                    mime_types: [
                        {
                            title: "Image files",
                            extensions: "jpg,gif,png"
                        }/*,
                         {
                         title: "Zip files",
                         extensions: "zip"
                         }*/
                    ],
                    prevent_duplicates: true
                }
            },
            uploader = new plupload.Uploader(opts);

        // $el.attr('name', origName + '!' + uploader.id);
        $el.attr('name', '');
        uploader._listTarget = listTargetEl;

        // add maxFileCount filter
        o.extend(uploader.getOption('filters'), { max_file_count: maxFileCount });
        plupload.addFileFilter('max_file_count', function (maxCount, file, cb) {
            var uploader = this,
            // currentQueueSize = uploader.files.length - uploader.total.uploaded - uploader.total.failed;
                currentQueueSize = uploader.files.length;

            if (maxCount > 1) {
                if (maxCount == currentQueueSize + 1) {
                    uploader.disableBrowse(true);
                    o.each(uploader.getOption('browse_button'), function (el) {
                        el.disabled = true;
                    });
                }

                if (maxCount <= currentQueueSize) {
                    uploader.trigger('Error', {
                        code: 000,//self.FILE_COUNT_ERROR, TODO
                        message: "File count error.",
                        file: file
                    });
                    cb(false);
                } else {
                    cb(true); // allow add file
                }
            } else {
                o.each(uploader.files, function(f) {
                    uploader.removeFile(f);
                });
                $(uploader._listTarget).html('');
                cb(true);
            }
        });

        uploader.bind('FilesRemoved', function (up, files) {
            up.disableBrowse(false);
            o.each(up.getOption('browse_button'), function (el) {
                el.disabled = false;
            });
        });

        uploader.bind('FileFiltered', function (up, file) {
            if (listTargetEl) {
                var $entry = $(renderTpl(entryTpl, file));

                $entry.hover(function () {
                    $entry.addClass('hover');
                }, function () {
                    $entry.removeClass('hover');
                });

                $(btnTpl).html('删除').click(function (event) {
                        up.removeFile(file);
                    $entry.fadeOut('normal', function () {
                        $entry.remove();
                    });
                    if (event.stopPropagation) {
                        event.stopPropagation();
                    } else {
                        event.cancelBubble = true;
                    }
                }).appendTo($entry.find('.upload-op').html(''));

                $(listTargetEl).append($entry);

                if ('thumb' == listMode) {
                    var wrap = $entry.find('.upload-entry-icon')[0];

                    $entry.addClass('upload-thumb');

                    // embed thumb
                    var img = new o.Image;
                    img.bind('load', function () {
                        if (!file.getSource().rendered) { // 如果没有渲染服务端图片才渲染
                            img.embed(wrap, { /*width: 110, height: 110,*/ crop: true });
                        }
                    });
                    img.bind('error', function () {
                        $(wrap).html('error');
                    });

                    img.load(file.getSource());
                }

                // hash(file.getSource());
            }

            if (autoStart) {
                up.start();
            }
        });

        // FilesAdded 每次都会将所有文件传入
        // uploader.bind('FilesAdded', function (up, files) {});

        uploader.bind('UploadFile', function (up, file) {
            var $entry = $('#' + file.id + '-entry');

            $(btnTpl).html('取消').click(function (event) {
                uploader.removeFile(file);
                $entry.fadeOut('normal', function () {
                    $entry.remove();
                });
                if (event.stopPropagation) {
                    event.stopPropagation();
                } else {
                    event.cancelBubble = true;
                }
            }).appendTo($entry.find('.upload-op').html(''));
        });

        uploader.bind('UploadProgress', function (up, file) {
            var $status = $('#' + file.id + '-entry .upload-entry-status');
            var $tip = $('#' + file.id + '-entry .upload-tip');
            if (100 != file.percent) {
                var lastTime = file.lastTime || new Date().getTime(),
                    currentTime = file.lastTime = new Date().getTime(),
                    lastUploaded = file.lastLoaded || 0,
                    currentUploaded = file.lastLoaded = file.loaded,
                    time = currentTime - lastTime,
                    speed = (currentUploaded - lastUploaded) / time,    //ms
                    remain = Math.round((file.size - currentUploaded) / speed / 1000);  // s

                // file.remainTime = formatSize(speed * 1000).toUpperCase() + '/S';//formatTime(remain);
                file.remainTime = formatTime(remain);
                $status.html(renderTpl(progressTpl, file));
//				$tip.show().html(speed * 1000);
            } else {
                delete file.lastTime;
                delete file.lastLoaded;
                delete file.remainTime;
            }
        });

        uploader.bind('FileUploaded', function (up, file, result) {
            var data = $.parseJSON(result.response);
            if (!data.error) {
                var $entry = $('#' + file.id + '-entry'),
                    $delBtn = $(btnTpl).html('删除').click(function (event) {
                        $entry.fadeOut('normal', function () {
                            $entry.remove();
                            up.removeFile(file);
                        });
                        up.removeFile(file);
                        if (event.stopPropagation) {
                            event.stopPropagation();
                        } else {
                            event.cancelBubble = true;
                        }
                    });

                $entry.find('.upload-entry-status').html(formatSize(file.size).toUpperCase() + ' <span class="nui-txt-suc">上传完成</span>');
                $entry.find('.upload-op').html('').append($delBtn);

                if (origName && valueField) {
                    $('<input type="hidden">').attr('name', origName).val(data[valueField]).appendTo($entry);
                }
                if ('thumb' == listMode) {
                    var $container = $entry.find('.upload-entry-icon'),
                        img = new Image(),
                        size = o.getSize($container[0]);
                    img.onload = function () {
                        file.getSource().rendered = true;
                        img.style.width = size.w + 'px'; //'100%';
                        img.style.height = size.h + 'px'; //100%';
                        /*$container.html('').append(img);*/
                        /*lyg*/


                        if($('#ImageIcon')){
                            $('#ImageIcon').attr('src',img.src);
                            var v = "#adviceImge p";
                  /*         $(v).append("<img class='advice_img' style='width: 65px;height:65px;' src='"+img.src+"' />");*/
                            var n = $(v).children("span").size();
                            if(n < 5){
                                $(v).append("<span style='position: relative;float: left;z-index: 10;'><img class='iimg' style='width: 65px;height:65px;margin-right: 7px;' src='"+img.src+"' /><a href='javascript:void(0);' class='delete' style='position: absolute;top:-11px;left:1px; color: #fff;'>删除</a></span>");
                            }
                            $(".delete").click(function(){
                                $(this).parents("span").remove();
                            });
                        }
                    };

                    img.src = contextPath + "/" + data[valueField] + ('img' == uploadName ? '_' + size.w + 'x' + size.h : '');
                }
            }
        });

        uploader.bind('Error', function (up, err) {
            var message, details = "";

            message = '<strong>' + err.message + '</strong>';

            switch (err.code) {
                case plupload.FILE_EXTENSION_ERROR:
                    details = o.sprintf(("File: %s"), err.file.name);
                    break;

                case plupload.FILE_SIZE_ERROR:
                    details = o.sprintf(("File: %s, size: %d, max file size: %d"), err.file.name, formatSize(err.file.size), formatSize(plupload.parseSize(up.getOption('filters').max_file_size)));
                    break;

                case plupload.FILE_DUPLICATE_ERROR:
                    details = o.sprintf(("%s already present in the queue."), err.file.name);
                    break;

                case self.FILE_COUNT_ERROR:
                    details = o.sprintf(("Upload element accepts only %d file(s) at a time. Extra files were stripped."), up.getOption('filters').max_file_count || 0);
                    break;

                case plupload.IMAGE_FORMAT_ERROR :
                    details = ("Image format either wrong or not supported.");
                    break;

                case plupload.HTTP_ERROR:
                    // details = _("Upload URL might be wrong or doesn't exist.");
                    var $entry = $('#' + err.file.id + '-entry');
                    var $op = $entry.find('.upload-op').html('');
                    var $btn = $(btnTpl).html('重试').appendTo($op).click(function () {
                        var file = err.file;
                        file.status = plupload.QUEUED;
                        uploader.start();
                    });
                    $('<span>|</span>').appendTo($op);
                    $(btnTpl).html('删除').appendTo($op).click(function () {
                        $entry.fadeOut('normal', $.proxy($entry.remove, $entry));
                        if (event.stopPropagation) {
                            event.stopPropagation();
                        } else {
                            event.cancelBubble = true;
                        }
                    });

                    $entry.find('.upload-entry-status').html('<span style="color: #ff1717;">上传失败, 错误代码' + err.code + ', 请重试</span>');
                    break;
            }

            message += "<i>" + details + "</i>";
            $('.upload-error').html(message);

            // do not show UI if no runtime can be initialized
            if (err.code === plupload.INIT_ERROR) {
                setTimeout(function () {
                    delete el.uploader;
                    uploader.destroy();
                }, 1);
            }
        });

        // dnd
        if (document.createElement('div').addEventListener) {
            plupload.each(uploader.getOption('drop_element'), function (el) {
                el.addEventListener('dragover', function () {
                    plupload.addClass(el, 'plupload-drag-file-over');
                }, false);

                el.addEventListener('dragleave', function () {
                    plupload.removeClass(el, 'plupload-drag-file-over');
                }, false);

                el.addEventListener('drop', function () {
                    plupload.removeClass(el, 'plupload-drag-file-over');
                }, false);
            });
        }

        uploader.init();
        el.uploader = uploader;
    }/*)*/;

    // 为了可以处理动态生成的先这样子 TODO
    return function() {
        $('.js-plupload').each(parseUpload);
    };

    // -------------------------
    function renderTpl(tpl, data) {
        for (var p in data) {
            if (data.hasOwnProperty(p)) {
                tpl = tpl.replace(new RegExp('{' + p + '}', 'g'), data[p]);
            }
        }
        return tpl;
    }

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

    function formatSize(size, pointLength, units) {
        var unit;

        units = units || [ 'B', 'K', 'M', 'G', 'TB' ];

        while ((unit = units.shift()) && size > 1024) {
            size = size / 1024;
        }

        return (unit === 'B' ? size : size.toFixed(pointLength || 2)) + unit;
    }

    //
    function hash(file) {
        var chunkSize = 2097152,    // 2MB
            chunks = Math.ceil(file.size / chunkSize),
            currentChunk = 0,
        // reader = new FileReader(),
            reader = new o.FileReader,// oMixe.FileReader
            spark = new SparkMD5;

        if (!file.size) {    // not html5, not flash, not silverlight
            // console.log('can not read file size', file.size);
            file.hash = -1;
            return;
        }

        file.hash = 0;
        // render.onload = function() {} // html5
        reader.bind('loadend', function (e) {   // mOxie
            // console.log('read chunk nr:', currentChunk + 1, 'of', chunks);

            // console.log(e.target.error);
            if (e.target.error) {
                file.hash = -1;
                return;
            }
            spark.appendBinary(e.target.result);
            currentChunk++;

            if (currentChunk < chunks) {
                loadNextChunk();
            } else {
                // console.log('finished');
                file.hash = spark.end(); // warning: call onece only
                console.warn('computed hash', file.hash);
            }

        });
        loadNextChunk();

        function loadNextChunk() {
            var start = currentChunk * chunkSize,
                end = start + chunkSize >= file.size ? file.size : start + chunkSize;

            /*
             slice = File.prototype.mozSlice || File.prototype.webkitSlice || File.prototype.slice;
             reader.readAsBinaryString(slice.call(file, start, end));
             */
            reader.readAsBinaryString(file.slice(start, end));
        }
    }
})(jQuery, plupload, mOxie);


parseUpload();

