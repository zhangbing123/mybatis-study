package com.tuling.plugins;

import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.testcontainers.shaded.com.google.common.base.Joiner;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Properties;

/***
 * 自定义插件
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
@Intercepts({@Signature(type = Executor.class, method = "query", args = {
        MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class
})})
//@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class ExamplePlugin implements Interceptor {

    private static final int MS_INDEX = 0;
    private static final int PARAM_INDEX = 1;
    private static final int RB_INDEX = 2;
    private static final int RH_INDEX = 3;

    /**
     * 自定义拦截器  拦截Executor的query()的方法 拦截带有三个参数的方法
     * 在此方法中获取查询的sql 并修改sql 模拟实现分页查询
     * @param invocation
     * @return
     * @throws Throwable
     */
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("代理");
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[MS_INDEX];
        //sql的参数
        Object param = args[PARAM_INDEX];

        BoundSql boundSql = ms.getBoundSql(param);
        if (SqlCommandType.SELECT == ms.getSqlCommandType()){
            //只有查询语句才进行分页  再次修改sql
            String sql = boundSql.getSql();
            sql = sql+" limit 0,10";

            //通过反射修改sql语句
            Field field = boundSql.getClass().getDeclaredField("sql");
            field.setAccessible(true);
            field.set(boundSql,sql);
            args[MS_INDEX] = newMappedStatement(ms,boundSql);

        }

        return invocation.proceed();
    }

    /**
     * 创建 MappedStatement
     * @param ms
     * @param newBoundSql
     * @return
     */
    private MappedStatement newMappedStatement(MappedStatement ms, BoundSql newBoundSql) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(),
                new StaticSqlSource(ms.getConfiguration(),newBoundSql.getSql(),newBoundSql.getParameterMappings()), ms.getSqlCommandType());
        builder.keyColumn(delimitedArrayToString(ms.getKeyColumns()));
        builder.keyGenerator(ms.getKeyGenerator());
        builder.keyProperty(delimitedArrayToString(ms.getKeyProperties()));
        builder.lang(ms.getLang());
        builder.resource(ms.getResource());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.resultOrdered(ms.isResultOrdered());
        builder.resultSets(delimitedArrayToString(ms.getResultSets()));
        builder.resultSetType(ms.getResultSetType());
        builder.timeout(ms.getTimeout());
        builder.statementType(ms.getStatementType());
        builder.useCache(ms.isUseCache());
        builder.cache(ms.getCache());
        builder.databaseId(ms.getDatabaseId());
        builder.fetchSize(ms.getFetchSize());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        return builder.build();
    }

    public String delimitedArrayToString(String[] array) {

        if (array == null || array.length == 0) {
            return "";
        }
        Joiner joiner = Joiner.on(",");
        return joiner.join(array);
    }

    // new4大对象的时候调用，所以4大对象都会被代理到Plugin
    public Object plugin(Object target) {
        System.out.println("调用自定义插件的plugin()方法");
        return Plugin.wrap(target, this);
    }

    // 加载的时候调用， 设置属性初始化
    public void setProperties(Properties properties) {
        System.out.println("执行自定义插件的setProperties()方法");
    }
}