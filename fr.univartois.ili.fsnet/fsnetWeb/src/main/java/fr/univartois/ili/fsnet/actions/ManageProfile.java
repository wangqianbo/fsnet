/*
 *  Copyright (C) 2010 Matthieu Proucelle <matthieu.proucelle at gmail.com>
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.sql.Date;

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
import org.apache.struts.action.DynaActionFormClass;
import org.apache.struts.config.FormBeanConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.ModuleUtils;

/**
 *
 * @author Geoffrey Boulay 
 */
public class ManageProfile extends MappingDispatchAction implements CrudAction {

    /**
     *  watched profile variable session name
     */
    public static final String WATCHED_PROFILE_VARIABLE = "watchedProfile";

    /**
     * format a name
     * exemple : entry : le BerrE return Le Berre
     * @param name string to format
     * @return format string
     */
    public static final String formatName(String name) {
        StringBuffer buf = new StringBuffer();
        boolean upperCase = true;
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            switch (c) {
                case '\'':
                    ;
                case ' ':
                    ;
                case '-':
                    buf.append(c);
                    upperCase = true;
                    break;
                default:
                    buf.append((upperCase) ? (Character.toUpperCase(c)) : (Character.toLowerCase(c)));
                    upperCase = false;
            }
        }
        return buf.toString();
    }
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("fsnetjpa");

    @Override
    public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ActionForward modify(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        EntiteSociale user = (EntiteSociale) request.getSession().getAttribute(Authenticate.AUTHENTICATED_USER);
        DynaActionForm dyna = (DynaActionForm) form;
        user.setNom(formatName(dyna.getString("name")));
        user.setPrenom(formatName(dyna.getString("firstName")));
        user.setAdresse(dyna.getString("adress"));
        try {
            user.setDateNaissance(Date.valueOf("dateOfBirth"));
        } catch (IllegalArgumentException iae) {
        }
        user.setSexe(dyna.getString("sexe"));
        user.setMdp(dyna.getString("pwd"));
        user.setProfession(formatName(dyna.getString("job")));
        user.setEmail(dyna.getString("mail"));
        user.setNumTel(dyna.getString("phone"));
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.merge(user);
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
        EntityManager em = factory.createEntityManager();
        if (form == null) {
            request.setAttribute("currentUser", request.getSession().getAttribute(Authenticate.AUTHENTICATED_USER));
            return mapping.findForward("success");
        }
        try {
            // TODO Affichage autre profile
            String idS = ((DynaActionForm) form).getString("id");
            int id = Integer.parseInt(idS);
            EntiteSociale profile = em.find(EntiteSociale.class, Integer.parseInt(idS));
            request.setAttribute(WATCHED_PROFILE_VARIABLE, profile);
            em.close();
            EntiteSociale user = (EntiteSociale) request.getSession().getAttribute(Authenticate.AUTHENTICATED_USER);
            return mapping.findForward("success");
        } catch (NumberFormatException e) {
            // TODO ERROR
        }
        return mapping.findForward("fail");
    }
}