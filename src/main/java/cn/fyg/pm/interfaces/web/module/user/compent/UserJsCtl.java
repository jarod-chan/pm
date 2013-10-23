package cn.fyg.pm.interfaces.web.module.user.compent;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.fyg.pm.application.UserService;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.model.user.UserSpecs;
import cn.fyg.pm.interfaces.web.shared.component.PageData;
import cn.fyg.pm.interfaces.web.shared.component.PageDataAssembler;
import cn.fyg.pm.interfaces.web.shared.component.QueryComp;
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;

@Controller
@RequestMapping("user")
public class UserJsCtl {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="select.json",method=RequestMethod.GET)
	@ResponseBody 
	public PageData<UserSmp> simpleQuery(UserJsQuery query){
		
		QueryComp<User> comp=new QueryComp<User>();
		if(StringUtils.isNotBlank(query.getKey())){
			comp.addSpec(UserSpecs.keyLike(query.getKey()));
		}
		
		if(StringUtils.isNotBlank(query.getName())){
			comp.addSpec(UserSpecs.nameLike(query.getName()));
		}
		
		if(query.getEnabled()!=null){
			comp.addSpec(UserSpecs.isEnabled(query.getEnabled()));
		}
		Pageable pageable= new PageRequest(query.getPage(),AppConstant.PAGE_SIZE,new Sort(new Order(Direction.ASC,"key")));
		Page<User> page = this.userService.findAll(comp.toSpec(), pageable);
		return PageDataAssembler.transferData(page, new UserTsf());
	}

}
