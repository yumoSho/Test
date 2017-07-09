package com.glanway.jty.service.loginandregister;

import com.glanway.jty.vo.RegisterVo;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tianxuan
 * @Time 2016/4/19
 */
public interface RegisterService {
    void register(RegisterVo rv, HttpServletRequest request) throws Exception;
}
