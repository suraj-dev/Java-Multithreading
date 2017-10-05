/*
 * Fibonacci contains methods to compute the first n fibonacci values
 */
public class Fibonacci {
	
	Fibonacci(int n) {
		fib(n);
	}
	
	/*
	 * this method recursively computes the first n fibonacci values.
	 */
	public int fib(int n) {
		if(n == 0)
	        return 0;
	    else if(n == 1)
	      return 1;
	   else
	      return fib(n - 1) + fib(n - 2);
	}
}
