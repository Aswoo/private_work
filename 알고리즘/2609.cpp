#include <iostream>
using namespace std;
int gcd(int, int);
int main() {

	int a = 0;
	int b = 0;
	cin >> a >> b;
	int _gcd = gcd(a, b);
	cout << _gcd << endl;
	cout << a*b/_gcd << endl;

	return 0;
}
int gcd(int a, int b) {
	while (b != 0) {
		int r = a%b;
		a = b;
		b = r;
	}
	return a;
}