import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class SerwisAukcyjny implements Aukcja {

	public int aktualnaCena=0;

	public class PrzedmiotAukcji implements Aukcja.PrzedmiotAukcji {

		public int id,oferta,cena;
		public String name;

		public PrzedmiotAukcji(int i, String n, int o, int c){
			id=i;
			name=n;
			oferta=o;
			cena=c;
		}
		public void setAktualnaCena(int i){
			cena=i;
		}
		public void setAktualnaOferta(int i){
			oferta=i;
		}
		public int identyfikator(){
			return id;
		}
		public String nazwaPrzedmiotu(){
			return name;
		}
		public int aktualnaOferta(){
			return oferta;
		}
		public int aktualnaCena(){
			
				if (oferta > cena)
					return oferta;
				else
					return cena;
			
		}
	}

	public class Oferta{
		public int oferta;
		public String uzytkownik;

		public Oferta(int o, String u){
			oferta = o;
			uzytkownik = u;
		}
	}

	public class PrzedmiotAukcjiInfo{
		public PrzedmiotAukcji przedmiot;
		public List<String> obserwujacy;
		public List<Oferta> oferty;
		public boolean koniec = false;

		public PrzedmiotAukcjiInfo(PrzedmiotAukcji p, List<String> obs, List<Oferta> o){
			przedmiot = p;
			obserwujacy = obs;
			oferty = o;
		}
	}

	Map<Integer, PrzedmiotAukcjiInfo> przedmioty = new HashMap<>();

	public void dodajPrzedmiotAukcji(Aukcja.PrzedmiotAukcji przedmiot){
		PrzedmiotAukcji pa = new PrzedmiotAukcji(przedmiot.identyfikator(), przedmiot.nazwaPrzedmiotu(),przedmiot.aktualnaOferta(), przedmiot.aktualnaCena());
		PrzedmiotAukcjiInfo pai = new PrzedmiotAukcjiInfo(pa, new ArrayList<String>(), new ArrayList<Oferta>());
		przedmioty.put(pa.id,pai);
	}

	public class Uzytkownik{
		public String username;
		public Powiadomienie kontakt;

		public Uzytkownik(String u, Powiadomienie k){
			username = u;
			kontakt = k;
		}
	}

	List<Uzytkownik> uzytkownicy = new ArrayList<>();

	public void dodajUżytkownika(String username, Powiadomienie kontakt){
		Uzytkownik user = new Uzytkownik(username,kontakt);
		uzytkownicy.add(user);
	}

	public void subskrypcjaPowiadomień(String username, int identyfikatorPrzedmiotuAukcji){
		przedmioty.get(identyfikatorPrzedmiotuAukcji).obserwujacy.add(username);
	}

	public void rezygnacjaZPowiadomień(String username, int identyfikatorPrzedmiotuAukcji){
		przedmioty.get(identyfikatorPrzedmiotuAukcji).obserwujacy.remove(username);
	}

	public void oferta(String username, int identyfikatorPrzedmiotuAukcji, int oferowanaKwota){

		int kwota = najwyższaOferta(identyfikatorPrzedmiotuAukcji);

		if(przedmioty.get(identyfikatorPrzedmiotuAukcji).koniec==false){
			Oferta nowaOferta = new Oferta(oferowanaKwota,username);
			przedmioty.get(identyfikatorPrzedmiotuAukcji).przedmiot.setAktualnaOferta(oferowanaKwota);
			przedmioty.get(identyfikatorPrzedmiotuAukcji).oferty.add(nowaOferta);
		}

		if(oferowanaKwota>kwota){
			for(String obs: przedmioty.get(identyfikatorPrzedmiotuAukcji).obserwujacy){
				for(Uzytkownik u: uzytkownicy){
					if(u.username==obs && u.username!=username){
						Powiadomienie pow = u.kontakt;
						pow.przebitoTwojąOfertę(przedmioty.get(identyfikatorPrzedmiotuAukcji).przedmiot);
					}
				}
			}
		}
	}

	public void koniecAukcji(int identyfikatorPrzedmiotuAukcji){
		przedmioty.get(identyfikatorPrzedmiotuAukcji).koniec=true;
	}

	public String ktoWygrywa(int identyfikatorPrzedmiotuAukcji){
		List<Oferta> dane = new ArrayList<>();
		dane = przedmioty.get(identyfikatorPrzedmiotuAukcji).oferty;
		Oferta wygrana = new Oferta(0,"temp");
		for(Oferta o:dane){
			if(o.oferta>wygrana.oferta){
				wygrana=o;
			}
		}
		return wygrana.uzytkownik;
	}

	public int najwyższaOferta(int identyfikatorPrzedmiotuAukcji){
		int najwyzsza=0;

			if(przedmioty.get(identyfikatorPrzedmiotuAukcji).oferty.size()==1){
				for(Oferta o : przedmioty.get(identyfikatorPrzedmiotuAukcji).oferty){
					najwyzsza=o.oferta;
				}
			}
			else{
				for(Oferta o : przedmioty.get(identyfikatorPrzedmiotuAukcji).oferty){
					if(o.oferta>najwyzsza)
						najwyzsza=o.oferta;
				}	
			}
		return najwyzsza;
	}

	@Override
	public void dodajUzytkownika(String username, Aukcja.Powiadomienie kontakt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void subskrypcjaPowiadomien(String username, int identyfikatorPrzedmiotuAukcji) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rezygnacjaZPowiadomien(String username, int identyfikatorPrzedmiotuAukcji) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int najwyzszaOferta(int identyfikatorPrzedmiotuAukcji) {
		// TODO Auto-generated method stub
		return 0;
	}
}