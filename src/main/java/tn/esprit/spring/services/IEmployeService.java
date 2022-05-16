
package tn.esprit.spring.services;


import java.util.List;


import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;



public interface IEmployeService {
	
	public int ajouterEmploye(Employe employe);
	public Employe modifierEmploye(Employe e);
	public void mettreAjourEmailByEmployeId(String email, int employeId);
	public void affecterEmployeADepartement(int employeId, int depId);
	public void desaffecterEmployeDuDepartement(int employeId, int depId);
	public String getEmployePrenomById(int employeId);
	public void deleteEmployeById(int employeId);
	public int getNombreEmployeJPQL();
	public List<String> getAllEmployeNamesJPQL();
	public List<Employe> getAllEmployeByEntreprise(Entreprise entreprise);
	public void mettreAjourEmailByEmployeIdJPQL(String email, int employeId);
	public float getSalaireByEmployeIdJPQL(int employeId);
	public List<Employe> getAllEmployes();
	public Employe getEmployerById(int id);

	
	
	

	
}
