package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.pagination.Paginator;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Right;
import fr.univartois.ili.fsnet.entities.SocialElement;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.SocialGroup;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;
import fr.univartois.ili.fsnet.facade.SocialGroupFacade;

public class ManageGroups extends MappingDispatchAction implements CrudAction {
/**
 * @author SAID Mohamed
 */
	@Override
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		HttpSession session = request.getSession(true);
		session.removeAttribute("idGroup");
		List<SocialEntity> allMembers = null;
		List<SocialGroup> allGroups = null;
		SocialGroup parentGroup = null;
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);
		SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);

		if (form != null) {

			DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
			String name = (String) dynaForm.get("name");
			dynaForm.set("name", "");
			String description = (String) dynaForm.get("description");
			dynaForm.set("description", "");
			String parent = (String) dynaForm.get("parentId");
			dynaForm.set("parentId", "");
			String owner = (String) dynaForm.get("socialEntityId");
			dynaForm.set("socialEntityId", "");
			String[] membersAccepted = (String[]) dynaForm
					.get("memberListRight");
			String[] rigthsAccepted = (String[]) dynaForm.get("rigthListRight");

			SocialEntity masterGroup = socialEntityFacade
					.getSocialEntity(Integer.valueOf(owner));
			if (!parent.equals(""))
				parentGroup = socialGroupFacade.getSocialGroup(Integer
						.parseInt(parent));
			List<SocialElement> socialElements = createSocialElement(em,
					membersAccepted, masterGroup, parentGroup);
			try {
				SocialGroup socialGroup = socialGroupFacade.createSocialGroup(
						masterGroup, name, description, socialElements);

				socialGroup.addRights(getAcceptedRigth(rigthsAccepted));

				em.getTransaction().begin();
				em.persist(socialGroup);
				if (parentGroup != null) {
					parentGroup.addSocialElements(socialGroup);
					em.merge(parentGroup);
				}
				em.getTransaction().commit();
			} catch (RollbackException e) {
				ActionErrors errors = new ActionErrors();
				errors.add("name", new ActionMessage("groups.name.exists"));
				saveErrors(request, errors);
				return mapping.findForward("errors");
			}
			em.close();
			dynaForm.set("name", "");
			dynaForm.set("description", "");
			dynaForm.set("socialEntityId", "");
		} else {

			allMembers = getRefusedSocialMember(socialGroupFacade,
					socialEntityFacade.getAllSocialEntity(), null);
			request.setAttribute("allMembers", allMembers);
			request.setAttribute("refusedMembers", allMembers);
			request.setAttribute("refusedRigths", Right.values());
			allGroups = socialGroupFacade.AllGroupChild(UserUtils
					.getHisGroup(request));
			request.setAttribute("allGroups", allGroups);
		}

		return mapping.findForward("success");
	}

	/**
	 * @author SAID Mohamed
	 */
	@Override
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub

		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);
		SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);

		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		Integer socialGroupId = Integer.parseInt(dynaForm.getString("id"));
		String name = (String) dynaForm.get("name");
		dynaForm.set("name", "");
		String description = (String) dynaForm.get("description");
		dynaForm.set("description", "");
		String owner = (String) dynaForm.get("socialEntityId");
		dynaForm.set("socialEntityId", "");

		String[] membersAccepted = (String[]) dynaForm.get("memberListRight");

		String[] rigthsAccepted = (String[]) dynaForm.get("rigthListRight");

		SocialEntity masterGroup = socialEntityFacade.getSocialEntity(Integer
				.valueOf(owner));

		List<SocialElement> socialElements = createSocialElement(em,
				membersAccepted, masterGroup, null);
		try {

			SocialGroup socialGroup = socialGroupFacade
					.getSocialGroup(socialGroupId);

			socialGroup.setName(name);
			socialGroup.setDescription(description);
			socialGroup.setMasterGroup(masterGroup);
			// socialGroup.setSocialElements(new ArrayList<SocialElement>());
			socialGroup.setRights(getAcceptedRigth(rigthsAccepted));
			socialGroup.setSocialElements(socialElements);

			em.getTransaction().begin();
			em.merge(socialGroup);
			em.getTransaction().commit();

			request.getSession(true).removeAttribute("idGroup");
		} catch (RollbackException e) {
			ActionErrors errors = new ActionErrors();
			errors.add("name", new ActionMessage("groups.name.exists"));
			saveErrors(request, errors);
			return mapping.findForward("errors");
		}
		em.close();
		dynaForm.set("name", "");
		dynaForm.set("description", "");
		dynaForm.set("nameParent", "");
		dynaForm.set("socialEntityId", "");

		return mapping.findForward("success");

	}

	@Override
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @author SAID Mohamed
	 */
	public ActionForward switchState(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String GroupSelected = request.getParameter("groupSelected");
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);
		em.getTransaction().begin();
		int socialGroupId = Integer.parseInt(GroupSelected);
		socialGroupFacade.switchState(socialGroupId);
		em.getTransaction().commit();
		em.close();

		return mapping.findForward("success");
	}

	/**
	 * @author SAID Mohamed
	 */
	@Override
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		EntityManager em = PersistenceProvider.createEntityManager();
		DynaActionForm dynaForm = (DynaActionForm) form;

		List<SocialEntity> allMembers = null;
		List<SocialEntity> refusedMembers = null;

		List<SocialEntity> acceptedMembers = null;
		Set<Right> acceptedRigths = null;
		Set<Right> refusedRigths = new HashSet<Right>();

		SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);
		SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);

		String id = request.getParameter("idGroup");

		if (id == null)
			id = (String) session.getAttribute("idGroup");
		else
			session.setAttribute("idGroup", id);
		Integer idGroup = Integer.valueOf(id);
		SocialGroup group = socialGroupFacade.getSocialGroup(idGroup);
		SocialEntity masterGroup = group.getMasterGroup();
		SocialGroup parentGroup = group.getGroup();

		allMembers = socialEntityFacade.getAllSocialEntity();

		acceptedMembers = socialGroupFacade.getAcceptedSocialEntity(group);
		refusedMembers = getRefusedSocialMember(socialGroupFacade, allMembers,
				group);
		acceptedRigths = group.getRights();
		for (Right right : Right.values()) {
			refusedRigths.add(right);
		}

		refusedRigths.removeAll(acceptedRigths);

		dynaForm.set("id", id);
		dynaForm.set("name", group.getName());
		dynaForm.set("description", group.getDescription());
		if (parentGroup != null)
			request.setAttribute("nameParent", parentGroup.getName());
		request.setAttribute("masterGroup", masterGroup);

		request.setAttribute("acceptedMembers", acceptedMembers);
		request.setAttribute("allMembers",
				socialEntityFacade.getAllSocialEntity());

		request.setAttribute("refusedMembers", refusedMembers);

		request.setAttribute("refusedRigths", refusedRigths);
		request.setAttribute("acceptedRigths", acceptedRigths);

		em.close();

		return mapping.findForward("success");
	}

	private Set<Right> getAcceptedRigth(String[] groupsAccepted) {
		Set<Right> rights = new HashSet<Right>();

		for (String string : groupsAccepted)
			rights.add(Right.valueOf(string));

		return rights;
	}

	private List<SocialElement> createSocialElement(EntityManager em,
			String[] membersAccepted, SocialEntity masterGroup,
			SocialGroup parentGroup) {

		List<SocialElement> socialElements = new ArrayList<SocialElement>();
		SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);

		for (String members : membersAccepted)
			socialElements.add(socialEntityFacade.getSocialEntity(Integer
					.valueOf(members)));

		if (!socialElements.contains(masterGroup))
			socialElements.add(masterGroup);
		if (parentGroup != null)
			socialElements.remove(parentGroup);
		return socialElements;
	}

	public List<SocialEntity> getRefusedSocialMember(SocialGroupFacade sgf,
			List<SocialEntity> allMembers, SocialGroup socialGroup) {
		if (sgf == null) {
			throw new IllegalArgumentException();
		}
		List<SocialEntity> resulEntities = new ArrayList<SocialEntity>();
		List<SocialEntity> members = allMembers;
		if (socialGroup != null) {
			members.removeAll(sgf.getAcceptedSocialEntity(socialGroup));
		}
		for (SocialEntity se : allMembers) {
			if (se.getGroup() == null)
				resulEntities.add(se);
		}
		return resulEntities;

	}

	public List<SocialGroup> getRefusedSocialGroup(SocialGroupFacade sgf,
			SocialGroup socialGroup) {
		if (sgf == null) {
			throw new IllegalArgumentException();
		}
		List<SocialGroup> resulGroups = new ArrayList<SocialGroup>();
		List<SocialGroup> allGroups = sgf.getAllSocialEntity();

		if (socialGroup != null) {
			List<SocialGroup> groups = sgf.getAcceptedSocialGroup(socialGroup);
			allGroups.removeAll(groups);
			allGroups.removeAll(sgf.AllParent(socialGroup));
		}

		for (SocialGroup sg : allGroups) {
			if (sg.getGroup() == null && !sg.equals(socialGroup))
				resulGroups.add(sg);
		}

		return resulGroups;
	}

	@Override
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		EntityManager em = PersistenceProvider.createEntityManager();
		SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);

		SocialGroup socialGroup = UserUtils.getHisGroup(request);

		List<SocialGroup> socialGroups = socialGroupFacade
				.AllGroupChild(socialGroup);

		Paginator<SocialGroup> paginator = new Paginator<SocialGroup>(
				socialGroups, request, "searchGroups");
		request.setAttribute("groupsListPaginator", paginator);
		return mapping.findForward("success");
	}

}