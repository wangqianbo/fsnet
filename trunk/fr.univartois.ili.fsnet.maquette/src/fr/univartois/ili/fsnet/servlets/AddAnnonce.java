package fr.univartois.ili.fsnet.servlets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.univartois.ili.fsnet.entities.Annonce;
import fr.univartois.ili.fsnet.entities.EntiteSociale;

/**
 * author jerome bouwy Servlet implementation class AddAnnonce
 */
public class AddAnnonce extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String DATABASE_NAME = "fsnetjpa";

	private EntityManagerFactory factory;

	private EntityManager em;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddAnnonce() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() throws ServletException {
		super.init();
		factory = Persistence.createEntityManagerFactory(DATABASE_NAME);
		em = factory.createEntityManager();
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		if (em != null) {
			em.close();
		}
		if (factory != null) {
			factory.close();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id = request.getParameter("idChoisi");
		getServletContext().setAttribute("idChoisi", id);

		if (id.equalsIgnoreCase("0")) {

			RequestDispatcher dispatch = getServletContext()
					.getRequestDispatcher("/annonces.jsp");
			dispatch.forward(request, response);
		}

		else {
			RequestDispatcher dispatch = getServletContext()
					.getRequestDispatcher("/detailAnnonce.jsp");
			dispatch.forward(request, response);

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		EntiteSociale en = null;

		HttpSession session = request.getSession();
		en = (EntiteSociale) session.getAttribute("entite");

		String titre = request.getParameter("titreAnnonce");
		String contenu = request.getParameter("contenuAnnonce");
		String dateFin = request.getParameter("dateFinAnnonce");
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");

		if (titre.isEmpty() || contenu.isEmpty() || dateFin.isEmpty()) {

			request.setAttribute("titre", titre);
			request.setAttribute("contenu", contenu);
			request.setAttribute("datefin", dateFin);
			RequestDispatcher dispatch = getServletContext()
					.getRequestDispatcher("/publierannonce.jsp");
			dispatch.forward(request, response);

		} else {

			Date date = null;
			Date aujourdhui = new Date();
			try {
				date = (Date) formatter.parse(dateFin);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Annonce nouvelleInfo = new Annonce(titre, aujourdhui, contenu,
					date, "Y", en);
			em.getTransaction().begin();
			em.persist(nouvelleInfo);
			em.getTransaction().commit();

			RequestDispatcher dispatch = getServletContext()
					.getRequestDispatcher("/annonces.jsp");
			dispatch.forward(request, response);

		}
	}
}
