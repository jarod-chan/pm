package cn.fyg.pm.interfaces.web.shared.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.interfaces.web.shared.query.core.Qitem;
import cn.fyg.pm.interfaces.web.shared.query.core.impl.AbstractQuerySpec;

public abstract class CommonQuery<T> extends AbstractQuerySpec<T> {

	private String no;// 编号

	private Date createdate_beg;// 制单日期开始

	private Date createdate_end;// 制单日期开始

	private String state;//状态

	private Project project;// 项目
	
	public List<Qitem> getStateList(){
		ArrayList<Qitem> arrayList = new ArrayList<Qitem>();
		arrayList.add(new Qitem("ext-all","全部"));
		arrayList.add(new Qitem("ext-notf","未完成"));
		arrayList.add(new Qitem("new_","新建"));
		arrayList.add(new Qitem("saved","已保存"));
		arrayList.add(new Qitem("commit","已提交"));
		arrayList.add(new Qitem("finish","已完成"));
		arrayList.add(new Qitem("invalid","已作废"));
		return arrayList;
	}
	
	@Override
	public String initOrderAttribute() {
		this.state="ext-all";
		return "createdate";
	}

	@Override
	public void initOrderAttributeList(List<Qitem> attributeList) {
		attributeList.add(new Qitem("no","编号"));
		attributeList.add(new Qitem("createdate","制单日期"));
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Date getCreatedate_beg() {
		return createdate_beg;
	}

	public void setCreatedate_beg(Date createdate_beg) {
		this.createdate_beg = createdate_beg;
	}

	public Date getCreatedate_end() {
		return createdate_end;
	}

	public void setCreatedate_end(Date createdate_end) {
		this.createdate_end = createdate_end;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
