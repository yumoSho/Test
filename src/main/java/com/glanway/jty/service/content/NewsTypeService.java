package com.glanway.jty.service.content;


import com.glanway.jty.entity.content.News;
import com.glanway.jty.entity.content.NewsType;
import com.glanway.jty.service.BaseService;

import java.util.List;

/**
 * @文件名: NewsService
 * @功能描述: 资讯管理类控制器
 * @author songzhe
 *
 */
public interface NewsTypeService extends BaseService<NewsType, Long>{

    List<NewsType> findAllByBankId();

    void myDelete(Long[] ids);
}
