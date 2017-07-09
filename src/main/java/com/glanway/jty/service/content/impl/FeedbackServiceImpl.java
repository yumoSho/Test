package com.glanway.jty.service.content.impl;

import com.glanway.jty.dao.content.FeedbackDao;
import com.glanway.jty.entity.content.Feedback;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.content.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FeedbackServiceImpl extends BaseServiceImpl<Feedback,Long> implements FeedbackService {

    @Autowired
    private FeedbackDao feedbackDao;

}
