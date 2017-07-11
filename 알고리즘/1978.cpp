#include <iostream>

using namespace std;

bool prime(int n) { 
	if (n < 2) { 
		return false;
	} 
	for (int i = 2; i*i <= n; i++) { 
		if (n % i == 0) 
		{
			return false; 
		} 
	} 
	return true;
}

int main() {

	int number = 0;
	int count = 0;
	cin >> number;

	for (int i = 0;i < number;i++)
	{
		int prime_num;
		cin >> prime_num;
		if (prime(prime_num))
		{
			count++;
		}
	}

	cout << count;
	//system("pause");

	return 0;
}