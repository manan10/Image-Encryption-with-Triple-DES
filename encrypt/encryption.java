package encrypt;
import java.util.*;

public class encryption
{
	public static int[] ip(int[] pt)		//initial permutation
	{
		int i,a;
		int ip[]=new int[]{58,50,42,34,26,18,10,2,60,52,44,36,28,20,12,4,62,54,46,38,30,22,14,6,64,56,48,40,32,24,16,8,57,49,41,33,25,17,9,1,59,51,43,35,27,19,11,3,61,53,45,37,29,21,13,5,63,55,47,39,31,23,15,7};

		for(i=0;i<64;i++)
		{
			a = ip[i];							// Permutation
			ip[i] = pt[a-1];
		}
		return ip;
	}
	
	public static int[] ip_inverse(int[] pt)		// Final permutation
	{
		int i,a;
		int ip_in[]=new int[]{40,8,48,16,56,24,64,32,39,7,47,15,55,23,63,31,38,6,46,14,54,22,62,30,37,5,45,13,53,21,61,29,36,4,44,12,52,20,60,28,35,3,43,11,51,19,59,27,34,2,42,10,50,18,58,26,33,1,41,9,49,17,57,25};
				
		for(i=0;i<64;i++)
		{
			a = ip_in[i];							// Permutation
			ip_in[i] = pt[a-1];
		}
		return ip_in;
	}
	
	public static int[] s_box(int s[][][],int ep[],int f)
	{
		int i=0,j,k;
		//decimal equivalent value of bits used to calculate rows and coloumns
		int r=(ep[f]*10)+ep[f+5];
		int c=(ep[f+1]*1000)+(ep[f+2]*100)+(ep[f+3]*10)+(ep[f+4]);		
		int temp;
		double sum=0;
		int ans[]=new int[4];			// 4-bit binary equivalent
		while(i<2)
		{
			temp=r%10;
			r=r/10;
			sum=sum+(temp*(Math.pow(2,i)));
			i++;
		}			
		r=(int)sum;				//decimal equivalent value of row		
		i=0;
		sum=0;
		while(i<4)
		{
			temp=c%10;
			c=c/10;
			sum=sum+(temp*(Math.pow(2,i)));
			i++;
		}
		c=(int)sum;				//decimal equivalent value of coloum
		temp=s[(f/6)][r][c];
		i=4;
		// Decimal to binary
		while(i>1)
		{
			ans[i-1] = temp%2;
			temp = temp/2;
			i--;
		}
		ans[0] = temp;		// 4-bit answer
		/*System.out.print("\nAns of s-box"+f+" : ");
		for(i=0;i<4;i++)
			System.out.print(ans[i]);
		*/	
		return ans;
	}
	public static int[] xor(int[] ep,int[] k,int n)
	{
		int i;
		for(i=0;i<n;i++)				//X-OR , n = no of bits
		{
			if(k[i]==ep[i])
			{
				ep[i]=0;
			}
			else
			{
				ep[i]=1;
			}
		}
		return ep;
	}
	
