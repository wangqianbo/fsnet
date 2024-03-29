package fr.univartois.ili.fsnet.admin.actions;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;

import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Meeting;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.InteractionFacade;
import fr.univartois.ili.fsnet.facade.InteractionRoleFacade;
import fr.univartois.ili.fsnet.facade.MeetingFacade;

/**
 * Execute CRUD Actions for the entity Event
 * 
 * @author Mohamed Bouragba
 */
public class ManageEvents extends MappingDispatchAction implements CrudAction {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.admin.actions.CrudAction#create(org.apache.struts
	 * .action.ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.admin.actions.CrudAction#modify(org.apache.struts
	 * .action.ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO code pour la modification

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.admin.actions.CrudAction#delete(org.apache.struts
	 * .action.ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		String eventId = (String) dynaForm.get("eventId");
		EntityManager em = PersistenceProvider.createEntityManager();
		// SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
		InteractionFacade interactionFacade = new InteractionFacade(em);
		MeetingFacade meetingFacade = new MeetingFacade(em);

		Meeting meeting = meetingFacade.getMeeting(Integer.parseInt(eventId));
		if (meeting != null) {
			em.getTransaction().begin();
			SocialEntity user = meeting.getCreator();
			Set<SocialEntity> followingEntitys = meeting.getFollowingEntitys();
			for (SocialEntity se : followingEntitys) {
				se.getFavoriteInteractions().remove(meeting);
			}
			InteractionRoleFacade interactionRoleFacade = new InteractionRoleFacade(
					em);
			interactionRoleFacade.unsubscribeAll(meeting);
			interactionFacade.deleteInteraction(user, meeting);
			try {
				em.getTransaction().commit();
			} catch (RollbackException e) {
				servlet.log("no commit", e);
			}
			em.close();
		}

		return mapping.findForward("success");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.admin.actions.CrudAction#search(org.apache.struts
	 * .action.ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		DynaActionForm searchForm = (DynaActionForm) form; // NOSONAR
		String searchString = (String) searchForm.get("searchString");

		EntityManager em = PersistenceProvider.createEntityManager();
		em.getTransaction().begin();
		MeetingFacade meetingFacade = new MeetingFacade(em);
		List<Meeting> results = meetingFacade.searchMeeting(searchString);
		em.getTransaction().commit();
		em.close();

		request.setAttribute("eventsList", results);
		return mapping.findForward("success");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.admin.actions.CrudAction#display(org.apache.struts
	 * .action.ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		String eventId = (String) dynaForm.get("eventId");

		EntityManager em = PersistenceProvider.createEntityManager();
		em.getTransaction().begin();

		// SocialEntity member = UserUtils.getAuthenticatedUser(request, em);

		MeetingFacade meetingFacade = new MeetingFacade(em);
		Meeting event = meetingFacade.getMeeting(Integer.parseInt(eventId));
		InteractionRoleFacade interactionRoleFacade = new InteractionRoleFacade(
				em);
		// boolean isSubscriber = interactionRoleFacade.isSubsriber(member,
		// event);
		Set<SocialEntity> subscribers = interactionRoleFacade
				.getSubscribers(event);

		em.getTransaction().commit();
		em.close();

		// TODO find a solution to paginate a Set

		request.setAttribute("subscribers", subscribers);
		request.setAttribute("subscriber", false);
		request.setAttribute("event", event);
		return mapping.findForward("success");
	}
}
