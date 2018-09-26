package com.hacker.demo;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author Hacker
 * @date：2018/9/26
 * @project project
 * @describe
 */
public class Test01 {

    public JSONObject parseSZZHBJson() {
        //社渚镇环保局
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("concat", "");
        jsonObject.put("concatPosition", "");
        jsonObject.put("telephone", "");
        jsonObject.put("regionProvinceCode", "320000");
        jsonObject.put("regionCityCode", "320400");
        jsonObject.put("regionCountryCode", "320485");
        jsonObject.put("regionAddress", "江苏省常州市社渚镇");
        return jsonObject;
    }

    public JSONObject parseNDZHBJson() {
        //社渚镇环保局
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("concat", "");
        jsonObject2.put("concatPosition", "");
        jsonObject2.put("telephone", "");
        jsonObject2.put("regionProvinceCode", "320000");
        jsonObject2.put("regionCityCode", "320400");
        jsonObject2.put("regionCountryCode", "320484");
        jsonObject2.put("regionAddress", "江苏省常州市南渡镇");
        return jsonObject2;
    }


    /**
     * 单个站点迁移（包含企业 + 站点 + 企业字符串）
     */
    @Test
    public void singletonStationMigrate() {

        String companyStr = "2224\tCompanyService:1527145614674\t10108ae4a12f2cc1\t江苏三益科技有限公司\t三益科技\t03\t\t\t\t\t\t\t\t\t\t\t\t\t320000\t320400\t320481\t经济开发区北郊工业园三益路1号\t江苏省常州市溧阳市经济开发区北郊工业园三益路1号\t\t\t\t1\t\t\t2018-05-24 15:06:54\t2018-05-24 15:06:54";

        String stationStr = "2836\tassetaccount/t_powerclient:1527145732256\t320481000017\tCompanyService:1527145614674\t8555667789\t江苏三益科技有限公司\t三益科技\t\t31.495656\t119.462102\t江苏省常州市溧阳市经济开发区北郊工业园三益路1号\t320000\t320400\t320481\t经济开发区北郊工业园三益路1号\t403\t26\t715.000000\t0.000000\t\t0\t\t2018-05-24\t\t\t\t\t\tCompanyService:1479455327257\t1\t3\t\t2018-05-24 15:08:52\t2018-06-05 17:54:36\t\t80108ae4a1e33a81\t10108ae4a12f2cc1\t101089c95a78d51d";

        String[] strs = companyStr.split("\t");

        String[] stationArray = stationStr.split("\t");

        String sql = "INSERT INTO `customer3.2`.`t_customer_company` (`customer_id`, `parent_id`, `name`, `short_name`, `type`, " +
                "`introduction`, `registered_capital`, `artificial_person`, `main_businesses`, `business_scopes`," +
                " `main_products`, `logo`, `website`, `company_scale`, `email`, `telphone`, `fax`, `province_code`, " +
                "`city_code`, `county_code`, `street`, `address`, `postcode`, `industry_code`, `longitude`, `latitude`," +
                " `expand1`, `expand2`, `expand3`, `sys_update_time`, `sys_updater`, `sys_deleted`, `sys_hash`," +
                " `sys_avail_data`, `sys_create_time`, `sys_creator`) " +
                "VALUES ('" + strs[2] + "', NULL, '" + strs[3] + "', '" + strs[4] + "', '12003', NULL, " +
                "NULL, NULL, NULL, NULL, NULL, 'null', NULL, NULL, NULL, 'null', NULL, '"
                + strs[18] + "', '" + strs[19] + "', '" + strs[20] + "', '" + strs[21] + "', '" + strs[22] + "', NULL," +
                " '33', '" + stationArray[8] + "', '" + stationArray[9] + "', NULL, '203004', '" + parseNDZHBJson() + "', '" + strs[strs.length - 1] + "', \'" +
                " ', '0', NULL, '0', '" + strs[strs.length - 1] + "', ' ');\n";

        String sqlStation = "INSERT INTO `asset3.2`.`t_asset_station` (`station_id`, `station_name`, `short_name`, " +
                "`station_py`, `enterprise_id`, `address`, `province_code`, `city_code`, `county_code`, `street`," +
                " `latitude`, `longitude`, `establish_time`, `project_access_stage`, `sys_create_time`, `sys_creator`," +
                " `sys_update_time`, `sys_updater`, `sys_deleted`, `sys_hash`, `sys_avail_data`, `electric_category`) " +
                "VALUES ('" + stationArray[stationArray.length - 3] + "', '" + stationArray[5] + "', '" + stationArray[6] + "', NULL, '" + strs[2] + "', '" + stationArray[10] + "', " +
                "'" + stationArray[11] + "', '" + stationArray[12] + "', '" + stationArray[13] + "', '" + stationArray[14] + "', '" + stationArray[8] + "', '" + stationArray[9] + "', NULL, '9002', '" + stationArray[stationArray.length - 6] + "', " +
                "' ', '" + stationArray[stationArray.length - 6] + "', ' ', '0', NULL, '0', NULL);\n";

        String companyStarSql = "INSERT INTO `customer3.2`.`t_customer_character` (`customer_id`, `character_`, `sys_create_time`," +
                " `sys_creator`, `sys_update_time`, `sys_updater`, `sys_deleted`, `sys_hash`, `sys_avail_data`) " +
                "VALUES ('" + strs[2] + "', '10011', '2018-06-04 14:44:05', '', '2018-06-04 14:44:05', '', '0', NULL, '0');\n";
        String companyStarSql2 = "INSERT INTO `customer3.2`.`t_customer_character` (`customer_id`, `character_`, `sys_create_time`," +
                " `sys_creator`, `sys_update_time`, `sys_updater`, `sys_deleted`, `sys_hash`, `sys_avail_data`) " +
                "VALUES ('" + strs[2] + "', '10002', '2018-06-04 14:44:05', '', '2018-06-04 14:44:05', '', '0', NULL, '0');\n";

        System.out.println(sql);
        System.out.println();
        System.out.println(sqlStation);
        System.out.println();
        System.out.println(companyStarSql);
        System.out.println(companyStarSql2);
    }


