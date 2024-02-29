package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;


	public Village(String nom, int nbVillageoisMaximum) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
	}
	public class Marche {
	    private Etal[] etals;

	    public Marche(int nombreEtals) {
	        
	    	etals = new Etal[nombreEtals];
	       
	    	for (int i = 0; i < nombreEtals; i++) {
	        	
	            etals[i] = new Etal();
	        }
	    }
	
		    public void utiliserEtal(int indice, Gaulois vendeur, String produit, int nbProduit) {
		        etals[indice].occuperEtal(vendeur, produit, nbProduit);
		    }

		    int trouverEtalLibre() {
		        for (int i = 0; i < etals.length; i++) {
		            if (!etals[i].isEtalOccupe()==true ) {
		                
		            	return i;
		            }
		            
		        }
		        return -1; 
		    }
		    public Etal[] trouverEtals(String produit) {
		        int compteur = 0;
		        
		        for (Etal etal : etals) {
		        	
		            if (etal.contientProduit(produit)) {
		                compteur++;
		                
		            }
		        }
		        
		        //creer un tableau avec les etals dans les quelles on vends un produit
		        Etal[] etalsAvecProduit = new Etal[compteur];
		        
		        int i = 0;
		        
		        for (Etal etal : etals) {
		        	
		            if (etal.contientProduit(produit)) {
		            	
		                etalsAvecProduit[i++] = etal;
		            }
		        }
		        return etalsAvecProduit;
		    }
		    
		    public Etal trouverVendeur(Gaulois gaulois) {
		        for (Etal etal : etals) {
		        	
		            if (etal.isEtalOccupe() && etal.getVendeur().equals(gaulois)) {
		                return etal; }
		        }
		        
		        return null;
		    }
		    public String afficherMarche() {
		    	
		    	StringBuilder stringBuilder = new StringBuilder();
		       
		    	int nbEtalVide = 0;
		        
		        for (Etal etal : etals) {
		        	
		            if (etal.isEtalOccupe()==true) {
		            	
		                stringBuilder.append(etal.afficherEtal());
		            } else {
		            	
		                nbEtalVide++;
		            }
		        }
		        if (nbEtalVide>0) {
		        	
		        	
		            stringBuilder.append("Il reste " + nbEtalVide + " étals non utilisés dans le marché.\n");
		        }
		        return stringBuilder.toString();
		    }
		    

	}	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
	    int indiceEtal = marche.trouverEtalLibre();
	    if (indiceEtal != -1) {
	        marche.utiliserEtal(indiceEtal, vendeur, produit, nbProduit);
	        return "Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal n°" + (indiceEtal + 1) + ".";
	    } else {
	        return "Impossible d'installer le vendeur " + vendeur.getNom() + ", aucun étal disponible.";
	    }
	}
	

	
	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les lÃ©gendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}



}