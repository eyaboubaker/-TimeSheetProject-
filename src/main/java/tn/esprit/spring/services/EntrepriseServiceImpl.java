package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Departement;

import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;

@Service
public class EntrepriseServiceImpl implements IEntrepriseService {

	@Autowired
    EntrepriseRepository entrepriseRepoistory;
	@Autowired
	DepartementRepository deptRepoistory;
	
	public int ajouterEntreprise(Entreprise entreprise) {
		entrepriseRepoistory.save(entreprise);
		return entreprise.getId();
	}

	public int ajouterDepartement(Departement dep) {
		deptRepoistory.save(dep);
		return dep.getId();
	}
	
	public void affecterDepartementAEntreprise(int depId, int entrepriseId) {
		//Le bout Master de cette relation N:1 est departement  
				//donc il faut rajouter l'entreprise a departement 
				// ==> c'est l'objet departement(le master) qui va mettre a jour l'association
				//Rappel : la classe qui contient mappedBy represente le bout Slave
				//Rappel : Dans une relation oneToMany le mappedBy doit etre du cote one.
		Optional<Entreprise> optionalentreprise = entrepriseRepoistory.findById(entrepriseId);
		Optional<Departement> optionaledepartement = deptRepoistory.findById(depId);
		
		if (optionalentreprise.isPresent() && optionaledepartement.isPresent())
		{
			Entreprise entrepriseManagedEntity = optionalentreprise.get();
			Departement depManagedEntity = optionaledepartement.get();
			
			depManagedEntity.setEntreprise(entrepriseManagedEntity);
			deptRepoistory.save(depManagedEntity);
		}
				
		
	}
	
	public List<String> getAllDepartementsNamesByEntreprise(int entrepriseId) {
		List<String> depNames = new ArrayList<>();
		Optional<Entreprise> optionalentreprise =  entrepriseRepoistory.findById(entrepriseId);
		
		if (optionalentreprise.isPresent())
		{
			Entreprise entrepriseManagedEntity =optionalentreprise.get();
			
			for(Departement dep : entrepriseManagedEntity.getDepartements()){
				depNames.add(dep.getName());
			}
			
		
		}
		return depNames;
	}

	@Transactional
	public void deleteEntrepriseById(int entrepriseId) {
		
		Optional<Entreprise> optionalentreprise = entrepriseRepoistory.findById(entrepriseId);
		if (optionalentreprise.isPresent())
		{
			entrepriseRepoistory.delete(optionalentreprise.get());	
		}
		
	}

	@Transactional
	public void deleteDepartementById(int depId) {
		
		Optional<Departement> optionalemploye = deptRepoistory.findById(depId);
		if (optionalemploye.isPresent())
		{
			deptRepoistory.delete(optionalemploye.get());	
		}
	}


	public Entreprise getEntrepriseById(int entrepriseId) {
		
		Optional<Entreprise> optionalentreprise =  entrepriseRepoistory.findById(entrepriseId);
		if (optionalentreprise.isPresent())
		{
			return optionalentreprise.get();	
		}
		else 
			return null;
	
	}

}
