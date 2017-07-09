package com.glanway.jty.service.customer.impl;


import com.glanway.jty.entity.customer.OauthBind;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.customer.OauthBindService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * </b>oauthBind Service
 *
 * @author SongZhe
 * @version 1.0
 * @Time 2016-10-09
 */
@Service("oauthBindService")
@Transactional
public class OauthBindServiceImpl extends BaseServiceImpl<OauthBind, Long> implements OauthBindService {

}
