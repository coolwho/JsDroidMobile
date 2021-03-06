package com.jsdroid.editor.parser;

import com.jsdroid.antlr4.mysql.MySqlLexer;
import com.jsdroid.editor.tip.Tip;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Token;

import java.io.IOException;

public class SqlParser extends AbstractParser {
    static String[] keywords = new String[]{"ADD", "ALL", "ALTER", "ANALYZE",
            "AND", "AS", "ASC", "BEFORE", "BETWEEN", "BOTH", "BY",
            "CALL", "CASCADE", "CASE", "CAST", "CHANGE", "CHARACTER",
            "CHECK", "COLLATE", "COLUMN", "CONDITION", "CONSTRAINT", "CONTINUE",
            "CONVERT", "CREATE", "CROSS", "CURRENT_USER", "CURSOR", "DATABASE",
            "DATABASES", "DECLARE", "DEFAULT", "DELAYED", "DELETE", "DESC",
            "DESCRIBE", "DETERMINISTIC", "DISTINCT", "DISTINCTROW", "DROP",
            "EACH", "ELSE", "ELSEIF", "ENCLOSED", "ESCAPED", "EXISTS",
            "EXIT", "EXPLAIN", "FALSE", "FETCH", "FOR", "FORCE", "FOREIGN",
            "FROM", "FULLTEXT", "GRANT", "GROUP", "HAVING", "HIGH_PRIORITY",
            "IF", "IGNORE", "IN", "INDEX", "INFILE", "INNER", "INOUT",
            "INSERT", "INTERVAL", "INTO", "IS", "ITERATE", "JOIN", "KEY",
            "KEYS", "KILL", "LEADING", "LEAVE", "LEFT", "LIKE", "LIMIT",
            "LINEAR", "LINES", "LOAD", "LOCK", "LOOP", "LOW_PRIORITY",
            "MASTER_BIND", "MASTER_SSL_VERIFY_SERVER_CERT", "MATCH", "MAXVALUE",
            "MODIFIES", "NATURAL", "NOT", "NO_WRITE_TO_BINLOG", "NULL",
            "ON", "OPTIMIZE", "OPTION", "OPTIONALLY", "OR", "ORDER",
            "OUT", "OUTER", "OUTFILE", "PARTITION", "PRIMARY", "PROCEDURE",
            "PURGE", "RANGE", "READ", "READS", "REFERENCES", "REGEXP",
            "RELEASE", "RENAME", "REPEAT", "REPLACE", "REQUIRE", "RESTRICT",
            "RETURN", "REVOKE", "RIGHT", "RLIKE", "SCHEMA", "SCHEMAS",
            "SELECT", "SET", "SEPARATOR", "SHOW", "SPATIAL", "SQL", "SQLEXCEPTION",
            "SQLSTATE", "SQLWARNING", "SQL_BIG_RESULT", "SQL_CALC_FOUND_ROWS",
            "SQL_SMALL_RESULT", "SSL", "STARTING", "STRAIGHT_JOIN", "TABLE",
            "TERMINATED", "THEN", "TO", "TRAILING", "TRIGGER", "TRUE",
            "UNDO", "UNION", "UNIQUE", "UNLOCK", "UNSIGNED", "UPDATE",
            "USAGE", "USE", "USING", "VALUES", "WHEN", "WHERE", "WHILE",
            "WITH", "WRITE", "XOR", "ZEROFILL", "TINYINT", "SMALLINT",
            "MEDIUMINT", "INT", "INTEGER", "BIGINT", "REAL", "DOUBLE",
            "FLOAT", "DECIMAL", "NUMERIC", "DATE", "TIME", "TIMESTAMP",
            "DATETIME", "YEAR", "CHAR", "VARCHAR", "BINARY", "VARBINARY",
            "TINYBLOB", "BLOB", "MEDIUMBLOB", "LONGBLOB", "TINYTEXT", "TEXT",
            "MEDIUMTEXT", "LONGTEXT", "ENUM", "YEAR_MONTH", "DAY_HOUR",
            "DAY_MINUTE", "DAY_SECOND", "HOUR_MINUTE", "HOUR_SECOND", "MINUTE_SECOND",
            "SECOND_MICROSECOND", "MINUTE_MICROSECOND", "HOUR_MICROSECOND",
            "DAY_MICROSECOND", "AVG", "BIT_AND", "BIT_OR", "BIT_XOR", "COUNT",
            "GROUP_CONCAT", "MAX", "MIN", "STD", "STDDEV", "STDDEV_POP",
            "STDDEV_SAMP", "SUM", "VAR_POP", "VAR_SAMP", "VARIANCE", "CURRENT_DATE",
            "CURRENT_TIME", "CURRENT_TIMESTAMP", "LOCALTIME", "CURDATE",
            "CURTIME", "DATE_ADD", "DATE_SUB", "EXTRACT", "LOCALTIMESTAMP",
            "NOW", "POSITION", "SUBSTR", "SUBSTRING", "SYSDATE", "TRIM",
            "UTC_DATE", "UTC_TIME", "UTC_TIMESTAMP", "ACCOUNT", "ACTION",
            "AFTER", "AGGREGATE", "ALGORITHM", "ANY", "AT", "AUTHORS",
            "AUTOCOMMIT", "AUTOEXTEND_SIZE", "AUTO_INCREMENT", "AVG_ROW_LENGTH",
            "BEGIN", "BINLOG", "BIT", "BLOCK", "BOOL", "BOOLEAN", "BTREE",
            "CACHE", "CASCADED", "CHAIN", "CHANGED", "CHANNEL", "CHECKSUM",
            "CIPHER", "CLIENT", "CLOSE", "COALESCE", "CODE", "COLUMNS",
            "COLUMN_FORMAT", "COMMENT", "COMMIT", "COMPACT", "COMPLETION",
            "COMPRESSED", "COMPRESSION", "CONCURRENT", "CONNECTION", "CONSISTENT",
            "CONTAINS", "CONTEXT", "CONTRIBUTORS", "COPY", "CPU", "DATA",
            "DATAFILE", "DEALLOCATE", "DEFAULT_AUTH", "DEFINER", "DELAY_KEY_WRITE",
            "DES_KEY_FILE", "DIRECTORY", "DISABLE", "DISCARD", "DISK",
            "DO", "DUMPFILE", "DUPLICATE", "DYNAMIC", "ENABLE", "ENCRYPTION",
            "END", "ENDS", "ENGINE", "ENGINES", "ERROR", "ERRORS", "ESCAPE",
            "EVEN", "EVENT", "EVENTS", "EVERY", "EXCHANGE", "EXCLUSIVE",
            "EXPIRE", "EXPORT", "EXTENDED", "EXTENT_SIZE", "FAST", "FAULTS",
            "FIELDS", "FILE_BLOCK_SIZE", "FILTER", "FIRST", "FIXED", "FLUSH",
            "FOLLOWS", "FOUND", "FULL", "FUNCTION", "GENERAL", "GLOBAL",
            "GRANTS", "GROUP_REPLICATION", "HANDLER", "HASH", "HELP", "HOST",
            "HOSTS", "IDENTIFIED", "IGNORE_SERVER_IDS", "importInfo", "INDEXES",
            "INITIAL_SIZE", "INPLACE", "INSERT_METHOD", "INSTALL", "INSTANCE",
            "INVOKER", "IO", "IO_THREAD", "IPC", "ISOLATION", "ISSUER",
            "JSON", "KEY_BLOCK_SIZE", "LANGUAGE", "LAST", "LEAVES", "LESS",
            "LEVEL", "LIST", "LOCAL", "LOGFILE", "LOGS", "MASTER", "MASTER_AUTO_POSITION",
            "MASTER_CONNECT_RETRY", "MASTER_DELAY", "MASTER_HEARTBEAT_PERIOD",
            "MASTER_HOST", "MASTER_LOG_FILE", "MASTER_LOG_POS", "MASTER_PASSWORD",
            "MASTER_PORT", "MASTER_RETRY_COUNT", "MASTER_SSL", "MASTER_SSL_CA",
            "MASTER_SSL_CAPATH", "MASTER_SSL_CERT", "MASTER_SSL_CIPHER", "MASTER_SSL_CRL",
            "MASTER_SSL_CRLPATH", "MASTER_SSL_KEY", "MASTER_TLS_VERSION", "MASTER_USER",
            "MAX_CONNECTIONS_PER_HOUR", "MAX_QUERIES_PER_HOUR", "MAX_ROWS",
            "MAX_SIZE", "MAX_UPDATES_PER_HOUR", "MAX_USER_CONNECTIONS", "MEDIUM",
            "MERGE", "MID", "MIGRATE", "MIN_ROWS", "MODE", "MODIFY",
            "MUTEX", "MYSQL", "NAME", "NAMES", "NCHAR", "NEVER", "NEXT",
            "NO", "NODEGROUP", "NONE", "OFFLINE", "OFFSET", "OJ", "OLD_PASSWORD",
            "ONE", "ONLINE", "ONLY", "OPEN", "OPTIMIZER_COSTS", "OPTIONS",
            "OWNER", "PACK_KEYS", "PAGE", "PARSER", "PARTIAL", "PARTITIONING",
            "PARTITIONS", "PASSWORD", "PHASE", "PLUGIN", "PLUGIN_DIR",
            "PLUGINS", "PORT", "PRECEDES", "PREPARE", "PRESERVE", "PREV",
            "PROCESSLIST", "PROFILE", "PROFILES", "PROXY", "QUERY", "QUICK",
            "REBUILD", "RECOVER", "REDO_BUFFER_SIZE", "REDUNDANT", "RELAY",
            "RELAY_LOG_FILE", "RELAY_LOG_POS", "RELAYLOG", "REMOVE", "REORGANIZE",
            "REPAIR", "REPLICATE_DO_DB", "REPLICATE_DO_TABLE", "REPLICATE_IGNORE_DB",
            "REPLICATE_IGNORE_TABLE", "REPLICATE_REWRITE_DB", "REPLICATE_WILD_DO_TABLE",
            "REPLICATE_WILD_IGNORE_TABLE", "REPLICATION", "RESET", "RESUME",
            "RETURNS", "ROLLBACK", "ROLLUP", "ROTATE", "ROW", "ROWS",
            "ROW_FORMAT", "SAVEPOINT", "SCHEDULE", "SECURITY", "SERVER",
            "SESSION", "SHARE", "SHARED", "SIGNED", "SIMPLE", "SLAVE",
            "SLOW", "SNAPSHOT", "SOCKET", "SOME", "SONAME", "SOUNDS",
            "SOURCE", "SQL_AFTER_GTIDS", "SQL_AFTER_MTS_GAPS", "SQL_BEFORE_GTIDS",
            "SQL_BUFFER_RESULT", "SQL_CACHE", "SQL_NO_CACHE", "SQL_THREAD",
            "START", "STARTS", "STATS_AUTO_RECALC", "STATS_PERSISTENT", "STATS_SAMPLE_PAGES",
            "STATUS", "STOP", "STORAGE", "STRING", "SUBJECT", "SUBPARTITION",
            "SUBPARTITIONS", "SUSPEND", "SWAPS", "SWITCHES", "TABLESPACE",
            "TEMPORARY", "TEMPTABLE", "THAN", "TRADITIONAL", "TRANSACTION",
            "TRIGGERS", "TRUNCATE", "UNDEFINED", "UNDOFILE", "UNDO_BUFFER_SIZE",
            "UNINSTALL", "UNKNOWN", "UNTIL", "UPGRADE", "USER", "USE_FRM",
            "USER_RESOURCES", "VALIDATION", "VALUE", "VARIABLES", "VIEW",
            "WAIT", "WARNINGS", "WITHOUT", "WORK", "WRAPPER", "X509",
            "XA", "XML", "EUR", "USA", "JIS", "ISO", "INTERNAL", "QUARTER",
            "MONTH", "DAY", "HOUR", "MINUTE", "WEEK", "SECOND", "MICROSECOND",
            "TABLES", "ROUTINE", "EXECUTE", "FILE", "PROCESS", "RELOAD",
            "SHUTDOWN", "SUPER", "PRIVILEGES", "ARMSCII8", "ASCII", "BIG5",
            "CP1250", "CP1251", "CP1256", "CP1257", "CP850", "CP852",
            "CP866", "CP932", "DEC8", "EUCJPMS", "EUCKR", "GB2312", "GBK",
            "GEOSTD8", "GREEK", "HEBREW", "HP8", "KEYBCS2", "KOI8R",
            "KOI8U", "LATIN1", "LATIN2", "LATIN5", "LATIN7", "MACCE",
            "MACROMAN", "SJIS", "SWE7", "TIS620", "UCS2", "UJIS", "UTF16",
            "UTF16LE", "UTF32", "UTF8", "UTF8MB3", "UTF8MB4", "ARCHIVE",
            "BLACKHOLE", "CSV", "FEDERATED", "INNODB", "MEMORY", "MRG_MYISAM",
            "MYISAM", "NDB", "NDBCLUSTER", "PERFOMANCE_SCHEMA", "REPEATABLE",
            "COMMITTED", "UNCOMMITTED", "SERIALIZABLE", "GEOMETRYCOLLECTION",
            "LINESTRING", "MULTILINESTRING", "MULTIPOINT", "MULTIPOLYGON",
            "POINT", "POLYGON", "ABS", "ACOS", "ADDDATE", "ADDTIME",
            "AES_DECRYPT", "AES_ENCRYPT", "AREA", "ASBINARY", "ASIN", "ASTEXT",
            "ASWKB", "ASWKT", "ASYMMETRIC_DECRYPT", "ASYMMETRIC_DERIVE",
            "ASYMMETRIC_ENCRYPT", "ASYMMETRIC_SIGN", "ASYMMETRIC_VERIFY", "ATAN",
            "ATAN2", "BENCHMARK", "BIN", "BIT_COUNT", "BIT_LENGTH", "BUFFER",
            "CEIL", "CEILING", "CENTROID", "CHARACTER_LENGTH", "CHARSET",
            "CHAR_LENGTH", "COERCIBILITY", "COLLATION", "COMPRESS", "CONCAT",
            "CONCAT_WS", "CONNECTION_ID", "CONV", "CONVERT_TZ", "COS",
            "COT", "CRC32", "CREATE_ASYMMETRIC_PRIV_KEY", "CREATE_ASYMMETRIC_PUB_KEY",
            "CREATE_DH_PARAMETERS", "CREATE_DIGEST", "CROSSES", "DATEDIFF",
            "DATE_FORMAT", "DAYNAME", "DAYOFMONTH", "DAYOFWEEK", "DAYOFYEAR",
            "DECODE", "DEGREES", "DES_DECRYPT", "DES_ENCRYPT", "DIMENSION",
            "DISJOINT", "ELT", "ENCODE", "ENCRYPT", "ENDPOINT", "ENVELOPE",
            "EQUALS", "EXP", "EXPORT_SET", "EXTERIORRING", "EXTRACTVALUE",
            "FIELD", "FIND_IN_SET", "FLOOR", "FORMAT", "FOUND_ROWS", "FROM_BASE64",
            "FROM_DAYS", "FROM_UNIXTIME", "GEOMCOLLFROMTEXT", "GEOMCOLLFROMWKB",
            "GEOMETRYCOLLECTIONFROMTEXT", "GEOMETRYCOLLECTIONFROMWKB", "GEOMETRYFROMTEXT",
            "GEOMETRYFROMWKB", "GEOMETRYN", "GEOMETRYTYPE", "GEOMFROMTEXT",
            "GEOMFROMWKB", "GET_FORMAT", "GET_LOCK", "GLENGTH", "GREATEST",
            "GTID_SUBSET", "GTID_SUBTRACT", "HEX", "IFNULL", "INET6_ATON",
            "INET6_NTOA", "INET_ATON", "INET_NTOA", "INSTR", "INTERIORRINGN",
            "INTERSECTS", "ISCLOSED", "ISEMPTY", "ISNULL", "ISSIMPLE",
            "IS_FREE_LOCK", "IS_IPV4", "IS_IPV4_COMPAT", "IS_IPV4_MAPPED",
            "IS_IPV6", "IS_USED_LOCK", "LAST_INSERT_ID", "LCASE", "LEAST",
            "LENGTH", "LINEFROMTEXT", "LINEFROMWKB", "LINESTRINGFROMTEXT",
            "LINESTRINGFROMWKB", "LN", "LOAD_FILE", "LOCATE", "LOG", "LOG10",
            "LOG2", "LOWER", "LPAD", "LTRIM", "MAKEDATE", "MAKETIME",
            "MAKE_SET", "MASTER_POS_WAIT", "MBRCONTAINS", "MBRDISJOINT",
            "MBREQUAL", "MBRINTERSECTS", "MBROVERLAPS", "MBRTOUCHES", "MBRWITHIN",
            "MD5", "MLINEFROMTEXT", "MLINEFROMWKB", "MONTHNAME", "MPOINTFROMTEXT",
            "MPOINTFROMWKB", "MPOLYFROMTEXT", "MPOLYFROMWKB", "MULTILINESTRINGFROMTEXT",
            "MULTILINESTRINGFROMWKB", "MULTIPOINTFROMTEXT", "MULTIPOINTFROMWKB",
            "MULTIPOLYGONFROMTEXT", "MULTIPOLYGONFROMWKB", "NAME_CONST", "NULLIF",
            "NUMGEOMETRIES", "NUMINTERIORRINGS", "NUMPOINTS", "OCT", "OCTET_LENGTH",
            "ORD", "OVERLAPS", "PERIOD_ADD", "PERIOD_DIFF", "PI", "POINTFROMTEXT",
            "POINTFROMWKB", "POINTN", "POLYFROMTEXT", "POLYFROMWKB", "POLYGONFROMTEXT",
            "POLYGONFROMWKB", "POW", "POWER", "QUOTE", "RADIANS", "RAND",
            "RANDOM_BYTES", "RELEASE_LOCK", "REVERSE", "ROUND", "ROW_COUNT",
            "RPAD", "RTRIM", "SEC_TO_TIME", "SESSION_USER", "SHA", "SHA1",
            "SHA2", "SIGN", "SIN", "SLEEP", "SOUNDEX", "SQL_THREAD_WAIT_AFTER_GTIDS",
            "SQRT", "SRID", "STARTPOINT", "STRCMP", "STR_TO_DATE", "ST_AREA",
            "ST_ASBINARY", "ST_ASTEXT", "ST_ASWKB", "ST_ASWKT", "ST_BUFFER",
            "ST_CENTROID", "ST_CONTAINS", "ST_CROSSES", "ST_DIFFERENCE",
            "ST_DIMENSION", "ST_DISJOINT", "ST_DISTANCE", "ST_ENDPOINT",
            "ST_ENVELOPE", "ST_EQUALS", "ST_EXTERIORRING", "ST_GEOMCOLLFROMTEXT",
            "ST_GEOMCOLLFROMTXT", "ST_GEOMCOLLFROMWKB", "ST_GEOMETRYCOLLECTIONFROMTEXT",
            "ST_GEOMETRYCOLLECTIONFROMWKB", "ST_GEOMETRYFROMTEXT", "ST_GEOMETRYFROMWKB",
            "ST_GEOMETRYN", "ST_GEOMETRYTYPE", "ST_GEOMFROMTEXT", "ST_GEOMFROMWKB",
            "ST_INTERIORRINGN", "ST_INTERSECTION", "ST_INTERSECTS", "ST_ISCLOSED",
            "ST_ISEMPTY", "ST_ISSIMPLE", "ST_LINEFROMTEXT", "ST_LINEFROMWKB",
            "ST_LINESTRINGFROMTEXT", "ST_LINESTRINGFROMWKB", "ST_NUMGEOMETRIES",
            "ST_NUMINTERIORRING", "ST_NUMINTERIORRINGS", "ST_NUMPOINTS", "ST_OVERLAPS",
            "ST_POINTFROMTEXT", "ST_POINTFROMWKB", "ST_POINTN", "ST_POLYFROMTEXT",
            "ST_POLYFROMWKB", "ST_POLYGONFROMTEXT", "ST_POLYGONFROMWKB", "ST_SRID",
            "ST_STARTPOINT", "ST_SYMDIFFERENCE", "ST_TOUCHES", "ST_UNION",
            "ST_WITHIN", "ST_X", "ST_Y", "SUBDATE", "SUBSTRING_INDEX",
            "SUBTIME", "SYSTEM_USER", "TAN", "TIMEDIFF", "TIMESTAMPADD",
            "TIMESTAMPDIFF", "TIME_FORMAT", "TIME_TO_SEC", "TOUCHES", "TO_BASE64",
            "TO_DAYS", "TO_SECONDS", "UCASE", "UNCOMPRESS", "UNCOMPRESSED_LENGTH",
            "UNHEX", "UNIX_TIMESTAMP", "UPDATEXML", "UPPER", "UUID", "UUID_SHORT",
            "VALIDATE_PASSWORD_STRENGTH", "VERSION", "WAIT_UNTIL_SQL_THREAD_AFTER_GTIDS",
            "WEEKDAY", "WEEKOFYEAR", "WEIGHT_STRING", "WITHIN", "YEARWEEK",
            "Y", "X"};

    @Override
    protected Lexer getLexer(String text) throws IOException {
        return new MySqlLexer(createCharStream(text));
    }

    @Override
    protected int getTokenColor(Token token) {
        int type = token.getType();
        if (in(type, 3, 918) || in(type, 934, 935)) {
            return Colors.keyword;
        }
        switch (type) {
            case MySqlLexer.NAME:
                return Colors.variable;
            case MySqlLexer.STRING_LITERAL:
                return Colors.string;
        }
        return 0;
    }

    @Override
    protected String getName(Token token) {
        switch (token.getType()) {
            case MySqlLexer.NAME:
                return token.getText();
        }
        return null;
    }

    @Override
    public String[] getKeyWords() {
        return keywords;
    }

    @Override
    public Tip[] getTemplates() {
        return new Tip[0];
    }
}
