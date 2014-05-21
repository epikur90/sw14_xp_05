package com.pinkee.messenger.server;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinkee.messenger.model.Contact;
import com.pinkee.messenger.model.EMFService;

@SuppressWarnings("serial")
public class ContactRequestServlet extends HttpServlet {
	
	private static final Logger logger = Logger.getLogger(ContactRequestServlet.class.getCanonicalName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Handles contact requests.");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		logger.log(Level.WARNING, "email: " + email );

		EntityManager em = EMFService.get().createEntityManager();
		try {
			Contact contact = Contact.find(email, em);
			if (contact == null) {
				
				logger.log(Level.WARNING, "contact=0: " );
				resp.getWriter().println(false);
			} else {
				resp.getWriter().println(true);
			}
			
		} finally {
			em.close();
		}
	}	
	
}