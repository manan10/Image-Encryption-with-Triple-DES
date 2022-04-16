package keygen;
import java.util.*;

public class keyg
{	
	public static int[] left_shift(int[] pc1)
	{
		int temp,i;
		
		temp = pc1[0];

		for(i=1;i<28;i++)						//Circular-LS of the leftmost 28 bits
			pc1[i-1] = pc1[i];
		pc1[27] = temp;
		
		temp = pc1[28];
		for(i=29;i<56;i++)						//Circular-LS of the rightmost 28 bits
			pc1[i-1] = pc1[i];
		pc1[55] = temp;

		return pc1;							//Shifted pc1  
	}
	
	public static int[][] gen_key(int pc1[])
	{
		int i,j,a;
		
		// To fetch all 16 keys
		int key[][] = new int[16][48];
		// PC-2 Table
		int pc2[] = new int[]
		{14,17,11,24,1,5,3,28,15,6,21,10,23,19,12,4,26,8,16,7,27,20,13,2,41,52,31,37,47,55,30,40,51,45,33,48,44,49,39,56,34,53,46,42,50,36,29,32};

		for(i=0;i<16;i++)
		{
			/*
				Shifting rules: 
					Round 1,2,9,16 - Shift left by 1 bit
					Other Rounds   - Shift left by 2 bits			
			*/
			if(i==0 || i==1 || i==8 || i==15)
				pc1 = left_shift(pc1);			// Left Shifting 28 leftmost and rightmost bits seperately
			else
			{
				pc1 = left_shift(pc1);
				pc1 = left_shift(pc1);
			}	
			/*System.out.print("\nPC-1 : ");
			for(j=0;j<56;j++)
				System.out.print(pc1[j]);*/
			for(j=0;j<48;j++)
			{
				a = pc2[j];						//Permutation and reduction(56-bit to 48-bit)
				key[i][j] = pc1[a-1];
			}
		}	
		return key;								//All keys generated
	}
	
	public static int[] parity_drop(String key1)
	{
		int i,j,a;
		// PC-1 Table	
		int[] pc1 = new int[]{57,49,41,33,25,17,9,1,58,50,42,34,26,18,10,2,59,51,43,35,27,19,11,3,60,52,44,36,63,55,47,39,31,23,15,7,62,54,46,38,30,22,14,6,61,53,45,37,29,21,13,5,28,20,12,4};
		int key[] = new int[64];
		
		//64-bit key in array from string		
		for(i=0;i<key1.length();i++)
		{														
			key[i] = Character.getNumericValue(key1.charAt(i));		
		}			
		
		for(i=0;i<56;i++)
		{
			a = pc1[i];							//Permutation PC-1 with removal of parity bits
			pc1[i] = key[a-1];
		}	
		return pc1;	
	}
}
