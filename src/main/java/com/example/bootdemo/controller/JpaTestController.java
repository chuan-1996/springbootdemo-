package com.example.bootdemo.controller;


import com.example.bootdemo.dao.UserDao;
import com.example.bootdemo.entity.UserEntity;
import com.example.bootdemo.vo.ResultVo;
import org.omg.CORBA.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * @author qq491
 */
@Controller
@RequestMapping("/Jpa")
public class JpaTestController {

    @Autowired
    public UserDao userDao;

    /**
     * Select
     */
    @RequestMapping(value = "/test1", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo test1(@RequestParam("key") String key,
                          @RequestParam("value") String value) {
        return new ResultVo(1,"success",userDao.findAll());
    }

    /**
     * 基于test1的流式操作
     */
    @RequestMapping(value = "/test2", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo test2(@RequestParam("key") String key,
                          @RequestParam("value") String value) {

        userDao.findAll()
                .stream().
                filter(s->s.getId()>0).
                limit(5).
                skip(0).
                map(UserEntity::getPasswd).
                sorted(Comparator.comparing(s->s)).
                reduce((acc,item)->{
                    System.out.println("item: " + item);
                    return acc;
                });

        return new ResultVo(1,"success",
                userDao.findAll()
                        .stream().
                        filter(s->s.getId()>0).
                        limit(5).
                        skip(0).
                        sorted(Comparator.comparing(UserEntity::getPasswd)).
                        map(UserEntity::getPasswd).
                        collect(Collectors.toList())
                );
    }

    /**
     *基于test1的分页
     * @param key  请求第几页
     * @param value  一页多少行
     */
    @RequestMapping(value = "/test3", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo test3(@RequestParam("key") String key,
                          @RequestParam("value") String value) {

        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(Integer.parseInt(key)-1, Integer.parseInt(value), sort);
        Page<UserEntity> list = userDao.findAll(pageable);
        System.out.println("共"+list.getTotalElements()+"条");
        System.out.println("共"+list.getTotalPages()+"页");
        System.out.println("当前"+list.getNumber()+1+"页");
        System.out.println("有下一页吗"+list.hasNext()+"");
        System.out.println("有上一页吗"+list.hasPrevious()+"");
        return new ResultVo(1,"success", list.getContent());
    }
    /**
     * add
     */
    @RequestMapping(value = "/test4", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo test4(@RequestParam("key") String key,
                          @RequestParam("value") String value) {
        userDao.saveAndFlush(new UserEntity(36,"JPATEST","JPATEST","JPATEST","用户","JPATEST"));

        return new ResultVo(1, "success", userDao.findAll());
    }

    /**
     * update
     */
    @RequestMapping(value = "/test5", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo test5(@RequestParam("key") String key,
                          @RequestParam("value") String value) {
        UserEntity a = userDao.findById(36).get();
        a.setUsername("I'M CHANGED");
        userDao.saveAndFlush(a);
        return new ResultVo(1, "success", userDao.findAll());
    }
    /**
     * delete
     */
    @RequestMapping(value = "/test6", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo test6(@RequestParam("key") String key,
                          @RequestParam("value") String value) {
        userDao.deleteById(36);
        return new ResultVo(1, "success", userDao.findAll());
    }

    /**
     * select
     * dao  annotation
     */
    @RequestMapping(value = "/test7", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo test7(@RequestParam("key") String key,
                          @RequestParam("value") String value) {

        Sort sort = new Sort(Sort.Direction.ASC, "id");

        //自定义分页接口
        System.out.println(userDao.find(PageRequest.of(Integer.parseInt(key)-1,Integer.parseInt(value),sort)).getContent());

        //模糊查询
        return new ResultVo(1, "success", userDao.findTest(key));
    }
}
