package com.blog.cache;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 项目缓存,如果有需要这边扩展成Redis等
 *
 * @author lijinzhou
 * @since 2017/12/20 19:08
 */
@Component
public class CacheUtils<T> {


    // start  用户拥有的权限临时缓存
    private Map<Integer, Set<String>> userAuthority = new HashMap<>();

    public Map<Integer, Set<String>> getUserAuthority() {
        return userAuthority;
    }

    public void clearUserAuthority() {
        userAuthority.clear();
    }

    public void putUserAuthority(Integer key, Set<String> userAuthorities) {
        this.getUserAuthority().put(key, userAuthorities);
    }

    public Set<String> getUserAuthority(Integer key) {
        return getUserAuthority().get(key);
    }
    // end  用户拥有的权限临时缓存








    // start 权限管理页面 内存缓存,做权限,增删改需要把这里缓存清除掉
    private Map<String, List<T>> authorities = new HashMap<>();

    public Map<String, List<T>> getAuthorities() {
        return authorities;
    }

    public void clearAuthorities() {
        authorities.clear();
    }

    public void putAuthorities(String key, List<T> authoritys) {
        this.getAuthorities().put(key, authoritys);
    }

    public List<T> getAuthorities(String key) {
        return this.getAuthorities().get(key);
    }
    // end 权限管理页面 内存缓存,做权限,增删改需要把这里缓存清除掉


}
