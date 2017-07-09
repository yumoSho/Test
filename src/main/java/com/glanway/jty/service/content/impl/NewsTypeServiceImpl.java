package com.glanway.jty.service.content.impl;

import com.glanway.jty.dao.content.NewsDao;
import com.glanway.jty.dao.content.NewsTypeDao;
import com.glanway.jty.entity.content.News;
import com.glanway.jty.entity.content.NewsType;
import com.glanway.jty.exception.CustomException;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.content.NewsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NewsTypeServiceImpl extends BaseServiceImpl<NewsType,Long> implements NewsTypeService {

    @Autowired
    private NewsTypeDao newsTypeDao;
  @Autowired
    private NewsDao newsDao;

    @Override
    public List<NewsType> findAllByBankId() {
        return newsTypeDao.findAllByBankId();
    }

    @Override
    public void myDelete(Long[] ids) {
        for(Long id : ids) {
            List<News> newsList = newsDao.findAllByTypeId(id);
            if(null != newsList && newsList.size() > 0){
                throw  new CustomException("删除失败,该分类已有子项");
            }else{
                newsTypeDao.delete(id);
            }
        }
    }
}
