package cn.fyg.pm.application.facade;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.domain.model.pjmember.Pjmember;
import cn.fyg.pm.domain.model.pjmember.PjmemberRepository;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.role.Role;
import cn.fyg.pm.domain.model.user.User;

@Component("pjmemberFacade")
public class PjmemberFacade {
	
	@Autowired
	PjmemberRepository pjmemberRepository;
	
	@Transactional
	public void savePjmember(Project project, List<Pjmember> pjmembers) {
		List<Pjmember> oldPjmembers = this.pjmemberRepository.findByProject(project);
		Set<Long> idSet=new HashSet<Long>();
		for (Pjmember pjmember : oldPjmembers) {
			idSet.add(pjmember.getId());
		}
		for (Pjmember pjmember : pjmembers) {
			//TODO  如果用户没选就直接跳过,判断应该放在页面上
			User user = pjmember.getUser();
			if(StringUtils.isBlank(user.getKey())){ continue;}
			
			pjmember.setProject(project);
			Role role = pjmember.getRole();
			if(StringUtils.isBlank(role.getKey())){ pjmember.setRole(null);}
			this.pjmemberRepository.save(pjmember);
			idSet.remove(pjmember.getId());
		}
		for (Long id : idSet) {
			this.pjmemberRepository.delete(id);
		}
	}
	
	
	public List<Pjmember> getProjecMember(Project project) {
		List<Pjmember> pjmembers = this.pjmemberRepository.findByProject(project);
		Collections.sort(pjmembers, new PjmemberComparator());
		return pjmembers;
	}
	
	private class PjmemberComparator implements Comparator<Pjmember> {

		@Override
		public int compare(Pjmember pjm1, Pjmember pjm2) {
			if( pjm1.getRole()==null&&pjm2.getRole()!=null){
				return -1;
			}else if(pjm1.getRole()!=null&&pjm2.getRole()==null){
				return 1;
			}else if(pjm1.getRole()==null&&pjm2.getRole()==null){
				return 0;
			}
			return pjm1.getRole().getSn().compareTo(pjm2.getRole().getSn());
		}
	}

}
