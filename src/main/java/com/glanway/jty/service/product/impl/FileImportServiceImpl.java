package com.glanway.jty.service.product.impl;


import com.glanway.jty.dao.product.FileImportDao;
import com.glanway.jty.entity.product.FileImport;
import com.glanway.jty.service.BaseServiceImpl;
import com.glanway.jty.service.product.FileImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class FileImportServiceImpl extends BaseServiceImpl<FileImport,Long> implements FileImportService{

    private FileImportDao fileImportDao;
    @Autowired
    public  void setFileImportDao(FileImportDao fileImportDao){
        this.fileImportDao = fileImportDao;
        super.setCrudDao(fileImportDao);
    }
}
