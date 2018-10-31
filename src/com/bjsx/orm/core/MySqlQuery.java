package com.bjsx.orm.core;

import com.bjsx.orm.po.Music_info;
import com.bjsx.orm.po.User;

/**
 * Created by Administrator on 2018/10/17.
 */
public class MySqlQuery extends Query {


    public static void main(String[] args) {
        Music_info m = new Music_info();
        User user = new User();
       /* m.setId(8);
        m.setMusic_name("爱你一万年");
        m.setMusic_size("2.0");
        m.setSinger_name("柳大华");
        new MySqlQuery().insert(m);*/

        //new MySqlQuery().delete(m.getClass(),7);

      /*  m.setId(8);
        m.setMusic_name("爱你一万年");
        m.setMusic_size("2.2");
        m.setSinger_name("刘德华");
        new MySqlQuery().update(m, new String[]{"music_size","singer_name"});*/
        /*System.out.println(new MySqlQuery().queryRows("select * from user where age>?",user.getClass(),new Object[]{10}));

        System.out.println(new MySqlQuery().queryRows("select * from user where age>?",user.getClass(),new Object[]{10}));*/
       /* System.out.println(new MySqlQuery().queryUniqueRows("select * from user where age>?",user.getClass(),new Object[]{10}));*/

   /*     System.out.println(new MySqlQuery().queryValue("select * from user where name=?",new Object[]{"张思聪"}));

        System.out.println(new MySqlQuery().queryRows("select * from user where age>?",user.getClass(),new Object[]{10}));
        System.out.println(new MySqlQuery().queryUniqueRows("select * from user where age>?",user.getClass(),new Object[]{10}));*/
        //QueryFactory.creatQuery().queryValue("select * from user where name=?",new Object[]{"张思聪"});

        long a = System.currentTimeMillis();
        for(int i=0;i<3000;i++){
            QueryFactory.creatQuery().queryValue("select * from user where name=?",new Object[]{"张思聪"});
        }

        long b = System.currentTimeMillis();
        System.out.println("---------------------------------15960");
        System.out.println(b-a);

    }

    @Override
    public Object qureyPagenate(int pageNum, int size) {
        return null;
    }
}
