/*  -----------------
 *  SysVision DBMagic
 *  -----------------
 *
 *  Copyright 2006-2012 SysVision - Consultadoria e Desenvolvimento em Sistemas de Informática, Lda.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package net.java.dbmagic.db;

import java.util.Iterator;

import net.java.dbmagic.model.Column;
import net.java.dbmagic.model.Entity;
import net.java.dbmagic.model.KeyGenerator;


public class MySQLDialect extends SQLCreater {
	public String getCreateTable(Entity table) {
		StringBuffer out = new StringBuffer();

		out.append("CREATE TABLE ");
		out.append(table.getName());
		out.append(" (");
		out.append(LINE);

		Column column = null;

		// COLUNAS
		for (Iterator i = table.getAllColumnList().iterator(); i.hasNext();) {
			column = (Column) i.next();

			out.append(TAB);
			out.append(column.getName());
			out.append(SPACE);
			out.append(translateType(column));

			if (!column.isNullable()) {
				out.append(SPACE);
				out.append("NOT NULL");
			}

			if (column.getPrimaryKey() != null) {
				if (column.getPrimaryKey().getKeyGenerator() != null && column.getPrimaryKey().getKeyGenerator().getType().equals(KeyGenerator.NATIVE)){
					out.append(SPACE);
					out.append("AUTO_INCREMENT");
				}
			}
			
			if (table.getPrimaryKey() != null || i.hasNext()) {
				out.append(",");
				out.append(LINE);
			} else {
				out.append(LINE);
			}
		}

		// PK
		if (table.getPrimaryKey() != null) {
			out.append(TAB);
			out.append("PRIMARY KEY (");

			for (Iterator i = table.getPrimaryKey().getColumnList().iterator(); i.hasNext();) {
				column = (Column) i.next();

				out.append(column.getName());

				if (i.hasNext()) {
					out.append(", ");
				}
			}

			out.append(")");
			out.append(LINE);
		}

		// FIM DA TABELA
		out.append(") ENGINE=InnoDB");

		return out.toString();
	}
	
	public String translateType(Column column) {
		
/*
CHAR  	String
VARCHAR 	String
LONGVARCHAR 	String
NUMERIC 	java.math.BigDecimal
DECIMAL 	java.math.BigDecimal
BIT 	boolean
TINYINT 	byte
SMALLINT 	short
INTEGER 	int
BIGINT 	long
REAL 	float
FLOAT 	double
DOUBLE 	double
BINARY 	byte []
VARBINARY 	byte []
LONGVARBINARY 	byte []
DATE 	java.sql.Date
TIME 	java.sql.Time
TIMESTAMP 	java.sql.Tiimestamp
 */
		
		if (column.getJavaType().equals("byte")) {
			return "TINYINT";
		} else if (column.getJavaType().equals("short")) {
			return "SMALLINT";
		} else if (column.getJavaType().equals("int")) {
			return "INTEGER";
		} else if (column.getJavaType().equals("long")) {
			return "BIGINT";
		} else if (column.getJavaType().equals("float")) {
			return "REAL";
		} else if (column.getJavaType().equals("double")) {
			return "DOUBLE";
		} else if (column.getJavaType().equals("java.math.BigDecimal")) {
			return "DECIMAL(" + column.getMaxSize() + "," + column.getDecimalDigits() + ")";
		} else if (column.getJavaType().equals("boolean")) {
			return "BIT";
		} else if (column.getJavaType().equals("String")) {
			if (column.getMaxSize() == 1 || column.isFixedSize()) {
				return "CHAR(" + column.getMaxSize() + ")";
			} else if (column.getMaxSize() > 1 && column.getMaxSize() < 65535) {
				return "VARCHAR(" + column.getMaxSize() + ")";
			} else {
				return "TEXT";
			}
		} else if (column.getJavaType().equals("byte[]")) {
			return "BINARY";
		} else if (column.getJavaType().equals("java.sql.Date")) {
			return "DATE";
		} else if (column.getJavaType().equals("java.sql.Time")) {
			return "TIME";
		} else if (column.getJavaType().equals("java.sql.Timestamp")) {
			return "TIMESTAMP";
		}

		return "UNKNOWN";
	}

	public String getSqlSeparater() {
		return ";";
	}
}
