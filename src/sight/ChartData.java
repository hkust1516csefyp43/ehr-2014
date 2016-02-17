package sight;

/**
*
* @author Alton
* @version 1.0
* @date 22/05/2015
* 
*/

public class ChartData<T1, T2>
{
	private T1 x;
	private T2 y;

	public ChartData(T1 x, T2 y)
	{
		this.x = x;
		this.y = y;
	}

	public void setX(T1 x)
	{
		this.x = x;
	}

	public void setY(T2 y)
	{
		this.y = y;
	}

	public T1 getX()
	{
		return x;
	}

	public T2 getY()
	{
		return y;
	}
}
