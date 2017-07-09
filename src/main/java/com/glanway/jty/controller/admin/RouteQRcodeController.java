package com.glanway.jty.controller.admin;

import com.glanway.jty.controller.BaseController;
import com.glanway.jty.entity.operations.QRcode;
import com.glanway.jty.service.operations.QRcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>名称: 二维码访问路由</p>
 * <p>说明: </p>
 * <p>修改记录：（修改日期 - 修改人 - 修改内容）</p>
 *
 * @author：tianxuan
 * @date：2016/8/411:20
 * @version: 1.0
 */
@Controller
@RequestMapping("/route")
public class RouteQRcodeController extends BaseController{
    @Autowired
    private QRcodeService qRcodeService;

    @RequestMapping
    public String route(Long qrCodeId) {
        QRcode qRcode = qRcodeService.find(qrCodeId);
        qRcode.setVisitorCount(qRcode.getVisitorCount() + 1);
        qRcodeService.update(qRcode);
        return "redirect:" + qRcode.getLink();
    }
}
