package com.jiazm.generate.bean;

public class ColumnData {
    private String columnName;
    private String firstUpperColumnName;
    private String dataType;
    private String dataTypeEnum;
    private String columnComment;
    private String columnCommentLong;
    private String columnType;
    private String charmaxLength = "";
    private String nullable;
    private String scale;
    private String precision;
    private String classType = "";
    private String columnDBName = "";
    private String tbColumnDBName = "";
    private String optionType = "";
    private String format = "";
    private String JsonName;
    private String columnDBQuotationMark;

    public String getFirstUpperColumnName() {
        return firstUpperColumnName;
    }

    public void setFirstUpperColumnName(String firstUpperColumnName) {
        this.firstUpperColumnName = firstUpperColumnName;
    }

    public String getDataTypeEnum() {
        return dataTypeEnum;
    }

    public void setDataTypeEnum(String dataTypeEnum) {
        this.dataTypeEnum = dataTypeEnum;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getColumnComment() {
        return this.columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public String getScale() {
        return this.scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getPrecision() {
        return this.precision;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }

    public String getClassType() {
        return this.classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getOptionType() {
        return this.optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public String getCharmaxLength() {
        return this.charmaxLength;
    }

    public void setCharmaxLength(String charmaxLength) {
        this.charmaxLength = charmaxLength;
    }

    public String getNullable() {
        return this.nullable;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
    }

    public String getColumnType() {
        return this.columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnDBName() {
        return columnDBName;
    }

    public void setColumnDBName(String columnDBName) {
        this.columnDBName = columnDBName;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getColumnCommentLong() {
        return columnCommentLong;
    }

    public void setColumnCommentLong(String columnCommentLong) {
        this.columnCommentLong = columnCommentLong;
    }

    public String getTbColumnDBName() {
        return tbColumnDBName;
    }

    public void setTbColumnDBName(String tbColumnDBName) {
        this.tbColumnDBName = tbColumnDBName;
    }

    public String getJsonName() {
        return JsonName;
    }

    public void setJsonName(String jsonName) {
        JsonName = jsonName;
    }

    public String getColumnDBQuotationMark() {
        return columnDBQuotationMark;
    }

    public void setColumnDBQuotationMark(String columnDBQuotationMark) {
        this.columnDBQuotationMark = columnDBQuotationMark;
    }

    public static class UniqueIndexColumn {

        private String indexName;
        private String indexDBColumn;

        public String getIndexName() {
            return indexName;
        }

        public void setIndexName(String indexName) {
            this.indexName = indexName;
        }

        public String getIndexDBColumn() {
            return indexDBColumn;
        }

        public void setIndexDBColumn(String indexDBColumn) {
            this.indexDBColumn = indexDBColumn;
        }
    }

}
