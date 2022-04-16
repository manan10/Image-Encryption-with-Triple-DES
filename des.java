import keygen.keyg;
import encrypt.encryption;
import printer.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import javax.imageio.*;
import javax.imageio.stream.*;
import java.awt.image.*;
import java.util.concurrent.TimeUnit;
import java.awt.*;

public class des
{
	public static void main(String args[])
	{
		int i,j,len,pos=0,k;
		String key1,key2,key3,ptext;			//	Initial key
		int pc1[] = new int[56];				//  To fetch 56-bit key from key1 after parity drop
		int pc2[] = new int[56];				//  To fetch 56-bit key from key2 after parity drop
		int pc3[] = new int[56];				//  To fetch 56-bit key from key3 after parity drop
		int k1[][] = new int[16][48];			//  To fetch the 16 keys of Key-1
		int k2[][] = new int[16][48];			//  To fetch the 16 keys of Key-2
		int k3[][] = new int[16][48];			//  To fetch the 16 keys of Key-3	
		int pt[] = new int[64];					//  To fetch original plaintext and final result
		int pt1[] = new int[64];				//  To fetch plaintext after Step-1 (E(k1)) 
		int pt2[] = new int[64];				//  To fetch plaintext after Step-2 (D(k2))
		int ct[] = new int[64];					//  To fetch ciphertext after Step-3(E(k3))
		int ct1[] = new int[64];				//  To fetch ciphertext after Step-4(D(k3))
		int ct2[] = new int[64];				//  To fetch ciphertext after Step-5(E(k2))
		Scanner s = new Scanner(System.in);
		
		try
		{
			len = img_bin.gen_binary();
			TimeUnit.SECONDS.sleep(2);
			System.out.print("\nImage Processed...");
			
			key1  = "0001001100110100010101110111100110011011101111001101111111110001";
			key2  = "0001010100110100001001010101000101010010111010111111001010100110";
			key3  = "1001010101010101001011110110010101001100000101010010101111001010";
			
			pc1 = keyg.parity_drop(key1);			// To calculate 56-bit key after parity drop and permutation for Key-1
			pc2 = keyg.parity_drop(key2);			// To calculate 56-bit key after parity drop and permutation for Key-2
			pc3 = keyg.parity_drop(key3);			// To calculate 56-bit key after parity drop and permutation for Key-3
			
			k1 = keyg.gen_key(pc1);					// To generate all 16 keys of Key-1
			k2 = keyg.gen_key(pc2);					// To generate all 16 keys of Key-2
			k3 = keyg.gen_key(pc3);					// To generate all 16 keys of Key-3
			TimeUnit.SECONDS.sleep(2);
			System.out.print("\nKeys Generated...");

			String data = new String(Files.readAllBytes(Paths.get("out.txt")));
			BufferedWriter outp = new BufferedWriter(new FileWriter("output.txt"));
			String outdata;
		
			for(k=0;k<len;k++)
			{
				for(i=0;i<64;i++)				//64-bit key in array from string
					pt[i] = Character.getNumericValue(data.charAt(pos+i));

				pt1 = encryption.encrypt(k1,pt);		//Step-1 (E(k1))	
				pt2 = encryption.decrypt(k2,pt1);		//Step-2 (D(k2))
				ct = encryption.encrypt(k3,pt2);		//Step-3 (E(k3))	
				ct1 = encryption.decrypt(k3,ct);		//Step-4 (D(k3))
				ct2 = encryption.encrypt(k2,ct1);		//Step-5 (E(k2))	
				pt = encryption.decrypt(k1,ct2);		//Step-6 (D(k1))
				for(i=0;i<64;i++)
					outp.write(pt[i]+"");
				pos = pos+64;	
			}
			outp.close();
			outdata = new String(Files.readAllBytes(Paths.get("output.txt")));
			TimeUnit.SECONDS.sleep(2);
			System.out.print("\nEncryption Completed...");
			//img_bin.getImage(outdata,len*64);
			TimeUnit.SECONDS.sleep(2);
			if(outdata.equals(data))
			{
				System.out.print("\nOutput data matched successfully.");
				System.out.print("\nDecrypted Successfully.");
			}
			else
				System.out.print("\nMatch Unsucceessful");
			System.out.print("\n\n------------------------------------------------------------------------------------------------------------\n");
		}
		catch(IOException e)
		{
			System.out.println(e);
		}	
		catch(InterruptedException ie)
		{
			System.out.println(ie);
		}
	}
}

class img_bin
{
	public static int gen_binary()
	{
		int len=0;
		try
		{
			Scanner sc = new Scanner(System.in);
			String ext, fName;
			int cnt = 0;
			
			System.out.print("\n------------------------------------------------------------------------------------------------------------\n");
			System.out.print("\tEnter Image Name(With Extension):  ");
			fName = sc.next();
			System.out.print("------------------------------------------------------------------------------------------------------------\n");
			ext = fName.substring(fName.indexOf(".") + 1,fName.length());
			
			File in = new File("image/"+fName);
			FileWriter out = new FileWriter("out.txt");
			BufferedImage img = ImageIO.read(in);
			ByteArrayOutputStream binaryImage = new ByteArrayOutputStream();
			ImageIO.write(img, ext, binaryImage);
			
			byte[] imageToBinary = binaryImage.toByteArray();
		
			StringBuilder sb = new StringBuilder();
			for(byte b : imageToBinary)
				sb.append(Integer.toBinaryString(b & 0xFF));
				
			len = sb.toString().length();	
			int mod = len % 64;
			if(mod != 0)
			{
				sb.append(1);
				for(int j=0;j<(64-mod-1);j++)
					sb.append(0);
				len = sb.toString().length();
			}
			out.write(sb.toString());
			out.close();
		}
		catch(IOException i)
		{
			System.out.println("No such image found");				
		} 	
		return len/64;
	}
	
}
