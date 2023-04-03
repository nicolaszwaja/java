/**
 * Klasa dekodera.
 */
public class Decoder {
    String data="";
	int mode=0;
	int num=0;
	int licznik;
	String output_="";
	/**
	 * Metoda pozwalajaca na wprowadzanie danych.
	 * @param value dostarczona wartosc
	 */
public void input( byte value ) {

	if(value==0||value==1||value==2||value==3||value==4||value==5||value==6||value==7||value==8||value==9){

    	if(licznik==5){
			output_=output1(data,num,output_);
			data="";
			num=0;
			mode=0;
			licznik=0;
	   }
	   	if(mode==0){
			if(value==0){
				mode=1;	
		}
		else{
			data=data+value;
		}
	   }
	   if(mode==1){
			num=num*10+value;
			licznik++;
	   }
	}
}
	/**
	 * Metoda pozwalajaca na pobranie wyniku dekodowania danych.
	 * @return wynik dzialania
	 */
public String output1(String data, int num, String temp){
		for(int i=0;i<num;i++){
			temp=temp+data;
		}
		return temp;
	}

	public String output(){
		String out=output1(data,num,output_);
		return out;
	}
	/**
	 * Przywrocenie poczatkowego stanu obiektu.
	 */
	public void reset() {
			data="";
			num=0;
			mode=0;
			licznik=0;
		}
	
}
	
	
	
