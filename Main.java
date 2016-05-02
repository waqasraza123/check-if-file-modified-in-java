import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {

	File ff;
	Scanner sc;
	SessionFactory factory; 
	File [] files;
	MessageDigest md; 
	Integer [] fileInts;
	byte[] digestArray;
	@SuppressWarnings("deprecation")
	

	Main() throws IOException{

		sc = new Scanner(System.in);
		System.out.println("Enter Directory you want Hashes for: ");
		ff = new File(sc.nextLine());
		files = ff.listFiles();


		try{

			factory = new Configuration().configure().buildSessionFactory();

		}catch (Throwable ex) { 
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex); 
		}

		for ( int i =0;i<files.length;i++){

			fileInts = new Integer[files.length];

			if ( files[i].isFile()){
				System.out.println(files[i].getName());

				try 
				{
					MessageDigest digest = MessageDigest.getInstance("MD5");
					FileInputStream fis = new FileInputStream(files[i].getPath());

					//Create byte array to read data in chunks
					byte[] byteArray = new byte[1024];
					int bytesCount = 0; 

					//Read file data and update in message digest
					while ((bytesCount = fis.read(byteArray)) != -1) {
						digest.update(byteArray, 0, bytesCount);
					};

					//close the stream; We don't need it now.
					fis.close();

					//Get the hash's bytes
					digestArray = digest.digest();
				}
				catch (Exception e){}

				String fileHASH = convertToString(digestArray);
				fileInts[i]  = storeTODB(files[i].getName(),files[i].getPath(), fileHASH);
			}
		}

	}

	public String convertToString(byte[] byteHash){

		String temp = new String(byteHash);
		return temp;
	}

	public Integer storeTODB(String fileName,String filePath,String Hash){

		Session session = factory.openSession();
		Transaction tx = null;
		Integer fileID = null;

		try{

			tx = session.beginTransaction();
			FilesData myFile = new FilesData(fileName,filePath,Hash);
			//			System.out.println(session.save(myFile));
			fileID = (Integer) session.save(myFile);
			System.out.println(fileID);
			tx.commit();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			session.close();
		}

		return fileID;
	}


	public static void main(String[]args) throws IOException{
		new Main();
	}

}
