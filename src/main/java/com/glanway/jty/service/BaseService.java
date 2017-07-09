package com.glanway.jty.service;







import com.glanway.gone.service.CrudService;

import java.io.Serializable;

/**
 * @author vacoor
 */
public interface BaseService<E, ID extends Serializable> extends CrudService<E, ID> {

    String PROVINCE="province";

    void approval(E var1);

    void reject(E var1);

}
