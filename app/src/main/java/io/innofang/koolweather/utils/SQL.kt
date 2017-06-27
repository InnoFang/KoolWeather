package io.innofang.koolweather.utils

/**
 * Author: Inno Fang
 * Time: 2017/6/27 09:57
 * Description:
 */


/**
 * Author: Inno Fang
 * Description: build SQL to create a table
 */
object SQL {

    private val mSql = StringBuilder()
    private val PRIMARY_KEY = " primary key "
    private val AUTOINCREMENT = " autoincrement "
    private val LEFT_BRACKET = "("
    private val RIGHT_BRACKET = ")"
    private val COMMA = ", "
    private val INTEGER = " integer "
    private val VARCHAR = " varchar(10) "
    private val FLOAT = " float "
    private val DOUBLE = " double "
    private val CHAR = " char(10) "
    private val TEXT = " text "


    fun createTable(tableName: String): SQLBuilder {
        return SQLBuilder(tableName)
    }

    class SQLBuilder(tableName: String) {

        init {
            mSql.append("create table ")
                    .append(tableName)
                    .append(LEFT_BRACKET)
        }

        fun addIntegerCols(cols: String): SQLBuilder {
            checkComma()
            mSql.append(cols)
                    .append(INTEGER)
            return this
        }

        fun addIntegerColsWithPrimaryKey(cols: String, useAutoincrement: Boolean): SQLBuilder {
            checkComma()
            mSql.append(cols)
                    .append(INTEGER)
                    .append(PRIMARY_KEY)
            checkAutoincrement(useAutoincrement)
            return this
        }

        fun addVarCharCols(cols: String): SQLBuilder {
            checkComma()
            mSql.append(cols)
                    .append(VARCHAR)
            return this
        }

        fun addVarCharCols(cols: String, length: Int): SQLBuilder {
            checkComma()
            mSql.append(cols)
                    .append(" varchar($length) ")
            return this
        }

        fun addVarCharColsWithPrimaryKey(cols: String): SQLBuilder {
            checkComma()
            mSql.append(cols)
                    .append(VARCHAR)
                    .append(PRIMARY_KEY)
            return this
        }

        fun addVarCharColsWithPrimaryKey(cols: String, length: Int): SQLBuilder {
            checkComma()
            mSql.append(cols)
                    .append(" varchar($length) ")
                    .append(PRIMARY_KEY)
            return this
        }


        fun addCharCols(cols: String): SQLBuilder {
            checkComma()
            mSql.append(cols)
                    .append(CHAR)
            return this
        }

        fun addCharCols(cols: String, length: Int): SQLBuilder {
            checkComma()
            mSql.append(cols)
                    .append(" varchar($length) ")
            return this
        }


        fun addCharColsWithPrimaryKey(cols: String): SQLBuilder {
            checkComma()
            mSql.append(cols)
                    .append(CHAR)
                    .append(PRIMARY_KEY)
            return this
        }

        fun addCharColsWithPrimaryKey(cols: String, length: Int): SQLBuilder {
            checkComma()
            mSql.append(cols)
                    .append(" char($length) ")
                    .append(PRIMARY_KEY)
            return this
        }


        fun addFloatCols(cols: String): SQLBuilder {
            checkComma()
            mSql.append(cols)
                    .append(FLOAT)
            return this
        }

        fun addFloatColsWithPrimaryKey(cols: String): SQLBuilder {
            checkComma()
            mSql.append(cols)
                    .append(FLOAT)
                    .append(PRIMARY_KEY)
            return this
        }


        fun addDoubleCols(cols: String): SQLBuilder {
            checkComma()
            mSql.append(cols)
                    .append(DOUBLE)
            return this
        }

        fun addDoubleColsWithPrimaryKey(cols: String): SQLBuilder {
            checkComma()
            mSql.append(cols)
                    .append(DOUBLE)
                    .append(PRIMARY_KEY)
            return this
        }

        fun addTextCols(cols: String): SQLBuilder {
            checkComma()
            mSql.append(cols)
                    .append(TEXT)
            return this
        }

        fun addTextColsWithPrimaryKey(cols: String): SQLBuilder {
            checkComma()
            mSql.append(cols)
                    .append(TEXT)
                    .append(PRIMARY_KEY)
            return this
        }


        fun create(): String {
            return mSql.append(RIGHT_BRACKET)
                    .toString()
                    .replace("  ", " ")
        }
    }

    private fun checkComma() {
        if (mSql[mSql.length - 1] != LEFT_BRACKET[0]) {
            mSql.append(COMMA)
        }
    }

    private fun checkAutoincrement(useAutoincrement: Boolean) {
        if (useAutoincrement)
            mSql.append(AUTOINCREMENT)
    }

}