    /**
     * 多个站点迁移（包含企业 + 站点 + 企业字符串）
     * 注意:要保证 企业 和 站点 一对一 对应，可以在 查询的时候都按照 企业id 排序
     */
    @Test
    public void moreStationMigrate() {

        /**
         * 在navicat 中复制多行数据，每一行的后面都有换行符的
         */
        String companyStr = "2224\tCompanyService:1527145614674\t10108ae4a12f2cc1\t江苏三益科技有限公司\t三益科技\t03\t\t\t\t\t\t\t\t\t\t\t\t\t320000\t320400\t320481\t经济开发区北郊工业园三益路1号\t江苏省常州市溧阳市经济开发区北郊工业园三益路1号\t\t\t\t1\t\t\t2018-05-24 15:06:54\t2018-05-24 15:06:54";
        String stationStr = "2836\tassetaccount/t_powerclient:1527145732256\t320481000017\tCompanyService:1527145614674\t8555667789\t江苏三益科技有限公司\t三益科技\t\t31.495656\t119.462102\t江苏省常州市溧阳市经济开发区北郊工业园三益路1号\t320000\t320400\t320481\t经济开发区北郊工业园三益路1号\t403\t26\t715.000000\t0.000000\t\t0\t\t2018-05-24\t\t\t\t\t\tCompanyService:1479455327257\t1\t3\t\t2018-05-24 15:08:52\t2018-06-05 17:54:36\t\t80108ae4a1e33a81\t10108ae4a12f2cc1\t101089c95a78d51d";

        StringBuilder companyContainer = new StringBuilder();
//        StringBuilder stationContainer = new StringBuilder();

        String[] strs = companyStr.split("\n");

        String[] stationArray = stationStr.split("\n");

        for (int i = 0; i < strs.length; i++) {
            //企业和站点是一一对应的关系，所以他们的数量应该是一样的
            List<String> stationList = Arrays.asList(stationArray[i].split("\t"));
            List<String> companyList = Arrays.asList(strs[i].split("\t"));
            if (stationList.contains(companyList.get(2))) {
                //\t  站点id， 经度， 维度
                companyContainer.append(strs[i]).append("\t").append(stationList.get(stationList.size() - 3)).append(stationList.get(8)).append(stationList.get(9));
            }
        }

        for (int i = 0; i < strs.length; i++) {
            String[] companyStrInfo = strs[i].split("\t");
            String sql = "INSERT INTO `customer3.2`.`t_customer_company` (`customer_id`, `parent_id`, `name`, `short_name`, `type`, " +
                    "`introduction`, `registered_capital`, `artificial_person`, `main_businesses`, `business_scopes`," +
                    " `main_products`, `logo`, `website`, `company_scale`, `email`, `telphone`, `fax`, `province_code`, " +
                    "`city_code`, `county_code`, `street`, `address`, `postcode`, `industry_code`, `longitude`, `latitude`," +
                    " `expand1`, `expand2`, `expand3`, `sys_update_time`, `sys_updater`, `sys_deleted`, `sys_hash`," +
                    " `sys_avail_data`, `sys_create_time`, `sys_creator`) " +
                    "VALUES ('" + companyStrInfo[2] + "', NULL, '" + companyStrInfo[3] + "', '" + companyStrInfo[4] + "', '12003', NULL, " +
                    "NULL, NULL, NULL, NULL, NULL, 'null', NULL, NULL, NULL, 'null', NULL, '"
                    + companyStrInfo[18] + "', '" + companyStrInfo[19] + "', '" + companyStrInfo[20] + "', '" + companyStrInfo[21] + "', '" + companyStrInfo[22] + "', NULL," +
                    " '33', '" + stationArray[8] + "', '" + stationArray[9] + "', NULL, '203004', '" + parseNDZHBJson() + "', '" + companyStrInfo[companyStrInfo.length - 1] + "', \'" +
                    " ', '0', NULL, '0', '" + companyStrInfo[companyStrInfo.length - 1] + "', ' ');\n";



            String companyStarSql = "INSERT INTO `customer3.2`.`t_customer_character` (`customer_id`, `character_`, `sys_create_time`," +
                    " `sys_creator`, `sys_update_time`, `sys_updater`, `sys_deleted`, `sys_hash`, `sys_avail_data`) " +
                    "VALUES ('" + companyStrInfo[2] + "', '10011', '2018-06-04 14:44:05', '', '2018-06-04 14:44:05', '', '0', NULL, '0');\n";
            String companyStarSql2 = "INSERT INTO `customer3.2`.`t_customer_character` (`customer_id`, `character_`, `sys_create_time`," +
                    " `sys_creator`, `sys_update_time`, `sys_updater`, `sys_deleted`, `sys_hash`, `sys_avail_data`) " +
                    "VALUES ('" + companyStrInfo[2] + "', '10002', '2018-06-04 14:44:05', '', '2018-06-04 14:44:05', '', '0', NULL, '0');\n";

            System.out.println(sql);
            System.out.println();
            System.out.println(companyStarSql);
            System.out.println();
            System.out.println(companyStarSql2);
        }


        for (int i = 0; i < stationArray.length; i++) {
            String[] stationSplit = stationArray[i].split("\t");

            String sqlStation = "INSERT INTO `asset3.2`.`t_asset_station` (`station_id`, `station_name`, `short_name`, " +
                    "`station_py`, `enterprise_id`, `address`, `province_code`, `city_code`, `county_code`, `street`," +
                    " `latitude`, `longitude`, `establish_time`, `project_access_stage`, `sys_create_time`, `sys_creator`," +
                    " `sys_update_time`, `sys_updater`, `sys_deleted`, `sys_hash`, `sys_avail_data`, `electric_category`) " +
                    "VALUES ('" + stationSplit[stationSplit.length - 3] + "', '" + stationSplit[5] + "', '" + stationSplit[6] + "', NULL, '" + stationSplit[stationSplit.length - 2] + "', '" + stationSplit[10] + "', " +
                    "'" + stationSplit[11] + "', '" + stationSplit[12] + "', '" + stationSplit[13] + "', '" + stationSplit[14] + "', '" + stationSplit[8] + "', '" + stationSplit[9] + "', NULL, '9002', '" + stationSplit[stationSplit.length - 6] + "', " +
                    "' ', '" + stationSplit[stationSplit.length - 6] + "', ' ', '0', NULL, '0', NULL);\n";

            System.out.println(sqlStation);
        }
    }
}
