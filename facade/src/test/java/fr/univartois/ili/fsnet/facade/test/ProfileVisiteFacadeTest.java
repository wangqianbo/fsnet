package fr.univartois.ili.fsnet.facade.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.ProfileVisite;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.ProfileVisiteFacade;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;

public class ProfileVisiteFacadeTest {

	private EntityManager em ;
	
	private SocialEntityFacade sef;
	private ProfileVisiteFacade pvf;
	private DateFormat formatter;
	
	
	@Before 
	public void setUp(){     
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TestPU");
		em = entityManagerFactory.createEntityManager();
		sef = new SocialEntityFacade(em);
		pvf = new ProfileVisiteFacade(em);
		formatter = new SimpleDateFormat("dd/MM/yyyy");
	}
	
	@Test
	public void visiteTest(){
		em.getTransaction().begin();
		SocialEntity totoTiti = sef.createSocialEntity("toto", "titi","toto@titi.com");
		em.getTransaction().commit();
		em.getTransaction().begin();
		SocialEntity totoTutu = sef.createSocialEntity("toto", "tutu","toto@tutu.com");
		em.getTransaction().commit();
		em.getTransaction().begin();
		pvf.visite(totoTiti, totoTutu);
		em.getTransaction().commit();
		em.getTransaction().begin();
		pvf.visite(totoTiti, totoTutu);
		em.getTransaction().commit();
	}
	
	@Test 
	public void getVisitorsTest(){
		SocialEntity[] seArray= new SocialEntity[10];
		for(int i=0;i<seArray.length;i++){
			em.getTransaction().begin();
			seArray[i] = sef.createSocialEntity("toto"+i,"tata"+i,"toto.tata"+i+"@gmail.com");		
			em.getTransaction().commit();
		}
		for(int i=1;i<seArray.length;i++){
			ProfileVisite pv  = new ProfileVisite(seArray[i],seArray[i-1]);
			try {
				pv.setLastVisite(formatter.parse("0"+i+"/01/2000"));
			} catch (ParseException e) {
				fail();
			}
			em.getTransaction().begin();
			em.persist(pv);
			em.getTransaction().commit();
		}
		ProfileVisite pv  = new ProfileVisite(seArray[3],seArray[6]);
		em.getTransaction().begin();
		em.persist(pv);
		em.getTransaction().commit();
		em.getTransaction().begin();
		List<ProfileVisite> pvs = pvf.getLastVisitor(seArray[3]);
		em.getTransaction().commit();
		assertEquals(2,pvs.size());
		assertEquals(seArray[6],pvs.get(0).getVisitor());
		assertEquals(seArray[2],pvs.get(1).getVisitor());
	}
	
	@Test 
	public void getLastVisitorSinceLastConnectionTest(){
		int nb = 10;
		SocialEntity[] seArray= new SocialEntity[nb];
		for(int i=0;i<seArray.length;i++){
			em.getTransaction().begin();
			seArray[i] = sef.createSocialEntity("titi"+i,"tutu"+i,"titi.tutu"+i+"@gmail.com");		
			em.getTransaction().commit();
		}		
		List<ProfileVisite> lastVisitors = pvf
				.getLastVisitorSinceLastConnection(seArray[0]);
		assertEquals(lastVisitors.size(),0);
		for(int i=1;i<seArray.length-1;i++){
			ProfileVisite pv  = new ProfileVisite(seArray[0],seArray[i]);
			try {
				pv.setLastVisite(formatter.parse("0"+i+"/01/2000"));
			} catch (ParseException e) {
				fail();
			}
			em.getTransaction().begin();
			em.persist(pv);
			em.getTransaction().commit();
		}				
		lastVisitors = pvf
				.getLastVisitorSinceLastConnection(seArray[0]);
		assertEquals(lastVisitors.size(),nb-2);
		
		ProfileVisite pv2  = new ProfileVisite(seArray[0],seArray[nb-1]);
		try {
			pv2.setLastVisite(formatter.parse("0"+ (nb-1) +"/01/2000"));
		} catch (ParseException e) {
			fail();
		}
		em.getTransaction().begin();
		em.persist(pv2);
		em.getTransaction().commit();
		lastVisitors = pvf
				.getLastVisitorSinceLastConnection(seArray[0]);
		assertEquals(lastVisitors.size(),nb-1);
	}
	
}
