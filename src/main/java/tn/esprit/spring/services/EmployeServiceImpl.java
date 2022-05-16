package tn.esprit.spring.services;

import java.util.ArrayList;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;

import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.TimesheetRepository;

@Service
public class EmployeServiceImpl implements IEmployeService {

	@Autowired
	EmployeRepository employeRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	ContratRepository contratRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;
	
	
	
	
	public void ajouterEmp(Employe employe)
	{
		
		if (employe != null)
		{
			employeRepository.save(employe);
		}
	}

	public int ajouterEmploye(Employe employe) {
	
		employeRepository.save(employe);
		return employe.getId();

	}

	public void mettreAjourEmailByEmployeId(String email, int employeId) {
		
		Optional<Employe> optionalemploye = employeRepository.findById(employeId);
		if (optionalemploye.isPresent())
		{
			Employe employe = optionalemploye.get();
			employe.setEmail(email);
			employeRepository.save(employe);
		}
		

	}
	
	public Employe getEmployerById(int id)
	{
		return employeRepository.findById(id).orElse(null);
	}

	
	@Transactional	
	public void affecterEmployeADepartement(int employeId, int depId) {
		
		Optional<Departement> optionldepartement = deptRepoistory.findById(depId);
		
		Optional<Employe> optionalemploye = employeRepository.findById(employeId);
		

		if(optionldepartement.isPresent() &&  optionalemploye.isPresent() )
		{
			Departement depManagedEntity = optionldepartement.get();
			Employe employeManagedEntity = optionalemploye.get();
		if (depManagedEntity.getEmployes() == null)
		{
			List<Employe> employes = new ArrayList<>();
			employes.add(employeManagedEntity);
			depManagedEntity.setEmployes(employes);
		}
		else{

			depManagedEntity.getEmployes().add(employeManagedEntity);

		}
			
		}
	}
	
	@Transactional
	public void desaffecterEmployeDuDepartement(int employeId, int depId)
	{
		Optional<Departement> optionaldepartement =deptRepoistory.findById(depId);
		
		
		if (optionaldepartement.isPresent())
		{
			Departement dep = optionaldepartement.get();
			
			int employeNb = dep.getEmployes().size();
			for(int index = 0; index < employeNb; index++){
				if(dep.getEmployes().get(index).getId() == employeId){
					dep.getEmployes().remove(index);
					break;//a revoir
				}
			}
		}
		
	}

	public int ajouterContrat(Contrat contrat) {
		contratRepoistory.save(contrat);
		return contrat.getReference();
	}
	
	public Contrat getContratById(int id)
	{
		return contratRepoistory.findById(id).orElse(null);
	}
	
	public void affecterContratAEmploye(int contratId, int employeId) {
		
		Optional<Contrat> optionalcontrat = contratRepoistory.findById(contratId);
		
		Optional<Employe> optionalemploye = employeRepository.findById(employeId);
		
		if (optionalcontrat.isPresent() && optionalemploye.isPresent())
		{
			Contrat contratManagedEntity = optionalcontrat.get();
			Employe employeManagedEntity = optionalemploye.get();
			contratManagedEntity.setEmploye(employeManagedEntity);
			contratRepoistory.save(contratManagedEntity);
		}

		
		
	}

	public String getEmployePrenomById(int employeId) {
		Optional<Employe> optionalemploye = employeRepository.findById(employeId);
		
		if (optionalemploye.isPresent())
		{
			Employe employeManagedEntity = optionalemploye.get();
			return employeManagedEntity.getPrenom();
		}
		else return "employe prénom n'existe pas";
	}
	
	public void deleteEmployeById(int employeId)
	{
	
		
		Optional<Employe> optionalemploye = employeRepository.findById(employeId);
		if (optionalemploye.isPresent())
		{
			Employe employe = optionalemploye.get();

			//Desaffecter l'employe de tous les departements
			//c'est le bout master qui permet de mettre a jour
			//la table d'association
			for(Departement dep : employe.getDepartements()){
				dep.getEmployes().remove(employe);
			}

			employeRepository.delete(employe);
		}
		
	}


	public int getNombreEmployeJPQL() {
		return employeRepository.countemp();
	}
	
	public List<String> getAllEmployeNamesJPQL() {
		return employeRepository.employeNames();

	}
	
	public List<Employe> getAllEmployeByEntreprise(Entreprise entreprise) {
		return employeRepository.getAllEmployeByEntreprisec(entreprise);
	}

	public void mettreAjourEmailByEmployeIdJPQL(String email, int employeId) {
		employeRepository.mettreAjourEmailByEmployeIdJPQL(email, employeId);

	}

	public float getSalaireByEmployeIdJPQL(int employeId) {
		return employeRepository.getSalaireByEmployeIdJPQL(employeId);
	}



	public List<Employe> getAllEmployes() {
				return (List<Employe>) employeRepository.findAll();
	}

	public Employe modifierEmploye(Employe e)
	{
		
		employeRepository.save(e);
		
		return e;
	}

}
