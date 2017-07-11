#include <iostream>
//골드 바흐의 추측
/*
	1742년, 독일의 아마추어 수학가 크리스티안 골드바흐는 레온하르트 오일러에게 다음과 같은 추측을 제안하는 편지를 보냈다.
	4보다 큰 모든 짝수는 두 홀수 소수의 합으로 나타낼 수 있다.
	예를 들어 8은 3 + 5로 나타낼 수 있고, 3과 5는 모두 홀수인 소수이다. 또, 20 = 3 + 17 = 7 + 13, 42 = 5 + 37 = 11 + 31 = 13 + 29 = 19 + 23 이다.
	이 추측은 아직도 해결되지 않은 문제이다.
	백만 이하의 모든 짝수에 대해서, 이 추측을 검증하는 프로그램을 작성하시오.
*/
using namespace std;
const int MAX = 1000000;
int pn;
bool check[MAX+1];
int main() {

	check[0] = check[1] = true;
	for (int i = 2;i*i < MAX;i++)
	{
		if (check[i] == false)
		{
			for (int j = i + i;j <= MAX;j += i) {
				check[j] = true;
			}
		}
	}

	int n = 1;
	while (n != 0)
	{
		cin >> n;
		for (int i = 2;i < n;i++)
		{
			if (check[i] == false && check[n - i] == false)
			{
					cout << n << "=" << i << "+" << n - i << endl;
					break;
			}
		}
	}
	

	//system("pause");

	return 0;
}