/**
 * 淘宝SKU组合查询算法实现
 * taobao ued: http://ued.taobao.org/blog/2012/07/sku-search-algorithm/
 * cnblog: http://www.cnblogs.com/purediy/archive/2012/12/02/2798454.html
 *
 * resource/js/common.js line 20
 * resource/js/index/detail.js line 82
 * id 03F746B8B0EA4DACE050A8B4AF295BE8
 *
 * @beta
 * @author vacoor
 */
window.SKU = (function () {
    var DEFAULT = {
        selectedClass: 'on',
        disabledClass: 'out-of-stock',
        valueAttr: 'data-value',
        container: document,
        defaultSku: null,
        allowUnselect: true,  // 允许不选择
        skuMap: {},
        updateSku: function (sku, $el) {
            window.console && window.console.log('update sku', sku, $el);
        }
    };
    function Sku(config) {
        var me = this;
        if (!(me instanceof Sku)) {
            return new Sku(config);
        }

        config = $.extend({}, DEFAULT, config || {});
        me._skuTable = buildSkuTable(config.skuMap);
        me._$skuAttrElements = $(config.container).find('[' + config.valueAttr + ']')
        me.config = config;
        me.resume();
        if(config.defaultSku) {
            $((config.defaultSku + '').split(';')).each(function(i, value) {
                me._$skuAttrElements.filter('[data-value="' + value + '"]').addClass(config.selectedClass);
            });
        }


    }

    Sku.prototype = {
        resume: function () {
            var me = this,
                config = me.config;

            me.resetSku();
            me._$skuAttrElements.on('click', function (e) {
                e.preventDefault();
                var $self = $(this),
                    $selectedEls,
                    selectedAttrs;

                // 如果已经被禁用, 直接返回
                if ($self.hasClass(config.disabledClass)) {
                    return;
                }

                if (config.allowUnselect) {
                    $self.toggleClass(config.selectedClass) // toggle 当前属性值选择状态
                } else {
                    $self.addClass(config.selectedClass);
                }
                $self.siblings().removeClass(config.selectedClass); // 取消所有兄弟属性值选择状态

                // 获取所有已选择属性值元素
                $selectedEls = me.getSelectedElements();
                if (!$selectedEls.length) {
                    // 如果没有选择任何元素, 直接重置
                    me.resetSku();
                    return;
                }
                // 获取所有选择的 sku 属性值
                selectedAttrs = $.map($selectedEls, function (el) {
                    return $(el).attr(me.config.valueAttr);
                });
                // 通知更新 sku
                me.triggerUpdateSku(me.getSku(selectedAttrs), $self)
                   // 校正当前元素除外的所有未选择的节点
                    me._$skuAttrElements.not($self).not($selectedEls).each(function () {
                        var $current = $(this),
                            value = $current.attr(config.valueAttr),
                            $selectedSibling,
                            testAttrs;

                        $current.removeClass(config.disabledClass);

                        // 当前元素被选中的兄弟元素
                        $selectedSibling = $current.siblings('.' + config.selectedClass);

                        // 非当前属性以外的所有属性的值
                        testAttrs = !$selectedSibling.length ? selectedAttrs.concat() : $.map($selectedEls.not($selectedSibling), function (el) {
                            return $(el).attr(config.valueAttr);
                        });
                        testAttrs = testAttrs.concat(value);

                        // 使用其他所有选中的属性值 + 当前属性值测试
                        var sku = me.getSku(testAttrs); // TODO 判断库存

                        if (!sku || ('stock' in sku && !sku.stock)) {
                            $current.addClass(config.disabledClass);
                        }
                    });
            });
        },
        pause: function () {
            var me = this;
            me._$skuAttrElements.off('click');
        },
        resetSku: function () {
            var me = this;

            me._$skuAttrElements.each(function () {
                var $self = $(this),
                    value = $self.attr(me.config.valueAttr);

                $self.removeClass(me.config.disabledClass);

                var sku = me.getSku(value);     // TODO 判断库存

                console.log(sku);

                if (!sku || ('stock' in sku && !sku.stock)) {
                     $self.addClass(me.config.disabledClass);
                     $self.removeClass(me.config.selectedClass);
                }
            });

            // 这里默认使用 ''作为默认属性值, skuMap 中 key 以 ';' 开头,即可统计所有单品sku
            me.triggerUpdateSku(me.getSku(''));
        },
        /**
         * 获取给定属性数组对应的 sku
         * @param attrs
         * @returns {*}
         */
        getSku: function (attrs) {
            var me = this;

            if (null == attrs || '' == attrs) {
                // 传递 '', null, undefined 时直接读取, 否则 [null]/[undefined] join 会转换为""
                return me._skuTable[attrs];
            }

            if ('[object Array]' != Object.prototype.toString.call(attrs)) {
                attrs = [attrs];
            }

            // attrs.sort(function (value1, value2) {
            sort(attrs, function (value1, value2) {
                // TODO sort
                // return parseInt(value1) - parseInt(value2);
                return value1 < value2;
            });
            return me._skuTable[attrs.join(';') + ";"];
        },
        getSelectedElements: function () {
            var me = this;

            return me._$skuAttrElements.filter('.' + me.config.selectedClass);
        },
        triggerUpdateSku: function (sku, $el) {
            var me = this;
           return me.config.updateSku(sku, $el);
        }
    };

    /**
     * 冒泡排序
     * @param array
     * @param fn
     * @returns {*}
     */
    function sort(array, fn) {
        if (!!fn && typeof fn === 'function') {
            if (array.length < 2) {
                return array;
            }
            var i = 0,
                j = i + 1,
                len = array.length,
                tmp,
                r = false,
                t = 0;
            for (; i < len; i++) {
                for (j = i + 1; j < len; j++) {
                    t = fn.call(array, array[i], array[j]);
                    r = (typeof t === 'number' ? t : !!t ? 1 : 0) > 0 ? true : false;
                    if (r) {
                        tmp = array[i];
                        array[i] = array[j];
                        array[j] = tmp;
                    }
                }
            }
            return array;
        }
        return array;
    }

    //初始化得到结果集
    function buildSkuTable(skuMap) {
        var skuTable = {};
        // 获取 skuMap 的 key
        var skuKeys = getObjKeys(skuMap), i, j;

        // 遍历每一条 sku
        for (i = 0; i < skuKeys.length; i++) {
            var skuKey = skuKeys[i],       // 一条sku信息key
                sku = skuMap[skuKey],	    // 一条sku信息value
                attrs = skuKey.split(";");  // sku信息key属性值数组

            // 将 key 属性值排序
            // attrs.sort(function (value1, value2) {
            sort(attrs, function (value1, value2) {
                // return parseInt(value1) - parseInt(value2);
                return value1 < value2;
            });

            // 对每个sku信息 key 属性值进行拆分组合

            var combArr = getCombMatrix(attrs);
            for (j = 0; j < combArr.length; j++) {
                var key = combArr[j].join(';');

                if (skuTable[key]) {
                    skuTable[key].stock += sku.stock;
                    /* skuTable[key].prices.push(sku.price);
                    skuTable[key].marketPrices.push(sku.marketPrice);*/
                } else {
                    skuTable[key] = {
                        stock: sku.stock,
                        /*prices: [sku.price],
                        marketPrices: [sku.marketPrice]*/
                    }
                }
            }

            // 结果集接放入SKUResult
            skuTable[attrs.join(';')] = {
                id: sku.id,
                stock: sku.stock,
                title: sku.title,
                prices: sku.price,
                promotePrice: sku.promotePrice,
                images: sku.images
            };
        }

        return skuTable;
    }

//获得对象的key
    function getObjKeys(obj) {
        if (obj !== Object(obj)) {
            throw new TypeError('Invalid object');
        }
        var keys = [];
        for (var key in obj) {
            if (Object.prototype.hasOwnProperty.call(obj, key)) {
                keys[keys.length] = key;
            }
        }
        return keys;
    }

    /**
     * 非全选状态下的所有可能
     * 从数组中生成指定长度的组合
     * 方法: 先生成[0,1...]形式的数组, 然后根据0,1从原数组取元素，得到组合数组
     */
    function getCombMatrix(array) {
        if (!array || !array.length) {
            return [];
        }

        var len = array.length;
        var aResult = [];
        var flagMatrix = [];

        for (var n = 1; n < len; n++) {
            flagMatrix = getComboBitMatrix(len, n);

            while (flagMatrix.length) {
                var aFlag = flagMatrix.shift();
                var aComb = [];

                for (var i = 0; i < len; i++) {
                    aFlag[i] && aComb.push(array[i]);
                }
                aResult.push(aComb);
            }
        }

        return aResult;
    }


    /**
     * 得到从 m个 元素中取 n 个元素的所有选取方式的矩阵
     *
     * 结果为[0,1...]形式的数组, 1表示选取，0表示不选取
     * eg:
     * 3 个元素中取 1 个元素
     * [[1, 0, 0], [0, 1, 0], [0, 0, 1]]
     *
     * 3 个元素中取 2 个元素
     * [[1, 1, 0], [1, 0, 1], [0, 1, 1]]
     */
    function getComboBitMatrix(m, n) {
        if (!n || n < 1 || n > m) {
            return [];
        }

        if (m == n) {
            var bits = [], i;
            for (i = 0; i < m; i++) {
                bits.push(1);
            }
            return [bits];
        }

        var matrix = [],
            bits = [],
            hasNext = true,
            i, j, k, copy;

        for (i = 0; i < m; i++) {
            bits[i] = i < n ? 1 : 0;
        }

        matrix.push(bits.concat());

        while (hasNext) {
            k = 0;
            for (i = 0; i < m - 1; i++) {
                if (bits[i] == 1 && bits[i + 1] == 0) {
                    for (j = 0; j < i; j++) {
                        bits[j] = j < k ? 1 : 0;
                    }

                    bits[i] = 0;
                    bits[i + 1] = 1;
                    copy = bits.concat();
                    matrix.push(copy);

                    if (copy.slice(-n).join("").indexOf('0') == -1) {
                        hasNext = false;
                    }
                    break;
                }
                bits[i] == 1 && k++;
            }
        }
        return matrix;
    }

    return Sku;
})();
