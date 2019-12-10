package com.example.bootdemo.service;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author chuan
 */
@Service
public class BeanMapperService {

    @Autowired
    private Mapper beanMapper;

    //基于Dozer转换对象的类型.
    public <T> T mapper(Object source, Class<T> destinationClass) {
        return beanMapper.map(source, destinationClass);
    }

    //基于Dozer转换Collection中对象的类型.
    public <T> List<T> mapperList(Collection sourceList, Class<T> destinationClass) {
        List<T> destinationList = new ArrayList<>();
        for (Object sourceObject : sourceList) {
            T destinationObject = beanMapper.map(sourceObject, destinationClass);
            destinationList.add(destinationObject);
        }
        return destinationList;
    }

    //基于Dozer将对象A的值拷贝到对象B中.
    public void copy(Object source, Object destinationObject) {
        beanMapper.map(source, destinationObject);
    }

}
