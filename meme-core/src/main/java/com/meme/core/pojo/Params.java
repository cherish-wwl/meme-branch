package com.meme.core.pojo;

import java.io.Serializable;

import com.meme.core.mybatis.annotation.Table;

@Table(name="sys_params",id="id")
public class Params implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;

    private String name;

    private String type;

    private String remark;

    private Integer prec;

    private Integer scale;

    private Integer nullable;

    private Integer enumvar;

    private String title;

    private String vartype;

    private Integer multi;

    private String validate;
    
    private String dictgroupcode;
	
	private String value;

    private String defval;

    public String getDictgroupcode() {
		return dictgroupcode;
	}

	public void setDictgroupcode(String dictgroupcode) {
		this.dictgroupcode = dictgroupcode;
	}

	public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getDefval() {
        return defval;
    }

    public void setDefval(String defval) {
        this.defval = defval == null ? null : defval.trim();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getPrec() {
        return prec;
    }

    public void setPrec(Integer prec) {
        this.prec = prec;
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    public Integer getNullable() {
        return nullable;
    }

    public void setNullable(Integer nullable) {
        this.nullable = nullable;
    }

    public Integer getEnumvar() {
		return enumvar;
	}

	public void setEnumvar(Integer enumvar) {
		this.enumvar = enumvar;
	}

	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getVartype() {
        return vartype;
    }

    public void setVartype(String vartype) {
        this.vartype = vartype == null ? null : vartype.trim();
    }

    public Integer getMulti() {
        return multi;
    }

    public void setMulti(Integer multi) {
        this.multi = multi;
    }

    public String getValidate() {
        return validate;
    }

    public void setValidate(String validate) {
        this.validate = validate == null ? null : validate.trim();
    }
}