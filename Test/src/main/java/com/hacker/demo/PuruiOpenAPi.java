package com.hacker.demo;

import org.junit.Test;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author Hacker
 * @date：2018/10/11
 * @project project
 * @describe
 */
public class PuruiOpenAPi {

    private static final String TABLE_NAME = "";
    private static final String VERSION = "";
    private static final String URL = "";
    private static final Object[] values = new Object[14];
    //可以获取参数名
    private static LocalVariableTableParameterNameDiscoverer discoverer;
    static {
        discoverer = new LocalVariableTableParameterNameDiscoverer();
    }

    public static void main(String[] args) {
        register(StationService.class);
    }

    /**
     * 注册：传入的是一个类实例:我们在注册的时候好像是以 实现类的参数名为准的，我遇到过一次，但是忘了
     *       通过环保的 openApi 注册类发现，应该就是以 实现类的参数名为准的!
     * @param clazz : 传入的是实现类
     *              重点在与 参数的组装：[{"name":"outTradeNo","type":"String","value":"1","order":1}]
     *              [{"name":"id","order":1,"type":"String","value":""},{"name":"_accountId","order":2,"type":"String","value":""}]
     */
    public static void register(Class clazz){
        //获取所有的方法
        Method[] declaredMethods = clazz.getDeclaredMethods();
        System.out.println("---------->" + Arrays.asList(declaredMethods.length));
        for (int i = 0; i < declaredMethods.length; i++) {
            String[] parameterNames = discoverer.getParameterNames(declaredMethods[i]);
            System.out.println(Arrays.asList(parameterNames));
        }
    }


    @Test
    public void test(){
        String sql = "INSERT INTO `openapi3.2`.`openapi_service` " +
                "(`service_name`," +
                " `method_name`, " +
                "`desc`, " +
                "`url`, " +
                "`back_service`, " +
                "`parameters`, " +
                "`version`, " +
                "`STATUS`, " +
                "`CREATOR`," +
                " `CREATE_TIME`, " +
                "`MODIFY_TIME`, " +
                "`approver`, " +
                "`approval_time`, " +
                "`SYS_RECORD_STATUS`) " +
                "VALUES (";

        System.out.println(sql);
        System.out.println("🌰");
    }
}
