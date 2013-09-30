package cn.fyg.pm.interfaces.web.module.trace.designnoti.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.fyg.pm.application.DesignNotiService;
import cn.fyg.pm.domain.model.design.designnoti.DesignNoti;
import cn.fyg.pm.domain.model.design.designnoti.DesignNotiSpecs;
import cn.fyg.pm.domain.model.design.designnoti.DesignNotiState;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.interfaces.web.shared.component.PageData;
import cn.fyg.pm.interfaces.web.shared.component.PageDataAssembler;
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;

@Controller
@RequestMapping("{projectId}/designnoti")
public class DesignNotiJsCtl {
	
	@Autowired
	DesignNotiService designNotiService;
	
	@RequestMapping(value="select.json",method=RequestMethod.GET)
	@ResponseBody 
	public PageData<DesignNotiSmp> simpleQuery(@PathVariable("projectId")Long projectId,DesignNotiJsQuery query){
		Project project=new Project();
		project.setId(projectId);
		Specifications<DesignNoti> specs=Specifications.where(DesignNotiSpecs.inProject(project))
				.and(DesignNotiSpecs.isState(DesignNotiState.finish))
				.and(DesignNotiSpecs.noLike(query.getNo()));
		Pageable pageable= new PageRequest(query.getPage(),AppConstant.PAGE_SIZE,new Sort(new Order(Direction.DESC,"id")));
		Page<DesignNoti> page = this.designNotiService.findAll(specs, pageable);
		return PageDataAssembler.transferData(page, new DesignNotiTsf());	
	}

}
