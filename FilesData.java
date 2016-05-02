import javax.persistence.Entity;

@Entity
public class FilesData {
	Integer id;
	String fileName;
	String fileHash;
	String filePath;
	
	FilesData (String fName,String fPath,String fHash){
		this.fileName = fName;
		this.filePath = fPath;
		this.fileHash = fHash;
	}
	
	
	public void setId(Integer id){
		this.id = id;
	}
	public Integer getId(){
		return id;
	}
	
	public void setFileName(String name){
		this.fileName = name;
	}
	public String  getFileName(){
		return fileName;
	}
	
	public void setFilePath(String path){
		this.filePath  = path;
	}
	public String getFilePath(){
		return filePath;
	}
	
	public void setFileHash(String hash){
		this.fileHash  = hash;
	}
	public String getFileHash(){
		return fileHash;
	}
	
	

}
