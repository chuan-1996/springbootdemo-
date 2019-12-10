package com.example.bootdemo.controller;

import com.example.bootdemo.utils.RedisUtil;
import com.example.bootdemo.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author qq491
 * //配置默认序列化与反序列化工具类
 * 2.afterPropertiesSet
 * //根据参数执行相关operation操作，例如，事务
 * 3.execute
 * //执行pipelining流水线相关操作
 * 4.executePipelined
 * //执行指定connection连接的相关操作
 * 5.executeWithStickyConnection
 * //执行session内的execute方法
 * 6.executeSession
 * //创建RedisConnection代理类
 * 7.createRedisConnectionProxy
 * //connection连接的预处理
 * 8.preProcessConnection
 * //结果的后处理，默认什么都不做
 * 9.postProcessResult
 * //是否向RedisCallback暴露本地连接
 * 10.isExposeConnection
 * //设置是否向RedisCallback暴露本地连接
 * 11.setExposeConnection
 * //12到26都是设置和获取相关序列化工具类
 * 12.isEnableDefaultSerializer
 * 13.setEnableDefaultSerializer
 * 14.getDefaultSerializer
 * 15.setDefaultSerializer
 * 16.setKeySerializer
 * 17.getKeySerializer
 * 18.setValueSerializer
 * 19.getValueSerializer
 * 20.getHashKeySerializer
 * 21.setHashKeySerializer
 * 22.getHashValueSerializer
 * 23.setHashValueSerializer
 * 24.getStringSerializer
 * 25.setStringSerializer
 * 26.setScriptExecutor
 * //27到34为私有方法，不对外提供使用
 * 27.rawKey
 * 28.rawString
 * 29.rawValue
 * 30.rawKeys
 * 31.deserializeKey
 * 32.deserializeMixedResults
 * 33.deserializeSet
 * 34.convertTupleValues
 * //执行事务
 * 35.exec
 * 36.execRaw
 * //删除操作
 * 37.delete
 * //接触链接
 * 38.unlink
 * //查看是否含有指定key
 * 39.hasKey
 * 40.countExistingKeys
 * //设置过期时间
 * 41.expire
 * 42.expireAt
 * //转换成字节流并向channel发送message
 * 43.convertAndSend
 * //获取过期时间
 * 44.getExpire
 * //根据传入的正则表达式返回所有的key
 * 46.keys
 * //取消指定key的过期时间
 * 47.persist
 * //移动指定的key和index到数据库中
 * 48.move
 * //从键空间随机获取一个key
 * 49.randomKey
 * //将指定key改成目标key
 * 50.rename
 * //key不存在时，将指定key改成目标key
 * 51.renameIfAbsent
 * //设置存储在指定key的类型
 * 52.type
 * //检索存储在key的值的序列化版本
 * 53.dump
 * //执行Redis的restore的命令
 * 54.restore
 * //标记事务阻塞的开始
 * 55.multi
 * //丢弃所有在multi之后发出的命令
 * 56.discard
 * //观察指定key在事务处理开始即multi之后的修改情况
 * 57.watch
 * //刷新先前观察的所有key
 * 58.unwatch
 * //为key元素排序
 * 59.sort
 * //关闭客户端连接
 * 60.killClient
 * //请求连接客户端的相关信息和统计数据
 * 61.getClientList
 * //更改复制配置到新的master
 * 62.slaveOf
 * //将本机更改为master
 * 63.slaveOfNoOne
 * //64到79都是获取相对应的操作
 * 64.opsForCluster
 * 65.opsForGeo
 * 66.boundGeoOps
 * 67.boundHashOps
 * 68.opsForHash
 * 69.opsForHyperLogLog
 * 70.opsForList
 * 71.boundListOps
 * 72.boundSetOps
 * 73.opsForSet
 * 74.opsForStream
 * 75.boundStreamOps
 * 76.boundValueOps
 * 77.opsForValue
 * 78.boundZSetOps
 * 79.opsForZSet
 * //设置是否支持事务
 * 80.setEnableTransactionSupport
 * //设置bean的类加载器
 * 81.setBeanClassLoader
 */
@Controller
@RequestMapping("/Redis")
public class RedisTestController {

    @Autowired
    public RedisTemplate redisTemplate;

