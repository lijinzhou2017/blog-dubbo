package com.blog.basedao;

import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;


/**
 * 基础 Mapper.
 * <p>
 * T：实体类对象
 * 如有扩展，使用继承多接口模式
 *
 * @author lijinzhou
 * @since 2017/12/11 11:54
 */
public interface IBaseDao<T,k> extends Mapper<T>, IdsMapper<T>, MySqlMapper<T>, ExampleMapper<T> {
/*
    */
/**
     * 根据实体类不为null的字段进行查询,条件全部使用=号and条件
     *//*

    List<T> select(T record);

    */
/**
     * 根据实体类不为null的字段查询总数,条件全部使用=号and条件
     *//*

    int selectCount(T record);

    */
/**
     * 根据主键进行查询,必须保证结果唯一
     * 单个字段做主键时,可以直接写主键的值
     * 联合主键时,key可以是实体类,也可以是Map
     *//*

    T selectByPrimaryKey(Object key);

    */
/**
     * 插入一条数据
     * 支持Oracle序列,UUID,类似Mysql的INDENTITY自动增长(自动回写)
     * 优先使用传入的参数值,参数值空时,才会使用序列、UUID,自动增长
     *//*

    int insert(T record);

    */
/**
     * 插入一条数据,只插入不为null的字段,不会影响有默认值的字段
     * 支持Oracle序列,UUID,类似Mysql的INDENTITY自动增长(自动回写)
     * 优先使用传入的参数值,参数值空时,才会使用序列、UUID,自动增长
     *//*

    int insertSelective(T record);

    */
/**
     * 根据实体类中字段不为null的条件进行删除,条件全部使用=号and条件
     *//*

    int delete(T key);

    */
/**
     * 通过主键进行删除,这里最多只会删除一条数据
     * 单个字段做主键时,可以直接写主键的值
     * 联合主键时,key可以是实体类,也可以是Map
     *//*

    int deleteByPrimaryKey(Object key);

    */
/**
     * 根据主键进行更新,这里最多只会更新一条数据
     *//*

    int updateByPrimaryKey(T record);

    */
/**
     * 根据主键进行更新
     * 只会更新不是null的数据
     *//*

    int updateByPrimaryKeySelective(T record);

    */
/**
     * 根据Exmaple条件查询总数
     *//*

    int selectCountByExample(Object example);

    */
/**
     * 根据Exmaple条件删除
     *//*

    int deleteByExample(Object example);

    */
/**
     * 根据Exmaple条件查询
     *//*

    List<T> selectByExample(Object example);
*/

}