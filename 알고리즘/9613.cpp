#include <iostream>

using namespace std;
int gcd(int x, int y) {
	if (y == 0) return x;
	else return gcd(y, x%y);
}

int main() {

	int t;//test 케이스 개수
	int n = 0;
	
	int ary_num[30];

	cin >> t;
	
	for (int i = 0;i < t;i++)
	{
		int sum_of_gcd = 0;
		cin >> n;
		for (int j = 0; j < n; j++) {
			cin >> ary_num[j];
		}

		for (int i = 0; i < n - 1;i++)
		{
			for (int j = i + 1; j < n;j++) {
				sum_of_gcd += gcd(ary_num[i], ary_num[j]);
			}

		}
		cout << sum_of_gcd << endl;
	}
	//system("pause");

	return 0;
}