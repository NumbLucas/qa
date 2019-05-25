package com.numbguy.qa.QAUtil;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

@Service
public class JedisAdapter implements InitializingBean {
    private JedisPool jedisPool;

    public static void printf(int index, Object object) {
        System.out.println(String.format("%d %s ",index, object));
    }
//    public  static  void main(String[] srgs) {
//        Jedis jedis = new Jedis();
//        jedis.flushAll();
//        jedis.set("hello", "world");
//        printf(1, jedis.get("hello"));
//        jedis.rename("hello", "rename_hello");
//        printf(2, jedis.get("rename_hello"));
//        jedis.setex("hello2", 15, "world");
//        jedis.set("pv", "100");
//        jedis.incr("pv");
//        printf(3, jedis.get("pv"));
//        jedis.decr("pv" );
//        jedis.sadd("LIKE:3:15", "20");
//    }

    @Override
    public void afterPropertiesSet() throws Exception {
        jedisPool = new JedisPool();
    }

    public long sadd(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.sadd(key, value);

        }catch (Exception e) {
            System.out.println("jedis add erro");
        }finally {
            if(jedis !=null) {
                jedis.close();
            }
        }
        return  0;
    }

    public long srem(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return  jedis.srem(key, value);
        }catch (Exception e) {

        }finally {
            if(jedis !=null) {
                jedis.close();
            }
        }
        return  0;
    }

    public long scard(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            long count = jedis.scard(key);
            return  count;
        }catch (Exception e) {
            System.out.println("scard error");
        }finally {
            if(jedis !=null) {
                jedis.close();
            }
        }
        return  0;
    }

    public boolean sismember(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return  jedis.sismember(key, value);
        }catch (Exception e) {

        }finally {
            if(jedis !=null) {
                jedis.close();
            }
        }
        return  false;
    }

    public List<String> brpop(int timeout, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.brpop(timeout, key);
        } catch (Exception e) {
            System.out.println("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public long lpush(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lpush(key, value);
        } catch (Exception e) {
            System.out.println("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public List<String> lrange(String key, int start, int end) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lrange(key, start, end);
        } catch (Exception e) {
            System.out.println("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }
}
