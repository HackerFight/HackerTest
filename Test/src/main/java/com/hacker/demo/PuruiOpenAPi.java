package com.hacker.demo;

import org.junit.Test;

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
    }
}
