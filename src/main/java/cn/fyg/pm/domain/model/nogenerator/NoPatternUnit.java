package cn.fyg.pm.domain.model.nogenerator;

/**
 *编号流水生成对象需实现该接口
 */
public interface NoPatternUnit {
	
	/**
	 * 返回某类业务数据的编码格式
	 * @return
	 */
	NoPattern getNoPattern();
	
	/**
	 * 设置编号
	 * @param generateNo
	 */
	void setNo(String no);
	
	/**
	 * 返回编号
	 * @return 
	 */
	String getNo();

	
}
