package cn.fyg.pm.application;

import java.util.Map;

import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.domain.model.user.User;

/**
 *用户供应商分配
 */
public interface SpmemberService {
	
	/**
	 * 给用户分配一个供应商，如果原先没用供应商，则分配，否则覆盖原有供应商
	 * @param user
	 * @param supplier
	 */
	void assignUserSupplier(User user,Supplier supplier);
	
	/**
	 * 判断一个用户的供应商是否分配
	 * @param user
	 * @return
	 */
	boolean isUserAssigned(User user);
	
	/**
	 * 获得用户供应商
	 * @param user
	 * @return
	 */
	Supplier getUserSupplier(User user);
	
	/**
	 * 获得所有已分配的供应商用户
	 * @return
	 */
	Map<User,Supplier> getAllUserSupplier();
	
	/**
	 * 清楚用户供应商
	 * @param user
	 */
	void clearUserSupplier(User user);

}
