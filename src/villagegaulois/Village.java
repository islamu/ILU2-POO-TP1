package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;
import exceptions.VillageSansChefException;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtal) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtal);

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

//	public String afficherVillageois() {
//		StringBuilder chaine = new StringBuilder();
//		if (nbVillageois < 1) {
//			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
//		} else {
//			chaine.append("Au village du chef " + chef.getNom() + " vivent les lÃ©gendaires gaulois :\n");
//			for (int i = 0; i < nbVillageois; i++) {
//				chaine.append("- " + villageois[i].getNom() + "\n");
//			}
//		}
//		return chaine.toString();
//	}
//	

	private static class Marche {
		private Etal[] etals;

		private Marche(int nbEtals) {
			this.etals = new Etal[nbEtals];
			for (int i = 0; i < etals.length; i++) {
				etals[i] = new Etal();
			}
		}

		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			Etal etal = etals[indiceEtal];
			etal.occuperEtal(vendeur, produit, nbProduit);
		}

		private int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (!etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}
//		faut que tout le tableau soit rempli
		private Etal[] trouverEtals(String produit) {
			Etal[] temp = new Etal[etals.length];
			int lenTemp=0;
			for (int i = 0; i < temp.length; i++) {
				Etal etal = etals[i];
				if (etal.isEtalOccupe() && etal.contientProduit(produit)) {
					temp[lenTemp] = etal;
					lenTemp++;
				}
			}
			return temp;
		}

		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				Etal etal = etals[i];
				if (etal.getVendeur().equals(gaulois)) {
					return etal;
				}
			}
			return null;
		}

		public String afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			int nbEtalLibre = 0;
			for (Etal etal : etals) {
				if (etal.isEtalOccupe())
					chaine.append(etal.afficherEtal());
				else
					nbEtalLibre++;
			}
			chaine.append("Il reste " + nbEtalLibre + " étals non utilisés dans le marché.");
			return chaine.toString();
		}

	}

	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");
		int etalLibre = marche.trouverEtalLibre();
		if (etalLibre == -1) {
			chaine.append("Il n'y a pas d'Etal libre");
		} else {
			marche.utiliserEtal(etalLibre, vendeur, produit, nbProduit);
			chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " a  l'étal n°"
					+ (etalLibre + 1) + ".\n");
		}

		return chaine.toString();
	}

	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etals = marche.trouverEtals(produit);
		if (etals[0] == null)
			chaine.append("\nIl n'y a pas de vendeur qui propose des " + produit + "\n");
		else {
			chaine.append("Les vendeur qui proposent des fleurs sont : \n");
			for (Etal etal : etals) {
				if (etal != null)
					chaine.append("- " + etal.getVendeur().getNom() + "\n");
			}
		}
		return chaine.toString();
	}

	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}

	public String partirVendeur(Gaulois vendeur) {
		Etal etal = marche.trouverVendeur(vendeur);
		return etal.libererEtal();
	}

	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Le marché du village \"" + nom + "\" posséde plusieurs étals :\n" + marche.afficherMarche());
		return chaine.toString();
	}

	public String afficherVillageois() throws VillageSansChefException {
		if (chef == null)
			throw new VillageSansChefException("Il n'y a pas de chef dans ce village !");
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
}
