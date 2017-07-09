package com.glanway.jty.utils;


import com.glanway.gone.util.CipherUtil;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;


@Component
public final class PassWordSaltUtil {


    //todo 根据用户密码和创建时间进行加盐操作

   public String  passWordSalt(String password,Date createDate){
       return CipherUtil.generatePassword(CipherUtil.generatePassword(password + createDate.getTime()) + createDate.getTime());
   }


    //todo 判断密码是否一致

    public Boolean  equalPassword(String dataBasePassword,String loginPassword,Date createDate){  //一样为真
        boolean isEqualPassword=false;
        if(CipherUtil.generatePassword(CipherUtil.generatePassword(loginPassword+createDate.getTime())+createDate.getTime()).equals(dataBasePassword))
            isEqualPassword=true;
        return isEqualPassword;
    }

    //todo 格式化存入数据库的时间
    public  Date getFormatDate(Date dateTime){
        try{
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date=simpleDateFormat.format(dateTime);
            return simpleDateFormat.parse(date);
        }catch(Exception e){
            return new Date();
        }
    }

}