    @RequestMapping(value = "/test1", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo test1(@RequestParam("key") String key,
                          @RequestParam("value") String value) {

        redisTemplate.opsForValue().set("String",1,100,TimeUnit.SECONDS);
        System.out.println("---->get:"+redisTemplate.opsForValue().get("String"));
        System.out.println("---->increment+1:"+redisTemplate.opsForValue().increment("String",1));
        System.out.println("---->decrement-2:"+redisTemplate.opsForValue().decrement("String",2));
        System.out.println("---->getExpire:"+redisTemplate.getExpire("String"));
        redisTemplate.delete("String");
        return new ResultVo(1,"success",0);
    }

    @RequestMapping(value = "/test2", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo test2(@RequestParam("key") String key,
                           @RequestParam("value") String value) {

        redisTemplate.opsForList().leftPushAll("List",1,2,3,4,5,6,7,8,9);
        redisTemplate.opsForList().leftPush("List","left");
        redisTemplate.opsForList().rightPush("List","right");
        for(int i = 0;i<redisTemplate.opsForList().size("List");++i){
            System.out.println("Row"+i+":"+redisTemplate.opsForList().index("List",i));
        }
        redisTemplate.opsForList().leftPop("List");
        redisTemplate.opsForList().leftPop("List");
        redisTemplate.opsForList().rightPop("List");
        redisTemplate.opsForList().rightPop("List");
        System.out.println("---->afterPop");
        for(int i = 0;i<redisTemplate.opsForList().size("List");++i){
            System.out.println("Row"+i+":"+redisTemplate.opsForList().index("List",i));
        }
        /**
         * count> 0：删除等于从左到右移动的值的第一个元素；count< 0：删除等于从右到左移动的值的第一个元素；count = 0：删除等于value的所有元素。
         * key count value
         */
        redisTemplate.opsForList().remove("List",0,5);
        System.out.println("---->afterremove-1");
        for(int i = 0;i<redisTemplate.opsForList().size("List");++i){
            System.out.println("Row"+i+":"+redisTemplate.opsForList().index("List",i));
        }
        redisTemplate.delete("List");
        return new ResultVo(1,"success",0);
    }
    @RequestMapping(value = "/test3", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo test3(@RequestParam("key") String key,
                          @RequestParam("value") String value) {

        redisTemplate.opsForSet().add("setValue","A","B","C","B","D","E","F");
        Set set = redisTemplate.opsForSet().members("setValue");
        System.out.println("通过members(K key)方法获取变量中的元素值:" + set);

        long setLength = redisTemplate.opsForSet().size("setValue");
        System.out.println("通过size(K key)方法获取变量中元素值的长度:" + setLength);

        Object randomMember = redisTemplate.opsForSet().randomMember("setValue");
        System.out.println("通过randomMember(K key)方法随机获取变量中的元素:" + randomMember);

        boolean isMember = redisTemplate.opsForSet().isMember("setValue","A");
        System.out.println("通过isMember(K key, Object o)方法检查给定的元素是否在变量中:" + isMember);

        boolean isMove = redisTemplate.opsForSet().move("setValue","A","destSetValue");
        if(isMove){
            set = redisTemplate.opsForSet().members("setValue");
            System.out.print("通过move(K key, V value, K destKey)方法转移变量的元素值到目的变量后的剩余元素:" + set);
            set = redisTemplate.opsForSet().members("destSetValue");
            System.out.println(",目的变量中的元素值:" + set);
        }

        Object popValue = redisTemplate.opsForSet().pop("setValue");
        System.out.print("通过pop(K key)方法弹出变量中的元素:" + popValue);
        set = redisTemplate.opsForSet().members("setValue");
        System.out.println(",剩余元素:" + set);

        long removeCount = redisTemplate.opsForSet().remove("setValue","E","F","G");
        System.out.print("通过remove(K key, Object... values)方法移除变量中的元素个数:" + removeCount);
        set = redisTemplate.opsForSet().members("setValue");
        System.out.println(",剩余元素:" + set);

        //START所有参数使用Colletion<K>的方法均测试失败，使用多种集合都无法正确取得，只能用redis内部的两个set做
        Set<String> list = new HashSet<String>();
        list.add("C");
        list.forEach(System.out::println);

        Set differenceSet = redisTemplate.opsForSet().difference("setValue",list);
        System.out.println("通过difference(K key, Collection<K> otherKeys)方法获取变量中与给定集合中变量不一样的值:" + differenceSet);
        // END 原因不明

        redisTemplate.opsForSet().add("destSetValue","C");
        differenceSet = redisTemplate.opsForSet().difference("setValue","destSetValue");
        System.out.println("通过difference(K key, Collection<K> otherKeys)方法获取变量中与给定变量不一样的值:" + differenceSet);

        redisTemplate.opsForSet().differenceAndStore("setValue","destSetValue","storeSetValue");
        set = redisTemplate.opsForSet().members("storeSetValue");
        System.out.println("通过differenceAndStore(K key, K otherKey, K destKey)方法将求出来的差值元素保存:" + set);
        // START 原因不明
        redisTemplate.opsForSet().differenceAndStore("setValue",list,"storeSetValue");
        set = redisTemplate.opsForSet().members("storeSetValue");
        System.out.println("通过differenceAndStore(K key, Collection<K> otherKeys, K destKey)方法将求出来的差值元素保存:" + set);
        // END 原因不明
        set = redisTemplate.opsForSet().distinctRandomMembers("setValue",2);
        System.out.println("通过distinctRandomMembers(K key, long count)方法获取去重的随机元素:" + set);

        set = redisTemplate.opsForSet().intersect("setValue","destSetValue");
        System.out.println("通过intersect(K key, K otherKey)方法获取交集元素:" + set);
        // START 原因不明
        set = redisTemplate.opsForSet().intersect("setValue",list);
        System.out.println("通过intersect(K key, Collection<K> otherKeys)方法获取交集元素:" + set);
        // END 原因不明
        redisTemplate.opsForSet().intersectAndStore("setValue","destSetValue","intersectValue");
        set = redisTemplate.opsForSet().members("intersectValue");
        System.out.println("通过intersectAndStore(K key, K otherKey, K destKey)方法将求出来的交集元素保存:" + set);
        // START 原因不明
        redisTemplate.opsForSet().intersectAndStore("setValue",list,"intersectListValue");
        set = redisTemplate.opsForSet().members("intersectListValue");
        System.out.println("通过intersectAndStore(K key, Collection<K> otherKeys, K destKey)方法将求出来的交集元素保存:" + set);
        // END 原因不明
        set = redisTemplate.opsForSet().union("setValue","destSetValue");
        System.out.println("通过union(K key, K otherKey)方法获取2个变量的合集元素:" + set);

        redisTemplate.opsForSet().unionAndStore("setValue","destSetValue","unionValue");
        set = redisTemplate.opsForSet().members("unionValue");
        System.out.println("通过unionAndStore(K key, K otherKey, K destKey)方法将求出来的交集元素保存:" + set);

        redisTemplate.delete("setValue");
        redisTemplate.delete("destSetValue");
        redisTemplate.delete("intersectValue");
        redisTemplate.delete("storeSetValue");
        redisTemplate.delete("unionValue");
        return new ResultVo(1,"success",0);
    }

    @RequestMapping(value = "/test4", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo test4(@RequestParam("key") String key,
                          @RequestParam("value") String value) {

        //同SET ，所有参数使用Colletion<K>的方法均测试失败，使用多种集合都无法正确取得交集，只能用redis内部的两个set做交集
        //add(K key, V value, double score)
        redisTemplate.opsForZSet().add("zSetValue","A",1);
        redisTemplate.opsForZSet().add("zSetValue","B",3);
        redisTemplate.opsForZSet().add("zSetValue","C",2);
        redisTemplate.opsForZSet().add("zSetValue","D",5);

        //range(K key, long start, long end)
        Set zSetValue = redisTemplate.opsForZSet().range("zSetValue",0,-1);
        System.out.println("通过range(K key, long start, long end)方法获取指定区间的元素:" + zSetValue);

        //rangeByScore(K key, double min, double max)
        zSetValue = redisTemplate.opsForZSet().rangeByScore("zSetValue",1,2);
        System.out.println("通过rangeByScore(K key, double min, double max)方法根据设置的score获取区间值:" + zSetValue);

        //rangeByScore(K key, double min, double max,long offset, long count)
        zSetValue = redisTemplate.opsForZSet().rangeByScore("zSetValue",1,5,1,3);
        System.out.println("通过rangeByScore(K key, double min, double max, long offset, long count)方法根据设置的score获取区间值:" + zSetValue);

        //count(K key, double min, double max)
        long count = redisTemplate.opsForZSet().count("zSetValue",1,5);
        System.out.println("通过count(K key, double min, double max)方法获取区间值的个数:" + count);

        //rank(K key, Object o)
        long index = redisTemplate.opsForZSet().rank("zSetValue","B");
        System.out.println("通过rank(K key, Object o)方法获取变量中元素的索引:" + index);

        //score(K key, Object o)
        double score = redisTemplate.opsForZSet().score("zSetValue","B");
        System.out.println("通过score(K key, Object o)方法获取元素的分值:" + score);

        //zCard(K key)
        long zCard = redisTemplate.opsForZSet().zCard("zSetValue");
        System.out.println("通过zCard(K key)方法获取变量的长度:" + zCard);

        //incrementScore(K key, V value, double delta)
        double incrementScore = redisTemplate.opsForZSet().incrementScore("zSetValue","C",5);
        System.out.print("通过incrementScore(K key, V value, double delta)方法增加变量中的元素的分值:" + 5);
        score = redisTemplate.opsForZSet().score("zSetValue","C");
        System.out.print(",修改后获取元素的分值:" + score);
        zSetValue = redisTemplate.opsForZSet().range("zSetValue",0,-1);
        System.out.println("，修改后排序的元素:" + zSetValue);

        //reverseRange(K key, long start, long end)
        zSetValue = redisTemplate.opsForZSet().reverseRange("zSetValue",0,-1);
        System.out.println("通过reverseRange(K key, long start, long end)方法倒序排列元素:" + zSetValue);

        //reverseRangeByScore(K key, double min, double max)
        zSetValue = redisTemplate.opsForZSet().reverseRangeByScore("zSetValue",1,5);
        System.out.println("通过reverseRangeByScore(K key, double min, double max)方法倒序排列指定分值区间元素:" + zSetValue);

        //reverseRangeByScore(K key, double min, double max, long offset, long count)
        zSetValue = redisTemplate.opsForZSet().reverseRangeByScore("zSetValue",1,5,1,2);
        System.out.println("通过reverseRangeByScore(K key, double min, double max, long offset, long count)方法倒序排列从给定下标和给定长度分值区间元素:" + zSetValue);

        //reverseRank(K key, Object o)
        long reverseRank = redisTemplate.opsForZSet().reverseRank("zSetValue","B");
        System.out.println("通过reverseRank(K key, Object o)获取倒序排列的索引值:" + reverseRank);

        //intersectAndStore(K key, K otherKey, K destKey)
        redisTemplate.opsForZSet().intersectAndStore("zSetValue","typedTupleSet","intersectSet");
        zSetValue = redisTemplate.opsForZSet().range("intersectSet",0,-1);
        System.out.println("通过intersectAndStore(K key, K otherKey, K destKey)方法获取2个变量的交集存放到第3个变量里面:" + zSetValue);

        //unionAndStore(K key, K otherKey, K destKey)
        redisTemplate.opsForZSet().unionAndStore("zSetValue","typedTupleSet","unionSet");
        zSetValue = redisTemplate.opsForZSet().range("unionSet",0,-1);
        System.out.println("通过unionAndStore(K key, K otherKey, K destKey)方法获取2个变量的交集存放到第3个变量里面:" + zSetValue);

        //remove(K key, Object... values)
        long removeCount = redisTemplate.opsForZSet().remove("unionListSet","A","B");
        zSetValue = redisTemplate.opsForZSet().range("unionListSet",0,-1);
        System.out.print("通过remove(K key, Object... values)方法移除元素的个数:" + removeCount);
        System.out.println(",移除后剩余的元素:" + zSetValue);

        //removeRangeByScore(K key, double min, double max)
        removeCount = redisTemplate.opsForZSet().removeRangeByScore("unionListSet",3,5);
        zSetValue = redisTemplate.opsForZSet().range("unionListSet",0,-1);
        System.out.print("通过removeRangeByScore(K key, double min, double max)方法移除元素的个数:" + removeCount);
        System.out.println(",移除后剩余的元素:" + zSetValue);

        //removeRange(K key, long start, long end)
        removeCount = redisTemplate.opsForZSet().removeRange("unionListSet",3,5);
        zSetValue = redisTemplate.opsForZSet().range("unionListSet",0,-1);
        System.out.print("通过removeRange(K key, long start, long end)方法移除元素的个数:" + removeCount);
        System.out.println(",移除后剩余的元素:" + zSetValue);

        redisTemplate.delete("zSetValue");
        redisTemplate.delete("unionSet");
        return new ResultVo(1,"success",0);
    }

    @RequestMapping(value = "/test5", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo test5(@RequestParam("key") String key,
                          @RequestParam("value") String value) {

        //put(H key, HK hashKey, HV value)
        redisTemplate.opsForHash().put("hashValue","map1","map1-1");
        redisTemplate.opsForHash().put("hashValue","map2","map2-2");

        //values(H key)
        List<Object> hashList = redisTemplate.opsForHash().values("hashValue");
        System.out.println("通过values(H key)方法获取变量中的hashMap值:" + hashList);

        //entries(H key)
        Map<Object,Object> map = redisTemplate.opsForHash().entries("hashValue");
        System.out.println("通过entries(H key)方法获取变量中的键值对:" + map);

        //get(H key, Object hashKey)
        Object mapValue = redisTemplate.opsForHash().get("hashValue","map1");
        System.out.println("通过get(H key, Object hashKey)方法获取map键的值:" + mapValue);

        //hasKey(H key, Object hashKey)
        boolean hashKeyBoolean = redisTemplate.opsForHash().hasKey("hashValue","map3");
        System.out.println("通过hasKey(H key, Object hashKey)方法判断变量中是否存在map键:" + hashKeyBoolean);

        //keys(H key)
        Set<Object> keySet = redisTemplate.opsForHash().keys("hashValue");
        System.out.println("通过keys(H key)方法获取变量中的键:" + keySet);

        //size(H key)
        long hashLength = redisTemplate.opsForHash().size("hashValue");
        System.out.println("通过size(H key)方法获取变量的长度:" + hashLength);

        //increment(H key, HK hashKey, double delta)
        double hashIncDouble = redisTemplate.opsForHash().increment("hashInc","map1",3);
        System.out.println("通过increment(H key, HK hashKey, double delta)方法使变量中的键以值的大小进行自增长:" + hashIncDouble);

        //multiGet(H key, Collection<HK> hashKeys)
        List<Object> list = new ArrayList<Object>();
        list.add("map1");
        list.add("map2");
        List mapValueList = redisTemplate.opsForHash().multiGet("hashValue",list);
        System.out.println("通过multiGet(H key, Collection<HK> hashKeys)方法以集合的方式获取变量中的值:"+mapValueList);

        //putAll(H key, Map<? extends HK,? extends HV> m)
        Map newMap = new HashMap();
        newMap.put("map3","map3-3");
        newMap.put("map5","map5-5");
        redisTemplate.opsForHash().putAll("hashValue",newMap);
        map = redisTemplate.opsForHash().entries("hashValue");
        System.out.println("通过putAll(H key, Map<? extends HK,? extends HV> m)方法以map集合的形式添加键值对:" + map);

        //putIfAbsent(H key, HK hashKey, HV value)
        redisTemplate.opsForHash().putIfAbsent("hashValue","map6","map6-6");
        map = redisTemplate.opsForHash().entries("hashValue");
        System.out.println("通过putIfAbsent(H key, HK hashKey, HV value)方法添加不存在于变量中的键值对:" + map);

        //scan(H key, ScanOptions options)
        Cursor<Map.Entry<Object,Object>> cursor = redisTemplate.opsForHash().scan("hashValue",ScanOptions.scanOptions().match("map1").build());
        //Cursor<Map.Entry<Object,Object>> cursor = redisTemplate.opsForHash().scan("hashValue",ScanOptions.NONE);
        while (cursor.hasNext()){
            Map.Entry<Object,Object> entry = cursor.next();
            System.out.println("通过scan(H key, ScanOptions options)方法获取匹配键值对:" + entry.getKey() + "---->" + entry.getValue());
        }

        //delete(H key, Object... hashKeys)
        redisTemplate.opsForHash().delete("hashValue","map1","map2");
        map = redisTemplate.opsForHash().entries("hashValue");
        System.out.println("通过delete(H key, Object... hashKeys)方法删除变量中的键值对后剩余的:" + map);

        redisTemplate.delete("String");
        return new ResultVo(1,"success",0);
    }

}
