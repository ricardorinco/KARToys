package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.MaskFormatter;
import javax.swing.text.PlainDocument;

@SuppressWarnings("serial")
public class Text extends PlainDocument {
	
	private DateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
	private int maximoCaracter;
	public enum TipoMesagem { Atencao, Erro, Padrao;} 
	
	public Text(int quantidadeCaracteres) {
		super();
		maximoCaracter = quantidadeCaracteres;
	}
	
	public Text() {}
		
	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		if (str == null) {
			return;
		}
		
		if (maximoCaracter <= 0) {
			super.insertString(offset, str, attr);
		}
		
		int tamanho = (getLength() + str.length());
		
		if (tamanho <= maximoCaracter) {
			super.insertString(offset, str, attr);
		}
	}
	
	public String replace(String str) {
		return str.replaceAll("[^0123456789]", "");
	}
	
	public MaskFormatter setMask(String Mascara) throws ParseException {
		MaskFormatter stringMascara = new MaskFormatter();
		stringMascara.setMask(Mascara);
		stringMascara.setPlaceholderCharacter(' ');
		return stringMascara;
	}

	public String formataData(Date data) {
		return formataData.format(data);
	}

	public Date formataData(String data) throws ParseException {
		return formataData.parse(data);
	}
	
}
