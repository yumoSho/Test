package com.glanway.jty.service.dictionary;

import com.glanway.gone.spring.bind.domain.Filters;
import com.glanway.gone.spring.bind.domain.Page;
import com.glanway.gone.spring.bind.domain.Pageable;
import com.glanway.jty.entity.dictionary.Dictionary;
import com.glanway.jty.service.BaseService;

import java.util.List;

/** 
* @文件名: DictionaryService.java
* @功能描述: 数据字典业务接口 
* @author SunF
* @date 2016年3月31日 上午10:25:19 
*  
*/
public interface DictionaryService extends BaseService<Dictionary, Long>{
	String SUPER_ID = "superId";
	String DIC_CODE = "dicCode";
	String DELETED = "deleted";

	/** 
	* @功能描述: 分页
	* @param superId 父id
	* @param pageable 分页信息
	* @return       
	*/
	Page<Dictionary> listPage(Long superId, Pageable pageable);

	/** 
	* @功能描述: 分页，根据条件查询字典列表 (暂时废弃)
	* @param dicCodeFilter 编码
	* @param dicNamefilters 名称
	* @param pageable
	* @return       
	*/
	@Deprecated
	Page<Dictionary> listFindPage(Filters dicCodeFilter, Filters dicNamefilters, Pageable pageable);

	/** 
	* @功能描述: 分页，根据条件查询字典列表
	* @param dicCodeFilter
	* @param dicNamefilters
	* @param pageable
	* @param pid 所属字典id
	* @return       
	*/
	Page<Dictionary> listFindPage(Filters dicCodeFilter, Filters dicNamefilters, Pageable pageable, Long pid);

	/** 
	* @功能描述: 根据父类id查询子类字典列表
	* @param superId 父id
	* @return 子类字典列表
	*/
	List<Dictionary> findBySuperId(Long superId);
	
	/** 
	 * @功能描述: 根据父类编码查询子类字典列表
	 * @param dicCode 父字典dicCode
	 * @return 子类字典列表
	 */
	List<Dictionary> findBySuperDicCode(String dicCode);

	/** 
	 * @功能描述: 根据父类编码和子类字段编码查询当前子类字典数据
	 * @param supperCode 父字典supperCode
	 * @param subCode 子字典subCode
	 * @return 子类字典对象
	 */
	Dictionary findBySuperDicCodeAndSubCode(String supperCode, String subCode);
	/** 
	* @功能描述: 保存字典信息
	* @param dictionary 字典数据
	* @return       
	*/
	void save(Dictionary dictionary);

	/** 
	* @功能描述: 批量删除字典
	* @说明：删除一个字典前需要先删除其包含的子字典，
	* 		   若被删除的字典中有任何一个字典含有子字典，则全部不允许删除
	* @param ids   字典id集合
	*/
	boolean batchDeleteDictionary(Long[] ids);

	/** 
	* @功能描述: 检查指定字典下是否存在该编号
	* @param dicCode 待查字典编号
	* @param superId 指定的父字典ID
	* @param id 字典id  添加时传空字符，编辑时传实际值
	* @return       
	*/
	boolean isExists(String dicCode, String superId, String id);


}
