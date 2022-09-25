package cn.gk.gmblog.adm.config;

/**
 * @Description: 表枚举
 * @Author: Mr.geek
 * @Date: 2021/11/3.
 */
public enum TableEnum {

    T_BUS_SAMPLE("t_bus_sample");

    private String tableName;

    private TableEnum(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }
}
