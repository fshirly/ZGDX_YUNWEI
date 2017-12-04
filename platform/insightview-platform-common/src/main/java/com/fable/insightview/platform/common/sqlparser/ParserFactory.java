/**
 * 
 */
package com.fable.insightview.platform.common.sqlparser;

import org.springframework.stereotype.Component;

import com.fable.insightview.platform.common.sqlparser.impl.MysqlParser;

/**
 * @author zhouwei
 * 
 */
@Component
public class ParserFactory {
	
	Parser parser;

	public Parser openParser() {
		if(parser == null){
			parser = newParser();
		}
		return parser;
	}

	private Parser newParser() {
		return new MysqlParser();
	}

}
