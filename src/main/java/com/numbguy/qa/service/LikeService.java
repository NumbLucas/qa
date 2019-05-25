package com.numbguy.qa.service;

import com.numbguy.qa.QAUtil.JedisAdapter;
import com.numbguy.qa.QAUtil.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    @Autowired
    JedisAdapter jedisAdapter;

    public long getLikeCount(int entityType, int entityId) {
        String LikeKey= RedisKeyUtil.getLikeKey(entityType, entityId);
        return jedisAdapter.scard(LikeKey);
    }

    public long getDisLikeCOunt(int entityType, int entityId) {
        String DisLikeKey= RedisKeyUtil.getDisLikeKey(entityType, entityId);
        return jedisAdapter.scard(DisLikeKey);
    }

    public long like(int userId, int entityType, int entityId) {
        String LikeKey= RedisKeyUtil.getLikeKey(entityType, entityId);
        String DisLikeKey= RedisKeyUtil.getDisLikeKey(entityType, entityId);

        jedisAdapter.sadd(LikeKey, String.valueOf(userId));
        jedisAdapter.srem(DisLikeKey, String.valueOf(userId));
        long count = jedisAdapter.scard(LikeKey);

        return count;
    }

    public long disLike(int userId, int entityType, int entityId) {
        String LikeKey= RedisKeyUtil.getLikeKey(entityType, entityId);
        String DisLikeKey= RedisKeyUtil.getDisLikeKey(entityType, entityId);

        jedisAdapter.sadd(DisLikeKey, String.valueOf(userId));
        jedisAdapter.srem(LikeKey, String.valueOf(userId));

        return jedisAdapter.scard(LikeKey);
    }

    public int getLikeStatus(int userId, int entityType, int entityId) {
        String LikeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        if(jedisAdapter.sismember(LikeKey, String.valueOf(userId))) {
            return 1;
        }
        String DisLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);

        return jedisAdapter.sismember(DisLikeKey, String.valueOf(userId))?-1:0;


    }
}
