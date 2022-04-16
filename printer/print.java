package printer;

public class print
{
	public static void print_val(int n,int k,int[] a)
	{
		int i;
		for(i=0;i<n;i++)						
		{
			if((i+1)%k==0)
					System.out.print(a[i]+" ");
			else
					System.out.print(a[i]);
		}
	}
	
	public static void print_keys(int[][] d)
	{
		int i,j,k;
		System.out.print("\n\n\n\t\t\t\t\tKeys\n");
		System.out.print("---------------------------------------------------------------------------------\n");
		for(i=0;i<16;i++)
		{
			if(i<9)
				System.out.print("\n\tKey "+(i+1)+" :  ");
			else
				System.out.print("\n\tKey "+(i+1)+" : ");	
			for(j=0;j<48;j++)
			{
				if((j+1)%6==0)
					System.out.print(d[i][j]+" ");
				else
					System.out.print(d[i][j]);
			}
		}
		System.out.print("\n\n---------------------------------------------------------------------------------\n");
	}
}