#include <iostream>
using namespace std;
const int MAX = 10000000;
bool check[MAX + 1];
int main()
{
	//bool check[MAX + 1]; // ���������� �ϸ� �������� �� ���� �ȿ��� ������ ����?
	check[0] = check[1] = true;
	for (int i = 2;i*i <= MAX;i++)
	{
		if (check[i] == false)
		{
			for (int j = i + i;j <= MAX;j += i) {
				check[j] = true;
			}
		}
	}
	int m, n;

	cin >> m >> n;

	for (int i = m;i <= n;i++)
	{
		if (check[i] == false)
		{
			cout << i << '/n';
		}
	}

	system("pause");

	return 0;
}