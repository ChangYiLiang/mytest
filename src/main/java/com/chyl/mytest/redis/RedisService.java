package com.chyl.mytest.redis;


import java.util.List;
import java.util.Map;

/**
 * @author only-lilei
 * @create 2018-01-26 下午6:30
 **/
public interface RedisService {
    /**
     * 字符串类型:通过key值获取对应的value对象
     * @param key
     * @return
     */
    String get(String key);
    /**
     * 字符串类型:存入key-value对象，如果key存在，那么默认更新value
     * @param key
     * @param value
     * @return
     */
    Boolean set(String key, String value);

    /**
     * 字符串类型:通过key值获取对应的object对象
     * @param key
     * @return
     */
    <T> T get(String key, Class<T> clazz);

    /**
     * 字符串类型:存入key-value对象，如果key存在，那么默认更新value
     * @param key
     * @param obj
     * @return
     */
    Boolean set(String key, Object obj);
    /**
     * 字符串类型:通过key删除对应的key和value
     * @param key
     * @return
     */
    Long delete(String key);
    /**
     * 字符串类型:通过key判断对象是否存在
     * @param key
     * @return
     */
    Boolean exists(String key);
    /**
     * 字符串类型:设置key对应的超时时间
     * @param key
     * @param expireTime
     * @return
     */
    Boolean expire(String key, Long expireTime);
    /**
     * 字符串类型:将 key 的值设为 value ，当且仅当 key 不存在。若给定的 key 已经存在，则 SETNX 不做任何动作。
     * @param key
     * @param value
     * @return
     */
    Boolean setnx(String key, String value);
    /**
     * 字符串类型:设置key和value的超时时间(设置成String返回类型,不然要设置成Void)
     * @param key
     * @param timeout
     * @param value
     * @return
     */
    Void setex(String key, Long timeout, String value);
    /**
     * 字符串类型:
     * 覆盖key对应的string的一部分，从指定的offset开始，覆盖value的长度。
     * 如果offset比当前key对应string还长，那么这个string后面就补0以达到offset。
     * 不存在的keys被认为是空字符串，所以这个命令可以确保key有一个足够大的字符串，能在offset处设置value。
     * @param key
     * @param value
     * @param offset
     * @return
     */
    Void setrange(String key, String value, Long offset);
    /**
     * 字符串类型:
     * 返回key对应的字符串value的子串，自个子串是由start和end位移决定的（两者都在string内）。
     * 可以用负的位移来表示从string尾部开始数的下标。所以-1就是最后一个字符，-2就是倒数第二个，以此类推。
     * 这个函数超出范围的请求时，都把结果限制在string内。
     * @param key
     * @param start
     * @param end
     * @return
     */
    byte[] getrange(String key, Long start, Long end);
    /**
     * 字符串类型:
     * 对存储在指定key的数值执行原子的加1操作。
     * 如果指定的key不存在，那么在执行incr操作之前，会先把它的值设定为0.
     * 如果指定的key中存储的值不是字符串类型或者存储的字符串类型不能表示为一个整数，那么执行这个命令时服务器会返回一个错误。
     * 注意：由于redis并没有一个明确的类型来表示整型数据，所以这个操作是一个字符串操作。
     * 执行这个操作的时候，key对应存储的字符串被解析为10进制的64位有符号整型数据。
     * 这个操作仅限于64位的有符号整型数据。
     * 事实上，redis内部采用整数形式来存储对应的整数值，所以对该类字符串值实际上是用整数保存，也就不存在存储整数的字符串表示所带来的额外消耗。
     * incr的原子操作是说即使多个客户端对同一个key发出incr命令，也决不会导致竞争的情况，
     * 例如如下情况永远不可能发生：客户端1和客户端2同时读出10，他们俩都对其加到11，然后将新值设置为11，最终值一定为12
     * @param key
     * @return
     */
    Long incr(String key);
    /**
     * 字符串类型:
     * 对key对应的数字做减一操作。如果key不存在，那么在操作之前，这个key对应的值会被设定为0。
     * 如果key有一个错误类型的value或者是一个不能表示成数字的字符串，就返回错误。这个操作最大支持在64位有符号的整型数字。
     * @param key
     * @return
     */
    Long decr(String key);
    /**
     * 字符串类型:
     * 将key进行递增。如果key不存在，操作之前，key就会被置为0.如果key的value类型错误或者是个不能表示成数字的字符串，就返回错误。
     * 这个操作最多支持64位有符号的整形数字。
     * @param key
     * @param offset
     * @return
     */
    Long incrby(String key, Long offset);
    /**
     * 字符串类型:
     * 将key对应的数字递减。如果key不存在，操作之前，key就会被置为0.如果key的value类型错误或者是个不能表示成数字的字符串，就返回错误。
     * 这个操作最多支持64位有符号的整形数字。
     * @param key
     * @param offset
     * @return
     */
    Long decrby(String key, Long offset);
    /**
     * 字符串类型:设置多个key和value
     * 对应给定的keys到他们对应的values上。Mset会用新的value替代已经存在的value，就像普通的set命令一样。
     * 如果你不想覆盖已经存在的values，那么需要参考msetnx.
     * mset是原子性的，所以所有给定的keys是一次性set的。
     * 客户端不可能看到这种一部分keys被更新而另外的没有改变的情况。
     * @param map
     * @return
     */
    Void mset(Map<byte[], byte[]> map);
    /**
     * 字符串类型:一次性获取多个key对应的value列表
     * @param bytes
     * @return
     */
    List<byte[]> mget(byte[]... bytes);

}
