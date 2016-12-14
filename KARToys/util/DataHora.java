package util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DataHora {
	
	private SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm");  

	public String getDataAtual() {
		return formataData.format(new Date(System.currentTimeMillis()));
	}
	
	public String getHoraAtual() {
		return formataHora.format(new Date(System.currentTimeMillis()));  
	}
	
	public boolean comparaHoras(String hora) throws ParseException {
		java.util.Date horaSecao = formataHora.parse(hora);
		java.util.Date horaAtual = formataHora.parse(getHoraAtual());

		if (horaAtual.compareTo(horaSecao) == -1) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean comparaData(String data) throws ParseException {
		java.util.Date dataSecao = formataData.parse(data);
		java.util.Date dataAtual = formataData.parse(getDataAtual());

		if (dataAtual.compareTo(dataSecao) == 0 || dataAtual.compareTo(dataSecao) == -1) {
			return true;
		} else {
			return false;
		}
	}

	
}