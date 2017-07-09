package com.glanway.jty.dao.customer;




import com.glanway.jty.dao.BaseDao;
import com.glanway.jty.entity.customer.Member;

import java.util.Map;

/**
* </b>会员 Dao
*  @author  generator
*  @Time     2016-04-12
*  @version 1.0
*/
public interface MemberDao extends BaseDao<Member,Long> {

    /**
     * 根据条件查询是否存在
     * @param param
     * @return 存在数量
     */
    int isExist(Map param);

    void setPassword(Map param);

    /**
     * MemberName 重复值
     * @param id memberID
     * @param memberName 用户名
     * @return
     */
    int memberNameRepeat(Long id, String memberName);

    /**
     * 得到某个银行的会员注册数量
     * @param
     * @return
     */
    Integer maxMemberCode();

    void active(Map param);
}
