package com.meme.core.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSONObject;
import com.meme.core.base.AbstractService;
import com.meme.core.cache.ehcache.initializer.DictEhCacheInitializer;
import com.meme.core.dao.DictItemMapper;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.mybatis.Criterion;
import com.meme.core.mybatis.MapperHelper;
import com.meme.core.mybatis.enums.Operator;
import com.meme.core.pojo.DictGroup;
import com.meme.core.pojo.DictItem;
import com.meme.core.pojo.DictItemView;
import com.meme.core.service.DictGroupService;
import com.meme.core.service.DictItemService;
import com.meme.core.util.IDGenerator;
import com.meme.core.util.StringUtil;

@Service("DictItemService")
@Transactional
public class DictItemServiceImpl extends AbstractService implements DictItemService {

	@Resource private DictItemMapper mapper;
	@Resource private DictGroupService dictGroupService;
	@Resource private DictEhCacheInitializer initializer;

	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(DictItem record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(DictItem record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public DictItem selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(DictItem record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(DictItem record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DictItem> selectAll(){
		return (List<DictItem>) MapperHelper.toBeanList(this.mapper.selectAll(DictItem.class), DictItem.class);
	}
	
	/**
	 * 重写查询规则构建语句
	 * @param entityClass
	 * @param form
	 */
	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
		if(null!=form.getDictgroupid()&&form.getDictgroupid()!=0l){
			super.getList().add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			super.getList().add(new Criterion.CriterionBuilder().condition("dictgroupid").operator(Operator.EQUAL).leftValue(form.getDictgroupid()).javaType(Long.class).build());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DictItem> selectByPagination(Form form) {
		this.buildSearchCriterion(DictItem.class, form);
		this.buildOrderByCriterion(DictItem.class, form);
		this.buildLimitCriterion(DictItem.class, form);
		return (List<DictItem>) MapperHelper.toBeanList(this.mapper.selectByPagination(DictItem.class,this.getList()), DictItem.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(DictItem.class, form);
		return this.mapper.count(DictItem.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DictItem> selectByEntity(DictItem record) {
		return (List<DictItem>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), DictItem.class);
	}

	@Override
	public int batchInsert(List<DictItem> record) {
		return this.mapper.batchInsert(record,DictItem.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,DictItem.class);
	}

	@Override
	public int batchUpdate(List<DictItem> record) {
		return this.mapper.batchUpdate(record,DictItem.class);
	}

	@Override
	public List<DictItemView> selectAllDict(Long dictgroupid) {
		return this.mapper.selectAllDict(dictgroupid);
	}

	@Override
	public int deleteByDictGroupids(List<Object> dictgroupids) {
		List<Criterion> list=new ArrayList<Criterion>();
		list.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		list.add(new Criterion.CriterionBuilder().condition("dictgroupid").operator(Operator.IN).leftValue(dictgroupids).javaType(Long.class).build());
		return this.mapper.deleteByCriterions(list, DictItem.class);
	}

	@Override
	public ResultMessage add(HttpServletRequest request, DictItem dictitem) {
		if(null==dictitem.getDictgroupid()||dictitem.getDictgroupid()==0L){
			return ResultMessage.failed("请提供字典项所属字典分组");
		}
		DictGroup group=this.dictGroupService.selectByPrimaryKey(dictitem.getDictgroupid());
		if(null==group){
			return ResultMessage.failed("请提供字典项所属字典分组不存在");
		}
		
		if(StringUtil.isOneEmpty(dictitem.getDictitemname(),dictitem.getDictitemcode())){
			return ResultMessage.failed("字典项名称和编码不能为空");
		}
		DictItem tmpobj=new DictItem();
		tmpobj.setDictgroupid(dictitem.getDictgroupid());
		tmpobj.setDictitemname(dictitem.getDictitemname());
		List<DictItem> list=this.selectByEntity(tmpobj);
		if(null!=list&&list.size()>0) return ResultMessage.failed("字典项名称重复，请修改");
		
		tmpobj=new DictItem();
		tmpobj.setDictgroupid(dictitem.getDictgroupid());
		tmpobj.setDictitemcode(dictitem.getDictitemcode());
		list=this.selectByEntity(tmpobj);
		if(null!=list&&list.size()>0) return ResultMessage.failed("字典分组编码重复，请修改");
		
		if(null!=dictitem.getIsdefault()&&dictitem.getIsdefault()==1){
			tmpobj=new DictItem();
			tmpobj.setDictgroupid(dictitem.getDictgroupid());
			list=this.selectByEntity(tmpobj);
			if(null!=list&&list.size()>0){
				for(DictItem item:list){
					if(item.getIsdefault()==1){
						return ResultMessage.failed("已有默认字典项，请修改为非默认");
					}
				}
			}
		}
		
		long id=IDGenerator.generateId();
		dictitem.setDictitemid(id);
		int result=this.insertSelective(dictitem);
		
		if(result>=1){
			List<DictItemView> items=this.selectAllDict(group.getDictgroupid());
			this.initializer.getInstance().removeAll(group.getDictgroupcode());
			this.initializer.initCaches(items, null);
			
//			DictItemView view=new DictItemView();
//			try {
//				BeanUtils.copyProperties(view, dictitem);
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
//			} catch (InvocationTargetException e) {
//				e.printStackTrace();
//			}
//			view.setDictgroupcode(group.getDictgroupcode());
//			view.setDictgroupname(group.getDictgroupname());
//			view.setType(group.getType());
//			
//			this.initializer.getInstance().setCategoryName(group.getDictgroupcode());
//			this.initializer.getInstance().put(view.getDictitemcode(), view);
		}
		
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage edit(HttpServletRequest request, DictItem dictitem) {
		if(null==dictitem.getDictgroupid()||dictitem.getDictgroupid()==0L){
			return ResultMessage.failed("请提供字典项所属字典分组");
		}
		
		DictGroup group=this.dictGroupService.selectByPrimaryKey(dictitem.getDictgroupid());
//		DictItem olditem=this.selectByPrimaryKey(dictitem.getDictitemid());
		if(null==group){
			return ResultMessage.failed("请提供字典项所属字典分组不存在");
		}
		
		if(StringUtil.isOneEmpty(dictitem.getDictitemname(),dictitem.getDictitemcode())){
			return ResultMessage.failed("字典项名称和编码不能为空");
		}
		DictItem tmpobj=new DictItem();
		tmpobj.setDictgroupid(dictitem.getDictgroupid());
		tmpobj.setDictitemname(dictitem.getDictitemname());
		List<DictItem> list=this.selectByEntity(tmpobj);
		if(null!=list&&list.size()>0) {
			if(list.get(0).getDictitemid().longValue()!=dictitem.getDictitemid().longValue()){
				return ResultMessage.failed("字典项名称重复，请修改");
			}
		}
		
		tmpobj=new DictItem();
		tmpobj.setDictgroupid(dictitem.getDictgroupid());
		tmpobj.setDictitemcode(dictitem.getDictitemcode());
		list=this.selectByEntity(tmpobj);
		if(null!=list&&list.size()>0) {
			if(list.get(0).getDictitemid().longValue()!=dictitem.getDictitemid().longValue()){
				return ResultMessage.failed("字典分组编码重复，请修改");
			}
		}
		
		if(dictitem.getIsdefault()==1){
			tmpobj=new DictItem();
			tmpobj.setDictgroupid(dictitem.getDictgroupid());
			list=this.selectByEntity(tmpobj);
			if(null!=list&&list.size()>0){
				for(DictItem item:list){
					if(item.getIsdefault()==1){
						if(item.getDictitemid().longValue()!=dictitem.getDictitemid().longValue()) {
							return ResultMessage.failed("已有默认字典项，请修改为非默认");
						}
					}
				}
			}
		}
		
		int result=this.updateByPrimaryKeySelective(dictitem);
		if(result>=1){
			List<DictItemView> items=this.selectAllDict(group.getDictgroupid());
			this.initializer.getInstance().removeAll(group.getDictgroupcode());
			this.initializer.initCaches(items, null);
//			DictItem item=this.selectByPrimaryKey(dictitem.getDictitemid());
//			DictItemView view=new DictItemView();
//			try {
//				BeanUtils.copyProperties(view, item);
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
//			} catch (InvocationTargetException e) {
//				e.printStackTrace();
//			}
//			view.setDictgroupcode(group.getDictgroupcode());
//			view.setDictgroupname(group.getDictgroupname());
//			view.setType(group.getType());
//			
//			this.initializer.getInstance().setCategoryName(group.getDictgroupcode());
//			//删除旧缓存数据
//			this.initializer.getInstance().remove(olditem.getDictitemcode());
//			//更换新缓存数据
//			this.initializer.getInstance().put(view.getDictitemcode(), view);
		}
		
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage delete(List<Object> ids) {
		if(null!=ids&&ids.size()>0){
			List<DictItemView> list=this.selectByDictitemids(ids);
			this.batchDelete(ids);
			
			if(null!=list&&list.size()>0){
				for(DictItemView dv:list){
					this.initializer.getInstance().setCategoryName(dv.getDictgroupcode());
					this.initializer.getInstance().remove(dv.getDictitemcode());
				}
			}
//			Map<Long,Long> idsMap=new HashMap<Long, Long>();
//			for(Object obj:ids){
//				idsMap.put(Long.valueOf(obj.toString()), Long.valueOf(obj.toString()));
//			}
//			Map dictitems=this.initializer.getInstance().getCacheList();
//	    	if(null!=dictitems&&dictitems.size()>0){
//				Iterator it=dictitems.keySet().iterator();
//				while(it.hasNext()){
//					//缓存分类键值
//					String key=(String)it.next();
//					//字典组下的所有字典项
//					LinkedHashMap value=(LinkedHashMap) dictitems.get(key);
//					Collection values=value.values();
//					for (Iterator iterator = values.iterator(); iterator.hasNext();) {
//						String dictitemcode=(String) iterator.next();
//						DictItemView item=(DictItemView)value.get(dictitemcode);
//						//判断分组ID在要删除的分组中时删除此分组缓存
//						if(idsMap.containsKey(item.getDictitemid())){
//							this.initializer.getInstance().setCategoryName(item.getDictgroupcode());
//							this.initializer.getInstance().remove(item.getDictitemcode());
//						}
//					}
//				}
//			}
		}
    	
    	return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public List<DictItemView> selectByDictitemids(List<Object> ids) {
		return this.mapper.selectByDictitemids(ids);
	}

	@Override
	public String doImport(HttpServletRequest request,Long dictgroupid) {
		CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
        List<DictItem> items=new ArrayList<DictItem>();
		if(multipartResolver.isMultipart(request)){
			MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
			Iterator<String> iter=multiRequest.getFileNames();
			if(iter.hasNext()){
				while(iter.hasNext()){
					MultipartFile file=multiRequest.getFile(iter.next());
					POIFSFileSystem fs=null;
					HSSFWorkbook wb=null;
					try {
						fs=new POIFSFileSystem(file.getInputStream());
						wb = new HSSFWorkbook(fs);
					} catch (IOException e) {
						e.printStackTrace();
					}
					HSSFSheet sheet=wb.getSheetAt(0);
					int rowNum=sheet.getLastRowNum();
					HSSFRow row=sheet.getRow(0);
			        //int colNum=row.getPhysicalNumberOfCells();
			        for(int i=1;i<=rowNum;i++) {
			        	row=sheet.getRow(i);
			        	DictItem item=new DictItem();
			        	item.setDictitemname(getCellFormatValue(row.getCell(0)).trim());
			        	item.setDictitemcode(getCellFormatValue(row.getCell(1)).trim());
			        	item.setSortno(Double.valueOf(getCellFormatValue(row.getCell(2)).trim()).intValue());
			        	item.setDictgroupid(dictgroupid);
			        	
			        	long id=IDGenerator.generateId();
			        	item.setDictitemid(id);
			        	items.add(item);
			        	//this.insertSelective(item);
			        }
				}
			}
		}
		if(null!=items&&items.size()>0){
			this.batchInsert(items);
			//更新缓存数据
			DictGroup group=this.dictGroupService.selectByPrimaryKey(dictgroupid);
			List<DictItemView> list=this.selectAllDict(group.getDictgroupid());
			this.initializer.getInstance().removeAll(group.getDictgroupcode());
			this.initializer.initCaches(list, null);
		}
		return JSONObject.toJSONString(ResultMessage.success("导入成功"));
	}
	
	private String getCellFormatValue(HSSFCell cell) {
        String cellvalue = "";
        if (null!=cell) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
            // 如果当前Cell的Type为NUMERIC
            case HSSFCell.CELL_TYPE_NUMERIC:
            case HSSFCell.CELL_TYPE_FORMULA: {
                // 判断当前的cell是否为Date
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    // 如果是Date类型则，转化为Data格式
                    
                    //方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
                    //cellvalue = cell.getDateCellValue().toLocaleString();
                    
                    //方法2：这样子的data格式是不带带时分秒的：2011-10-12
                    Date date = cell.getDateCellValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    cellvalue = sdf.format(date);
                    
                }
                // 如果是纯数字
                else {
                    // 取得当前Cell的数值
                    cellvalue = String.valueOf(cell.getNumericCellValue());
                }
                break;
            }
            // 如果当前Cell的Type为STRIN
            case HSSFCell.CELL_TYPE_STRING:
                // 取得当前的Cell字符串
                cellvalue = cell.getRichStringCellValue().getString();
                break;
            // 默认的Cell值
            default:
                cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }

	@Override
	public List<DictItem> selectByDictgroupcode(String dictgroupcode) {
		return this.mapper.selectByDictgroupcode(dictgroupcode);
	}
}
