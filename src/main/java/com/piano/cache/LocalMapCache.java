package com.piano.cache;

import io.micronaut.core.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class LocalMapCache {

        /**
         * 预缓存信息
         */
        private static final Map<String, String> CACHE_MAP = new ConcurrentHashMap<>();
        /**
         * 每个缓存生效时间一分钟
         */
        public static final long CACHE_HOLD_TIME_HALF_1M = 30 * 1000L;
        /**
         * 每个缓存生效时间一分钟
         */
        public static final long CACHE_HOLD_TIME_1M = 60 * 1000L;
        /**
         * 每个缓存生效时间5分钟
         */
        public static final long CACHE_HOLD_TIME_5M = 60 * 5 * 1000L;
        /**
         * 每个缓存生效时间2小时
         */
        public static final long CACHE_HOLD_TIME_2H = 6 * 60 * 60 * 1000L;

        /**
         * 每个缓存生效时间24小时
         */
        public static final long CACHE_HOLD_TIME_24H = 24 * 60 * 60 * 1000L;

        /**
         * 存放一个缓存对象，默认保存时间12小时
         * @param cacheName
         * @param json
         */
        public static void put(String cacheName, String json) {
            put(cacheName, json, CACHE_HOLD_TIME_2H);
        }

        /**
         * 存放一个缓存对象，保存时间为holdTime
         * @param cacheName
         * @param json
         * @param holdTime
         */
        public static void put(String cacheName, String json, long holdTime) {
            CACHE_MAP.put(cacheName, json);
            CACHE_MAP.put(cacheName + "_HoldTime", System.currentTimeMillis() + holdTime + "");//缓存失效时间
        }

        /**
         * 取出一个缓存对象
         * @param cacheName
         * @return
         */
        public static String get(String cacheName) {
            if (checkCacheName(cacheName)) {
                return CACHE_MAP.get(cacheName);
            }
            return null;
        }

        /**
         * 删除所有缓存
         */
        public static void removeAll() {
            CACHE_MAP.clear();
        }

        /**
         * 删除某个缓存
         * @param cacheName
         */
        public static void remove(String cacheName) {
            CACHE_MAP.remove(cacheName);
            CACHE_MAP.remove(cacheName + "_HoldTime");
        }

        /**
         * 检查缓存对象是否存在，
         * 若不存在，则返回false
         * 若存在，检查其是否已过有效期，如果已经过了则删除该缓存并返回false
         * @param cacheName
         * @return
         */
        private synchronized static boolean checkCacheName(String cacheName) {
            if(StringUtils.isEmpty(CACHE_MAP.get(cacheName + "_HoldTime"))){
                CACHE_MAP.remove(cacheName);
                return false;
            }
            long cacheHoldTime = Long.parseLong(CACHE_MAP.get(cacheName + "_HoldTime"));
            if (cacheHoldTime == 0L) {
                return false;
            }
            if (cacheHoldTime < System.currentTimeMillis()) {
                remove(cacheName);
                return false;
            }
            return true;
        }
}
