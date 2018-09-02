package main;

public class DataPacket {
	
	private String word;
	private String meaning;
	
	public void setString(String newWord) {
		this.word = newWord;
	}
	
	public String getString() {
		return this.word;
	}
	
	public void setMeaning(String newMeaning) {
		this.meaning = newMeaning;
	}
	
	public String getMeaning() {
		return this.meaning;
	}
	
}
