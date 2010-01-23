package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;

import fr.univartois.ili.fsnet.auth.Authenticate;
import fr.univartois.ili.fsnet.entities.EntiteSociale;
import fr.univartois.ili.fsnet.entities.Message;
import fr.univartois.ili.fsnet.entities.Topic;
import fr.univartois.ili.fsnet.form.ProfileForm;

/**
 *
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class ManageTopicMessages extends MappingDispatchAction implements CrudAction {
	
	private static EntityManagerFactory factory = Persistence
	.createEntityManagerFactory("fsnetjpa");
	
	private static final Logger logger = Logger.getAnonymousLogger();

    @Override
    public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	logger.info("create Message: ");
    	EntityManager em = factory.createEntityManager();
		DynaActionForm dynaForm = (DynaActionForm) form;
		String messageDescription = (String) dynaForm.get("messageDescription");
		int topicId = Integer.valueOf(Integer.parseInt(dynaForm.getString("topicId")));
		Topic topic = em.find(Topic.class, topicId);
		
		Date date = new Date();
		
		EntiteSociale entiteSociale = (EntiteSociale) request.getSession()
				.getAttribute(Authenticate.AUTHENTICATED_USER);
		Message message = new Message(messageDescription, date, entiteSociale, topic);
		
		em.getTransaction().begin();
		topic.getLesMessages().add(message);
		em.getTransaction().commit();
		
		em.close();
		return mapping.findForward("success");
    }

    @Override
    public ActionForward modify(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	
        logger.info("modify Message ");
        EntityManager em = factory.createEntityManager();
        DynaActionForm dynaForm = (DynaActionForm) form;
        String messageDescription = (String) dynaForm.get("messageDescription");
		int messageId = Integer.valueOf(Integer.parseInt(dynaForm.getString("messageId")));
        Message message = em.find(Message.class, messageId);
        message.setContenu(messageDescription);
        int topicId = Integer.valueOf(Integer.parseInt(dynaForm.getString("topicId")));
		Topic topic = em.find(Topic.class, topicId);

        em.getTransaction().begin();
        em.merge(message);
        topic.getLesMessages();
        em.getTransaction().commit();
        em.close();
        return mapping.findForward("success");
    }

    @Override
    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ActionForward display(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	logger.info("display Message: ");
    	String topicId = request.getParameter("topicId");
    	request.setAttribute("topicId", topicId);
    	String messageId = request.getParameter("messageId");
    	
    	if(messageId!=null){
    		EntityManager em = factory.createEntityManager();
        	Message message = em.find(Message.class, Integer.parseInt(messageId));
    		request.setAttribute("message", message);
    	}
    	
    	return mapping.findForward("success");
    }
}