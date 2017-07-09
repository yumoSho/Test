package com.glanway.jty.service.customer;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.entity.customer.Member;
import com.glanway.jty.service.BaseService;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
* </b>Member Service
*  @author  generator
*  @Time     2016-04-12
*  @version 1.0
*/
public interface MemberService extends BaseService<Member,Long> {
    boolean nameIsExist(String memberName);

    boolean phoneIsExist(String phone);

    boolean emailIsExist(String email);

    boolean isExist(Map param);

    Integer maxMemberCode();

    void updateLoginTimeAndIp(Member member);

    Member setPassword(Map<String, Object> param, String password) throws InvocationTargetException, IllegalAccessException;

    /**
     * MemberName 重复值
     * @param id memberID
     * @param memberName 用户名
     * @return
     */
    int memberNameRepeat(Long id, String memberName);

    /**
     * 修改密码 验证原密码是否重复
     * @param param
     * @param password
     * @param oldPassword
     * @return
     */
    Member setPassword(Map<String, Object> param, String password, String oldPassword) throws InvocationTargetException, IllegalAccessException;


    /**
     * 导出的查询
     * @param filters
     * @param pageable
     * @return
     */
    List<Member> findToExport(Filters filters, Pageable pageable);

    /**
     * 验证原密码是否一致
     * 根据
     * @param param 手机号 或者 邮箱
     * @param password
     */
    public Boolean validatePasswordEqual(Map<String, Object> param, String password, String oldPassword);

    /**
     *
     * @param param
     */
    void active(Map param);
}
