package cn.fyg.pm.interfaces.web.module.project.query;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.project.ProjectSpecs;
import cn.fyg.pm.interfaces.web.shared.query.refactor.Qitem;
import cn.fyg.pm.interfaces.web.shared.query.refactor.impl.AbstractQuerySpec;

public class ProjectQuery extends AbstractQuerySpec<Project> {

	private String no;// 编号

	private String name;// 名称

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String initOrderAttribute() {
		return "no";
	}

	@Override
	public void initOrderAttributeList(List<Qitem> attributeList) {
		attributeList.add(new Qitem("no", "编号"));
	}

	@Override
	public void doSpec(List<Specification<Project>> specs) {
		if (StringUtils.isNotBlank(this.getNo())) {
			specs.add(ProjectSpecs.noLike(this.getNo()));
		}
		if (StringUtils.isNotBlank(this.getName())) {
			specs.add(ProjectSpecs.nameLike(this.getName()));
		}
	}

}
