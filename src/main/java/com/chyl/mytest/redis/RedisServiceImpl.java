package com.chyl.mytest.redis;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author only-lilei
 * @create 2018-01-26 下午6:34
 **/
@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private RedisSerializer<String> serializer = new StringRedisSerializer();

    private RedisSerializer<Object> objectRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
    
    public String get(String key) {
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte keys[] = serializer.serialize(key);
                byte values[] = redisConnection.get(keys);
                if(values == null){
                    return null;
                }
                String value = serializer.deserialize(values);
                //System.out.println("key:"+key+",value:"+value);
                return value;
            }
        });
    }

    public Boolean set(String key,String value) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte keys[] = serializer.serialize(key);
                byte values[] = serializer.serialize(value);
                redisConnection.set(keys,values);
                return true;
            }
        });
    }

    public <T> T get(String key,Class<T> clazz) {
        return redisTemplate.execute(new RedisCallback<T>() {
            @Override
            public T doInRedis(RedisConnection redisConnection) throws DataAccessException {
                String str = get(key);
                if(str ==null){
                    return null;
                }
                JSONObject a = JSONObject.parseObject(str);
                return JSONObject.toJavaObject(a,clazz);
            }
        });
    }

    public Boolean set(String key, Object obj) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte keys[] = serializer.serialize(key);
                byte values[] = objectRedisSerializer.serialize(obj);
                redisConnection.set(keys,values);
                return true;
            }
        });
    }

    public Long delete(String key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {

                byte keys[] = serializer.serialize(key);
                return redisConnection.del(keys);
            }
        });
    }

    public Boolean exists(String key) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte keys[] = serializer.serialize(key);
                return redisConnection.exists(keys);
            }
        });
    }

    public Boolean expire(String key, Long expireTime) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte keys[] = serializer.serialize(key);
                return redisConnection.expire(keys,expireTime);
            }
        });
    }

    public Boolean setnx(String key, String value) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte keys[] = serializer.serialize(key);
                byte values[] = serializer.serialize(value);
                return redisConnection.setNX(keys,values);
            }
        });
    }

    public Void setex(String key,Long timeout,String value) {
        return redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte keys[] = serializer.serialize(key);
                byte values[] = serializer.serialize(value);
                redisConnection.setEx(keys,timeout,values);
                return null;
            }
        });
    }

    public Void setrange(String key,String value, Long offset) {
        return redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte keys[] = serializer.serialize(key);
                byte values[] = serializer.serialize(value);
                redisConnection.setRange(keys,values,offset);
                return null;
            }
        });
    }

    public byte[] getrange(String key,Long start,Long end) {
        return redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte keys[] = serializer.serialize(key);
                return redisConnection.getRange(keys,start,end);
            }
        });
    }

    public Long incr(String key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte keys[] = serializer.serialize(key);
                return redisConnection.incr(keys);
            }
        });
    }

    public Long decr(String key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte keys[] = serializer.serialize(key);
                return redisConnection.decr(keys);
            }
        });
    }

    public Long incrby(String key, Long offset) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte keys[] = serializer.serialize(key);
                return redisConnection.incrBy(keys,offset);
            }
        });
    }

    public Long decrby(String key, Long offset) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte keys[] = serializer.serialize(key);
                return redisConnection.decrBy(keys,offset);
            }
        });
    }

    public Void mset(Map<byte[],byte[]> map) {
        return redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.mSet(map);
                return null;
            }
        });
    }

    public List<byte[]> mget(byte[]... bytes) {
        return redisTemplate.execute(new RedisCallback<List<byte[]>>() {
            @Override
            public List<byte[]> doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.mGet(bytes);
            }
        });
    }
}
