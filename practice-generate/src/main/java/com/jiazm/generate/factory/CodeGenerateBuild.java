package com.jiazm.generate.factory;

import com.jiazm.generate.bean.ColumnData;
import com.jiazm.generate.util.CodeResourceUtil;
import com.jiazm.generate.util.TableConvert;
import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 构建sql
 *
 * @author LiLong10
 * @date 2019-03-25
 * Copyright 2019 Lenovo GSC Technical Service
 */
public class CodeGenerateBuild {
    static String url;
    static String username;
    static String password;
    static String rt = "\r\t";

    static {
        try {
            Class.forName(CodeResourceUtil.DIVER_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMysqlInfo(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public List<ColumnData> getBeanFeildList(String tableName) throws SQLException {
        return getColumnDatas(tableName);
    }

    public List<ColumnData> getColumnDatas(String tableName) throws SQLException {
        String SQLColumns = null;

        if (CodeResourceUtil.DATABASE_TYPE == CodeResourceUtil.DATABASE_TYPE_MYSQL) {
            SQLColumns = String.format("select column_name ,data_type,column_comment,0,0,character_maximum_length,is_nullable nullable from information_schema.columns where table_name =  '%s' and table_schema =  '%s'  order by ordinal_position", tableName, CodeResourceUtil.DATABASE_NAME);
        } else if (CodeResourceUtil.DATABASE_TYPE == CodeResourceUtil.DATABASE_TYPE_ORACLE) {
            String[] split = tableName.split("\\.");
            String table_name = split.length > 1 ? split[1] : tableName;
            String database_name = split.length > 1 ? split[0] : CodeResourceUtil.DATABASE_NAME;
            SQLColumns = String.format("select t.column_name ,t.data_type,c.comments,0,0,t.data_length,t.nullable from all_tab_columns t,all_col_comments c where t.table_name = '%s' and t.owner = '%s' and t.table_name = c.table_name and t.column_name = c.column_name", table_name, database_name);
        } else if (CodeResourceUtil.DATABASE_TYPE == CodeResourceUtil.DATABASE_TYPE_POSTGRESQL) {
            SQLColumns = String.format("SELECT col.COLUMN_NAME,col.data_type,des.description,col.numeric_precision,col.numeric_scale,col.character_maximum_length,col.is_nullable FROM information_schema.COLUMNS col LEFT JOIN pg_description des ON col.TABLE_NAME :: regclass=des.objoid AND col.ordinal_position=des.objsubid WHERE TABLE_NAME='%s' AND table_schema='%s' ORDER BY ordinal_position;", tableName, CodeResourceUtil.DATABASE_NAME);
        }

        Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(SQLColumns);
        List columnList = new ArrayList();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String name = rs.getString(1);
            String columnName = getFieldNameToPropertyName(name.toLowerCase());
            String type = rs.getString(2);
            String comment = rs.getString(3);
            String precision = rs.getString(4);
            String scale = rs.getString(5);
            String charMaxLength = rs.getString(6) == null ? "" : rs.getString(6);
            String nullable = TableConvert.getNullAble(rs.getString(7));
            String dataTypeEnum = dataTypeEnum(type);
            String dataType = getType(type, precision, scale);

            ColumnData cd = new ColumnData();
            cd.setColumnName(columnName);
            cd.setFirstUpperColumnName(getTablesNameToClassName(columnName));
            cd.setDataType(dataType);
            cd.setDataTypeEnum(dataTypeEnum);
            cd.setColumnType(type);
            cd.setPrecision(precision);
            cd.setScale(scale);
            cd.setCharmaxLength(charMaxLength);
            cd.setNullable(nullable);
            cd.setColumnDBName(name);
            cd.setColumnDBQuotationMark(getColumnDBQuotationMark(name));
            cd.setTbColumnDBName(tableName + "." + cd.getColumnDBQuotationMark());
            setComment(cd, comment, name);
            columnList.add(cd);
        }
        rs.close();
        ps.close();
        con.close();
        return columnList;
    }

    public List<ColumnData> getUniqueIndexColumn(String tableName, List<ColumnData> columnDatas) throws SQLException {
        String SQLColumns = null;

        if (CodeResourceUtil.DATABASE_TYPE == CodeResourceUtil.DATABASE_TYPE_MYSQL) {
            SQLColumns = String.format("SHOW INDEX FROM %s WHERE non_unique = 0 AND key_name != 'PRIMARY'", tableName);
        } else if (CodeResourceUtil.DATABASE_TYPE == CodeResourceUtil.DATABASE_TYPE_ORACLE) {
            String[] split = tableName.split("\\.");
            String table_name = split.length > 1 ? split[1] : tableName;
            String database_name = split.length > 1 ? split[0] : CodeResourceUtil.DATABASE_NAME;
            SQLColumns = String.format("select col.CONSTRAINT_NAME key_name,col.COLUMN_NAME Column_name from user_constraints con,user_cons_columns col where con.constraint_name=col.constraint_name and con.constraint_type='P' and col.table_name='%s' and col.OWNER='%s'", table_name, database_name);
        } else if (CodeResourceUtil.DATABASE_TYPE == CodeResourceUtil.DATABASE_TYPE_POSTGRESQL) {
            SQLColumns = String.format("SELECT tnsp.nspname,trel.relname,irel.relname AS key_name,A.attname AS COLUMN_NAME FROM pg_index AS i JOIN pg_class AS trel ON trel.oid=i.indrelid JOIN pg_namespace AS tnsp ON trel.relnamespace=tnsp.oid JOIN pg_class AS irel ON irel.oid=i.indexrelid CROSS JOIN LATERAL UNNEST (i.indkey) WITH ORDINALITY AS C (colnum,ORDINALITY) JOIN pg_attribute AS A ON trel.oid=A.attrelid AND A.attnum=C.colnum WHERE trel.relname='%s' AND tnsp.nspname='%s' AND i.indisprimary !='t' GROUP BY tnsp.nspname,trel.relname,irel.relname,A.attname", tableName, CodeResourceUtil.DATABASE_NAME);
        }

        Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(SQLColumns);
        List<ColumnData.UniqueIndexColumn> indexList = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            ColumnData.UniqueIndexColumn cd = new ColumnData.UniqueIndexColumn();
            cd.setIndexName(rs.getString("key_name"));
            cd.setIndexDBColumn(rs.getString("Column_name"));
            indexList.add(cd);
        }
        rs.close();
        ps.close();
        con.close();

        if (indexList.isEmpty()) return null;

        List<ColumnData> uniqueColumnDatas = new ArrayList<>();

        Map<String, List<ColumnData.UniqueIndexColumn>> uniqueIndexMap = indexList.stream().collect(Collectors.groupingBy(ColumnData.UniqueIndexColumn::getIndexName));
        //TODO 只返回第一个，暂定一般情况不会出现多个唯一
        List<ColumnData.UniqueIndexColumn> uniqueIndexColumns = uniqueIndexMap.entrySet().iterator().next().getValue();
        uniqueIndexColumns.forEach(uniqueIndexColumn -> {
            columnDatas.stream().filter(columnData -> uniqueIndexColumn.getIndexDBColumn().equals(columnData.getColumnDBName())).forEach(uniqueColumnDatas::add);
        });
        return uniqueColumnDatas;
    }

    private String getColumnDBQuotationMark(String ColumnDBName) {
        String quotationMark = null;
        if (CodeResourceUtil.DATABASE_TYPE == CodeResourceUtil.DATABASE_TYPE_MYSQL) {
            quotationMark = "`";
        } else if (CodeResourceUtil.DATABASE_TYPE == CodeResourceUtil.DATABASE_TYPE_ORACLE) {
            quotationMark = "\"";
        } else if (CodeResourceUtil.DATABASE_TYPE == CodeResourceUtil.DATABASE_TYPE_POSTGRESQL) {
            quotationMark = "\"";
        }
        return String.format("%s%s%s", quotationMark, ColumnDBName, quotationMark);
    }

    private void setComment(ColumnData cd, String comment, String name) {
        if (StringUtils.isNotBlank(comment)) {
            comment = comment.trim();
            String[] t = comment.split(":|：");
            cd.setColumnComment(t[0]);
            cd.setColumnCommentLong(comment);
            String[] j = comment.split("##");
            if (j.length > 1) cd.setJsonName(j[1]);
        } else {
            cd.setColumnComment(name);
            cd.setColumnCommentLong(name);
        }
    }

    private String dataTypeEnum(String type) {
        String dataTypeEnum;
        switch (type) {
            case "tinyint":
                dataTypeEnum = type;
                break;
            case "decimal":
                dataTypeEnum = type;
                break;
            case "date":
                dataTypeEnum = type;
                break;
            case "datetime":
                dataTypeEnum = type;
                break;
            default:
                dataTypeEnum = null;
                break;
        }
        return dataTypeEnum;
    }

    private String getType(String dataType, String precision, String scale) {
        dataType = dataType.toLowerCase();

        if (dataType.contains("char") || dataType.contains("text"))
            dataType = "String";
        else if (dataType.contains("bigint"))
            dataType = "Long";
        else if (dataType.contains("int"))
            dataType = "Integer";
        else if (dataType.contains("float"))
            dataType = "Float";
        else if (dataType.contains("double"))
            dataType = "Double";
        else if (dataType.contains("number")) {
            if ((StringUtils.isNotBlank(scale))
                    && (Integer.parseInt(scale) > 0))
                dataType = "BigDecimal";
            else if ((StringUtils.isNotBlank(precision))
                    && (Integer.parseInt(precision) > 6))
                dataType = "Long";
            else
                dataType = "Integer";
        } else if (dataType.contains("decimal"))
            dataType = "BigDecimal";
        else if (dataType.contains("date"))
            dataType = "Date";
        else if (dataType.contains("time"))
            dataType = "Date";
        else if (dataType.contains("clob"))
            dataType = "java.sql.Clob";
        else dataType = "java.lang.Object";

        return dataType;
    }

    public String getTablesNameToClassName(String tableName) {
        String[] split = tableName.split("_");
        return Arrays.stream(split).map(s -> s.substring(0, 1).toUpperCase() + s.substring(1)).collect(Collectors.joining());
    }

    public String getFieldNameToPropertyName(String fieldName) {
        fieldName = getTablesNameToClassName(fieldName);
        return fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
    }

    private String getColumnFields(List<ColumnData> columnList, String tableName) {
        String sb = columnList.stream().map(data -> "," + tableName + "." + data.getColumnDBQuotationMark()).collect(Collectors.joining());
        return sb.substring(1);
    }

    private String[] getColumnDBList(List<ColumnData> columnList) {
        return columnList.stream().map(ColumnData::getColumnDBQuotationMark).toArray(String[]::new);
    }

    private String[] getColumnList(List<ColumnData> columnList) {
        return columnList.stream().map(ColumnData::getColumnName).toArray(String[]::new);
    }

    public Map<String, Object> getAutoCreateSql(String tableName, List<ColumnData> columnDatas) throws Exception {

        String[] columnList = getColumnList(columnDatas);
        String[] columnDBList = getColumnDBList(columnDatas);
        String tbColumnDBFields = getColumnFields(columnDatas, tableName);

        String insert = getInsertSql(tableName, columnList, columnDBList);
        String selectById = getSelectByIdSql(tableName, columnList, columnDBList);
        String deleteById = getDeleteByIdSql(tableName, columnList, columnDBList);
        String updateById = getUpdateByIdSql(tableName, columnDatas);

        Map sqlMap = new HashMap();
        sqlMap.put("columnFields", tbColumnDBFields);
        sqlMap.put("insert", insert);
        sqlMap.put("updateById", updateById);
        sqlMap.put("deleteById", deleteById);
        sqlMap.put("selectById", selectById);

        List<ColumnData> uniqueIndexColumns = getUniqueIndexColumn(tableName, columnDatas);
        if (uniqueIndexColumns != null && !uniqueIndexColumns.isEmpty()) {
            String selectByUniqueIndexSql = getSelectByUniqueIndexSql(tableName, uniqueIndexColumns);
            String updateByUniqueIndexSql = getUpdateByUniqueIndexSql(tableName, columnDatas, uniqueIndexColumns);
            String delByUniqueIndexSql = getDelByUniqueIndexSql(tableName, uniqueIndexColumns);

            sqlMap.put("selectByUniqueIndexSql", selectByUniqueIndexSql);
            sqlMap.put("updateByUniqueIndexSql", updateByUniqueIndexSql);
            sqlMap.put("delByUniqueIndexSql", delByUniqueIndexSql);
        }

        return sqlMap;
    }

    private String getInsertSql(String tableName, String[] columns, String[] columnsDB) {
        List<String> columnList = Arrays.asList(columns).subList(1, columns.length);
        List<String> columnsDBList = Arrays.asList(columnsDB).subList(1, columnsDB.length);
        return String.format("insert into %s(%s)%s values(#{%s})", tableName, String.join(",", columnsDBList), rt, String.join("},#{", columnList));
    }

    private String getDeleteByIdSql(String tableName, String[] columnsList, String[] columnDBList) {
        return String.format("delete from %s where %s = #{%s}", tableName, columnDBList[0], columnsList[0]);
    }

    private String getSelectByIdSql(String tableName, String[] columnsList, String[] columnDBList) {
        return String.format("select <include refid=\"Column_List\" /> from %s where %s = #{%s}", tableName, columnDBList[0], columnsList[0]);
    }

    private String getUpdateByIdSql(String tableName, List<ColumnData> columnList) {
        ColumnData idColumn = columnList.get(0);
        String sb = IntStream.range(1, columnList.size()).mapToObj(columnList::get).map(data -> String.format("        <if test=\"%s != null \">%s            %s=#{%s},%s        </if>%s", data.getColumnName(), rt, data.getColumnDBQuotationMark(), data.getColumnName(), rt, rt)).collect(Collectors.joining());
        return String.format("update %s set %s    <trim  suffixOverrides=\",\" >%s%s    </trim> where %s=#{%s}", tableName, rt, rt, sb, idColumn.getColumnDBQuotationMark(), idColumn.getColumnName());
    }

    private String getSelectByUniqueIndexSql(String tableName, List<ColumnData> uniqueIndexColumns) {
        String where = uniqueIndexColumns.stream().map(data -> String.format("and %s = #{%s} ", data.getColumnDBQuotationMark(), data.getColumnName())).collect(Collectors.joining());
        String sql = String.format("select <include refid=\"Column_List\" /> from %s where %s", tableName, where.substring(4));
        return sql;
    }

    private String getDelByUniqueIndexSql(String tableName, List<ColumnData> uniqueIndexColumns) {
        String where = uniqueIndexColumns.stream().map(data -> String.format("and %s = #{%s} ", data.getColumnDBQuotationMark(), data.getColumnName())).collect(Collectors.joining());
        String sql = String.format("delete from %s where %s", tableName, where.substring(4));
        return sql;
    }

    private String getUpdateByUniqueIndexSql(String tableName, List<ColumnData> columnList, List<ColumnData> uniqueIndexColumns) {
        String where = uniqueIndexColumns.stream().map(data -> String.format("and %s = #{%s} ", data.getColumnDBQuotationMark(), data.getColumnName())).collect(Collectors.joining());
        String sb = IntStream.range(1, columnList.size()).mapToObj(columnList::get).filter(columnData -> !where.contains("{" + columnData.getColumnName() + "}")).map(data -> String.format("        <if test=\"%s != null \">%s            %s=#{%s},%s        </if>%s", data.getColumnName(), rt, data.getColumnDBQuotationMark(), data.getColumnName(), rt, rt)).collect(Collectors.joining());
        String sql = String.format("update %s set %s    <trim  suffixOverrides=\",\" >%s%s    </trim> where %s", tableName, rt, rt, sb, where.substring(4));
        return sql;
    }
}
