package com.meme.core.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;

import com.meme.core.excel.entity.FieldNode;

public abstract class CellParser implements IParser{

	private CellParser parser;
	
	@Override
	public Object parse(HSSFCell cell, FieldNode node) {
		try {
			Class<?> type=Class.forName(node.getType());
			if(type.getCanonicalName().equals(this.getType().getCanonicalName())){
				
			}else return this.getParser().parse(cell, node);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	public CellParser getParser() {
		return parser;
	}
	public void setParser(CellParser parser) {
		this.parser = parser;
	}
}