	public static int[] fk(int[] pt,int[] key)		//The F function
	{
		int a,i,j,k;
		int r1[] = new int[32];					// rightmost 32 bits
		int s_ep[][] = new int[8][4];			// to fetch output from each s-box
		int s_ep1[] = new int[32];				// combination of outputs of every sboxs
		for(i=0;i<32;i++)
			r1[i] = pt[i+32];
		// Expansion Array	
		int ep[] = new int[]{32,1,2,3,4,5,4,5,6,7,8,9,8,9,10,11,12,13,12,13,14,15,16,17,16,17,18,19,20,21,20,21,22,23,24,25,24,25,26,27,28,29,28,29,30,31,32,1};
		// Permutation Array
		int p[] = new int[]{16,7,20,21,29,12,28,17,1,15,23,26,5,18,31,10,2,8,24,14,32,27,3,9,19,13,30,6,22,11,4,25};
		// S-box Initialization
		int s[][][] = new int[][][]
		{
			{
				{14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},{0,15,7,4,14,2,13,1,10,6,12,11,9,5,3,8},{4,1,14,8,13,6,2,11,15,12,9,7,3,10,5,0},{15,12,8,2,4,9,1,7,5,11,3,14,10,0,6,13}
			},
			{
				{15,1,8,14,6,11,3,4,9,7,2,13,12,0,5,10},{3,13,4,7,15,2,8,14,12,0,1,10,6,9,11,5},{0,14,7,11,10,4,13,1,5,8,12,6,9,3,2,15},{13,8,10,1,3,15,4,2,11,6,7,12,0,5,14,9}
			},
			{
				{10,0,9,14,6,3,15,5,1,13,12,7,11,4,2,8},{13,7,0,9,3,4,6,10,2,8,5,14,12,11,15,1},{13,6,4,9,8,15,3,0,11,1,2,12,5,10,14,7},{1,10,13,0,6,9,8,7,4,15,14,3,11,5,2,12}
			},
			{
				{7,13,14,3,0,6,9,10,1,2,8,5,11,12,4,15},{13,8,11,5,6,15,0,3,4,7,2,12,1,10,14,9},{10,6,9,0,12,11,7,13,15,1,3,14,5,2,8,4},{3,15,0,6,10,1,13,8,9,4,5,11,12,7,2,14}
			},
			{
				{2,12,4,1,7,10,11,6,8,5,3,15,13,0,14,9},{14,11,2,12,4,7,13,1,5,0,15,10,3,9,8,6},{4,2,1,11,10,13,7,8,15,9,12,5,6,3,0,14},{11,8,12,7,1,14,2,13,6,15,0,9,10,4,5,3}
			},
			{
				{12,1,10,15,9,2,6,8,0,13,3,4,14,7,5,11},{10,15,4,2,7,12,9,5,6,1,13,14,0,11,3,8},{9,14,15,5,2,8,12,3,7,0,4,10,1,13,11,6},{4,3,2,12,9,5,15,10,11,14,1,7,6,0,8,13}
			},
			{
				{4,11,2,14,15,0,8,13,3,12,9,7,5,10,6,1},{13,0,11,7,4,9,1,10,14,3,5,12,2,15,8,6},{1,4,11,13,12,3,7,14,10,15,6,8,0,5,9,2},{6,11,13,8,1,4,10,7,9,5,0,15,14,2,3,12}
			},
			{
				{13,2,8,4,6,15,11,1,10,9,3,14,5,0,12,7},{1,15,13,8,10,3,7,4,12,5,6,11,0,14,9,2},{7,11,4,1,9,12,14,2,0,6,10,13,15,3,5,8},{2,1,14,7,4,10,8,13,15,12,9,0,3,5,6,11}
			},
		};
		
		/*for(i=0;i<8;i++)
		{
			System.out.print("\nS-Box "+i+"\n");
			for(j=0;j<4;j++)
			{
				for(k=0;k<16;k++)
				{
					System.out.print(s[i][j][k]+" ");
				}
				System.out.println();
			}
		}*/
					
		for(i=0;i<48;i++)
		{
			a = ep[i];							// expansion from 32 to 48
			ep[i] = r1[a-1];
		}
		
		ep = xor(ep,key,48);					//xor of expanded pt and key
		/*
		System.out.println("\nAfter xor EP:");
		for(i=0;i<48;i++)
		{
			if((i+1)%6==0)
				System.out.print(ep[i]+" ");
			else
				System.out.print(ep[i]);	
		}
		*/
		int f=0;
		for(i=0;i<8;i++)
		{
			s_ep[i] = s_box(s,ep,f);		// Calling all s-boxes
			f = f+6;
		}
		k=0;
		for(i=0;i<8;i++)
		{
			for(j=0;j<4;j++)
			{
				s_ep1[k] = s_ep[i][j];	 	//Combining outputs of all S-boxes 
				k++;
			}
		}
		
		/*System.out.print("\nAfter s-box : ");
		for(i=0;i<32;i++)
			System.out.print(s_ep1[i]);
		*/
		
		for(i=0;i<32;i++)
		{
			a = p[i];							// Permutation
			p[i] = s_ep1[a-1];
		}
		/*
		System.out.print("\nAfter Permutation : ");
		for(i=0;i<32;i++)
		{
			if((i+1)%4 == 0)
				System.out.print(p[i]+" ");
			else
				System.out.print(p[i]);	
		}
		*/	
		return p;	
	}
	
	public static int[] swap(int[] pt)		// To swap leftmost and rightmost 32-bits of PT
	{
		int pt1[] = new int[64];
		int i;
		for(i=0;i<32;i++)
			pt1[i+32] = pt[i];
		for(i=32;i<64;i++)
			pt1[i-32] = pt[i];
		return pt1;	 	
	}
	
	public static int[] encrypt(int[][] key,int[] pt)
	{
		int i,j;
		int r1[] = new int[32];					//for returning rightmost bits after fk

		pt = ip(pt);							// Initial Permutation
		for(i=0;i<16;i++)
		{
			r1=fk(pt,key[i]);					//call to fk	
			pt = xor(pt,r1,32);					//X-or between leftmost 32 bits and Processed rightmost 32 bits
			pt = swap(pt);
		}							//Swapping leftmost and rightmost 32 bits
		pt = swap(pt);							//Final Swap
		pt = ip_inverse(pt);					//IP-1
		return pt;								//Generated CT for the Block	
	}
	
	public static int[] decrypt(int[][] key,int[] ct)
	{
		int i,j;
		int r1[] = new int[32];					//for returning rightmost bits after fk
		ct = ip(ct);							// Initial Permutation
		for(i=15;i>=0;i--)
		{
			r1=fk(ct,key[i]);					//call to fk	
			ct = xor(ct,r1,32);					//X-or between leftmost 32 bits and Processed rightmost 32 bits
			ct = swap(ct);						//Swapping leftmost and rightmost 32 bit	
		}
		ct = swap(ct);							//Final Swap
		ct = ip_inverse(ct);					//IP-1
		return ct;								//Generated CT for the Block	
	}
}

